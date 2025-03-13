package cat.itacademy.s05.t02.Controllers;

import cat.itacademy.s05.t02.DTOs.Robo.CreateRoboDTO;
import cat.itacademy.s05.t02.DTOs.Robo.RoboDTO;
import cat.itacademy.s05.t02.Exceptions.RoboNotFoundException;
import cat.itacademy.s05.t02.Models.Robo;
import cat.itacademy.s05.t02.Service.RoboService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/robos")
public class RoboController {

    private static final Logger logger = LogManager.getLogger(RoboController.class);

    @Autowired
    private RoboService roboService;

    @PostMapping("/build")
    @Operation(summary = "Build a new robo", description = "Builds a new robo and associates it with a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Robo created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<RoboDTO> buildRobo(@RequestBody CreateRoboDTO createRoboDTO) {
        Robo robo = new Robo(createRoboDTO.getName(), createRoboDTO.getType(), createRoboDTO.getUserId());
        Robo savedRobo = roboService.buildRobo(robo);
        RoboDTO roboDTO = new RoboDTO(savedRobo.getId(), savedRobo.getName(), savedRobo.getType(), savedRobo.getUserId());
        return ResponseEntity.ok(roboDTO);
    }

    @DeleteMapping("/destroy/{id}")
    @Operation(summary = "Destroys a robo", description = "Destroys a robo by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Robo deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Robo not found")
    })
    public ResponseEntity<String> destroyRobo(@PathVariable Long id) {
        Robo existingRobo = roboService.getRoboById(id);
        if (existingRobo == null) {
            logger.error("Robo not found: " + id);
            throw new RoboNotFoundException("Robo not found with id: " + id);
        }
        roboService.destroyRobo(id);
        return ResponseEntity.ok("Robo deleted successfully");
    }

    @GetMapping("/all")
    @Operation(summary = "Get all robos", description = "Retrieves all robos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Robos retrieved successfully")
    })
    public ResponseEntity<List<RoboDTO>> getAllRobos() {
        List<Robo> robos = roboService.getAllRobos();
        List<RoboDTO> roboDTOs = robos.stream()
                .map(robo -> new RoboDTO(robo.getId(), robo.getName(), robo.getType(), robo.getUserId()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(roboDTOs);
    }

    @PutMapping("/update")
    @Operation(summary = "Update a robo's name", description = "Updates the name of an existing robo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Robo updated successfully"),
            @ApiResponse(responseCode = "404", description = "Robo not found")
    })
    public ResponseEntity<RoboDTO> updateRobo(@RequestParam Long id, @RequestParam String name) {
        Robo existingRobo = roboService.getRoboById(id);
        if (existingRobo == null) {
            logger.error("Robo not found: " + id);
            throw new RoboNotFoundException("Robo not found with id: " + id);
        }
        existingRobo.setName(name);
        Robo updatedRobo = roboService.updateRobo(existingRobo);
        RoboDTO roboDTO = new RoboDTO(updatedRobo.getId(), updatedRobo.getName(), updatedRobo.getType(), updatedRobo.getUserId());
        return ResponseEntity.ok(roboDTO);
    }
}
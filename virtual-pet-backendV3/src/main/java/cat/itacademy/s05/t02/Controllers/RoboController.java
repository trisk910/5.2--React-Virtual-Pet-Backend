package cat.itacademy.s05.t02.Controllers;

import cat.itacademy.s05.t02.DTOs.Robo.*;
import cat.itacademy.s05.t02.Exceptions.InsuficientCreditsException;
import cat.itacademy.s05.t02.Exceptions.RoboNotFoundException;
import cat.itacademy.s05.t02.Models.Robo;
import cat.itacademy.s05.t02.Models.User;
import cat.itacademy.s05.t02.Service.RoboService;
import cat.itacademy.s05.t02.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
            @ApiResponse(responseCode = "200", description = "Robo built successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "400", description = "Insufficient Credits")
    })
    public ResponseEntity<String> buildRobo(@RequestBody CreateRoboDTO createRoboDTO) {
        try {
            Robo robo = new Robo(
                    createRoboDTO.getName(), createRoboDTO.getType(), createRoboDTO.getUserId()
            );
            roboService.buildRobo(robo);
            return ResponseEntity.ok("Robo built successfully");
        } catch (InsuficientCreditsException e) {
            return ResponseEntity.badRequest().body("Insufficient Credits");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid input");
        }
    }


    @DeleteMapping("/destroy/{id}")
    @Operation(summary = "Destroys a robo", description = "Destroys a robo by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Robo deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Robo not found")
    })
    public ResponseEntity<String> destroyRobo(@PathVariable Long id) {
        try {
            Robo existingRobo = roboService.getRoboById(id);
            if (existingRobo == null) {
                throw new RoboNotFoundException("Robo not found with id: " + id);
            }
            roboService.destroyRobo(id);
            return ResponseEntity.ok("Robo destroyed successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all robos", description = "Retrieves all robos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Robos retrieved successfully")
    })
    public ResponseEntity<AdminRobosDTO> getAllRobos() {
        List<Robo> robos = roboService.getAllRobos();
        List<RoboResponseDTO> roboDTOs = robos.stream()
                .map(robo -> new RoboResponseDTO(
                        robo.getId(), robo.getName(), robo.getType(), robo.getUserId(),
                        robo.getHealth(), robo.getAttack(), robo.getDefense(), robo.getSpeed(), robo.getLevel()))
                .collect(Collectors.toList());

        AdminRobosDTO adminRobosDTO = new AdminRobosDTO(roboDTOs.size(), roboDTOs);
        return ResponseEntity.ok(adminRobosDTO);
    }


    @GetMapping("/get/{id}")
    @Operation(summary = "Get robos by user ID", description = "Retrieves all robos associated with a user by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Robos retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserRobosDTO> getRobosByUserId(@PathVariable Long id) {
        List<Robo> robos = roboService.getRobosByUserId(id);
        List<RoboResponseDTO> roboDTOs = robos.stream()
                .map(robo -> new RoboResponseDTO(
                        robo.getId(), robo.getName(), robo.getType(), robo.getUserId(),
                        robo.getHealth(), robo.getAttack(), robo.getDefense(), robo.getSpeed(),robo.getLevel()))
                .collect(Collectors.toList());
        UserRobosDTO userRobosDTO = new UserRobosDTO(id, roboDTOs);
        return ResponseEntity.ok(userRobosDTO);
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
        RoboDTO roboDTO = new RoboDTO( updatedRobo.getId(), updatedRobo.getName(), updatedRobo.getType(),
                updatedRobo.getUserId(), updatedRobo.getHealth(),  updatedRobo.getAttack(), updatedRobo.getDefense(),
                updatedRobo.getSpeed(), updatedRobo.getLevel()
        );
        return ResponseEntity.ok(roboDTO);
    }

    @PutMapping("/repair/{id}")
    @Operation(summary = "Repair a robo", description = "Restores the stats of a robo to their original values")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Robo repaired successfully"),
            @ApiResponse(responseCode = "404", description = "Robo not found")
    })
    public ResponseEntity<RoboDTO> repairRobo(@PathVariable Long id) {
        Robo existingRobo = roboService.getRoboById(id);
        if (existingRobo == null) {
            logger.error("Robo not found: " + id);
            throw new RoboNotFoundException("Robo not found with id: " + id);
        }
        /*existingRobo.setHealth(existingRobo.getOriginalHealth());
        existingRobo.setAttack(existingRobo.getOriginalAttack());
        existingRobo.setDefense(existingRobo.getOriginalDefense());
        existingRobo.setSpeed(existingRobo.getOriginalSpeed());*/
        roboService.repairRobo(existingRobo);
        Robo updatedRobo = roboService.updateRobo(existingRobo);
        RoboDTO roboDTO = new RoboDTO(
                updatedRobo.getId(), updatedRobo.getName(), updatedRobo.getType(),
                updatedRobo.getUserId(), updatedRobo.getHealth(), updatedRobo.getAttack(),
                updatedRobo.getDefense(), updatedRobo.getSpeed(), updatedRobo.getLevel()
        );
        return ResponseEntity.ok(roboDTO);
    }

    @PostMapping("/repair/all")
    @Operation(summary = "Repair all robos", description = "Restores the stats of all robos to their original values")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All robos repaired successfully")
    })
    public ResponseEntity<String> repairAllRobos() {
        roboService.repairAllRobos();
        return ResponseEntity.ok("All robos repaired successfully");
    }


    @PostMapping("/{id}/upgrade")
    @Operation(summary = "Upgrade a robo", description = "Upgrades a robo by increasing its stats")
    public ResponseEntity<String> upgradeRobo(@PathVariable Long id) {
        roboService.repairRobo(roboService.getRoboById(id));
        boolean success = roboService.upgradeRobo(id);
        if (success) {
            return ResponseEntity.ok("Robo upgraded successfully");
        } else {
            return ResponseEntity.badRequest().body("Insufficient currency or invalid robo");
        }
    }


    @GetMapping("/{id}/upgrade-cost")
    @Operation(summary = "Get upgrade cost of a robo", description = "Retrieves the cost of upgrading a robo")
    public ResponseEntity<Integer> getUpgradeCost(@PathVariable Long id) {
        try {
            int cost = roboService.getUpgradeCost(id);
            return ResponseEntity.ok(cost);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
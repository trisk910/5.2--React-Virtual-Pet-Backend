package cat.itacademy.s05.t02.Controllers;

import cat.itacademy.s05.t02.DTOs.CreateRoboDTO;
import cat.itacademy.s05.t02.DTOs.RoboDTO;
import cat.itacademy.s05.t02.Exceptions.RoboNotFoundException;
import cat.itacademy.s05.t02.Models.Robo;
import cat.itacademy.s05.t02.service.RoboService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<RoboDTO> buildRobo(@RequestBody CreateRoboDTO createRoboDTO) {
        Robo robo = new Robo(createRoboDTO.getName(), createRoboDTO.getType(), createRoboDTO.getUserId());
        return roboService.buildRobo(robo)
                .map(savedRobo -> new RoboDTO(savedRobo.getId(), savedRobo.getName(), savedRobo.getType(), savedRobo.getUserId()));
    }

    @DeleteMapping("/destroy/{id}")
    @Operation(summary = "Destroys a robo", description = "Destroys a robo by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Robo deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Robo not found")
    })
    public Mono<ResponseEntity<String>> destroyRobo(@PathVariable String id) {
        return roboService.getRoboById(id)
                .switchIfEmpty(Mono.error(new RoboNotFoundException(id)))
                .flatMap(existingRobo -> roboService.destroyRobo(id)
                        .then(Mono.just(ResponseEntity.ok("Robo deleted successfully"))))
                .onErrorResume(RoboNotFoundException.class, e -> {
                    logger.error("Robo not found: " + e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()));
                });
    }

    @GetMapping("/all")
    @Operation(summary = "Get all robos", description = "Retrieves all robos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Robos retrieved successfully")
    })
    public Flux<RoboDTO> getAllRobos() {
        return roboService.getAllRobos()
                .map(robo -> new RoboDTO(robo.getId(), robo.getName(), robo.getType(), robo.getUserId()));
    }

    @PutMapping("/update")
    @Operation(summary = "Update a robo's name", description = "Updates the name of an existing robo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Robo updated successfully"),
            @ApiResponse(responseCode = "404", description = "Robo not found")
    })
    public Mono<ResponseEntity<RoboDTO>> updateRobo(@RequestParam String id, @RequestParam String name) {
        return roboService.getRoboById(id)
                .switchIfEmpty(Mono.error(new RoboNotFoundException(id)))
                .flatMap(existingRobo -> {
                    existingRobo.setName(name);
                    return roboService.updateRobo(existingRobo)
                            .map(updatedRobo -> new RoboDTO(updatedRobo.getId(), updatedRobo.getName(), updatedRobo.getType(), updatedRobo.getUserId()))
                            .map(roboDTO -> ResponseEntity.ok(roboDTO));
                })
                .onErrorResume(RoboNotFoundException.class, e -> {
                    logger.error("Robo not found: " + e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
                });
    }
}
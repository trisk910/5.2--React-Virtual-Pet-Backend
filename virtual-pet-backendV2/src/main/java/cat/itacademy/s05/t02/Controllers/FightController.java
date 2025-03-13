package cat.itacademy.s05.t02.Controllers;

import cat.itacademy.s05.t02.DTOs.CreateFightDTO;
import cat.itacademy.s05.t02.DTOs.FightDTO;
import cat.itacademy.s05.t02.Exceptions.FightNotFoundException;
import cat.itacademy.s05.t02.Exceptions.RoboNotFoundException;
import cat.itacademy.s05.t02.Models.Fight;
import cat.itacademy.s05.t02.Models.Robo;
import cat.itacademy.s05.t02.repository.FightRepository;
import cat.itacademy.s05.t02.service.FightSimulator;
import cat.itacademy.s05.t02.service.RoboService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fights")
public class FightController {
    private static final Logger logger = LogManager.getLogger(FightController.class);

    @Autowired
    private FightRepository fightRepository;

    @Autowired
    private RoboService roboService;

    private final FightSimulator fightSimulator = new FightSimulator();

    @PostMapping("/fight")
    @Operation(summary = "Fight Robos", description = "Simulates a fight between two robos and saves the result")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fight simulated successfully"),
            @ApiResponse(responseCode = "404", description = "Robo not found")
    })
    public Mono<ResponseEntity<FightDTO>> simulateFight(@RequestBody CreateFightDTO createFightDTO) {
        return roboService.getRoboById(createFightDTO.getRobo1Id())
                .switchIfEmpty(Mono.error(new RoboNotFoundException(createFightDTO.getRobo1Id())))
                .zipWith(roboService.getRoboById(createFightDTO.getRobo2Id())
                        .switchIfEmpty(Mono.error(new RoboNotFoundException(createFightDTO.getRobo2Id()))))
                .flatMap(pets -> {
                    Robo robo1 = pets.getT1();
                    Robo robo2 = pets.getT2();
                    String winner = fightSimulator.determineWinner(robo1, robo2);
                    logger.info("Winner: " + winner);
                    Fight fight = new Fight(robo1.getName(), robo2.getName(), winner);
                    return fightRepository.save(fight)
                            .map(savedFight -> new FightDTO(savedFight.getId(), savedFight.getRobo1Name(), savedFight.getRobo2Name(), savedFight.getWinner()))
                            .map(fightDTO -> ResponseEntity.ok(fightDTO));
                })
                .onErrorResume(RoboNotFoundException.class, e -> {
                    logger.error("Robo not found: " + e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
                });
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a fight", description = "Deletes a fight by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fight deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Fight not found")
    })
    public Mono<ResponseEntity<Void>> deleteFight(@PathVariable String id) {
        return fightRepository.findById(id)
                .switchIfEmpty(Mono.error(new FightNotFoundException(id)))
                .flatMap(fight -> fightRepository.delete(fight)
                        .then(Mono.just(ResponseEntity.ok().<Void>build())))
                .onErrorResume(FightNotFoundException.class, e -> {
                    logger.error("Fight not found: " + e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
                });
    }
}
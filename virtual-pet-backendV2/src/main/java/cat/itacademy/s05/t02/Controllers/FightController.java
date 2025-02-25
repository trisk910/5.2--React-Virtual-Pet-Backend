package cat.itacademy.s05.t02.Controllers;

import cat.itacademy.s05.t02.Exceptions.FightNotFoundException;
import cat.itacademy.s05.t02.Exceptions.PetNotFoundException;
import cat.itacademy.s05.t02.Models.Fight;
import cat.itacademy.s05.t02.Models.Pet;
import cat.itacademy.s05.t02.repository.FightRepository;
import cat.itacademy.s05.t02.service.FightSimulator;
import cat.itacademy.s05.t02.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fights")
public class FightController {
    private static final Logger logger = LogManager.getLogger(FightController.class);

    @Autowired
    private FightRepository fightRepository;

    @Autowired
    private PetService petService;

    private final FightSimulator fightSimulator = new FightSimulator();

    @PostMapping("/fight")
    @Operation(summary = "Fight Pets", description = "Simulates a fight between two pets and saves the result")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fight simulated successfully"),
            @ApiResponse(responseCode = "404", description = "Pet not found")
    })
    public Mono<ResponseEntity<Fight>> simulateFight(@RequestParam String pet1Id, @RequestParam String pet2Id) {
        return petService.getPetById(pet1Id)
                .switchIfEmpty(Mono.error(new PetNotFoundException(pet1Id)))
                .zipWith(petService.getPetById(pet2Id)
                        .switchIfEmpty(Mono.error(new PetNotFoundException(pet2Id))))
                .flatMap(pets -> {
                    Pet pet1 = pets.getT1();
                    Pet pet2 = pets.getT2();
                    String winner = fightSimulator.determineWinner(pet1, pet2);
                    logger.info("Winner: " + winner);
                    Fight fight = new Fight(pet1.getName(), pet2.getName(), winner);
                    return fightRepository.save(fight)
                            .map(savedFight -> ResponseEntity.ok(savedFight));
                })
                .onErrorResume(PetNotFoundException.class, e -> {
                    logger.error("Pet not found: " + e.getMessage());
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
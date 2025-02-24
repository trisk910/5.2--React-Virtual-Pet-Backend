package cat.itacademy.s05.t02.Controllers;

import cat.itacademy.s05.t02.Models.Fight;
import cat.itacademy.s05.t02.Models.Pet;
import cat.itacademy.s05.t02.repository.FightRepository;
import cat.itacademy.s05.t02.service.FightSimulator;
import cat.itacademy.s05.t02.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fights")
public class FightController {

    @Autowired
    private FightRepository fightRepository;

    @Autowired
    private PetService petService;

    private final FightSimulator fightSimulator = new FightSimulator();

    @PostMapping("/fight")
    @Operation(summary = "Fight Pets", description = "Simulates a fight between two pets and saves the result")
    public Mono<Fight> simulateFight(@RequestParam String pet1Id, @RequestParam String pet2Id) {
        return petService.getPetById(pet1Id)
                .zipWith(petService.getPetById(pet2Id))
                .flatMap(pets -> {
                    Pet pet1 = pets.getT1();
                    Pet pet2 = pets.getT2();
                    String winner = fightSimulator.determineWinner(pet1, pet2);
                    Fight fight = new Fight(pet1.getName(), pet2.getName(), winner);
                    return fightRepository.save(fight);
                });
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a fight", description = "Deletes a fight by its ID")
    public Mono<Void> deleteFight(@PathVariable String id) {
        return fightRepository.deleteById(id);
    }
}
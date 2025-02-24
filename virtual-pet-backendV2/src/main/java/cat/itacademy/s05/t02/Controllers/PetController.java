package cat.itacademy.s05.t02.Controllers;

import cat.itacademy.s05.t02.Models.Pet;
import cat.itacademy.s05.t02.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping("/create")
    @Operation(summary = "Create a new pet", description = "Creates a new pet and associates it with a user")
    public Mono<Pet> createPet(
            @RequestBody @Schema(description = "Pet object containing necessary fields", required = true, example = "{ \"name\": \"Robo\", \"type\": \"ranged\", \"userId\": \"\" }") Pet pet) {
        return petService.createPet(pet);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a pet", description = "Deletes a pet by its ID")
    public Mono<String> deletePet(@PathVariable String id) {
        return petService.deletePet(id).thenReturn("Pet deleted successfully");
    }

    @GetMapping("/all")
    @Operation(summary = "Get all pets", description = "Retrieves all pets")
    public Flux<Pet> getAllPets() {
        return petService.getAllPets();
    }

    @PutMapping("/update")
    @Operation(summary = "Update a pet's name", description = "Updates the name of an existing pet")
    public Mono<Pet> updatePet(@RequestParam String id, @RequestParam String name) {
        return petService.getPetById(id)
                .flatMap(existingPet -> {
                    existingPet.setName(name);
                    return petService.updatePet(existingPet);
                });
    }
}
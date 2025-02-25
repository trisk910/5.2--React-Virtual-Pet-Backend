package cat.itacademy.s05.t02.Controllers;

import cat.itacademy.s05.t02.Exceptions.PetNotFoundException;
import cat.itacademy.s05.t02.Models.Pet;
import cat.itacademy.s05.t02.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/pets")
public class PetController {

    private static final Logger logger = LogManager.getLogger(PetController.class);

    @Autowired
    private PetService petService;

    @PostMapping("/create")
    @Operation(summary = "Create a new pet", description = "Creates a new pet and associates it with a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public Mono<Pet> createPet(
            @RequestBody @Schema(description = "Pet object containing necessary fields", required = true, example = "{ \"name\": \"Robo\", \"type\": \"ranged\", \"userId\": \"\" }") Pet pet) {
        return petService.createPet(pet);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a pet", description = "Deletes a pet by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Pet not found")
    })
    public Mono<ResponseEntity<String>> deletePet(@PathVariable String id) {
        return petService.getPetById(id)
                .switchIfEmpty(Mono.error(new PetNotFoundException(id)))
                .flatMap(existingPet -> petService.deletePet(id)
                        .then(Mono.just(ResponseEntity.ok("Pet deleted successfully"))))
                .onErrorResume(PetNotFoundException.class, e -> {
                    logger.error("Pet not found: " + e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()));
                });
    }

    @GetMapping("/all")
    @Operation(summary = "Get all pets", description = "Retrieves all pets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pets retrieved successfully")
    })
    public Flux<Pet> getAllPets() {
        return petService.getAllPets();
    }

    @PutMapping("/update")
    @Operation(summary = "Update a pet's name", description = "Updates the name of an existing pet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet updated successfully"),
            @ApiResponse(responseCode = "404", description = "Pet not found")
    })
    public Mono<ResponseEntity<Pet>> updatePet(@RequestParam String id, @RequestParam String name) {
        return petService.getPetById(id)
                .switchIfEmpty(Mono.error(new PetNotFoundException(id)))
                .flatMap(existingPet -> {
                    existingPet.setName(name);
                    return petService.updatePet(existingPet)
                            .map(updatedPet -> ResponseEntity.ok(updatedPet));
                })
                .onErrorResume(PetNotFoundException.class, e -> {
                    logger.error("Pet not found: " + e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
                });
    }
}
package cat.itacademy.s05.t02.S05T02.Controllers;

import cat.itacademy.s05.t02.S05T02.Entities.Pet;
import cat.itacademy.s05.t02.S05T02.Entities.Enums.PetType;
import cat.itacademy.s05.t02.S05T02.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping("/create")
    public Pet createPet(@RequestBody Pet pet) {
        Pet newPet = new Pet(pet.getName(), pet.getType());
        return newPet;
    }

    @DeleteMapping("/delete/{id}")
    public String deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return "Pet deleted successfully";
    }

    @GetMapping("/all")
    public Flux<Pet> getAllPets() {
        return petService.getAllPets();
    }

    @PutMapping("/update")
    public Mono<Pet> updatePet(@RequestBody Pet pet) {
        return petService.updatePet(pet);
    }
}

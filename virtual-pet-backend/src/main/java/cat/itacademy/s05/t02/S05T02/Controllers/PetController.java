package cat.itacademy.s05.t02.S05T02.Controllers;

import cat.itacademy.s05.t02.S05T02.Entities.Pet;
import cat.itacademy.s05.t02.S05T02.Entities.Enums.PetType;
import org.springframework.web.bind.annotation.*;

@RestController
public class PetController {

    @PostMapping("/create")
    public Pet createPet(@RequestBody Pet pet) {
        // Recreate the pet to initialize stats
        Pet newPet = new Pet(pet.getName(), pet.getType());
        return newPet;
    }
}

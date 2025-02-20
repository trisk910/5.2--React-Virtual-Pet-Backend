package cat.itacademy.s05.t02.S05T02.service;

import cat.itacademy.s05.t02.S05T02.Entities.Pet;
import cat.itacademy.s05.t02.S05T02.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Override
    public Mono<Pet> createPet(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public Mono<Void> deletePet(Long id) {
        return petRepository.deleteById(String.valueOf(id));
    }

    @Override
    public Flux<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    public Mono<Pet> updatePet(Pet pet) {
        return petRepository.findById(String.valueOf(pet.getId()))
                .flatMap(existingPet -> {
                    existingPet.setName(pet.getName());
                    existingPet.setType(pet.getType());
                    return petRepository.save(existingPet);
                });
    }
}
package cat.itacademy.s05.t02.S05T02.service;

import cat.itacademy.s05.t02.S05T02.Entities.Pet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PetService {
    Mono<Pet> createPet(Pet pet);
    Mono<Void> deletePet(Long id);
    Flux<Pet> getAllPets();
    Mono<Pet> updatePet(Pet pet);
}

package cat.itacademy.s05.t02.service;


import cat.itacademy.s05.t02.Models.Pet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PetService {
    Mono<Pet> createPet(Pet pet);
    Mono<Void> deletePet(String id);
    Flux<Pet> getAllPets();
    Mono<Pet> getPetById(String id);
    Mono<Pet> updatePet(Pet pet);
}

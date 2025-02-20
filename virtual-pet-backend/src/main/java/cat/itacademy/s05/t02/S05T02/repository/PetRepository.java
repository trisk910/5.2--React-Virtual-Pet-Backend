package cat.itacademy.s05.t02.S05T02.repository;

import cat.itacademy.s05.t02.S05T02.Entities.Pet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PetRepository extends ReactiveMongoRepository<Pet, String> {
}

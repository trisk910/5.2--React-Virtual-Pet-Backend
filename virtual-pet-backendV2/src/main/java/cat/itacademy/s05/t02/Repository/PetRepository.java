package cat.itacademy.s05.t02.Repository;


import cat.itacademy.s05.t02.Models.Robo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PetRepository extends ReactiveMongoRepository<Robo, String> {
}

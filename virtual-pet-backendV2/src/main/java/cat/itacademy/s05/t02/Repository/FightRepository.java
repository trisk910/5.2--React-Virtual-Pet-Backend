package cat.itacademy.s05.t02.Repository;

import cat.itacademy.s05.t02.Models.Fight;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface FightRepository extends ReactiveMongoRepository<Fight, String> {
}
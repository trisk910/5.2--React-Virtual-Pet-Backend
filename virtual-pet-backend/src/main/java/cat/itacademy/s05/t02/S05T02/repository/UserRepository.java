package cat.itacademy.s05.t02.S05T02.repository;

import cat.itacademy.s05.t02.S05T02.Entities.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, Long> {
    Mono<User> findByUsername(String username);
}
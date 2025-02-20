package cat.itacademy.s05.t02.S05T02.service;

import cat.itacademy.s05.t02.S05T02.Entities.User;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> registerUser(User user);
    Mono<String> loginUser(User user);
}

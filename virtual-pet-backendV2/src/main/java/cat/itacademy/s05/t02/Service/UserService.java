package cat.itacademy.s05.t02.Service;

import cat.itacademy.s05.t02.Models.User;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> registerUser(User user);
    Mono<Boolean> loginUser(String username, String password);
    Mono<User> findByUsername(String username);
}

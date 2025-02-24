package cat.itacademy.s05.t02.Controllers;

import cat.itacademy.s05.t02.Models.User;
import cat.itacademy.s05.t02.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class UserAuthController {
    private static final Logger logger = LogManager.getLogger(UserAuthController.class);
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Mono<String> registerUser(@RequestBody User user) {
        return userService.findByUsername(user.getUsername())
                .flatMap(existingUser -> {
                    logger.info("Username already exists: " + user.getUsername());
                    return Mono.just("Username already exists");
                })
                .switchIfEmpty(userService.registerUser(user)
                        .thenReturn("User registered successfully"));
    }

    @PostMapping("/login")
    public Mono<String> loginUser(@RequestBody User user) {
        return userService.loginUser(user.getUsername(), user.getPassword())
                .doOnSuccess(result -> {
                    if (result) {
                        logger.info("Login successful for user: " + user.getUsername());
                    } else {
                        logger.info("Login failed for user: " + user.getUsername());
                    }
                })
                .map(result -> result ? "Login successful" : "Login failed");
    }
}
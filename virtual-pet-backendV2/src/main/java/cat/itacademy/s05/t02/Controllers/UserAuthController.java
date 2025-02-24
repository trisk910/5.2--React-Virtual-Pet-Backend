package cat.itacademy.s05.t02.Controllers;

import cat.itacademy.s05.t02.Models.Enums.RoleType;
import cat.itacademy.s05.t02.Models.User;
import cat.itacademy.s05.t02.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class UserAuthController {
    private static final Logger logger = LogManager.getLogger(UserAuthController.class);
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Mono<String> registerUser(
            @RequestParam String name,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(defaultValue = "ROLE_USER") String roleType) {
        return userService.findByUsername(username)
                .flatMap(existingUser -> {
                    logger.info("Username already exists: " + username);
                    return Mono.just("Username already exists");
                })
                .switchIfEmpty(userService.registerUser(new User(name, username, email, password, RoleType.valueOf(roleType)))
                        .thenReturn("User registered successfully"));
    }

    @PostMapping("/login")
    public Mono<String> loginUser(@RequestParam String username, @RequestParam String password) {
        return userService.loginUser(username, password)
                .doOnSuccess(result -> {
                    if (result) {
                        logger.info("Login successful for user: " + username);
                    } else {
                        logger.info("Login failed for user: " + username);
                    }
                })
                .map(result -> result ? "Login successful" : "Login failed");
    }
}
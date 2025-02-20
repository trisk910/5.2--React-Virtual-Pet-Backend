package cat.itacademy.s05.t02.S05T02.Controllers;

import cat.itacademy.s05.t02.S05T02.Entities.User;
import cat.itacademy.s05.t02.S05T02.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
public class UserAuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        userService.registerUser(user);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public Mono<String> loginUser(@RequestBody User user) {
        return userService.loginUser(user);
    }
}
package cat.itacademy.s05.t02.controllers;

import cat.itacademy.s05.t02.models.User;
import cat.itacademy.s05.t02.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

    private final UserService userService;

    public CurrencyController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add/{value}")
    public ResponseEntity<String> addCurrency(Authentication authentication, @PathVariable int value) {
        User user = userService.findByUsername(authentication.getName());
        userService.addCurrency(user.getId(), value);
        return ResponseEntity.ok("Currency added successfully");
    }

    @PostMapping("/substract/{value}")
    public ResponseEntity<String> substractCurrency(Authentication authentication, @PathVariable int value) {
        User user = userService.findByUsername(authentication.getName());
        userService.subtractCurrency(user.getId(), value);
        return ResponseEntity.ok("Currency subtracted successfully");
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Integer> getCurrency(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user.getCurrency());
    }
}
package cat.itacademy.s05.t02.Controllers;


import cat.itacademy.s05.t02.Models.User;
import cat.itacademy.s05.t02.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

    @Autowired
    private UserService userService;

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
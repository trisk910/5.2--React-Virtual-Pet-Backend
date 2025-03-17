package cat.itacademy.s05.t02.Controllers;


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

    @PostMapping("/add")
    public ResponseEntity<String> addCurrency(Authentication authentication, @RequestParam int amount) {
        String username = authentication.getName();
        userService.addCurrency(username, amount);
        return ResponseEntity.ok("Currency added successfully");
    }

    @PostMapping("/subtract")
    public ResponseEntity<String> subtractCurrency(Authentication authentication, @RequestParam int amount) {
        String username = authentication.getName();
        userService.subtractCurrency(username, amount);
        return ResponseEntity.ok("Currency subtracted successfully");
    }
}
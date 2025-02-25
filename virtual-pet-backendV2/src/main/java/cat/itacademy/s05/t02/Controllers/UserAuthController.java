package cat.itacademy.s05.t02.Controllers;

import cat.itacademy.s05.t02.Models.Enums.RoleType;
import cat.itacademy.s05.t02.Models.User;
import cat.itacademy.s05.t02.Security.JwtUtil;
import cat.itacademy.s05.t02.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class UserAuthController {
    private static final Logger logger = LogManager.getLogger(UserAuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Username already exists")
    })
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Login failed"),
            @ApiResponse(responseCode = "403", description = "Access forbidden")
    })
    public Mono<String> loginUser(@RequestParam String username, @RequestParam String password) {
        return userService.findByUsername(username)
                .flatMap(user -> {
                    Authentication authentication = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(username, password));

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    String jwt = jwtUtil.generateToken(userDetails.getUsername());

                    return Mono.just(jwt);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Invalid username or password")));
    }


/*@RestController
@RequestMapping("/auth")
public class UserAuthController {
    private static final Logger logger = LogManager.getLogger(UserAuthController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Username already exists")
    })
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Login failed")
    })
    public Mono<String> loginUser(@RequestParam String username, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String jwt = jwtUtil.generateToken(userDetails.getUsername());
        return Mono.just(jwt);
    }*/
    /*public Mono<String> loginUser(@RequestParam String username, @RequestParam String password) {
        return userService.loginUser(username, password)
                .doOnSuccess(result -> {
                    if (result) {
                        logger.info("Login successful for user: " + username);
                    } else {
                        logger.info("Login failed for user: " + username);
                    }
                })
                .map(result -> result ? "Login successful" : "Login failed");
    }*/
}
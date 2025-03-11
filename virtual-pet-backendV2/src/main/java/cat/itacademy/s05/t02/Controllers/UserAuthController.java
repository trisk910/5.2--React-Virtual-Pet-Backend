package cat.itacademy.s05.t02.Controllers;

import cat.itacademy.s05.t02.DTOS.UserLoginDTO;
import cat.itacademy.s05.t02.DTOS.UserRegisterDTO;
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

import java.util.List;

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
    public Mono<UserRegisterDTO> registerUser(@RequestBody UserRegisterDTO userRegisterDTO) {
        return userService.findByUsername(userRegisterDTO.getUsername())
                .flatMap(existingUser -> {
                    logger.error("Username already exists: " + userRegisterDTO.getUsername());
                    return Mono.<UserRegisterDTO>error(new RuntimeException("Username already exists"));
                })
                .switchIfEmpty(userService.registerUser(new User(
                                userRegisterDTO.getName(),
                                userRegisterDTO.getUsername(),
                                userRegisterDTO.getEmail(),
                                userRegisterDTO.getPassword(),
                                RoleType.valueOf(userRegisterDTO.getRoleType())))
                        .flatMap(user -> {
                            String jwt = jwtUtil.generateToken(user.getUsername(), List.of("ROLE_" + user.getRoleType().name()));
                            userRegisterDTO.setToken(jwt);
                            return Mono.just(userRegisterDTO);
                        }));
    }

    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Login failed"),
            @ApiResponse(responseCode = "403", description = "Access forbidden")
    })
    public Mono<UserLoginDTO> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        return userService.findByUsername(userLoginDTO.getUsername())
                .flatMap(user -> {
                    try {
                        Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword()));

                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        UserDetails userDetails = userDetailsService.loadUserByUsername(userLoginDTO.getUsername());
                        String jwt = jwtUtil.generateToken(user.getUsername(), List.of("ROLE_" + user.getRoleType().name()));

                        userLoginDTO.setToken(jwt);
                        logger.info("Authentication successful for user: " + userLoginDTO.getUsername());
                        return Mono.just(userLoginDTO);
                    } catch (Exception e) {
                        logger.error("Login failed for user: " + userLoginDTO.getUsername(), e);
                        return Mono.error(new RuntimeException("Invalid username or password"));
                    }
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Invalid username or password")));
    }

}
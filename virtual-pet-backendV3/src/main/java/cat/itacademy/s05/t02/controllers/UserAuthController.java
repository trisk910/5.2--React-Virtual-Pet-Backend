package cat.itacademy.s05.t02.controllers;

import cat.itacademy.s05.t02.dtos.user.UserDTO;
import cat.itacademy.s05.t02.dtos.user.UserLeaderboardDTO;
import cat.itacademy.s05.t02.security.JwtTokenUtil;
import cat.itacademy.s05.t02.dtos.user.CreateUserDTO;
import cat.itacademy.s05.t02.dtos.user.UserRequestDTO;
import cat.itacademy.s05.t02.dtos.user.UserResponseDTO;
import cat.itacademy.s05.t02.exceptions.UserAlreadyExistsException;
import cat.itacademy.s05.t02.exceptions.UserNotFoundException;
import cat.itacademy.s05.t02.models.User;
import cat.itacademy.s05.t02.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class UserAuthController {
    private static final Logger logger = LogManager.getLogger(UserAuthController.class);

    private final UserService userService;

    private final JwtTokenUtil jwtTokenUtil;

    private final UserDetailsService userDetailsService;

    public UserAuthController(UserService userService, JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Username already exists")
    })
    public String registerUser(@RequestBody CreateUserDTO createUserDTO) {
        User existingUser = userService.findByUsername(createUserDTO.getUsername());
        if (existingUser != null) {
            logger.info("Username already exists: " + createUserDTO.getUsername());
            throw new UserAlreadyExistsException("Username already exists: " + createUserDTO.getUsername());
        } else {
            userService.registerUser(new User(createUserDTO.getName(), createUserDTO.getUsername(), createUserDTO.getEmail(),
                    createUserDTO.getPassword(), createUserDTO.getRoleType(),createUserDTO.getCurrency(),createUserDTO.getProfileImage()));
            return "User registered successfully";
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Login user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Login failed")
    })
    public ResponseEntity<UserResponseDTO> loginUser(@RequestBody UserRequestDTO authenticationRequest) throws Exception {
        userService.authenticateUser(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        User user = userService.findByUsername(authenticationRequest.getUsername());
        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getRoleType(),
                user.getProfileImage(), user.getCurrency(), user.getWins());
        return ResponseEntity.ok(new UserResponseDTO(token, userDTO));
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "403", description = "User not found")
    })
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getEmail(),
                user.getRoleType(), user.getProfileImage(), user.getCurrency(), user.getWins());
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/ranking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ranking retrieved successfully")
    })
    public ResponseEntity<List<UserLeaderboardDTO>> getUserRanking() {
        List<User> users = userService.getUsersRankedByWins();
        List<UserLeaderboardDTO> UserLeaderboardDTO = users.stream()
                .map(user -> new UserLeaderboardDTO(user.getId(), user.getUsername(), user.getWins()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(UserLeaderboardDTO);
    }
}
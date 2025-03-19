package cat.itacademy.s05.t02.Controllers;

import cat.itacademy.s05.t02.DTOs.User.UserDTO;
import cat.itacademy.s05.t02.Security.JwtTokenUtil;
import cat.itacademy.s05.t02.DTOs.User.CreateUserDTO;
import cat.itacademy.s05.t02.DTOs.User.UserRequestDTO;
import cat.itacademy.s05.t02.DTOs.User.UserResponseDTO;
import cat.itacademy.s05.t02.Exceptions.UserAlreadyExistsException;
import cat.itacademy.s05.t02.Exceptions.UserNotFoundException;
import cat.itacademy.s05.t02.Models.User;
import cat.itacademy.s05.t02.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class UserAuthController {
    private static final Logger logger = LogManager.getLogger(UserAuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/register")
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Login failed")
    })
    public ResponseEntity<?> loginUser(@RequestBody UserRequestDTO authenticationRequest) throws Exception {
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
            @ApiResponse(responseCode = "404", description = "User not found")
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
    public ResponseEntity<List<UserDTO>> getUserRanking() {
        List<User> users = userService.getUsersRankedByWins();
        List<UserDTO> userDTOs = users.stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getWins()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

}
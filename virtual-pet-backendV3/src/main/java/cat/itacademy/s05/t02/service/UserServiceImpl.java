package cat.itacademy.s05.t02.service;

import cat.itacademy.s05.t02.exceptions.AuthenticationException;
import cat.itacademy.s05.t02.exceptions.UserNotFoundException;
import cat.itacademy.s05.t02.models.User;
import cat.itacademy.s05.t02.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public boolean loginUser(String username, String password) {
        User existingUser = userRepository.findByUsername(username);
        return existingUser != null && passwordEncoder.matches(password, existingUser.getPassword());
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void authenticateUser(String username, String password) throws AuthenticationException {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("INVALID_CREDENTIALS", e);
        }
    }
    @Override
    public void addCurrency(Long userId, int amount) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setCurrency(user.getCurrency() + amount);
        userRepository.save(user);
    }

    @Override
    public void subtractCurrency(Long userId, int amount) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setCurrency(user.getCurrency() - amount);
        userRepository.save(user);
    }
    @Override
    public void incrementWins(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setWins(user.getWins() + 1);
        userRepository.save(user);
    }

    @Override
    public List<User> getUsersRankedByWins() {
        return userRepository.findAllByOrderByWinsDesc();
    }
}
package cat.itacademy.s05.t02.Service;

import cat.itacademy.s05.t02.Exceptions.AuthenticationException;
import cat.itacademy.s05.t02.Models.User;
import cat.itacademy.s05.t02.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public void addCurrency(String username, int amount) {
        User user = userRepository.findByUsername(username);
        user.setCurrency(user.getCurrency() + amount);
        userRepository.save(user);
    }

    @Override
    public void subtractCurrency(String username, int amount) {
        User user = userRepository.findByUsername(username);
        user.setCurrency(user.getCurrency() - amount);
        userRepository.save(user);
    }
}
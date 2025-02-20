package cat.itacademy.s05.t02.S05T02.service;

import cat.itacademy.s05.t02.S05T02.Entities.User;
import cat.itacademy.s05.t02.S05T02.Security.JwtTokenProvider;
import cat.itacademy.s05.t02.S05T02.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public Mono<User> registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Mono<String> loginUser(User user) {
        return userRepository.findByUsername(user.getUsername())
                .filter(existingUser -> passwordEncoder.matches(user.getPassword(), existingUser.getPassword()))
                .flatMap(existingUser -> {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(existingUser.getUsername(), null, new ArrayList<>());
                    return Mono.just(jwtTokenProvider.generateJwtToken(authentication));
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Invalid username or password")));
    }
}
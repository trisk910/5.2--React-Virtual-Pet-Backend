package cat.itacademy.s05.t02.Service;


import cat.itacademy.s05.t02.Models.User;
import cat.itacademy.s05.t02.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<User> registerUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Mono<Boolean> loginUser(String username, String password) {
        return userRepository.findByUsername(username)
                .map(existingUser -> existingUser.getPassword().equals(password));
    }

    @Override
    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
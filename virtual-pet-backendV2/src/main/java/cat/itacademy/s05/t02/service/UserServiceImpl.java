package cat.itacademy.s05.t02.service;
/*

import cat.itacademy.s05.t02.Models.User;
import cat.itacademy.s05.t02.repository.UserRepository;
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
    }*/

import cat.itacademy.s05.t02.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;
import cat.itacademy.s05.t02.Models.User;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> org.springframework.security.core.userdetails.User
                        .withUsername(user.getUsername())
                        .password(user.getPassword())
                        .authorities("ROLE_USER")
                        .build())
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("Usuari no trobat")))
                .block();
    }
}

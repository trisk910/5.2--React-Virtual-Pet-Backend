package cat.itacademy.s05.t02.S05T02.service;

import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    String generateToken(Authentication authentication);
    boolean validateToken(String token);
    String getUsernameFromToken(String token);
}
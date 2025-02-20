package cat.itacademy.s05.t02.S05T02.service;

import cat.itacademy.s05.t02.S05T02.Security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String generateToken(Authentication authentication) {
        return jwtTokenProvider.generateJwtToken(authentication);
    }

    @Override
    public boolean validateToken(String token) {
        return jwtTokenProvider.validateJwtToken(token);
    }

    @Override
    public String getUsernameFromToken(String token) {
        return jwtTokenProvider.getUserNameFromJwtToken(token);
    }
}
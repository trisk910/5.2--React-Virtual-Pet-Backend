package cat.itacademy.s05.t02.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String username = null;
        String jwt = null;

        String authorizationHeader = request.getHeader("Authorization");

        // Verificamos si la cabecera Authorization es válida
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            logger.error("No se encontró un token válido en la cabecera Authorization.");
            chain.doFilter(request, response); // Continuar sin autenticar
            return;
        }

        // Extraemos el token JWT eliminando "Bearer "
        jwt = authorizationHeader.substring(7);

        try {
            // Validamos el token antes de extraer información
            if (jwt.isEmpty()) {
                throw new MalformedJwtException("El token JWT está vacío.");
            }

            // Extraemos el usuario del token
            username = jwtUtil.extractUsername(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // Validamos el token con el usuario
                if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    logger.info("Usuario autenticado correctamente: " + username);
                } else {
                    logger.error("Token inválido para el usuario: " + username);
                }
            }
        } catch (MalformedJwtException e) {
            logger.error("Error al procesar el token JWT: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado en la autenticación JWT: ", e);
        }

        chain.doFilter(request, response);
    }




}

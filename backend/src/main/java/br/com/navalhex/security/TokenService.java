package br.com.navalhex.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import br.com.navalhex.modules.user.entity.UserEntity;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;
    private Instant genExpirationDate() {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
}
    public String recoverToken(HttpServletRequest request) {
        String tokenHeader = request.getHeader("Authorization");
        if(tokenHeader == null) return null;
        return tokenHeader.replace("Bearer ", "");
    }


    public String generateToken(UserEntity user) {
        try {
            return JWT.create()
                    .withSubject(user.getEmail())
                    .withClaim("role", user.getRole().name())
                    .withExpiresAt(genExpirationDate())
                    .sign(Algorithm.HMAC256(secret));
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar token", e);
        }
    }

    public String validateToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(secret))
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            return "";
        }
    }


}

package com.restfulapi.securitas.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JwtUtil {
    // с помощью этого ключа генерируются токены
    private final String SECRET_KEY = "dtq70sSAPgEOhciuHUI1YZgOrM2IB8636bEYGZwcKZ5aBsHtxhVVnT5RV70cpqfz";

    // эта функция расшифровывает имя пользователя из токена
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // а эта расшифровывает дату истечения срока годности токена
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // функции выше вызывали эту функцию, но с разными аргументами. В общем - она что-либо достает из токена.
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // а эта функция достает все шо можно
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // проверяет токен на просрочку
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // функция принимает данные пользователя: логин и пароль, затем вызывает функцию createToken, и возвращает результат
    // ее работы
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    // создает токен, который валиден 5 минут
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 5 * 60))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes(StandardCharsets.UTF_8)).compact();
    }

    public String createTokenForNfc(String subject) {
        return Jwts.builder().setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes(StandardCharsets.UTF_8)).compact();
    }

    // проверяет токен на совпадение переданного логина и логина из токена, а так же время действия токена
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

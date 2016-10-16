package juja.microservices.gamification.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import juja.microservices.gamification.user.User;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Util class for generating and parsing of token.
 *
 * @author olga kulykova email o.kulykova@gmail.com
 */
@Component
public final class JwtUtil {

    private final String secret = "mysecret";

    /**
     * Tries to parse specified String as a JWT token. If successful, returns User object with username, id
     * and role prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
     *
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or null if a token is invalid.
     */
    public User parseToken(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            return new User((String) body.get("userId"), body.getSubject(), (Set<String>) body.get("role"));
        } catch (JwtException | ClassCastException e) {
            return null;
        }
    }

    /**
     * Generates a JWT token containing username as subject, and userId and role as additional claims.
     * These properties are taken from the specified User object. Tokens validity is infinite.
     *
     * @param u the user for which the token will be generated
     * @return the JWT token
     */
    public String generateToken(User u) {
        Claims claims = Jwts.claims().setSubject(u.getUsername());
        claims.put("userId", u.getId() + "");
        claims.put("role", u.getAuthorities().toArray());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}

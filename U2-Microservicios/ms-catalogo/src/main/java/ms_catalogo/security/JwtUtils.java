package ms_catalogo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;

@Component
public class JwtUtils {

    private final String jwtSecret = "u6rckmOPVwT9OuoWZf9lZtabfM6TTRGTYiS6OQ4Nkb8"; // debe coincidir con auth-service
    private final Key key = Keys.hmacShaKeyFor(Base64.getUrlDecoder().decode(jwtSecret));

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean hasRole(Claims claims, String role) {
        String roles = claims.get("roles", String.class);
        return roles != null && roles.contains(role);
    }
}

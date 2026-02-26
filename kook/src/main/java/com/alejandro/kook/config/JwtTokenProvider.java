package com.alejandro.kook.config;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alejandro.kook.Model.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

/**
 * Se encarga de generar tokens JWT cuando un usuario inicia sesión satisfactoriamente.
 */
@Component
public class JwtTokenProvider {
    
    Logger log = LoggerFactory.getLogger(this.getClass());

    // inyectamos los valores del aplication properties
    @Value("${app.security.jwt.secret}")
    private String jwtSecret;

    @Value("${app.security.jwt.expiration}")
    private long jwtDurationSeconds;

    public String generateToken(Authentication authentication) {
        // TODO Auto-generated method stub
        // Aquí es donde se genera el token JWT usando la librería que hayamos elegido, como jjwt o java-jwt.
        // El token debe incluir el nombre de usuario y la fecha de expiración, y debe ser firmado con la clave secreta.
        Usuario user = (Usuario) authentication.getPrincipal();
        return generateToken(user);
    }

    public String generateToken(Usuario user) {
        log.info("Generando token JWT para el usuario: " + user.getUsername());

        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS256)
                .setHeaderParam("typ", "JWT")
                .setSubject(Long.toString(user.getId()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (jwtDurationSeconds * 1000)))
                .claim("username", user.getUsername())
                .claim("email", user.getEmail())
                .compact();
    }

    public boolean isValidToken(String token) {
        // TODO Auto-generated method stub
        // Aquí es donde se valida el token JWT en cada petición, verificando su firma y su fecha de expiración. 
        // Si el token es válido, se devuelve true; si no, se devuelve false.
        if (!StringUtils.hasLength(token)) {
            return false;
        }

        try {
            JwtParser validator = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build();
            validator.parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.info("Error en la firma del token", e);
        } catch (MalformedJwtException | UnsupportedJwtException e) {
            log.info("Token incorrecto", e);
        } catch (ExpiredJwtException e) {
            log.info("Token expirado", e);
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        // TODO Auto-generated method stub
        JwtParser parser = Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
            .build();

        Claims claims = parser.parseClaimsJws(token).getBody();
        return claims.get("username").toString();
    }
}

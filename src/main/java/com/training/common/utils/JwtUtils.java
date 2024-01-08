package com.training.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${icario.training.jwtSecret}")
    private String jwtSecret;

    @Value("${icario.training.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        SecretKey secret = getSecretKey();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(secret)
                .compact();
    }

    public Claims parseClaimsJws(String token) {
        SecretKey secret = getSecretKey();
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build().parseClaimsJws(token)
                .getBody();
    }

    public String getUserNameFromJwtToken(String token) {
        Claims claims = parseClaimsJws(token);
        return claims.getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        SecretKey secret = getSecretKey();
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    private SecretKey getSecretKey() {
        byte[] encode = Base64.getEncoder().encode(this.jwtSecret.getBytes(StandardCharsets.UTF_8));
        return Keys.hmacShaKeyFor(encode);
    }

}

package com.example.demo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    //Secret Key for signing tokens
   private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256) ;

   //Token valid for 24 hours
   private final long EXPIRATION_TIME =86400000;

   //Generate token for a user
   public String generateToken(String username) {
    return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(secretKey)
            .compact();
   }

   //extract username from token
   public String extractUsername(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
   }

   //validate token
   public boolean validateToken(String token,String username) {
     String extractedUsername =extractUsername(token);
     return extractedUsername.equals(username) && !isTokenExpired(token);
   }

   // Check if token is expired
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}

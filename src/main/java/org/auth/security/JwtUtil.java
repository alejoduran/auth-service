package org.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Getter
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

  private Key key;

  @PostConstruct
  public void init() {
    if (secret != null && secret.length() >= 32) {

      byte[] keyBytes = Base64.getDecoder().decode(secret);
      this.key = Keys.hmacShaKeyFor(keyBytes);
    } else {

      this.key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
    }
  }

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

  public String extractEmail(String token) {
    return extractAllClaims(token).getSubject();
  }

  public boolean validateToken(String token) {
    try {
      extractAllClaims(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public boolean isTokenExpired(String token) {
    return extractAllClaims(token).getExpiration().before(new Date());
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

}

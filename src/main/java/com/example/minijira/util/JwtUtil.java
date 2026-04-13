package com.example.minijira.util;

import java.util.Date;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    
   private final SecretKey key ;
     
    private  long EXPIRE_TIME;

    public JwtUtil(@Value("${jwt.expiration}") long expireTime,
                   @Value("${jwt.secret}") String secret) {
        this.EXPIRE_TIME = expireTime;
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

     
     
    


    public String generateToken(String username){
      

        return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis()+EXPIRE_TIME))
                    .signWith(key)
                    .compact();
    }

    public Claims getTokenDetails(String token){
        return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
    }

    public String extractUsername(String token){
        return getTokenDetails(token)
                .getSubject();
    }

    public boolean validateToken(String token,UserDetails userDetails) {
        return userDetails.getUsername().equals(extractUsername(token))
                && 
                !getTokenDetails(token).getExpiration().before(new Date());

    }

}

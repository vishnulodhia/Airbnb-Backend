package com.spring_boot.Airbnb.Security;




import com.spring_boot.Airbnb.Model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;


@Service
public class JWTService {

        @Value("${jwt.secretKey}")
        private String jwtSecret;

        private SecretKey getSecretKey(){
            return Keys.hmacShaKeyFor(jwtSecret.getBytes());
        }

        public String generateAccessToken(User user){
            return  Jwts.builder()
                    .setSubject(user.getId().toString())
                    .claim("email",user.getEmail())
                    .claim("roles", user.getRoles())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 1000*60*10))
                    .signWith(getSecretKey())
                    .compact();
        }

        public String generateRefersToken(User user){
            return  Jwts.builder()
                    .setSubject(user.getId().toString())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 1000L *60*60*24*30*6))
                    .signWith(getSecretKey())
                    .compact();
        }

        public Long getUserIdFromToken(String token){
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token) // Use parseClaimsJws for signed tokens
                    .getBody();
            return Long.valueOf(claims.getSubject());
        }

    }



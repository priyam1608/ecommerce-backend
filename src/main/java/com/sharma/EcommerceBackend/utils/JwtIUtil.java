package com.sharma.EcommerceBackend.utils;

import com.sharma.EcommerceBackend.constants.JwtConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtIUtil {

    SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
    public String generateToken(Authentication auth){
        String jwt = Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .claim("email",auth.getName())
                .signWith(key).compact();

        return jwt;
    }

    public String getEmailFromToken(String jwt){
        jwt = jwt.substring(7);
        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();
        String email = String.valueOf(claims.get("email"));

        return email;
    }
}

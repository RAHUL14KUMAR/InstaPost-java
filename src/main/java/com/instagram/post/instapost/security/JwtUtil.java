package com.instagram.post.instapost.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.instagram.post.instapost.Entity.UserEntity;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String jwtSecreteKey;
    
    //generate a secreate key method
    private SecretKey generateSecreteKey(){
        return Keys.hmacShaKeyFor(jwtSecreteKey.getBytes(StandardCharsets.UTF_8));
    }

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    public String createToken(UserEntity user){
        return Jwts.builder()
        .setSubject(user.getId().toString())
        .claim("email",user.getEmail())
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(generateSecreteKey())  
        .compact();
    }

    // retrive the userid from jwttoken in form of interger or long
    public long getUseridFromToken(String token){
        String num=Jwts.parserBuilder().setSigningKey(generateSecreteKey()).build().parseClaimsJws(token)
        .getBody()
        .getSubject();

        System.out.println("user id from token extract"+num);

        return Long.parseLong(num);
    }

    // validate the jwt token
    public boolean validateJwtToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(generateSecreteKey()).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException e) {
            System.out.println("Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: " + e.getMessage());
        }
        return false;
    }
}

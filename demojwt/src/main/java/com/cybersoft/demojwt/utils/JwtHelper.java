package com.cybersoft.demojwt.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtHelper {

    @Value("${jwt.key}")
    private String keyJWT;

    public boolean deceryptToken(String token){
        boolean result = false;
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(keyJWT));
        try {
            Jwts.parser().verifyWith(key).build().parseClaimsJws(token);
            result = true;
        }catch (Exception e){
            System.out.println("deceryptToken " + e.getLocalizedMessage());
        }

        return result;
    }

    public String getDataToken(String token){
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(keyJWT));
        String role = "";
        try {
           role =  Jwts.parser().verifyWith(key).build().parseClaimsJws(token).getPayload().getSubject();

        }catch (Exception e){
            System.out.println("DataToken " + e.getLocalizedMessage());
        }

        return role;
    }
}

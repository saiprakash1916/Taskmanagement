package com.Demo.taskManagement.Security;

import com.Demo.taskManagement.exception.APIException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    public String generateToken(Authentication authentication){
        String email = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + 3600000);
        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, "JWTSecurity")
                .compact();
        return token;
    }
    public String getEmailFromToken(String token){
        Claims claims = Jwts.parser().setSigningKey("JWTSecurity").parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey("JWTSecurity").parseClaimsJws(token);
            return true;
        }catch (Exception e){
            throw new APIException("Token Issue: " + e.getMessage());
        }
    }
}

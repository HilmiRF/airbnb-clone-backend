package com.hilmirafiff.airbnb_clone_be.security;

import com.hilmirafiff.airbnb_clone_be.entity.User;
import com.hilmirafiff.airbnb_clone_be.exception.ApplicationException;
import com.hilmirafiff.airbnb_clone_be.util.AppErrorEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret:null}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds:null}")
    private long jwtExpirationTime;

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    public String getUserEmail(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public String generateToken(User user) {
        String email = user.getEmail();
        Date currentDate = new Date();
        Date expiredDate = new Date(currentDate.getTime() + this.jwtExpirationTime);
        return Jwts.builder()
                .subject(email)
                .issuedAt(currentDate)
                .expiration(expiredDate)
                .signWith(key())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key())
                    .build()
                    .parse(token);
            return true;
        } catch (ExpiredJwtException ex) {
            throw new ApplicationException(AppErrorEnum.EXPIRED_JWT);
        } catch (MalformedJwtException ex) {
            throw new ApplicationException(AppErrorEnum.INVALID_JWT);
        } catch (SecurityException e) {
            throw new ApplicationException(AppErrorEnum.SECURITY_JWT);
        } catch (IllegalArgumentException ex) {
            throw new ApplicationException(AppErrorEnum.EMPTY_JWT_CLAIMS);
        }
    }
}


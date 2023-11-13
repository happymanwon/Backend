package org.hmanwon.config.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.hmanwon.config.jwt.SigningKeyResolver;

public class JwtTokenUtils {
    public static String getUsername(String token) {
        return Jwts.parserBuilder()
            .setSigningKeyResolver(SigningKeyResolver.instance)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject(); // username
    }

    public static boolean isExpired(String token, String key) {
        Date expiredDate = extractClaims(token, key).getExpiration();
        return expiredDate.before(new Date());
    }

    private static Claims extractClaims(String token, String key) {
        return Jwts.parserBuilder().setSigningKey(getKey(key))
            .build().parseClaimsJws(token).getBody();
    }


    public static String generateToken(String userName, String key, long expiredTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("userName", userName);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
            .signWith(getKey(key), SignatureAlgorithm.HS256)
            .compact();

    }

    private static Key getKey(String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

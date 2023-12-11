package org.hmanwon.domain.auth.application;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtProvider {

    @Value("${jwt.secretKey}")
    private String secretKey;
    @Value("${jwt.accessTokenValidTime}")
    private long accessTokenValidTime;
    @Value("${jwt.refreshTokenValidTime}")
    private long refreshTokenValidTime;

    public String createAccessToken(String email, Long memberId) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenValidTime);

        return Jwts.builder()
            .setSubject(email)
            .claim("email", email)
            .claim("memberId", memberId)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public String createRefreshToken() {
        Date now = new Date();

        return Jwts.builder()
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody();
        return claims.getSubject();
    }

    public Long getMemberIdFromToken(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token).getBody();
        return (Long) claims.get("memberId");
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token).getBody();
            return true;
        } catch (ExpiredJwtException e) {
            throw new JwtException("JWT 토큰이 만료되었습니다.");
        } catch (UnsupportedJwtException e) {
            throw new JwtException("지원되지 않는 JWT 토큰입니다.");
        } catch (MalformedJwtException e) {
            throw new JwtException("올바르게 구성되지 않은 JWT 토큰입니다.");
        } catch (SignatureException e) {
            throw new JwtException("유효하지 않은 JWT 서명입니다.");
        } catch (IllegalArgumentException e) {
            throw new JwtException("JWT 클레임이 비어있습니다.");
        }
    }
}

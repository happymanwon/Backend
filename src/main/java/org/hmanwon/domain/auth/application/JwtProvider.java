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

    /***
     * 액세스 토큰 발급
     * @param email 회원 이메일
     * @param memberId 회원 ID
     * @return 액세스 토큰
     */
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

    /***
     * 리프레시 토큰 발급
     * @return 리프레시 토큰
     */
    public String createRefreshToken() {
        Date now = new Date();

        return Jwts.builder()
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    /***
     * 액세스 토큰에서 이메일 조회
     * @param token 액세스 토큰
     * @return 이메일
     */
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody();
        return claims.getSubject();
    }

    /***
     * 액세스 토큰에서 회원 ID 조회
     * @param token 액세스 토큰
     * @return 회원 ID
     */
    public Long getMemberIdFromToken(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token).getBody();
        return Long.parseLong(String.valueOf(claims.get("memberId")));
    }

    /***
     * 토큰 유효성 검증
     * @param token 토큰
     * @return 유효 여부
     */
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

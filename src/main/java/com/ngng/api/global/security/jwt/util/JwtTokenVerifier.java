package com.ngng.api.global.security.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenVerifier {

    private final Key key;
//    private final TokenRepository tokenRepository;

    public JwtTokenVerifier(@Value("${jwt.secret.key}")
                            String secretKey) {
//                            TokenRepository tokenRepository) {

        this.key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
//        this.tokenRepository = tokenRepository;
    }

    public String getEmail(String token) {

        return parseToken(token).getSubject();
    }

    public String getRole(String token) {

        return parseToken(token).get("role", String.class);
    }
    public String getType(String token) {

        return parseToken(token).get("type", String.class);
    }

    public boolean validateToken(String token) {

        try {
            // 우리가 서명한 key값과 일치한지 확인
            parseToken(token);
        } catch (Exception e) {

            return false;
        }

        return true;
    }

    public boolean isExpired(String token) {

        if (token.startsWith("Bearer ")) {

            token = token.substring(7);
        }

        try {

            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {

            return true;
        }
    }

    public boolean checkRefreshToken(String token) {
        // db에 해당 refreshToekn이 저장되어 있는지 확인
//        boolean isExists = !tokenRepository.existsByTokenName(token);

//        return isExists && validateToken(token);
        return validateToken(token);
    }

    // 각 claim getter에서 공통적인 부분 메서드화
    public Claims parseToken(String token) {

        if (token.startsWith("Bearer ")) {

            token = token.substring(7);
        }

        Jws<Claims> jwtClaims = jwtClaims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

        return jwtClaims.getBody();
    }
}

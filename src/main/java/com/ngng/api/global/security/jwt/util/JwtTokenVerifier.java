package com.ngng.api.global.security.jwt.util;

import com.ngng.api.global.security.jwt.custom.CustomUserDetails;
import com.ngng.api.global.security.jwt.entity.Token;
import com.ngng.api.global.security.jwt.repository.TokenRepository;
import io.jsonwebtoken.Claims;
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
    private final TokenRepository tokenRepository;

    public JwtTokenVerifier(@Value("${jwt.secret.key}")
                            String secretKey,
                            TokenRepository tokenRepository) {

        this.key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
        this.tokenRepository = tokenRepository;
    }

    public String getEmail(String token) {

        return tokenParser(token).getSubject();
    }

    public String getRole(String token) {

        return tokenParser(token).get("role", String.class);
    }
    public String getType(String token) {

        return tokenParser(token).get("type", String.class);
    }

    public boolean isExpired(String token) {

        return tokenParser(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token) {

        try {
            // 우리가 서명한 key값과 일치한지 확인
            tokenParser(token);
        } catch (Exception e) {

            return false;
        }

        return true;
    }

    public boolean checkRefreshToken(String token) {
        // db에 해당 refreshToekn이 저장되어 있는지 확인
        boolean isExists = !tokenRepository.existsByTokenName(token);

        return isExists && validateToken(token);
    }


    // 각 claim getter에서 공통적인 부분 메서드화
    public Claims tokenParser(String token) {

        token = token.replaceAll("Bearer ", "");

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}

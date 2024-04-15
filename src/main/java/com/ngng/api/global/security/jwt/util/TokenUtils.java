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
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtils {

    private final Key key;
    private final TokenRepository tokenRepository;

    @Value("${jwt.expiration.access}")
    private int accessTokenExpirationMs;
    @Value("${jwt.expiration.refresh}")
    private int refreshTokenExpirationMs;

    public TokenUtils(@Value("${jwt.secret.key}")
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

        return !tokenParser(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token) {

        try {

            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            return isExpired(token);
        } catch (Exception e) {

            return false;
        }
    }

    public boolean checkAccessToken(String token) {

        return validateToken(token);
    }

    public boolean checkRefreshToken(String token) {

        boolean isExists = !tokenRepository.existsByTokenName(token);

        return isExists && validateToken(token);
    }

    public Map<String, String> createToken(CustomUserDetails userDetails) {

        Date now = new Date();

        // accessToken과 refreshToken을 함께 발급 (accessToekn이 만료 되면 refreshToken도 발급받도록)
        String accessToken = "Bearer " + Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenExpirationMs))
                .setSubject(userDetails.getUsername())
                .claim("role", userDetails.getRole())
                .claim("type", "access")
                .signWith(key)
                .compact();

        String refreshToken = "Bearer " + Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenExpirationMs))
                .setSubject(userDetails.getUsername())
                .claim("role", userDetails.getRole())
                .claim("type", "refresh")
                .signWith(key)
                .compact();

        Token token = Token.builder()
                .tokenName(refreshToken)
                .user(userDetails.getUser())
                .build();

        tokenRepository.save(token);

        // map에 담아 header에 추가
        Map<String, String> tokens = new HashMap<>();

        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }

    public Claims tokenParser(String token) {

        token = token.replaceAll("Bearer ", "");

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}

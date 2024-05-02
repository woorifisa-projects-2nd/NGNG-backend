package com.ngng.api.global.security.jwt.util;

import com.ngng.api.global.security.custom.CustomUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

//    private final TokenRepository tokenRepository;
    private final Key key;

    public JwtTokenProvider(@Value("${jwt.secret.key}")
                            String secretKey) {
//                            TokenRepository tokenRepository) {

        this.key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
//        this.tokenRepository = tokenRepository;
    }

    @Value("${jwt.expiration.access}")
    private int accessTokenExpirationMs;
    @Value("${jwt.expiration.refresh}")
    private int refreshTokenExpirationMs;

    public String createAccessToken(CustomUserDetails userDetails) {

        String PREFIX_TOKEN = "Bearer ";
        Date now = new Date();

        return PREFIX_TOKEN + Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenExpirationMs))
                .setSubject(userDetails.getUsername())
                .claim("role", userDetails.getRole())
                .claim("type", "access")
                .signWith(key)
                .compact();
    }

    public String createRefreshToken(CustomUserDetails userDetails) {

        Date now = new Date();

        // refreshToken은 cookie로 저장되고 사용되기 때문에 Bearer 접두사를 붙힐 필요가 없음
        String refreshToken = Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenExpirationMs))
                .setSubject(userDetails.getUsername())
                .claim("role", userDetails.getRole())
                .claim("type", "refresh")
                .signWith(key)
                .compact();

//        Token token = Token.builder()
//                .tokenName(refreshToken)
//                .id(userDetails.getUser().getUserId())
//                .build();
//
//        // refreshToken은 로그인 할 때 발급되기 때문에 repository에 저장하고 이를 확인하여 현재 로그인중임을 확인
//        tokenRepository.save(token);

        return refreshToken;
    }

//    public void deleteRefreshToken(String refreshToken) {
//
//        Token token = tokenRepository.findTokenByTokenName(refreshToken).orElseThrow(() -> new JwtException("invalid token"));
//
//        tokenRepository.delete(token);
//    }
}

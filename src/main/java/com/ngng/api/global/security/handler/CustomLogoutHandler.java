package com.ngng.api.global.security.handler;

import com.ngng.api.global.security.jwt.entity.Token;
import com.ngng.api.global.security.jwt.repository.TokenRepository;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        // request에서 Authorization header(accessToken)을 받아와 해당 토큰이 db에 저장되어 있는지 검증
        String refreshToken = request.getHeader("Authorization");
        Token token = tokenRepository.findTokenByTokenName(refreshToken).orElseThrow(() -> new JwtException("Invalid Token"));

        tokenRepository.delete(token);
    }
}

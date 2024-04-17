package com.ngng.api.global.security.handler;

import com.ngng.api.global.security.jwt.entity.Token;
import com.ngng.api.global.security.jwt.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // TODO : Cookie로 수정
        String refreshToken = request.getHeader("Authorization");
        Token token = tokenRepository.findTokenByTokenName(refreshToken);

        if (token == null) {

            return;
        }

        tokenRepository.delete(token);
    }
}

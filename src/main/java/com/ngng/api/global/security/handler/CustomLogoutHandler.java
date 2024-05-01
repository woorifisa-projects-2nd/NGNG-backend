package com.ngng.api.global.security.handler;

import com.ngng.api.global.security.custom.CustomHttpServletResponseWrapper;
import com.ngng.api.global.security.jwt.util.JwtTokenProvider;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class CustomLogoutHandler implements LogoutHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("refreshToken"))
                .findFirst()
                .orElseThrow(() -> new JwtException("no token"))
                .getValue();

        jwtTokenProvider.deleteRefreshToken(refreshToken);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
                .path("/")
                .domain("localhost")
                .httpOnly(true)
                .secure(true)
                .maxAge(0)
                .build();

        HttpServletResponseWrapper wrapper = new CustomHttpServletResponseWrapper(response);

        wrapper.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}

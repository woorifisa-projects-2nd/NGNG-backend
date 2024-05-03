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

    // /logout 경로로 요청이 들어올 경우 해당 handler가 동작
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        // refreshToken의 value와 maxAge를 삭제 후 Cookie에 set하여 return, 사용자의 브라우저에서 refreshToken을 삭제하는 과정
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

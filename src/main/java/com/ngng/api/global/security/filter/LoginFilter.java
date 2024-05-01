package com.ngng.api.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngng.api.global.security.custom.CustomHttpServletResponseWrapper;
import com.ngng.api.global.security.custom.CustomUserDetails;
import com.ngng.api.global.security.dto.response.LoginResponse;
import com.ngng.api.global.security.jwt.util.JwtTokenProvider;
import com.ngng.api.user.user.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        // request에서 email과 password를 받아 security 내부적으로 사용되는 token 생성
        String email = obtainEmail(request);
        String password = obtainPassword(request);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);

        return authenticationManager.authenticate(token);
    }

    protected String obtainEmail(HttpServletRequest request) {

        return request.getParameter("email");
    }

    // 로그인에 성공할 시 동작되는 메서드
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {

        CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();
        User user = userDetails.getUser();

        String accessToken = tokenProvider.createAccessToken(userDetails);
        String refreshToken = tokenProvider.createRefreshToken(userDetails);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // refreshToken은 header에 set-cookie를 통해 전달
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .path("/")
                .domain("localhost")
                .httpOnly(true)
                .secure(true)
                .build();

        HttpServletResponseWrapper wrapper = new CustomHttpServletResponseWrapper(response);

        wrapper.setHeader(HttpHeaders.AUTHORIZATION, accessToken);
        wrapper.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        // servlet에서 response를 보낼 때 객체 그대로를 보낼 수 없어 ObjectMapper를 통해 객체를 String형으로 변환
        ObjectMapper mapper = new ObjectMapper();

        LoginResponse loginResponse = LoginResponse.success(user);
        String stringResponse = mapper.writeValueAsString(loginResponse);

        // accessToken은 body에 담아서 전달
        PrintWriter writer = response.getWriter();

        writer.write(stringResponse);
        writer.flush();

        response.setStatus(HttpServletResponse.SC_OK);
    }

    // 로그인에 실패할 시 동작되는 메서드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}

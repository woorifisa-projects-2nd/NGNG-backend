package com.ngng.api.global.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngng.api.global.security.dto.response.LoginResponse;
import com.ngng.api.global.security.jwt.custom.CustomUserDetails;
import com.ngng.api.global.security.jwt.util.TokenUtils;
import com.ngng.api.user.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        String email = obtainEmail(request);
        String password = obtainPassword(request);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);

        return authenticationManager.authenticate(token);
    }

    protected String obtainEmail(HttpServletRequest request) {

        return request.getParameter("email");
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {

        CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();
        User user = userDetails.getUser();

        String accessToken = tokenUtils.createAccessToken(userDetails);
        String refreshToken = tokenUtils.createRefreshToken(userDetails);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // refreshToken은 header에 set-cookie를 통해 전달
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .path("/")
                .domain("localhost")
                .httpOnly(true)
                .secure(true)
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        ObjectMapper mapper = new ObjectMapper();

        LoginResponse loginResponse = LoginResponse.of(user, accessToken);
        String stringResponse = mapper.writeValueAsString(loginResponse);

        // accessToken은 body에 담아서 전달
        PrintWriter writer = response.getWriter();

        writer.write(stringResponse);
        writer.flush();

        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}

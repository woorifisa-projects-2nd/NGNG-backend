package com.ngng.api.global.security.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ngng.api.global.security.custom.CustomHttpServletResponseWrapper;
import com.ngng.api.global.security.custom.CustomUserDetails;
import com.ngng.api.global.security.jwt.util.JwtTokenProvider;
import com.ngng.api.global.security.jwt.util.JwtTokenVerifier;
import com.ngng.api.user.user.entity.Role;
import com.ngng.api.user.user.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final JwtTokenVerifier tokenVerifier;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        HttpServletResponseWrapper wrapper = new CustomHttpServletResponseWrapper(response);

        // Authorization Header에서 get accessToken
        String accessToken = request.getHeader("Authorization");

        // accessToken이 없거나 유효기간이 지났을 경우 refreshToken 검증
        if (accessToken.isEmpty() || tokenVerifier.isExpired(accessToken)) {

            Cookie[] cookies = request.getCookies();

            if (cookies == null || ObjectUtils.isEmpty(cookies)) { // cookies가 비어있을 경우

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                return;
            }

            // Cookie에서 get refreshToken
            String refreshToken = Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals("refreshToken"))
                    .findFirst()
                    .get()
                    .getValue();

            if (tokenVerifier.isExpired(refreshToken)) { // refreshToken의 유효기간이 지났을 경우

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                return;
            }

            if (isValidRefreshToken(refreshToken)) { // refreshToken이 유효할 경우 accessToken을 재발급하고 이후 동작(기존 요청) 처리

                CustomUserDetails customUserDetails = createCustomUserDetails(refreshToken);
                String token = tokenProvider.createAccessToken(customUserDetails);
                wrapper.setHeader(HttpHeaders.AUTHORIZATION, token);

                Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);

                return;
            }
        }

        if (isValidAccessToken(accessToken)) { // accessToken이 유효할 경우 곧바로 이후 동작(기존 요청) 처리

            CustomUserDetails customUserDetails = createCustomUserDetails(accessToken);
            Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);

            return;
        }

        // 여기까지 올 경우가 없어야 하지만, accessToken이 invalid할 경우 401 에러 반환
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String uri = request.getRequestURI();
        String method = request.getMethod();

        // 해당 패턴과 일치한 요청일 경우 TokenFilter를 거치지 않도록 설정
        Pattern uriPattern = Pattern.compile("/(login|logout|search|join|find|category|main)(/.*)?");

        return (uri.startsWith("/products") && method.equals("GET")) || uriPattern.matcher(uri).matches();
    }

    // createAccessToken이 UserDetails를 인자로 받기 때문에 token의 정보를 기준으로 UserDetails를 생성하는 메서드
    private CustomUserDetails createCustomUserDetails(String token) {

        String email = tokenVerifier.getEmail(token);
        String role = tokenVerifier.getRole(token);

        return new CustomUserDetails(User.builder()
                .email(email)
                .role(Role.builder()
                        .roleType(role)
                        .build())
                .build());
    }

    // accessToken과 refreshToken의 유효성을 검증
    private boolean isValidAccessToken(String token) {

        return token.startsWith("Bearer ") ||
                tokenVerifier.validateToken(token) ||
                tokenVerifier.getType(token).equals("access");
    }

    private boolean isValidRefreshToken(String refreshToken) {

        return tokenVerifier.validateToken(refreshToken) ||
                tokenVerifier.getType(refreshToken).equals("refresh");
    }
}

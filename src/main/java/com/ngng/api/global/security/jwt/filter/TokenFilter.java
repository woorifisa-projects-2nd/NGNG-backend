package com.ngng.api.global.security.jwt.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ngng.api.global.security.jwt.custom.CustomHttpServletResponseWrapper;
import com.ngng.api.global.security.jwt.custom.CustomUserDetails;
import com.ngng.api.global.security.jwt.util.JwtTokenProvider;
import com.ngng.api.global.security.jwt.util.JwtTokenVerifier;
import com.ngng.api.user.entity.Role;
import com.ngng.api.user.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        if (accessToken.isEmpty() || tokenVerifier.isExpired(accessToken)) {

            // Cookie에서 get refreshToken
            String refreshToken = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals("refreshToken"))
                    .findFirst()
                    .get()
                    .getValue();

            if (refreshToken.isEmpty() || tokenVerifier.isExpired(refreshToken)) {

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                return;
            }

            if (isValidRefreshToken(refreshToken)) {

                CustomUserDetails customUserDetails = createCustomUserDetails(refreshToken);
                String token = tokenProvider.createAccessToken(customUserDetails);
                wrapper.setHeader(HttpHeaders.AUTHORIZATION, token);

                // 원래 token만 재발급해줘야하지만, 임의로 이후 동작까지 되도록 설정(추후 변경)
                Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);

                return;
            }
        }

        if (isValidAccessToken(accessToken)) {

            CustomUserDetails customUserDetails = createCustomUserDetails(accessToken);
            Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String uri = request.getRequestURI();
        String method = request.getMethod();

        Pattern uriPattern = Pattern.compile("/(login|logout|search|join|find|category|main)(/.*)?");

        return (uri.startsWith("/products") && method.equals("GET")) || uriPattern.matcher(uri).matches();
    }

    private CustomUserDetails createCustomUserDetails(String token) throws JsonProcessingException {

        String email = tokenVerifier.getEmail(token);
        String role = tokenVerifier.getRole(token);

        return new CustomUserDetails(User.builder()
                .email(email)
                .role(Role.builder()
                        .roleType(role)
                        .build())
                .build());
    }

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

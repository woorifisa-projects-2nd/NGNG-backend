package com.ngng.api.global.security.jwt.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngng.api.global.security.dto.response.ReissuanceAccessTokenResponse;
import com.ngng.api.global.security.jwt.custom.CustomUserDetails;
import com.ngng.api.global.security.jwt.util.JwtTokenProvider;
import com.ngng.api.global.security.jwt.util.JwtTokenVerifier;
import com.ngng.api.user.entity.Role;
import com.ngng.api.user.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final JwtTokenVerifier tokenVerifier;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = request.getHeader("Authorization");

        if (checkAccessToken(accessToken)) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        if (tokenVerifier.isExpired(accessToken)) {

            log.info("expired accessToken");

            String refreshToken = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals("refreshToken"))
                    .findFirst()
                    .toString();

            if (checkRefreshToken(refreshToken)) {

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                return;
            }

            createAndSetReissuedAccessToken(refreshToken, response);

            return;
        }

        CustomUserDetails customUserDetails = createCustomUserDetails(accessToken);
        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String uri = request.getRequestURI();
        String method = request.getMethod();

        Pattern uriPattern = Pattern.compile("/(login|logout|search|join|find|products)(/.*)?");

        return (uri.startsWith("/product") && method.equals("GET")) || uriPattern.matcher(uri).matches();
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

    private boolean checkAccessToken(String accessToken) {

        return accessToken == null ||
                !accessToken.startsWith("Bearer ") ||
                !tokenVerifier.validateToken(accessToken) ||
                !tokenVerifier.getType(accessToken).equals("access");
    }

    private boolean checkRefreshToken(String refreshToken) {

        return refreshToken == null ||
                !tokenVerifier.getType(refreshToken).equals("refresh") ||
                !tokenVerifier.checkRefreshToken(refreshToken);
    }

    private void createAndSetReissuedAccessToken(String refreshToken, HttpServletResponse response) throws IOException {

        CustomUserDetails userDetails = createCustomUserDetails(refreshToken);

        String reissuedAccessToken = tokenProvider.createAccessToken(userDetails);
        ReissuanceAccessTokenResponse tokenResponse = ReissuanceAccessTokenResponse.of(reissuedAccessToken);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        String stringResponse = mapper.writeValueAsString(tokenResponse);

        PrintWriter writer = response.getWriter();

        writer.write(stringResponse);
        writer.flush();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}

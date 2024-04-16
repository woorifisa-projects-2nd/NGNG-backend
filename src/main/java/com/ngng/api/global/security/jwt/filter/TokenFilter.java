package com.ngng.api.global.security.jwt.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngng.api.global.security.dto.response.LoginResponse;
import com.ngng.api.global.security.dto.response.ReissuanceAccessTokenResponse;
import com.ngng.api.global.security.jwt.custom.CustomUserDetails;
import com.ngng.api.global.security.jwt.util.TokenUtils;
import com.ngng.api.role.entity.Role;
import com.ngng.api.user.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    private final TokenUtils tokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = request.getHeader("Authorization");

        if (accessToken == null ||
                !accessToken.startsWith("Bearer ") ||
                tokenUtils.validateToken(accessToken) ||
                !tokenUtils.getType(accessToken).equals("access")) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        if (tokenUtils.isExpired(accessToken)) {

            log.info("expired accessToken");

            String refreshToken = Arrays.stream(
                            request.getCookies())
                    .filter(cookie -> cookie.getName().equals("refreshToken"))
                    .findFirst()
                    .toString();

            if (refreshToken == null ||
                    !tokenUtils.getType(refreshToken).equals("refresh") ||
                    tokenUtils.checkRefreshToken(refreshToken)) {

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                return;
            }

            CustomUserDetails userDetails = createCustomUserDetails(refreshToken);

            String reissuedAccessToken = tokenUtils.createAccessToken(userDetails);
            ReissuanceAccessTokenResponse tokenResponse = ReissuanceAccessTokenResponse.of(reissuedAccessToken);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            ObjectMapper mapper = new ObjectMapper();
            String stringResponse = mapper.writeValueAsString(tokenResponse);

            PrintWriter writer = response.getWriter();

            writer.write(stringResponse);
            writer.flush();

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

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

        Pattern uriPattern = Pattern.compile("/(login|logout|search|join|find)(/.*)?");

        return (uri.startsWith("/product") && method.equals("GET")) || uriPattern.matcher(uri).matches();
    }

    public CustomUserDetails createCustomUserDetails(String token) throws JsonProcessingException {

        String email = tokenUtils.getEmail(token);
        String role = tokenUtils.getRole(token);

        return new CustomUserDetails(User.builder()
                .email(email)
                .role(Role.builder()
                        .roleType(role)
                        .build())
                .build());
    }
}

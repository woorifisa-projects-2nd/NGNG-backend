package com.ngng.api.global.security.jwt.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngng.api.global.security.dto.response.ReissuanceAccessTokenResponse;
import com.ngng.api.global.security.jwt.custom.CustomUserDetails;
import com.ngng.api.global.security.jwt.util.JwtTokenProvider;
import com.ngng.api.global.security.jwt.util.JwtTokenVerifier;
import com.ngng.api.role.entity.Role;
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

    private final JwtTokenVerifier tokenVerifier;
    private final JwtTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = request.getHeader("Authorization");

        if (checkAccessToken(accessToken)) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        if (tokenVerifier.isExpired(accessToken)) {

            String refreshToken = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals("refreshToken"))
                    .findFirst()
                    .toString();

            if (checkRefreshToken(refreshToken)) {

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                return;
            }

            createAndSetNewAccessToken(refreshToken, response);

            return;
        }

        CustomUserDetails customUserDetails = createCustomUserDetailsByToken(accessToken);
        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    /*
    * boolean값으로 return되는 메서드로,
    * 해당하는 uri 패턴과 일치할 때(return이 true일 때) 해당 필터를 동작하지 않게 하는 메서드
    * false를 return할 시 필터 동작
    * */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String uri = request.getRequestURI();
        String method = request.getMethod();

        Pattern uriPattern = Pattern.compile("/(login|logout|search|join|find)(/.*)?");

        return (uri.startsWith("/product") && method.equals("GET")) || uriPattern.matcher(uri).matches();
    }

    // token의 email과 role으로 임시 User객체를 생성하고 이를 UserDetails로 변환
    private CustomUserDetails createCustomUserDetailsByToken(String token) throws JsonProcessingException {

        String email = tokenVerifier.getEmail(token);
        String role = tokenVerifier.getRole(token);

        return new CustomUserDetails(User.builder()
                .email(email)
                .role(Role.builder()
                        .roleType(role)
                        .build())
                .build());
    }

    // accessToken이 유효한지 검증
    private boolean checkAccessToken(String accessToken) {

        return accessToken == null ||
                !accessToken.startsWith("Bearer ") ||
                !tokenVerifier.validateToken(accessToken) ||
                !tokenVerifier.getType(accessToken).equals("access");
    }

    // refreshToken이 유효한지 검증
    private boolean checkRefreshToken(String refreshToken) {

        return refreshToken == null ||
                !tokenVerifier.getType(refreshToken).equals("refresh") ||
                !tokenVerifier.checkRefreshToken(refreshToken);
    }

    // refreshToken이 유효할 시 다시 accessToken을 만들어 response에 set하는 메서드
    private void createAndSetNewAccessToken(String refreshToken, HttpServletResponse response) throws IOException {

        CustomUserDetails userDetails = createCustomUserDetailsByToken(refreshToken);

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

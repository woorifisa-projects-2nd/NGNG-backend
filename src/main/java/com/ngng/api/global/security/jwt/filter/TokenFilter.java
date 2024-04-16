package com.ngng.api.global.security.jwt.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngng.api.global.security.dto.response.LoginResponse;
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

            String refreshToken = Arrays.stream(
                            request.getCookies())
                    .filter(cookie -> cookie.getName().equals("refreshToken"))
                    .findFirst()
                    .toString();

            if (refreshToken == null ||
                    !refreshToken.startsWith("Bearer ") ||
                    tokenUtils.validateToken(refreshToken) ||
                    !tokenUtils.getType(refreshToken).equals("refresh") ||
                    tokenUtils.isExpired(refreshToken)) {

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                return;
            }

            /*
            * refreshToken이 검증되면 accessToken을 다시 만들어서 response에 담아줘야하는데,
            * filter에서 처리하고 이후에 계속 유지될 수 있게하는 방법 찾아봐야함.
            * */
            
//            CustomUserDetails userDetails = createCustomUserDetails(refreshToken);
//            User user = userDetails.getUser();
//
//            String recreateAccessToken = tokenUtils.createAccessToken(userDetails);
//
//            response.setContentType("application/json");
//            response.setCharacterEncoding("UTF-8");
//
//            ObjectMapper mapper = new ObjectMapper();
//
//            LoginResponse loginResponse = LoginResponse.of(user, recreateAccessToken);
//            String stringUser = mapper.writeValueAsString(loginResponse);
//
//            PrintWriter writer = response.getWriter();
//
//            writer.write(stringUser);
//            writer.flush();
//
//            response.setStatus(HttpServletResponse.SC_OK);
        }


//        String tokenType = tokenUtils.getType(token);
//
//        if (tokenType.equals("access")) {
//
//            if (tokenUtils.checkAccessToken(token)) {
//
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            } else {
//
//                log.info("access token permit");
//
//                CustomUserDetails customUserDetails = createCustomUserDetails(token);
//                Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//                filterChain.doFilter(request, response);
//            }
//
//            return;
//        }
//
//        if (tokenType.equals("refresh") && !tokenUtils.checkRefreshToken(token)) {
//
//            if (tokenUtils.checkRefreshToken(token)) {
//
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            } else {
//
//                CustomUserDetails userDetails = createCustomUserDetails(token);
//                User user = userDetails.getUser();
//
//                Map<String, String> tokens = tokenUtils.createToken(userDetails);
//                String accessToken = tokens.get("accessToken");
//                String refreshToken = tokens.get("refreshToken");
//
//                response.setContentType("application/json");
//                response.setCharacterEncoding("UTF-8");
//
//
//                ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
//                        .path("/")
//                        .httpOnly(true)
//                        .secure(true)
//                        .build();
//
//                response.setHeader("Set-Cookie", cookie.toString());
//
//                ObjectMapper mapper = new ObjectMapper();
//
//                LoginResponse loginResponse = LoginResponse.of(user, accessToken);
//                String stringUser = mapper.writeValueAsString(loginResponse);
//
//                PrintWriter writer = response.getWriter();
//
//                writer.write(stringUser);
//                writer.flush();
//
//                response.setStatus(HttpServletResponse.SC_OK);
//            }
//
//            return;
//        }
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

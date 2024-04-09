package com.ngng.api.global.security.jwt.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngng.api.global.security.jwt.custom.CustomUserDetails;
import com.ngng.api.global.security.jwt.util.TokenUtils;
import com.ngng.api.role.entity.Role;
import com.ngng.api.user.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    private final TokenUtils tokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);

            return;
        }

        String tokenType = tokenUtils.getType(token);

        if (tokenType.equals("access")) {

            if (tokenUtils.checkAccessToken(token)) {

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                return;
            } else {

                CustomUserDetails customUserDetails = createCustomUserDetails(token);
                Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);

                return;
            }
        }

        if (tokenType.equals("refresh") && !tokenUtils.checkRefreshToken(token)) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        CustomUserDetails customUserDetails = createCustomUserDetails(token);
        Map<String, String> tokens = tokenUtils.createToken(customUserDetails);
        ObjectMapper mapper = new ObjectMapper();
        String stringResponse = mapper.writeValueAsString(tokens);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(stringResponse);
        response.getWriter().flush();

        response.setStatus(HttpServletResponse.SC_OK);

        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String uri = request.getRequestURI();
        String method = request.getMethod();

        if (uri.startsWith("/product") && method.equals("GET")) {

            return true;
        }

        if (uri.startsWith("/login") || uri.startsWith("/logout") || uri.startsWith("/search") || uri.startsWith("/join") || uri.startsWith("/find")) {

            return true;
        }

        return false;
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

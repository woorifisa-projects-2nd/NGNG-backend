package com.ngng.api.user.user.service;

import com.ngng.api.global.security.custom.CustomHttpServletResponseWrapper;
import com.ngng.api.global.security.custom.CustomUserDetails;
import com.ngng.api.global.security.jwt.util.JwtTokenProvider;
import com.ngng.api.user.user.dto.request.AccountConfirmRequest;
import com.ngng.api.user.user.dto.request.AddressConfirmRequest;
import com.ngng.api.user.user.dto.response.AccountConfirmResponse;
import com.ngng.api.user.user.dto.response.AddressConfirmResponse;
import com.ngng.api.user.user.entity.Role;
import com.ngng.api.user.user.entity.User;
import com.ngng.api.user.user.repository.UserRepository;
import com.ngng.api.user.user.repository.UserRoleRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ConfirmService {

    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public AccountConfirmResponse accountConfirm(AccountConfirmRequest request, Cookie cookie, HttpServletResponse response) {

        User user = userRepository.findById(request.userId()).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저"));

        if (user.getAccountNumber() != null || userRepository.existsByAccountNumber(request.accountNumber())) {

            return AccountConfirmResponse.fail();
        }

        // user entity에 accountNumber, accountBank 저장, role 변경
        Role role = roleRepository.findByRoleType("USER");

        user.accountConfirm(request.accountBank(), request.accountNumber(), role);

//         accessToken과 refreshToken 재발급 및 기존 토큰 삭제
//        jwtTokenProvider.deleteRefreshToken(cookie.getValue());

        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        String accessToken = jwtTokenProvider.createAccessToken(customUserDetails);
        String refreshToken = jwtTokenProvider.createRefreshToken(customUserDetails);

        HttpServletResponseWrapper wrapper = new CustomHttpServletResponseWrapper(response);

        ResponseCookie responseCookie = ResponseCookie.from("refreshToken", refreshToken)
                .path("/")
                .domain("localhost")
                .httpOnly(true)
                .secure(true)
                .build();

        wrapper.setHeader(HttpHeaders.AUTHORIZATION, accessToken);
        wrapper.setHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

        return AccountConfirmResponse.success(accessToken);
    }

    @Transactional
    public AddressConfirmResponse addressConfirm(AddressConfirmRequest request) {

        User user = userRepository.findById(request.id()).orElseThrow();

        user.addressConfirm(request.address());

        return AddressConfirmResponse.success();
    }
}

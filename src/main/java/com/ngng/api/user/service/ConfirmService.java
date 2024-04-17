package com.ngng.api.user.service;

import com.ngng.api.global.security.jwt.util.TokenUtils;
import com.ngng.api.role.entity.Role;
import com.ngng.api.user.dto.request.AccountConfirmRequest;
import com.ngng.api.user.dto.request.AddressConfirmRequest;
import com.ngng.api.user.dto.response.AccountConfirmResponse;
import com.ngng.api.user.dto.response.AddressConfirmResponse;
import com.ngng.api.user.entity.User;
import com.ngng.api.user.repository.UserRepository;
import com.ngng.api.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ConfirmService {

    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;
    private final TokenUtils tokenUtils;

    @Transactional
    public AccountConfirmResponse accountConfirm(AccountConfirmRequest request) {

        User user = userRepository.findById(request.userId()).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저"));

        if (user.getAccountNumber() != null || userRepository.existsByAccountNumber(request.accountNumber())) {

            return AccountConfirmResponse.fail();
        }

        // user entity에 accountNumber, accountBank 저장, role 변경
        Role role = roleRepository.findByRoleType("USER");
        // TODO 토큰 재발급 추가
        user.accountConfirm(request.accountBank(), request.accountNumber(), role);

        return AccountConfirmResponse.success();
    }

    @Transactional
    public AddressConfirmResponse addressConfirm(AddressConfirmRequest request) {

        User user = userRepository.findById(request.id()).orElseThrow();

        user.addressConfirm(request.address());

        return AddressConfirmResponse.success();
    }
}

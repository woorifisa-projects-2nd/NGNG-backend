package com.ngng.api.user.service;

import com.ngng.api.point.service.PointHistoryService;
import com.ngng.api.role.entity.Role;
import com.ngng.api.user.dto.request.JoinRequest;
import com.ngng.api.user.dto.response.JoinResponse;
import com.ngng.api.user.entity.User;
import com.ngng.api.user.repository.UserRepository;
import com.ngng.api.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class JoinService {

    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final PointHistoryService pointHistoryService;

    @Transactional
    public JoinResponse join(JoinRequest request) {

        // 현재 전화번호와 email이 존재하는지 확인, 이메일 확인 및 전화번호 확인 절차에서도 확인 필요
        if (userRepository.existsByPhoneNumber(request.phoneNumber()) || userRepository.existsByEmail(request.email())) {

            return JoinResponse.fail();
        }

        // 계좌인증 로직이 완성되면 UNCONFIRMED_USER 로 변경
        Role role = roleRepository.findByRoleType("UNCONFIRMED_USER");

        User user = userRepository.save(User.builder()
                .name(request.name())
                .email(request.email())
                .nickname(request.nickname())
                .password(passwordEncoder.encode(request.password()))
                .phoneNumber(request.phoneNumber())
                .role(role)
                .build());

        pointHistoryService.createInitByUser(user);

        return JoinResponse.success(user.getUserId());
    }
}

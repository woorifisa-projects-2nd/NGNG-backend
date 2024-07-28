package com.ngng.api.user.user.service;

import com.ngng.api.user.point.service.PointHistoryService;
import com.ngng.api.user.user.dto.request.JoinRequest;
import com.ngng.api.user.user.dto.response.JoinResponse;
import com.ngng.api.user.user.entity.Role;
import com.ngng.api.user.user.entity.User;
import com.ngng.api.user.user.repository.UserRepository;
import com.ngng.api.user.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class JoinService {

    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final PointHistoryService pointHistoryService;

    @Transactional
    public JoinResponse join(JoinRequest request) {

        if (userRepository.existsByPhoneNumber(request.phoneNumber()) || userRepository.existsByEmail(request.email())) {

            return JoinResponse.fail();
        }

        Role role = roleRepository.findByRoleType("UNCONFIRMED_USER");

        User user = userRepository.save(User.builder()
                .name(request.name())
                .email(request.email())
                .nickname(request.nickname())
                .password(bCryptPasswordEncoder.encode(request.password()))
                .phoneNumber(request.phoneNumber())
                .role(role)
                .build());

        pointHistoryService.createInitByUser(user);

        return JoinResponse.success(user.getUserId());
    }
}

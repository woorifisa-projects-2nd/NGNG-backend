package com.ngng.api.global.security.dto.response;

import com.ngng.api.user.user.entity.User;
import lombok.Builder;

@Builder
public record LoginResponse(Long id,
                            String name,
                            String nickname,
                            String role) {

    public static LoginResponse success(User user) {

        return LoginResponse.builder()
                .id(user.getUserId())
                .name(user.getName())
                .role(user.getRole().getRoleType())
                .nickname(user.getNickname())
                .build();
    }
}

package com.ngng.api.global.security.dto.response;

import com.ngng.api.user.entity.User;
import lombok.Builder;

@Builder
public record LoginResponse(Long id,
                            String name,
                            String nickname,
                            String accessToken,
                            String refreshToken) {

    public static LoginResponse of(User user, String accessToken) {

        return LoginResponse.builder()
                .id(user.getUserId())
                .name(user.getName())
                .nickname(user.getNickname())
                .accessToken(accessToken)
                .build();
    }
}

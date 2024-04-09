package com.ngng.api.global.security.dto.response;

import com.ngng.api.user.entity.User;
import lombok.Builder;

import java.util.Map;

@Builder
public record LoginResponse(Long id,
                            String name,
                            String nickname,
                            String accessToken,
                            String refreshToken) {

    public static LoginResponse of(User user, Map<String, String> tokens) {

        String accessToken = tokens.get("accessToken");
        String refreshToken = tokens.get("refreshToken");

        return LoginResponse.builder()
                .id(user.getUserId())
                .name(user.getName())
                .nickname(user.getNickname())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}

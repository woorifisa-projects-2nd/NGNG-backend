package com.ngng.api.user.user.dto.response;

import lombok.Builder;

@Builder
public record JoinResponse(boolean isJoinSuccess,
                           Long id) {

    public static JoinResponse fail() {

        return JoinResponse.builder()
                .isJoinSuccess(false)
                .build();
    }

    public static JoinResponse success(Long id) {

        return JoinResponse.builder()
                .isJoinSuccess(true)
                .id(id)
                .build();
    }
}

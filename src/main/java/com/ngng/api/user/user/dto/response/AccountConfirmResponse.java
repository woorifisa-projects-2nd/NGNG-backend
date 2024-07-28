package com.ngng.api.user.user.dto.response;

import lombok.Builder;

@Builder
public record AccountConfirmResponse(boolean isConfirmAccount,
                                     String accessToken) {

    public static AccountConfirmResponse fail() {

        return AccountConfirmResponse.builder()
                .isConfirmAccount(false)
                .build();
    }
    public static AccountConfirmResponse success(String accessToken) {

        return AccountConfirmResponse.builder()
                .isConfirmAccount(true)
                .accessToken(accessToken)
                .build();
    }
}

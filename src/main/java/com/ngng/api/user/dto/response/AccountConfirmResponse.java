package com.ngng.api.user.dto.response;

import lombok.Builder;

@Builder
public record AccountConfirmResponse(boolean isConfirmAccount) {

    public static AccountConfirmResponse fail() {

        return AccountConfirmResponse.builder()
                .isConfirmAccount(false)
                .build();
    }
    public static AccountConfirmResponse success() {

        return AccountConfirmResponse.builder()
                .isConfirmAccount(true)
                .build();
    }
}

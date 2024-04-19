package com.ngng.api.user.dto.response;

import lombok.Builder;

@Builder
public record EmailAuthResponse(String authNumber,
                                boolean isSuccess) {

    public static EmailAuthResponse success(String authNumber) {

        return EmailAuthResponse.builder()
                .authNumber(authNumber)
                .isSuccess(true)
                .build();
    }

    public static EmailAuthResponse fail() {

        return EmailAuthResponse.builder()
                .isSuccess(false)
                .build();
    }
}

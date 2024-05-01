package com.ngng.api.user.user.dto.response;

import lombok.Builder;

@Builder
public record PhoneNumberAuthResponse(String authNumber,
                                      boolean isSuccess) {

    public static PhoneNumberAuthResponse success(String authNumber) {

        return PhoneNumberAuthResponse.builder()
                .authNumber(authNumber)
                .isSuccess(true)
                .build();
    }

    public static PhoneNumberAuthResponse fail() {

        return PhoneNumberAuthResponse.builder()
                .isSuccess(true)
                .build();
    }
}

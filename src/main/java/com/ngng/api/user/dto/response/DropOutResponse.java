package com.ngng.api.user.dto.response;

import lombok.Builder;

@Builder
public record DropOutResponse(boolean isDropOut) {

    public static DropOutResponse success() {

        return DropOutResponse.builder()
                .isDropOut(true)
                .build();
    }

    public static DropOutResponse fail() {

        return DropOutResponse.builder()
                .isDropOut(false)
                .build();
    }
}

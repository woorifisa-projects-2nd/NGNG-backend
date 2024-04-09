package com.ngng.api.user.dto.response;

import lombok.Builder;

@Builder
public record AddressConfirmResponse(boolean isConfirmAddress) {

    public static AddressConfirmResponse success() {

        return AddressConfirmResponse.builder()
                .isConfirmAddress(true)
                .build();
    }
}

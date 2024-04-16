package com.ngng.api.global.security.dto.response;

import lombok.Builder;

@Builder
public record ReissuanceAccessTokenResponse(String accessToken) {

    public static ReissuanceAccessTokenResponse of (String accessToken) {

        return ReissuanceAccessTokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}

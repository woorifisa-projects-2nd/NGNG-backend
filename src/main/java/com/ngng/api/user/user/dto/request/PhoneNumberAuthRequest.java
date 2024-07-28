package com.ngng.api.user.user.dto.request;

import lombok.Builder;

@Builder
public record PhoneNumberAuthRequest(String name,
                                     String phoneNumber) {
}

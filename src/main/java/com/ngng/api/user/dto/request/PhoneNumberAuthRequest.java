package com.ngng.api.user.dto.request;

import lombok.Builder;

@Builder
public record PhoneNumberAuthRequest(String name,
                                     String phoneNumber) {
}

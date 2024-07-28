package com.ngng.api.user.user.dto.request;

public record JoinRequest(String name,
                          String email,
                          String password,
                          String nickname,
                          String phoneNumber) {
}

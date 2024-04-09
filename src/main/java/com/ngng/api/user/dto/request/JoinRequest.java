package com.ngng.api.user.dto.request;

public record JoinRequest(String name,
                          String email,
                          String password,
                          String nickname,
                          String phoneNumber,
                          String channel) {
}

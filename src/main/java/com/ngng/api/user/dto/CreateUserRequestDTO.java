package com.ngng.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateUserRequestDTO {
    private String name;
    private String nickname;
    private String email;
    private String password;
    private String address;
    private String phoneNumber;
    private String channel;
    private String accountBank;
    private String accountNumber;
    private Long roleId;
}

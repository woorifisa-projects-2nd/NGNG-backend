package com.ngng.api.user.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequestDTO {

    private String nickname;
    private String address;

    private String accountBank;
    private String accountNumber;

}
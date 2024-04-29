package com.ngng.api.user.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AdminUserUpdateRequest {
    private Long userId;
    private String name;
    private String nickName;
    private String phoneNumber;
    private String email;
    private String password;
    private String accountBank;
    private String accountNumber;
    private String address;
}

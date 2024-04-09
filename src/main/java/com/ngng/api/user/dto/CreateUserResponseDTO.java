package com.ngng.api.user.dto;

import com.ngng.api.role.entity.Role;
import com.ngng.api.user.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateUserResponseDTO {
    private Long userId;
    private String name;
    private String nickname;
    private String email;
    private String password;
    private String address;
    private String phoneNumber;
    private String channel;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String accountBank;
    private String accountNumber;
    private Role roleType;
}

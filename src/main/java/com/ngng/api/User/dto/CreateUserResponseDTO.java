package com.ngng.api.User.dto;

import com.ngng.api.User.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String accountBank;
    private String accountNumber;
    private UserRole roleType;
}

package com.ngng.api.User.dto;

import com.ngng.api.User.entity.User;
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
public class UpdateUserRequestDTO {
    private String name;
    private String nickname;
    private String email;
    private String password;
    private String address;
    private String phoneNumber;
    private String channel;
    private LocalDateTime createdAt;
    private String accountBank;
    private String accountNumber;
    private UserRole roleType;

    // 정적 팩토리 메서드
    public static UpdateUserRequestDTO from(User user) {
        // User 엔티티에 담긴 개별 값들을 추출
//        final Long userId = user.getUserId();
        final String name = user.getName();
        final String nickname = user.getNickname();
        final String email = user.getEmail();
        final String password = user.getPassword();
        final String address = user.getAddress();
        final String phoneNumber = user.getPhoneNumber();
        final String channel = user.getChannel();
        final LocalDateTime createdAt = user.getCreatedAt();
        final String accountBank = user.getAccountBank();
        final String accountNumber = user.getAccountNumber();
        final UserRole roleType = user.getRoleType();

        return new UpdateUserRequestDTO(name, nickname, email, password, address, phoneNumber, channel, createdAt, accountBank, accountNumber, roleType);

    }

}

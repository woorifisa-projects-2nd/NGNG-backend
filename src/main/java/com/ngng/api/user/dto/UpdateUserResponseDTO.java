package com.ngng.api.user.dto;

import com.ngng.api.user.entity.User;
import com.ngng.api.role.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateUserResponseDTO {
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

    // 정적 팩토리 메서드
    public static UpdateUserResponseDTO from(User user) {
        // User 엔티티에 담긴 개별 값들을 추출
        final Long userId = user.getUserId();
        final String name = user.getName();
        final String nickname = user.getNickname();
        final String email = user.getEmail();
        final String password = user.getPassword();
        final String address = user.getAddress();
        final String phoneNumber = user.getPhoneNumber();
        final String channel = user.getChannel();
        final Timestamp createdAt = user.getCreatedAt();
        final Timestamp updatedAt = user.getUpdatedAt();
        final String accountBank = user.getAccountBank();
        final String accountNumber = user.getAccountNumber();
        final Role roleType = user.getRole();

        return new UpdateUserResponseDTO(userId, name, nickname, email, password, address, phoneNumber, channel, createdAt, updatedAt, accountBank, accountNumber, roleType);

    }

}

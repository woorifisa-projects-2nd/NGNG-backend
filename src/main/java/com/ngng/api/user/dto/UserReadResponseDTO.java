package com.ngng.api.user.dto;

import com.ngng.api.point.entity.PointHistory;
import com.ngng.api.role.entity.Role;
import com.ngng.api.user.entity.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserReadResponseDTO {

    private Long userId;
    private String name;
    private String nickName;
    private String phoneNumber;
    private String email;
    private String accountBank;
    private String accountNumber;
    private String address;
    private Role role;
    private Long point;

    public UserReadResponseDTO from(User user){
        return UserReadResponseDTO.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .nickName(user.getNickname())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .accountBank(user.getAccountBank())
                .accountNumber(user.getAccountNumber())
                .address(user.getAddress())
                .role(user.getRole())
                .build();
    }

    public UserReadResponseDTO from(User user, PointHistory pointHistory){
        return UserReadResponseDTO.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .nickName(user.getNickname())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .accountBank(user.getAccountBank())
                .accountNumber(user.getAccountNumber())
                .address(user.getAddress())
                .role(user.getRole())
                .point(pointHistory.getCost())
                .build();
    }

}

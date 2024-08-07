package com.ngng.api.user.user.dto;

import com.ngng.api.product.product.dto.response.ReadProductMypageResponseDTO;
import com.ngng.api.transaction.dto.ReadTransactionDetailsDTO;
import com.ngng.api.user.point.entity.PointHistory;
import com.ngng.api.user.user.entity.Role;
import com.ngng.api.user.user.entity.User;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserMyPageReadResponseDTO {
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
    private List<ReadProductMypageResponseDTO> sellList;
    private List<ReadTransactionDetailsDTO> buyList;

    public UserMyPageReadResponseDTO from(User user){
        return UserMyPageReadResponseDTO.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .nickName(user.getNickname())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .accountBank(user.getAccountBank())
                .accountNumber(user.getAccountNumber())
                .address(user.getAddress())
                .role(from_role(user.getRole()))
                .build();
    }

    public UserMyPageReadResponseDTO from(
            User user,
            PointHistory pointHistory,
            List<ReadProductMypageResponseDTO> sellList,
            List<ReadTransactionDetailsDTO> buyList
    ){
        return UserMyPageReadResponseDTO.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .nickName(user.getNickname())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .accountBank(user.getAccountBank())
                .accountNumber(user.getAccountNumber())
                .address(user.getAddress())
                .role(from_role(user.getRole()))
                .point(pointHistory.getCost())
                .sellList(sellList)
                .buyList(buyList)
                .build();
    }

    public Role from_role(Role role){
        Role newRole = new Role();
        newRole.setRoleId(role.getRoleId());
        newRole.setRoleType(role.getRoleType());
        return newRole;
    }
}

package com.ngng.api.user.dto;

import com.ngng.api.point.entity.PointHistory;
import com.ngng.api.product.dto.response.ReadProductResponseDTO;
import com.ngng.api.role.entity.Role;
import com.ngng.api.transaction.dto.ReadTransactionDetailsDTO;
import com.ngng.api.user.entity.User;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReadUserMyPageResponseDTO {
    private Long userId;
    private String name;
    private String phoneNumber;
    private String email;
    private String accountBank;
    private String accountNumber;
    private String address;
    private Role role;
    private Long point;
    private List<ReadProductResponseDTO> sellList;
    private List<ReadTransactionDetailsDTO> buyList;

    public ReadUserMyPageResponseDTO from(User user){
        return ReadUserMyPageResponseDTO.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .accountBank(user.getAccountBank())
                .accountNumber(user.getAccountNumber())
                .address(user.getAddress())
                .role(from_role(user.getRole()))
                .build();
    }

    public ReadUserMyPageResponseDTO from(
            User user,
            PointHistory pointHistory,
            List<ReadProductResponseDTO> sellList,
            List<ReadTransactionDetailsDTO> buyList
    ){
        return ReadUserMyPageResponseDTO.builder()
                .userId(user.getUserId())
                .name(user.getName())
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

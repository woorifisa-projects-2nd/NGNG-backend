package com.ngng.api.user.service;

import com.ngng.api.role.entity.Role;
import com.ngng.api.user.dto.*;
import com.ngng.api.user.entity.User;
import com.ngng.api.user.entity.UserRole;
import com.ngng.api.user.repository.UserRepository;
import com.ngng.api.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public List<ReadUserListResponseDTO> findAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);

        return users.stream()
                .map(ReadUserListResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    public User findById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        return userOptional.orElse(null);
//        return userRepository.findById(ownerId).orElse(null); // orElseThrow를 통한 적절한 예외 처리 권장
    }

    @Override
    public CreateUserResponseDTO save(CreateUserRequestDTO userCreateDTO) {

        Optional<UserRole> userRole = userRoleRepository.findById(userCreateDTO.getRoleId());
        UserRole role = userRole.orElseThrow();

        User user = User.builder()
                .name(userCreateDTO.getName())
                .nickname(userCreateDTO.getNickname())
                .email(userCreateDTO.getEmail())
                .password(userCreateDTO.getPassword())
                .address(userCreateDTO.getAddress())
                .phoneNumber(userCreateDTO.getPhoneNumber())
                .channel(userCreateDTO.getChannel())
                .accountBank(userCreateDTO.getAccountBank())
                .accountNumber(userCreateDTO.getAccountNumber())
                .role(Role.builder()
                        .roleId(userCreateDTO.getRoleId())
                        .roleType(role.getRoleType())
                        .build()
                )
                .build();

        User responseUser = userRepository.save(user);

        return CreateUserResponseDTO.builder()
                .userId(responseUser.getUserId())
                .name(responseUser.getName())
                .nickname(responseUser.getNickname())
                .email(responseUser.getEmail())
                .password(responseUser.getPassword())
                .address(responseUser.getAddress())
                .phoneNumber(responseUser.getPhoneNumber())
                .channel(responseUser.getChannel())
                .createdAt(responseUser.getCreatedAt())
                .updatedAt(responseUser.getUpdatedAt())
                .accountBank(responseUser.getAccountBank())
                .accountNumber(responseUser.getAccountNumber())
                .roleType(responseUser.getRole())
                .build();

    }

    @Override
    public UpdateUserResponseDTO update(Long userId, UpdateUserRequestDTO userUpdateDTO) {

        Optional<User> found = userRepository.findById(userId);

        User user = User.builder()
                .userId(userId)
                .name(userUpdateDTO.getName())
                .nickname(userUpdateDTO.getNickname())
                .email(userUpdateDTO.getEmail())
                .password(userUpdateDTO.getPassword())
                .address(userUpdateDTO.getAddress())
                .phoneNumber(userUpdateDTO.getPhoneNumber())
                .channel(userUpdateDTO.getChannel())
                .createdAt(found.get().getCreatedAt())
                .accountBank(userUpdateDTO.getAccountBank())
                .accountNumber(userUpdateDTO.getAccountNumber())
                .role(userUpdateDTO.getRoleType())
                .build();

        User responseUser = userRepository.save(user);

        System.out.println(responseUser);

        return UpdateUserResponseDTO.builder()
                .userId(userId)
                .name(responseUser.getName())
                .nickname(responseUser.getNickname())
                .email(responseUser.getEmail())
                .password(responseUser.getPassword())
                .address(responseUser.getAddress())
                .phoneNumber(responseUser.getPhoneNumber())
                .channel(responseUser.getChannel())
                .createdAt(responseUser.getCreatedAt())
                .updatedAt(responseUser.getUpdatedAt())
                .accountBank(responseUser.getAccountBank())
                .accountNumber(responseUser.getAccountNumber())
                .roleType(responseUser.getRole())
                .build();

    }

}

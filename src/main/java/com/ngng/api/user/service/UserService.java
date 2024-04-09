package com.ngng.api.user.service;

import com.ngng.api.user.dto.*;
import com.ngng.api.user.entity.User;

import java.util.List;

public interface UserService {
    List<ReadUserListResponseDTO> findAll();

    User findById(Long userId);

    CreateUserResponseDTO save(CreateUserRequestDTO userCreateDTO);

    UpdateUserResponseDTO update(Long userId, UpdateUserRequestDTO userUpdateDTO);

}

package com.ngng.api.User.service;

import com.ngng.api.User.dto.*;
import com.ngng.api.User.entity.User;

import java.util.List;

public interface UserService {
    List<ReadUserListResponseDTO> findAll();

    User findById(Long userId);

    CreateUserResponseDTO save(CreateUserRequestDTO userCreateDTO);

    UpdateUserResponseDTO update(Long userId, UpdateUserRequestDTO userUpdateDTO);

}

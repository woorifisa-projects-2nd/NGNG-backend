package com.ngng.api.user.service;

import com.ngng.api.user.dto.request.DropOutRequest;
import com.ngng.api.user.dto.response.DropOutResponse;
import com.ngng.api.user.entity.User;
import com.ngng.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DropOutService {

    private final UserRepository userRepository;

    @Transactional
    public DropOutResponse dropOut(DropOutRequest request) {

        User user = userRepository.findById(request.id()).orElseThrow(() -> new UsernameNotFoundException("no user"));

        user.changeVisible();

        return DropOutResponse.success();
    }
}

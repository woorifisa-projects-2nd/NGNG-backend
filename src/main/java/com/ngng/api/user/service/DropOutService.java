package com.ngng.api.user.service;

import com.ngng.api.user.dto.request.DropOutRequest;
import com.ngng.api.user.dto.response.DropOutResponse;
import com.ngng.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DropOutService {

    private final UserRepository userRepository;

    public DropOutResponse dropOut(DropOutRequest request) {

        boolean isExistingUser = userRepository.existsById(request.id());

        if (!isExistingUser) {

            return DropOutResponse.fail();
        }

        // TODO visible 업데이트하는 거로 수정
        userRepository.deleteById(request.id());

        return DropOutResponse.success();
    }
}

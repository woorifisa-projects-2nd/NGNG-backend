package com.ngng.api.user.controller;

import com.ngng.api.user.dto.request.JoinRequest;
import com.ngng.api.user.dto.response.JoinResponse;
import com.ngng.api.user.service.JoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/join")
@RestController
@Slf4j
public class JoinController {

    private final JoinService joinService;

    @PostMapping
    public ResponseEntity<?> join(@RequestBody JoinRequest request) {
        log.info("join process start");

        JoinResponse response = joinService.join(request);

        if (!response.isJoinSuccess()) {

            return ResponseEntity.badRequest().build();
        }

        URI location = URI.create("/users/" + response.id());

        return ResponseEntity.created(location).build();
    }
}

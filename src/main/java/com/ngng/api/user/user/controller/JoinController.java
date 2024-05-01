package com.ngng.api.user.user.controller;

import com.ngng.api.user.user.dto.request.JoinRequest;
import com.ngng.api.user.user.dto.response.JoinResponse;
import com.ngng.api.user.user.service.JoinService;
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
@Slf4j(topic = "user-log")
public class JoinController {

    private final JoinService joinService;

    @PostMapping
    public ResponseEntity<?> join(@RequestBody JoinRequest request) {

        JoinResponse response = joinService.join(request);

        if (!response.isJoinSuccess()) {
            log.error("Faild Create User , Email - {}",request.email());
            return ResponseEntity.badRequest().build();
        }
        log.info("Success Create User id: {} email: {} ",response.id(),request.email());


        URI location = URI.create("/users/" + response.id());

        return ResponseEntity.created(location).build();
    }
}

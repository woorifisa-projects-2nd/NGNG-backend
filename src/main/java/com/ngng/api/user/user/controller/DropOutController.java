package com.ngng.api.user.user.controller;

import com.ngng.api.user.user.dto.request.DropOutRequest;
import com.ngng.api.user.user.dto.response.DropOutResponse;
import com.ngng.api.user.user.service.DropOutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/dropout")
@RestController
@Slf4j(topic = "user-log")
public class DropOutController {

    private final DropOutService dropOutService;


    @DeleteMapping
    public ResponseEntity<?> dropOut(@RequestBody DropOutRequest request) {

        DropOutResponse response = dropOutService.dropOut(request);

        if (!response.isDropOut()) {

            log.error("Failed DropOut User , Email - {}", request.id());

            return ResponseEntity.badRequest().build();
        }

        log.info("Success DropOut User id: {}", request.id());

        return ResponseEntity.noContent().build();
    }
}

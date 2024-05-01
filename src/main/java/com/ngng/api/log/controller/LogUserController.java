package com.ngng.api.log.controller;

import com.ngng.api.log.dto.LogDTO;
import com.ngng.api.log.service.LogUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/logs")
public class LogUserController {

    private final LogUserService logService;
    private final int PAGE_SIZE = 10;

    @GetMapping("/users")
    public ResponseEntity<?> readLog(@RequestParam(value="page", defaultValue="0") Integer page) {
        try {
            Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "timestamp"));
            Page<LogDTO> logs = logService.readLog(pageable);
            return ResponseEntity.ok(logs);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading log file");
        }
    }
}

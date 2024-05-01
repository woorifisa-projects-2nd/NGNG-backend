package com.ngng.api.user.user.controller;

import com.ngng.api.global.security.jwt.util.JwtTokenVerifier;
import com.ngng.api.user.point.service.PointHistoryService;
import com.ngng.api.product.product.service.ProductService;
import com.ngng.api.transaction.service.TransactionDetailsService;
import com.ngng.api.user.user.dto.UserMyPageReadResponseDTO;
import com.ngng.api.user.user.dto.UserReadResponseDTO;
import com.ngng.api.user.user.dto.UserUpdateRequestDTO;
import com.ngng.api.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final PointHistoryService pointHistoryService;
    private final TransactionDetailsService transactionDetailsService;
    private final ProductService productService;
    private final JwtTokenVerifier jwtTokenVerifier;


    @GetMapping
    public ResponseEntity<UserReadResponseDTO> read() {
        return ResponseEntity.ok().body(userService.readUser());
    }

    @GetMapping("/mypage")
    public ResponseEntity<UserMyPageReadResponseDTO> readMyPage() {
        return ResponseEntity.ok().body(userService.readUserMyPage());
    }

    @PutMapping
    public ResponseEntity<UserReadResponseDTO> update(@RequestBody UserUpdateRequestDTO userUpdateDTO) {
            return ResponseEntity.ok().body(userService.update( userUpdateDTO));

    }
}

package com.ngng.api.user.controller;

import com.ngng.api.global.security.jwt.custom.CustomUserDetails;
import com.ngng.api.global.security.jwt.util.TokenUtils;
import com.ngng.api.point.service.PointHistoryService;
import com.ngng.api.product.service.ProductService;
import com.ngng.api.transaction.service.TransactionDetailsService;
import com.ngng.api.user.dto.UserMyPageReadResponseDTO;
import com.ngng.api.user.dto.UserReadResponseDTO;
import com.ngng.api.user.dto.UserUpdateRequestDTO;
import com.ngng.api.user.entity.User;
import com.ngng.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final PointHistoryService pointHistoryService;
    private final TransactionDetailsService transactionDetailsService;
    private final ProductService productService;
    private final TokenUtils tokenUtils;


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

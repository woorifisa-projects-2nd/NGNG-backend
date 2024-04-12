package com.ngng.api.user.controller;

import com.ngng.api.point.service.PointHistoryService;
import com.ngng.api.product.service.ProductService;
import com.ngng.api.transaction.service.TransactionDetailsService;
import com.ngng.api.user.dto.UserMyPageReadResponseDTO;
import com.ngng.api.user.dto.UserReadResponseDTO;
import com.ngng.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final PointHistoryService pointHistoryService;
    private final TransactionDetailsService transactionDetailsService;
    private final ProductService productService;


    @GetMapping("/{userId}")
    public ResponseEntity<UserReadResponseDTO> read(@PathVariable("userId") Long userId) {

        return ResponseEntity.ok().body(userService.readUserById(userId));
    }

    @GetMapping("/{userId}/mypage")
    public ResponseEntity<UserMyPageReadResponseDTO> readMyPage(@PathVariable("userId") Long userId) {

        return ResponseEntity.ok().body(userService.readUserMyPage(userId));
    }



}

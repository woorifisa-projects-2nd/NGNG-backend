package com.ngng.api.user.controller;

import com.ngng.api.point.entity.PointHistory;
import com.ngng.api.point.service.PointHistoryService;
import com.ngng.api.product.dto.response.ReadProductResponseDTO;
import com.ngng.api.product.service.ProductService;
import com.ngng.api.transaction.dto.ReadTransactionDetailsDTO;
import com.ngng.api.transaction.service.TransactionDetailsService;
import com.ngng.api.user.dto.ReadUserMyPageResponseDTO;
import com.ngng.api.user.dto.ReadUserResponseDTO;
import com.ngng.api.user.entity.User;
import com.ngng.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<ReadUserResponseDTO> read(@PathVariable Long userId) {
        User user = userService.findById(userId);
        PointHistory pointHistory = pointHistoryService.readCostByUser(user);

        ReadUserResponseDTO response = ReadUserResponseDTO.from(userService.findById(userId),pointHistory);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{userId}/mypage")
    public ResponseEntity<ReadUserMyPageResponseDTO> readMyPage(@PathVariable("userId") Long userId) {
        User user = userService.findById(userId);
        PointHistory point = pointHistoryService.readCostByUser(user);

        List<ReadProductResponseDTO> sellList = productService.readSellProductsByUserId(user.getUserId());

        List<ReadTransactionDetailsDTO> buyList = transactionDetailsService.readAllByConsumerId(user.getUserId());


        ReadUserMyPageResponseDTO response = new ReadUserMyPageResponseDTO().from(user,point,sellList,buyList);

        return ResponseEntity.ok().body(response);
    }
}

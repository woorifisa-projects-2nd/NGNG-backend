package com.ngng.api.user.user.controller;

import com.ngng.api.user.user.dto.request.AccountConfirmRequest;
import com.ngng.api.user.user.dto.request.AddressConfirmRequest;
import com.ngng.api.user.user.dto.response.AccountConfirmResponse;
import com.ngng.api.user.user.dto.response.AddressConfirmResponse;
import com.ngng.api.user.user.service.ConfirmService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/confirm")
@RestController
@Slf4j(topic = "user-log")
public class ConfirmController {

    private final ConfirmService confirmService;

    @PutMapping("/account")
    public ResponseEntity<?> accountConfirm(@RequestBody AccountConfirmRequest request, @CookieValue("refreshToken")Cookie cookie, HttpServletResponse response) {

        AccountConfirmResponse accountConfirmResponse = confirmService.accountConfirm(request, cookie, response);

        if (!accountConfirmResponse.isConfirmAccount()) {
            log.error("Failed Confirm account , userId : {}  bank : {}  number : {}", request.userId(), request.accountBank(), request.accountNumber());
            return ResponseEntity.badRequest().build();
        }
        log.info("Success  Confirm account , userId : {}  bank : {}  number : {}", request.userId(), request.accountBank(), request.accountNumber());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/address")
    public ResponseEntity<?> addressConfirm(@RequestBody AddressConfirmRequest request) {

        AddressConfirmResponse response = confirmService.addressConfirm(request);

        if (!response.isConfirmAddress()) {
            log.error("Faild Confirm address , userId : {}  address : {}", request.id(), request.address());
            return ResponseEntity.badRequest().build();
        }
        log.info("Success Confirm address , userId : {}  address : {}", request.id(), request.address());
        return ResponseEntity.noContent().build();
    }
}

package com.ngng.api.user.controller;

import com.ngng.api.user.dto.request.AccountConfirmRequest;
import com.ngng.api.user.dto.request.AddressConfirmRequest;
import com.ngng.api.user.dto.response.AccountConfirmResponse;
import com.ngng.api.user.dto.response.AddressConfirmResponse;
import com.ngng.api.user.service.ConfirmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/confirm")
@RestController
@Slf4j(topic = "user-log")
public class ConfirmController {

    private final ConfirmService confirmService;

    @PutMapping("/account")
    public ResponseEntity<?> accountConfirm(@RequestBody AccountConfirmRequest request) {

        AccountConfirmResponse response = confirmService.accountConfirm(request);

        if (!response.isConfirmAccount()) {
            log.error("Faild Confirm account , userId : {}  bank : {}  number : {}",request.userId() , request.accountBank() , request.accountNumber());
            return ResponseEntity.badRequest().build();
        }
        log.info("Success  Confirm account , userId : {}  bank : {}  number : {}",request.userId() , request.accountBank() , request.accountNumber());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/address")
    public ResponseEntity<?> addressConfirm(@RequestBody AddressConfirmRequest request) {

        AddressConfirmResponse response = confirmService.addressConfirm(request);

        if (!response.isConfirmAddress()) {
            log.error("Faild Confirm address , userId : {}  address : {}",request.id() , request.address() );
            return ResponseEntity.badRequest().build();
        }
        log.info("Success Confirm address , userId : {}  address : {}",request.id() , request.address() );
        return ResponseEntity.noContent().build();
    }
}

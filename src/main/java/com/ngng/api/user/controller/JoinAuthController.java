package com.ngng.api.user.controller;

import com.ngng.api.user.dto.request.EmailAuthRequest;
import com.ngng.api.user.dto.request.PhoneNumberAuthRequest;
import com.ngng.api.user.dto.response.EmailAuthResponse;
import com.ngng.api.user.dto.response.PhoneNumberAuthResponse;
import com.ngng.api.user.service.EmailAuthService;
import com.ngng.api.user.service.PhoneNumberAuthService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/join/auth")
@RestController
@Slf4j(topic = "user-log")
public class JoinAuthController {

    private final EmailAuthService emailAuthService;
    private final PhoneNumberAuthService phoneNumberAuthService;

    @PostMapping("/phonenumber")
    public ResponseEntity<?> sendMessage(@RequestBody PhoneNumberAuthRequest request) {

        PhoneNumberAuthResponse response = phoneNumberAuthService.sendMessage(request);

        if (!response.isSuccess()) {
            log.error("Faild Auth PhoneNumber , name - {}",request.name());
            return ResponseEntity.badRequest().build();
        }
        log.info("Success  Auth PhoneNumber , name - {}",request.name());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/email")
    public ResponseEntity<?> sendEmail(@RequestBody EmailAuthRequest request) throws MessagingException {

        EmailAuthResponse response = emailAuthService.sendMail(request);

        if (!response.isSuccess()) {
            log.error("Faild Auth email , name - {}",request.name());
            return ResponseEntity.badRequest().build();
        }
        log.info("Success  Auth email , name - {}",request.name());
        return ResponseEntity.ok().body(response);
    }
}

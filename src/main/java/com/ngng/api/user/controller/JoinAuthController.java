package com.ngng.api.user.controller;

import com.ngng.api.user.dto.request.PhoneNumberAuthRequest;
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

@RequestMapping("/join")
@RestController
public class JoinAuthController {

    final DefaultMessageService messageService;

    private final String nurigoDomain = "https://api.coolsms.co.kr";

    @Value("${message.from}")
    private String from = "01041296078";

    public JoinAuthController(@Value("${message.key.api}")
                              String apiKey,
                              @Value("${message.key.secret}")
                              String secretKey) {

        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, secretKey, nurigoDomain);
    }

    @PostMapping("/phonenumber")
    public ResponseEntity<SingleMessageSentResponse> sendMessage(@RequestBody PhoneNumberAuthRequest request) {

        Message message = new Message();

        message.setFrom(from);
        message.setTo(request.phoneNumber());

        int randomNum = (int) (Math.random() * 999999) + 1;
        String formattedRandomNum = String.format("%06d", randomNum);

        message.setText("니꺼내꺼 인증번호 : " + formattedRandomNum);

        SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));

        return ResponseEntity.ok().body(response);
    }
}

package com.ngng.api.user.service;

import com.ngng.api.user.dto.request.PhoneNumberAuthRequest;
import com.ngng.api.user.dto.response.PhoneNumberAuthResponse;
import com.ngng.api.user.repository.UserRepository;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PhoneNumberAuthService {

    private final DefaultMessageService messageService;
    private final UserRepository userRepository;

    @Value("${message.from}")
    private String from;

    public PhoneNumberAuthService(@Value("${message.key.api}")
                                  String apiKey,
                                  @Value("${message.key.secret}")
                                  String secretKey,
                                  UserRepository userRepository) {

        String nurigoDomain = "https://api.coolsms.co.kr";

        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, secretKey, nurigoDomain);
        this.userRepository = userRepository;
    }

    public PhoneNumberAuthResponse sendMessage(PhoneNumberAuthRequest request) {

        String phoneNumber = request.phoneNumber();

        if (userRepository.existsByPhoneNumber(phoneNumber)) {

            return PhoneNumberAuthResponse.fail();
        }

        Message message = new Message();
        message.setFrom(from); // 발신
        message.setTo(phoneNumber); // 수신

        // 인증번호 세팅
        int randomNum = (int) (Math.random() * 999999) + 1;
        String formattedRandomNum = String.format("%06d", randomNum);

        message.setText(request.name() + "님의 내꺼니꺼 인증번호\n" + formattedRandomNum); // 내용

        SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message)); // 발송 후  get response

        if (response.getStatusCode().equals("2000")) { // 정상 처리

            return PhoneNumberAuthResponse.success(formattedRandomNum);
        }

        return PhoneNumberAuthResponse.fail();
    }
}

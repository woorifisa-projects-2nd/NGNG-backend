package com.ngng.api.user.user.service;

import com.ngng.api.user.user.dto.request.EmailAuthRequest;
import com.ngng.api.user.user.dto.response.EmailAuthResponse;
import com.ngng.api.user.user.repository.UserRepository;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailAuthService {

    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;

    @Value("${spring.mail.username}")
    private String from;

    public EmailAuthResponse sendMail(EmailAuthRequest request) throws MessagingException {

        if (userRepository.existsByEmail(request.email())) {

            return EmailAuthResponse.fail();
        }

        MimeMessage message = javaMailSender.createMimeMessage();

        message.setFrom(from); // 발신
        message.setRecipients(Message.RecipientType.TO, request.email()); // 수신

        // 인증번호 세팅
        int randomNum = (int) (Math.random() * 999999) + 1;
        String formattedRandomNum = String.format("%06d", randomNum);

        message.setSubject("[내꺼니꺼] 회원가입 인증번호 알림"); // 제목
        message.setText("내꺼니꺼 인증번호 : " + formattedRandomNum); // 내용

        javaMailSender.send(message); // 발송

        return EmailAuthResponse.success(formattedRandomNum);
    }
}

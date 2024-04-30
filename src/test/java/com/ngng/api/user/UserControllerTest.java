package com.ngng.api.user;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngng.api.penalty.dto.CreatePenaltyRequestDTO;
import com.ngng.api.user.dto.UserUpdateRequestDTO;
import com.ngng.api.user.dto.request.*;
import jakarta.validation.constraints.Email;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
@Transactional
@WithUserDetails(value = "jin@gmail.com" , setupBefore = TestExecutionEvent.TEST_EXECUTION)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    PlatformTransactionManager transactionManager;

    TransactionStatus status;

    @BeforeEach
    void beforeEach() {
        // 트랜잭션 시작
        status = transactionManager.getTransaction(new DefaultTransactionDefinition());
    }

    @AfterEach
    void afterEach() {
        // 트랜잭션 롤백
        transactionManager.rollback(status);
    }

    // admin readAll
    @Test
    void adminReadAll() throws Exception {
        mvc.perform(get("/admin/users")).andExpect(status().isOk());
    }

    // admin read
    @Test
    void adminRead() throws Exception {
        Long userId = 1L;
        mvc.perform(get("/admin/users/"+userId)).andExpect(status().isOk());
    }

    //  admin update
    @Test
    void adminUpdate() throws Exception {
        Long userId = 1L;
        String name = "김철수";
        String nickName = "짱구야놀자";
        String phoneNumber = "123123123";
        String email = "zzanggu@gmail.com";
        String password = "1234";
        String accountBank = "카카오뱅크";
        String accountNumber = "111122223333";
        String address = "떡잎마을";

        AdminUserUpdateRequest requestDto = new AdminUserUpdateRequest(
                userId,
                name,
                nickName,
                phoneNumber,
                email,
                password,
                accountBank,
                accountNumber,
                address
        );
        String jsonStr = objectMapper.writeValueAsString(requestDto);

        mvc.perform(put("/admin/users/" + userId)
                        .content(jsonStr)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    // admin delete
    @Test
    void adminDelete() throws Exception {
        Long userId = 1L;
        mvc.perform(delete("/admin/users/"+userId))
                .andExpect(status().isOk());
    }

    // accountConfirm
    @Test
    void accountConfirm() throws Exception {
        Long userId = 1L;
        String accountBank= "신한";
        String accountNumber = "11111111111";
        AccountConfirmRequest requestDto = new AccountConfirmRequest(
                userId,
                accountBank,
                accountNumber
        );
        String jsonStr = objectMapper.writeValueAsString(requestDto);

        mvc.perform(put("/confirm/account")
                        .content(jsonStr)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    // addressConfirm
    @Test
    void addressConfirm() throws Exception {
        Long id = 1L;
        String address = "떡잎마을";
        AddressConfirmRequest requestDto = new AddressConfirmRequest(
                id,
                address
        );
        String jsonStr = objectMapper.writeValueAsString(requestDto);

        mvc.perform(
                put("/confirm/address")
                        .content(jsonStr)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    // dropOut
    @Test
    void dropOut() throws Exception {
        Long id = 1L;
        DropOutRequest requestDto = new DropOutRequest(id);
        String jsonStr = objectMapper.writeValueAsString(requestDto);

        mvc.perform(delete("/dropout")
                .content(jsonStr)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    // sendMessage
    @Test
    void sendMessage() throws Exception {
        String name=  "신짱구";
        String phoneNumber = "01011112222";
        PhoneNumberAuthRequest requestDto = new PhoneNumberAuthRequest(
                name,
                phoneNumber
        );
        String jsonStr = objectMapper.writeValueAsString(requestDto);

        mvc.perform(post("/join/auth/phonenumber")
                .content(jsonStr)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // sendEmail
    @Test
    void sendEmail() throws Exception {
        String name = "신짱구";
        String email = "zzangu@gmail.com";
        EmailAuthRequest requestDto = new EmailAuthRequest(
                name,
                email
        );
        String jsonStr = objectMapper.writeValueAsString(requestDto);

        mvc.perform(post("/join/auth/email")
                        .content(jsonStr)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // join
    @Test
    void join() throws Exception {
        String name = "신짱구";
        String email = "zzangu@gmail.com";
        String password= "1234";
        String nickname = "철수야놀자아";
        String phoneNumber= "12341234123";
        JoinRequest requestDto = new JoinRequest(
                name,
                email,
                password,
                nickname,
                phoneNumber
        );
        String jsonStr = objectMapper.writeValueAsString(requestDto);

        mvc.perform(post("/join")
                        .content(jsonStr)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    // read
    @Test
    void read() throws Exception {
        mvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    // readMyPage
    @Test
    void readMyPage() throws Exception {
        mvc.perform(get("/users/mypage"))
                .andExpect(status().isOk());
    }

    // update
    @Test
    void update() throws Exception {
        String nickname = "철수야놀장";
        String address = "떡잎마을";
        UserUpdateRequestDTO requestDto = new UserUpdateRequestDTO(
                nickname,
                address
        );
        String jsonStr = objectMapper.writeValueAsString(requestDto);

        mvc.perform(put("/users")
                        .content(jsonStr)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }



}

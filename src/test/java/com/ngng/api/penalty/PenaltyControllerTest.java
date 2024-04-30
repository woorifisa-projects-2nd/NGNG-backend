package com.ngng.api.penalty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngng.api.penalty.dto.CreatePenaltyRequestDTO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
@Transactional
@WithUserDetails(value = "jin@gmail.com" , setupBefore = TestExecutionEvent.TEST_EXECUTION)
public class PenaltyControllerTest {

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



    // readAll 테스트
    @Test
    void readAll() throws Exception {
        mvc.perform(get("/admin/penalties")).andExpect(status().isOk());
    }

    // read 테스트
    @Test
    void read() throws Exception {
        String reportId = "33";
        mvc.perform(get("/admin/penalties/"+reportId)).andExpect(status().isOk());
    }

    // create 테스트
    @Test
    void create() throws Exception{
        LocalDateTime endDate = LocalDateTime.now().plusWeeks(1);
        Timestamp timestamp = Timestamp.valueOf(endDate);

        Long userId = 2L;
        Long reporterId = 1L;
        String reason = "신고 사유 : 욕설";
        Long penaltyLevelId = 1L;
        Long reportId = 34L;

        CreatePenaltyRequestDTO requestDto = new CreatePenaltyRequestDTO(timestamp, userId, reporterId, reason, penaltyLevelId, reportId);
        String jsonStr = objectMapper.writeValueAsString(requestDto);

        mvc.perform(post("/admin/penalties")
                        .content(jsonStr)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}

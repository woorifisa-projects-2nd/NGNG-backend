package com.ngng.api.report;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngng.api.report.report.dto.CreateReportRequestDTO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
@Transactional
@WithUserDetails(value = "jin@gmail.com" , setupBefore = TestExecutionEvent.TEST_EXECUTION)
public class ReportControllerTest {

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

    // readAll test
    @Test
    void readAll() throws Exception {
        mvc.perform(get("/admin/reports")).andExpect(status().isOk());
    }

    // read test
    @Test
    void read() throws Exception {
        Long reportId = 33L;
        mvc.perform(get("/admin/reports/"+reportId)).andExpect(status().isOk());
    }

    // update test
    @Test
    void update() throws  Exception {
        Long reportId = 33L;
        mvc.perform(patch("/admin/reports/"+reportId))
                .andExpect(status().isOk());
    }

    // delete test
    @Test
    void deleteTest() throws  Exception {
        Long reportId = 33L;
        mvc.perform(delete("/admin/reports/" + reportId))
                .andExpect(status().isOk());
    }

    // create test
    @Test
    void create() throws Exception {
        String reportContents = "신고내용";
        Long reportTypeId = 3L;
        Long reporterId = 2L;
        Long userId = 1L;
        Boolean isReport = false;
        Long productId = 7L;
        Long privateChatId = 1L;
        CreateReportRequestDTO requestDto = new CreateReportRequestDTO(
                reportContents,
                reportTypeId,
                reporterId,
                userId,
                isReport,
                productId,
                privateChatId
        );
        String jsonStr = objectMapper.writeValueAsString(requestDto);
        mvc.perform(post("/reports")
                .content(jsonStr)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
    }



}

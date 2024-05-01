package com.ngng.api.point;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngng.api.penalty.dto.CreatePenaltyRequestDTO;
import com.ngng.api.point.dto.CreateAddPointRequestDTO;
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
public class PointControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    PlatformTransactionManager transactionManager;

    TransactionStatus status;

    // read point test
    @Test
    void readPoint() throws Exception {
        mvc.perform(get("/points")).andExpect(status().isOk());
    }

    // readAllPointHistory test
    @Test
    void readAllPointHistory() throws Exception {
        mvc.perform(get("/points/all")).andExpect(status().isOk());
    }

    // addPoint test
    @Test
    void addPoint() throws Exception {

        Long addCost = 1000L;
        String type = "충전";

        CreateAddPointRequestDTO requestDto = new CreateAddPointRequestDTO(addCost, type);
        String jsonStr = objectMapper.writeValueAsString(requestDto);

        mvc.perform(post("/points")
                .content(jsonStr)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

}

package com.ngng.api.transaction.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngng.api.transaction.dto.CreateTransactionDetailsRequestDTO;
import com.ngng.api.transaction.dto.ReadTransactionDetailsDTO;
import com.ngng.api.transaction.dto.UpdateTransactionDetailsRequestDTO;
import com.ngng.api.transaction.service.TransactionDetailsService;
import com.ngng.api.transaction.service.TransactionRequestService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
@Transactional
@WithUserDetails(value = "jin@gmail.com" , setupBefore = TestExecutionEvent.TEST_EXECUTION)  // BeforeEach 실행전 인증 세션을 생성한다.
class TransactionControllerTest {
//    https://velog.io/@gale4739/Spring-Boot-Controller-test-with-JUnit5


    @Autowired
    private MockMvc mvc;

    @Autowired
    private TransactionDetailsService transactionDetailsService;

    @Autowired
    private TransactionRequestService transactionRequestService;

    @Autowired
    private ObjectMapper objectMapper;


    // ============================  트랜잭션 롤백
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
//==================================


    @Test
    void getTransactionById() throws Exception {
        String transctionId = "3";

//        @GetMapping("/{TransactionId}")
        mvc.perform(get("/transaction/"+transctionId))
                .andExpect(status().isOk());
    }

    @Test
    void getTransactionDetailsBySellerDTO() throws Exception{

        mvc.perform(get("/transaction/sell")).andExpect(status().isOk());

        mvc.perform(get("/transaction/sell").param("status","1"))
                .andExpect(status().isOk());


    }

    @Test
    void readTransactionDetailsByBuyerDTO()throws Exception {
//    @GetMapping("/buy")
            mvc.perform(get("/transaction/buy")).andExpect(status().isOk());
    }

    @Test
    void createTransactionDetails()throws Exception {
        Long productId = 6L;
        Long buyerId = 3L;
        Long price = 4000L;

        CreateTransactionDetailsRequestDTO req = new CreateTransactionDetailsRequestDTO(
                productId,
                buyerId,
                price
        );


        String jsonStr = objectMapper.writeValueAsString(req);

        mvc.perform(post("/transaction")
                .content(jsonStr)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void updateTransactionStatus()throws Exception {
//        @PutMapping("/{id}")
//        public ResponseEntity<ReadTransactionDetailsDTO> updateTransactionStatus(@PathVariable("id") Long transId, @RequestBody UpdateTransactionDetailsRequestDTO request){

        Long transacctionId = 3L;
        Long statusId = 3L;

        Map<String, Object> bodyMap = new HashMap<> ();
        bodyMap.put("statusId",statusId);

        String jsonStr = objectMapper.writeValueAsString(bodyMap);

        mvc.perform(put("/transaction/" + transacctionId)
                .content(jsonStr)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void readAllTransactionRequestByProductId() throws Exception{
         String productId = "12";

        mvc.perform(get("/transaction/request/"+productId))
                .andExpect(status().isOk());
    }

    @Test
    void createTransactionRequest()throws Exception {

        Long productId = 12L;
        Long sellerId = 2L;
        Long buyerId = 3L;
        Long price = 5000L;


        Map<String, Object> bodyMap = new HashMap<> ();
        bodyMap.put("productId",productId);
        bodyMap.put("sellerId",sellerId);
        bodyMap.put("buyerId",buyerId);
        bodyMap.put("price",price);

        String jsonStr = objectMapper.writeValueAsString(bodyMap);

        mvc.perform(post("/transaction/request")
                        .content(jsonStr)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updatedTransactionRequest()throws Exception {

        Long transactionRequestId = 9L;
        Boolean isAccepted = false;


        Map<String, Object> bodyMap = new HashMap<> ();
        bodyMap.put("transactionRequestId",transactionRequestId);
        bodyMap.put("isAccepted",isAccepted);


        String jsonStr = objectMapper.writeValueAsString(bodyMap);

        mvc.perform(put("/transaction/request")
                        .content(jsonStr)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
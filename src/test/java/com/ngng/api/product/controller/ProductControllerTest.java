package com.ngng.api.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngng.api.product.dto.request.TagRequestDTO;
import com.ngng.api.transaction.dto.CreateTransactionDetailsRequestDTO;
import com.ngng.api.transaction.service.TransactionDetailsService;
import com.ngng.api.transaction.service.TransactionRequestService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
@Transactional
@WithUserDetails(value = "jin@gmail.com" , setupBefore = TestExecutionEvent.TEST_EXECUTION)  // BeforeEach 실행전 인증 세션을 생성한다.
class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

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
    void create() throws Exception {
        String title = "code test";
        String content =" code test 입니다.";
        Long price = 400L;
        Boolean isEscrow = false;
        Boolean discountable = false;
        String purchaseAt = "2222";
        Boolean freeShipping = false;
        Long userId = 3L;
        Long statusId = 2L;
        Long categoryId = 4L;
        List<TagRequestDTO> tags = new ArrayList<>();
        tags.add(TagRequestDTO.builder().tagName("test1").build());
        tags.add(TagRequestDTO.builder().tagName("test2").build());

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("title",title);
        bodyMap.put("content",content);
        bodyMap.put("price",price);
        bodyMap.put("isEscrow",isEscrow);
        bodyMap.put("discountable",discountable);
        bodyMap.put("purchaseAt",purchaseAt);
        bodyMap.put("freeShipping",freeShipping);
        bodyMap.put("userId",userId);
        bodyMap.put("statusId",statusId);
        bodyMap.put("categoryId",categoryId);
        bodyMap.put("tags",tags);


        String jsonStr = objectMapper.writeValueAsString(bodyMap);


        mvc.perform(post("/products")
                        .content(jsonStr)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());


    }

    @Test
    void read()throws Exception {
        String productId = "3";

        mvc.perform(get("/products/"+productId)).andExpect(status().isOk());

    }

    @Test
    void updateRefresh() throws Exception{
//        @PostMapping("/refresh/{productId}")
        String productId = "3";

        mvc.perform(post("/products/refresh/"+productId)).andExpect(status().isOk());
    }

    @Test
    void fileUpload()throws Exception {
//        진짜 s3 올라기니 패스
    }

    @Test
    void readAll() throws Exception{
        mvc.perform(get("/products")).andExpect(status().isOk());
    }

    @Test
    void update() throws Exception{
//        @PutMapping(path = "/{productId}")

        String productId = "3";

        String title = "update test";
        String content =" update test 입니다.";
        Long price = 400L;
        Boolean isEscrow = false;
        Boolean discountable = false;
        String purchaseAt = "2222";
        Boolean freeShipping = false;
        Long userId = 3L;
        Long statusId = 2L;
        Long categoryId = 4L;
        List<TagRequestDTO> tags = new ArrayList<>();
        tags.add(TagRequestDTO.builder().tagName("test1").build());
        tags.add(TagRequestDTO.builder().tagName("test2").build());

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("title",title);
        bodyMap.put("content",content);
        bodyMap.put("price",price);
        bodyMap.put("isEscrow",isEscrow);
        bodyMap.put("discountable",discountable);
        bodyMap.put("purchaseAt",purchaseAt);
        bodyMap.put("freeShipping",freeShipping);
        bodyMap.put("userId",userId);
        bodyMap.put("statusId",statusId);
        bodyMap.put("categoryId",categoryId);
        bodyMap.put("tags",tags);


        String jsonStr = objectMapper.writeValueAsString(bodyMap);


        mvc.perform(put("/products/" + productId)
                        .content(jsonStr)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateVisibility() throws Exception{
//        @DeleteMapping(path = "/{productId}")
        String productId = "16";

        mvc.perform(delete("/products/"+productId)).andExpect(status().isOk());
    }
}
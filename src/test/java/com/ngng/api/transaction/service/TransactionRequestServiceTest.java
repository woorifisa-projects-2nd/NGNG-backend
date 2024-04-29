package com.ngng.api.transaction.service;

import com.ngng.api.transaction.dto.CreateTransactionRequestDTO;
import com.ngng.api.transaction.dto.TransactionRequestDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class TransactionRequestServiceTest {

    @Autowired
    TransactionRequestService transactionRequestService;

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

    private Long createRequestId ;

    @Test
    @DisplayName("거래 요청 생성하기")
    void create() {
        Long productId = 6L;
        Long sellerId = 2L;
        Long buyerId = 3L;
        Long price = 400L;

        CreateTransactionRequestDTO request = new CreateTransactionRequestDTO(productId,sellerId,buyerId,price);
        Long res = transactionRequestService.create(request);
        Long res2 = transactionRequestService.create(request);

//        생성됨
        assert(res > 0);
        createRequestId=res;

//        이미존재함
        assertEquals(res2,-1);

    }

    @Test
    void update() {

        Long requestId = 9L;

        Long res = transactionRequestService.update(requestId,true);
        Long res2 = transactionRequestService.update(requestId,false);
        Long non = transactionRequestService.update(2L,false);

        assertEquals(res,requestId);
        assertEquals(res2,requestId);
        assertEquals(non,-1);

    }

    @Test
    @DisplayName("12번 상품에 대한 요청 개수 는 2개 이다.")
    void readAll() {
        Long productId = 12L;

        List<TransactionRequestDTO> res = transactionRequestService.readAll(productId);

        assertEquals(res.size(),2);
    }
}
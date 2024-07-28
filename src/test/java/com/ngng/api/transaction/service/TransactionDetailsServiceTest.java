package com.ngng.api.transaction.service;

import com.ngng.api.transaction.dto.CreateTransactionDetailsRequestDTO;
import com.ngng.api.transaction.dto.ReadTransactionDetailsDTO;
import com.ngng.api.transaction.dto.UpdateTransactionDetailsRequestDTO;
import com.ngng.api.transaction.repository.TransactionDetailsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



//@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class TransactionDetailsServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    TransactionDetailsRepository transactionDetailsRepository;

    @Autowired
    TransactionDetailsService transactionDetailsService;



    @Test
    @DisplayName("거래 내역을 생성한다. " +
            "문제점 거래 내역 id로 검사하면 auto increment 값이 1씩 늘어나므로 확인하기 골치아픔")
    void create() {
        Long productId = 6L;
        Long buyerId =3L;
        Long price = 400L;

//        int newTransactionId = 10;

        // TODO 기존의 거래내역 최대 id 불러오기?

        CreateTransactionDetailsRequestDTO req =
                new CreateTransactionDetailsRequestDTO(productId,buyerId,price);

//        ReadTransactionDetailsDTO res = transactionDetailsService.create(req);


//        assertEquals(res.getId(),newTransactionId);

    }

    @Test
    @WithUserDetails(value = "jin@gmail.com" , setupBefore = TestExecutionEvent.TEST_EXECUTION)  // BeforeEach 실행전 인증 세션을 생성한다.
    @DisplayName("거래내역 번호3 의 상태 값을 2으로 변경")
    void updateTransactionStatus() {
//      given
        Long TransactionId = 3L;
        Long updateStatusId = 2L;

//      when
        UpdateTransactionDetailsRequestDTO req = new UpdateTransactionDetailsRequestDTO(updateStatusId);
        ReadTransactionDetailsDTO res =  transactionDetailsService.updateTransactionStatus(TransactionId,req);

        ReadTransactionDetailsDTO dataGetById= transactionDetailsService.readTransactionDetailsById(TransactionId);

//      then
        assertEquals(res.getStatus().getId(),updateStatusId);
        assertEquals(dataGetById.getStatus().getId(),updateStatusId);

    }

    @Test
    @DisplayName("거래내역 ID 로 거래 내역 상세 정보를 얻을수 있다.")
    void readTransactionDetailsById() {
       Long TransactionId = 3L;

       ReadTransactionDetailsDTO data= transactionDetailsService.readTransactionDetailsById(TransactionId);

       assertEquals(data.getId(),3);

    }

    @Test
    void readAllByConsumerId() {
        Long consumerId = 3L;
        int size = 2;

        List<ReadTransactionDetailsDTO> data = transactionDetailsService.readAllByConsumerId(consumerId);


        assertThrows(AssertionFailedError.class, () -> assertEquals(data.size(),0));
        assertEquals(data.size(),size);
    }



    @WithUserDetails(value = "jin@gmail.com" , setupBefore = TestExecutionEvent.TEST_EXECUTION)  // BeforeEach 실행전 인증 세션을 생성한다.
    @Test
    @DisplayName("jin@gamil.com 로 로그인한 유저의 구매 내역 개수 확인 , 개수 : 2")
    void readAllByConsumer() {
        int size = 2;

        List<ReadTransactionDetailsDTO> data = transactionDetailsService.readAllByConsumer();


        assertThrows(AssertionFailedError.class, () -> assertEquals(data.size(),0));
        assertEquals(data.size(),size);
    }


    @WithUserDetails(value = "jin@gmail.com" , setupBefore = TestExecutionEvent.TEST_EXECUTION)  // BeforeEach 실행전 인증 세션을 생성한다.
    @DisplayName("jin@gamil.com 로 로그인한 유저의 판매 내역 개수 확인 , 개수 : 0")
    @Test
    void readAllBySeller() {
        int size = 0;

        List<ReadTransactionDetailsDTO> data = transactionDetailsService.readAllBySeller();

        assertEquals(data.size(),size);
    }

    @Test
    void readAllBySellerId() {
        Long sellerId = 2L;
        int size = 4;

        List<ReadTransactionDetailsDTO> data = transactionDetailsService.readAllBySellerId(sellerId);


        assertThrows(AssertionFailedError.class, () -> assertEquals(data.size(),0));
        assertEquals(data.size(),size);
    }

    @Test
    @WithUserDetails(value = "water@gmail.com" , setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("water@gmail.com 로 로그인한 유저의 판매 완료한 물품 조회")
    void readAllBySellerIdAndStatusId() {
        Long statusId = 5L;

        int size = 1;

        List<ReadTransactionDetailsDTO> data = transactionDetailsService.readAllBySellerIdAndStatusId(statusId);

        assertEquals(data.size(),size);
    }


}
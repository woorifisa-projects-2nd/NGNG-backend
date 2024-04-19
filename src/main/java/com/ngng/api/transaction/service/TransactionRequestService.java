package com.ngng.api.transaction.service;

import com.ngng.api.product.dto.request.UpdateProductRequestDTO;
import com.ngng.api.product.entity.Product;
import com.ngng.api.product.repository.ProductRepository;
import com.ngng.api.product.service.ProductService;
import com.ngng.api.transaction.dto.CreateTransactionDetailsRequestDTO;
import com.ngng.api.transaction.dto.CreateTransactionRequestDTO;
import com.ngng.api.transaction.dto.TransactionRequestDTO;
import com.ngng.api.transaction.entity.TransactionRequest;
import com.ngng.api.transaction.repository.TransactionRequestRepository;
import com.ngng.api.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransactionRequestService {
    private final TransactionRequestRepository transactionRequestRepository;
    private final ProductService productService;
    private final TransactionDetailsService transactionDetailsService;

    public Long create(CreateTransactionRequestDTO request){
        return transactionRequestRepository.save(
                TransactionRequest.builder()
                        .product(Product.builder().productId(request.getProductId()).build())
                        .buyer(User.builder().userId(request.getBuyerId()).build())
                        .seller(User.builder().userId(request.getSellerId()).build())
                        .price(request.getPrice())
                        .build()
        ).getTransactionRequestId();
    }

    @Transactional
    public Long update( Long requestId, Boolean isAccepted){
        TransactionRequest target = transactionRequestRepository.findById(requestId).orElse(null);
        if(target == null){
            return -1L;
        }else{
            if(isAccepted){
                // 1. 요청 수락일 경우
                target.setIsAccepted(true);

                // 2. 상품 forSale false로 바꾸기
                Long productId = target.getProduct().getProductId();
                productService.updateForSale(productId, false);

                // 3. transactionDetails 추가
                // TODO 배송지 어떤 값으로 추가할지? 구매자의 address값 넣을지 아님 수정하게 할지
                transactionDetailsService.create(new CreateTransactionDetailsRequestDTO(productId, target.getBuyer().getUserId(), target.getPrice()));
                List<TransactionRequest> others = transactionRequestRepository.findAllByProductProductId(productId);

                // 4. 나머지는 거부 처리
                others.forEach(request -> {
                    if(!Objects.equals(request.getTransactionRequestId(), requestId)){
                        request.setIsAccepted(false);
                    }
                });

                return target.getTransactionRequestId();
            }else{ // 요청 거절일 경우
                target.setIsAccepted(false);
                return target.getTransactionRequestId();
            }

        }
    }

    public List<TransactionRequestDTO> readAll(Long productId){
        return transactionRequestRepository.findAllByProductProductId(productId).stream()
                .map(TransactionRequestDTO::new).toList();
    }
}

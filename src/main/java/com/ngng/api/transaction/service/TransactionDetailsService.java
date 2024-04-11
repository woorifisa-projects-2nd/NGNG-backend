package com.ngng.api.transaction.service;

import com.ngng.api.product.entity.Product;
import com.ngng.api.product.repository.ProductRepository;
import com.ngng.api.transaction.dto.CreateTransactionDetailsRequestDTO;
import com.ngng.api.transaction.dto.ReadTransactionDetailsDTO;
import com.ngng.api.transaction.dto.UpdateTransactionDetailsRequestDTO;
import com.ngng.api.transaction.entity.TransactionDetails;
import com.ngng.api.transaction.entity.TransactionStatus;
import com.ngng.api.transaction.repository.TransactionDetailsRepository;
import com.ngng.api.transaction.repository.TransactionStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionDetailsService extends Exception {

    private final TransactionDetailsRepository transactionDetailsRepository;
    private final TransactionStatusRepository transactionStatusRepository;
//    private final ProductService productService;
    private final ProductRepository productRepository;

//    @Transactional(readOnly = true)
    public ReadTransactionDetailsDTO readTransactionDetailsById(Long TransactionId) {
       TransactionDetails transactionDetails = transactionDetailsRepository.findById(TransactionId).orElse(null);

       if(transactionDetails == null){
           throw new ResponseStatusException(HttpStatus.NOT_FOUND,"거래 내역을 찾을수 업습니다.");
       }

       return new ReadTransactionDetailsDTO().from(transactionDetails);

    }

    public List<ReadTransactionDetailsDTO> readAllByConsumerId(Long id){
        List<TransactionDetails> transactionDetails = transactionDetailsRepository.findALlByConsumerId(id);

        return  transactionDetails.stream()
                .map(item -> new ReadTransactionDetailsDTO().from(item))
                .collect(Collectors.toList());
    }

    public List<ReadTransactionDetailsDTO> readAllBySellerId(Long id){
        List<TransactionDetails> transactionDetails = transactionDetailsRepository.findAllBySellerId(id);

        return  transactionDetails.stream()
                .map(item -> new ReadTransactionDetailsDTO().from(item))
                .collect(Collectors.toList());
    }


    public List<ReadTransactionDetailsDTO> readAllBySellerIdAndStatusId(Long id,Long status){
        List<TransactionDetails> transactionDetails = transactionDetailsRepository.findAllBySellerIdFilterStatus(id,status);

        return  transactionDetails.stream()
                .map(item -> new ReadTransactionDetailsDTO().from(item))
                .collect(Collectors.toList());
    }

    public TransactionDetails create(CreateTransactionDetailsRequestDTO request){
        Product product = productRepository.findById(request.getProduct().getProductId()).orElse(null);


        if(product == null){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND,"상품을 찾을수 없습니다.");
        }

        TransactionDetails transactionDetails = TransactionDetails.builder()
                .product(product)
                .consumer(request.getConsumer())
                .seller(product.getUser())
                .address(request.getAddress())
                .status(new TransactionStatus(1L))
                .build();

        return transactionDetailsRepository.save(transactionDetails);
    }

    @Transactional()
    public ReadTransactionDetailsDTO updateTransactionStatus(Long transId, UpdateTransactionDetailsRequestDTO request){

        TransactionDetails transactionDetails;
        try{
//            System.out.println(transId+"-"+request.getStatus().getId());
//            int  ds = transactionDetailsRepository.updateTransactionStatus(transId,request.getStatus().getId());

//            System.out.println(ds);


            transactionDetails = transactionDetailsRepository.findById(transId).orElseThrow();
//            TransactionStatus status = transactionStatusRepository.findById(request.getStatus().getId()).orElseThrow();
            TransactionStatus status = transactionStatusRepository.findById(request.getStatus().getId()).orElseThrow();


//            transactionDetails.setUpdatedAt(null);
            transactionDetails.setStatus(status);


        } catch (Exception e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"잘못된 쿼리 요청 입니다.");
        }


//        transactionDetailsRepository.save(transactionDetails);

        return new ReadTransactionDetailsDTO().from(transactionDetails);


    }
}

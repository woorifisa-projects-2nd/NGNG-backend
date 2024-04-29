package com.ngng.api.transaction.controller;

import com.ngng.api.transaction.dto.*;
import com.ngng.api.transaction.service.TransactionDetailsService;
import com.ngng.api.transaction.service.TransactionRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
@Slf4j(topic = "transaction-log")
public class TransactionController {

    private final TransactionDetailsService transactionDetailsService;
    private final TransactionRequestService transactionRequestService;

    @GetMapping("/{TransactionId}")
    public ResponseEntity<ReadTransactionDetailsDTO> getTransactionById(@PathVariable("TransactionId") Long transactionId) {
        return ResponseEntity.ok().body(
                transactionDetailsService.readTransactionDetailsById(transactionId)
        );
    }

    @GetMapping("/sell")
    public ResponseEntity<List<ReadTransactionDetailsDTO>> getTransactionDetailsBySellerDTO(
            @RequestParam(name = "status", required = false) Long status
    ){
        System.out.println(status);
        if(status != null){
            return ResponseEntity.ok().body(
                    transactionDetailsService.readAllBySellerIdAndStatusId(status)
            );
        }

        return ResponseEntity.ok().body(
                transactionDetailsService.readAllBySeller()
        );
    }

    @GetMapping("/buy")
    public ResponseEntity<List<ReadTransactionDetailsDTO>> readTransactionDetailsByBuyerDTO(){
        return ResponseEntity.ok().body(
                transactionDetailsService.readAllByConsumer()
        );
    }

    @PostMapping
    public ResponseEntity<ReadTransactionDetailsDTO> createTransactionDetails(@RequestBody CreateTransactionDetailsRequestDTO request){

        ReadTransactionDetailsDTO response = transactionDetailsService.create(request);

        URI location = URI.create("/transaction/" + response.getId());

        return ResponseEntity.created(location).body(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ReadTransactionDetailsDTO> updateTransactionStatus(@PathVariable("id") Long transId, @RequestBody UpdateTransactionDetailsRequestDTO request){
        return ResponseEntity.ok().body(transactionDetailsService.updateTransactionStatus(transId,request));
    }

    @GetMapping("/request/{productId}")
    public ResponseEntity<List<TransactionRequestDTO>> readAllTransactionRequestByProductId(@PathVariable Long productId){
        return ResponseEntity.ok().body(transactionRequestService.readAll(productId));
    }

    @PostMapping("/request")
    public ResponseEntity<Long> createTransactionRequest(@RequestBody CreateTransactionRequestDTO request){
        Long requestId = transactionRequestService.create(request);
        log.info("Success Create TransactionRequest id: {}",requestId);
        return ResponseEntity.ok().body(requestId);

    }

    @PutMapping("/request")
    public ResponseEntity<Long> updatedTransactionRequest(@RequestBody UpdateTransactionRequestDTO request){
        Long res = transactionRequestService.update(request.getTransactionRequestId(), request.getIsAccepted());
        if(res > 0) {
            if(request.getIsAccepted()){
                log.info("Success Accept TransactionRequest id: {}",request.getTransactionRequestId());
            }else{
                log.info("Success Decline TransactionRequest id: {}",request.getTransactionRequestId());
            }

            return ResponseEntity.ok()
                    .body(res);
        }
        return ResponseEntity.notFound().build();
    }

}

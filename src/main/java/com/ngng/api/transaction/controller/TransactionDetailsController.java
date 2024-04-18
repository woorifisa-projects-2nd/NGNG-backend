package com.ngng.api.transaction.controller;

import com.ngng.api.transaction.entity.TransactionDetails;
import com.ngng.api.transaction.service.TransactionDetailsService;
import com.ngng.api.transaction.dto.CreateTransactionDetailsRequestDTO;
import com.ngng.api.transaction.dto.ReadTransactionDetailsDTO;
import com.ngng.api.transaction.dto.UpdateTransactionDetailsRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionDetailsController {

    private final TransactionDetailsService transactionDetailsService;

    @GetMapping("/{TransactionId}")
    public ResponseEntity<ReadTransactionDetailsDTO> getTransactionById(@PathVariable("TransactionId") Long transactionId) {
        return ResponseEntity.ok().body(
                transactionDetailsService.readTransactionDetailsById(transactionId)
        );
    }

    @GetMapping("/sell/{userId}")
    public ResponseEntity<List<ReadTransactionDetailsDTO>> getTransactionDetailsBySellerDTO(
            @PathVariable("userId") Long userId,
            @RequestParam(name = "status", required = false) Long status
    ){
        System.out.println(status);
        if(status != null){
            return ResponseEntity.ok().body(
                    transactionDetailsService.readAllBySellerIdAndStatusId(userId,status)
            );
        }

        return ResponseEntity.ok().body(
                transactionDetailsService.readAllBySellerId(userId)
        );
    }

    @GetMapping("/buy/{userId}")
    public ResponseEntity<List<ReadTransactionDetailsDTO>> readTransactionDetailsByBuyerDTO(@PathVariable("userId") Long userId){
        return ResponseEntity.ok().body(
                transactionDetailsService.readAllByConsumerId(userId)
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

}

package com.ngng.api.transaction.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionDetailsRequestDTO {
    private Long productId;
    private Long buyerId;
    private Long price;
}

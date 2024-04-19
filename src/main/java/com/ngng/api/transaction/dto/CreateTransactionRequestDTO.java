package com.ngng.api.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTransactionRequestDTO {
    Long productId;
    Long sellerId;
    Long buyerId;
    Long price;
}

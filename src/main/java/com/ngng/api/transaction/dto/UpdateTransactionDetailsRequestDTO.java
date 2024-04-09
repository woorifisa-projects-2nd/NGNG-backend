package com.ngng.api.transaction.dto;

import com.ngng.api.transaction.model.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTransactionDetailsRequestDTO {
    private TransactionStatus status;
}

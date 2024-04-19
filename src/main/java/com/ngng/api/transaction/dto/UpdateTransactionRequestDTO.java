package com.ngng.api.transaction.dto;

import lombok.Getter;

@Getter
public class UpdateTransactionRequestDTO {
    Long transactionRequestId;
    Boolean isAccepted;
}

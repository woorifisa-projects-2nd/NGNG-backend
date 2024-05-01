package com.ngng.api.transaction.dto;

import com.ngng.api.product.product.dto.UserDTO;
import com.ngng.api.product.product.dto.response.ProductDTO;
import com.ngng.api.transaction.entity.TransactionRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequestDTO {
    Long transactionRequestId;
    ProductDTO product;
    UserDTO buyer;
    UserDTO seller;
    Boolean isAccepted;
    Timestamp createdAt;
    Timestamp updatedAt;
    Long price;


    public TransactionRequestDTO (TransactionRequest data) {
        this.transactionRequestId = data.getTransactionRequestId();
        this.product = ProductDTO.builder().productId(data.getProduct().getProductId()).build();
        this.buyer = new UserDTO(data.getBuyer());
        this.seller = new UserDTO(data.getSeller());
        this.isAccepted = data.getIsAccepted();
        this.createdAt = data.getCreatedAt();
        this.updatedAt = data.getUpdatedAt();
        this.price = data.getPrice();
    }

}

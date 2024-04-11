package com.ngng.api.transaction.dto;



import com.ngng.api.product.entity.Product;
import com.ngng.api.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionDetailsRequestDTO {
    private Product product;
    private User consumer;
    private String address;


}

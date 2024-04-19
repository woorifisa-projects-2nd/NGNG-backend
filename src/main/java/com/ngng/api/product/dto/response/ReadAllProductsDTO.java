package com.ngng.api.product.dto.response;

import com.ngng.api.product.dto.UserDTO;
import lombok.*;

import java.sql.Timestamp;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadAllProductsDTO {
    Long id;
    String title;
    String content;
    Long price;
    Boolean isEscrow;
    Boolean discountable;
    String purchaseAt;
    Boolean forSale;
    Timestamp createdAt;
    Timestamp updatedAt;
    Boolean visible;
    Boolean freeShipping;
    Timestamp refreshedAt;
    UserDTO user;
    ReadProductStatusResponseDTO status;
    ReadProductCategoryResponseDTO category;
}

package com.ngng.api.product.product.dto.request;

import lombok.*;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CreateProductRequestDTO {
    String title;
    String content;
    Long price;
    Boolean isEscrow;
    Boolean discountable;
    String purchaseAt;
    Boolean freeShipping;
    Long userId;
    Long statusId;
    Long categoryId;
    List<TagRequestDTO> tags;
}

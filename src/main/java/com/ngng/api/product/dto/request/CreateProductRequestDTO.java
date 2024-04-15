package com.ngng.api.product.dto.request;

import com.ngng.api.productImage.dto.request.CreateImageRequestDTO;
import com.ngng.api.productTag.dto.request.CreateProductTagRequestDTO;
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
    List<CreateProductTagRequestDTO> tags;
//    List<CreateImageRequestDTO> images;
//    String thumbnailUrl;
}

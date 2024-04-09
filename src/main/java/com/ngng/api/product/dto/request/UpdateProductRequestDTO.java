package com.ngng.api.product.dto.request;

import com.ngng.api.productImage.dto.request.UpdateImageRequestDTO;
import com.ngng.api.productTag.dto.request.UpdateProductTagRequestDTO;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductRequestDTO {
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
    Timestamp refreshedAt;
    Boolean freeShipping;
    Long userId;
    Long statusId;
    Long categoryId;
    String thumbnailUrl;
    List<UpdateProductTagRequestDTO> tags;
    List<UpdateImageRequestDTO> images;
}

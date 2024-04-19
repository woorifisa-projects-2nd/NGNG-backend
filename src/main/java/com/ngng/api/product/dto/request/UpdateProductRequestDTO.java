package com.ngng.api.product.dto.request;

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
    Boolean visible;
    Timestamp refreshedAt;
    Boolean freeShipping;
    Long statusId;
    Long categoryId;
    List<TagRequestDTO> tags;
}

package com.ngng.api.product.productImage.dto.request;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteImageRequestDTO {
    String imageURL;
}

package com.ngng.api.product.dto.response;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadProductCategoryResponseDTO {
    Long id;
    String name;
}

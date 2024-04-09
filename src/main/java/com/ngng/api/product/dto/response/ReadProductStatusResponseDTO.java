package com.ngng.api.product.dto.response;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadProductStatusResponseDTO {
    Long id;
    String name;
}

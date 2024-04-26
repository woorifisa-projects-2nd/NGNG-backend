package com.ngng.api.product.dto.response;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadProductImageResponseDTO {
    Long id;
    String imageURL;
    Boolean visible;
    String contentType;
}

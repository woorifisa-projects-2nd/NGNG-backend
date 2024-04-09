package com.ngng.api.productImage.dto.response;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadProductImageResponseDTO {
    Long id;
    String imageURL;
}

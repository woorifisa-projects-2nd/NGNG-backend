package com.ngng.api.productImage.dto.request;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateImageRequestDTO {
    String imageURL;
}

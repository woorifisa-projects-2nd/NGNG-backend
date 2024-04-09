package com.ngng.api.product.dto.response;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadProductUserResponseDTO {
    Long id;
    String name;
    String nickname;
}

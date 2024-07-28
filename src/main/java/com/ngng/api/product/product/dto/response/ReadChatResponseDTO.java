package com.ngng.api.product.product.dto.response;

import lombok.*;

import java.sql.Timestamp;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadChatResponseDTO {
    private Long id;
    private String message;
    private Long userId;
    private String userNickName;
    private Timestamp createdAt;
}

package com.ngng.api.product.dto.response;

import com.ngng.api.chat.ReadChatResponseDTO.ReadChatResponseDTO;
import com.ngng.api.productImage.dto.response.ReadProductImageResponseDTO;
import com.ngng.api.productTag.dto.response.ReadProductTagResponseDTO;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadProductResponseDTO {
    Long id;
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
    Boolean freeShipping;
    Boolean available;
    Timestamp refreshedAt;
    ReadProductUserResponseDTO user;
    ReadProductStatusResponseDTO status;
    ReadProductCategoryResponseDTO category;
    List<ReadProductTagResponseDTO> tags;
    List<ReadProductImageResponseDTO> images;
    List<ReadChatResponseDTO> chats;
}

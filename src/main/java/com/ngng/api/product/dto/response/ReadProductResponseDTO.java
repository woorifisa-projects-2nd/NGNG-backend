package com.ngng.api.product.dto.response;

import com.ngng.api.chat.ReadChatResponseDTO.ReadChatResponseDTO;
import com.ngng.api.product.entity.Product;
import com.ngng.api.productImage.dto.response.ReadProductImageResponseDTO;
import com.ngng.api.productTag.dto.response.ReadProductTagResponseDTO;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

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


    public ReadProductResponseDTO from(Product product){
        return ReadProductResponseDTO
                .builder()
                .id(product.getProductId())
                .content(product.getContent())
                .createdAt(product.getCreatedAt())
                .discountable(product.getDiscountable())
                .forSale(product.getForSale())
                .freeShipping(product.getFreeShipping())
                .images(product.getImages()
                        .stream().map(image -> ReadProductImageResponseDTO
                                .builder()
                                .id(image.getProductImageId())
                                .imageURL(image.getImageUrl())
                                .build())
                        .collect(Collectors.toList()))
                .title(product.getTitle())
                .isEscrow(product.getIsEscrow())
                .price(product.getPrice())
                .visible(product.getVisible())
                .updatedAt(product.getUpdatedAt())
                .refreshedAt(product.getRefreshedAt())
                .isEscrow(product.getIsEscrow())
                .purchaseAt(product.getPurchaseAt())
                .available(product.getAvailable())
                .tags(product.getTags()
                        .stream().map(tag -> ReadProductTagResponseDTO
                                .builder()
                                .tagName(tag.getTag())
                                .build())
                        .collect(Collectors.toList())
                )
                .status(ReadProductStatusResponseDTO.builder()
                        .id(product.getStatus().getStatusId())
                        .name(product.getStatus().getStatusName())
                        .build()
                )
                .user(ReadProductUserResponseDTO.builder()
                        .id(product.getUser().getUserId())
                        .name(product.getUser().getName())
                        .nickname(product.getUser().getNickname())
                        .build())
                .category(ReadProductCategoryResponseDTO.builder()
                        .id(product.getCategory().getCategoryId())
                        .name(product.getCategory().getCategoryName())
                        .build())
                .chats(chats)
                .build();

    }
}

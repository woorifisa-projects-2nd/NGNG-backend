package com.ngng.api.product.product.dto.response;

import com.ngng.api.product.product.dto.UserDTO;
import com.ngng.api.product.product.entity.Product;
import com.ngng.api.product.thumbnail.dto.ThumbnailDTO;
import com.ngng.api.transaction.dto.ReadTransactionDetailsDTO;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadProductMypageResponseDTO {
    private Long id;
    private String title;
    private String content;
    private Long price;
    private Boolean isEscrow;
    private Boolean discountable;
    private String purchaseAt;
    private Boolean forSale;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Boolean visible;
    private Boolean freeShipping;
    private Boolean available;
    private Timestamp refreshedAt;
    private UserDTO user;
    private ReadProductStatusResponseDTO status;
    private ReadProductCategoryResponseDTO category;
    private List<TagResponseDTO> tags;
    private List<ReadProductImageResponseDTO> images;
    private ThumbnailDTO thumbnail;
    private ReadTransactionDetailsDTO transactionDetails;



    public ReadProductMypageResponseDTO from(Product product){
        return ReadProductMypageResponseDTO
                .builder()
                .id(product.getProductId())
                .content(product.getContent())
                .createdAt(product.getCreatedAt())
                .discountable(product.getDiscountable())
                .forSale(product.getForSale())
                .freeShipping(product.getFreeShipping())
                .thumbnail(ThumbnailDTO.
                        builder()
                        .id(product.getThumbnail().getThumbnailId())
                        .thumbnailURL(product.getThumbnail().getThumbnailUrl())
                        .build())
                .images(product.getProductImages()
                        .stream().map(image -> ReadProductImageResponseDTO
                                .builder()
                                .id(image.getProductImageId())
                                .imageURL(image.getImageUrl())
                                .visible(image.getVisible())
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

                .tags(product.getTags()
                        .stream().map(tag -> TagResponseDTO
                                .builder()
                                .tagName(tag.getTagName())
                                .build())
                        .collect(Collectors.toList())
                )
                .status(ReadProductStatusResponseDTO.builder()
                        .id(product.getStatus().getStatusId())
                        .name(product.getStatus().getStatusName())
                        .build()
                )
                .user(new UserDTO(product.getUser()))
                .category(ReadProductCategoryResponseDTO.builder()
                        .id(product.getCategory().getCategoryId())
                        .name(product.getCategory().getCategoryName())
                        .build())
                .transactionDetails(new ReadTransactionDetailsDTO().from(product.getTransactionDetails()))
                .build();

    }
}

package com.ngng.api.transaction.dto;


import com.ngng.api.product.dto.response.ReadProductImageResponseDTO;
import com.ngng.api.product.dto.response.ReadProductStatusResponseDTO;
import com.ngng.api.product.dto.response.TagResponseDTO;
import com.ngng.api.product.entity.Product;
import com.ngng.api.thumbnail.dto.ThumbnailDTO;
import com.ngng.api.transaction.entity.TransactionDetails;
import com.ngng.api.transaction.entity.TransactionStatus;

import com.ngng.api.user.entity.User;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReadTransactionDetailsDTO {

    private Long id;
    private String address;
    private DetailsProductDTO product;
    private DetailsUserDTO consumer;
    private DetailsUserDTO seller;
    private TransactionStatus status;
    private Long price;

    public ReadTransactionDetailsDTO from(TransactionDetails details){
        if(details == null) return ReadTransactionDetailsDTO.builder().build();

        return ReadTransactionDetailsDTO.builder()
                .id(details.getId())
                .address(details.getAddress())
                .status(details.getStatus())
                .product(new DetailsProductDTO().from(details.getProduct()))
                .consumer(new DetailsUserDTO().from(details.getConsumer()))
                .seller(new DetailsUserDTO().from(details.getSeller()))
                .build();
    }


    @Builder
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class DetailsProductDTO{

        private Long productId;
        private String title;
        private Long price;
        private Boolean isEscrow;
        private String purchaseAt;
        private Boolean visible;
        private Boolean freeShipping;
        private ReadProductStatusResponseDTO status;
        private String category;
        private List<TagResponseDTO> tags;
        private List<ReadProductImageResponseDTO> images;
        private ThumbnailDTO thumbnail;

        public DetailsProductDTO from(Product product){
            return DetailsProductDTO.builder()
                    .productId(product.getProductId())
                    .title(product.getTitle())
                    .price(product.getPrice())
                    .isEscrow(product.getIsEscrow())
                    .purchaseAt(product.getPurchaseAt())
                    .visible(product.getVisible())
                    .freeShipping(product.getFreeShipping())
                    .category(product.getCategory().getCategoryName())
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
                                    .build())
                            .collect(Collectors.toList()))
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
                    .build();
        }


    }

    @Builder
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class DetailsUserDTO{
        private String name;
        private String phoneNumber;


        public DetailsUserDTO from(User user){
            return DetailsUserDTO.builder()
                    .name(user.getName())
                    .phoneNumber(user.getPhoneNumber())

                    .build();
        }


    }
}

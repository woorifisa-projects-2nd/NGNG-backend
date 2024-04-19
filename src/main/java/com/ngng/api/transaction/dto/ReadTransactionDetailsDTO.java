package com.ngng.api.transaction.dto;


import com.ngng.api.product.entity.Product;
import com.ngng.api.transaction.entity.TransactionDetails;
import com.ngng.api.transaction.entity.TransactionStatus;

import com.ngng.api.user.entity.User;
import lombok.*;

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
        private String status;
        private String category;


        public DetailsProductDTO from(Product product){
            return DetailsProductDTO.builder()
                    .productId(product.getProductId())
                    .title(product.getTitle())
                    .price(product.getPrice())
                    .isEscrow(product.getIsEscrow())
                    .purchaseAt(product.getPurchaseAt())
                    .visible(product.getVisible())
                    .freeShipping(product.getFreeShipping())
                    .status(product.getStatus().getStatusName())
                    .category(product.getCategory().getCategoryName())
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

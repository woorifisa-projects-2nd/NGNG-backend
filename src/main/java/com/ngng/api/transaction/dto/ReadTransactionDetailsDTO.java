package com.ngng.api.transaction.dto;

import com.ngng.api.product.model.*;
import com.ngng.api.transaction.model.TransactionDetails;
import com.ngng.api.transaction.model.TransactionStatus;
import com.ngng.api.user.model.User;
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
                    .status(product.getStatus().getName())
                    .category(product.getCategory().getName())
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

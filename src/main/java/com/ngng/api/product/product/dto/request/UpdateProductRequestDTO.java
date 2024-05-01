package com.ngng.api.product.product.dto.request;

import com.ngng.api.product.product.entity.Product;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductRequestDTO {
    String title;
    String content;
    Long price;
    Boolean isEscrow;
    Boolean discountable;
    String purchaseAt;
    Boolean forSale;
    Boolean visible;
    Boolean freeShipping;
    Long statusId;
    Long categoryId;
    List<TagRequestDTO> tags;

    public UpdateProductRequestDTO(Product product){
        this.title = product.getTitle();
        this.content =product.getContent();
        this.price = product.getPrice();
        this.isEscrow = product.getIsEscrow();
        this.discountable = product.getDiscountable();
        this.purchaseAt = product.getPurchaseAt();
        this.forSale = product.getForSale();
        this.visible = product.getVisible();
        this.freeShipping = product.getFreeShipping();
        this.statusId = product.getStatus().getStatusId();
        this.categoryId = product.getCategory().getCategoryId();
        this.tags = product.getTags().stream().map(tag->
            TagRequestDTO.builder().tagName(tag.getTagName()).build()
        ).toList();
    }
}

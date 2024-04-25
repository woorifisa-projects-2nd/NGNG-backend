package com.ngng.api.search.dto.response;

import com.ngng.api.search.document.ProductsDocument;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record CategoryProductsResponse(Page<ProductsDocument> products) {

    public static CategoryProductsResponse of(Page<ProductsDocument> products) {

        return CategoryProductsResponse.builder()
                .products(products)
                .build();
    }
}

package com.ngng.api.search.dto.response;

import com.ngng.api.search.document.ProductsDocument;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
public record MainProductsResponse(Page<ProductsDocument> products) {

    public static MainProductsResponse of(Page<ProductsDocument> products) {

        return MainProductsResponse.builder()
                .products(products)
                .build();
    }
}

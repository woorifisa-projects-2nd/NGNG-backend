package com.ngng.api.search.dto.response;

import com.ngng.api.search.document.ProductsDocument;
import lombok.Builder;

import java.util.List;

@Builder
public record SearchProductsResponse(List<ProductsDocument> products,
                                     long totalHits,
                                     int totalPages) {

    public static SearchProductsResponse of(List<ProductsDocument> products, long totalHits, int totalPages) {

        return SearchProductsResponse.builder()
                .products(products)
                .totalHits(totalHits)
                .totalPages(totalPages)
                .build();
    }
}

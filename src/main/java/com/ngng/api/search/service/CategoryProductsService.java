package com.ngng.api.search.service;

import com.ngng.api.search.document.ProductsDocument;
import com.ngng.api.search.dto.response.CategoryProductsResponse;
import com.ngng.api.search.repository.ProductsDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryProductsService {

    private final ProductsDocumentRepository productsDocumentRepository;

    public CategoryProductsResponse findByCategory(String category, int page) {

        // 페이지당 20개
        Pageable pageable = PageRequest.of(page, 20);
        Page<ProductsDocument> products = productsDocumentRepository.findByCategoryOrderByRefreshedAtDesc(category, pageable);

        return CategoryProductsResponse.of(products);
    }
}

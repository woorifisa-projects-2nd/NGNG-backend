package com.ngng.api.search.service;

import com.ngng.api.search.document.ProductsDocument;
import com.ngng.api.search.dto.response.MainProductsResponse;
import com.ngng.api.search.repository.ProductsDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MainProductsService {

    private final ProductsDocumentRepository productsDocumentRepository;

    public MainProductsResponse getMain() {

        Pageable pageable = PageRequest.of(0, 60);

        Page<ProductsDocument> products = productsDocumentRepository.findByOrderByCreatedAtDesc(pageable);

        return MainProductsResponse.of(products);
    }
}

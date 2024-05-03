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

    private final int PAGE_SIZE = 60;

    public MainProductsResponse getMain() {

        // 메인 페이지에서는 한번에 60개의 상품을 보내고 배너를 기준으로 나눠서 보여줌
        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        Page<ProductsDocument> products = productsDocumentRepository.findByForSaleOrderByCreatedAtDesc(true, pageable);

        return MainProductsResponse.of(products);
    }
}

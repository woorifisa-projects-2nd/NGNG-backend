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

        /*
        * 처음 화면에는 12개만 보여주지만
        * 한번에 60개를 보내고
        * 더보기버튼을 누를때마다 12개씩 추가로 보여주는 형태
        * 이후 60개를 다 읽으면 더보기버튼 없애고
        * 검색 or 카테고리 선택 권유
        * (ex 찾으시는 상품이 없으신가요? 원하는 키워드로 상품을 검색해보세요)
        * */

        Pageable pageable = PageRequest.of(0, 60);

        Page<ProductsDocument> products = productsDocumentRepository.findByForSaleOrderByCreatedAtDesc(true, pageable);

        return MainProductsResponse.of(products);
    }
}

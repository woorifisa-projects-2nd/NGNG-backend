package com.ngng.api.search.repository;

import com.ngng.api.search.document.ProductsDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsDocumentRepository extends ElasticsearchRepository<ProductsDocument, String> {

    // 현재 판매중인 상품을 최신 기준으로 정렬해서 보여줌, 메인 페이지 용도
    Page<ProductsDocument> findByForSaleOrderByCreatedAtDesc(boolean forSale, Pageable pageable);

    // 해당 카테고리의 상품을 최근 끌어올리기를 한 기준으로 보여줌, 카테고리 용도
    Page<ProductsDocument> findByCategoryOrderByRefreshedAtDesc(String category, Pageable pageable);

    // 다중필드 검색은 ElasticRepository로 구현하기 어려워 QueryBuilder 사용함
}

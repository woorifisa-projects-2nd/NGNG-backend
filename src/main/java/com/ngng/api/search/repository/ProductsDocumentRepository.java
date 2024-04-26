package com.ngng.api.search.repository;

import com.ngng.api.search.document.ProductsDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsDocumentRepository extends ElasticsearchRepository<ProductsDocument, String> {

    Page<ProductsDocument> findAll(Pageable pageable);
    Page<ProductsDocument> findByOrderByCreatedAtDesc(Pageable pageable);
    Page<ProductsDocument> findByCategoryOrderByCreatedAtDesc(String category, Pageable pageable);
}

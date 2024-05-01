package com.ngng.api.search.service;

import com.ngng.api.search.document.ProductsDocument;
import com.ngng.api.search.dto.response.SearchProductsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchProductsService {

    private final ElasticsearchOperations elasticsearchOperations;
    private final int PAGE_SIZE = 20;

    public SearchProductsResponse findBySearchKeyword(String keyword, int page) {

        Criteria criteria;

        if (keyword.isEmpty()) {

            criteria = new Criteria().exists();
        } else {

            Criteria titleCriteria = Criteria.where("title").contains(keyword);
            Criteria contentCriteria = Criteria.where("content").contains(keyword);
            Criteria tagsCriteria = Criteria.where("tags").contains(keyword);

            criteria = titleCriteria.or(contentCriteria).or(tagsCriteria);
        }

        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("createdAt").descending());

        Query query = new CriteriaQuery(criteria).setPageable(pageable);

        SearchHits<ProductsDocument> hits = elasticsearchOperations.search(query, ProductsDocument.class);

        long totalHits = hits.getTotalHits();
        int totalPages = (int) Math.ceil((double) totalHits / PAGE_SIZE);

        List<ProductsDocument> products = hits.stream()
                .map(SearchHit::getContent)
                .toList();

        return SearchProductsResponse.of(products, totalHits, totalPages);
    }
}

package com.ngng.api.search.controller;

import com.ngng.api.search.dto.response.SearchProductsResponse;
import com.ngng.api.search.service.SearchProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@RequestMapping("/search")
@RestController
public class SearchProductsController {

    private final SearchProductsService searchProductsService;

    @GetMapping("/{keyword}/{page}")
    public ResponseEntity<?> findBySearch(@PathVariable String keyword, @PathVariable int page) {

        keyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8); // 한글 검색 시 문자가 깨져서 들어오기 때문에 UTF-8로 다시 디코딩
        SearchProductsResponse response = searchProductsService.findBySearchKeyword(keyword, page);

        return ResponseEntity.ok().body(response);
    }
}

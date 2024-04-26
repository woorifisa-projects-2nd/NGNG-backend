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

//    @GetMapping("/{keyword}")
//    public ResponseEntity<?> findBySearchFirst(@PathVariable String keyword) {
//
//        // URL은 ASCII코드를 기준으로 하지만 한글은 아스키테이블에 없기 때문에 UTF-8 형태로 디코딩 필요
//        keyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);
//        SearchProductsResponse response = searchProductsService.findBySearchKeyword(keyword, 0);
//
//        return ResponseEntity.ok().body(response);
//    }

    @GetMapping("/{keyword}/{page}")
    public ResponseEntity<?> findBySearch(@PathVariable String keyword, @PathVariable int page) {

        keyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);
        SearchProductsResponse response = searchProductsService.findBySearchKeyword(keyword, page);

        return ResponseEntity.ok().body(response);
    }}

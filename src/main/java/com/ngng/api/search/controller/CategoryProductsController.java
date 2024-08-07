package com.ngng.api.search.controller;

import com.ngng.api.search.dto.response.CategoryProductsResponse;
import com.ngng.api.search.service.CategoryProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/category")
@RestController
public class CategoryProductsController {

    private final CategoryProductsService categoryProductsService;

    @GetMapping("/{category}/{page}")
    public ResponseEntity<?> findByCategory(@PathVariable String category, @PathVariable int page) {

        CategoryProductsResponse response = categoryProductsService.findByCategory(category, page);

        return ResponseEntity.ok().body(response);
    }
}

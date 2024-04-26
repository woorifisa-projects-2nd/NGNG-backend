package com.ngng.api.search.controller;

import com.ngng.api.search.dto.response.MainProductsResponse;
import com.ngng.api.search.service.MainProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/main")
@RestController
public class MainProductsController {

    private final MainProductsService mainProductsService;

    @GetMapping
    public ResponseEntity<?> getMain() {

        MainProductsResponse response = mainProductsService.getMain();

        return ResponseEntity.ok().body(response);
    }
}

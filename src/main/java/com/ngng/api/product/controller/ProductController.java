package com.ngng.api.product.controller;

import com.ngng.api.product.dto.request.CreateProductRequestDTO;
import com.ngng.api.product.dto.request.UpdateProductRequestDTO;
import com.ngng.api.product.dto.response.ReadProductResponseDTO;
import com.ngng.api.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/products")
@RestController
@RequiredArgsConstructor
@Tag(name = "Product API")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "상품 추가", description = "전달받은 값으로 상품을 생성합니다.")
    @PostMapping()
    public ResponseEntity<Long> create(@RequestBody CreateProductRequestDTO product){
        return ResponseEntity.ok().body(productService.create(product));
    }

    @GetMapping(path = "/{productId}")
    @Parameter(name = "id", description = "상품 id")
    @Operation(summary = "상품 상세페이지 조회", description = "id값으로 특정 상품을 찾습니다.")
    public ResponseEntity<ReadProductResponseDTO> read(@PathVariable Long productId){
        ReadProductResponseDTO found = productService.read(productId);
        if(found == null){
            return ResponseEntity.notFound().build();
        }else{
           return ResponseEntity.ok(productService.read(productId));
        }
    }

    @PutMapping(path = "/{productId}")
    @Parameter(name = "id", description = "상품 id")
    @Operation(summary = "상품 정보 수정", description = "id값과 전달받은 값으로 특정 상품의 정보를 수정합니다.")
    public ResponseEntity<Long> update(@PathVariable Long productId, @RequestBody UpdateProductRequestDTO product){
        return ResponseEntity.ok(productService.update(productId, product));
    }

    @DeleteMapping(path = "/{productId}")
    @Parameter(name = "id", description = "상품 id")
    @Operation(summary = "상품 삭제", description = "id값으로 해당 상품을 찾아 보이지 않게 숨깁니다.")
    public ResponseEntity<Long> updateVisibility(@PathVariable Long productId){
        return  ResponseEntity.ok(productService.delete(productId));
    }
}

package com.ngng.api.productTag.service;

import com.ngng.api.product.entity.Product;
import com.ngng.api.productTag.dto.response.ReadProductTagResponseDTO;
import com.ngng.api.productTag.entity.ProductTag;
import com.ngng.api.productTag.repository.ProductTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductTagService {
    private final ProductTagRepository productTagRepository;

    public Long create(Long productId, String tag){
        return productTagRepository.save(ProductTag.builder()
                        .tag(tag)
                        .product(Product.builder().productId(productId).build())
                .build()).getProductTagId();
    }

    public List<ReadProductTagResponseDTO> readAllByProductId(Long productTagId){
        return productTagRepository.findAllByProductProductId(productTagId)
                .stream()
                .map(productTag -> ReadProductTagResponseDTO.builder().tagName(productTag.getTag()).build())
                .collect(Collectors.toList());

    }

    public void delete(Long productTagId){
        productTagRepository.deleteById(productTagId);
    }

    public void deleteByProductIdAndTag(Long productId, String tag){
        productTagRepository.deleteByProductProductIdAndTag(productId, tag);
    }
}

package com.ngng.api.productImage.service;

import com.ngng.api.productImage.dto.response.ReadProductImageResponseDTO;
import com.ngng.api.productImage.entity.ProductImage;
import com.ngng.api.product.entity.Product;

import com.ngng.api.productImage.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductImageService {
    private final ProductImageRepository productImageRepository;

    public void create(Long productId, String imageURL){
        // TODO : S3에 업로드하고 주소 받아오는 로직
        productImageRepository.save(
                ProductImage.builder()
                .imageUrl(imageURL)
                .product(Product.builder().id(productId).build())
                .build());
    }

    public List<ReadProductImageResponseDTO> readAllByProductId(Long productId){
        return productImageRepository.findAllByProductId(productId).stream().
                map(image -> ReadProductImageResponseDTO
                        .builder()
                        .id(image.getId())
                        .imageURL(image.getImageUrl())
                        .build()
                )
        .collect(Collectors.toList());
    }

    public ProductImage readByProductIdAndImageUrl( Long productId,String imageUrl){
        return productImageRepository.findByProductIdAndImageUrl(productId,imageUrl);
    }
    public Long delete(Long productId, String imageUrl){
        ProductImage found = productImageRepository.findByProductIdAndImageUrl(productId, imageUrl);
        found.setVisibility(false);
        found.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return productImageRepository.save(found).getId();
    }
}

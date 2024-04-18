package com.ngng.api.product.service;

import com.ngng.api.product.dto.response.ReadProductImageResponseDTO;
import com.ngng.api.product.entity.ProductImage;
import com.ngng.api.product.entity.Product;

import com.ngng.api.product.repository.ProductImageRepository;
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
                .product(Product.builder().productId(productId).build())
                .build());
    }

    public List<ReadProductImageResponseDTO> readAllByProductId(Long productId){
        return productImageRepository.findAllByProductProductId(productId).stream().
                map(image -> ReadProductImageResponseDTO
                        .builder()
                        .id(image.getProductImageId())
                        .imageURL(image.getImageUrl())
                        .build()
                )
        .collect(Collectors.toList());
    }

    public ProductImage readByProductIdAndImageUrl( Long productId,String imageUrl){
        return productImageRepository.findByProductProductIdAndImageUrl(productId,imageUrl);
    }
    public Long delete(Long productId, String imageUrl){
        ProductImage found = productImageRepository.findByProductProductIdAndImageUrl(productId, imageUrl);
        found.setVisible(false);
        found.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return productImageRepository.save(found).getProductImageId();
    }
}

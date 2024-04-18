package com.ngng.api.product.repository;

import com.ngng.api.product.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    public List<ProductImage> findAllByProductProductId(Long productId);

    public ProductImage findByProductProductIdAndImageUrl(Long productId,String imageUrl);
}

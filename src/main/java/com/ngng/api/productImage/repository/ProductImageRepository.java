package com.ngng.api.productImage.repository;

import com.ngng.api.productImage.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    public List<ProductImage> findAllByProductId(Long productId);

    public ProductImage findByProductIdAndImageUrl(Long productId,String imageUrl);
}

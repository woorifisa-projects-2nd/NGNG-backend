package com.ngng.api.product.product.repository;

import com.ngng.api.product.product.entity.Product;
import com.ngng.api.product.product.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    public List<ProductImage> findAllByProductProductId(Long productId);

    public ProductImage findByProductProductIdAndImageUrl(Long productId,String imageUrl);

    List<ProductImage> findByProduct(Product product);

    @Transactional
    @Modifying
    @Query(value = "update product_image p set visible = 0 where p.product_id = :productId and p.image_url in :images", nativeQuery = true)
    public void deleteAllByProductIdAndImages(@Param("productId") Long productId, @Param("images") List<String> images);


}

package com.ngng.api.thumbnail.repository;

import com.ngng.api.product.entity.Product;
import com.ngng.api.thumbnail.entity.Thumbnail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ThumbnailRepository extends JpaRepository<Thumbnail, Long> {
    public Optional<Thumbnail> findByProductProductId(Long productId);

    public void deleteByProduct(Product product);

    Optional<Thumbnail> findByProduct(Product productId);

}

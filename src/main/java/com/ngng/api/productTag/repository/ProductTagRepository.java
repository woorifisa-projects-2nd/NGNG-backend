package com.ngng.api.productTag.repository;

import com.ngng.api.productTag.entity.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {
    public List<ProductTag> findAllByProductProductId(Long id);

    public Long deleteByProductProductIdAndTag(Long productId, String tag);
}

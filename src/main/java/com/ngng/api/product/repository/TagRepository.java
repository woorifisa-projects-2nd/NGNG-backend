package com.ngng.api.product.repository;

import com.ngng.api.product.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    public List<Tag> findAllByProductProductId(Long id);

    public Long deleteByProductProductIdAndTagName(Long productId, String tagName);
}

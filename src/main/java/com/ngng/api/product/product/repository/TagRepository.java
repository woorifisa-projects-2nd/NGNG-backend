package com.ngng.api.product.product.repository;

import com.ngng.api.product.product.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    public List<Tag> findAllByProductProductId(Long id);

    public Long deleteByProductProductIdAndTagName(Long productId, String tagName);

    @Transactional
    @Modifying
    @Query(value = "delete from tag t where t.product_id = :productId and t.tag_name in :tags", nativeQuery = true)
    public void deleteAllByProductIdAndTagName(@Param("productId") Long productId, @Param("tags") List<String> tags);
}

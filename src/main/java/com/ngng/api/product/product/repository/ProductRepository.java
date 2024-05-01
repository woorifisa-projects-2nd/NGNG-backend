package com.ngng.api.product.product.repository;

import com.ngng.api.product.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.user.id = :userId")
    List<Product> readAllSellProductBySellerId(@Param("userId") Long userId);

    Page<Product> findAllByVisible(Boolean visible, Pageable pageable);
}

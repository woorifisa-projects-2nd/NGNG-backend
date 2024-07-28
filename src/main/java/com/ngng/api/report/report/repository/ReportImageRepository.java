package com.ngng.api.report.report.repository;

import com.ngng.api.report.report.entity.ReportImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportImageRepository extends JpaRepository<ReportImage, Long> {
//    public List<ProductImage> findAllByProductProductId(Long productId);
//
//    public ProductImage findByProductProductIdAndImageUrl(Long productId,String imageUrl);
}

package com.ngng.api.report.service;

import com.ngng.api.report.entity.Report;
import com.ngng.api.report.entity.ReportImage;
import com.ngng.api.report.repository.ReportImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportImageService {
    private final ReportImageRepository reportImageRepository;

    public void create(Long reportId, String imageURL) {
        // TODO : S3에 업로드하고 주소 받아오는 로직
        reportImageRepository.save(
                ReportImage.builder()
                        .imageUrl(imageURL)
                        .report(Report.builder().reportId(reportId).build())
                        .build());
    }

//    public List<ReadProductImageResponseDTO> readAllByProductId(Long productId){
//        return productImageRepository.findAllByProductProductId(productId).stream().
//                map(image -> ReadProductImageResponseDTO
//                        .builder()
//                        .id(image.getProductImageId())
//                        .imageURL(image.getImageUrl())
//                        .build()
//                )
//        .collect(Collectors.toList());
//    }
//
//    public ProductImage readByProductIdAndImageUrl( Long productId,String imageUrl){
//        return productImageRepository.findByProductProductIdAndImageUrl(productId,imageUrl);
//    }
//    public Long delete(Long productId, String imageUrl){
//        ProductImage found = productImageRepository.findByProductProductIdAndImageUrl(productId, imageUrl);
//        found.setVisible(false);
//        found.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
//        return productImageRepository.save(found).getProductImageId();
//    }
}

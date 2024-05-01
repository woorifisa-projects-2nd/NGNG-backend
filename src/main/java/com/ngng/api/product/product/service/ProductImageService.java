package com.ngng.api.product.product.service;

import com.ngng.api.product.product.dto.response.ReadProductImageResponseDTO;
import com.ngng.api.product.product.entity.Product;
import com.ngng.api.product.product.entity.ProductImage;
import com.ngng.api.product.product.repository.ProductImageRepository;
import com.ngng.api.product.thumbnail.entity.Thumbnail;
import com.ngng.api.product.thumbnail.repository.ThumbnailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductImageService {
    private final ProductImageRepository productImageRepository;
    private final ThumbnailRepository thumbnailRepository;

    public void create(Long productId, String imageURL) {
        // TODO : S3에 업로드하고 주소 받아오는 로직

        String extension = imageURL.substring(imageURL.lastIndexOf('.') + 1);

        // 확장자에 따라 contentType 결정
        String contentType = "IMAGE";
        if (extension.equals("mp4")) {
            contentType = "VIDEO";
        }

        productImageRepository.save(
                ProductImage.builder()
                        .imageUrl(imageURL)
                        .contentType(contentType)
                        .product(Product.builder().productId(productId).build())
                        .build());
    }

    public List<ReadProductImageResponseDTO> readAllByProductId(Long productId) {
        return productImageRepository.findAllByProductProductId(productId).stream().
                map(image -> ReadProductImageResponseDTO
                        .builder()
                        .id(image.getProductImageId())
                        .imageURL(image.getImageUrl())
                        .build()
                )
                .collect(Collectors.toList());
    }

    public ProductImage readByProductIdAndImageUrl(Long productId, String imageUrl) {
        return productImageRepository.findByProductProductIdAndImageUrl(productId, imageUrl);
    }

    public Long delete(Long productId, String imageUrl) {
        ProductImage found = productImageRepository.findByProductProductIdAndImageUrl(productId, imageUrl);
        found.setVisible(false);
        found.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return productImageRepository.save(found).getProductImageId();
    }

    @Transactional
    public Long deleteImages(Long productId) {
        Product product = new Product(productId);
        Optional<Thumbnail> thumbnail = thumbnailRepository.findByProduct(product);

        // 기존 썸네일이 존재하는 경우 삭제
        if (thumbnail.isPresent()) {
            thumbnailRepository.deleteByProduct(thumbnail.get().getProduct());
        }

        List<ProductImage> images = productImageRepository.findByProduct(product);

        // 이미지 URL만을 담는 리스트를 생성
        List<String> imageUrls = images.stream()
                .map(ProductImage::getImageUrl) // ProductImage 객체에서 imageUrl 속성만 추출
                .toList();

        productImageRepository.deleteAllByProductIdAndImages(productId, imageUrls);

        return productId;

    }
}

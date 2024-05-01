package com.ngng.api.product.thumbnail.service;

import com.ngng.api.product.product.entity.Product;
import com.ngng.api.product.thumbnail.entity.Thumbnail;
import com.ngng.api.product.thumbnail.repository.ThumbnailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ThumbnailService {
    private final ThumbnailRepository thumbnailRepository;

    @Transactional
    public void create( Long productId , String thumbnailUrl){

        Product product = new Product(productId);
        Optional<Thumbnail> thumbnail = thumbnailRepository.findByProduct(product);

        // 기존 썸네일이 존재하는 경우
        if (thumbnail.isPresent()) {
            thumbnailRepository.deleteByProduct(thumbnail.get().getProduct());
        }

        thumbnailRepository.save(Thumbnail.builder()
                        .thumbnailUrl(thumbnailUrl)
                        .product(Product.builder().productId(productId).build())
                .build());
    }

    public Thumbnail readByProductId(Long productId){
        return thumbnailRepository.findByProductProductId(productId).orElse(null);
    }

    public void update(Long thumbnailId, String newUrl){
        Thumbnail found = thumbnailRepository.findById(thumbnailId).orElseThrow();
        found.setThumbnailUrl(newUrl);
        thumbnailRepository.save(found);

    }
}

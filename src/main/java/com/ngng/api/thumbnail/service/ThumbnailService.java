package com.ngng.api.thumbnail.service;

import com.ngng.api.product.entity.Product;
import com.ngng.api.thumbnail.entity.Thumbnail;
import com.ngng.api.thumbnail.repository.ThumbnailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ThumbnailService {
    private final ThumbnailRepository thumbnailRepository;

    public void create( Long productId , String thumbnailUrl){

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

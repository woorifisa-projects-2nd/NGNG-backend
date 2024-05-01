package com.ngng.api.product.product.service;

import com.ngng.api.product.product.dto.response.TagResponseDTO;
import com.ngng.api.product.product.entity.Product;
import com.ngng.api.product.product.entity.Tag;
import com.ngng.api.product.product.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagService {
    private final TagRepository tagRepository;

    public Long create(Long productId, String tag){
        return tagRepository.save(Tag.builder()
                        .tagName(tag)
                        .product(Product.builder().productId(productId).build())
                .build()).getTagId();
    }

    public List<TagResponseDTO> readAllByProductId(Long productTagId){
        return tagRepository.findAllByProductProductId(productTagId)
                .stream()
                .map(tag -> TagResponseDTO.builder().tagName(tag.getTagName()).build())
                .collect(Collectors.toList());

    }

    public void delete(Long productTagId){
        tagRepository.deleteById(productTagId);
    }

    public void deleteByProductIdAndTagName(Long productId, String tagName){
        tagRepository.deleteByProductProductIdAndTagName(productId, tagName);
    }

    public void deleteAllByProductIdAndTagName(Long productId, List<String> tags){
        tagRepository.deleteAllByProductIdAndTagName(productId, tags);
    }
}

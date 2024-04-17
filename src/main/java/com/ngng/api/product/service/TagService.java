package com.ngng.api.product.service;

import com.ngng.api.product.entity.Product;
import com.ngng.api.product.dto.response.TagResponseDTO;
import com.ngng.api.product.entity.Tag;
import com.ngng.api.product.repository.TagRepository;
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

    public void deleteByProductIdAndTag(Long productId, String tag){
        tagRepository.deleteByProductProductIdAndTagName(productId, tag);
    }
}

package com.ngng.api.product.service;

import com.ngng.api.product.dto.request.CreateProductRequestDTO;
import com.ngng.api.product.dto.request.UpdateProductRequestDTO;
import com.ngng.api.product.dto.response.*;
import com.ngng.api.product.entity.Category;
import com.ngng.api.product.entity.Product;
import com.ngng.api.product.entity.Tag;
import com.ngng.api.product.repository.ProductRepository;
import com.ngng.api.productImage.dto.response.ReadProductImageResponseDTO;
import com.ngng.api.productImage.service.ProductImageService;
import com.ngng.api.productTag.dto.request.UpdateProductTagRequestDTO;
import com.ngng.api.status.entity.Status;
import com.ngng.api.thumbnail.service.ThumbnailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageService productImageService;
    private final TagService tagService;
    private final ThumbnailService thumbnailService;

    public Long create(CreateProductRequestDTO request) {


        // TODO 파일 받아서 업로드 하는 걸로 수정
        // 3. 이미지 등록
//        product.getImages().forEach(image -> productImageService.create(productId, image.getImageURL()));

        // 4. 썸네일 등록
//        log.info("Thumbnail url : "+ product.getThumbnailUrl());
//        thumbnailService.create(product.getThumbnailUrl(), productId);

        return productRepository.save(new Product(request)).getProductId();
    }


    public ReadProductResponseDTO read(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return null;
        }
        return ReadProductResponseDTO
                .builder()
                .id(product.getProductId())
                .content(product.getContent())
                .createdAt(product.getCreatedAt())
                .discountable(product.getDiscountable())
                .forSale(product.getForSale())
                .freeShipping(product.getFreeShipping())
                .images(product.getProductImages()
                        .stream().map(image -> ReadProductImageResponseDTO
                                .builder()
                                .id(image.getProductImageId())
                                .imageURL(image.getImageUrl())
                                .visible(image.getVisible())
                                .build())
                        .collect(Collectors.toList()))
                .title(product.getTitle())
                .isEscrow(product.getIsEscrow())
                .price(product.getPrice())
                .visible(product.getVisible())
                .updatedAt(product.getUpdatedAt())
                .refreshedAt(product.getRefreshedAt())
                .isEscrow(product.getIsEscrow())
                .purchaseAt(product.getPurchaseAt())
                .available(product.getAvailable())
                .tags(product.getTags()
                        .stream().map(tag -> TagResponseDTO
                                .builder()
                                .tagName(tag.getTagName())
                                .build())
                        .collect(Collectors.toList())
                )
                .status(ReadProductStatusResponseDTO.builder()
                        .id(product.getStatus().getStatusId())
                        .name(product.getStatus().getStatusName())
                        .build()
                )
                .user(ReadProductUserResponseDTO.builder()
                        .id(product.getUser().getUserId())
                        .name(product.getUser().getName())
                        .nickname(product.getUser().getNickname())
                        .build())
                .category(ReadProductCategoryResponseDTO.builder()
                        .id(product.getCategory().getCategoryId())
                        .name(product.getCategory().getCategoryName())
                        .build())
                //.chats(product.getPublicChats())
                .build();
    }

    public List<ReadAllProductsDTO> readAll() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            return null;
        }
        return products.stream().map((product -> ReadAllProductsDTO
                .builder()
                .id(product.getProductId())
                .content(product.getContent())
                .createdAt(product.getCreatedAt())
                .discountable(product.getDiscountable())
                .forSale(product.getForSale())
                .freeShipping(product.getFreeShipping())
                .title(product.getTitle())
                .isEscrow(product.getIsEscrow())
                .price(product.getPrice())
                .visible(product.getVisible())
                .updatedAt(product.getUpdatedAt())
                .refreshedAt(product.getRefreshedAt())
                .isEscrow(product.getIsEscrow())
                .purchaseAt(product.getPurchaseAt())
                .available(product.getAvailable())
                .user(ReadProductUserResponseDTO.builder()
                        .id(product.getUser().getUserId())
                        .name(product.getUser().getName())
                        .nickname(product.getUser().getNickname())
                        .build())
                .category(ReadProductCategoryResponseDTO.builder()
                        .id(product.getCategory().getCategoryId())
                        .name(product.getCategory().getCategoryName())
                        .build())
                .status(ReadProductStatusResponseDTO.builder()
                        .id(product.getStatus().getStatusId())
                        .name(product.getStatus().getStatusName())
                        .build()).build())).toList();
    }

    public List<Product> readSellProductsByUserId(Long sellerId) {

        List<Product> products = productRepository.readAllSellProductBySellerId(sellerId);

//        return products.stream()
//                .map(item -> new ReadProductMypageResponseDTO().from(item))
//                .collect(Collectors.toList());
        return products;
    }


    @Transactional
    public Long update(Long productId, UpdateProductRequestDTO product) {

        Product found = productRepository.findById(productId).orElseThrow();
        log.info("기존 상품 가져오기");

        // 6. 상품 정보 수정 // TODO : 메서드만들어서 한줄로 줄이기
        found.setTitle(product.getTitle());
        found.setContent(product.getContent());
        found.setPrice(product.getPrice());
        found.setIsEscrow(product.getIsEscrow());
        found.setDiscountable(product.getDiscountable());
        found.setPurchaseAt(product.getPurchaseAt());
        found.setForSale(product.getForSale());
        found.setFreeShipping(product.getFreeShipping());
        found.setRefreshedAt(product.getRefreshedAt());
        found.setStatus(Status.builder().statusId(product.getStatusId()).build());
        found.setCategory(Category.builder().categoryId(product.getCategoryId()).build());

        Long updatedProductId = productRepository.save(found).getProductId();

        List<Tag> originalTags = found.getTags();  // 기존의 태그

        System.out.println("originalTags = " + originalTags);

        List<UpdateProductTagRequestDTO> newTags = product.getTags();  // 새로운 태그

        System.out.println("newTags = " + newTags);

        // 1. 태그 삭제
        originalTags.forEach(originalTag -> { // 기존의 태그 리스트 순회하면서
            if (!newTags.contains(originalTag)) {  // 새로운 태그에 포함되지 않은 것 삭제
                tagService.deleteByProductIdAndTag(productId, originalTag.getTagName());
            }
        });
        log.info("포함되지 않은 태그 삭제 완료");
        // 2. 태그 추가
        newTags.forEach(newTag -> { // 새로운 태그 리스트 순회하면서
            if (!originalTags.contains(newTag)) { // 기존의 태그에 포함되지 않은 것 추가
                tagService.create(productId, newTag.getTagName());
            }
        });

        log.info("새로운 태그 추가 완료");
//        List<String> originalImageUrls = found.getProductImages().stream().map(ProductImage::getImageUrl).toList();  // 기존의 이미지
//        List<UpdateImageRequestDTO> newImages = product.getImages(); // 새로운 이미지
//        log.info("기존 이미지 가져오기")`;
//        // 3. 이미지 삭제
//        originalImageUrls.forEach(originalImageUrl -> {
//            if (!newImages.contains(originalImageUrl)) {  // 새로운 이미지에 포함되지 않은 것 삭제
//                productImageService.delete(productId, originalImageUrl);
//            }
//        });
//        log.info("새로운 이미지에 포함되지 않은 것 삭제 완료");
//        // 4. 이미지 추가
//        newImages.forEach(newImage -> { // 새로운 이미지 리스트 순회하면서
//            if (!originalImageUrls.contains(newImage)) { // 기존의 이미지에 포함되지 않은 것 추가
//                productImageService.create(productId, newImage.getImageURL());
//            }
//        });
//        log.info("기존의 이미지에 포함되지 않은 것 추가 완료");
//
//        // 5. 썸네일 변경
//        Thumbnail originalThumbnail = thumbnailService.readByProductId(productId);
//        if (!originalThumbnail.getThumbnailUrl().equals(product.getThumbnailUrl())) {
//            // TODO : 썸네일 생성
//            // TODO : 썸네일 S3 업로드
//            thumbnailService.update(originalThumbnail.getThumbnailId(), product.getThumbnailUrl());
//        }
//        log.info("썸네일 변경");

        return updatedProductId;
    }

    public Long delete(Long productId) {  // visibility만 false로 수정
        Product product = productRepository.findById(productId).orElseThrow();
        product.setVisible(false);
//        product.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return productRepository.save(product).getProductId();
    }
}

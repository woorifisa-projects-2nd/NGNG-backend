package com.ngng.api.product.service;

import com.ngng.api.product.dto.UserDTO;
import com.ngng.api.product.dto.request.TagRequestDTO;
import com.ngng.api.product.dto.request.CreateProductRequestDTO;
import com.ngng.api.product.dto.request.TagRequestDTO;
import com.ngng.api.product.dto.request.UpdateProductRequestDTO;
import com.ngng.api.product.dto.response.*;
import com.ngng.api.product.entity.Product;
import com.ngng.api.product.entity.Tag;
import com.ngng.api.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "product-log")
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final TagService tagService;


    public Long create(CreateProductRequestDTO request){
        return productRepository.save(new Product(request)).getProductId();
    }

    public ReadProductResponseDTO read(Long productId){
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
                        .user( new UserDTO(product.getUser()))
                        .category(ReadProductCategoryResponseDTO.builder()
                                .id(product.getCategory().getCategoryId())
                                .name(product.getCategory().getCategoryName())
                                .build())
                        .chats(product.getPublicChats().stream()
                                .map(chat ->
                                    ReadChatResponseDTO.builder()
                                            .id(chat.getPublicChatId())
                                            .message(chat.getMessage())
                                            .userId(chat.getUser().getUserId())
                                            .userNickName(chat.getUser().getNickname())
                                            .createdAt(chat.getCreatedAt())
                                        .build()
                                ).toList())
                .reports(product.getReports().stream().map(report ->
                        ReadReportResponseDTO.builder()
                                .reportId(report.getReportId())
                                .reportContents(report.getReportContents())
                                .reportType(report.getReportType())
                                .reporter(new UserDTO(report.getReporter()))
                                .user(new UserDTO(report.getUser()))
                                .createdAt(report.getCreatedAt())
                                .updatedAt(report.getUpdatedAt())
                                .isReport(report.getIsReport())
                        .build()).toList())
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
                .user(new UserDTO(product.getUser()))
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
    public Long update(Long productId, UpdateProductRequestDTO request){
        Product found = productRepository.findById(productId).orElseThrow();

        List<String> originalTags = found.getTags().stream().map(Tag::getTagName).toList();  // 기존의 태그dhk
        List<String> newTags = request.getTags().stream().map(TagRequestDTO::getTagName).toList();  // 새로운 태그

        // 기존의 태그 리스트 순회하면서 새로운 태그에 포함되지 않은 것 삭제
        originalTags.forEach(originalTag -> {
            if(!newTags.contains(originalTag)){
                tagService.deleteAllByProductIdAndTagName(productId, originalTags);
            }
        });

        found.getTags().clear();
        found.from(request);

        log.info("Success Update Product id: {} ",productId);
        return found.getProductId();
    }

    public Long updateForSale(Long productId, Boolean forSale){
        Product target = productRepository.findById(productId).orElse(null);
        if(target != null){
            target.setForSale(forSale);
            log.info("Success Update Product ForSale id: {} ",productId);
        }
        return target.getProductId();

    }

    public Long updateRefresh(Long productId){
        Product target = productRepository.findById(productId).orElse(null);
        if(target != null){
            target.setRefreshedAt(new Timestamp(new Date().getTime()));
            log.info("Success Update Product Refresh id: {} ",productId);
        }
        return target.getProductId();
    }

    public Long delete(Long productId){
        Product product = productRepository.findById(productId).orElseThrow();
        // 신고 안 받았고 거래 진행 안 했거나 거래취소된 경우에만 삭제 가능
        if(product.getReports() == null || product.getTransactionDetails() == null || product.getTransactionDetails().getStatus().getId().equals(6L)){
            product.setVisible(false);
            product.setForSale(false);
            log.info("Success Delete Product id: {} ",productId);
            return productRepository.save(product).getProductId();
        }else{
            return -1L;
        }

    }
}
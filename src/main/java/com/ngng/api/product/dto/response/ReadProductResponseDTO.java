package com.ngng.api.product.dto.response;

import com.ngng.api.product.dto.UserDTO;
import com.ngng.api.product.entity.Product;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadProductResponseDTO {
    Long id;
    String title;
    String content;
    Long price;
    Boolean isEscrow;
    Boolean discountable;
    String purchaseAt;
    Boolean forSale;
    Timestamp createdAt;
    Timestamp updatedAt;
    Boolean visible;
    Boolean freeShipping;
    Timestamp refreshedAt;
    UserDTO user;
    ReadProductStatusResponseDTO status;
    ReadProductCategoryResponseDTO category;
    List<TagResponseDTO> tags;
    List<ReadProductImageResponseDTO> images;
    List<ReadChatResponseDTO> chats;
    List<ReadReportResponseDTO> reports;


    public ReadProductResponseDTO from(Product product){
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
                .user(new UserDTO(product.getUser()))
                .category(ReadProductCategoryResponseDTO.builder()
                        .id(product.getCategory().getCategoryId())
                        .name(product.getCategory().getCategoryName())
                        .build())
                .chats(chats)
                .reports(product.getReports().stream()
                        .map(report -> ReadReportResponseDTO.builder()
                                .reportId(report.getReportId())
                                .reportContents(report.getReportContents())
                                .reportType(report.getReportType())
                                .reporter(new UserDTO(report.getReporter()) )
                                .user(new UserDTO(report.getUser()))
                                .isReport(report.getIsReport())
                                .createdAt(report.getCreatedAt())
                                .updatedAt(report.getUpdatedAt())
                                .build())
                        .toList())
                .build();
    }
}

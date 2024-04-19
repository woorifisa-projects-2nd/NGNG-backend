package com.ngng.api.product.entity;

import com.ngng.api.product.dto.request.CreateProductRequestDTO;
import com.ngng.api.product.dto.request.UpdateProductRequestDTO;
import com.ngng.api.report.entity.Report;
import com.ngng.api.status.entity.Status;
import com.ngng.api.thumbnail.entity.Thumbnail;
import com.ngng.api.transaction.entity.TransactionDetails;
import com.ngng.api.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "product")
@DynamicInsert
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private Long price;

    @ColumnDefault("false")
    private Boolean isEscrow;

    @ColumnDefault("false")
    private Boolean discountable;

    private String purchaseAt;

    @ColumnDefault("true")
    private Boolean forSale;

    @ColumnDefault("true")
    private Boolean visible;

    @ColumnDefault("false")
    private Boolean freeShipping;

    private Timestamp refreshedAt;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Tag> tags;

    @OneToOne(mappedBy = "product")
    private Thumbnail thumbnail;

    @OneToMany(mappedBy = "product")
    private List<ProductImage> productImages;

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    private List<PublicChat> publicChats;

    @OneToOne(mappedBy = "product")
    private TransactionDetails transactionDetails;

    @OneToMany
    @JoinColumn(name="product_id")
    private List<Report> reports;

    public Product(Long id){
        this.productId = id;
    }

    public Product(CreateProductRequestDTO request) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.price = request.getPrice();
        this.isEscrow = request.getIsEscrow();
        this.discountable = request.getDiscountable();
        this.purchaseAt = request.getPurchaseAt();
        this.freeShipping = request.getFreeShipping();
        this.user = User.builder().userId(request.getUserId()).build();
        this.status = Status.builder().statusId(request.getStatusId()).build();
        this.category = Category.builder().categoryId(request.getCategoryId()).build();
        this.tags = request.getTags().stream().
                map(tag -> Tag.builder()
                        .product(this)
                        .tagName(tag.getTagName())
                        .build()).toList();
    }

    public Product from (UpdateProductRequestDTO request){

        this.setTitle(request.getTitle());
        this.setContent(request.getContent());
        this.setPrice(request.getPrice());
        this.setIsEscrow(request.getIsEscrow());
        this.setDiscountable(request.getDiscountable());
        this.setPurchaseAt(request.getPurchaseAt());
        this.setForSale(request.getForSale());
        this.setVisible(request.getVisible());
        this.setFreeShipping(request.getFreeShipping());
        this.setStatus(Status.builder().statusId(request.getStatusId()).build());
        this.setCategory(Category.builder().categoryId(request.getCategoryId()).build());
        this.setTags(request.getTags().stream()
                .map(target -> Tag.builder()
                        .product(this)
                        .tagName(target.getTagName())
                        .build())
                .toList());
        return this;
    }
}

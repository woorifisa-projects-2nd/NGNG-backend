package com.ngng.api.product.entity;

import com.ngng.api.category.entity.Category;
import com.ngng.api.productImage.entity.ProductImage;
import com.ngng.api.productTag.entity.ProductTag;
import com.ngng.api.status.entity.Status;
import com.ngng.api.thumbnail.entity.Thumbnail;
import com.ngng.api.transaction.entity.TransactionDetails;
import com.ngng.api.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

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
    private String title;
    private String content;
    private Long price;

    @ColumnDefault("true")
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
    private Timestamp updatedAt;

    @ColumnDefault("true")
    private Boolean available;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(mappedBy = "product")
    private Thumbnail thumbnail;

    @OneToMany
    @JoinColumn(name = "product_id")
    private List<ProductTag> tags;

    @OneToMany
    @JoinColumn(name = "product_id")
    private List<ProductImage> images;

    @OneToOne(mappedBy = "product")
    private TransactionDetails transactionDetails;

    public Product(Long id){
        this.productId = id;
    }
}

package com.ngng.api.productTag.entity;

import com.ngng.api.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedBy;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicInsert
@Table(name = "product_tag")
public class ProductTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_tag_id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "tag")
    private String tag;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;
}

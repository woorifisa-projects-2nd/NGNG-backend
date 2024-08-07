package com.ngng.api.product.thumbnail.entity;

import com.ngng.api.product.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product_thumbnail")
public class Thumbnail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long thumbnailId;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String thumbnailUrl;

    @CreationTimestamp
    private Timestamp createdAt;

    private Timestamp updatedAt;
}

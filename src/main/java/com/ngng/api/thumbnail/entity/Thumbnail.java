package com.ngng.api.thumbnail.entity;

import com.ngng.api.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "thumbnail")
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

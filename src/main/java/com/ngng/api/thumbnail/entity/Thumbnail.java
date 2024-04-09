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
    @Column(name = "thumbnail_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name="created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
}

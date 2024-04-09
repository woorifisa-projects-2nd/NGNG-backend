package com.ngng.api.chat.entity;

import com.ngng.api.user.entity.User;
import com.ngng.api.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@DynamicInsert
@Table(name = "public_chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long publicChatId;

    private String message;

    @ColumnDefault("true")
    private Boolean visible;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @CreationTimestamp
    private Timestamp createdAt;

    private Timestamp updatedAt;

}

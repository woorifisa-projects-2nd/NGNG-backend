package com.ngng.api.transaction.entity;

import com.ngng.api.product.product.entity.Product;
import com.ngng.api.user.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "transaction_request")
@DynamicInsert
public class TransactionRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionRequestId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;
    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    private Boolean isAccepted;
    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;

    private Long price;


}

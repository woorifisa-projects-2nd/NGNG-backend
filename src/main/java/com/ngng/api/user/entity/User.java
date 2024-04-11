package com.ngng.api.user.entity;

import com.ngng.api.role.entity.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
@DynamicInsert
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String name;
    private String nickname;
    private String email;
    private String password;
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

//    @ColumnDefault("LOCAL")
//    private String channel;

    @ColumnDefault("true")
    private Boolean visible;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "account_bank")
    private String accountBank;
    @Column(name = "account_number")
    private String accountNumber;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public void accountConfirm(String accountBank, String accountNumber, Role role) {

        this.accountBank = accountBank;
        this.accountNumber = accountNumber;
        this.role = role;
    }

    public void addressConfirm(String address) {

        this.address = address;
    }
}

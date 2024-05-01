package com.ngng.api.user.user.repository;

import com.ngng.api.user.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String PhoneNumber);
    boolean existsByAccountNumber(String accountNumber);

    Page<User> findByVisible(Boolean visible, Pageable pageable);
}

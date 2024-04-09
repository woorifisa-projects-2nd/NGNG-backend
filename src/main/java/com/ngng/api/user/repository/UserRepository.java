package com.ngng.api.user.repository;

import com.ngng.api.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String PhoneNumber);
    boolean existsByAccountNumber(String accountNumber);
}

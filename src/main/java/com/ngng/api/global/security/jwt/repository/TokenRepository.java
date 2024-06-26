package com.ngng.api.global.security.jwt.repository;

import com.ngng.api.global.security.jwt.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Boolean existsByTokenName(String token);
    Optional<Token> findTokenByTokenName(String token);
}

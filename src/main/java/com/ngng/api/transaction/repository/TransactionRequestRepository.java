package com.ngng.api.transaction.repository;

import com.ngng.api.transaction.entity.TransactionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRequestRepository extends JpaRepository<TransactionRequest, Long> {
     List<TransactionRequest> findAllByProductProductId(Long productId);
     Optional<TransactionRequest> findByProductProductIdAndBuyerUserId(Long productId, Long buyerId);


}

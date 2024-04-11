package com.ngng.api.transaction.repository;


import com.ngng.api.transaction.entity.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionStatusRepository  extends JpaRepository<TransactionStatus,Long> {
}

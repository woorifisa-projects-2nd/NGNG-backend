package com.ngng.api.point.repository;

import com.ngng.api.point.model.PointHistory;
import com.ngng.api.transaction.model.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointHistoryRepository extends JpaRepository<PointHistory,Long> {

    @Query("SELECT p FROM PointHistory p WHERE p.user.id = :userId ORDER BY id DESC LIMIT 1" )
    PointHistory findLastByUserId(@Param("userId") Long userId);

    @Query("SELECT p FROM PointHistory p WHERE p.user.id = :userId ORDER BY id DESC" )
    List<PointHistory> findAllByUserId(@Param("userId") Long userId);

}

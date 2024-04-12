package com.ngng.api.penalty.repository;

import com.ngng.api.penalty.entity.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PenaltyRepository extends JpaRepository<Penalty, Long> {
    Optional<Penalty> findByReportId(Long reportId);
}

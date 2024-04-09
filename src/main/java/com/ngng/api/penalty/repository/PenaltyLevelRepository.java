package com.ngng.api.penalty.repository;

import com.ngng.api.penalty.entity.PenaltyLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PenaltyLevelRepository extends JpaRepository<PenaltyLevel, Long> {
}

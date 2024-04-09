package com.ngng.api.Penalty.repository;

import com.ngng.api.Penalty.entity.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PenaltyRepository extends JpaRepository<Penalty, Long> {
}

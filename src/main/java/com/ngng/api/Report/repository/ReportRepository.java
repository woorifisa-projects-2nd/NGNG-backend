package com.ngng.api.Report.repository;

import com.ngng.api.Report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}

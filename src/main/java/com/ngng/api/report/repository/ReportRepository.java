package com.ngng.api.report.repository;

import com.ngng.api.report.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Page<Report> findAll(Pageable pageable);
    Page<Report> findByIsReport(Boolean isReport, Pageable pageable);
}

package com.ngng.api.report.report.service;

import com.ngng.api.report.report.dto.*;
import org.springframework.data.domain.Page;

public interface ReportService {

    Page<ReadReportListResponseDTO> findAll(Integer page, Boolean unprocessedOnly);

    ReadReportResponseDTO findById(Long userId);

    CreateReportResponseDTO save(CreateReportRequestDTO createReportRequestDTO);

    ReadReportResponseDTO update(Long reportId, Boolean isReport);

    DeleteReportResponseDTO delete(Long reportId);

}

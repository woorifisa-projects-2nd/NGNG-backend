package com.ngng.api.report.service;

import com.ngng.api.report.dto.*;
import org.springframework.data.domain.Page;

public interface ReportService {

    Page<ReadReportListResponseDTO> findAll(int page, boolean unprocessedOnly);

    ReadReportResponseDTO findById(Long userId);

    CreateReportResponseDTO save(CreateReportRequestDTO createReportRequestDTO);

    ReadReportResponseDTO update(Long reportId, int isReport);

    DeleteReportResponseDTO delete(Long reportId);

}

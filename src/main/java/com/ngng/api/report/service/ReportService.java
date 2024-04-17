package com.ngng.api.report.service;

import com.ngng.api.report.dto.*;

import java.util.List;

public interface ReportService {

    List<ReadReportListResponseDTO> findAll();

    ReadReportResponseDTO findById(Long userId);

    CreateReportResponseDTO save(CreateReportRequestDTO createReportRequestDTO);

    ReadReportResponseDTO update(Long reportId, Boolean isReport);

    DeleteReportResponseDTO delete(Long reportId);

}

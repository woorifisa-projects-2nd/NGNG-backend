package com.ngng.api.Report.service;

import com.ngng.api.Report.dto.*;

import java.util.List;

public interface ReportService {

    List<ReadReportListResponseDTO> findAll();

    ReadReportResponseDTO findById(Long userId);

    CreateReportResponseDTO save(CreateReportRequestDTO createReportRequestDTO);

    ReadReportResponseDTO update(Long reportId, int isReport);

    DeleteReportResponseDTO delete(Long reportId);

}

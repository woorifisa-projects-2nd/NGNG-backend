package com.ngng.api.report.service;

import com.ngng.api.report.dto.*;
import com.ngng.api.report.entity.Report;
import com.ngng.api.report.entity.ReportImage;
import com.ngng.api.report.entity.ReportType;
import com.ngng.api.report.repository.ReportImageRepository;
import com.ngng.api.report.repository.ReportRepository;
import com.ngng.api.report.repository.ReportTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ReportTypeRepository reportTypeRepository;
    private final ReportImageRepository reportImageRepository;


    @Override
    public List<ReadReportListResponseDTO> findAll() {
        List<Report> reports = new ArrayList<>();
        reportRepository.findAll().forEach(reports::add);

        return reports.stream()
                .map(ReadReportListResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    public ReadReportResponseDTO findById(Long reportId) {
        Optional<Report> reportOptional = reportRepository.findById(reportId);

        Report report = reportOptional.orElse(null);

        return ReadReportResponseDTO.builder()
                .reportId(report.getReportId())
                .reportContents(report.getReportContents())
                .reportType(report.getReportType())
                .reporter(report.getReporter())
                .user(report.getUser())
                .isReport(report.getIsReport())
                .createdAt(report.getCreatedAt())
                .updatedAt(report.getUpdatedAt())
                .productId(report.getProductId())
                .privateChatId(report.getPrivateChatId())
                .reportImages(report.getReportImages().stream()
                        .map(ReportImage::getReportImg)
                        .collect(Collectors.toList()))
                .build();

    }

    @Override
    public CreateReportResponseDTO save(CreateReportRequestDTO createReportRequestDTO) {
        Optional<ReportType> reportType = reportTypeRepository.findById(createReportRequestDTO.getReportTypeId());
        ReportType responseReportType = reportType.orElseThrow(); // TODO 여기 예외처리하기

        Report report = Report.builder()
                .reportContents(createReportRequestDTO.getReportContents())
                .reportType(ReportType.builder()
                        .reportTypeId(createReportRequestDTO.getReportTypeId())
                        .reportType(responseReportType.getReportType())
                        .build())
                .reporterId(createReportRequestDTO.getReporterId())
                .userId(createReportRequestDTO.getUserId())
                .isReport(createReportRequestDTO.getIsReport())
                .productId(createReportRequestDTO.getProductId())
                .privateChatId(createReportRequestDTO.getPrivateChatId())
                .build();

        Report responseReport = reportRepository.save(report);

        return CreateReportResponseDTO.builder()
                .reportId(responseReport.getReportId())
                .reportContents(responseReport.getReportContents())
                .reportType(responseReport.getReportType())
                .reporter(responseReport.getReporter())
                .user(responseReport.getUser())
                .isReport(responseReport.getIsReport())
                .createdAt(responseReport.getCreatedAt())
                .updatedAt(responseReport.getUpdatedAt())
                .productId(responseReport.getProductId())
                .privateChatId(responseReport.getPrivateChatId())
                .build();
    }

    @Override
    public ReadReportResponseDTO update(Long reportId, int isReport) {
        System.out.println("reportId = " + reportId);
        System.out.println("isReport = " + isReport);

        Report report = reportRepository.findById(reportId).orElseThrow(() ->
                new EntityNotFoundException("report not found")
        );

        report.setIsReport(isReport);

        Report responseReport = reportRepository.save(report);

        return ReadReportResponseDTO.builder()
                .reportId(responseReport.getReportId())
                .reportContents(responseReport.getReportContents())
                .reportType(responseReport.getReportType())
                .reporter(responseReport.getReporter())
                .user(responseReport.getUser())
                .isReport(responseReport.getIsReport())
                .createdAt(responseReport.getCreatedAt())
                .updatedAt(responseReport.getUpdatedAt())
                .productId(responseReport.getProductId())
                .privateChatId(responseReport.getPrivateChatId())
                .reportImages(responseReport.getReportImages().stream()
                        .map(ReportImage::getReportImg)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public DeleteReportResponseDTO delete(Long reportId) {
        Report report = reportRepository.findById(reportId).orElseThrow(() ->
                new EntityNotFoundException("report not found")
        );

        report.setVisible(1);

        Report responseReport = reportRepository.save(report);

        return DeleteReportResponseDTO.builder()
                .reportId(responseReport.getReportId())
                .visible(responseReport.getVisible())
                .build();

    }


}

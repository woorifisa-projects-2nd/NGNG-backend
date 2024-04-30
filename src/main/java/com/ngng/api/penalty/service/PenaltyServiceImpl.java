package com.ngng.api.penalty.service;

import com.ngng.api.penalty.dto.ReadPenaltyListResponseDTO;
import com.ngng.api.penalty.dto.ReadPenaltyResponseDTO;
import com.ngng.api.penalty.entity.Penalty;
import com.ngng.api.penalty.repository.PenaltyLevelRepository;
import com.ngng.api.penalty.repository.PenaltyRepository;
import com.ngng.api.report.dto.ReadReportResponseDTO;
import com.ngng.api.report.entity.Report;
import com.ngng.api.report.repository.ReportRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PenaltyServiceImpl implements PenaltyService {
    private final PenaltyRepository penaltyRepository;
    private final PenaltyLevelRepository penaltyLevelRepository;
    private final ReportRepository reportRepository;

    @Override
    public List<ReadPenaltyListResponseDTO> findAll() {
        List<Penalty> penalties = new ArrayList<>();
        penaltyRepository.findAll().forEach(penalties::add);

        return penalties.stream()
                .map(ReadPenaltyListResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    public ReadPenaltyResponseDTO findById(Long reportId) {
        Penalty penalty = penaltyRepository.findByReportId(reportId).orElseThrow(() ->
                new EntityNotFoundException("penalty not found")
        );

        Report report = reportRepository.findById(reportId).orElseThrow(() ->
                new EntityNotFoundException("report not found")
        );

        return ReadPenaltyResponseDTO.builder()
                .penaltyId(penalty.getPenaltyId())
                .startDate(penalty.getStartDate())
                .endDate(penalty.getEndDate())
                .reason(penalty.getReason())
                .penaltyLevel(penalty.getPenaltyLevel())
                .report(ReadReportResponseDTO.builder()
                        .reportId(report.getReportId())
                        .productId(report.getProductId())
                        .build())
                .build();
    }

}

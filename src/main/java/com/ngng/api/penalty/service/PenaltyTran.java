package com.ngng.api.penalty.service;

import com.ngng.api.penalty.dto.CreatePenaltyRequestDTO;
import com.ngng.api.penalty.dto.CreatePenaltyResponseDTO;
import com.ngng.api.penalty.entity.Penalty;
import com.ngng.api.penalty.entity.PenaltyLevel;
import com.ngng.api.penalty.repository.PenaltyLevelRepository;
import com.ngng.api.penalty.repository.PenaltyRepository;
import com.ngng.api.product.entity.Product;
import com.ngng.api.product.repository.ProductRepository;
import com.ngng.api.report.entity.Report;
import com.ngng.api.report.repository.ReportRepository;
import com.ngng.api.report.service.ReportService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "penalty-log")
public class PenaltyTran {

    private final PenaltyRepository penaltyRepository;
    private final PenaltyLevelRepository penaltyLevelRepository;
    private final ReportRepository reportRepository;
    private final ReportService reportService;
    private final ProductRepository productRepository;

    @Transactional
    public CreatePenaltyResponseDTO penaltySaveAndReportUpdate(CreatePenaltyRequestDTO createPenaltyRequestDTO) {

        Long penaltyLevelId = createPenaltyRequestDTO.getPenaltyLevelId();
        PenaltyLevel penaltyLevel = penaltyLevelRepository.findById(penaltyLevelId).orElseThrow(() ->
                new EntityNotFoundException("penaltyLevel not found")
        );

        Long reportId = createPenaltyRequestDTO.getReportId();
        Report report = reportRepository.findById(reportId).orElseThrow(() ->
                new EntityNotFoundException("Report not found")
        );

        Timestamp banDate = new Timestamp(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();

        if(penaltyLevel.getPenaltyLevelId() == 1) {
            calendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(String.valueOf(penaltyLevel.getPenaltyLevelDays())));
            banDate = new Timestamp(calendar.getTimeInMillis());
        } else if(penaltyLevel.getPenaltyLevelId() == 2) {
            calendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(String.valueOf(penaltyLevel.getPenaltyLevelDays())));
            banDate = new Timestamp(calendar.getTimeInMillis());
        }
        else if(penaltyLevel.getPenaltyLevelId() == 3) {
            LocalDateTime banLocalDateTime = LocalDateTime.of(2038, 1, 19, 0, 0, 0);
            banDate = Timestamp.valueOf(banLocalDateTime);
            // TIMESTAMP : 2038-01-19 03:14:07’ UTC까지의 범위
        }

        Penalty penalty = Penalty.builder()
                .endDate(banDate)
                .userId(createPenaltyRequestDTO.getUserId())
                .reporterId(createPenaltyRequestDTO.getReporterId())
                .reason(createPenaltyRequestDTO.getReason())
                .penaltyLevel(PenaltyLevel.builder()
                        .penaltyLevelId(penaltyLevel.getPenaltyLevelId())
                        .penaltyLevelName(penaltyLevel.getPenaltyLevelName())
                        .penaltyLevelDays(penaltyLevel.getPenaltyLevelDays())
                        .build()
                )
                .reportId(createPenaltyRequestDTO.getReportId())
                .build();

        Penalty responsepenalty = penaltyRepository.save(penalty);

//        System.exit(0);

        updateIsReport(responsepenalty.getReportId(), true);
        updateProductForSale(report.getProductId(), false);

        log.info("Success Create Penalty id: {} User id : {} level : {} ",responsepenalty.getPenaltyId(),responsepenalty.getUserId(),responsepenalty.getPenaltyLevel());
        return CreatePenaltyResponseDTO.builder()
                .startDate(responsepenalty.getStartDate())
                .endDate(responsepenalty.getEndDate())
                .userId(responsepenalty.getUserId())
                .reporterId(responsepenalty.getReporterId())
                .reason(responsepenalty.getReason())
                .penaltyLevel(responsepenalty.getPenaltyLevel())
                .reportId(responsepenalty.getReportId())
                .createdAt(responsepenalty.getCreatedAt())
                .updatedAt(responsepenalty.getUpdatedAt())
                .build();

    }

    public void updateIsReport(Long reportId, Boolean isReport) {

        Report report = reportRepository.findById(reportId).orElseThrow(() ->
                new EntityNotFoundException("report not found")
        );

        report.setIsReport(isReport);

        reportRepository.save(report);

    }

    private void updateProductForSale(Long productId, Boolean forSale) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new EntityNotFoundException("product not found")
        );

        product.setForSale(forSale);

        productRepository.save(product);
    }
}

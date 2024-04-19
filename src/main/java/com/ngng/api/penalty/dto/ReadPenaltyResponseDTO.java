package com.ngng.api.penalty.dto;

import com.ngng.api.penalty.entity.PenaltyLevel;
import com.ngng.api.report.dto.ReadReportResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReadPenaltyResponseDTO {
    private Long penaltyId;
    private Timestamp startDate;
    private Timestamp endDate;
//    private Long userId;
//    private Long reporterId;
    private String reason;
    private PenaltyLevel penaltyLevel;
    private ReadReportResponseDTO report;
//    private Long reportId;

//    public static ReadPenaltyResponseDTO from(Penalty penalty) {
//
//        final Long penaltyId = penalty.getPenaltyId();
//        final Timestamp startDate = penalty.getStartDate();
//        final Timestamp endDate = penalty.getEndDate();
////        final Long userId = penalty.getUserId();
////        final Long reporterId = penalty.getReporterId();
//        final String reason = penalty.getReason();
//        final PenaltyLevel penaltyLevel = penalty.getPenaltyLevel();
//        final ReadReportResponseDTO report = penalty.getReport();
////        final Long reportId = penalty.getReportId();
//
//        return new ReadPenaltyResponseDTO(penaltyId, startDate, endDate, reason, penaltyLevel, report);
//
//    }
}

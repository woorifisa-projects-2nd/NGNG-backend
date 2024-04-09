package com.ngng.api.penalty.dto;

import com.ngng.api.penalty.entity.Penalty;
import com.ngng.api.penalty.entity.PenaltyLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReadPenaltyListResponseDTO {
    private Long penaltyId;
    private Timestamp startDate;
    private Timestamp endDate;
    private Long userId;
    private Long reporterId;
    private String reason;
    private PenaltyLevel penaltyLevel;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public static ReadPenaltyListResponseDTO from(Penalty penalty) {

        final Long penaltyId = penalty.getPenaltyId();
        final Timestamp startDate = penalty.getStartDate();
        final Timestamp endDate = penalty.getEndDate();
        final Long userId = penalty.getUserId();
        final Long reporterId = penalty.getReporterId();
        final String reason = penalty.getReason();
        final PenaltyLevel penaltyLevel = penalty.getPenaltyLevel();
        final Timestamp createdAt = penalty.getCreatedAt();
        final Timestamp updatedAt = penalty.getUpdatedAt();

        return new ReadPenaltyListResponseDTO(penaltyId, startDate, endDate, userId, reporterId, reason, penaltyLevel, createdAt, updatedAt);

    }
}

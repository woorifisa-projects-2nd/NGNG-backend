package com.ngng.api.Penalty.dto;

import com.ngng.api.Penalty.entity.Penalty;
import com.ngng.api.Penalty.entity.PenaltyLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReadPenaltyListResponseDTO {
    private Long penaltyId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long userId;
    private Long reporterId;
    private String reason;
    private PenaltyLevel penaltyLevel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ReadPenaltyListResponseDTO from(Penalty penalty) {

        final Long penaltyId = penalty.getPenaltyId();
        final LocalDateTime startDate = penalty.getStartDate();
        final LocalDateTime endDate = penalty.getEndDate();
        final Long userId = penalty.getUserId();
        final Long reporterId = penalty.getReporterId();
        final String reason = penalty.getReason();
        final PenaltyLevel penaltyLevel = penalty.getPenaltyLevel();
        final LocalDateTime createdAt = penalty.getCreatedAt();
        final LocalDateTime updatedAt = penalty.getUpdatedAt();

        return new ReadPenaltyListResponseDTO(penaltyId, startDate, endDate, userId, reporterId, reason, penaltyLevel, createdAt, updatedAt);

    }
}

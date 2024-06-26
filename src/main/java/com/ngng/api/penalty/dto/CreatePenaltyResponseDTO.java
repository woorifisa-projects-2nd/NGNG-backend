package com.ngng.api.penalty.dto;

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
public class CreatePenaltyResponseDTO {
    private Timestamp startDate;
    private Timestamp endDate;
    private Long userId;
    private Long reporterId;
    private String reason;
    private PenaltyLevel penaltyLevel;
    private Long reportId;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}

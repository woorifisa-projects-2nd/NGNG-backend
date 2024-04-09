package com.ngng.api.Penalty.dto;

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
public class CreatePenaltyResponseDTO {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long userId;
    private Long reporterId;
    private String reason;
    private PenaltyLevel penaltyLevel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

package com.ngng.api.Penalty.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreatePenaltyRequestDTO {
//    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long userId;
    private Long reporterId;
    private String reason;
    private Long penaltyLevelId;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;

}

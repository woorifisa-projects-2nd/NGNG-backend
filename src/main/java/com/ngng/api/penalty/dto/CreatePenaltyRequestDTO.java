package com.ngng.api.penalty.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreatePenaltyRequestDTO {
//    private Timestamp startDate;
    private Timestamp endDate;
    private Long userId;
    private Long reporterId;
    private String reason;
    private Long penaltyLevelId;
//    private Timestamp createdAt;
//    private Timestamp updatedAt;

}

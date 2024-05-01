package com.ngng.api.report.report.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateReportRequestDTO {
    private String reportContents;
    private Long reportTypeId;
    private Long reporterId;
    private Long userId;
    private Boolean isReport;
    private Long productId;
    private Long privateChatId;

}

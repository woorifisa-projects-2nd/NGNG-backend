package com.ngng.api.report.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DeleteReportResponseDTO {
    private Long reportId;
    private Boolean visible;
}

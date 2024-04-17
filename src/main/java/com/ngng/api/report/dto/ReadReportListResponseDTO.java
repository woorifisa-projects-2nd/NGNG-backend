package com.ngng.api.report.dto;

import com.ngng.api.report.entity.ReportType;
import com.ngng.api.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReadReportListResponseDTO {
    private Long reportId;
    private String reportContents;
    private ReportType reportType;
    private User reporter;
    private User user;
    private Boolean isReport;
    private Timestamp createdAt;
    private Long productId;
    private Long privateChatId;
    private Boolean visible;

}

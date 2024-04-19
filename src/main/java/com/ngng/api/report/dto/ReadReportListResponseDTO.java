package com.ngng.api.report.dto;

import com.ngng.api.report.entity.Report;
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

    public static ReadReportListResponseDTO from(Report report) {
        // report 엔티티에 담긴 개별 값들을 추출

        final Long reportId = report.getReportId();
        final String reportContents = report.getReportContents();
        final ReportType reportType = report.getReportType();
        final User reporter = report.getReporter();
        final User user = report.getUser();
        final Boolean isReport = report.getIsReport();
        final Timestamp createdAt = report.getCreatedAt();
        final Long productId = report.getProductId();
        final Long privateChatId = report.getPrivateChatId();
        final Boolean visible = report.getVisible();

        return new ReadReportListResponseDTO(reportId, reportContents, reportType, reporter, user, isReport, createdAt, productId, privateChatId, visible);

    }

}

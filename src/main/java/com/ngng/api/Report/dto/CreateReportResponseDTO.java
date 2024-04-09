package com.ngng.api.Report.dto;

import com.ngng.api.Report.entity.ReportType;
import com.ngng.api.User.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateReportResponseDTO {
    private Long reportId;
    private String reportContents;
    private ReportType reportType;
    private User reporter;
    private User user;
    private int isReport;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Long productId;
    private Long privateChatId;

}

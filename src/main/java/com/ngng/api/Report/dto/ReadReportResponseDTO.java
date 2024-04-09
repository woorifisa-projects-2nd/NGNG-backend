package com.ngng.api.Report.dto;

import com.ngng.api.Report.entity.ReportType;
import com.ngng.api.User.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReadReportResponseDTO {
    private Long reportId;
    private String reportContents;
    private ReportType reportType;
    private User reporter;
    private User user;
    private int isReport;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long productId;
    private Long privateChatId;
    private int visible;
    private List<ReportImageDTO> reportImages; // ReportImageDTO 리스트로 변경

}

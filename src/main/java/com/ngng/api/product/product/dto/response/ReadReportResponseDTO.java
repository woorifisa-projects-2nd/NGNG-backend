package com.ngng.api.product.product.dto.response;

import com.ngng.api.product.product.dto.UserDTO;
import com.ngng.api.report.report.entity.ReportType;
import lombok.*;

import java.sql.Timestamp;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadReportResponseDTO {
    Long reportId;
    String reportContents;
    ReportType reportType;
    UserDTO reporter;
    UserDTO user;
    Boolean isReport;
    Timestamp createdAt;
    Timestamp updatedAt;
}

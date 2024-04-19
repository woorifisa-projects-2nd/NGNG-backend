package com.ngng.api.product.dto.response;

import com.ngng.api.product.dto.UserDTO;
import com.ngng.api.report.entity.ReportType;
import com.ngng.api.user.entity.User;
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

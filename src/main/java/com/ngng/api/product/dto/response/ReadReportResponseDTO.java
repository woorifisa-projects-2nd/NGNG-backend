package com.ngng.api.product.dto.response;

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
    Long reportType;
    Long reporterId;
    Long userId;
    Boolean isReport;
    Timestamp createdAt;
    Timestamp updatedAt;
}

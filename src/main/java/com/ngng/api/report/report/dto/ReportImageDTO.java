package com.ngng.api.report.report.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReportImageDTO {
    private Long reportImageId;
    private String imageUrl;
    private String contentType;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}

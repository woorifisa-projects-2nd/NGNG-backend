package com.ngng.api.Report.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ngng.api.Report.dto.ReportImageDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자를 통해서 값 변경 목적으로 접근하는 메시지들 차단
@AllArgsConstructor
@Builder
@DynamicInsert
@Table(name = "report_image")
public class ReportImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportImageId;
    private String imageUrl;
//    private Long reportId;

    // 신고 이미지 리스트 추가
    @ManyToOne
    @JoinColumn(name = "report_id")
    @JsonIgnore
    private Report report;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public ReportImageDTO getReportImg() {
        return ReportImageDTO.builder()
                .reportImageId(this.reportImageId)
                .imageUrl(this.imageUrl)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}

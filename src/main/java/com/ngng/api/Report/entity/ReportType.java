package com.ngng.api.Report.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자를 통해서 값 변경 목적으로 접근하는 메시지들 차단
@AllArgsConstructor
@Builder
@Table(name = "report_type")
public class ReportType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportTypeId;

    private String reportType;
}

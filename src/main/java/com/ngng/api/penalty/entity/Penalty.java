package com.ngng.api.penalty.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Getter
@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자를 통해서 값 변경 목적으로 접근하는 메시지들 차단
@AllArgsConstructor
@Builder
@DynamicInsert
@Table(name = "penalty")
public class Penalty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long penaltyId;
    @CreationTimestamp
    private Timestamp startDate;
    private Timestamp endDate;
    private Long userId;
    private Long reporterId;
    private String reason;
    private Long reportId;

    @ManyToOne
    @JoinColumn(name = "penalty_level_id")
    private PenaltyLevel penaltyLevel;

//    @OneToOne
//    @JoinColumn(name = "report_id", referencedColumnName = "reportId") // 외래 키와 매핑
//    private Report report;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

}

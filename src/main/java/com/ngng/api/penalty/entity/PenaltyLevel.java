package com.ngng.api.penalty.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Getter
@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자를 통해서 값 변경 목적으로 접근하는 메시지들 차단
@AllArgsConstructor
@Builder
@DynamicInsert
@Table(name = "penalty_level")
public class PenaltyLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long penaltyLevelId;
    private String penaltyLevelName;
    private Long penaltyLevelDays;
}

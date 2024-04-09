package com.ngng.api.Report.entity;

import com.ngng.api.User.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자를 통해서 값 변경 목적으로 접근하는 메시지들 차단
@AllArgsConstructor
@Builder
@DynamicInsert
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;
    private String reportContents;

    @Column(name = "reporter_id")
    private Long reporterId;

    @Column(name = "user_id")
    private Long userId;

    private int isReport;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private Long productId;
    private Long privateChatId;
    private int visible;

    @ManyToOne
    @JoinColumn(name = "report_type_id")
    private ReportType reportType;

    @OneToOne
    @JoinColumn(name = "reporter_id", insertable = false, updatable = false)
    private User reporter;

    @OneToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    // 신고 이미지 리스트 추가
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "report")
    private final List<ReportImage> reportImages = new ArrayList<>();

}

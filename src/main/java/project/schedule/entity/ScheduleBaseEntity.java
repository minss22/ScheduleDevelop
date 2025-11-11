package project.schedule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

/**
 * BaseEntity 클래스
 * - 엔티티의 생성 및 수정 시간을 자동으로 관리
 * - JPA Auditing 사용
 */
@Getter
@MappedSuperclass // 하위 엔티티에 매핑 정보만 전달
@EntityListeners(AuditingEntityListener.class) // 엔티티의 생성/수정 이벤트를 감지해 자동으로 날짜를 업데이트
public abstract class ScheduleBaseEntity {

    /**
     * 엔티티가 처음 생성될 때 자동으로 저장되는 생성일시
     */
    @CreatedDate // 생성시점 자동 기록
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    /**
     * 엔티티가 수정될 때마다 자동으로 갱신되는 수정일시
     */
    @LastModifiedDate // 수정시점 자동 기록
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;
}
package project.schedule.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.schedule.dto.CreateScheduleRequest;
import project.schedule.dto.UpdateScheduleRequest;

/**
 * Schedule 클래스
 * - 일정 정보를 저장하는 Entity 클래스
 * - 일정 제목, 일정 내용, 유저명 저장
 * - 고유 식별자(ID)는 자동으로 생성하여 저장
 * - 작성일, 수정일 필드는 BaseEntity를 상속받아 자동으로 저장
 */
@Getter
@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends ScheduleBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 일정 고유 ID
    @Column(length = 50, nullable = false)
    private String name; // 작성 유저명
    @Column(length = 50, nullable = false)
    private String title; // 일정 제목
    @Column(nullable = false)
    private String content; // 일정 내용

    /**
     * 유저명, 일정 제목, 일정 내용 저장 (Create)
     * @param request 일정 생성 Request DTO
     */
    public Schedule(CreateScheduleRequest request) {
        this.name = request.getName();
        this.title = request.getTitle();
        this.content = request.getContent();
    }

    /**
     * 유저명, 일정 제목, 일정 내용 수정 (Update)
     * @param request 일정 수정 Request DTO
     */
    public void update(UpdateScheduleRequest request) {
        this.name = request.getName();
        this.title = request.getTitle();
        this.content = request.getContent();
    }
}

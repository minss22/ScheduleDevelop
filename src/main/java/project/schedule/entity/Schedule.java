package project.schedule.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.schedule.dto.CreateScheduleRequest;
import project.schedule.dto.UpdateScheduleRequest;
import project.user.entity.User;

/**
 * Schedule 클래스
 * - 일정 정보를 저장하는 Entity 클래스
 * - 일정 제목, 일정 내용, 유저 저장
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
    private String title; // 일정 제목
    @Column(nullable = false)
    private String content; // 일정 내용

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 유저

    /**
     * 유저명, 일정 제목, 일정 내용 저장
     * @param request 일정 생성 Request DTO
     * @param user 작성 유저
     */
    public Schedule(CreateScheduleRequest request, User user) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.user = user;
    }

    /**
     * 일정 제목, 일정 내용 수정
     * @param request 일정 수정 Request DTO
     */
    public void update(UpdateScheduleRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }
}

package project.schedule.dto;

import lombok.Getter;
import project.schedule.entity.Schedule;
import java.time.LocalDateTime;

/**
 * SchedulesResponse 클래스
 * - 전체 일정의 응답 정보를 담는 DTO 클래스
 */
@Getter
public class SchedulesResponse {
    private final Long id;
    private final String title;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    /**
     * Schedule Entity를 이용한 생성자
     * @param schedule 일정 Entity
     */
    public SchedulesResponse(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getModifiedAt();
    }
}

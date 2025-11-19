package project.schedule.dto;

import lombok.Getter;
import project.schedule.entity.Schedule;
import java.time.LocalDateTime;

/**
 * ScheduleResponse 클래스
 * - 일정 응답 정보를 담는 DTO 클래스
 */
@Getter
public class ScheduleResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final Long userId; // 일정 작성자 고유 ID
    private final String userName; // 일정 작성자 이름
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    /**
     * Schedule Entity를 이용하여 정보를 저장하는 생성자
     * @param schedule 일정 Entity
     */
    public ScheduleResponse(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.userId = schedule.getUser().getId();
        this.userName = schedule.getUser().getName();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getModifiedAt();
    }
}

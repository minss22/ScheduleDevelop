package project.schedule.dto;

import lombok.Getter;

/**
 * UpdateScheduleRequest 클래스
 * - 일정 수정 요청을 위한 DTO
 */
@Getter
public class UpdateScheduleRequest {
    private String name;
    private String title;
    private String content;
}

package project.schedule.dto;

import lombok.Getter;

/**
 * CreateScheduleRequest 클래스
 * - 일정 생성 요청을 위한 DTO 클래스
 */
@Getter
public class CreateScheduleRequest {
    private String title;
    private String content;
}

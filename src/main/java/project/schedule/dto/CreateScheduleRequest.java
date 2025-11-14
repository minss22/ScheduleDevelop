package project.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * CreateScheduleRequest 클래스
 * - 일정 생성 요청을 위한 DTO 클래스
 */
@Getter
public class CreateScheduleRequest {
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
}

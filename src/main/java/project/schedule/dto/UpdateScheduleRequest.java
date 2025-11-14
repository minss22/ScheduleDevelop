package project.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * UpdateScheduleRequest 클래스
 * - 일정 수정 요청을 위한 DTO
 */
@Getter
public class UpdateScheduleRequest {
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
}

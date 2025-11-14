package project.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * UpdateUserRequest 클래스
 * - 유저 수정 요청을 위한 DTO 클래스
 */
@Getter
public class UpdateUserRequest {
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;
}

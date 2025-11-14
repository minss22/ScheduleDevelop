package project.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * CreateUserRequest 클래스
 * - 회원가입(유저 생성) 요청을 위한 DTO 클래스
 */
@Getter
public class CreateUserRequest {
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;
    @Size(min = 4, max = 20, message = "비밀번호 크기제한: 4~20글자")
    private String password;
}

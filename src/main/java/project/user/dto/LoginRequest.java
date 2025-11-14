package project.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class LoginRequest {
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;
    private String password;
}

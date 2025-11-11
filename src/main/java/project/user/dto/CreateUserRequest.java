package project.user.dto;

import lombok.Getter;

/**
 * CreateUserRequest 클래스
 * - 유저 생성 요청을 위한 DTO 클래스
 */
@Getter
public class CreateUserRequest {
    private String name;
    private String email;
}

package project.user.dto;

import lombok.Getter;

/**
 * UpdateUserRequest 클래스
 * - 유저 수정 요청을 위한 DTO 클래스
 */
@Getter
public class UpdateUserRequest {
    private String name;
    private String email;
}

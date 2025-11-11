package project.user.dto;

import lombok.Getter;
import project.user.entity.User;

import java.time.LocalDateTime;

/**
 * UserResponse 클래스
 * - 유저 응답 정보를 담는 DTO 클래스
 */
@Getter
public class UserResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getModifiedAt();
    }
}

package project.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.user.dto.CreateUserRequest;
import project.user.dto.UpdateUserRequest;

/**
 * User 클래스
 * - 유저 정보를 저장하는 Entity 클래스
 * - 유저명, 이메일 저장
 * - 고유 식별자(ID)는 자동으로 생성하여 저장
 * - 작성일, 수정일 필드는 UserBaseEntity 상속받아 자동으로 저장
 */
@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends UserBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 유저 고유 ID
    @Column(length = 50, nullable = false)
    private String name; // 유저명
    @Column(unique = true, nullable = false)
    private String email; // 이메일

    /**
     * 유저명, 이메일 저장 (Create)
     * @param request 유저 생성 Request DTO
     */
    public User(CreateUserRequest request) {
        this.name = request.getName();
        this.email = request.getEmail();
    }

    /**
     * 유저명, 이메일 수정 (Update)
     * @param request 유저 수정 Request DTO
     */
    public void update(UpdateUserRequest request) {
        this.name = request.getName();
        this.email = request.getEmail();
    }

}

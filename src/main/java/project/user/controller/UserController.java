package project.user.controller;

import project.user.dto.*;
import project.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 유저 생성: 회원가입
     * @param request 유저 생성 Request DTO
     * @return 생성된 유저 Response DTO와 201(CREATED) 상태 코드
     */
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@RequestBody CreateUserRequest request) {
        UserResponse result = userService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * 유저 선택 조회
     * @param userId 유저 고유 ID
     * @return 조회된 유저 Response DTO와 200(OK) 상태 코드
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getOneUser(@PathVariable Long userId) {
        UserResponse result = userService.getOne(userId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 유저 전체 조회
     * @return 조회된 유저 Response DTO 리스트와 200(OK) 상태 코드
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUser() {
        List<UserResponse> result = userService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 유저 수정
     * @param userId 유저 고유 ID
     * @param request 유저 수정 Request DTO
     * @return 수정된 유저 Response DTO와 200(OK) 상태 코드
     */
    @PutMapping("/users/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId, @RequestBody UpdateUserRequest request) {
        UserResponse result = userService.update(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 유저 삭제
     * @param userId 유저 고유 ID
     * @return 204(NO_CONTENT) 상태 코드
     */
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

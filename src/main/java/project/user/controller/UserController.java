package project.user.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import project.config.UnauthorizedException;
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
    public ResponseEntity<UserResponse> signup(
            @Valid @RequestBody CreateUserRequest request
    ) {
        UserResponse result = userService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * 로그인
     * @param request 로그인 Request DTO
     * @param session 유저의 로그인 상태
     * @return 세션 정보와 200(OK) 상태 코드
     */
    @PostMapping("/login")
    public ResponseEntity<SessionUser> login(
            @Valid @RequestBody LoginRequest request,
            HttpSession session
    ) {
        SessionUser sessionUser = userService.login(request); // 로그인한 유저 정보를 담는 DTO
        session.setAttribute("loginUser", sessionUser); // key: "loginUser", value: sessionUser

        return ResponseEntity.status(HttpStatus.OK).body(sessionUser);
    }

    /**
     * 로그아웃
     * @param sessionUser 로그인한 유저 정보를 담은 DTO
     * @param session 유저의 로그인 상태
     * @return 성공 시 204(NO_CONTENT) 상태 코드, 로그인 안 했을 경우 401(Unauthorized) 상태 코드
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            HttpSession session
    ) {
        if (sessionUser == null) throw new UnauthorizedException();

        session.invalidate(); // 세션을 무효화
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 유저 선택 조회
     * @param sessionUser 로그인한 유저 정보를 담은 DTO
     * @return 유저 Response DTO와 200(OK) 상태 코드, 로그인 안 했을 경우 401(Unauthorized) 상태 코드
     */
    @GetMapping("/users")
    public ResponseEntity<UserResponse> getOneUser(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser
    ) {
        if (sessionUser == null) throw new UnauthorizedException();

        UserResponse result = userService.getOne(sessionUser.getId());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 유저 전체 조회
     * @return 조회된 유저 Response DTO 리스트와 200(OK) 상태 코드
     */
    @GetMapping("/users/all")
    public ResponseEntity<List<UserResponse>> getAllUser() {
        List<UserResponse> result = userService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 유저 수정
     * @param sessionUser 로그인한 유저 정보를 담은 DTO
     * @param request 유저 수정 Request DTO
     * @return 수정된 유저 Response DTO와 200(OK) 상태 코드, 로그인 안 했을 경우 401(Unauthorized) 상태 코드
     */
    @PutMapping("/users")
    public ResponseEntity<UserResponse> updateUser(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        if (sessionUser == null) throw new UnauthorizedException();

        UserResponse result = userService.update(sessionUser.getId(), request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 유저 삭제: 탈퇴
     * @param sessionUser 로그인한 유저 정보를 담은 DTO
     * @return 204(NO_CONTENT) 상태 코드, 로그인 안 했을 경우 401(Unauthorized) 상태 코드
     */
    @DeleteMapping("/users")
    public ResponseEntity<Void> deleteUser(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            HttpSession session
    ) {
        if (sessionUser == null) throw new UnauthorizedException();

        userService.delete(sessionUser.getId());
        session.invalidate(); // 세션을 무효화
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

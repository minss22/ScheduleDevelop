package project.user.service;

import project.schedule.entity.Schedule;
import project.user.dto.*;
import project.user.entity.User;
import project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // 회원가입
    @Transactional
    public UserResponse save(CreateUserRequest request) {
        User user = new User(request);
        User saved = userRepository.save(user);

        return new UserResponse(saved);
    }

    // 로그인
    @Transactional(readOnly = true)
    public SessionUser login(LoginRequest request) {
        // 이메일 조회
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new IllegalStateException("없는 유저입니다.")
        );

        // 비밀번호 일치 여부
        if (!user.getPassword().equals(request.getPassword()))
            throw new IllegalStateException("비밀번호가 틀렸습니다."); // 예외 처리

        return new SessionUser(user);
    }

    // 단건 조회
    @Transactional(readOnly = true)
    public UserResponse getOne(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("없는 유저입니다.")
        );

        return new UserResponse(user);
    }

    // 다건 조회
    @Transactional(readOnly = true)
    public List<UserResponse> getAll() {
        List<User> users = userRepository.findAll();

        return users.stream().map(UserResponse::new).toList();
    }

    // 수정
    @Transactional
    public UserResponse update(Long id, UpdateUserRequest request) {
        User user = findOrThrow(id);

        user.update(request); // 선택한 유저
        userRepository.flush(); // 변경내용 DB에 동기화해서 수정일 갱신

        return new UserResponse(user);
    }

    // 삭제
    @Transactional
    public void delete(Long id) {
        boolean exist = userRepository.existsById(id);

        if (!exist) throw new IllegalStateException("없는 유저입니다.");

        userRepository.deleteById(id);
    }

    // id를 기준으로 조회, null이면 예외 처리
    private User findOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 일정입니다."));
    }
}

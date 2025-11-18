package project.user.service;

import project.config.EmailAlreadyExistsException;
import project.config.PasswordMismatchException;
import project.config.UserNotFoundException;
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
        // 이미 이메일이 존재하는 경우 예외 처리
        if (userRepository.existsByEmail(request.getEmail()))
            throw new EmailAlreadyExistsException();

        User user = new User(request);
        User saved = userRepository.save(user);

        return new UserResponse(saved);
    }

    // 로그인
    @Transactional(readOnly = true)
    public SessionUser login(LoginRequest request) {
        // 이메일 조회
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(UserNotFoundException::new);

        // 이메일과 비밀번호가 일치하지 않을 경우 예외 처리
        if (!user.getPassword().equals(request.getPassword()))
            throw new PasswordMismatchException();

        return new SessionUser(user);
    }

    // 단건 조회
    @Transactional(readOnly = true)
    public UserResponse getOne(Long id) {
        // 유저 조회
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

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
        // 이미 이메일이 존재하는 경우 예외 처리
        if (userRepository.existsByEmail(request.getEmail()))
            throw new EmailAlreadyExistsException();

        // 유저 조회
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        user.update(request); // 선택한 유저의 정보 업데이트
        userRepository.flush(); // 변경내용 DB에 동기화해서 수정일 갱신

        return new UserResponse(user);
    }

    // 삭제
    @Transactional
    public void delete(Long id) {
        boolean exist = userRepository.existsById(id);
        if (!exist) throw new UserNotFoundException();

        userRepository.deleteById(id);
    }
}

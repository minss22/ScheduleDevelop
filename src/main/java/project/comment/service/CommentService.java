package project.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.comment.dto.CommentResponse;
import project.comment.dto.CreateCommentRequest;
import project.comment.dto.UpdateCommentRequest;
import project.comment.entity.Comment;
import project.comment.repository.CommentRepository;
import project.config.CommentNotFoundException;
import project.config.ScheduleNotFoundException;
import project.config.UserNotFoundException;
import project.schedule.entity.Schedule;
import project.schedule.repository.ScheduleRepository;
import project.user.entity.User;
import project.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public CommentResponse save(Long userId, Long scheduleId, CreateCommentRequest request) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new); // 유저 조회
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(ScheduleNotFoundException::new); // 일정 조회

        Comment comment = new Comment(request, user, schedule); // Entity에 댓글 저장
        Comment saved = commentRepository.save(comment); // DB에 Entity 저장

        return new CommentResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getAll(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(ScheduleNotFoundException::new); // 일정 조회

        // 특정 일정의 전체 댓글 조회
        List<Comment> comments = commentRepository.findBySchedule(schedule);

        return comments.stream().map(CommentResponse::new).toList();
    }

    public CommentResponse update(Long userId, Long commentId, UpdateCommentRequest request) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new); // 유저 조회

        // 댓글 ID와 유저가 모두 일치해야 댓글 조회 → 유저가 작성한 댓글만 수정하도록 하기 위해
        Comment comment = commentRepository.findByIdAndUser(commentId, user).orElseThrow(CommentNotFoundException::new);
        comment.update(request);
        scheduleRepository.flush(); // 변경내용 DB에 동기화해서 수정일 갱신

        return new CommentResponse(comment);
    }

    public void delete(Long userId, Long commentId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new); // 유저 조회

        // 댓글 ID와 유저가 모두 일치해야 댓글 조회 → 유저가 작성한 댓글만 삭제하도록 하기 위해
        boolean exist = commentRepository.existsByIdAndUser(commentId, user);
        if (!exist) throw new CommentNotFoundException(); // 없으면 예외 처리

        commentRepository.deleteById(commentId); // 댓글 삭제
    }
}

package project.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.comment.dto.CommentResponse;
import project.comment.entity.Comment;
import project.schedule.entity.Schedule;
import project.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findBySchedule(Schedule schedule);

    Optional<Comment> findByIdAndUser(Long commentId, User user);

    boolean existsByIdAndUser(Long commentId, User user);
}

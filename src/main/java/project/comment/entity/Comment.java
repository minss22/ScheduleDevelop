package project.comment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.comment.dto.CreateCommentRequest;
import project.comment.dto.UpdateCommentRequest;
import project.schedule.entity.Schedule;
import project.user.entity.User;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends CommentBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 댓글 고유 ID
    @Column(nullable = false)
    private String content; // 댓글 내용

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 유저 (댓글 작성자)

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule; // 일정

    public Comment(CreateCommentRequest request, User user, Schedule schedule) {
        this.content = request.getContent();
        this.user = user;
        this.schedule = schedule;
    }

    public void update(UpdateCommentRequest request) {
        this.content = request.getContent();
    }
}

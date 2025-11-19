package project.comment.dto;

import lombok.Getter;
import project.comment.entity.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {
    private final Long id;
    private final String content;
    private final Long userId; // 댓글 작성자 고유 ID
    private final String userName; // 댓글 작성자 이름
    private final Long scheduleId; // 일정 고유 ID
    private final String scheduleTitle; // 일정 제목
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userId = comment.getUser().getId();
        this.userName = comment.getUser().getName();
        this.scheduleId = comment.getSchedule().getId();
        this.scheduleTitle = comment.getSchedule().getTitle();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getModifiedAt();
    }
}

package project.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.comment.dto.CommentResponse;
import project.comment.dto.CreateCommentRequest;
import project.comment.dto.UpdateCommentRequest;
import project.comment.service.CommentService;
import project.config.UnauthorizedException;
import project.user.dto.SessionUser;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/users/schedules/{scheduleId}/comments")
    public ResponseEntity<CommentResponse> createComment(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long scheduleId,
            @Valid @RequestBody CreateCommentRequest request
    ) {
        if (sessionUser == null) throw new UnauthorizedException(); // 로그인 상태 확인

        CommentResponse result = commentService.save(sessionUser.getId(), scheduleId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 댓글 조회
    @GetMapping("/users/schedules/{scheduleId}/comments")
    public ResponseEntity<List<CommentResponse>> getAllComments(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long scheduleId
    ) {
        if (sessionUser == null) throw new UnauthorizedException(); // 로그인 상태 확인

        List<CommentResponse> result = commentService.getAll(scheduleId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 댓글 수정
    @PutMapping("/users/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long commentId,
            @Valid @RequestBody UpdateCommentRequest request
    ) {
        if (sessionUser == null) throw new UnauthorizedException(); // 로그인 상태 확인

        CommentResponse result = commentService.update(sessionUser.getId(), commentId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 댓글 삭제
    @DeleteMapping("/users/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<List<Void>> deleteComment(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long commentId
    ) {
        if (sessionUser == null) throw new UnauthorizedException(); // 로그인 상태 확인

        commentService.delete(sessionUser.getId(), commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

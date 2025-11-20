package project.config;

/**
 * 조회하려는 댓글이 없을 때 예외 처리
 */
public class CommentNotFoundException extends ServiceException {
    public CommentNotFoundException() {
        super(ErrorMessage.NOT_FOUND_COMMENT);
    }
}

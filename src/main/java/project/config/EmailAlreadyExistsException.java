package project.config;

/**
 * 회원가입 시 이미 이메일이 존재하는 경우 예외 처리
 */
public class EmailAlreadyExistsException extends ServiceException {
    public EmailAlreadyExistsException() {
        super(ErrorMessage.DUPLICATED_EMAIL);
    }
}

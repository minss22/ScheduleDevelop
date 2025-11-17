package project.config;

/**
 * 로그인 시 이메일과 비밀번호가 일치하지 않을 경우 예외 처리
 */
public class PasswordMismatchException extends ServiceException{
    public PasswordMismatchException() {
        super(ErrorMessage.INVALID_PASSWORD); // 401 상태코드 지정
    }
}

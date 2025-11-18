package project.config;

/**
 * 로그인이 되지 않은 상태에서 보호된 기능 호출 시 예외 처리
 */
public class UnauthorizedException extends ServiceException {
    public UnauthorizedException() {
        super(ErrorMessage.LOGIN_REQUIRED);
    }
}

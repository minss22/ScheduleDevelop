package project.config;

/**
 * 조회하려는 유저가 없을 때 예외 처리
 */
public class UserNotFoundException extends ServiceException{
    public UserNotFoundException() {
        super(ErrorMessage.NOT_FOUND_USER);
    }
}

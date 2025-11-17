package project.config;

import lombok.Getter;

/**
 * 공통 상위 커스텀 에러 클래스
 */
@Getter
public class ServiceException extends RuntimeException {

    private final ErrorMessage errorMessage;

    public ServiceException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }
}
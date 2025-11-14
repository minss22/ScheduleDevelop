package project.config;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 공통 상위 커스텀 에러 클래스
 */
@Getter
public class ServiceException extends RuntimeException {

    private final HttpStatus status;

    public ServiceException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
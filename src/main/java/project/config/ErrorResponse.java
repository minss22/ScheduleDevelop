package project.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final int status; // 상태
    private final String code; // 코드
    private final String message; // 메시지

    public ErrorResponse(ErrorMessage errorCode) {
        this.status = errorCode.getStatus().value();
        this.code = errorCode.name();
        this.message = errorCode.getMessage();
    }
}
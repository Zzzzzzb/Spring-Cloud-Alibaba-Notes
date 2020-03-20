package com.stackingrule.usercenter.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionErrorHandler {

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ErrorBody> error(SecurityException e) {

        log.error("发生SecurityException异常", e);
        return new ResponseEntity<>(
                ErrorBody.builder()
                        .body("Token非法，用户不允许访问!!!")
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .build(),
                HttpStatus.UNAUTHORIZED
        );

    }

}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class ErrorBody {
    private String body;
    private int status;
}


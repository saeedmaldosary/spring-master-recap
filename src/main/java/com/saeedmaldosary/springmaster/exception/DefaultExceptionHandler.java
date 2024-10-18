package com.saeedmaldosary.springmaster.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;
import java.util.List;

@ControllerAdvice
public class DefaultExceptionHandler {
    @ExceptionHandler(ResourcesNotFoundException.class)
    public ResponseEntity<ApiError> handle(ResourcesNotFoundException e,
                                           HttpServletRequest request) {
new ApiError(
        request.getRequestURI(),
        e.getMessage(),
        HttpStatus.NOT_FOUND.value(),
        ZonedDateTime.now(),
        List.of()
);
return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handle(Exception e,
                                           HttpServletRequest request) {
        new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ZonedDateTime.now(),
                List.of()
        );
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

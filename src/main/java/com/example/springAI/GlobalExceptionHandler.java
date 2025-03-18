package com.example.springAI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String OPEN_AI_CLIENT_RAISED_EXCEPTION = "Open AI client raised an exception";

    ObjectMapper objectMapper = new ObjectMapper();
    @ExceptionHandler(NonTransientAiException.class)
    ProblemDetail handleOpenAiHttpException(NonTransientAiException ex) throws JsonProcessingException {
        HttpStatus status = Optional
                .ofNullable(HttpStatus.resolve(ex.hashCode())).orElse(HttpStatus.BAD_REQUEST);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        problemDetail.setTitle(OPEN_AI_CLIENT_RAISED_EXCEPTION);
        return problemDetail;
    }
}

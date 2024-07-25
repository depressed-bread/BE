package likelion_insideout.emotion.api_expense_post.controller;

import likelion_insideout.emotion.api_expense_post.model.dto.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "likelion_insideout.emotion.api_expense_post.controller")
public class CustomException {

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandle(IllegalArgumentException e) {
        ErrorResult errorResult = new ErrorResult("BAD Argument", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

}

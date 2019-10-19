package by.milosh.controller;

import by.milosh.validator.CallValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CallControllerHelper {

    private final CallValidator callValidator;

    @Autowired
    public CallControllerHelper(CallValidator callValidator) {
        this.callValidator = callValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(callValidator);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleError(MethodArgumentNotValidException exception) {
        List<ObjectError> allErrors = exception.getBindingResult().getAllErrors();
        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(allErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList()));
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.TEXT_PLAIN)
                .body(exception.getMessage());
    }
}

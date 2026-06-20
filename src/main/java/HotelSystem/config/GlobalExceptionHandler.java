package HotelSystem.config;

import HotelSystem.dto.ErrorResponse;
import HotelSystem.exception.AlreadyExistException;
import HotelSystem.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<String>> handleException(
            Exception exception,
            HttpServletRequest request) {
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse<String>(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST,
                        exception.getMessage(),
                        request.getRequestURI()));
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ErrorResponse<String>> handleAlreadyExistException(
            AlreadyExistException exception,
            HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse<String>(
                        LocalDateTime.now(),
                        HttpStatus.CONFLICT,
                        exception.getMessage(),
                        request.getRequestURI()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleUserNotFoundException(
            ResourceNotFoundException exception,
            HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse<String>(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND,
                        exception.getMessage(),
                        request.getRequestURI()));
    }
}

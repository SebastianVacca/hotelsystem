package HotelSystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorResponse<T> {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private T message;
    private String path;

    public ErrorResponse(LocalDateTime timestamp, HttpStatus status, T message, String path) {
        this(timestamp, status.value(), status.getReasonPhrase(), message, path);
    }

    public ErrorResponse(LocalDateTime timestamp, Integer status, String error, T message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public T getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}

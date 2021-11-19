package org.example.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class TaskExceptionHandler {
    @Data
    private static class TaskException {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        @JsonProperty(value = "time")
        private ZonedDateTime zonedDateTime;

        @JsonProperty(value = "status")
        private HttpStatus httpStatus;

        private String message;

        public TaskException(String message) {
            this.zonedDateTime = ZonedDateTime.now();
            this.httpStatus = HttpStatus.NOT_FOUND;
            this.message = message;
        }
    }

    @ExceptionHandler(value = {TaskNotFoundException.class})
    public ResponseEntity<Object> handlerTaskNotFoundException(TaskNotFoundException e) {
        TaskException taskNotFoundException = new TaskException(e.getMessage());
        return new ResponseEntity<>(taskNotFoundException, HttpStatus.NOT_FOUND);
    }
}
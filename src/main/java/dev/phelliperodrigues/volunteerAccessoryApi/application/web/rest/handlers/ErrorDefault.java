package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.handlers;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDefault {

    private LocalDateTime timestamp;
    private HttpStatus status;
    private List<String> errors;
    private String cause;


    // Generate functions to DLS attributes
    public ErrorDefault() {
    }

    public ErrorDefault(LocalDateTime timestamp, HttpStatus status, List<String> errors, String cause) {
        this.timestamp = timestamp;
        this.status = status;
        this.errors = errors;
        this.cause = cause;
    }

    //Generate method builder and build
    public static ErrorDefaultBuilder builder() {
        return new ErrorDefaultBuilder();
    }

    public static class ErrorDefaultBuilder {

        private LocalDateTime timestamp;
        private HttpStatus status;
        private List<String> errors;
        private String cause;

        public ErrorDefaultBuilder() {
        }

        public ErrorDefaultBuilder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ErrorDefaultBuilder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public ErrorDefaultBuilder errors(List<String> errors) {
            this.errors = errors;
            return this;
        }

        public ErrorDefaultBuilder cause(String cause) {
            this.cause = cause;
            return this;
        }

        public ErrorDefault build() {
            return new ErrorDefault(timestamp, status, errors, cause);
        }

        public String toString() {
            return "ErrorDefault.ErrorDefaultBuilder(timestamp=" + this.timestamp + ", status=" + this.status + ", errors=" + this.errors + ", cause=" + this.cause + ")";
        }
    }


}
package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.handlers;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(Date timestamp, String status, int statusCode, String message, String error,
                       String classThrowable) {
}

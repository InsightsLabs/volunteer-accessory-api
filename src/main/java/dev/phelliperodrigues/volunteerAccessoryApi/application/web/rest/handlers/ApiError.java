package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.handlers;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(String status, String message, String error) {
}

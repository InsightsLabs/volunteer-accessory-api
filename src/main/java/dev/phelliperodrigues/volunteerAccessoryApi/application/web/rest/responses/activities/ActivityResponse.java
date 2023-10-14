package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.responses.activities;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.activities.Activity;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.enums.BookType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivityResponse {
    private UUID id;
    private String name;
    private BookType bookType;

    public static ActivityResponse build(Activity activity) {
        return ActivityResponse.builder()
                .id(activity.getId())
                .name(activity.getName())
                .bookType(activity.getBookType())
                .build();
    }
}

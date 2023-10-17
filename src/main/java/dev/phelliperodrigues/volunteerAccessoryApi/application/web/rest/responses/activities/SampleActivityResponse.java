package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.responses.activities;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.activities.Activity;
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
public class SampleActivityResponse {
    private UUID id;

    public static SampleActivityResponse build(Activity activity) {
        if (activity != null)
            return SampleActivityResponse.builder()
                    .id(activity.getId())
                    .build();
        return null;
    }
}

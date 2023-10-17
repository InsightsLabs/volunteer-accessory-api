package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.requests.activities;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.activities.Activity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SampleActivityRequest {
    @NotNull(message = "Campo id é obrigatório")
    private UUID id;

    public Activity toDomain() {
        return Activity.builder()
                .id(this.id)
                .build();
    }
}

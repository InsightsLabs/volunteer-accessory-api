package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.requests.sector;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.sector.Sector;
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
public class SampleSectorRequest {

    @NotNull(message = "Campo id é obrigatório")
    private UUID id;

    public Sector toDomain() {
        return Sector.builder()
                .id(this.id)
                .build();
    }
}

package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.requests.subsector;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.subsector.SubSector;
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
public class SampleSubSectorRequest {
    @NotNull(message = "Campo id é obrigatório")
    private UUID id;


    public SubSector toDomain() {
        return SubSector.builder()
                .id(this.id)
                .build();
    }
}

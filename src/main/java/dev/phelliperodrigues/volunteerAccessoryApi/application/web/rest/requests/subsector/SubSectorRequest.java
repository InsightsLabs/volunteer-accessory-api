package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.requests.subsector;

import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.requests.sector.SampleSectorRequest;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.subsector.SubSector;
import jakarta.validation.constraints.NotEmpty;
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
public class SubSectorRequest {
    private UUID id;
    @NotEmpty(message = "O campo \"nome\" é obrigatório")
    private String name;
    private String observations;
    private boolean active;
    @NotNull(message = "O sub setor precisa conter um setor valido")
    private SampleSectorRequest sector;

    public SubSector toDomain() {
        return SubSector.builder()
                .id(this.id)
                .name(this.name)
                .observations(this.observations)
                .active(this.active)
                .sector(this.sector.toDomain())
                .build();
    }
}

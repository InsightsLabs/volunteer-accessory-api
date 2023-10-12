package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.requests.sector;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.sector.Sector;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SectorRequest {

    private UUID id;
    @NotEmpty(message = "O campo \"nome\" é obrigatório")
    private String name;
    private String observations;
    private boolean active;

    public Sector toDomain() {
        return Sector.builder()
                .id(this.id)
                .name(this.name)
                .observations(this.observations)
                .active(this.active)
                .build();
    }
}

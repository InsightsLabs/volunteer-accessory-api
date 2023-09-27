package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.requests;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.Sector;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SectorRequest {
    @NotEmpty(message = "O campo \"nome\" é obrigatório")
    private String name;
    private String observations;
    private boolean active;

    public Sector toSector() {
        return Sector.builder()
                .name(this.name)
                .observations(this.observations)
                .active(this.active)
                .build();
    }
}

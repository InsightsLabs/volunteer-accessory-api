package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.responses;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.Sector;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectorResponse {
    private UUID id;
    private String name;
    private String observations;
    private boolean active;


    public static SectorResponse build(Sector sector) {
        return SectorResponse.builder()
                .id(sector.getId())
                .name(sector.getName())
                .observations(sector.getObservations())
                .active(sector.isActive())
                .build();
    }
}

package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.responses.sector;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.sector.Sector;
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
public class SectorResponse {
    private UUID id;
    private String name;
    private String observations;
    private Boolean active;


    public static SectorResponse build(Sector sector) {
        return SectorResponse.builder()
                .id(sector.getId())
                .name(sector.getName())
                .observations(sector.getObservations())
                .active(sector.getActive())
                .build();
    }
}

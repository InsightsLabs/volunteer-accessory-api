package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.responses.subsector;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.responses.sector.SampleSectorResponse;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.subsector.SubSector;
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
public class SubSectorResponse {
    private UUID id;
    private String name;
    private String observations;
    private Boolean active;
    private SampleSectorResponse sector;


    public static SubSectorResponse build(SubSector subSector) {
        return SubSectorResponse.builder()
                .id(subSector.getId())
                .name(subSector.getName())
                .observations(subSector.getObservations())
                .active(subSector.getActive())
                .sector(SampleSectorResponse.build(subSector.getSector()))
                .build();
    }
}

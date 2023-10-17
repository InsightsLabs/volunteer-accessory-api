package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.responses.subsector;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class SampleSubSectorResponse {
    private UUID id;


    public static SampleSubSectorResponse build(SubSector subSector) {
        if (subSector != null)
            return SampleSubSectorResponse.builder()
                    .id(subSector.getId())
                    .build();
        return null;
    }
}

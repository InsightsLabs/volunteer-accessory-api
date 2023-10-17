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
public class SampleSectorResponse {
    private UUID id;


    public static SampleSectorResponse build(Sector sector) {
        if (sector != null)
            return SampleSectorResponse.builder()
                    .id(sector.getId())
                    .build();
        return null;
    }
}

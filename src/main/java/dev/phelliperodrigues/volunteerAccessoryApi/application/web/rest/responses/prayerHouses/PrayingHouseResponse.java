package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.responses.prayerHouses;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.responses.activities.SampleActivityResponse;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.responses.sector.SampleSectorResponse;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.responses.subsector.SampleSubSectorResponse;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.prayerHouses.PrayingHouse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrayingHouseResponse {
    private UUID id;
    private String name;
    private SampleSectorResponse sector;
    private SampleSubSectorResponse subSector;
    private List<SampleActivityResponse> activities = new ArrayList<>();

    public static PrayingHouseResponse build(PrayingHouse entity) {
        return PrayingHouseResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .sector(SampleSectorResponse.build(entity.getSector()))
                .subSector(SampleSubSectorResponse.build(entity.getSubSector()))
                .activities(entity.getActivities().stream().map(SampleActivityResponse::build).toList())
                .build();
    }
}

package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.requests.prayerHouses;

import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.requests.activities.SampleActivityRequest;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.requests.sector.SampleSectorRequest;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.requests.subsector.SampleSubSectorRequest;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.prayerHouses.PrayingHouse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrayingHouseRequest {

    private UUID id;
    @NotEmpty(message = "O campo \"nome\" é obrigatório")
    private String name;

    @NotNull(message = "A casa de oração precisa perterncer a um setor")
    private SampleSectorRequest sector;

    private SampleSubSectorRequest subSector;

    private List<SampleActivityRequest> activities = new ArrayList<>();


    public PrayingHouse toDomain() {
        return PrayingHouse.builder()
                .id(this.id)
                .name(this.name)
                .subSector(this.subSector != null ? this.subSector.toDomain() : null)
                .sector(this.sector != null ? this.sector.toDomain() : null)
                .activities(this.activities != null ? this.activities.stream().map(SampleActivityRequest::toDomain).toList() : List.of())
                .build();
    }
}

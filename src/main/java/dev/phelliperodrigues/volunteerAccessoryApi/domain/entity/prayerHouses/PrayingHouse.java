package dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.prayerHouses;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.activities.Activity;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.sector.Sector;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.subsector.SubSector;
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
public class PrayingHouse {
    private UUID id;
    private String name;
    private Sector sector;
    private SubSector subSector;
    private List<Activity> activities = new ArrayList<>();
    private UUID createUserId;
    private UUID updateUserId;


    public void update(PrayingHouse domain) {
        this.name = domain.getName();
        this.updateUserId = domain.getUpdateUserId();
        this.sector = domain.getSector();
        this.subSector = domain.getSubSector();
        this.activities = domain.getActivities();
    }

    public List<Activity> getActivities() {
        if (this.activities == null) {
            return List.of();
        }
        return this.activities;
    }

}

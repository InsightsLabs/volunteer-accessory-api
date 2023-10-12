package dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.sector;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.subsector.SubSector;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

import static dev.phelliperodrigues.volunteerAccessoryApi.utils.UUID.fromStrg;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sector {
    private UUID id;
    private String name;
    private String observations;
    private Boolean active;
    private UUID createUserId;
    private UUID updateUserId;
    private Set<SubSector> subSectors;


    public boolean isActive() {
        if (this.active == null)
            return false;
        return this.active;
    }

    public void update(Sector sector) {
        this.name = sector.getName();
        this.observations = sector.getObservations();
        this.active = sector.getActive();
        this.updateUserId = sector.getUpdateUserId();
    }

    public static class SectorBuilder {
        public SectorBuilder idByString(String id) {
            if (id != null)
                this.id = fromStrg(id);
            return this;
        }
    }

}

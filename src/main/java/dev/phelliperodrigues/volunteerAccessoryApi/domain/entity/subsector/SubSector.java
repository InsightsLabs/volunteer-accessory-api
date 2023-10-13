package dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.subsector;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.sector.Sector;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static dev.phelliperodrigues.volunteerAccessoryApi.utils.UUID.fromStrg;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubSector {
    private UUID id;
    private String name;
    private String observations;
    private Boolean active;
    private UUID createUserId;
    private UUID updateUserId;
    private Sector sector;


    public boolean isActive() {
        if (this.active == null)
            return false;
        return this.active;
    }

    public void update(SubSector subSector) {
        this.name = subSector.getName();
        this.observations = subSector.getObservations();
        this.active = subSector.getActive();
        this.updateUserId = subSector.getUpdateUserId();
        this.sector = subSector.getSector();
    }

    public static class SubSectorBuilder {
        public SubSectorBuilder idByString(String id) {
            if (id != null)
                this.id = fromStrg(id);
            return this;
        }
    }

}

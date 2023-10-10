package dev.phelliperodrigues.volunteerAccessoryApi.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

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


    public boolean isActive() {
        if (this.active == null)
            return false;
        return this.active;
    }

    public void update(Sector sector) {
        this.id = sector.getId();
        this.name = sector.getName();
        this.observations = sector.getObservations();
        this.active = sector.getActive();
        this.updateUserId = sector.getUpdateUserId();
    }

    public static class SectorBuilder {
        public SectorBuilder idByString(String id) {
            if (id != null)
                this.id = UUID.fromString(id);
            return this;
        }
    }

}

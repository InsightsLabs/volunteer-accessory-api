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
    private boolean active;
    private UUID createUserId;
    private UUID updateUserId;

    public static class SectorBuilder {
        public SectorBuilder idByString(String id) {
            if (id != null)
                this.id = UUID.fromString(id);
            return this;
        }
    }

}

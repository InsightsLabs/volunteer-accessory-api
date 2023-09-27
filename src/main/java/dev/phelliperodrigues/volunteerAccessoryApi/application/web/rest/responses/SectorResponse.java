package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.Sector;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectorResponse {
    private UUID id;
    private String name;
    private String observations;
    private boolean active;
    @JsonProperty("create_at")
    private LocalDateTime createdAt;
    @JsonProperty("update_at")
    private LocalDateTime updatedAt;
    @JsonProperty("create_user_id")
    private UUID createUserId;
    @JsonProperty("update_user_id")
    private UUID updateUserId;


    public static SectorResponse build(Sector sector) {
        return SectorResponse.builder()
                .id(sector.getId())
                .name(sector.getName())
                .observations(sector.getObservations())
                .active(sector.isActive())
                .createdAt(sector.getCreatedAt())
                .updatedAt(sector.getUpdatedAt())
                .createUserId(sector.getCreateUserId())
                .updateUserId(sector.getUpdateUserId())
                .build();
    }
}

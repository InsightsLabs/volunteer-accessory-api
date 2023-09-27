package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
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
}

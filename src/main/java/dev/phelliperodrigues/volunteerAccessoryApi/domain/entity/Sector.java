package dev.phelliperodrigues.volunteerAccessoryApi.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

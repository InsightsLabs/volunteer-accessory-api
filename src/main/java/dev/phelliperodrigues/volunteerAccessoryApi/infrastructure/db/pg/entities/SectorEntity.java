package dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.entities;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.Sector;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sectors")
public class SectorEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String name;

    private String observations;

    private boolean active;

    @Column(name = "create_user_id", nullable = false, updatable = false)
    private UUID createUserId;

    @Column(name = "update_user_id")
    private UUID updateUserId;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    public static SectorEntity from(Sector sector) {
        return SectorEntity.builder()
                .id(sector.getId())
                .name(sector.getName())
                .observations(sector.getObservations())
                .active(sector.isActive())
                .createUserId(sector.getCreateUserId())
                .updateUserId(sector.getUpdateUserId())
                .build();
    }

    public Sector toSector() {
        return Sector.builder()
                .id(this.id)
                .name(this.name)
                .observations(this.observations)
                .active(this.active)
                .build();
    }
}

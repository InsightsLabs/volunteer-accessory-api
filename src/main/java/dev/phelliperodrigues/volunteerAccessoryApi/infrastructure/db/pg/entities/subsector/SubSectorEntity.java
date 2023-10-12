package dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.entities.subsector;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.subsector.SubSector;
import dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.entities.sector.SectorEntity;
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
@Table(name = "sub_sectors",
        indexes = {@Index(name = "idx_sub_sector_name", columnList = "name")}
)
public class SubSectorEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String name;

    private String observations;

    private boolean active;

    @ManyToOne
    @JoinColumn(name = "sector_id")
    private SectorEntity sector;

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

    public static SubSectorEntity from(SubSector sector) {
        return SubSectorEntity.builder()
                .id(sector.getId())
                .name(sector.getName())
                .observations(sector.getObservations())
                .active(sector.isActive())
                .sector(sector.getSector() != null ? SectorEntity.builder().id(sector.getSector().getId()).build() : null)
                .createUserId(sector.getCreateUserId())
                .updateUserId(sector.getUpdateUserId())
                .build();
    }

    public SubSector toDomain() {
        return SubSector.builder()
                .id(this.id)
                .name(this.name)
                .observations(this.observations)
                .active(this.active)
                .sector(this.sector != null ? this.sector.toDomain() : null)
                .createUserId(this.createUserId)
                .updateUserId(this.updateUserId)
                .build();
    }

    public SubSector toDomainWithoutSector() {
        return SubSector.builder()
                .id(this.id)
                .name(this.name)
                .observations(this.observations)
                .active(this.active)
                .createUserId(this.createUserId)
                .updateUserId(this.updateUserId)
                .build();
    }
}

package dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.entities.sector;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.sector.Sector;
import dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.entities.subsector.SubSectorEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sectors",
        indexes = {@Index(name = "sectors_name_text_pattern_ops_idx", columnList = "name")}
)
public class SectorEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    private String observations;

    private Boolean active;

    @OneToMany(mappedBy = "sector", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubSectorEntity> subSectors;

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
                .subSectors(sector.getSubSectors() != null ? sector.getSubSectors().stream().map(SubSectorEntity::from).collect(Collectors.toSet()) : Set.of())
                .build();
    }

    public Sector toDomain() {
        return Sector.builder()
                .id(this.id)
                .name(this.name)
                .observations(this.observations)
                .active(this.active)
                .createUserId(this.createUserId)
                .updateUserId(this.updateUserId)
                .subSectors(this.subSectors != null ? this.subSectors.stream().map(SubSectorEntity::toDomainWithoutSector).collect(Collectors.toSet()) : null)
                .build();
    }
}

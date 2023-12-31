package dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.entities.prayerHouses;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.activities.Activity;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.prayerHouses.PrayingHouse;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.sector.Sector;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.subsector.SubSector;
import dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.entities.activities.ActivityEntity;
import dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.entities.sector.SectorEntity;
import dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.entities.subsector.SubSectorEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "prayer_houses",
        indexes = {
                @Index(name = "prayer_houses_name_text_pattern_ops_idx", columnList = "name"),
                @Index(name = "prayer_houses_sector_id_text_idx", columnList = "sector_id"),
                @Index(name = "prayer_houses_sub_sector_id_text_idx", columnList = "sub_sector_id")
        }
)
public class PrayingHouseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "sector_id", nullable = false)
    private SectorEntity sector;

    @ManyToOne
    @JoinColumn(name = "sub_sector_id")
    private SubSectorEntity subSector;

    @ManyToMany
    @JoinTable(
            name = "prayer_house_activities",
            joinColumns = @JoinColumn(name = "praying_house_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    private List<ActivityEntity> activities = new ArrayList<>();


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

    public static PrayingHouseEntity from(PrayingHouse entity) {
        return PrayingHouseEntity.builder()
                .id(entity.getId())
                .name(entity.getName())
                .sector(SectorEntity.from(entity.getSector()))
                .subSector(SubSectorEntity.from(entity.getSubSector()))
                .activities(entity.getActivities().stream().map(ActivityEntity::from).toList())
                .createUserId(entity.getCreateUserId())
                .updateUserId(entity.getUpdateUserId())
                .build();
    }

    public PrayingHouse toDomain() {
        return PrayingHouse.builder()
                .id(this.id)
                .name(this.name)
                .sector(this.getSectorDomain())
                .subSector(this.getSubSectorDomain())
                .activities(this.getActivityDomain())
                .createUserId(this.createUserId)
                .updateUserId(this.updateUserId)
                .build();
    }

    public List<ActivityEntity> getActivities() {
        if (this.activities == null) {
            return List.of();
        }
        return this.activities;
    }

    private Sector getSectorDomain() {
        if (this.sector == null)
            return null;
        return this.sector.toDomain();
    }

    private SubSector getSubSectorDomain() {
        if (this.subSector == null)
            return null;
        return this.subSector.toDomain();
    }

    private List<Activity> getActivityDomain() {
        if (this.activities == null) {
            return List.of();
        }
        return this.activities.stream().map(ActivityEntity::toDomain).toList();
    }

}

package dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.entities.activities;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.activities.Activity;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.enums.BookType;
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
@Table(name = "activities",
        indexes = {
                @Index(name = "activities_name_text_pattern_ops_idx", columnList = "name"),
                @Index(name = "activities_bookType_text_pattern_ops_idx", columnList = "book_type")
        }
)
public class ActivityEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "book_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private BookType bookType;

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

    public static ActivityEntity from(Activity entity) {
        return ActivityEntity.builder()
                .id(entity.getId())
                .name(entity.getName())
                .bookType(entity.getBookType())
                .createUserId(entity.getCreateUserId())
                .updateUserId(entity.getUpdateUserId())
                .build();
    }

    public Activity toDomain() {
        return Activity.builder()
                .id(this.id)
                .name(this.name)
                .bookType(this.bookType)
                .createUserId(this.createUserId)
                .updateUserId(this.updateUserId)
                .build();
    }
}

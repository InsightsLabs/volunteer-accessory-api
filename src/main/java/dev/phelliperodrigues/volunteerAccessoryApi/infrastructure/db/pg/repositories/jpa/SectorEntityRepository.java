package dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.repositories.jpa;

import dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.entities.SectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SectorEntityRepository extends JpaRepository<SectorEntity, UUID> {
}
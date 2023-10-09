package dev.phelliperodrigues.volunteerAccessoryApi.domain.repositories;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.Sector;

import java.util.Optional;
import java.util.UUID;

public interface SectorRepository {

    Sector save(Sector sector);

    Optional<Sector> findById(UUID id);
}

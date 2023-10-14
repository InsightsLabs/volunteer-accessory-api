package dev.phelliperodrigues.volunteerAccessoryApi.domain.repositories.sector;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.sector.Sector;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SectorRepository {

    Sector save(Sector sector);

    Optional<Sector> findById(UUID id);

    Page<Sector> findAllBy(Sector sector, Pageable pageable);

    void deleteById(UUID id);

    void deleteAllByIdInBatch(List<UUID> id);
}

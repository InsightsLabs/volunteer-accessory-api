package dev.phelliperodrigues.volunteerAccessoryApi.domain.repositories.subsector;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.subsector.SubSector;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface SubSectorRepository {

    SubSector save(SubSector sector);

    Optional<SubSector> findById(UUID id);

    Page<SubSector> findAllBy(SubSector sector, Pageable pageable);

    void deleteById(UUID id);
}

package dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.repositories;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.Sector;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.repositories.SectorRepository;
import dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.entities.SectorEntity;
import dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.repositories.jpa.SectorEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;


@Primary
@Component
@RequiredArgsConstructor
public class SectorRepositoryImpl implements SectorRepository {

    private final SectorEntityRepository sectorEntityRepository;

    @Override
    public Sector save(Sector sector) {
        var sectorEntity = sectorEntityRepository.save(SectorEntity.from(sector));
        return sectorEntity.toSector();
    }

    @Override
    public Optional<Sector> findById(UUID id) {
        return Optional.empty();
    }
}

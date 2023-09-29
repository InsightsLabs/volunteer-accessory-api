package dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.repositories;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.Sector;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.repositories.SectorRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class SectorRepositoryImpl implements SectorRepository {
    @Override
    public Sector save(Sector sector) {

        return sector;
    }
}

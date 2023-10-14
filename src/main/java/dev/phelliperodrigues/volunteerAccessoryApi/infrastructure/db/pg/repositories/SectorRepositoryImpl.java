package dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.repositories;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.sector.Sector;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.repositories.sector.SectorRepository;
import dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.entities.sector.SectorEntity;
import dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.repositories.jpa.SectorEntityRepository;
import dev.phelliperodrigues.volunteerAccessoryApi.utils.RestPage;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Primary
@Component
@RequiredArgsConstructor
public class SectorRepositoryImpl implements SectorRepository {

    private final SectorEntityRepository repository;

    @Override
    @CacheEvict(value = "sectors", allEntries = true)
    public Sector save(Sector sector) {
        var sectorEntity = repository.save(SectorEntity.from(sector));
        return sectorEntity.toDomain();
    }

    @Override
    public Optional<Sector> findById(UUID id) {
        return repository.findById(id)
                .map(SectorEntity::toDomain);
    }

    @Override
    @Cacheable(value = "sectors")
    public RestPage<Sector> findAllBy(Sector sector, Pageable pageable) {
        Specification<SectorEntity> specification = Specification.where(null);

        if (sector.getActive() != null) {
            specification = specification.and(SectorEntityRepository.isActive(sector.getActive()));
        }

        if (sector.getName() != null) {
            specification = specification.and(SectorEntityRepository.likeName(sector.getName()));
        }

        if (sector.getId() != null) {
            specification = specification.and(SectorEntityRepository.byId(sector.getId()));
        }

        return new RestPage<>(
                repository.findAll(specification, pageable).map(SectorEntity::toDomain)
        );
    }

    @Override
    @CacheEvict(value = "sectors", allEntries = true)
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    @CacheEvict(value = "sectors", allEntries = true)
    public void deleteAllByIdInBatch(List<UUID> id) {
        repository.deleteAllByIdInBatch(id);
    }
}

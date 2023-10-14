package dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.repositories;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.subsector.SubSector;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.repositories.subsector.SubSectorRepository;
import dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.entities.subsector.SubSectorEntity;
import dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.repositories.jpa.SubSectorEntityRepository;
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
public class SubSectorRepositoryImpl implements SubSectorRepository {
    private final SubSectorEntityRepository repository;

    @Override
    @CacheEvict(value = "sub_sectors", allEntries = true)
    public SubSector save(SubSector sector) {
        var sectorEntity = repository.save(SubSectorEntity.from(sector));
        return sectorEntity.toDomain();
    }

    @Override
    public Optional<SubSector> findById(UUID id) {
        return repository.findById(id)
                .map(SubSectorEntity::toDomain);
    }

    @Override
    @Cacheable(value = "sub_sectors")
    public RestPage<SubSector> findAllBy(SubSector sector, Pageable pageable) {
        Specification<SubSectorEntity> specification = Specification.where(null);

        if (sector.getActive() != null) {
            specification = specification.and(SubSectorEntityRepository.isActive(sector.getActive()));
        }

        if (sector.getName() != null) {
            specification = specification.and(SubSectorEntityRepository.likeName(sector.getName()));
        }

        if (sector.getId() != null) {
            specification = specification.and(SubSectorEntityRepository.byId(sector.getId()));
        }

        if (sector.getSector() != null && sector.getSector().getName() != null) {
            specification = specification.and(SubSectorEntityRepository.likeSectorName(sector.getSector().getName()));
        }

        if (sector.getSector() != null && sector.getSector().getId() != null) {
            specification = specification.and(SubSectorEntityRepository.bySectorId(sector.getSector().getId()));
        }

        var results = repository.findAll(specification, pageable).map(SubSectorEntity::toDomain);
        return new RestPage<>(
                results
        );
    }

    @Override
    @CacheEvict(value = "sub_sectors", allEntries = true)
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    @CacheEvict(value = "sub_sectors", allEntries = true)
    public void deleteAllByIdInBatch(List<UUID> id) {
        repository.deleteAllByIdInBatch(id);
    }
}

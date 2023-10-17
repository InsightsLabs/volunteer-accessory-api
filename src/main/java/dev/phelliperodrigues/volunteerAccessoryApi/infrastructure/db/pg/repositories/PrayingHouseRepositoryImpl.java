package dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.repositories;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.prayerHouses.PrayingHouse;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.repositories.prayerHouses.PrayingHouseRepository;
import dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.entities.prayerHouses.PrayingHouseEntity;
import dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.repositories.jpa.PrayingHouseEntityRepository;
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
public class PrayingHouseRepositoryImpl implements PrayingHouseRepository {
    private final PrayingHouseEntityRepository repository;

    @Override
    @CacheEvict(value = "prayer_houses", allEntries = true)
    public PrayingHouse save(PrayingHouse domain) {
        var entity = repository.save(PrayingHouseEntity.from(domain));
        return entity.toDomain();
    }

    @Override
    public Optional<PrayingHouse> findById(UUID id) {
        return repository.findById(id)
                .map(PrayingHouseEntity::toDomain);
    }

    @Override
    @Cacheable(value = "prayer_houses")
    public RestPage<PrayingHouse> findAllBy(PrayingHouse domain, Pageable pageable) {
        Specification<PrayingHouseEntity> specification = Specification.where(null);


        if (domain.getName() != null) {
            specification = specification.and(PrayingHouseEntityRepository.likeName(domain.getName()));
        }

        if (domain.getId() != null) {
            specification = specification.and(PrayingHouseEntityRepository.byId(domain.getId()));
        }

        if (domain.getSector() != null && domain.getSector().getName() != null) {
            specification = specification.and(PrayingHouseEntityRepository.likeSectorName(domain.getSector().getName()));
        }

        if (domain.getSector() != null && domain.getSector().getId() != null) {
            specification = specification.and(PrayingHouseEntityRepository.bySectorId(domain.getSector().getId()));
        }

        if (domain.getSubSector() != null && domain.getSubSector().getName() != null) {
            specification = specification.and(PrayingHouseEntityRepository.likeSubSectorName(domain.getSubSector().getName()));
        }

        if (domain.getSubSector() != null && domain.getSubSector().getId() != null) {
            specification = specification.and(PrayingHouseEntityRepository.bySubSectorId(domain.getSubSector().getId()));
        }

        return new RestPage<>(
                repository.findAll(specification, pageable).map(PrayingHouseEntity::toDomain)
        );
    }

    @Override
    @CacheEvict(value = "prayer_houses", allEntries = true)
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    @CacheEvict(value = "prayer_houses", allEntries = true)
    public void deleteAllByIdInBatch(List<UUID> id) {
        repository.deleteAllByIdInBatch(id);
    }
}

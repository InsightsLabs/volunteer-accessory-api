package dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.repositories;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.activities.Activity;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.repositories.activities.ActivityRepository;
import dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.entities.activities.ActivityEntity;
import dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.repositories.jpa.ActivityEntityRepository;
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
public class ActivityRepositoryImpl implements ActivityRepository {
    private final ActivityEntityRepository repository;

    @Override
    @CacheEvict(value = "activities", allEntries = true)
    public Activity save(Activity domain) {
        var entity = repository.save(ActivityEntity.from(domain));
        return entity.toDomain();
    }

    @Override
    public Optional<Activity> findById(UUID id) {
        return repository.findById(id)
                .map(ActivityEntity::toDomain);
    }

    @Override
    @Cacheable(value = "activities")
    public RestPage<Activity> findAllBy(Activity domain, Pageable pageable) {
        Specification<ActivityEntity> specification = Specification.where(null);


        if (domain.getName() != null) {
            specification = specification.and(ActivityEntityRepository.likeName(domain.getName()));
        }

        if (domain.getId() != null) {
            specification = specification.and(ActivityEntityRepository.byId(domain.getId()));
        }

        if (domain.getBookType() != null) {
            specification = specification.and(ActivityEntityRepository.bookTypeIs(domain.getBookType()));
        }

        return new RestPage<>(
                repository.findAll(specification, pageable).map(ActivityEntity::toDomain)
        );
    }

    @Override
    @CacheEvict(value = "activities", allEntries = true)
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    @CacheEvict(value = "activities", allEntries = true)
    public void deleteAllByIdInBatch(List<UUID> id) {
        repository.deleteAllByIdInBatch(id);
    }
}

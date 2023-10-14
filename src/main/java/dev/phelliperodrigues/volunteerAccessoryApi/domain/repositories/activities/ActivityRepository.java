package dev.phelliperodrigues.volunteerAccessoryApi.domain.repositories.activities;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.activities.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ActivityRepository {

    Activity save(Activity sector);

    Optional<Activity> findById(UUID id);

    Page<Activity> findAllBy(Activity sector, Pageable pageable);

    void deleteById(UUID id);

    void deleteAllByIdInBatch(List<UUID> id);
}

package dev.phelliperodrigues.volunteerAccessoryApi.domain.services.activities;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.activities.Activity;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.repositories.activities.ActivityRepository;
import dev.phelliperodrigues.volunteerAccessoryApi.utils.Exceptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static dev.phelliperodrigues.volunteerAccessoryApi.utils.UUID.fromStrg;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository repository;

    public Activity create(Activity activity) {
        log.info("create activity");
        activity.setCreateUserId(UUID.randomUUID());
        var created = repository.save(activity);
        log.info("Activity {} created", created.getId());
        return created;

    }

    public Activity findById(String id) {
        var uuid = fromStrg(id);
        var activity = repository.findById(uuid)
                .orElseThrow(() -> Exceptions.notFoundException("Atividade não encontrada: " + id));
        log.info("Activity {} found", activity.getId());
        return activity;
    }

    public Page<Activity> findAllBy(Activity activity, Pageable pageable) {
        log.info("Find all activity by: {}", activity);
        var activities = repository.findAllBy(activity, pageable);
        log.info("Activities {} found", activities.getTotalElements());
        return activities;
    }

    public Activity update(Activity activity, String id) {
        log.info("Update activity");

        var found = findById(id);
        found.update(activity);

        var save = repository.save(found);
        log.info("Activity {} updated", save.getId());
        return save;
    }

    public void delete(UUID id) {
        repository.deleteById(id);
        log.info("Sub Activity {} deleted", id);

    }

    public void deleteAll(List<UUID> ids) {
        try {
            repository.deleteAllByIdInBatch(ids);
        } catch (Exception ex) {
            log.error("Error deleting", ex);
        }
    }
}

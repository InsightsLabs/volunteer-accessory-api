package dev.phelliperodrigues.volunteerAccessoryApi.domain.services.prayerHouses;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.prayerHouses.PrayingHouse;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.repositories.prayerHouses.PrayingHouseRepository;
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
public class PrayingHouseService {

    private final PrayingHouseRepository repository;

    public PrayingHouse create(PrayingHouse entity) {
        log.info("create entity");
        entity.setCreateUserId(UUID.randomUUID());
        var created = repository.save(entity);
        log.info("{} {} created", entity.getClass().getSimpleName(), created.getId());
        return created;

    }

    public PrayingHouse findById(String id) {
        var uuid = fromStrg(id);
        var entity = repository.findById(uuid)
                .orElseThrow(() -> Exceptions.notFoundException("Atividade não encontrada: " + id));
        log.info("{} {} found", entity.getClass().getSimpleName(), entity.getId());
        return entity;
    }

    public Page<PrayingHouse> findAllBy(PrayingHouse entity, Pageable pageable) {
        log.info("Find all entity by: {}", entity);
        var activities = repository.findAllBy(entity, pageable);
        log.info("{} {} found", entity.getClass().getSimpleName(), activities.getTotalElements());
        return activities;
    }

    public PrayingHouse update(PrayingHouse entity, String id) {
        log.info("Update entity");

        var found = findById(id);
        found.update(entity);

        var save = repository.save(found);
        log.info("{} {} updated", entity.getClass().getSimpleName(), save.getId());
        return save;
    }

    public void delete(UUID id) {
        repository.deleteById(id);
        log.info("{} deleted", id);

    }

    public void deleteAll(List<UUID> ids) {
        try {
            repository.deleteAllByIdInBatch(ids);
        } catch (Exception ex) {
            log.error("Error deleting", ex);
        }
    }
}

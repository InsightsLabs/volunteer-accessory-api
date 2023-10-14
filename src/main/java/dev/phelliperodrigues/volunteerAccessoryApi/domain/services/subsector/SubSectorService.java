package dev.phelliperodrigues.volunteerAccessoryApi.domain.services.subsector;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.subsector.SubSector;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.repositories.subsector.SubSectorRepository;
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
public class SubSectorService {

    private final SubSectorRepository repository;

    public SubSector create(SubSector subSector) {
        log.info("Create Sub Sector");
        subSector
                .setCreateUserId(UUID.randomUUID());
        var created = repository.save(subSector);
        log.info("Sub Sector {} created", created.getId());
        return created;
    }

    public SubSector findById(String id) {
        var uuid = fromStrg(id);
        var subSector = repository.findById(uuid)
                .orElseThrow(() -> Exceptions.notFoundException("Sub Setor não Encontrado: " + id));
        log.info("Sub Sector {} found", subSector
                .getId());
        return subSector
                ;
    }

    public Page<SubSector> findAllBy(SubSector subSector, Pageable pageable) {
        log.info("find all Sub Sector by: {}", subSector
        );
        var subSectors = repository.findAllBy(subSector, pageable);
        log.info("Sub Sectors {} found", subSectors.getTotalElements());
        return subSectors;
    }

    public SubSector update(SubSector subSector, String id) {
        log.info("update Sub Sector");

        var found = findById(id);
        found.update(subSector
        );

        var save = repository.save(found);
        log.info("Sub Sector {} updated", save.getId());
        return save;
    }

    public void delete(String id) {
        var found = findById(id);
        repository.deleteById(found.getId());
        log.info("Sub Sector {} deleted", id);

    }

    public void deleteAll(List<UUID> ids) {
        try {
            repository.deleteAllByIdInBatch(ids);
        } catch (Exception ex) {
            log.error("Error deleting", ex);
        }
    }
}

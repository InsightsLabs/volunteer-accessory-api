package dev.phelliperodrigues.volunteerAccessoryApi.domain.services.sector;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.sector.Sector;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.repositories.sector.SectorRepository;
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
public class SectorService {

    private final SectorRepository repository;

    public Sector create(Sector sector) {
        log.info("create sector");
        sector.setCreateUserId(UUID.randomUUID());
        var created = repository.save(sector);
        log.info("Sector {} created", created.getId());
        return created;
    }

    public Sector findById(String id) {
        var uuid = fromStrg(id);
        var sector = repository.findById(uuid)
                .orElseThrow(() -> Exceptions.notFoundException("Setor não Encontrado: " + id));
        log.info("Sector {} found", sector.getId());
        return sector;
    }

    public Page<Sector> findAllBy(Sector sector, Pageable pageable) {
        log.info("find all sector by: {}", sector);
        var sectors = repository.findAllBy(sector, pageable);
        log.info("Sectors {} found", sectors.getTotalElements());
        return sectors;
    }

    public Sector update(Sector sector, String id) {
        log.info("update sector");

        var found = findById(id);
        found.update(sector);

        var save = repository.save(found);
        log.info("Sector {} updated", save.getId());
        return save;
    }

    public void delete(String id) {
        var found = findById(id);
        repository.deleteById(found.getId());
        log.info("Sector {} deleted", id);

    }

    public void deleteAll(List<UUID> ids) {
        try {
            repository.deleteAllByIdInBatch(ids);
        } catch (Exception ex) {
            log.error("Error deleting", ex);
        }
    }
}

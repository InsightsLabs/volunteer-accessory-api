package dev.phelliperodrigues.volunteerAccessoryApi.domain.services;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.Sector;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.repositories.SectorRepository;
import dev.phelliperodrigues.volunteerAccessoryApi.utils.Exceptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static dev.phelliperodrigues.volunteerAccessoryApi.utils.UUID.fromStrg;

@Slf4j
@Service
@RequiredArgsConstructor
public class SectorService {

    private final SectorRepository sectorRepository;

    public Sector create(Sector sector) {
        log.info("create sector");
        sector.setCreateUserId(UUID.randomUUID());
        var created = sectorRepository.save(sector);
        log.info("Sector {} created", created.getId());
        return created;
    }

    public Sector findById(String id) {
        var uuid = fromStrg(id);
        var sector = sectorRepository.findById(uuid)
                .orElseThrow(() -> Exceptions.notFoundException("Setor não Encontrado: " + id));
        log.info("Sector {} found", sector.getId());
        return sector;
    }

    public Page<Sector> findAllBy(Sector sector, Pageable pageable) {
        log.info("find all sector by: {}", sector);
        var sectors = sectorRepository.findAllBy(sector, pageable);
        log.info("Sectors {} found", sectors.getTotalElements());
        return sectors;
    }

    public Sector update(Sector sector, String id) {
        log.info("update sector");

        var found = findById(id);
        found.update(sector);

        var save = sectorRepository.save(found);
        log.info("Sector {} updated", save.getId());
        return save;
    }

    public void delete(String id) {
        var found = findById(id);
        sectorRepository.deleteById(found.getId());
        log.info("Sector {} deleted", id);
        
    }
}

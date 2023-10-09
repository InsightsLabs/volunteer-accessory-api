package dev.phelliperodrigues.volunteerAccessoryApi.domain.services;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.Sector;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.repositories.SectorRepository;
import dev.phelliperodrigues.volunteerAccessoryApi.utils.Exceptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SectorService {

    private final SectorRepository sectorRepository;

    public Sector create(Sector sector) {
        sector.setCreateUserId(UUID.randomUUID());
        var created = sectorRepository.save(sector);
        log.info("Sector {} created", created.getId());
        return created;
    }

    public Sector findById(String id) {
        try {
            return sectorRepository.findById(UUID.fromString(id))
                    .orElseThrow(() -> Exceptions.notFoundException("Setor não Encontrado: " + id));

        } catch (IllegalArgumentException ex) {
            throw Exceptions.invalidIdException(id);
        }
    }
}

package dev.phelliperodrigues.volunteerAccessoryApi.domain.services;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.Sector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SectorService {

    public Sector create(Sector sector) {
        sector.setId(UUID.randomUUID());
        sector.setCreatedAt(LocalDateTime.now());
        sector.setCreateUserId(UUID.randomUUID());

        return sector;
    }
}

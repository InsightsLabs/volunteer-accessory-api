package dev.phelliperodrigues.volunteerAccessoryApi.domain.services;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.Sector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SectorServiceTest {

    private SectorService sectorService;

    @BeforeEach
    void setUp() {
        sectorService = new SectorService();
    }

    @Test
    @DisplayName("Should create sector with success")
    void create() {
        var sector = Sector.builder().build();
        var sectorCreated = sectorService.create(sector);

        Assertions.assertNotNull(sectorCreated);
        Assertions.assertNotNull(sectorCreated.getId());
        Assertions.assertEquals(sector.getName(), sectorCreated.getName());
        Assertions.assertEquals(sector.getObservations(), sectorCreated.getObservations());
        Assertions.assertEquals(sector.isActive(), sectorCreated.isActive());
        Assertions.assertNotNull(sectorCreated.getCreatedAt());
        Assertions.assertNull(sectorCreated.getUpdatedAt());
        Assertions.assertNotNull(sectorCreated.getCreateUserId());
        Assertions.assertNull(sectorCreated.getUpdateUserId());

    }
}

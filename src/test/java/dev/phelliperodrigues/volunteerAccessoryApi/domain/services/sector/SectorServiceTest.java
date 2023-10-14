package dev.phelliperodrigues.volunteerAccessoryApi.domain.services.sector;

import com.github.javafaker.Faker;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.sector.Sector;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.repositories.sector.SectorRepository;
import dev.phelliperodrigues.volunteerAccessoryApi.utils.FakerUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SectorServiceTest {

    private final Faker faker = FakerUtil.getInstance();


    @Mock
    private SectorRepository sectorRepository;

    @InjectMocks
    private SectorService sectorService;


    @Test
    @DisplayName("[create()] Should create sector with success")
    void create() {

        var sector = buildSector();

        when(sectorRepository.save(Mockito.any())).thenReturn(sector);

        var sectorCreated = sectorService.create(sector);

        Assertions.assertNotNull(sectorCreated);
        Assertions.assertNotNull(sectorCreated.getId());
        assertEquals(sector.getName(), sectorCreated.getName());
        assertEquals(sector.getObservations(), sectorCreated.getObservations());
        assertEquals(sector.isActive(), sectorCreated.isActive());
        verify(sectorRepository, times(1)).save(Mockito.any());

    }

    @Test
    @DisplayName("[findById()] Should return a sector with success")
    void findById() {

        var sector = buildSector();
        when(sectorRepository.findById(Mockito.any())).thenReturn(Optional.of(sector));

        var result = sectorService.findById(UUID.randomUUID().toString());


        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getId());
        assertEquals(sector.getName(), result.getName());
        assertEquals(sector.getObservations(), result.getObservations());
        assertEquals(sector.isActive(), result.isActive());
        verify(sectorRepository, times(1)).findById(Mockito.any());

    }

    @Test
    @DisplayName("[findById()] Should handle exception ResponseStatusException from NotFound when not match sector by id")
    void findByIdNotFound() {
        when(sectorRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        var throwable = assertThrows(ResponseStatusException.class, () -> sectorService.findById(UUID.randomUUID().toString()));

        assertEquals(HttpStatus.NOT_FOUND, throwable.getStatusCode());
        verify(sectorRepository, times(1)).findById(Mockito.any());

    }

    @Test
    @DisplayName("[findById()] Should handle exception ResponseStatusException from BadRequest when id is not valid")
    void findByIdBadRequest() {

        var throwable = assertThrows(ResponseStatusException.class, () -> sectorService.findById("123"));

        assertEquals(HttpStatus.BAD_REQUEST, throwable.getStatusCode());
        verify(sectorRepository, Mockito.never()).findById(Mockito.any());

    }

    private Sector buildSector() {
        return Sector.builder()
                .id(UUID.randomUUID())
                .name(faker.company().industry())
                .observations(faker.lorem().sentence(10))
                .active(faker.bool().bool())
                .build();
    }

    @Test
    @DisplayName("[findAllBy] Should return all sector by terms")
    void findAllBy() {
        var sector = buildSector();
        when(sectorRepository.findAllBy(Mockito.any(), Mockito.any())).thenReturn(new PageImpl<>(List.of(sector)));


        var result = sectorService.findAllBy(sector, Pageable.unpaged());


        Assertions.assertNotNull(result);
        org.assertj.core.api.Assertions.assertThat(result.getContent()).isNotEmpty();
        assertEquals(sector.getName(), result.getContent().stream().findFirst().map(Sector::getName).orElse(null));
        assertEquals(sector.getObservations(), result.getContent().stream().findFirst().map(Sector::getObservations).orElse(null));
        assertEquals(sector.isActive(), result.getContent().stream().findFirst().map(Sector::isActive).orElse(null));
        verify(sectorRepository, times(1)).findAllBy(Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("[findAllBy] Should return empty sectors by terms")
    void findAllByEmpty() {
        var sector = buildSector();
        when(sectorRepository.findAllBy(Mockito.any(), Mockito.any())).thenReturn(new PageImpl<>(List.of()));


        var result = sectorService.findAllBy(sector, Pageable.unpaged());


        Assertions.assertNotNull(result);
        org.assertj.core.api.Assertions.assertThat(result.getContent()).isEmpty();
        verify(sectorRepository, times(1)).findAllBy(Mockito.any(), Mockito.any());
    }


    @Test
    @DisplayName("[UPDATE] Should update the sector with successfully updated")
    void test_update_returnsUpdatedSector() {
        var id = UUID.randomUUID();
        var sector = Sector.builder()
                .id(id)
                .name("Test Sector")
                .observations("Test Observations")
                .active(true)
                .build();
        var updatedSector = Sector.builder()
                .id(id)
                .name("Updated Sector")
                .observations("Updated Observations")
                .active(false)
                .build();
        when(sectorRepository.findById(id)).thenReturn(Optional.of(sector));
        when(sectorRepository.save(any())).thenReturn(updatedSector);

        var result = sectorService.update(updatedSector, id.toString());

        assertEquals(updatedSector, result);
        verify(sectorRepository, times(1)).findById(id);
        verify(sectorRepository, times(1)).save(updatedSector);
    }

    @Test
    @DisplayName("[UPDATE] Should handle exception ResponseStatusException from NotFound when not match sector by id")
    void test_update_throwsNotFoundException() {
        UUID id = UUID.randomUUID();
        String sectorId = id.toString();

        when(sectorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> sectorService.update(new Sector(), sectorId));
    }

    @Test
    @DisplayName("[UPDATE] Should update name with passed value and observation with null")
    void test_update_updatesOnlyPresentFields() {
        UUID id = UUID.randomUUID();
        Sector sector = Sector.builder()
                .id(id)
                .name("Test Sector")
                .observations("Test Observations")
                .active(true)
                .build();
        Sector updatedSector = Sector.builder()
                .id(id)
                .name("Updated Sector")
                .build();
        String sectorId = id.toString();

        when(sectorRepository.findById(id)).thenReturn(Optional.of(sector));
        when(sectorRepository.save(any())).thenReturn(updatedSector);

        Sector result = sectorService.update(updatedSector, sectorId);

        assertEquals(updatedSector.getName(), result.getName());
        assertNull(result.getObservations());
        assertFalse(result.isActive());
        verify(sectorRepository, times(1)).findById(id);
        verify(sectorRepository, times(1)).save(updatedSector);
    }

    @Test
    void test_update_doesNotUpdateIdField() {
        UUID id = UUID.randomUUID();
        Sector sector = Sector.builder()
                .id(id)
                .name("Test Sector")
                .observations("Test Observations")
                .active(true)
                .build();
        Sector updatedSector = Sector.builder()
                .id(id)
                .name("Updated Sector")
                .observations("Updated Observations")
                .active(false)
                .build();
        String sectorId = id.toString();

        when(sectorRepository.findById(id)).thenReturn(Optional.of(sector));
        when(sectorRepository.save(any())).thenReturn(updatedSector);

        Sector result = sectorService.update(updatedSector, sectorId);

        assertEquals(id, result.getId());
        verify(sectorRepository, times(1)).findById(id);
        verify(sectorRepository, times(1)).save(updatedSector);
    }


    @Test
    @DisplayName("[DELETE] Given a valid sector id, the method should delete the sector from the database.")
    public void test_valid_sector_id() {
        UUID sectorId = UUID.randomUUID();

        sectorService.delete(sectorId);

        verify(sectorRepository).deleteById(sectorId);
    }

    @Test
    @DisplayName("[DELETE ALL] Delete all sectors in the list")
    public void test_deleteAll_deleteAllSubSectors() {
        List<UUID> ids = Arrays.asList(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());

        Mockito.doNothing().when(sectorRepository).deleteAllByIdInBatch(Mockito.any());

        sectorService.deleteAll(ids);

        Mockito.verify(sectorRepository, Mockito.times(1)).deleteAllByIdInBatch(Mockito.any());
    }
    
    @Test
    @DisplayName("[DELETE ALL] Delete all sectors in the list with exception")
    public void test_deleteAll_deleteAllSectors_with_exceptions() {
        List<UUID> ids = Arrays.asList(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());

        Mockito.doThrow(new IllegalStateException()).when(sectorRepository).deleteAllByIdInBatch(Mockito.any());

        sectorService.deleteAll(ids);

        Mockito.verify(sectorRepository, Mockito.times(1)).deleteAllByIdInBatch(Mockito.any());
    }


}



package dev.phelliperodrigues.volunteerAccessoryApi.domain.services;

import com.github.javafaker.Faker;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.Sector;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.repositories.SectorRepository;
import dev.phelliperodrigues.volunteerAccessoryApi.utils.FakerUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
    @Mock
    Logger log;

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
        assertEquals(sector.getName(), result.getContent().stream().findFirst().get().getName());
        assertEquals(sector.getObservations(), result.getContent().stream().findFirst().get().getObservations());
        assertEquals(sector.isActive(), result.getContent().stream().findFirst().get().isActive());
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
        // Arrange
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

        // Act
        var result = sectorService.update(updatedSector, id.toString());

        // Assert
        assertEquals(updatedSector, result);
        verify(sectorRepository, times(1)).findById(id);
        verify(sectorRepository, times(1)).save(updatedSector);
    }

    @Test
    @DisplayName("[UPDATE] Should handle exception ResponseStatusException from NotFound when not match sector by id")
    void test_update_throwsNotFoundException() {
        // Arrange
        UUID id = UUID.randomUUID();
        String sectorId = id.toString();

        when(sectorRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> sectorService.update(new Sector(), sectorId));
    }

    @Test
    @DisplayName("[UPDATE] Should update name with passed value and observation with null")
    void test_update_updatesOnlyPresentFields() {
        // Arrange
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

        // Act
        Sector result = sectorService.update(updatedSector, sectorId);

        // Assert
        assertEquals(updatedSector.getName(), result.getName());
        assertNull(result.getObservations());
        assertFalse(result.isActive());
        verify(sectorRepository, times(1)).findById(id);
        verify(sectorRepository, times(1)).save(updatedSector);
    }

    // Does not update the id field.
    @Test
    void test_update_doesNotUpdateIdField() {
        // Arrange
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

        // Act
        Sector result = sectorService.update(updatedSector, sectorId);

        // Assert
        assertEquals(id, result.getId());
        verify(sectorRepository, times(1)).findById(id);
        verify(sectorRepository, times(1)).save(updatedSector);
    }


    // Given a valid sector id, the method should delete the sector from the database.
    @Test
    @DisplayName("[DELETE] Given a valid sector id, the method should delete the sector from the database.")
    public void test_valid_sector_id() {
        // Mock dependencies
        UUID sectorId = UUID.randomUUID();
        Sector sector = new Sector();
        sector.setId(sectorId);

        // Stub findById method
        when(sectorRepository.findById(sectorId)).thenReturn(Optional.of(sector));

        // Call delete method
        sectorService.delete(sectorId.toString());

        // Verify that deleteById method was called with the correct sector id
        verify(sectorRepository).deleteById(sectorId);
    }

    @Test
    @DisplayName("[DELETE] Given a non-existent sector id, the method should not throw an exception and should not delete any sector.")
    public void test_non_existent_sector_id() {
        UUID sectorId = UUID.randomUUID();

        // Stub findById method to return empty optional
        when(sectorRepository.findById(sectorId)).thenReturn(Optional.empty());

        // Call delete method
        assertThrows(ResponseStatusException.class, () -> sectorService.delete(sectorId.toString()));

        // Verify that deleteById method was not called
        verify(sectorRepository, never()).deleteById(any(UUID.class));
    }

    // Given a null sector id, the method should throw an IllegalArgumentException.
    @Test
    @DisplayName("[DELETE] Given a null sector id, the method should throw an IllegalArgumentException.")
    public void test_null_sector_id() {
        // Call delete method with null sector id
        assertThrows(ResponseStatusException.class, () -> sectorService.delete(null));

        // Verify that deleteById method was not called
        verify(sectorRepository, never()).deleteById(any(UUID.class));
    }

    // Given an invalid sector id (not a UUID), the method should throw an IllegalArgumentException.
    @Test
    @DisplayName("[DELETE] Given an invalid sector id (not a UUID), the method should throw an IllegalArgumentException.")
    public void test_invalid_sector_id() {
        // Call delete method with invalid sector id
        assertThrows(ResponseStatusException.class, () -> sectorService.delete("invalid_id"));

        // Verify that deleteById method was not called
        verify(sectorRepository, never()).deleteById(any(UUID.class));
    }


}



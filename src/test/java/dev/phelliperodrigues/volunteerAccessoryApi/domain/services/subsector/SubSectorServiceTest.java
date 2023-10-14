package dev.phelliperodrigues.volunteerAccessoryApi.domain.services.subsector;

import com.github.javafaker.Faker;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.sector.Sector;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.subsector.SubSector;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.repositories.subsector.SubSectorRepository;
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
class SubSectorServiceTest {

    private final Faker faker = FakerUtil.getInstance();


    @Mock
    private SubSectorRepository sectorRepository;

    @InjectMocks
    private SubSectorService sectorService;


    @Test
    @DisplayName("[create()] Should create with success")
    void create() {

        var subSector = buildSubSector();

        when(sectorRepository.save(Mockito.any())).thenReturn(subSector);

        var saved = sectorService.create(subSector);

        Assertions.assertNotNull(saved);
        Assertions.assertNotNull(saved.getId());
        assertEquals(subSector.getName(), saved.getName());
        assertEquals(subSector.getObservations(), saved.getObservations());
        assertEquals(subSector.isActive(), saved.isActive());
        verify(sectorRepository, times(1)).save(Mockito.any());

    }

    @Test
    @DisplayName("[findById()] Should return with success")
    void findById() {

        var subSector = buildSubSector();
        when(sectorRepository.findById(Mockito.any())).thenReturn(Optional.of(subSector));

        var result = sectorService.findById(UUID.randomUUID().toString());


        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getId());
        assertEquals(subSector.getName(), result.getName());
        assertEquals(subSector.getObservations(), result.getObservations());
        assertEquals(subSector.isActive(), result.isActive());
        verify(sectorRepository, times(1)).findById(Mockito.any());

    }

    @Test
    @DisplayName("[findById()] Should handle exception ResponseStatusException from NotFound when not match by id")
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

    @Test
    @DisplayName("[findAllBy] Should return all by terms")
    void findAllBy() {
        var subSector = buildSubSector();
        when(sectorRepository.findAllBy(Mockito.any(), Mockito.any())).thenReturn(new PageImpl<>(List.of(subSector)));


        var result = sectorService.findAllBy(subSector, Pageable.unpaged());


        Assertions.assertNotNull(result);
        org.assertj.core.api.Assertions.assertThat(result.getContent()).isNotEmpty();
        assertEquals(subSector.getName(), result.getContent().stream().findFirst().map(SubSector::getName).orElse(null));
        assertEquals(subSector.getObservations(), result.getContent().stream().findFirst().map(SubSector::getObservations).orElse(null));
        assertEquals(subSector.isActive(), result.getContent().stream().findFirst().map(SubSector::isActive).orElse(null));
        verify(sectorRepository, times(1)).findAllBy(Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("[findAllBy] Should return empty by terms")
    void findAllByEmpty() {
        var sector = buildSubSector();
        when(sectorRepository.findAllBy(Mockito.any(), Mockito.any())).thenReturn(new PageImpl<>(List.of()));


        var result = sectorService.findAllBy(sector, Pageable.unpaged());


        Assertions.assertNotNull(result);
        org.assertj.core.api.Assertions.assertThat(result.getContent()).isEmpty();
        verify(sectorRepository, times(1)).findAllBy(Mockito.any(), Mockito.any());
    }


    @Test
    @DisplayName("[UPDATE] Should update with successfully updated")
    void test_update_returnsUpdatedSubSector() {
        var id = UUID.randomUUID();
        var subSector = SubSector.builder()
                .id(id)
                .name("Test SubSector")
                .observations("Test Observations")
                .active(true)
                .build();
        var updatedSubSector = SubSector.builder()
                .id(id)
                .name("Updated SubSector")
                .observations("Updated Observations")
                .active(false)
                .build();
        when(sectorRepository.findById(id)).thenReturn(Optional.of(subSector));
        when(sectorRepository.save(any())).thenReturn(updatedSubSector);

        var result = sectorService.update(updatedSubSector, id.toString());

        assertEquals(updatedSubSector, result);
        verify(sectorRepository, times(1)).findById(id);
        verify(sectorRepository, times(1)).save(updatedSubSector);
    }

    @Test
    @DisplayName("[UPDATE] Should handle exception ResponseStatusException from NotFound when not match by id")
    void test_update_throwsNotFoundException() {
        UUID id = UUID.randomUUID();
        String sectorId = id.toString();

        when(sectorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> sectorService.update(new SubSector(), sectorId));
    }

    @Test
    @DisplayName("[UPDATE] Should update name with passed value and observation with null")
    void test_update_updatesOnlyPresentFields() {
        UUID id = UUID.randomUUID();
        SubSector subSector = SubSector.builder()
                .id(id)
                .name("Test SubSector")
                .observations("Test Observations")
                .active(true)
                .build();
        SubSector updatedSubSector = SubSector.builder()
                .id(id)
                .name("Updated SubSector")
                .build();
        String sectorId = id.toString();

        when(sectorRepository.findById(id)).thenReturn(Optional.of(subSector));
        when(sectorRepository.save(any())).thenReturn(updatedSubSector);

        SubSector result = sectorService.update(updatedSubSector, sectorId);

        assertEquals(updatedSubSector.getName(), result.getName());
        assertNull(result.getObservations());
        assertFalse(result.isActive());
        verify(sectorRepository, times(1)).findById(id);
        verify(sectorRepository, times(1)).save(updatedSubSector);
    }

    @Test
    void test_update_doesNotUpdateIdField() {
        UUID id = UUID.randomUUID();
        SubSector sector = SubSector.builder()
                .id(id)
                .name("Test SubSector")
                .observations("Test Observations")
                .active(true)
                .build();
        SubSector updatedSubSector = SubSector.builder()
                .id(id)
                .name("Updated SubSector")
                .observations("Updated Observations")
                .active(false)
                .build();
        String sectorId = id.toString();

        when(sectorRepository.findById(id)).thenReturn(Optional.of(sector));
        when(sectorRepository.save(any())).thenReturn(updatedSubSector);

        SubSector result = sectorService.update(updatedSubSector, sectorId);

        assertEquals(id, result.getId());
        verify(sectorRepository, times(1)).findById(id);
        verify(sectorRepository, times(1)).save(updatedSubSector);
    }


    @Test
    @DisplayName("[DELETE] Given a valid id, the method should delete from the database.")
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
    public void test_deleteAll_deleteAllSubSectors_with_exceptions() {
        List<UUID> ids = Arrays.asList(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());

        Mockito.doThrow(new IllegalStateException()).when(sectorRepository).deleteAllByIdInBatch(Mockito.any());

        sectorService.deleteAll(ids);

        Mockito.verify(sectorRepository, Mockito.times(1)).deleteAllByIdInBatch(Mockito.any());
    }


    private SubSector buildSubSector() {
        return SubSector.builder()
                .id(UUID.randomUUID())
                .name(faker.company().industry())
                .observations(faker.lorem().sentence(10))
                .active(faker.bool().bool())
                .sector(Sector.builder()
                        .id(UUID.randomUUID())
                        .build())
                .build();
    }


}



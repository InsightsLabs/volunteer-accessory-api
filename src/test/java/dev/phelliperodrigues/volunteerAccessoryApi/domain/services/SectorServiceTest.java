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
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        Mockito.when(sectorRepository.save(Mockito.any())).thenReturn(sector);

        var sectorCreated = sectorService.create(sector);

        Assertions.assertNotNull(sectorCreated);
        Assertions.assertNotNull(sectorCreated.getId());
        assertEquals(sector.getName(), sectorCreated.getName());
        assertEquals(sector.getObservations(), sectorCreated.getObservations());
        assertEquals(sector.isActive(), sectorCreated.isActive());
        Mockito.verify(sectorRepository, Mockito.times(1)).save(Mockito.any());

    }

    @Test
    @DisplayName("[findById()] Should return a sector with success")
    void findById() {

        var sector = buildSector();
        Mockito.when(sectorRepository.findById(Mockito.any())).thenReturn(Optional.of(sector));

        var result = sectorService.findById(UUID.randomUUID());


        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getId());
        assertEquals(sector.getName(), result.getName());
        assertEquals(sector.getObservations(), result.getObservations());
        assertEquals(sector.isActive(), result.isActive());
        Mockito.verify(sectorRepository, Mockito.times(1)).findById(Mockito.any());

    }

    @Test
    @DisplayName("[findById()] Should handle exception ResponseStatusException from NotFound when not match sector by id")
    void findByIdNotFound() {
        Mockito.when(sectorRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        var throwable = Assertions.assertThrows(ResponseStatusException.class, () -> sectorService.findById(UUID.randomUUID().toString()));

        assertEquals(HttpStatus.NOT_FOUND, throwable.getStatusCode());

    }

    @Test
    @DisplayName("[findById()] Should handle exception ResponseStatusException from BadRequest when id is not valid")
    void findByIdBadRequest() {
        Mockito.when(sectorRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        var throwable = Assertions.assertThrows(ResponseStatusException.class, () -> sectorService.findById("123"));

        assertEquals(HttpStatus.BAD_REQUEST, throwable.getStatusCode());

    }

    private Sector buildSector() {
        return Sector.builder()
                .id(UUID.randomUUID())
                .name(faker.company().industry())
                .observations(faker.lorem().sentence(10))
                .active(faker.bool().bool())
                .build();
    }

}

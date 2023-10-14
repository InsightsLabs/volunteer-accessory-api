package dev.phelliperodrigues.volunteerAccessoryApi.domain.services.activities;

import com.github.javafaker.Faker;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.activities.Activity;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.enums.BookType;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.repositories.activities.ActivityRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivityServiceTest {

    private final Faker faker = FakerUtil.getInstance();


    @Mock
    private ActivityRepository repository;

    @InjectMocks
    private ActivityService service;


    @Test
    @DisplayName("[create()] Should create with success")
    void create() {

        var entity = buildEntity();

        when(repository.save(Mockito.any())).thenReturn(entity);

        var result = service.create(entity);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getId());
        assertEquals(entity.getName(), result.getName());
        assertEquals(entity.getBookType(), result.getBookType());
        verify(repository, times(1)).save(Mockito.any());

    }

    @Test
    @DisplayName("[findById()] Should return with success")
    void findById() {

        var entity = buildEntity();
        when(repository.findById(Mockito.any())).thenReturn(Optional.of(entity));

        var result = service.findById(UUID.randomUUID().toString());


        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getId());
        assertEquals(entity.getName(), result.getName());
        assertEquals(entity.getBookType(), result.getBookType());
        verify(repository, times(1)).findById(Mockito.any());

    }

    @Test
    @DisplayName("[findById()] Should handle exception ResponseStatusException from NotFound when not match by id")
    void findByIdNotFound() {
        when(repository.findById(Mockito.any())).thenReturn(Optional.empty());

        var throwable = assertThrows(ResponseStatusException.class, () -> service.findById(UUID.randomUUID().toString()));

        assertEquals(HttpStatus.NOT_FOUND, throwable.getStatusCode());
        verify(repository, times(1)).findById(Mockito.any());

    }

    @Test
    @DisplayName("[findById()] Should handle exception ResponseStatusException from BadRequest when id is not valid")
    void findByIdBadRequest() {

        var throwable = assertThrows(ResponseStatusException.class, () -> service.findById("123"));

        assertEquals(HttpStatus.BAD_REQUEST, throwable.getStatusCode());
        verify(repository, Mockito.never()).findById(Mockito.any());

    }

    @Test
    @DisplayName("[findAllBy] Should return all registers by terms")
    void findAllBy() {
        var activity = buildEntity();
        when(repository.findAllBy(Mockito.any(), Mockito.any())).thenReturn(new PageImpl<>(List.of(activity)));


        var result = service.findAllBy(activity, Pageable.unpaged());


        Assertions.assertNotNull(result);
        org.assertj.core.api.Assertions.assertThat(result.getContent()).isNotEmpty();
        assertEquals(activity.getName(), result.getContent().stream().findFirst().map(Activity::getName).orElse(null));
        assertEquals(activity.getBookType(), result.getContent().stream().findFirst().map(Activity::getBookType).orElse(null));
        verify(repository, times(1)).findAllBy(Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("[findAllBy] Should return empty by terms")
    void findAllByEmpty() {
        var activity = buildEntity();
        when(repository.findAllBy(Mockito.any(), Mockito.any())).thenReturn(new PageImpl<>(List.of()));


        var result = service.findAllBy(activity, Pageable.unpaged());


        Assertions.assertNotNull(result);
        org.assertj.core.api.Assertions.assertThat(result.getContent()).isEmpty();
        verify(repository, times(1)).findAllBy(Mockito.any(), Mockito.any());
    }


    @Test
    @DisplayName("[UPDATE] Should update with successfully")
    void update() {
        var id = UUID.randomUUID();
        var entity = buildEntityById(id);
        var updated = buildEntityById(id);
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(updated);

        var result = service.update(updated, id.toString());

        assertEquals(updated, result);
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(updated);
    }

    @Test
    @DisplayName("[UPDATE] Should handle exception ResponseStatusException from NotFound when not match by id")
    void updateThrowsNotFoundException() {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        when(repository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.update(new Activity(), id));
    }


    @Test
    @DisplayName("[DELETE] Should delete with success")
    public void delete() {
        var uuid = UUID.randomUUID();

        service.delete(uuid);

        verify(repository).deleteById(uuid);
    }

    @Test
    @DisplayName("[DELETE ALL] Delete all with success")
    public void deleteAll() {
        List<UUID> ids = Arrays.asList(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());

        Mockito.doNothing().when(repository).deleteAllByIdInBatch(Mockito.any());

        service.deleteAll(ids);

        Mockito.verify(repository, Mockito.times(1)).deleteAllByIdInBatch(Mockito.any());
    }

    @Test
    @DisplayName("[DELETE ALL] Delete all in the list with exception")
    public void deleteAllWithException() {
        List<UUID> ids = Arrays.asList(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());

        Mockito.doThrow(new IllegalStateException()).when(repository).deleteAllByIdInBatch(Mockito.any());

        service.deleteAll(ids);

        Mockito.verify(repository, Mockito.times(1)).deleteAllByIdInBatch(Mockito.any());
    }

    private Activity buildEntity() {
        return Activity.builder()
                .id(UUID.randomUUID())
                .name(faker.company().industry())
                .bookType(BookType.random())
                .build();
    }

    private Activity buildEntityById(UUID id) {
        return Activity.builder()
                .id(id)
                .name(faker.company().name())
                .bookType(BookType.random())
                .build();
    }


}



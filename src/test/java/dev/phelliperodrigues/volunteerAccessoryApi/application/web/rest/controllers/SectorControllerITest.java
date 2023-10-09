package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import dev.phelliperodrigues.volunteerAccessoryApi.VolunteerAccessoryApiApplication;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.requests.SectorRequest;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.Sector;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.services.SectorService;
import dev.phelliperodrigues.volunteerAccessoryApi.utils.Exceptions;
import dev.phelliperodrigues.volunteerAccessoryApi.utils.FakerUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

import static dev.phelliperodrigues.volunteerAccessoryApi.utils.Endpoints.SECTOR_API;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = SectorController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = VolunteerAccessoryApiApplication.class)
class SectorControllerITest {

    private final Faker faker = FakerUtil.getInstance();

    @Autowired
    private MockMvc mvc;

    @MockBean
    SectorService sectorService;


    @Test
    @DisplayName("[CREATE] Should create a valid and active sector")
    void shouldCreateValidSector() throws Exception {
        var request = SectorRequest.builder()
                .name(faker.company().industry())
                .observations(faker.lorem().paragraph())
                .active(faker.bool().bool())
                .build();

        var sector = request.toSector();
        BDDMockito.given(sectorService.create(sector))
                .willReturn(Sector.builder()
                        .id(UUID.randomUUID())
                        .name(request.getName())
                        .observations(request.getObservations())
                        .active(request.isActive())
                        .createUserId(UUID.randomUUID())
                        .build());

        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.post(SECTOR_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.header().string("Location", containsString("/api/v1/registrations/sectors/")))
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(request.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("observations").value(request.getObservations()))
                .andExpect(MockMvcResultMatchers.jsonPath("active").value(request.isActive()));
    }

    @Test
    @DisplayName("[CREATE] Should create a valid sector without observation")
    void shouldCreateValidSectorWithoutObservation() throws Exception {
        var request = SectorRequest.builder()
                .name(faker.company().industry())
                .active(faker.bool().bool())
                .build();

        var sector = request.toSector();
        BDDMockito.given(sectorService.create(sector))
                .willReturn(Sector.builder()
                        .id(UUID.randomUUID())
                        .name(request.getName())
                        .observations(request.getObservations())
                        .active(request.isActive())
                        .createUserId(UUID.randomUUID())
                        .build());

        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.post(SECTOR_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.header().string("Location", containsString("/api/v1/registrations/sectors/")))
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(request.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("observations").value(request.getObservations()))
                .andExpect(MockMvcResultMatchers.jsonPath("active").value(request.isActive()));
    }

    @Test
    @DisplayName("[CREATE] Should create a valid sector without active and observation")
    void shouldCreateValidSectorWithoutActiveAndObservation() throws Exception {
        var request = SectorRequest.builder()
                .name(faker.company().industry())
                .build();

        var sector = request.toSector();
        BDDMockito.given(sectorService.create(sector))
                .willReturn(Sector.builder()
                        .id(UUID.randomUUID())
                        .name(request.getName())
                        .observations(request.getObservations())
                        .active(request.isActive())
                        .createUserId(UUID.randomUUID())
                        .build());

        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.post(SECTOR_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.header().string("Location", containsString("/api/v1/registrations/sectors/")))
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(request.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("observations").value(request.getObservations()))
                .andExpect(MockMvcResultMatchers.jsonPath("active").value(request.isActive()));
    }

    @Test
    @DisplayName("[CREATE] Should return bad request if name is null")
    void shouldReturnBadRequestIfNameIsNull() throws Exception {

        var request = SectorRequest.builder()
                .name(null)
                .observations(faker.lorem().paragraph())
                .active(faker.bool().bool())
                .build();

        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.post(SECTOR_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].field").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].defaultMessage").value("O campo \"nome\" é obrigatório"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].objectName").value("sectorRequest"));
    }

    @Test
    @DisplayName("[CREATE] Should return bad request if name is empty")
    void shouldReturnBadRequestIfNameIsEmpty() throws Exception {

        var request = SectorRequest.builder()
                .name("")
                .observations(faker.lorem().paragraph())
                .active(faker.bool().bool())
                .build();

        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.post(SECTOR_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].field").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].defaultMessage").value("O campo \"nome\" é obrigatório"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].objectName").value("sectorRequest"));
    }

    @Test
    @DisplayName("[CREATE] Should return bad request if without name")
    void shouldReturnBadRequestIfWithoutName() throws Exception {

        var request = SectorRequest.builder()
                .observations(faker.lorem().paragraph())
                .active(faker.bool().bool())
                .build();

        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.post(SECTOR_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].field").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].defaultMessage").value("O campo \"nome\" é obrigatório"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].objectName").value("sectorRequest"));
    }

    @Test
    @DisplayName("[FIND BY ID] Should return a sector with success")
    void findByIdWithSuccess() throws Exception {
        UUID uuid = UUID.randomUUID();
        var sector = Sector.builder()
                .id(uuid)
                .name(faker.company().industry())
                .observations(faker.lorem().paragraph())
                .active(faker.bool().bool())
                .build();
        BDDMockito.given(sectorService.findById(uuid.toString()))
                .willReturn(sector);
        var response = MockMvcRequestBuilders.get(SECTOR_API + "/" + uuid)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(uuid.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(sector.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("observations").value(sector.getObservations()))
                .andExpect(MockMvcResultMatchers.jsonPath("active").value(sector.isActive()));
    }

    @Test
    @DisplayName("[FIND BY ID] Should return bad request if ID is not valid")
    void findByIdBadRequest() throws Exception {

        BDDMockito.given(sectorService.findById("123"))
                .willThrow(Exceptions.invalidIdException("123"));

        var response = MockMvcRequestBuilders.get(SECTOR_API + "/" + "123")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("status").value("Bad Request"))
                .andExpect(MockMvcResultMatchers.jsonPath("timestamp").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("statusCode").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("ID: 123 com formato inválido"))
                .andExpect(MockMvcResultMatchers.jsonPath("error").value("400 BAD_REQUEST \"ID: 123 com formato inválido\""));
    }

    @Test
    @DisplayName("[FIND BY ID] Should return not found if not match ID")
    void findByIdNotFound() throws Exception {

        BDDMockito.given(sectorService.findById("123"))
                .willThrow(Exceptions.notFoundException("Setor não encontrado"));

        var response = MockMvcRequestBuilders.get(SECTOR_API + "/" + "123")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("status").value("Not Found"))
                .andExpect(MockMvcResultMatchers.jsonPath("timestamp").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("statusCode").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Setor não encontrado"))
                .andExpect(MockMvcResultMatchers.jsonPath("error").value("404 NOT_FOUND \"Setor não encontrado\""));
    }

    @Test
    @DisplayName("[FIND ALL BY] Should return with success all sectors match")
    void findAllBy() throws Exception {
        UUID uuid = UUID.randomUUID();
        var name = faker.company().industry();
        var isActive = faker.bool().bool();
        var sector = Sector.builder()
                .id(uuid)
                .name(name)
                .observations(faker.lorem().paragraph())
                .active(isActive)
                .build();
        BDDMockito.given(sectorService.findAllBy(Mockito.any()))
                .willReturn(List.of(sector));
        var response = MockMvcRequestBuilders.get(SECTOR_API)
                .queryParam("name", name)
                .queryParam("active", String.valueOf(isActive))
                .queryParam("id", uuid.toString())
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(uuid.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value(sector.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].observations").value(sector.getObservations()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].active").value(sector.isActive()));
    }

    @Test
    @DisplayName("[FIND ALL BY] Should return with success a empty list sectors")
    void findAllByEmpty() throws Exception {
        UUID uuid = UUID.randomUUID();
        var name = faker.company().industry();
        var isActive = faker.bool().bool();

        BDDMockito.given(sectorService.findAllBy(Mockito.any()))
                .willReturn(List.of());
        var response = MockMvcRequestBuilders.get(SECTOR_API)
                .queryParam("name", name)
                .queryParam("active", String.valueOf(isActive))
                .queryParam("id", uuid.toString())
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", empty()))

                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]").doesNotExist());
    }
}
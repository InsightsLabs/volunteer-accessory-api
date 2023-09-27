package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import dev.phelliperodrigues.volunteerAccessoryApi.VolunteerAccessoryApiApplication;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.requests.SectorRequest;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.Sector;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.services.SectorService;
import dev.phelliperodrigues.volunteerAccessoryApi.utils.FakerUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.UUID;

import static dev.phelliperodrigues.volunteerAccessoryApi.utils.Endpoints.SECTOR_API;
import static org.hamcrest.Matchers.containsString;

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
    @DisplayName("Should create a valid and active sector")
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
                        .createdAt(LocalDateTime.now())
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
                .andExpect(MockMvcResultMatchers.jsonPath("active").value(request.isActive()))
                .andExpect(MockMvcResultMatchers.jsonPath("create_at").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("update_at").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("create_user_id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("update_user_id").isEmpty());
    }

    @Test
    @DisplayName("Should create a valid sector without observation")
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
                        .createdAt(LocalDateTime.now())
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
                .andExpect(MockMvcResultMatchers.jsonPath("active").value(request.isActive()))
                .andExpect(MockMvcResultMatchers.jsonPath("create_at").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("update_at").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("create_user_id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("update_user_id").isEmpty());
    }

    @Test
    @DisplayName("Should create a valid sector without active and observation")
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
                        .createdAt(LocalDateTime.now())
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
                .andExpect(MockMvcResultMatchers.jsonPath("active").value(request.isActive()))
                .andExpect(MockMvcResultMatchers.jsonPath("create_at").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("update_at").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("create_user_id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("update_user_id").isEmpty());
    }

    @Test
    @DisplayName("Should return bad request if name is null")
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
    @DisplayName("Should return bad request if name is empty")
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
    @DisplayName("Should return bad request if without name")
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


}
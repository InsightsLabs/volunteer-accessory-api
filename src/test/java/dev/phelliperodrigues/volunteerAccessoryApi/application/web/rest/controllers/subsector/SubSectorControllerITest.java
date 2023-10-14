package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.controllers.subsector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import dev.phelliperodrigues.volunteerAccessoryApi.VolunteerAccessoryApiApplication;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.requests.sector.SectorRequest;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.requests.subsector.SubSectorRequest;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.sector.Sector;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.subsector.SubSector;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.services.subsector.SubSectorService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static dev.phelliperodrigues.volunteerAccessoryApi.utils.Endpoints.SUB_SECTOR_API;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = SubSectorController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = VolunteerAccessoryApiApplication.class)
class SubSectorControllerITest {

    private final Faker faker = FakerUtil.getInstance();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SubSectorService service;


    @Test
    @DisplayName("[CREATE] Should create a valid and active sector")
    void shouldCreateValidSector() throws Exception {
        var request = SubSectorRequest.builder()
                .id(UUID.randomUUID())
                .name(faker.company().industry())
                .observations(faker.lorem().paragraph())
                .active(faker.bool().bool())
                .sector(buildSector())
                .build();

        var sector = request.toDomain();
        BDDMockito.given(service.create(sector))
                .willReturn(SubSector.builder()
                        .id(UUID.randomUUID())
                        .name(request.getName())
                        .observations(request.getObservations())
                        .active(request.isActive())
                        .createUserId(UUID.randomUUID())
                        .sector(Sector.builder()
                                .id(request.getSector().getId())
                                .build())
                        .build());

        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.post(SUB_SECTOR_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.header().string("Location", containsString("/api/v1/registrations/subsectors/")))
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(request.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("observations").value(request.getObservations()))
                .andExpect(MockMvcResultMatchers.jsonPath("active").value(request.isActive()));
    }

    @Test
    @DisplayName("[CREATE] Should create a valid sector without observation")
    void shouldCreateValidSectorWithoutObservation() throws Exception {
        var request = SubSectorRequest.builder()
                .name(faker.company().industry())
                .active(faker.bool().bool())
                .sector(buildSector())
                .build();

        var sector = request.toDomain();
        BDDMockito.given(service.create(sector))
                .willReturn(SubSector.builder()
                        .id(UUID.randomUUID())
                        .name(request.getName())
                        .observations(request.getObservations())
                        .active(request.isActive())
                        .createUserId(UUID.randomUUID())
                        .sector(Sector.builder()
                                .id(request.getSector().getId())
                                .build())
                        .build());

        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.post(SUB_SECTOR_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.header().string("Location", containsString("/api/v1/registrations/subsectors/")))
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(request.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("observations").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("active").value(request.isActive()));
    }

    @Test
    @DisplayName("[CREATE] Should create a valid sector without active and observation")
    void shouldCreateValidSectorWithoutActiveAndObservation() throws Exception {
        var request = SubSectorRequest.builder()
                .name(faker.company().industry())
                .sector(buildSector())
                .build();

        var sector = request.toDomain();
        BDDMockito.given(service.create(sector))
                .willReturn(SubSector.builder()
                        .id(UUID.randomUUID())
                        .name(request.getName())
                        .observations(request.getObservations())
                        .active(request.isActive())
                        .createUserId(UUID.randomUUID())
                        .sector(Sector.builder()
                                .id(request.getSector().getId())
                                .build())
                        .build());

        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.post(SUB_SECTOR_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.header().string("Location", containsString("/api/v1/registrations/subsectors/")))
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(request.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("observations").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("active").value(request.isActive()));
    }

    @Test
    @DisplayName("[CREATE] Should return bad request if name is null")
    void shouldReturnBadRequestIfNameIsNull() throws Exception {

        var request = SubSectorRequest.builder()
                .name(null)
                .observations(faker.lorem().paragraph())
                .active(faker.bool().bool())
                .build();

        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.post(SUB_SECTOR_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[*].field", containsInAnyOrder("name", "sector")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[*].defaultMessage", containsInAnyOrder("O campo \"nome\" é obrigatório", "O sub setor precisa conter um setor valido")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].objectName").value("subSectorRequest"));
    }

    @Test
    @DisplayName("[CREATE] Should return bad request if name is empty")
    void shouldReturnBadRequestIfNameIsEmpty() throws Exception {

        var request = SubSectorRequest.builder()
                .name("")
                .observations(faker.lorem().paragraph())
                .active(faker.bool().bool())
                .build();

        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.post(SUB_SECTOR_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[*].field", containsInAnyOrder("name", "sector")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[*].defaultMessage", containsInAnyOrder("O campo \"nome\" é obrigatório", "O sub setor precisa conter um setor valido")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].objectName").value("subSectorRequest"));
    }

    @Test
    @DisplayName("[CREATE] Should return bad request if without name")
    void shouldReturnBadRequestIfWithoutName() throws Exception {

        var request = SubSectorRequest.builder()
                .observations(faker.lorem().paragraph())
                .active(faker.bool().bool())
                .build();

        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.post(SUB_SECTOR_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[*].field", containsInAnyOrder("name", "sector")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[*].defaultMessage", containsInAnyOrder("O campo \"nome\" é obrigatório", "O sub setor precisa conter um setor valido")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].objectName").value("subSectorRequest"));
    }

    @Test
    @DisplayName("[FIND BY ID] Should return a sector with success")
    void findByIdWithSuccess() throws Exception {
        UUID uuid = UUID.randomUUID();
        var sector = SubSector.builder()
                .id(uuid)
                .name(faker.company().industry())
                .observations(faker.lorem().paragraph())
                .active(faker.bool().bool())
                .sector(Sector.builder()
                        .id(buildSector().getId())
                        .build()).build();
        BDDMockito.given(service.findById(uuid.toString()))
                .willReturn(sector);
        var response = MockMvcRequestBuilders.get(SUB_SECTOR_API + "/" + uuid)
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

        BDDMockito.given(service.findById("123"))
                .willThrow(Exceptions.invalidIdException("123"));

        var response = MockMvcRequestBuilders.get(SUB_SECTOR_API + "/" + "123")
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

        BDDMockito.given(service.findById("123"))
                .willThrow(Exceptions.notFoundException("Setor não encontrado"));

        var response = MockMvcRequestBuilders.get(SUB_SECTOR_API + "/" + "123")
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
    @DisplayName("[FIND ALL BY] Should return with success all subsectors match")
    void findAllBy() throws Exception {
        UUID uuid = UUID.randomUUID();
        var name = faker.company().industry();
        var isActive = faker.bool().bool();
        var sector = SubSector.builder()
                .id(uuid)
                .name(name)
                .observations(faker.lorem().paragraph())
                .active(isActive)
                .sector(Sector.builder()
                        .id(UUID.randomUUID())
                        .build())
                .build();
        BDDMockito.given(service.findAllBy(Mockito.any(), Mockito.any()))
                .willReturn(new PageImpl<>(List.of(sector)));
        var response = MockMvcRequestBuilders.get(SUB_SECTOR_API)
                .queryParam("name", name)
                .queryParam("active", String.valueOf(isActive))
                .queryParam("id", uuid.toString())
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(uuid.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value(sector.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].observations").value(sector.getObservations()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].active").value(sector.isActive()));
    }

    @Test
    @DisplayName("[FIND ALL BY] Should return with success a empty list subsectors")
    void findAllByEmpty() throws Exception {
        UUID uuid = UUID.randomUUID();
        var name = faker.company().industry();
        var isActive = faker.bool().bool();

        BDDMockito.given(service.findAllBy(Mockito.any(), Mockito.any()))
                .willReturn(Page.empty());
        var response = MockMvcRequestBuilders.get(SUB_SECTOR_API)
                .queryParam("name", name)
                .queryParam("active", String.valueOf(isActive))
                .queryParam("id", uuid.toString())
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", empty()))

                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0]").doesNotExist());
    }


    @Test
    @DisplayName("[UPDATE] Should update with success")
    void update() throws Exception {
        var request = SubSectorRequest.builder()
                .id(UUID.randomUUID())
                .name(faker.company().industry())
                .observations(faker.lorem().paragraph())
                .active(faker.bool().bool())
                .sector(buildSector())
                .build();
        var uuid = UUID.randomUUID();
        var sector = request.toDomain();
        BDDMockito.given(service.update(sector, uuid.toString()))
                .willReturn(SubSector.builder()
                        .id(uuid)
                        .name(request.getName())
                        .observations(request.getObservations())
                        .active(request.isActive())
                        .createUserId(UUID.randomUUID())
                        .updateUserId(UUID.randomUUID())
                        .sector(Sector.builder()
                                .id(request.getSector().getId())
                                .build())
                        .build());

        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.put(SUB_SECTOR_API + "/" + uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(request.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("observations").value(request.getObservations()))
                .andExpect(MockMvcResultMatchers.jsonPath("active").value(request.isActive()));
    }

    @Test
    @DisplayName("[UPDATE] Should create a valid sector without observation")
    void updateWithoutObservation() throws Exception {
        var request = SubSectorRequest.builder()
                .name(faker.company().industry())
                .active(faker.bool().bool())
                .sector(buildSector())
                .build();
        var uuid = UUID.randomUUID();
        var sector = request.toDomain();
        BDDMockito.given(service.update(sector, uuid.toString()))
                .willReturn(SubSector.builder()
                        .id(UUID.randomUUID())
                        .name(request.getName())
                        .observations(request.getObservations())
                        .active(request.isActive())
                        .createUserId(UUID.randomUUID())
                        .updateUserId(UUID.randomUUID())
                        .sector(Sector.builder()
                                .id(request.getSector().getId())
                                .build())
                        .build());

        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.put(SUB_SECTOR_API + "/" + uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(request.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("observations").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("active").value(request.isActive()));
    }

    @Test
    @DisplayName("[UPDATE] Should update a valid sector without active and observation")
    void updateWithoutObservationAndActiveParam() throws Exception {
        var request = SubSectorRequest.builder()
                .name(faker.company().industry())
                .sector(buildSector())
                .build();
        var uuid = UUID.randomUUID();
        var sector = request.toDomain();
        BDDMockito.given(service.update(sector, uuid.toString()))
                .willReturn(SubSector.builder()
                        .id(uuid)
                        .name(request.getName())
                        .observations(request.getObservations())
                        .active(request.isActive())
                        .createUserId(UUID.randomUUID())
                        .updateUserId(UUID.randomUUID())
                        .sector(Sector.builder()
                                .id(request.getSector().getId())
                                .build())
                        .build());

        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.put(SUB_SECTOR_API + "/" + uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(request.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("observations").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("active").value(request.isActive()));
    }

    @Test
    @DisplayName("[UPDATE] Should return bad request if name is null")
    void updateBadRequestWithoutName() throws Exception {

        var request = SubSectorRequest.builder()
                .name(null)
                .build();
        var uuid = UUID.randomUUID();
        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.put(SUB_SECTOR_API + "/" + uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[*].field", containsInAnyOrder("name", "sector")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[*].defaultMessage", containsInAnyOrder("O campo \"nome\" é obrigatório", "O sub setor precisa conter um setor valido")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].objectName").value("subSectorRequest"));
    }

    @Test
    @DisplayName("[UPDATE] Should return bad request if name is empty")
    void updateBadRequestEmptyName() throws Exception {


        var request = SubSectorRequest.builder()
                .name("")
                .build();
        var uuid = UUID.randomUUID();
        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.put(SUB_SECTOR_API + "/" + uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[*].field", containsInAnyOrder("name", "sector")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[*].defaultMessage", containsInAnyOrder("O campo \"nome\" é obrigatório", "O sub setor precisa conter um setor valido")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].objectName").value("subSectorRequest"));
    }

    @Test
    @DisplayName("[UPDATE] Should return bad request if without name")
    void updateWithoutName() throws Exception {

        var request = SectorRequest.builder()
                .observations(faker.lorem().paragraph())
                .active(faker.bool().bool())
                .build();

        var uuid = UUID.randomUUID();
        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.put(SUB_SECTOR_API + "/" + uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[*].field", containsInAnyOrder("name", "sector")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[*].defaultMessage", containsInAnyOrder("O campo \"nome\" é obrigatório", "O sub setor precisa conter um setor valido")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].objectName").value("subSectorRequest"));
    }

    @Test
    @DisplayName("[UPDATE] Should return Not Found")
    void updateNotFound() throws Exception {
        var request = SubSectorRequest.builder()
                .name(faker.company().industry())
                .sector(buildSector())

                .build();
        var uuid = UUID.randomUUID();
        var json = new ObjectMapper().writeValueAsString(request);
        BDDMockito.given(service.update(request.toDomain(), uuid.toString()))
                .willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        var response = MockMvcRequestBuilders.put(SUB_SECTOR_API + "/" + uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Not Found"))
                .andExpect(MockMvcResultMatchers.jsonPath("status").value("Not Found"))
                .andExpect(MockMvcResultMatchers.jsonPath("statusCode").value("404"));
    }
    
    @Test
    @DisplayName("[DELETE] Should delete sector")
    void delete() throws Exception {
        var uuid = UUID.randomUUID();
        BDDMockito.doNothing().when(service).delete(uuid);
        var response = MockMvcRequestBuilders.delete(SUB_SECTOR_API + "/" + uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("[DELETE ALL] Should delete all sector")
    void deleteAll() throws Exception {
        var ids = Arrays.asList(UUID.randomUUID(), UUID.nameUUIDFromBytes(new byte[0]), UUID.randomUUID());
        BDDMockito.doNothing().when(service).deleteAll(ids);
        var json = new ObjectMapper().writeValueAsString(ids);

        var response = MockMvcRequestBuilders.delete(SUB_SECTOR_API + "/all")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    private SectorRequest buildSector() {

        return SectorRequest.builder()
                .id(UUID.randomUUID())
                .name(faker.company().industry())
                .observations(faker.lorem().paragraph())
                .active(faker.bool().bool())
                .build();
    }

}
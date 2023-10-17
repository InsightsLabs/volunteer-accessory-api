package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.controllers.prayerHouses;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import dev.phelliperodrigues.volunteerAccessoryApi.VolunteerAccessoryApiApplication;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.builders.PrayingHouseRequestBuilderMock;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.requests.prayerHouses.PrayingHouseRequest;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.requests.sector.SampleSectorRequest;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.builders.PrayingHouseBuilderMock;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.prayerHouses.PrayingHouse;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.enums.BookType;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.services.prayerHouses.PrayingHouseService;
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

import static dev.phelliperodrigues.volunteerAccessoryApi.utils.Endpoints.PRAYER_HOUSES_APY;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = PrayingHouseController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = VolunteerAccessoryApiApplication.class)
class PrayingHouseControllerITest {

    private final Faker faker = FakerUtil.getInstance();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PrayingHouseService service;


    @Test
    @DisplayName("[CREATE] Should create")
    void shouldCreateValidSector() throws Exception {
        var request = PrayingHouseRequestBuilderMock.build();

        var entity = request.toDomain();
        BDDMockito.given(service.create(entity))
                .willReturn(buildEntityByRequest(request));

        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.post(PRAYER_HOUSES_APY)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.header().string("Location", containsString(PRAYER_HOUSES_APY)))
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(request.getName()));
    }

    @Test
    @DisplayName("[CREATE] Should return bad request if name is null")
    void shouldReturnBadRequestIfNameIsNull() throws Exception {

        var request = PrayingHouseRequest.builder()
                .name(null)
                .sector(SampleSectorRequest.builder().build())
                .build();

        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.post(PRAYER_HOUSES_APY)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].field").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].defaultMessage").value("O campo \"nome\" é obrigatório"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].objectName").value("prayingHouseRequest"));
    }

    @Test
    @DisplayName("[CREATE] Should return bad request if name is empty")
    void shouldReturnBadRequestIfNameIsEmpty() throws Exception {

        var request = PrayingHouseRequest.builder()
                .name("")
                .sector(SampleSectorRequest.builder().build())
                .build();

        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.post(PRAYER_HOUSES_APY)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].field").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].defaultMessage").value("O campo \"nome\" é obrigatório"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].objectName").value("prayingHouseRequest"));
    }

    @Test
    @DisplayName("[CREATE] Should return bad request if without name")
    void shouldReturnBadRequestIfWithoutName() throws Exception {

        var request = PrayingHouseRequest.builder()
                .sector(SampleSectorRequest.builder().build())
                .build();

        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.post(PRAYER_HOUSES_APY)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].field").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].defaultMessage").value("O campo \"nome\" é obrigatório"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].objectName").value("prayingHouseRequest"));
    }

    @Test
    @DisplayName("[CREATE] Should return bad request if without sector")
    void shouldReturnBadRequestIfWithoutSector() throws Exception {

        var request = PrayingHouseRequest.builder()
                .name(faker.company().name())
                .build();

        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.post(PRAYER_HOUSES_APY)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].field").value("sector"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].defaultMessage").value("A casa de oração precisa perterncer a um setor"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].objectName").value("prayingHouseRequest"));
    }


    @Test
    @DisplayName("[FIND BY ID] Should return a entity with success")
    void findByIdWithSuccess() throws Exception {
        UUID uuid = UUID.randomUUID();
        var entity = PrayingHouseBuilderMock.buildWithId(uuid);
        BDDMockito.given(service.findById(uuid.toString()))
                .willReturn(entity);
        var response = MockMvcRequestBuilders.get(PRAYER_HOUSES_APY + "/" + uuid)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(uuid.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(entity.getName()));

    }


    @Test
    @DisplayName("[FIND BY ID] Should return bad request if ID is not valid")
    void findByIdBadRequest() throws Exception {

        BDDMockito.given(service.findById("123"))
                .willThrow(Exceptions.invalidIdException("123"));

        var response = MockMvcRequestBuilders.get(PRAYER_HOUSES_APY + "/" + "123")
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

        var response = MockMvcRequestBuilders.get(PRAYER_HOUSES_APY + "/" + "123")
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
    @DisplayName("[FIND ALL BY] Should return with success all match")
    void findAllBy() throws Exception {
        UUID uuid = UUID.randomUUID();
        var entity = PrayingHouseBuilderMock.buildWithId(uuid);
        BDDMockito.given(service.findAllBy(Mockito.any(), Mockito.any()))
                .willReturn(new PageImpl<>(List.of(entity)));
        var response = MockMvcRequestBuilders.get(PRAYER_HOUSES_APY)
                .queryParam("name", entity.getName())
                .queryParam("id", uuid.toString())
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(uuid.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value(entity.getName()));

    }

    @Test
    @DisplayName("[FIND ALL BY] Should return with success a empty list")
    void findAllByEmpty() throws Exception {
        UUID uuid = UUID.randomUUID();
        var name = faker.company().industry();

        BDDMockito.given(service.findAllBy(Mockito.any(), Mockito.any()))
                .willReturn(Page.empty());
        var response = MockMvcRequestBuilders.get(PRAYER_HOUSES_APY)
                .queryParam("name", name)
                .queryParam("bookType", BookType.MANUTENCAO.toString())
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
        var request = PrayingHouseRequestBuilderMock.build();
        var uuid = UUID.randomUUID();
        var entity = request.toDomain();
        BDDMockito.given(service.update(entity, uuid.toString()))
                .willReturn(buildEntityByRequest(request));

        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.put(PRAYER_HOUSES_APY + "/" + uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(request.getName()));

    }

    @Test
    @DisplayName("[UPDATE] Should return bad request if name is null")
    void updateBadRequestWithoutName() throws Exception {

        var request = PrayingHouseRequestBuilderMock.buildWithName(null);

        var uuid = UUID.randomUUID();
        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.put(PRAYER_HOUSES_APY + "/" + uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].field").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].defaultMessage").value("O campo \"nome\" é obrigatório"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].objectName").value("prayingHouseRequest"));
    }

    @Test
    @DisplayName("[UPDATE] Should return bad request if name is empty")
    void updateBadRequestEmptyName() throws Exception {


        var request = PrayingHouseRequestBuilderMock.buildWithName("");
        var uuid = UUID.randomUUID();
        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.put(PRAYER_HOUSES_APY + "/" + uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].field").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].defaultMessage").value("O campo \"nome\" é obrigatório"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].objectName").value("prayingHouseRequest"));
    }

    @Test
    @DisplayName("[UPDATE] Should return bad request if without name")
    void updateWithoutName() throws Exception {

        var request = PrayingHouseRequestBuilderMock.buildWithoutName();

        var uuid = UUID.randomUUID();
        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.put(PRAYER_HOUSES_APY + "/" + uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].field").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].defaultMessage").value("O campo \"nome\" é obrigatório"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].objectName").value("prayingHouseRequest"));
    }

    @Test
    @DisplayName("[UPDATE] Should return bad request if without sector")
    void updateWithoutSector() throws Exception {

        var request = PrayingHouseRequestBuilderMock.buildWithoutSector();

        var uuid = UUID.randomUUID();
        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.put(PRAYER_HOUSES_APY + "/" + uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].field").value("sector"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].defaultMessage").value("A casa de oração precisa perterncer a um setor"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].objectName").value("prayingHouseRequest"));
    }

    @Test
    @DisplayName("[UPDATE] Should return Not Found")
    void updateNotFound() throws Exception {
        var request = PrayingHouseRequestBuilderMock.build();
        var uuid = UUID.randomUUID();
        var json = new ObjectMapper().writeValueAsString(request);
        BDDMockito.given(service.update(request.toDomain(), uuid.toString()))
                .willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        var response = MockMvcRequestBuilders.put(PRAYER_HOUSES_APY + "/" + uuid)
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
    @DisplayName("[DELETE] Should delete entity")
    void delete() throws Exception {
        var uuid = UUID.randomUUID();
        BDDMockito.doNothing().when(service).delete(uuid);
        var response = MockMvcRequestBuilders.delete(PRAYER_HOUSES_APY + "/" + uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("[DELETE ALL] Should delete all entity")
    void deleteAll() throws Exception {
        var ids = Arrays.asList(UUID.randomUUID(), UUID.nameUUIDFromBytes(new byte[0]), UUID.randomUUID());
        BDDMockito.doNothing().when(service).deleteAll(ids);
        var json = new ObjectMapper().writeValueAsString(ids);

        var response = MockMvcRequestBuilders.delete(PRAYER_HOUSES_APY + "/all")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    private static PrayingHouse buildEntityByRequest(PrayingHouseRequest request) {
        return PrayingHouse.builder()
                .id(UUID.randomUUID())
                .name(request.getName())
                .createUserId(UUID.randomUUID())
                .build();
    }

}
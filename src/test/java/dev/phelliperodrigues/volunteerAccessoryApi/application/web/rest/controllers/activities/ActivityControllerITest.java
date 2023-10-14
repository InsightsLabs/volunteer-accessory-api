package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.controllers.activities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import dev.phelliperodrigues.volunteerAccessoryApi.VolunteerAccessoryApiApplication;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.requests.activities.ActivityRequest;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.activities.Activity;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.enums.BookType;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.services.activities.ActivityService;
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

import static dev.phelliperodrigues.volunteerAccessoryApi.utils.Endpoints.ACTIVITIES_API;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = ActivityController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = VolunteerAccessoryApiApplication.class)
class ActivityControllerITest {

    private final Faker faker = FakerUtil.getInstance();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ActivityService service;


    @Test
    @DisplayName("[CREATE] Should create a valid activity")
    void shouldCreateValidSector() throws Exception {
        var request = buildRequest();

        var activity = request.toDomain();
        BDDMockito.given(service.create(activity))
                .willReturn(buildEntityByRequest(request));

        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.post(ACTIVITIES_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.header().string("Location", containsString("/api/v1/registrations/activities/")))
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(request.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("bookType").value(request.getBookType().toString()));
    }

    @Test
    @DisplayName("[CREATE] Should return bad request if name is null")
    void shouldReturnBadRequestIfNameIsNull() throws Exception {

        var request = ActivityRequest.builder()
                .name(null)
                .bookType(BookType.MANUTENCAO)
                .build();

        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.post(ACTIVITIES_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].field").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].defaultMessage").value("O campo \"nome\" é obrigatório"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].objectName").value("activityRequest"));
    }

    @Test
    @DisplayName("[CREATE] Should return bad request if name is empty")
    void shouldReturnBadRequestIfNameIsEmpty() throws Exception {

        var request = ActivityRequest.builder()
                .name("")
                .bookType(BookType.MANUTENCAO)
                .build();

        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.post(ACTIVITIES_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].field").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].defaultMessage").value("O campo \"nome\" é obrigatório"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].objectName").value("activityRequest"));
    }

    @Test
    @DisplayName("[CREATE] Should return bad request if without name")
    void shouldReturnBadRequestIfWithoutName() throws Exception {

        var request = ActivityRequest.builder()
                .bookType(BookType.MANUTENCAO)
                .build();

        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.post(ACTIVITIES_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].field").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].defaultMessage").value("O campo \"nome\" é obrigatório"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].objectName").value("activityRequest"));
    }

    @Test
    @DisplayName("[CREATE] Should return bad request if without book type")
    void shouldReturnBadRequestIfWithoutBookType() throws Exception {

        var request = ActivityRequest.builder()
                .name(faker.company().industry())
                .build();

        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.post(ACTIVITIES_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].field").value("bookType"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].defaultMessage").value("O campo \"Tipo de Livro\" é obrigatório"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].objectName").value("activityRequest"));
    }

    @Test
    @DisplayName("[FIND BY ID] Should return a activity with success")
    void findByIdWithSuccess() throws Exception {
        UUID uuid = UUID.randomUUID();
        var entity = buildEntityById(uuid);
        BDDMockito.given(service.findById(uuid.toString()))
                .willReturn(entity);
        var response = MockMvcRequestBuilders.get(ACTIVITIES_API + "/" + uuid)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(uuid.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(entity.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("bookType").value(entity.getBookType().toString()));

    }


    @Test
    @DisplayName("[FIND BY ID] Should return bad request if ID is not valid")
    void findByIdBadRequest() throws Exception {

        BDDMockito.given(service.findById("123"))
                .willThrow(Exceptions.invalidIdException("123"));

        var response = MockMvcRequestBuilders.get(ACTIVITIES_API + "/" + "123")
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

        var response = MockMvcRequestBuilders.get(ACTIVITIES_API + "/" + "123")
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
    @DisplayName("[FIND ALL BY] Should return with success all activities match")
    void findAllBy() throws Exception {
        UUID uuid = UUID.randomUUID();
        var entity = buildEntityById(uuid);
        BDDMockito.given(service.findAllBy(Mockito.any(), Mockito.any()))
                .willReturn(new PageImpl<>(List.of(entity)));
        var response = MockMvcRequestBuilders.get(ACTIVITIES_API)
                .queryParam("name", entity.getName())
                .queryParam("bookType", entity.getBookType().toString())
                .queryParam("id", uuid.toString())
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(uuid.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value(entity.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].bookType").value(entity.getBookType().toString()));

    }

    @Test
    @DisplayName("[FIND ALL BY] Should return with success a empty list activities")
    void findAllByEmpty() throws Exception {
        UUID uuid = UUID.randomUUID();
        var name = faker.company().industry();

        BDDMockito.given(service.findAllBy(Mockito.any(), Mockito.any()))
                .willReturn(Page.empty());
        var response = MockMvcRequestBuilders.get(ACTIVITIES_API)
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
        var request = buildRequest();
        var uuid = UUID.randomUUID();
        var activity = request.toDomain();
        BDDMockito.given(service.update(activity, uuid.toString()))
                .willReturn(buildEntityByRequest(request));

        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.put(ACTIVITIES_API + "/" + uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(request.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("bookType").value(request.getBookType().toString()));

    }

    @Test
    @DisplayName("[UPDATE] Should return bad request if name is null")
    void updateBadRequestWithoutName() throws Exception {

        var request = ActivityRequest.builder()
                .name(null)
                .bookType(BookType.MANUTENCAO)
                .build();
        var uuid = UUID.randomUUID();
        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.put(ACTIVITIES_API + "/" + uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].field").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].defaultMessage").value("O campo \"nome\" é obrigatório"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].objectName").value("activityRequest"));
    }

    @Test
    @DisplayName("[UPDATE] Should return bad request if name is empty")
    void updateBadRequestEmptyName() throws Exception {


        var request = ActivityRequest.builder()
                .name("")
                .bookType(BookType.MANUTENCAO)
                .build();
        var uuid = UUID.randomUUID();
        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.put(ACTIVITIES_API + "/" + uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].field").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].defaultMessage").value("O campo \"nome\" é obrigatório"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].objectName").value("activityRequest"));
    }

    @Test
    @DisplayName("[UPDATE] Should return bad request if without name")
    void updateWithoutName() throws Exception {

        var request = ActivityRequest.builder()
                .bookType(BookType.MANUTENCAO)
                .build();

        var uuid = UUID.randomUUID();
        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.put(ACTIVITIES_API + "/" + uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].field").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].defaultMessage").value("O campo \"nome\" é obrigatório"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].objectName").value("activityRequest"));
    }

    @Test
    @DisplayName("[UPDATE] Should return bad request if without bookType")
    void updateWithoutBookType() throws Exception {

        var request = ActivityRequest.builder()
                .name(faker.company().name())
                .build();

        var uuid = UUID.randomUUID();
        var json = new ObjectMapper().writeValueAsString(request);

        var response = MockMvcRequestBuilders.put(ACTIVITIES_API + "/" + uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].field").value("bookType"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].defaultMessage").value("O campo \"Tipo de Livro\" é obrigatório"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].objectName").value("activityRequest"));
    }

    @Test
    @DisplayName("[UPDATE] Should return Not Found")
    void updateNotFound() throws Exception {
        var request = buildRequest();
        var uuid = UUID.randomUUID();
        var json = new ObjectMapper().writeValueAsString(request);
        BDDMockito.given(service.update(request.toDomain(), uuid.toString()))
                .willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        var response = MockMvcRequestBuilders.put(ACTIVITIES_API + "/" + uuid)
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
    @DisplayName("[DELETE] Should delete activity")
    void delete() throws Exception {
        var uuid = UUID.randomUUID();
        BDDMockito.doNothing().when(service).delete(uuid);
        var response = MockMvcRequestBuilders.delete(ACTIVITIES_API + "/" + uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("[DELETE ALL] Should delete all activity")
    void deleteAll() throws Exception {
        var ids = Arrays.asList(UUID.randomUUID(), UUID.nameUUIDFromBytes(new byte[0]), UUID.randomUUID());
        BDDMockito.doNothing().when(service).deleteAll(ids);
        var json = new ObjectMapper().writeValueAsString(ids);

        var response = MockMvcRequestBuilders.delete(ACTIVITIES_API + "/all")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(response)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    private static Activity buildEntityByRequest(ActivityRequest request) {
        return Activity.builder()
                .id(UUID.randomUUID())
                .name(request.getName())
                .bookType(request.getBookType())
                .createUserId(UUID.randomUUID())
                .build();
    }

    private Activity buildEntityById(UUID uuid) {
        return Activity.builder()
                .id(uuid)
                .name(faker.company().industry())
                .bookType(BookType.MANUTENCAO)
                .build();
    }

    private ActivityRequest buildRequest() {
        return ActivityRequest.builder()
                .name(faker.company().industry())
                .bookType(BookType.MANUTENCAO)
                .build();
    }
}
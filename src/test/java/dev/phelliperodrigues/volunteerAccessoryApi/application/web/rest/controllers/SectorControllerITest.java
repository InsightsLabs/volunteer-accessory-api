package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import dev.phelliperodrigues.volunteerAccessoryApi.VolunteerAccessoryApiApplication;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.requests.SectorRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.stream.Stream;

import static dev.phelliperodrigues.volunteerAccessoryApi.application.web.Endpoints.SECTOR_API;
import static org.hamcrest.Matchers.containsString;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = SectorController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = VolunteerAccessoryApiApplication.class)
class SectorControllerITest extends TestIT {

    Faker faker = new Faker();

    @Autowired
    private MockMvc mvc;

    @TestFactory
    @DisplayName("Should create sector with success")
    Stream<DynamicTest> create() {
        @Nested
        record TestCase(String name, MockMvc mvc, SectorRequest request) {
            @Test
            @DisplayName("${name}")
            void execute() throws Exception {
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
        }
        var testCases = Stream.of(
                new TestCase("Should create a valid and active sector", mvc, SectorRequest.builder()
                        .name(faker.company().industry())
                        .observations(faker.lorem().paragraph())
                        .active(faker.bool().bool())
                        .build()),
                new TestCase("Should create a valid sector without observation", mvc, SectorRequest.builder()
                        .name(faker.company().industry())
                        .active(faker.bool().bool())
                        .build()),
                new TestCase("Should create a valid sector without active and observation", mvc, SectorRequest.builder()
                        .name(faker.company().industry())
                        .build())

        );

        return DynamicTest.stream(testCases.flatMap(this::expandTestCases), this::generateDisplayName, TestCaseMethod::invoke);
    }

    @TestFactory
    @DisplayName("Should return bad request if name is null or empty")
    Stream<DynamicTest> createWithValidation() {
        record TestCase(String name, MockMvc mvc, SectorRequest request) {
            @Test
            @DisplayName("${name}")
            void execute() throws Exception {
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
        var testCases = Stream.of(
                new TestCase("Should return bad request if name is null", mvc, SectorRequest.builder()
                        .name(null)
                        .observations(faker.lorem().paragraph())
                        .active(faker.bool().bool())
                        .build()),
                new TestCase("Should return bad request if name is empty", mvc, SectorRequest.builder()
                        .name("")
                        .observations(faker.lorem().paragraph())
                        .active(faker.bool().bool())
                        .build()),
                new TestCase("Should return bad request if name not send", mvc, SectorRequest.builder()
                        .observations(faker.lorem().paragraph())
                        .active(faker.bool().bool())
                        .build())

        );

        return DynamicTest.stream(testCases.flatMap(this::expandTestCases), this::generateDisplayName, TestCaseMethod::invoke);
    }

}
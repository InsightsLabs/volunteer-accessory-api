package dev.phelliperodrigues.volunteerAccessoryApi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration(classes = VolunteerAccessoryApiApplication.class)
class VolunteerAccessoryApiApplicationTests {

    @Test
    void testApplication() {
        MockedStatic<SpringApplication> utilities = Mockito.mockStatic(SpringApplication.class);
        utilities.when((MockedStatic.Verification) SpringApplication.run(VolunteerAccessoryApiApplication.class, new String[]{})).thenReturn(null);
        VolunteerAccessoryApiApplication.main(new String[]{});
        assertThat(SpringApplication.run(VolunteerAccessoryApiApplication.class)).isEqualTo(null);
    }

    @Test
    void validCreationClass() {
        VolunteerAccessoryApiApplication volunteerAccessoryApiApplication = new VolunteerAccessoryApiApplication();
        assertThat(volunteerAccessoryApiApplication).isNotNull();
    }

}

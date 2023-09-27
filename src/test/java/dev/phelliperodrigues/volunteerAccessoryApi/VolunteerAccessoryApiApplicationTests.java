package dev.phelliperodrigues.volunteerAccessoryApi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class VolunteerAccessoryApiApplicationTests {

    @Test
    void validCreationClass() {
        VolunteerAccessoryApiApplication volunteerAccessoryApiApplication = new VolunteerAccessoryApiApplication();
        assertThat(volunteerAccessoryApiApplication).isNotNull();
    }

}

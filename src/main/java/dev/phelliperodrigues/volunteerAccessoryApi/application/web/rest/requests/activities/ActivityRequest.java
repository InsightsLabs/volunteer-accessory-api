package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.requests.activities;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.activities.Activity;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.enums.BookType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityRequest {

    private UUID id;
    @NotEmpty(message = "O campo \"nome\" é obrigatório")
    private String name;
    @NotNull(message = "O campo \"Tipo de Livro\" é obrigatório")
    private BookType bookType;

    public Activity toDomain() {
        return Activity.builder()
                .id(this.id)
                .name(this.name)
                .bookType(bookType)
                .build();
    }
}

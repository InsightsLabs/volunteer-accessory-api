package dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.activities;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.enums.BookType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Activity {
    private UUID id;
    private String name;
    private BookType bookType;
    private UUID createUserId;
    private UUID updateUserId;


    public void update(Activity domain) {
        this.name = domain.getName();
        this.bookType = domain.getBookType();
        this.updateUserId = domain.getUpdateUserId();
    }

}

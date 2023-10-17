package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.builders;

import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.requests.prayerHouses.PrayingHouseRequest;
import org.instancio.Instancio;

import java.util.UUID;

import static org.instancio.Select.field;

public class PrayingHouseRequestBuilderMock {

    public static PrayingHouseRequest build() {
        return Instancio.of(PrayingHouseRequest.class).create();
    }

    public static PrayingHouseRequest buildWithId(UUID id) {
        return Instancio.of(PrayingHouseRequest.class)
                .supply(field(PrayingHouseRequest::getId), gen -> id).create();
    }

    public static PrayingHouseRequest buildWithName(String name) {
        return Instancio.of(PrayingHouseRequest.class)
                .supply(field(PrayingHouseRequest::getName), gen -> name).create();
    }

    public static PrayingHouseRequest buildWithoutName() {
        return Instancio.of(PrayingHouseRequest.class)
                .ignore(field(PrayingHouseRequest::getName)).create();
    }

    public static PrayingHouseRequest buildWithoutSector() {
        return Instancio.of(PrayingHouseRequest.class)
                .ignore(field(PrayingHouseRequest::getSector)).create();
    }
}

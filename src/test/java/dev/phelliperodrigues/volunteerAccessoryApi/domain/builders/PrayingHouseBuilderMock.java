package dev.phelliperodrigues.volunteerAccessoryApi.domain.builders;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.prayerHouses.PrayingHouse;
import org.instancio.Instancio;

import java.util.UUID;

import static org.instancio.Select.field;

public class PrayingHouseBuilderMock {

    public static PrayingHouse build() {
        return Instancio.of(PrayingHouse.class).create();
    }

    public static PrayingHouse buildWithId(UUID id) {
        return Instancio.of(PrayingHouse.class)
                .supply(field(PrayingHouse::getId), gen -> id).create();
    }

    public static PrayingHouse buildWithName(String name) {
        return Instancio.of(PrayingHouse.class)
                .supply(field(PrayingHouse::getName), gen -> name).create();
    }

    public static PrayingHouse buildWithoutName() {
        return Instancio.of(PrayingHouse.class)
                .ignore(field(PrayingHouse::getName)).create();
    }

    public static PrayingHouse buildWithoutSector() {
        return Instancio.of(PrayingHouse.class)
                .ignore(field(PrayingHouse::getSector)).create();
    }
}

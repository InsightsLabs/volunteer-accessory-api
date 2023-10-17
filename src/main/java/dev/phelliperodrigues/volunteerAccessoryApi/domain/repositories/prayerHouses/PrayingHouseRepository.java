package dev.phelliperodrigues.volunteerAccessoryApi.domain.repositories.prayerHouses;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.prayerHouses.PrayingHouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PrayingHouseRepository {

    PrayingHouse save(PrayingHouse sector);

    Optional<PrayingHouse> findById(UUID id);

    Page<PrayingHouse> findAllBy(PrayingHouse sector, Pageable pageable);

    void deleteById(UUID id);

    void deleteAllByIdInBatch(List<UUID> id);
}

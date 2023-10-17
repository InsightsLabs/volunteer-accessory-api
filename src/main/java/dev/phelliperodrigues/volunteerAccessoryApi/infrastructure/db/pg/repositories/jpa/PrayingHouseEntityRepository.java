package dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.repositories.jpa;

import dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.entities.prayerHouses.PrayingHouseEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface PrayingHouseEntityRepository extends JpaRepository<PrayingHouseEntity, UUID>, JpaSpecificationExecutor<PrayingHouseEntity> {

    static Specification<PrayingHouseEntity> likeName(String name) {
        return (root, query, builder) ->
                builder.like(
                        builder.upper(root.get("name")),
                        "%" + name.toUpperCase() + "%"
                );
    }

    static Specification<PrayingHouseEntity> byId(UUID id) {
        return (root, query, builder) ->
                builder.equal(
                        root.get("id"),
                        id
                );
    }

    static Specification<PrayingHouseEntity> likeSectorName(String name) {
        return (root, query, builder) ->
                builder.like(
                        builder.upper(root.get("sector").get("name")),
                        "%" + name.toUpperCase() + "%"
                );
    }

    static Specification<PrayingHouseEntity> bySectorId(UUID id) {
        return (root, query, builder) ->
                builder.equal(
                        root.get("sector").get("id"),
                        id
                );
    }

    static Specification<PrayingHouseEntity> likeSubSectorName(String name) {
        return (root, query, builder) ->
                builder.like(
                        builder.upper(root.get("subSector").get("name")),
                        "%" + name.toUpperCase() + "%"
                );
    }

    static Specification<PrayingHouseEntity> bySubSectorId(UUID id) {
        return (root, query, builder) ->
                builder.equal(
                        root.get("subSector").get("id"),
                        id
                );
    }

}

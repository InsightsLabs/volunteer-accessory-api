package dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.repositories.jpa;

import dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.entities.subsector.SubSectorEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface SubSectorEntityRepository extends JpaRepository<SubSectorEntity, UUID>, JpaSpecificationExecutor<SubSectorEntity> {

    static Specification<SubSectorEntity> likeName(String name) {
        return (root, query, builder) ->
                builder.like(
                        builder.upper(root.get("name")),
                        "%" + name.toUpperCase() + "%"
                );
    }

    static Specification<SubSectorEntity> byId(UUID id) {
        return (root, query, builder) ->
                builder.equal(
                        root.get("id"),
                        id
                );
    }

    static Specification<SubSectorEntity> isActive(boolean active) {
        return (root, query, builder) ->
                builder.equal(root.get("active"), active);
    }

    static Specification<SubSectorEntity> likeSectorName(String name) {
        return (root, query, builder) ->
                builder.like(
                        builder.upper(root.get("sector").get("name")),
                        "%" + name.toUpperCase() + "%"
                );
    }

    static Specification<SubSectorEntity> bySectorId(UUID id) {
        return (root, query, builder) ->
                builder.equal(
                        root.get("sector").get("id"),
                        id
                );
    }

}
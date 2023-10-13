package dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.repositories.jpa;

import dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.entities.sector.SectorEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface SectorEntityRepository extends JpaRepository<SectorEntity, UUID>, JpaSpecificationExecutor<SectorEntity> {

    static Specification<SectorEntity> likeName(String name) {
        return (root, query, builder) ->
                builder.like(
                        builder.upper(root.get("name")),
                        "%" + name.toUpperCase() + "%"
                );
    }

    static Specification<SectorEntity> byId(UUID id) {
        return (root, query, builder) ->
                builder.equal(
                        root.get("id"),
                        id
                );
    }

    static Specification<SectorEntity> isActive(boolean active) {
        return (root, query, builder) ->
                builder.equal(root.get("active"), active);
    }

}
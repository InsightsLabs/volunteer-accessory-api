package dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.repositories.jpa;

import dev.phelliperodrigues.volunteerAccessoryApi.domain.enums.BookType;
import dev.phelliperodrigues.volunteerAccessoryApi.infrastructure.db.pg.entities.activities.ActivityEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ActivityEntityRepository extends JpaRepository<ActivityEntity, UUID>, JpaSpecificationExecutor<ActivityEntity> {

    static Specification<ActivityEntity> likeName(String name) {
        return (root, query, builder) ->
                builder.like(
                        builder.upper(root.get("name")),
                        "%" + name.toUpperCase() + "%"
                );
    }

    static Specification<ActivityEntity> byId(UUID id) {
        return (root, query, builder) ->
                builder.equal(
                        root.get("id"),
                        id
                );
    }

    static Specification<ActivityEntity> bookTypeIs(BookType bookType) {
        return (root, query, builder) ->
                builder.equal(root.get("bookType"), bookType);
    }

}
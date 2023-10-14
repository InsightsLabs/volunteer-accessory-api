package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.controllers.activities;

import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.handlers.ApiError;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.handlers.MethodArgumentNotValidExceptionHandler;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.requests.activities.ActivityRequest;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.responses.activities.ActivitiesPageResponse;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.responses.activities.ActivityResponse;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.activities.Activity;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.enums.BookType;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.services.activities.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static dev.phelliperodrigues.volunteerAccessoryApi.utils.Endpoints.ACTIVITIES_API;

@Slf4j
@RestController
@RequestMapping(ACTIVITIES_API)
@Tag(name = "Atividade")
public class ActivityController {

    private final ActivityService service;

    public ActivityController(ActivityService service) {
        this.service = service;
    }

    @Operation(
            summary = "Criar uma 'atividade'",
            description = "Endpoint de criação de 'atividade'",
            tags = {"Atividade"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criado com sucesso", content = {@Content(schema = @Schema(implementation = ActivityResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = {@Content(schema = @Schema(implementation = MethodArgumentNotValidExceptionHandler.Error.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Proibido", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
    })
    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ActivityResponse> create(@RequestBody @Valid ActivityRequest request) {
        var result = service.create(request.toDomain());
        return ResponseEntity.created(URI.create(ACTIVITIES_API + "/" + result.getId()))
                .body(ActivityResponse.build(result));

    }

    @Operation(
            summary = "Buscar 'atividade' por id",
            description = "Endpoint de busca de 'atividade' por ID",
            tags = {"Atividade"}
    )
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "Encontrado com sucesso", content = {@Content(schema = @Schema(implementation = ActivityResponse.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404", description = "Não Encontrado", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500", description = "Erro interno", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "401", description = "Não autorizado", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "403", description = "Proibido", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
            }
    )
    @GetMapping(value = "/{id}")
    public ResponseEntity<ActivityResponse> findById(@PathVariable String id) {
        var result = service.findById(id);
        return ResponseEntity.ok(ActivityResponse.build(result));
    }

    @Operation(
            summary = "Buscar 'atividade' por termo {id, name, active}",
            description = "Endpoint de busca de 'atividadees' por termo paginada",
            tags = {"Atividade"}
    )
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "Encontrado com sucesso", content = {@Content(schema = @Schema(implementation = ActivitiesPageResponse.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500", description = "Erro interno", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "401", description = "Não autorizado", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "403", description = "Proibido", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
            }
    )
    @PageableAsQueryParam
    @GetMapping
    public ResponseEntity<ActivitiesPageResponse> findAllBy(
            @RequestParam(name = "id", required = false) UUID id,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "book_type", required = false) BookType bookType,
            @Parameter(hidden = true) Pageable pageable
    ) {
        var search = Activity.builder()
                .id(id)
                .name(name)
                .bookType(bookType)
                .build();

        var result = service.findAllBy(search, pageable);

        var pageResponse = new PageImpl<>(
                result.getContent()
                        .stream()
                        .map(ActivityResponse::build)
                        .toList(),
                pageable,
                result.getTotalElements()

        );
        return ResponseEntity.ok(new ActivitiesPageResponse(pageResponse));
    }

    @Operation(
            summary = "Atualiza uma 'atividade' pelo ID",
            description = "Endpoint de atualização de '",
            tags = {"Atividade"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso", content = {@Content(schema = @Schema(implementation = ActivityResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Não Encontrado", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = {@Content(schema = @Schema(implementation = MethodArgumentNotValidExceptionHandler.Error.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Proibido", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
    })
    @Transactional
    @PutMapping(value = "/{id}")
    public ResponseEntity<ActivityResponse> update(@RequestBody @Valid ActivityRequest request, @PathVariable String id) {
        var result = service.update(request.toDomain(), id);
        return ResponseEntity.ok(ActivityResponse.build(result));
    }


    @Operation(
            summary = "Deletar uma 'atividade' pelo ID",
            description = "Endpoint para deletar 'atividade'",
            tags = {"Atividade"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = {@Content(schema = @Schema(implementation = MethodArgumentNotValidExceptionHandler.Error.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Proibido", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
    })
    @Transactional
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @Operation(
            summary = "Deletar varias 'atividades' pelo ID",
            description = "Endpoint para deletar 'atividades' pela lista de ID",
            tags = {"Atividade"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = {@Content(schema = @Schema(implementation = MethodArgumentNotValidExceptionHandler.Error.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Proibido", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
    })
    @Transactional
    @DeleteMapping(value = "/all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll(@RequestBody List<UUID> ids) {
        service.deleteAll(ids);
    }


}

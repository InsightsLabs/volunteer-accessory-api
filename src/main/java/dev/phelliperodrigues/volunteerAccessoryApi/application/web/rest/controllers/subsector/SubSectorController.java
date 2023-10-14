package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.controllers.subsector;

import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.handlers.ApiError;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.handlers.MethodArgumentNotValidExceptionHandler;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.requests.subsector.SubSectorRequest;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.responses.sector.SectorPageResponse;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.responses.subsector.SubSectorPageResponse;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.responses.subsector.SubSectorResponse;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.sector.Sector;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.subsector.SubSector;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.services.subsector.SubSectorService;
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

import static dev.phelliperodrigues.volunteerAccessoryApi.utils.Endpoints.SUB_SECTOR_API;

@Slf4j
@RestController
@RequestMapping(SUB_SECTOR_API)
@Tag(name = "Sub Setor")
public class SubSectorController {

    private final SubSectorService service;

    public SubSectorController(SubSectorService service) {
        this.service = service;
    }

    @Operation(
            summary = "Criar um sub setor",
            description = "Endpoint de criação de sub setor",
            tags = {"Sub Setor"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criado com sucesso", content = {@Content(schema = @Schema(implementation = SubSectorResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = {@Content(schema = @Schema(implementation = MethodArgumentNotValidExceptionHandler.Error.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Proibido", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
    })
    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SubSectorResponse> create(@RequestBody @Valid SubSectorRequest request) {
        var sector = service.create(request.toDomain());
        return ResponseEntity.created(URI.create(SUB_SECTOR_API + "/" + sector.getId()))
                .body(SubSectorResponse.build(sector));

    }

    @Operation(
            summary = "Buscar sub setor por id",
            description = "Endpoint de busca de sub setor por ID",
            tags = {"Sub Setor"}
    )
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "Encontrado com sucesso", content = {@Content(schema = @Schema(implementation = SubSectorResponse.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404", description = "Não Encontrado", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500", description = "Erro interno", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "401", description = "Não autorizado", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "403", description = "Proibido", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
            }
    )
    @GetMapping(value = "/{id}")
    public ResponseEntity<SubSectorResponse> findById(@PathVariable String id) {
        var sector = service.findById(id);
        return ResponseEntity.ok(SubSectorResponse.build(sector));
    }

    @Operation(
            summary = "Buscar sub setor por termo {id, name, active}",
            description = "Endpoint de busca de sub setores por termo paginada",
            tags = {"Sub Setor"}
    )
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "Encontrado com sucesso", content = {@Content(schema = @Schema(implementation = SectorPageResponse.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500", description = "Erro interno", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "401", description = "Não autorizado", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "403", description = "Proibido", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
            }
    )
    @PageableAsQueryParam
    @GetMapping
    public ResponseEntity<SubSectorPageResponse> findAllBy(
            @RequestParam(name = "id", required = false) String id,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "active", required = false) Boolean active,
            @RequestParam(name = "sectorName", required = false) String sectorName,
            @RequestParam(name = "sectorId", required = false) String sectorId,
            @Parameter(hidden = true) Pageable pageable
    ) {
        var search = SubSector.builder()
                .idByString(id)
                .name(name)
                .active(active)
                .sector(Sector.builder().idByString(sectorId).name(sectorName).build())
                .build();

        var result = service.findAllBy(search, pageable);

        var sectorPaginate = new PageImpl<>(
                result.getContent()
                        .stream()
                        .map(SubSectorResponse::build)
                        .toList(),
                pageable,
                result.getTotalElements()

        );
        log.info("{} sub sectors find", sectorPaginate.getTotalElements());
        return ResponseEntity.ok(new SubSectorPageResponse(sectorPaginate));
    }

    @Operation(
            summary = "Atualiza um sub setor pelo ID",
            description = "Endpoint de atualização de sub setor",
            tags = {"Sub Setor"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso", content = {@Content(schema = @Schema(implementation = SubSectorResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Não Encontrado", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = {@Content(schema = @Schema(implementation = MethodArgumentNotValidExceptionHandler.Error.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Proibido", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
    })
    @Transactional
    @PutMapping(value = "/{id}")
    public ResponseEntity<SubSectorResponse> update(@RequestBody @Valid SubSectorRequest request, @PathVariable String id) {
        var sector = service.update(request.toDomain(), id);
        return ResponseEntity.ok(SubSectorResponse.build(sector));
    }


    @Operation(
            summary = "Deletar um sub setor pelo ID",
            description = "Endpoint para deletar sub setor",
            tags = {"Sub Setor"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não Encontrado", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
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
            summary = "Deletar um sub setor pelo ID",
            description = "Endpoint para deletar sub setor",
            tags = {"Sub Setor"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não Encontrado", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
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

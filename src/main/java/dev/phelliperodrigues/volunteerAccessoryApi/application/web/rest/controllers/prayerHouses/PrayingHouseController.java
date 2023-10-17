package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.controllers.prayerHouses;

import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.handlers.ApiError;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.handlers.MethodArgumentNotValidExceptionHandler;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.requests.prayerHouses.PrayingHouseRequest;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.responses.common.PageResponse;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.responses.prayerHouses.PrayingHouseResponse;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.prayerHouses.PrayingHouse;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.sector.Sector;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.subsector.SubSector;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.services.prayerHouses.PrayingHouseService;
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

import static dev.phelliperodrigues.volunteerAccessoryApi.utils.Endpoints.PRAYER_HOUSES_APY;

@Slf4j
@RestController
@RequestMapping(PRAYER_HOUSES_APY)
@Tag(name = "Casa de Oração")
public class PrayingHouseController {

    private final PrayingHouseService service;

    public PrayingHouseController(PrayingHouseService service) {
        this.service = service;
    }

    @Operation(
            summary = "Criar uma 'casa de oração'",
            description = "Endpoint de criação de 'casa de oração'",
            tags = {"Casa de Oração"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criado com sucesso", content = {@Content(schema = @Schema(implementation = PrayingHouseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = {@Content(schema = @Schema(implementation = MethodArgumentNotValidExceptionHandler.Error.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Proibido", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
    })
    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PrayingHouseResponse> create(@RequestBody @Valid PrayingHouseRequest request) {
        var result = service.create(request.toDomain());
        return ResponseEntity.created(URI.create(PRAYER_HOUSES_APY + "/" + result.getId()))
                .body(PrayingHouseResponse.build(result));

    }

    @Operation(
            summary = "Buscar 'casa de oração' por id",
            description = "Endpoint de busca de 'casa de oração' por ID",
            tags = {"Casa de Oração"}
    )
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "Encontrado com sucesso", content = {@Content(schema = @Schema(implementation = PrayingHouseResponse.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404", description = "Não Encontrado", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500", description = "Erro interno", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "401", description = "Não autorizado", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "403", description = "Proibido", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
            }
    )
    @GetMapping(value = "/{id}")
    public ResponseEntity<PrayingHouseResponse> findById(@PathVariable String id) {
        var result = service.findById(id);
        return ResponseEntity.ok(PrayingHouseResponse.build(result));
    }

    @Operation(
            summary = "Buscar 'casa de oração' por termo {id, name, active}",
            description = "Endpoint de busca de 'atividadees' por termo paginada",
            tags = {"Casa de Oração"}
    )
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "Encontrado com sucesso", content = {@Content(schema = @Schema(implementation = PageResponse.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500", description = "Erro interno", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "401", description = "Não autorizado", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "403", description = "Proibido", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
            }
    )
    @PageableAsQueryParam
    @GetMapping
    public ResponseEntity<PageResponse<PrayingHouseResponse>> findAllBy(
            @RequestParam(name = "id", required = false) UUID id,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "sectorName", required = false) String sectorName,
            @RequestParam(name = "sectorId", required = false) String sectorId,
            @RequestParam(name = "subSectorName", required = false) String subSectorName,
            @RequestParam(name = "subSectorId", required = false) String subSectorId,
            @Parameter(hidden = true) Pageable pageable
    ) {
        var search = PrayingHouse.builder()
                .id(id)
                .name(name)
                .sector(Sector.builder().idByString(sectorId).name(sectorName).build())
                .subSector(SubSector.builder().idByString(subSectorId).name(subSectorName).build())
                .build();

        var result = service.findAllBy(search, pageable);

        var pageResponse = new PageImpl<>(
                result.getContent()
                        .stream()
                        .map(PrayingHouseResponse::build)
                        .toList(),
                pageable,
                result.getTotalElements()

        );
        return ResponseEntity.ok(new PageResponse<>(pageResponse));
    }

    @Operation(
            summary = "Atualiza uma 'casa de oração' pelo ID",
            description = "Endpoint de atualização de '",
            tags = {"Casa de Oração"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso", content = {@Content(schema = @Schema(implementation = PrayingHouseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Não Encontrado", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = {@Content(schema = @Schema(implementation = MethodArgumentNotValidExceptionHandler.Error.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Proibido", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
    })
    @Transactional
    @PutMapping(value = "/{id}")
    public ResponseEntity<PrayingHouseResponse> update(@RequestBody @Valid PrayingHouseRequest request, @PathVariable String id) {
        var result = service.update(request.toDomain(), id);
        return ResponseEntity.ok(PrayingHouseResponse.build(result));
    }


    @Operation(
            summary = "Deletar uma 'casa de oração' pelo ID",
            description = "Endpoint para deletar 'casa de oração'",
            tags = {"Casa de Oração"}
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
            summary = "Deletar varias 'casas de oração' pelo ID",
            description = "Endpoint para deletar 'casas de oração' pela lista de ID",
            tags = {"Casa de Oração"}
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

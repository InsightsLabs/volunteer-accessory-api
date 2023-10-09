package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.controllers;

import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.handlers.ErrorDefault;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.handlers.MethodArgumentNotValidExceptionHandler;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.requests.SectorRequest;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.responses.SectorResponse;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.services.SectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static dev.phelliperodrigues.volunteerAccessoryApi.utils.Endpoints.SECTOR_API;

@RestController
@RequestMapping(SECTOR_API)
@Tag(name = "Setor")
public class SectorController {

    private final SectorService sectorService;

    public SectorController(SectorService sectorService) {
        this.sectorService = sectorService;
    }

    @Operation(
            summary = "Criar um setor",
            description = "Endpoint de criação de setor",
            tags = {"Setor"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criado com sucesso", content = {@Content(schema = @Schema(implementation = SectorResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = {@Content(schema = @Schema(implementation = MethodArgumentNotValidExceptionHandler.Error.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = {@Content(schema = @Schema(implementation = ErrorDefault.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = {@Content(schema = @Schema(implementation = ErrorDefault.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Proibido", content = {@Content(schema = @Schema(implementation = ErrorDefault.class), mediaType = "application/json")}),
    })
    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SectorResponse> create(@RequestBody @Valid SectorRequest request) {
        var sector = sectorService.create(request.toSector());
        return ResponseEntity.created(URI.create(SECTOR_API + "/" + sector.getId()))
                .body(SectorResponse.build(sector));

    }

    @Operation(
            summary = "Buscar setor por id",
            description = "Endpoint de criação de setor",
            tags = {"Setor"}
    )
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "Encontrado com sucesso", content = {@Content(schema = @Schema(implementation = SectorResponse.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = {@Content(schema = @Schema(implementation = MethodArgumentNotValidExceptionHandler.Error.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500", description = "Erro interno", content = {@Content(schema = @Schema(implementation = ErrorDefault.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "401", description = "Não autorizado", content = {@Content(schema = @Schema(implementation = ErrorDefault.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "403", description = "Proibido", content = {@Content(schema = @Schema(implementation = ErrorDefault.class), mediaType = "application/json")}),
            }
    )
    @GetMapping(value = "/{id}")
    public ResponseEntity<SectorResponse> findById(@PathVariable String id) {
        var sector = sectorService.findById(id);
        return ResponseEntity.ok(SectorResponse.build(sector));
    }


}

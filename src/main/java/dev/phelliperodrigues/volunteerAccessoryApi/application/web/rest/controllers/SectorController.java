package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.controllers;

import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.handlers.ErrorDefault;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.handlers.MethodArgumentNotValidExceptionHandler;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.requests.SectorRequest;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.responses.SectorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;

import static dev.phelliperodrigues.volunteerAccessoryApi.application.web.Endpoints.SECTOR_API;

@RestController
@RequestMapping(SECTOR_API)
@Tag(name = "Setor")
public class SectorController {


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
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SectorResponse> create(@RequestBody @Valid SectorRequest request) {
        var id = UUID.randomUUID();
        return ResponseEntity.created(URI.create(SECTOR_API + "/" + id))
                .body(SectorResponse.builder()
                        .id(id)
                        .name(request.getName())
                        .observations(request.getObservations())
                        .active(request.isActive())
                        .createdAt(LocalDateTime.now())
                        .createUserId(UUID.randomUUID())
                        .build());

    }


}

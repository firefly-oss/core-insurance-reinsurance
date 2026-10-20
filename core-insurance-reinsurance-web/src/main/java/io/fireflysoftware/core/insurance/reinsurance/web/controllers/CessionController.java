/*
 * Copyright 2026 Firefly Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package io.fireflysoftware.core.insurance.reinsurance.web.controllers;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.insurance.reinsurance.core.services.CessionService;
import io.fireflysoftware.core.insurance.reinsurance.interfaces.dtos.CessionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * REST API for premium ceded from a policy to a treaty.
 */
@RestController
@RequestMapping("/api/v1/treaties/{treatyId}/cessions")
@RequiredArgsConstructor
@Validated
@Tag(name = "Cessions", description = "Premium ceded from a policy to a treaty")
public class CessionController {

    private final CessionService cessionService;

    @PostMapping("/filter")
    @Operation(summary = "Filter cessions", description = "Retrieve a page of cessions matching the filter")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Page of matching records"),
        @ApiResponse(responseCode = "400", description = "Invalid filter request", content = @Content)
    })
    public Mono<ResponseEntity<PaginationResponse<CessionDTO>>> filterCessions(
            @Parameter(description = "Unique identifier of the Treaty", required = true)
            @PathVariable UUID treatyId,
            @Parameter(description = "Filter criteria", required = true)
            @Valid @RequestBody FilterRequest<CessionDTO> filterRequest) {
        return cessionService.filterCessions(treatyId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create Cession", description = "Create a Cession")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cession created", content = @Content(schema = @Schema(implementation = CessionDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content)
    })
    public Mono<ResponseEntity<CessionDTO>> createCession(
            @Parameter(description = "Unique identifier of the Treaty", required = true)
            @PathVariable UUID treatyId,
            @Parameter(description = "Cession to create", required = true)
            @Valid @RequestBody CessionDTO dto) {
        return cessionService.createCession(treatyId, dto)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));
    }

    @GetMapping("/{cessionId}")
    @Operation(summary = "Get Cession by id", description = "Retrieve one Cession")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cession found", content = @Content(schema = @Schema(implementation = CessionDTO.class))),
        @ApiResponse(responseCode = "404", description = "Cession not found", content = @Content)
    })
    public Mono<ResponseEntity<CessionDTO>> getCessionById(
            @Parameter(description = "Unique identifier of the Treaty", required = true)
            @PathVariable UUID treatyId,
            @Parameter(description = "Unique identifier of the Cession", required = true)
            @PathVariable UUID cessionId) {
        return cessionService.getCessionById(treatyId, cessionId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{cessionId}")
    @Operation(summary = "Update Cession", description = "Update an existing Cession")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cession updated", content = @Content(schema = @Schema(implementation = CessionDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content),
        @ApiResponse(responseCode = "404", description = "Cession not found", content = @Content)
    })
    public Mono<ResponseEntity<CessionDTO>> updateCession(
            @Parameter(description = "Unique identifier of the Treaty", required = true)
            @PathVariable UUID treatyId,
            @Parameter(description = "Unique identifier of the Cession", required = true)
            @PathVariable UUID cessionId,
            @Parameter(description = "Updated Cession", required = true)
            @Valid @RequestBody CessionDTO dto) {
        return cessionService.updateCession(treatyId, cessionId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{cessionId}")
    @Operation(summary = "Delete Cession", description = "Delete a Cession")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Cession deleted"),
        @ApiResponse(responseCode = "404", description = "Cession not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> deleteCession(
            @Parameter(description = "Unique identifier of the Treaty", required = true)
            @PathVariable UUID treatyId,
            @Parameter(description = "Unique identifier of the Cession", required = true)
            @PathVariable UUID cessionId) {
        return cessionService.deleteCession(treatyId, cessionId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}

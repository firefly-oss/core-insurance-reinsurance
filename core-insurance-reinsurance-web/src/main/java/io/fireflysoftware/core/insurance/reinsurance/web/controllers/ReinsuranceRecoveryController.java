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
import io.fireflysoftware.core.insurance.reinsurance.core.services.ReinsuranceRecoveryService;
import io.fireflysoftware.core.insurance.reinsurance.interfaces.dtos.ReinsuranceRecoveryDTO;
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
 * REST API for claim amount notified to a treaty for recovery.
 */
@RestController
@RequestMapping("/api/v1/treaties/{treatyId}/recoveries")
@RequiredArgsConstructor
@Validated
@Tag(name = "Reinsurance Recoveries", description = "Claim amount notified to a treaty for recovery")
public class ReinsuranceRecoveryController {

    private final ReinsuranceRecoveryService reinsuranceRecoveryService;

    @PostMapping("/filter")
    @Operation(summary = "Filter reinsuranceRecoveries", description = "Retrieve a page of reinsuranceRecoveries matching the filter")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Page of matching records"),
        @ApiResponse(responseCode = "400", description = "Invalid filter request", content = @Content)
    })
    public Mono<ResponseEntity<PaginationResponse<ReinsuranceRecoveryDTO>>> filterReinsuranceRecoveries(
            @Parameter(description = "Unique identifier of the Treaty", required = true)
            @PathVariable UUID treatyId,
            @Parameter(description = "Filter criteria", required = true)
            @Valid @RequestBody FilterRequest<ReinsuranceRecoveryDTO> filterRequest) {
        return reinsuranceRecoveryService.filterReinsuranceRecoveries(treatyId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create ReinsuranceRecovery", description = "Create a ReinsuranceRecovery")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "ReinsuranceRecovery created", content = @Content(schema = @Schema(implementation = ReinsuranceRecoveryDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content)
    })
    public Mono<ResponseEntity<ReinsuranceRecoveryDTO>> createReinsuranceRecovery(
            @Parameter(description = "Unique identifier of the Treaty", required = true)
            @PathVariable UUID treatyId,
            @Parameter(description = "ReinsuranceRecovery to create", required = true)
            @Valid @RequestBody ReinsuranceRecoveryDTO dto) {
        return reinsuranceRecoveryService.createReinsuranceRecovery(treatyId, dto)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));
    }

    @GetMapping("/{reinsuranceRecoveryId}")
    @Operation(summary = "Get ReinsuranceRecovery by id", description = "Retrieve one ReinsuranceRecovery")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "ReinsuranceRecovery found", content = @Content(schema = @Schema(implementation = ReinsuranceRecoveryDTO.class))),
        @ApiResponse(responseCode = "404", description = "ReinsuranceRecovery not found", content = @Content)
    })
    public Mono<ResponseEntity<ReinsuranceRecoveryDTO>> getReinsuranceRecoveryById(
            @Parameter(description = "Unique identifier of the Treaty", required = true)
            @PathVariable UUID treatyId,
            @Parameter(description = "Unique identifier of the ReinsuranceRecovery", required = true)
            @PathVariable UUID reinsuranceRecoveryId) {
        return reinsuranceRecoveryService.getReinsuranceRecoveryById(treatyId, reinsuranceRecoveryId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{reinsuranceRecoveryId}")
    @Operation(summary = "Update ReinsuranceRecovery", description = "Update an existing ReinsuranceRecovery")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "ReinsuranceRecovery updated", content = @Content(schema = @Schema(implementation = ReinsuranceRecoveryDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content),
        @ApiResponse(responseCode = "404", description = "ReinsuranceRecovery not found", content = @Content)
    })
    public Mono<ResponseEntity<ReinsuranceRecoveryDTO>> updateReinsuranceRecovery(
            @Parameter(description = "Unique identifier of the Treaty", required = true)
            @PathVariable UUID treatyId,
            @Parameter(description = "Unique identifier of the ReinsuranceRecovery", required = true)
            @PathVariable UUID reinsuranceRecoveryId,
            @Parameter(description = "Updated ReinsuranceRecovery", required = true)
            @Valid @RequestBody ReinsuranceRecoveryDTO dto) {
        return reinsuranceRecoveryService.updateReinsuranceRecovery(treatyId, reinsuranceRecoveryId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{reinsuranceRecoveryId}")
    @Operation(summary = "Delete ReinsuranceRecovery", description = "Delete a ReinsuranceRecovery")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "ReinsuranceRecovery deleted"),
        @ApiResponse(responseCode = "404", description = "ReinsuranceRecovery not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> deleteReinsuranceRecovery(
            @Parameter(description = "Unique identifier of the Treaty", required = true)
            @PathVariable UUID treatyId,
            @Parameter(description = "Unique identifier of the ReinsuranceRecovery", required = true)
            @PathVariable UUID reinsuranceRecoveryId) {
        return reinsuranceRecoveryService.deleteReinsuranceRecovery(treatyId, reinsuranceRecoveryId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}

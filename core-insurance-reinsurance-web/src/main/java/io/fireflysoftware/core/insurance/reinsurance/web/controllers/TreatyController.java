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
import io.fireflysoftware.core.insurance.reinsurance.core.services.TreatyService;
import io.fireflysoftware.core.insurance.reinsurance.interfaces.dtos.TreatyDTO;
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
 * REST API for reinsurance treaty or facultative placement recorded by the insurer.
 */
@RestController
@RequestMapping("/api/v1/treaties")
@RequiredArgsConstructor
@Validated
@Tag(name = "Treaties", description = "Reinsurance treaty or facultative placement recorded by the insurer")
public class TreatyController {

    private final TreatyService treatyService;

    @PostMapping("/filter")
    @Operation(summary = "Filter treaties", description = "Retrieve a page of treaties matching the filter")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Page of matching records"),
        @ApiResponse(responseCode = "400", description = "Invalid filter request", content = @Content)
    })
    public Mono<ResponseEntity<PaginationResponse<TreatyDTO>>> filterTreaties(
            @Parameter(description = "Filter criteria", required = true)
            @Valid @RequestBody FilterRequest<TreatyDTO> filterRequest) {
        return treatyService.filterTreaties(filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create Treaty", description = "Create a Treaty")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Treaty created", content = @Content(schema = @Schema(implementation = TreatyDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content)
    })
    public Mono<ResponseEntity<TreatyDTO>> createTreaty(
            @Parameter(description = "Treaty to create", required = true)
            @Valid @RequestBody TreatyDTO dto) {
        return treatyService.createTreaty(dto)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));
    }

    @GetMapping("/{treatyId}")
    @Operation(summary = "Get Treaty by id", description = "Retrieve one Treaty")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Treaty found", content = @Content(schema = @Schema(implementation = TreatyDTO.class))),
        @ApiResponse(responseCode = "404", description = "Treaty not found", content = @Content)
    })
    public Mono<ResponseEntity<TreatyDTO>> getTreatyById(
            @Parameter(description = "Unique identifier of the Treaty", required = true)
            @PathVariable UUID treatyId) {
        return treatyService.getTreatyById(treatyId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{treatyId}")
    @Operation(summary = "Update Treaty", description = "Update an existing Treaty")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Treaty updated", content = @Content(schema = @Schema(implementation = TreatyDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content),
        @ApiResponse(responseCode = "404", description = "Treaty not found", content = @Content)
    })
    public Mono<ResponseEntity<TreatyDTO>> updateTreaty(
            @Parameter(description = "Unique identifier of the Treaty", required = true)
            @PathVariable UUID treatyId,
            @Parameter(description = "Updated Treaty", required = true)
            @Valid @RequestBody TreatyDTO dto) {
        return treatyService.updateTreaty(treatyId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{treatyId}")
    @Operation(summary = "Delete Treaty", description = "Delete a Treaty")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Treaty deleted"),
        @ApiResponse(responseCode = "404", description = "Treaty not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> deleteTreaty(
            @Parameter(description = "Unique identifier of the Treaty", required = true)
            @PathVariable UUID treatyId) {
        return treatyService.deleteTreaty(treatyId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}

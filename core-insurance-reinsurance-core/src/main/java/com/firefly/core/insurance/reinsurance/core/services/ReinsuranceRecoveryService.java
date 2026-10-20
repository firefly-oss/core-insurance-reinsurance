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


package com.firefly.core.insurance.reinsurance.core.services;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.insurance.reinsurance.interfaces.dtos.ReinsuranceRecoveryDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Repository operations for claim amount notified to a treaty for recovery.
 */
public interface ReinsuranceRecoveryService {

    /**
     * Filters reinsuranceRecoveries with the given criteria.
     *
     * @param parentId and filterRequest filter criteria
     * @return a page of ReinsuranceRecoveryDTO
     */
    Mono<PaginationResponse<ReinsuranceRecoveryDTO>> filterReinsuranceRecoveries(UUID treatyId, FilterRequest<ReinsuranceRecoveryDTO> filterRequest);

    /**
     * Creates a ReinsuranceRecovery.
     *
     * @param parentId and dto values to store
     * @return the stored DTO
     */
    Mono<ReinsuranceRecoveryDTO> createReinsuranceRecovery(UUID treatyId, ReinsuranceRecoveryDTO dto);

    /**
     * Updates a ReinsuranceRecovery.
     *
     * @param parentId, id and dto
     * @return the updated DTO
     */
    Mono<ReinsuranceRecoveryDTO> updateReinsuranceRecovery(UUID treatyId, UUID reinsuranceRecoveryId, ReinsuranceRecoveryDTO dto);

    /**
     * Deletes a ReinsuranceRecovery.
     *
     * @param parentId and id identifier
     * @return completion signal
     */
    Mono<Void> deleteReinsuranceRecovery(UUID treatyId, UUID reinsuranceRecoveryId);

    /**
     * Loads a ReinsuranceRecovery by id.
     *
     * @param parentId and id identifier
     * @return the DTO
     */
    Mono<ReinsuranceRecoveryDTO> getReinsuranceRecoveryById(UUID treatyId, UUID reinsuranceRecoveryId);
}

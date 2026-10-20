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
import com.firefly.core.insurance.reinsurance.interfaces.dtos.CessionDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Repository operations for premium ceded from a policy to a treaty.
 */
public interface CessionService {

    /**
     * Filters cessions with the given criteria.
     *
     * @param parentId and filterRequest filter criteria
     * @return a page of CessionDTO
     */
    Mono<PaginationResponse<CessionDTO>> filterCessions(UUID treatyId, FilterRequest<CessionDTO> filterRequest);

    /**
     * Creates a Cession.
     *
     * @param parentId and dto values to store
     * @return the stored DTO
     */
    Mono<CessionDTO> createCession(UUID treatyId, CessionDTO dto);

    /**
     * Updates a Cession.
     *
     * @param parentId, id and dto
     * @return the updated DTO
     */
    Mono<CessionDTO> updateCession(UUID treatyId, UUID cessionId, CessionDTO dto);

    /**
     * Deletes a Cession.
     *
     * @param parentId and id identifier
     * @return completion signal
     */
    Mono<Void> deleteCession(UUID treatyId, UUID cessionId);

    /**
     * Loads a Cession by id.
     *
     * @param parentId and id identifier
     * @return the DTO
     */
    Mono<CessionDTO> getCessionById(UUID treatyId, UUID cessionId);
}

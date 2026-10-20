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


package io.fireflysoftware.core.insurance.reinsurance.core.services;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.insurance.reinsurance.interfaces.dtos.TreatyDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Repository operations for reinsurance treaty or facultative placement recorded by the insurer.
 */
public interface TreatyService {

    /**
     * Filters treaties with the given criteria.
     *
     * @param filterRequest filter criteria
     * @return a page of TreatyDTO
     */
    Mono<PaginationResponse<TreatyDTO>> filterTreaties(FilterRequest<TreatyDTO> filterRequest);

    /**
     * Creates a Treaty.
     *
     * @param dto values to store
     * @return the stored DTO
     */
    Mono<TreatyDTO> createTreaty(TreatyDTO dto);

    /**
     * Updates a Treaty.
     *
     * @param id and dto
     * @return the updated DTO
     */
    Mono<TreatyDTO> updateTreaty(UUID treatyId, TreatyDTO dto);

    /**
     * Deletes a Treaty.
     *
     * @param id identifier
     * @return completion signal
     */
    Mono<Void> deleteTreaty(UUID treatyId);

    /**
     * Loads a Treaty by id.
     *
     * @param id identifier
     * @return the DTO
     */
    Mono<TreatyDTO> getTreatyById(UUID treatyId);
}

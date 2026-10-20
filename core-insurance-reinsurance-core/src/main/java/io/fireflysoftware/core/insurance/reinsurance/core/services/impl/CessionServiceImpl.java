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


package io.fireflysoftware.core.insurance.reinsurance.core.services.impl;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import org.fireflyframework.web.error.exceptions.ResourceNotFoundException;
import io.fireflysoftware.core.insurance.reinsurance.core.mappers.CessionMapper;
import io.fireflysoftware.core.insurance.reinsurance.core.services.CessionService;
import io.fireflysoftware.core.insurance.reinsurance.interfaces.dtos.CessionDTO;
import io.fireflysoftware.core.insurance.reinsurance.models.entities.Cession;
import io.fireflysoftware.core.insurance.reinsurance.models.repositories.CessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class CessionServiceImpl implements CessionService {

    @Autowired
    private CessionRepository repository;

    @Autowired
    private CessionMapper mapper;

    @Override
    public Mono<PaginationResponse<CessionDTO>> filterCessions(UUID treatyId, FilterRequest<CessionDTO> filterRequest) {
        if (filterRequest.getFilters() == null) {
            filterRequest.setFilters(new CessionDTO());
        }
        filterRequest.getFilters().setTreatyId(treatyId);
        return FilterUtils.createFilter(Cession.class, mapper::toDTO).filter(filterRequest);
    }

    @Override
    public Mono<CessionDTO> createCession(UUID treatyId, CessionDTO dto) {
        return Mono.just(dto)
                .doOnNext(value -> value.setTreatyId(treatyId))
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<CessionDTO> updateCession(UUID treatyId, UUID cessionId, CessionDTO dto) {
        return repository.findById(cessionId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("Cession", cessionId.toString())))
                .flatMap(existing -> {
                    if (!treatyId.equals(existing.getTreatyId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "Cession with id '" + cessionId + "' does not belong to treaty '" + treatyId + "'"));
                    }
                    mapper.updateEntityFromDto(dto, existing);
                    return repository.save(existing);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteCession(UUID treatyId, UUID cessionId) {
        return repository.findById(cessionId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("Cession", cessionId.toString())))
                .flatMap(existing -> {
                    if (!treatyId.equals(existing.getTreatyId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "Cession with id '" + cessionId + "' does not belong to treaty '" + treatyId + "'"));
                    }
                    return repository.deleteById(cessionId);
                });
    }

    @Override
    public Mono<CessionDTO> getCessionById(UUID treatyId, UUID cessionId) {
        return repository.findById(cessionId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("Cession", cessionId.toString())))
                .flatMap(existing -> {
                    if (!treatyId.equals(existing.getTreatyId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "Cession with id '" + cessionId + "' does not belong to treaty '" + treatyId + "'"));
                    }
                    return Mono.just(mapper.toDTO(existing));
                });
    }
}

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
import io.fireflysoftware.core.insurance.reinsurance.core.mappers.ReinsuranceRecoveryMapper;
import io.fireflysoftware.core.insurance.reinsurance.core.services.ReinsuranceRecoveryService;
import io.fireflysoftware.core.insurance.reinsurance.interfaces.dtos.ReinsuranceRecoveryDTO;
import io.fireflysoftware.core.insurance.reinsurance.models.entities.ReinsuranceRecovery;
import io.fireflysoftware.core.insurance.reinsurance.models.repositories.ReinsuranceRecoveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class ReinsuranceRecoveryServiceImpl implements ReinsuranceRecoveryService {

    @Autowired
    private ReinsuranceRecoveryRepository repository;

    @Autowired
    private ReinsuranceRecoveryMapper mapper;

    @Override
    public Mono<PaginationResponse<ReinsuranceRecoveryDTO>> filterReinsuranceRecoveries(UUID treatyId, FilterRequest<ReinsuranceRecoveryDTO> filterRequest) {
        if (filterRequest.getFilters() == null) {
            filterRequest.setFilters(new ReinsuranceRecoveryDTO());
        }
        filterRequest.getFilters().setTreatyId(treatyId);
        return FilterUtils.createFilter(ReinsuranceRecovery.class, mapper::toDTO).filter(filterRequest);
    }

    @Override
    public Mono<ReinsuranceRecoveryDTO> createReinsuranceRecovery(UUID treatyId, ReinsuranceRecoveryDTO dto) {
        return Mono.just(dto)
                .doOnNext(value -> value.setTreatyId(treatyId))
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ReinsuranceRecoveryDTO> updateReinsuranceRecovery(UUID treatyId, UUID reinsuranceRecoveryId, ReinsuranceRecoveryDTO dto) {
        return repository.findById(reinsuranceRecoveryId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("ReinsuranceRecovery", reinsuranceRecoveryId.toString())))
                .flatMap(existing -> {
                    if (!treatyId.equals(existing.getTreatyId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "ReinsuranceRecovery with id '" + reinsuranceRecoveryId + "' does not belong to treaty '" + treatyId + "'"));
                    }
                    mapper.updateEntityFromDto(dto, existing);
                    return repository.save(existing);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteReinsuranceRecovery(UUID treatyId, UUID reinsuranceRecoveryId) {
        return repository.findById(reinsuranceRecoveryId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("ReinsuranceRecovery", reinsuranceRecoveryId.toString())))
                .flatMap(existing -> {
                    if (!treatyId.equals(existing.getTreatyId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "ReinsuranceRecovery with id '" + reinsuranceRecoveryId + "' does not belong to treaty '" + treatyId + "'"));
                    }
                    return repository.deleteById(reinsuranceRecoveryId);
                });
    }

    @Override
    public Mono<ReinsuranceRecoveryDTO> getReinsuranceRecoveryById(UUID treatyId, UUID reinsuranceRecoveryId) {
        return repository.findById(reinsuranceRecoveryId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("ReinsuranceRecovery", reinsuranceRecoveryId.toString())))
                .flatMap(existing -> {
                    if (!treatyId.equals(existing.getTreatyId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "ReinsuranceRecovery with id '" + reinsuranceRecoveryId + "' does not belong to treaty '" + treatyId + "'"));
                    }
                    return Mono.just(mapper.toDTO(existing));
                });
    }
}

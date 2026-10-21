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
import io.fireflysoftware.core.insurance.reinsurance.core.mappers.TreatyMapper;
import io.fireflysoftware.core.insurance.reinsurance.core.services.TreatyService;
import io.fireflysoftware.core.insurance.reinsurance.interfaces.dtos.TreatyDTO;
import io.fireflysoftware.core.insurance.reinsurance.interfaces.enums.TreatyStatus;
import io.fireflysoftware.core.insurance.reinsurance.models.entities.Treaty;
import io.fireflysoftware.core.insurance.reinsurance.models.repositories.TreatyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class TreatyServiceImpl implements TreatyService {

    @Autowired
    private TreatyRepository repository;

    @Autowired
    private TreatyMapper mapper;

    @Override
    public Mono<PaginationResponse<TreatyDTO>> filterTreaties(FilterRequest<TreatyDTO> filterRequest) {
        return FilterUtils.createFilter(Treaty.class, mapper::toDTO).filter(filterRequest);
    }

    @Override
    public Mono<TreatyDTO> createTreaty(TreatyDTO dto) {
        return Mono.just(dto)
                .map(mapper::toEntity)
                .doOnNext(entity -> {
                    if (entity.getTreatyStatus() == null) {
                        entity.setTreatyStatus(TreatyStatus.DRAFT);
                    }
                })
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<TreatyDTO> updateTreaty(UUID treatyId, TreatyDTO dto) {
        return repository.findById(treatyId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("Treaty", treatyId.toString())))
                .flatMap(existing -> {
                    TreatyStatus current = existing.getTreatyStatus() != null
                            ? existing.getTreatyStatus()
                            : TreatyStatus.DRAFT;
                    mapper.updateEntityFromDto(dto, existing);
                    existing.setTreatyId(treatyId);
                    TreatyStatus next = existing.getTreatyStatus() != null
                            ? existing.getTreatyStatus()
                            : current;
                    if (!TreatyLifecycle.canTransition(current, next)) {
                        return Mono.error(new IllegalStateException(
                                "Treaty " + treatyId + " cannot move from " + current + " to " + next));
                    }
                    existing.setTreatyStatus(next);
                    return repository.save(existing);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteTreaty(UUID treatyId) {
        return repository.findById(treatyId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("Treaty", treatyId.toString())))
                .flatMap(existing -> {
                    TreatyStatus status = existing.getTreatyStatus() != null
                            ? existing.getTreatyStatus()
                            : TreatyStatus.DRAFT;
                    if (status != TreatyStatus.DRAFT) {
                        return Mono.error(new IllegalStateException(
                                "Treaty " + treatyId + " is " + status
                                        + " and cannot be deleted; expire it so the treaty history is kept"));
                    }
                    return repository.deleteById(treatyId);
                });
    }

    @Override
    public Mono<TreatyDTO> getTreatyById(UUID treatyId) {
        return repository.findById(treatyId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("Treaty", treatyId.toString())))
                .map(mapper::toDTO);
    }
}

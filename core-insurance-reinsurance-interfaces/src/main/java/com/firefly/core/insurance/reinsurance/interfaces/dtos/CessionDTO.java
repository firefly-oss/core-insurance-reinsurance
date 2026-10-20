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


package com.firefly.core.insurance.reinsurance.interfaces.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.firefly.core.insurance.reinsurance.interfaces.enums.CessionStatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fireflyframework.utils.annotations.FilterableId;

/**
 * Premium ceded from a policy to a treaty.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CessionDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID cessionId;

    @FilterableId
    @NotNull(message = "Treaty id is required")
    private UUID treatyId;

    /** Logical reference: core-insurance-policies policy. This service does not own that record. */
    @FilterableId
    @NotNull(message = "policyId is required")
    private UUID policyId;

    /** Logical reference: core-insurance-financials premium schedule. This service does not own that record. */
    @FilterableId
    private UUID premiumScheduleId;

    @NotNull(message = "cededPremium is required")
    private BigDecimal cededPremium;

    @Pattern(regexp = "[A-Z]{3}", message = "currency must be a 3-letter ISO code")
    private String currency;

    @NotNull(message = "cessionStatus is required")
    private CessionStatus cessionStatus;

    @NotNull(message = "cededAt is required")
    private LocalDateTime cededAt;

    /** Logical reference: core-banking-payments payment order. This service does not own that record. */
    @FilterableId
    private UUID paymentOrderId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

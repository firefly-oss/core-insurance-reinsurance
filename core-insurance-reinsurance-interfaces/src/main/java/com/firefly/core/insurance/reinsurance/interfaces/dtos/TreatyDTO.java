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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.firefly.core.insurance.reinsurance.interfaces.enums.LineOfBusiness;
import com.firefly.core.insurance.reinsurance.interfaces.enums.TreatyStatus;
import com.firefly.core.insurance.reinsurance.interfaces.enums.TreatyType;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fireflyframework.utils.annotations.FilterableId;

/**
 * Reinsurance treaty or facultative placement recorded by the insurer.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatyDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID treatyId;

    /** Logical reference: Firefly deployment tenant. This service does not own that record. */
    @FilterableId
    @NotNull(message = "tenantId is required")
    private UUID tenantId;

    @NotBlank(message = "treatyNumber is required")
    @Size(max = 64, message = "treatyNumber must not exceed 64 characters")
    private String treatyNumber;

    /** Logical reference: core-common-contract-mgmt contract. This service does not own that record. */
    @FilterableId
    private UUID contractId;

    /** Logical reference: core-common-customer-mgmt party. This service does not own that record. */
    @FilterableId
    @NotNull(message = "reinsurerPartyId is required")
    private UUID reinsurerPartyId;

    @NotNull(message = "treatyType is required")
    private TreatyType treatyType;

    @NotNull(message = "lineOfBusiness is required")
    private LineOfBusiness lineOfBusiness;

    @NotNull(message = "effectiveFrom is required")
    private LocalDate effectiveFrom;

    private LocalDate effectiveTo;

    @NotNull(message = "treatyStatus is required")
    private TreatyStatus treatyStatus;

    private BigDecimal cessionRate;

    @Pattern(regexp = "[A-Z]{3}", message = "currency must be a 3-letter ISO code")
    private String currency;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

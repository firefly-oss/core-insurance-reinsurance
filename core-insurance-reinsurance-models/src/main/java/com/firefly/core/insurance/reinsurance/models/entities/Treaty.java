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


package com.firefly.core.insurance.reinsurance.models.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.firefly.core.insurance.reinsurance.interfaces.enums.LineOfBusiness;
import com.firefly.core.insurance.reinsurance.interfaces.enums.TreatyStatus;
import com.firefly.core.insurance.reinsurance.interfaces.enums.TreatyType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Reinsurance treaty or facultative placement recorded by the insurer.
 * Maps to the 'treaty' table.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("treaty")
public class Treaty {

    @Id
    @Column("treaty_id")
    private UUID treatyId;

    @Column("tenant_id")
    private UUID tenantId;

    @Column("treaty_number")
    private String treatyNumber;

    @Column("contract_id")
    private UUID contractId;

    @Column("reinsurer_party_id")
    private UUID reinsurerPartyId;

    @Column("treaty_type")
    private TreatyType treatyType;

    @Column("line_of_business")
    private LineOfBusiness lineOfBusiness;

    @Column("effective_from")
    private LocalDate effectiveFrom;

    @Column("effective_to")
    private LocalDate effectiveTo;

    @Column("treaty_status")
    private TreatyStatus treatyStatus;

    @Column("cession_rate")
    private BigDecimal cessionRate;

    @Column("currency")
    private String currency;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}

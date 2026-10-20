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


package io.fireflysoftware.core.insurance.reinsurance.models.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import io.fireflysoftware.core.insurance.reinsurance.interfaces.enums.CessionStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Premium ceded from a policy to a treaty.
 * Maps to the 'cession' table.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("cession")
public class Cession {

    @Id
    @Column("cession_id")
    private UUID cessionId;

    @Column("treaty_id")
    private UUID treatyId;

    @Column("policy_id")
    private UUID policyId;

    @Column("premium_schedule_id")
    private UUID premiumScheduleId;

    @Column("ceded_premium")
    private BigDecimal cededPremium;

    @Column("currency")
    private String currency;

    @Column("cession_status")
    private CessionStatus cessionStatus;

    @Column("ceded_at")
    private LocalDateTime cededAt;

    @Column("payment_order_id")
    private UUID paymentOrderId;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}

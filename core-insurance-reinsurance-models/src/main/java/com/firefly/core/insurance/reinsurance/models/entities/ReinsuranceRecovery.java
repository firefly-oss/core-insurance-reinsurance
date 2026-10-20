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
import java.time.LocalDateTime;
import java.util.UUID;

import com.firefly.core.insurance.reinsurance.interfaces.enums.ReinsuranceRecoveryStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Claim amount notified to a treaty for recovery.
 * Maps to the 'reinsurance_recovery' table.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("reinsurance_recovery")
public class ReinsuranceRecovery {

    @Id
    @Column("reinsurance_recovery_id")
    private UUID reinsuranceRecoveryId;

    @Column("treaty_id")
    private UUID treatyId;

    @Column("claim_id")
    private UUID claimId;

    @Column("recovery_amount")
    private BigDecimal recoveryAmount;

    @Column("currency")
    private String currency;

    @Column("recovery_status")
    private ReinsuranceRecoveryStatus recoveryStatus;

    @Column("notified_at")
    private LocalDateTime notifiedAt;

    @Column("payment_order_id")
    private UUID paymentOrderId;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}

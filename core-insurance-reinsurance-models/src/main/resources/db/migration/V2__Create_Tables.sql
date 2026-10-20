-- Flyway V2: tables for core-insurance-reinsurance

-- Reinsurance treaty or facultative placement recorded by the insurer
CREATE TABLE treaty (
    treaty_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL,
    treaty_number VARCHAR(64) NOT NULL UNIQUE,
    contract_id UUID NULL,
    reinsurer_party_id UUID NOT NULL,
    treaty_type treaty_type_enum NOT NULL,
    line_of_business line_of_business_enum NOT NULL,
    effective_from DATE NOT NULL,
    effective_to DATE NULL,
    treaty_status treaty_status_enum NOT NULL,
    cession_rate DECIMAL(19, 4) NULL,
    currency VARCHAR(3) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Premium ceded from a policy to a treaty
CREATE TABLE cession (
    cession_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    treaty_id UUID NOT NULL REFERENCES treaty (treaty_id) ON DELETE CASCADE,
    policy_id UUID NOT NULL,
    premium_schedule_id UUID NULL,
    ceded_premium DECIMAL(19, 4) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    cession_status cession_status_enum NOT NULL,
    ceded_at TIMESTAMP WITH TIME ZONE NOT NULL,
    payment_order_id UUID NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Claim amount notified to a treaty for recovery
CREATE TABLE reinsurance_recovery (
    reinsurance_recovery_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    treaty_id UUID NOT NULL REFERENCES treaty (treaty_id) ON DELETE CASCADE,
    claim_id UUID NOT NULL,
    recovery_amount DECIMAL(19, 4) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    recovery_status reinsurance_recovery_status_enum NOT NULL,
    notified_at TIMESTAMP WITH TIME ZONE NOT NULL,
    payment_order_id UUID NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_treaty_tenant_id ON treaty (tenant_id);
CREATE INDEX idx_treaty_contract_id ON treaty (contract_id);
CREATE INDEX idx_treaty_reinsurer_party_id ON treaty (reinsurer_party_id);
CREATE INDEX idx_cession_treaty_id ON cession (treaty_id);
CREATE INDEX idx_cession_policy_id ON cession (policy_id);
CREATE INDEX idx_cession_premium_schedule_id ON cession (premium_schedule_id);
CREATE INDEX idx_cession_payment_order_id ON cession (payment_order_id);
CREATE INDEX idx_reinsurance_recovery_treaty_id ON reinsurance_recovery (treaty_id);
CREATE INDEX idx_reinsurance_recovery_claim_id ON reinsurance_recovery (claim_id);
CREATE INDEX idx_reinsurance_recovery_payment_order_id ON reinsurance_recovery (payment_order_id);
COMMENT ON COLUMN treaty.tenant_id IS 'Logical reference: Firefly deployment tenant. No foreign key across services.';
COMMENT ON COLUMN treaty.contract_id IS 'Logical reference: core-common-contract-mgmt contract. No foreign key across services.';
COMMENT ON COLUMN treaty.reinsurer_party_id IS 'Logical reference: core-common-customer-mgmt party. No foreign key across services.';
COMMENT ON COLUMN cession.policy_id IS 'Logical reference: core-insurance-policies policy. No foreign key across services.';
COMMENT ON COLUMN cession.premium_schedule_id IS 'Logical reference: core-insurance-financials premium schedule. No foreign key across services.';
COMMENT ON COLUMN cession.payment_order_id IS 'Logical reference: core-banking-payments payment order. No foreign key across services.';
COMMENT ON COLUMN reinsurance_recovery.claim_id IS 'Logical reference: core-insurance-claims claim. No foreign key across services.';
COMMENT ON COLUMN reinsurance_recovery.payment_order_id IS 'Logical reference: core-banking-payments payment order. No foreign key across services.';

-- Flyway V1: enum types for core-insurance-reinsurance

CREATE TYPE treaty_type_enum AS ENUM (
    'QUOTA_SHARE',
    'SURPLUS',
    'EXCESS_OF_LOSS',
    'FACULTATIVE'
);

CREATE TYPE treaty_status_enum AS ENUM (
    'DRAFT',
    'ACTIVE',
    'RUN_OFF',
    'EXPIRED'
);

CREATE TYPE line_of_business_enum AS ENUM (
    'MOTOR',
    'PROPERTY',
    'LIABILITY',
    'LIFE',
    'HEALTH',
    'MARINE',
    'AVIATION',
    'ACCIDENT',
    'OTHER'
);

CREATE TYPE cession_status_enum AS ENUM (
    'RECORDED',
    'BORDEREAU',
    'SETTLED'
);

CREATE TYPE reinsurance_recovery_status_enum AS ENUM (
    'NOTIFIED',
    'AGREED',
    'COLLECTED',
    'DISPUTED'
);


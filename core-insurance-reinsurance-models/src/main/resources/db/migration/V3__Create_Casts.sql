-- Flyway V3: implicit varchar casts for enum parameters

CREATE CAST (varchar AS treaty_type_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS treaty_status_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS line_of_business_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS cession_status_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS reinsurance_recovery_status_enum) WITH INOUT AS IMPLICIT;

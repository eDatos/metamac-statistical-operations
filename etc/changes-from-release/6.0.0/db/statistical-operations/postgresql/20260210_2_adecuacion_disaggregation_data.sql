-- --------------------------------------------------------------------------------------------------
-- EDATOS-5543 - Modificaciones posteriores sobre los campos de desagregación, migrada a edatos y PRODUCER
-- --------------------------------------------------------------------------------------------------

--- Se actualizan todas las instancias con los valores de su operación padre

BEGIN;

UPDATE TB_INSTANCES instances
SET
    disaggregation_by_sex         = operation.disaggregation_by_sex,
    disaggregation_by_age         = operation.disaggregation_by_age,
    disaggregation_by_nationality = operation.disaggregation_by_nationality,
    disaggregation_by_disability  = operation.disaggregation_by_disability
FROM TB_OPERATIONS operation
WHERE instances.operation_fk = operation.id;

COMMIT;
-- --------------------------------------------------------------------------------------------------
-- EDATOS-5543 - Modificaciones posteriores sobre los campos de desagregación, migrada a edatos y PRODUCER
-- --------------------------------------------------------------------------------------------------

-- Se eliminan las columnas de  desagregaciones por sexo, edad,
-- nacionalidad y discapacidad de la tabla TB_OPERATIONS.

ALTER TABLE TB_OPERATIONS
    DROP COLUMN disaggregation_by_sex,
    DROP COLUMN disaggregation_by_age,
    DROP COLUMN disaggregation_by_nationality,
    DROP COLUMN disaggregation_by_disability;
COMMIT;
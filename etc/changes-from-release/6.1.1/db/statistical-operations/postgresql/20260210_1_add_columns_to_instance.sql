-- --------------------------------------------------------------------------------------------------
-- EDATOS-5543 - Modificaciones posteriores sobre los campos de desagregación, migrada a edatos y PRODUCER
-- --------------------------------------------------------------------------------------------------

-- Añade nuevas columnas a la tabla TB_INSTANCES para almacenar las desagregaciones por sexo, edad,
-- nacionalidad y discapacidad.

ALTER TABLE TB_INSTANCES
    ADD COLUMN disaggregation_by_sex BOOLEAN NULL,
    ADD COLUMN disaggregation_by_age BOOLEAN NULL,
    ADD COLUMN disaggregation_by_nationality BOOLEAN NULL,
    ADD COLUMN disaggregation_by_disability BOOLEAN NULL;
COMMIT;
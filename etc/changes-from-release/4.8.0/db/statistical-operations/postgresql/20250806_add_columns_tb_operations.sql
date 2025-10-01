-- --------------------------------------------------------------------------------------------------
-- EDATOS-5174 - Crear nuevos campos en e-Operaciones
-- --------------------------------------------------------------------------------------------------

-- Añade nuevas columnas a la tabla TB_OPERATIONS para almacenar las desagregaciones por sexo, edad,
-- nacionalidad y discapacidad.
-- Estas columnas permiten valores nulos inicialmente pero se definen con valor por defecto 'false'.

ALTER TABLE TB_OPERATIONS
    ADD COLUMN disaggregation_by_sex BOOLEAN NULL,
    ADD COLUMN disaggregation_by_age BOOLEAN NULL,
    ADD COLUMN disaggregation_by_nationality BOOLEAN NULL,
    ADD COLUMN disaggregation_by_disability BOOLEAN NULL;

COMMIT;
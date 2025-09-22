-- --------------------------------------------------------------------------------------------------
-- EDATOS-5174 - Crear nuevos campos en e-Operaciones
-- --------------------------------------------------------------------------------------------------

-- Añade nuevas columnas a la tabla TB_OPERATIONS para almacenar las desagregaciones por sexo, edad,
-- nacionalidad y discapacidad.
-- Estas columnas permiten valores nulos inicialmente pero se definen con valor por defecto 'false'.

ALTER TABLE TB_OPERATIONS
    ADD COLUMN disaggregation_by_sex BOOLEAN DEFAULT false,
    ADD COLUMN disaggregation_by_age BOOLEAN DEFAULT false,
    ADD COLUMN disaggregation_by_nationality BOOLEAN DEFAULT false,
    ADD COLUMN disaggregation_by_disability BOOLEAN DEFAULT false;

-- Inicializa todas las operaciones existentes con valor 'false' en los nuevos campos

UPDATE TB_OPERATIONS
SET disaggregation_by_sex = false,
    disaggregation_by_age = false,
    disaggregation_by_nationality = false,
    disaggregation_by_disability = false;

COMMIT;
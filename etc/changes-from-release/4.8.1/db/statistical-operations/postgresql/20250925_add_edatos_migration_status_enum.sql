-- --------------------------------------------------------------------------------------------------
-- EDATOS-5189 Nueva propiedad: Migrada a EDATOS
-- --------------------------------------------------------------------------------------------------

-- Añade nueva columna a la tabla de operaciones que determina el estado de migración a EDATOS
ALTER TABLE TB_OPERATIONS
ADD COLUMN EDATOS_MIGRATION_STATUS VARCHAR(255);

-- 'Update' statement without 'where' updates all table rows at once
UPDATE TB_OPERATIONS
SET EDATOS_MIGRATION_STATUS = 'NOT_STARTED';

ALTER TABLE TB_OPERATIONS
ALTER COLUMN EDATOS_MIGRATION_STATUS SET NOT NULL;

commit;

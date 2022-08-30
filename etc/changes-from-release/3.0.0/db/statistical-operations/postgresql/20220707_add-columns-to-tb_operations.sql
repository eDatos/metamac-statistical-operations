-- --------------------------------------------------------------------------------------------------
-- EDATOS-3680
-- [metamac-statistical-operations] Añadir dos nuevos metadatos: técnico responsable y técnico auxiliar
-- --------------------------------------------------------------------------------------------------

-- Añade nuevas columnas a la tabla de operaciones que determina el técnico/a responsable y técnico/a auxiliar

ALTER TABLE TB_OPERATIONS
ADD COLUMN ASSISTANT_TECHNICIAN VARCHAR(255);

ALTER TABLE TB_OPERATIONS
ADD COLUMN TECHNICIAN_IN_CHARGE VARCHAR(255);

commit;

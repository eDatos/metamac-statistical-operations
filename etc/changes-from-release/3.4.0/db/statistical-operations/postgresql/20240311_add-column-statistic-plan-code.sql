-- --------------------------------------------------------------------------------------------------
-- EDATOS-4412 - Incorporar un nuevo metadato en el inventario de operaciones estadísticas para registrar el código aprobado en el plan estadístico.
-- --------------------------------------------------------------------------------------------------

-- Añade nueva columna a la tabla de operaciones

ALTER TABLE TB_OPERATIONS
ADD COLUMN statistic_plan_code VARCHAR(255);

commit;

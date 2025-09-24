-- --------------------------------------------------------------------------------------------------
-- EDATOS-5195 - Añadir nuevo campo "STATISTICAL OPERATION URL" en TB_OPERATIONS
-- --------------------------------------------------------------------------------------------------

-- Añade una nueva columna para almacenar la URL de la operación estadística.
-- Campo opcional (NULL permitido), pero con valor por defecto 'FILL_ME'.

ALTER TABLE TB_OPERATIONS
    ADD COLUMN statistical_operation_url VARCHAR(500) DEFAULT 'FILL_ME';

-- Inicializa todas las operaciones existentes con el valor por defecto

UPDATE TB_OPERATIONS
SET statistical_operation_url = 'FILL_ME';

COMMIT;

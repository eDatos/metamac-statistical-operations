-- --------------------------------------------------------------------------------------------------
---- EDATOS-5286 - Modificar la tabla que alimenta OFFICIALITY_TYPE para que sea jerárquica
-- Nuevo campo en tabla TB_LIS_OFFICIALITY_TYPES con el orden de visualización

--------------------------------------------------------------------------------------------------------------------------

ALTER TABLE TB_LIS_OFFICIALITY_TYPES ADD COLUMN visualisation_order INTEGER;
ALTER TABLE TB_LIS_OFFICIALITY_TYPES ADD COLUMN hierarchy_level  INTEGER NOT NULL DEFAULT 0;

update TB_LIS_OFFICIALITY_TYPES set visualisation_order = -1 where identifier = 'ESTUDIO' ;
update  TB_LIS_OFFICIALITY_TYPES set visualisation_order = -2 where identifier = 'OFICIAL' ;

--el campo order debe ser not nulo.
ALTER TABLE TB_LIS_OFFICIALITY_TYPES ALTER COLUMN VISUALISATION_ORDER set NOT NULL;

COMMIT;


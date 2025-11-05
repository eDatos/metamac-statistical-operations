-- --------------------------------------------------------------------------------------------------
---- EDATOS-5286 - Modificar la tabla que alimenta OFFICIALITY_TYPE para que sea jerárquica
---- Borrar de la tabla TB_LIS_OFFICIALITY_TYPES los dos valores antiguos OFICIAL Y ESTUDIO 

--------------------------------------------------------------------------------------------------------------------------

delete from TB_LIS_OFFICIALITY_TYPES where identifier in ('OFICIAL', 'ESTUDIO');

COMMIT;


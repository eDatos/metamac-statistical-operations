-- --------------------------------------------------------------------------------------------------
-- ---- EDATOS-5210 - Modificar el campo COLL_METHOD
-- ---- Borrar de la tabla  TB_LIS_COLL_METHODS todos los valores antiguos
-- --------------------------------------------------------------------------------------------------


DELETE
FROM TB_LIS_COLL_METHODS
WHERE IDENTIFIER IN ('AUTOENUMERACION', 'ENTREVISTA_DIRECTA', 'CONVERSACION_TELEFONO', 'TRANSCRIPCION_DOCUMENTO',
                     'OBSERVACION_DIRECTA', 'FORMAS_MIXTAS', 'OTRAS', 'NO_APLICABLE', 'INDETERMINADA');

COMMIT;


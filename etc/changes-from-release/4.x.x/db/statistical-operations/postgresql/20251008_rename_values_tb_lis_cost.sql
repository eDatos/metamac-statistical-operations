-- --------------------------------------------------------------------------------------------------
-- EDATOS-5212  Modificar los campo COST_BURDEN, COST y añadir un nuevo campo COST_DETAIL
--
--     Se actualizan los valores en BBDD según la lista del IBESTAT
--
--     La lista de valores del COST para el IBESTAT es esta:
--          Irrelevante
--          Bajo (igual o inferior a 20.000€)
--          Medio (entre 20.001€ y 100.000€)
--          Alto (entre 100.001€ y 500.000€)
--          Muy alto (más de 500.001€)
--
-- --------------------------------------------------------------------------------------------------

UPDATE TB_LIS_COSTS
SET IDENTIFIER = 'IRRELEVANTE'
WHERE IDENTIFIER = 'MUY_BAJO';
-- ----------------------------------------------------------------------------------------------------------------------------
-- EDATOS-5305 Incorporar enlace a la url de difusion de la operacion
-- ----------------------------------------------------------------------------------------------------------------------------

-- alter table TB_LIS_OPERATION_URLS
ALTER TABLE TB_LIS_OPERATION_URLS
    ALTER COLUMN NAME_FK SET NOT NULL;
COMMIT;

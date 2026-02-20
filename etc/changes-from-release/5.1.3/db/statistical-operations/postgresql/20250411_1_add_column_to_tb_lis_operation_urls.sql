-- ----------------------------------------------------------------------------------------------------------------------------
-- EDATOS-5305 Incorporar enlace a la url de difusion de la operacion
-- ----------------------------------------------------------------------------------------------------------------------------

-- alter table TB_LIS_OPERATION_URLS to add new column;
ALTER TABLE TB_LIS_OPERATION_URLS
    ADD COLUMN NAME_FK BIGINT;

ALTER TABLE TB_LIS_OPERATION_URLS ADD CONSTRAINT FK_TB_LIS_OPERATION_URLS_NAME_INTERNATIONAL_STRING_ID
    FOREIGN KEY (NAME_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID);
COMMIT;

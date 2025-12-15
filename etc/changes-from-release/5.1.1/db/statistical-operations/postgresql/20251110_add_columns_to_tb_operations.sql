-- ----------------------------------------------------------------------------------------------------------------------------
-- EDATOS-5289 Implementar etiquetas destacado y reciente
-- -----------------2-----------------------------------------------------------------------------------------------------------

-- alter table TB_OPERATIONS to add new columns;
ALTER TABLE TB_OPERATIONS
    ADD COLUMN NEWNESS_UNTIL_DATE_TZ VARCHAR(50);
ALTER TABLE TB_OPERATIONS
    ADD COLUMN NEWNESS_UNTIL_DATE TIMESTAMP;
ALTER TABLE TB_OPERATIONS
    ADD COLUMN FEATURED_UNTIL_DATE_TZ VARCHAR(50);
ALTER TABLE TB_OPERATIONS
    ADD COLUMN FEATURED_UNTIL_DATE TIMESTAMP;
COMMIT;

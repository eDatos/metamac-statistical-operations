-- ----------------------------------------------------------------------------------------------------------------------------
-- EDATOS-5211 - Cambiar la etiqueta del metadato INFORMATION_SUPPLIERS y crear un nuevo metadato INFORMATION_SUPPLIERS_PRIVATE
-- ----------------------------------------------------------------------------------------------------------------------------

-- alter table tb_instances to add new column;
ALTER TABLE TB_INSTANCES
    ADD COLUMN PRIVATE_INF_SUPPLIERS_FK BIGINT;

-- add foreign key constraint to tb_international_strings
ALTER TABLE TB_INSTANCES
    ADD CONSTRAINT FK_TB_INSTANCES_PRIVATE_INF_SUPPLIERS_FK
        FOREIGN KEY (PRIVATE_INF_SUPPLIERS_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID);

COMMIT;

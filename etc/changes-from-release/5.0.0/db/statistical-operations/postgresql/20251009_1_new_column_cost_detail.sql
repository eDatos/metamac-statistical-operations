-- --------------------------------------------------------------------------------------------------
-- EDATOS-5212  Modificar los campo COST_BURDEN, COST y añadir un nuevo campo COST_DETAIL
--
--
-- --------------------------------------------------------------------------------------------------

ALTER TABLE TB_INSTANCES
ADD COLUMN COST_DETAIL_FK BIGINT NULL;

ALTER TABLE TB_INSTANCES ADD CONSTRAINT FK_TB_INSTANCES_COST_DETAIL_FK
    FOREIGN KEY (COST_DETAIL_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID);

commit;

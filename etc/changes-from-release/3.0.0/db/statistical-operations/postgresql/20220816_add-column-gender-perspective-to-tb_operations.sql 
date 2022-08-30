-- --------------------------------------------------------------------------------------------------
-- EDATOS-3731 - Incluir metadato relativo al abordaje de la perspectiva de género
-- --------------------------------------------------------------------------------------------------
-- ADD COLUMN GENDER_PERSPECTIVE_FK TO TB_OPERATIONS

ALTER TABLE TB_OPERATIONS
ADD COLUMN GENDER_PERSPECTIVE_FK BIGINT;

ALTER TABLE TB_OPERATIONS
    ADD CONSTRAINT FK_TB_OPERATIONS_GENDER_PERSPECTIVE_FK
        FOREIGN KEY (GENDER_PERSPECTIVE_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID);

commit;

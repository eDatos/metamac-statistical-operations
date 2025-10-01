-- --------------------------------------------------------------------------------------------------
-- EDATOS-5195 - Añadir soporte de URLs internacionalizadas por operación estadística
-- --------------------------------------------------------------------------------------------------

-- Tabla intermedia: cada operación puede tener varias URLs
-- y cada URL está referenciada a un INTERNATIONAL_STRING (que a su vez enlaza con LOCALIZED_STRINGS).



CREATE TABLE TB_LIS_OPERATION_URLS (
                                       ID BIGINT NOT NULL,
                                       UUID VARCHAR(36) NOT NULL,
                                       VERSION BIGINT NOT NULL,
                                       INTERNATIONAL_STRING_FK BIGINT NOT NULL,
                                       OPERATION_FK BIGINT NOT NULL
);

ALTER TABLE TB_LIS_OPERATION_URLS ADD CONSTRAINT PK_TB_LIS_OPERATION_URLS
    PRIMARY KEY (ID);

ALTER TABLE TB_LIS_OPERATION_URLS
    ADD CONSTRAINT UQ_TB_LIS_OPERATION_URLS UNIQUE (UUID);

ALTER TABLE TB_LIS_OPERATION_URLS ADD CONSTRAINT FK_TB_LIS_OPERATION_URLS_INTERNATIONAL_STRING_ID
    FOREIGN KEY (INTERNATIONAL_STRING_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID);
ALTER TABLE TB_LIS_OPERATION_URLS ADD CONSTRAINT FK_TB_LIS_OPERATION_URLS_OPERATION_ID
    FOREIGN KEY (OPERATION_FK) REFERENCES TB_OPERATIONS (ID);

-- Índices
CREATE INDEX IDX_OPERATION_URLS_OPERATION ON TB_LIS_OPERATION_URLS(OPERATION_FK);
CREATE INDEX IDX_OPERATION_URLS_INTSTR ON TB_LIS_OPERATION_URLS(INTERNATIONAL_STRING_FK);

-- Nueva secuencia  en TB_SEQUENCES
INSERT INTO TB_SEQUENCES(SEQUENCE_NAME, SEQUENCE_NEXT_VALUE) VALUES ('OPERATION_URLS', 1);

COMMIT;

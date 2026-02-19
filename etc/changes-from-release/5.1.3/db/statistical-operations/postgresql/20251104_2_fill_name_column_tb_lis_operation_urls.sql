-- ---------------------------------------------------------------------------------------------------
-- ---- EDATOS-5305 Incorporar enlace a la url de difusion de la operacion
-- ---- Modificar tabla TB_LIS_OPERATION_URLS  para que tenga una descripcion por defecto.
-- --------------------------------------------------------------------------------------------------
-- NOTA. Cada bloque DO $$ ... END $$ debe ejecutarse en secuencia. Si la BD está con autocommit, asegúrarase de ejecutar este script en el orden mostrado.
--------------------------------------------------------------------------------------------------------------------------

--!! ATENCIÓN!!!. PARA EJECUTAR ESTE SCRIPT PRIMERO SE HA DEBIDO EJECUTAR EL SCRIPT 20250411_1_add_column_to_tb_lis_operation_urls.sql


DO
$$
    DECLARE
        REC RECORD;
    BEGIN
        FOR REC IN SELECT ID FROM TB_LIS_OPERATION_URLS
            LOOP

                INSERT INTO TB_INTERNATIONAL_STRINGS (ID, VERSION)
                VALUES (GET_NEXT_SEQUENCE_VALUE('I18NSTRS'), 1);

                INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION)
                VALUES (GET_NEXT_SEQUENCE_VALUE('L10NSTRS'), 'URL', 'es',
                        GET_NEXT_SEQUENCE_VALUE('I18NSTRS'), 1);
                UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'L10NSTRS';

                INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION)
                VALUES (GET_NEXT_SEQUENCE_VALUE('L10NSTRS'), 'URL', 'en',
                        GET_NEXT_SEQUENCE_VALUE('I18NSTRS'), 1);
                UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'L10NSTRS';

                INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION)
                VALUES (GET_NEXT_SEQUENCE_VALUE('L10NSTRS'), 'URL', 'ca',
                        GET_NEXT_SEQUENCE_VALUE('I18NSTRS'), 1);
                UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'L10NSTRS';

                UPDATE TB_LIS_OPERATION_URLS SET NAME_FK = GET_NEXT_SEQUENCE_VALUE('I18NSTRS') WHERE ID = REC.ID;

                UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'I18NSTRS';
            END LOOP;
    END
$$;

COMMIT
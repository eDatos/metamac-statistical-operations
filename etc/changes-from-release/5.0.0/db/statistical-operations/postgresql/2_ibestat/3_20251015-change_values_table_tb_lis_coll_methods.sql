-- --------------------------------------------------------------------------------------------------
-- --------------------------------------  SOLO PARA IBESTAT  ---------------------------------------
-- --------------------------------------------------------------------------------------------------
-- --------------------------------------------------------------------------------------------------
-- ---- EDATOS-5210 - Modificar el campo COLL_METHOD
-- ---- Actualizar los valores antiguos de las instancias de operaciones existentes a los nuevos valores indicados por ibestat.

-- --- Desde IBESTAT nos facilitan la siguiente correlacion de mapeo:
-- ----------- AUTOENUMERACION → 02 (Utilización de datos administrativos)
-- ----------- ENTREVISTA_DIRECTA → 01 (Recogida directa de datos estadísticos)
-- ----------- CONVERSACION_TELEFONO → 01 (Recogida directa de datos estadísticos)
-- ----------- TRANSCRIPCION_DOCUMENTO → 02 (Utilización de datos administrativos)
-- ----------- OBSERVACION_DIRECTA → 01 (Recogida directa de datos estadísticos)
-- ----------- FORMAS_MIXTAS → 03 (Utilización conjunta de datos estadísticos y administrativos)
-- ----------- OTRAS → 07 (Otras formas de obtención de datos)
-- ----------- NO_APLICABLE → 08 (No aplicable)
-- ----------- INDETERMINADA → 09 (Aún no determinada)
-- --------------------------------------------------------------------------------------------------


-- AUTOENUMERACION → 02 (Utilización de datos administrativos)
DO $$
    DECLARE
        v_coll_method_id INTEGER;
    BEGIN
        SELECT id INTO v_coll_method_id
        FROM TB_LIS_COLL_METHODS
        WHERE identifier = '02';

        IF v_coll_method_id IS NULL THEN
            RAISE EXCEPTION 'No se encontró el identifier 02 en TB_LIS_COLL_METHODS';
        END IF;

        UPDATE tb_instances
        SET coll_method_fk = v_coll_method_id
        WHERE id IN (SELECT ID FROM TB_LIS_COLL_METHODS WHERE Identifier = 'AUTOENUMERACION');

        RAISE NOTICE 'AUTOENUMERACION → 02 - Filas actualizadas: %', (SELECT COUNT(*) FROM tb_instances WHERE coll_method_fk = v_coll_method_id);
    END $$;

-- ENTREVISTA_DIRECTA → 01 (Recogida directa de datos estadísticos)
DO $$
    DECLARE
        v_coll_method_id INTEGER;
    BEGIN
        SELECT id INTO v_coll_method_id
        FROM TB_LIS_COLL_METHODS
        WHERE identifier = '01';

        IF v_coll_method_id IS NULL THEN
            RAISE EXCEPTION 'No se encontró el identifier 01 en TB_LIS_COLL_METHODS';
        END IF;

        UPDATE tb_instances
        SET coll_method_fk = v_coll_method_id
        WHERE id IN (SELECT ID FROM TB_LIS_COLL_METHODS WHERE Identifier = 'ENTREVISTA_DIRECTA');

        RAISE NOTICE 'ENTREVISTA_DIRECTA → 01 - Filas actualizadas: %', (SELECT COUNT(*) FROM tb_instances WHERE coll_method_fk = v_coll_method_id);
    END $$;

-- CONVERSACION_TELEFONO → 01 (Recogida directa de datos estadísticos)
DO $$
    DECLARE
        v_coll_method_id INTEGER;
    BEGIN
        SELECT id INTO v_coll_method_id
        FROM TB_LIS_COLL_METHODS
        WHERE identifier = '01';

        IF v_coll_method_id IS NULL THEN
            RAISE EXCEPTION 'No se encontró el identifier 01 en TB_LIS_COLL_METHODS';
        END IF;

        UPDATE tb_instances
        SET coll_method_fk = v_coll_method_id
        WHERE id IN (SELECT ID FROM TB_LIS_COLL_METHODS WHERE Identifier = 'CONVERSACION_TELEFONO');

        RAISE NOTICE 'CONVERSACION_TELEFONO → 01 - Filas actualizadas: %', (SELECT COUNT(*) FROM tb_instances WHERE coll_method_fk = v_coll_method_id);
    END $$;

-- TRANSCRIPCION_DOCUMENTO → 02 (Utilización de datos administrativos)
DO $$
    DECLARE
        v_coll_method_id INTEGER;
    BEGIN
        SELECT id INTO v_coll_method_id
        FROM TB_LIS_COLL_METHODS
        WHERE identifier = '02';

        IF v_coll_method_id IS NULL THEN
            RAISE EXCEPTION 'No se encontró el identifier 02 en TB_LIS_COLL_METHODS';
        END IF;

        UPDATE tb_instances
        SET coll_method_fk = v_coll_method_id
        WHERE id IN (SELECT ID FROM TB_LIS_COLL_METHODS WHERE Identifier = 'TRANSCRIPCION_DOCUMENTO');

        RAISE NOTICE 'TRANSCRIPCION_DOCUMENTO → 02 - Filas actualizadas: %', (SELECT COUNT(*) FROM tb_instances WHERE coll_method_fk = v_coll_method_id);
    END $$;

-- OBSERVACION_DIRECTA → 01 (Recogida directa de datos estadísticos)
DO $$
    DECLARE
        v_coll_method_id INTEGER;
    BEGIN
        SELECT id INTO v_coll_method_id
        FROM TB_LIS_COLL_METHODS
        WHERE identifier = '01';

        IF v_coll_method_id IS NULL THEN
            RAISE EXCEPTION 'No se encontró el identifier 01 en TB_LIS_COLL_METHODS';
        END IF;

        UPDATE tb_instances
        SET coll_method_fk = v_coll_method_id
        WHERE id IN (SELECT ID FROM TB_LIS_COLL_METHODS WHERE Identifier = 'OBSERVACION_DIRECTA');

        RAISE NOTICE 'OBSERVACION_DIRECTA → 01 - Filas actualizadas: %', (SELECT COUNT(*) FROM tb_instances WHERE coll_method_fk = v_coll_method_id);
    END $$;

-- FORMAS_MIXTAS → 03 (Utilización conjunta de datos estadísticos y administrativos)
DO $$
    DECLARE
        v_coll_method_id INTEGER;
    BEGIN
        SELECT id INTO v_coll_method_id
        FROM TB_LIS_COLL_METHODS
        WHERE identifier = '03';

        IF v_coll_method_id IS NULL THEN
            RAISE EXCEPTION 'No se encontró el identifier 03 en TB_LIS_COLL_METHODS';
        END IF;

        UPDATE tb_instances
        SET coll_method_fk = v_coll_method_id
        WHERE id IN (SELECT ID FROM TB_LIS_COLL_METHODS WHERE Identifier = 'FORMAS_MIXTAS');

        RAISE NOTICE 'FORMAS_MIXTAS → 03 - Filas actualizadas: %', (SELECT COUNT(*) FROM tb_instances WHERE coll_method_fk = v_coll_method_id);
    END $$;

-- OTRAS → 07 (Otras formas de obtención de datos)
DO $$
    DECLARE
        v_coll_method_id INTEGER;
    BEGIN
        SELECT id INTO v_coll_method_id
        FROM TB_LIS_COLL_METHODS
        WHERE identifier = '07';

        IF v_coll_method_id IS NULL THEN
            RAISE EXCEPTION 'No se encontró el identifier 07 en TB_LIS_COLL_METHODS';
        END IF;

        UPDATE tb_instances
        SET coll_method_fk = v_coll_method_id
        WHERE id IN (SELECT ID FROM TB_LIS_COLL_METHODS WHERE Identifier = 'OTRAS');

        RAISE NOTICE 'OTRAS → 07 - Filas actualizadas: %', (SELECT COUNT(*) FROM tb_instances WHERE coll_method_fk = v_coll_method_id);
    END $$;

-- NO_APLICABLE → 08 (No aplicable)
DO $$
    DECLARE
        v_coll_method_id INTEGER;
    BEGIN
        SELECT id INTO v_coll_method_id
        FROM TB_LIS_COLL_METHODS
        WHERE identifier = '08';

        IF v_coll_method_id IS NULL THEN
            RAISE EXCEPTION 'No se encontró el identifier 08 en TB_LIS_COLL_METHODS';
        END IF;

        UPDATE tb_instances
        SET coll_method_fk = v_coll_method_id
        WHERE id IN (SELECT ID FROM TB_LIS_COLL_METHODS WHERE Identifier = 'NO_APLICABLE');

        RAISE NOTICE 'NO_APLICABLE → 08 - Filas actualizadas: %', (SELECT COUNT(*) FROM tb_instances WHERE coll_method_fk = v_coll_method_id);
    END $$;

-- INDETERMINADA → 09 (Aún no determinada)
DO $$
    DECLARE
        v_coll_method_id INTEGER;
    BEGIN
        SELECT id INTO v_coll_method_id
        FROM TB_LIS_COLL_METHODS
        WHERE identifier = '09';

        IF v_coll_method_id IS NULL THEN
            RAISE EXCEPTION 'No se encontró el identifier 09 en TB_LIS_COLL_METHODS';
        END IF;

        UPDATE tb_instances
        SET coll_method_fk = v_coll_method_id
        WHERE id IN (SELECT ID FROM TB_LIS_COLL_METHODS WHERE Identifier = 'INDETERMINADA');

        RAISE NOTICE 'INDETERMINADA → 09 - Filas actualizadas: %', (SELECT COUNT(*) FROM tb_instances WHERE coll_method_fk = v_coll_method_id);
    END $$;

COMMIT;


-- --------------------------------------------------------------------------------------------------
-- ---- EDATOS-5210 - Modificar el campo COLL_METHOD
-- ---- Actualizar los valores antiguos de las instancias de operaciones existentes a los nuevos valores indicados por ibestat.
-- --------------------------------------------------------------------------------------------------


-- FIXME EDATOS-5210: Preparacion de script hasta que IBESTAT nos facilite la correspondencia de lo existente con los nuevos datos alojados

-- AUTOENUMERACION
DO $$
    DECLARE
        v_coll_method_id INTEGER;
    BEGIN
        -- Recuperar el valor en la variable
        SELECT id
        INTO v_coll_method_id
        FROM TB_LIS_COLL_METHODS
        WHERE identifier = 'PORDEFINIR';

        -- Validar que se encontró el registro
        IF v_coll_method_id IS NULL THEN
            RAISE EXCEPTION 'No se encontró el identifier PORDEFINIR en TB_LIS_COLL_METHODS';
        END IF;

        -- Hacer el UPDATE usando la variable
        UPDATE tb_instances
        SET coll_method_fk = v_coll_method_id
        WHERE id IN (SELECT ID FROM TB_LIS_COLL_METHODS WHERE Identifier = 'AUTOENUMERACION');

        -- Mostrar cuántas filas se actualizaron
        RAISE NOTICE 'AUTOENUMERACION - Filas actualizadas: %', (SELECT COUNT(*) FROM tb_instances WHERE coll_method_fk = v_coll_method_id);
    END $$;

-- ENTREVISTA_DIRECTA
DO $$
    DECLARE
        v_coll_method_id INTEGER;
    BEGIN
        -- Recuperar el valor en la variable
        SELECT id
        INTO v_coll_method_id
        FROM TB_LIS_COLL_METHODS
        WHERE identifier = 'PORDEFINIR';

        -- Validar que se encontró el registro
        IF v_coll_method_id IS NULL THEN
            RAISE EXCEPTION 'No se encontró el identifier PORDEFINIR en TB_LIS_COLL_METHODS';
        END IF;

        -- Hacer el UPDATE usando la variable
        UPDATE tb_instances
        SET coll_method_fk = v_coll_method_id
        WHERE id IN (SELECT ID FROM TB_LIS_COLL_METHODS WHERE Identifier = 'ENTREVISTA_DIRECTA');

        -- Mostrar cuántas filas se actualizaron
        RAISE NOTICE 'ENTREVISTA_DIRECTA - Filas actualizadas: %', (SELECT COUNT(*) FROM tb_instances WHERE coll_method_fk = v_coll_method_id);
    END $$;

-- CONVERSACION_TELEFONO
DO $$
    DECLARE
        v_coll_method_id INTEGER;
    BEGIN
        -- Recuperar el valor en la variable
        SELECT id
        INTO v_coll_method_id
        FROM TB_LIS_COLL_METHODS
        WHERE identifier = 'PORDEFINIR';

        -- Validar que se encontró el registro
        IF v_coll_method_id IS NULL THEN
            RAISE EXCEPTION 'No se encontró el identifier PORDEFINIR en TB_LIS_COLL_METHODS';
        END IF;

        -- Hacer el UPDATE usando la variable
        UPDATE tb_instances
        SET coll_method_fk = v_coll_method_id
        WHERE id IN (SELECT ID FROM TB_LIS_COLL_METHODS WHERE Identifier = 'CONVERSACION_TELEFONO');

        -- Mostrar cuántas filas se actualizaron
        RAISE NOTICE 'CONVERSACION_TELEFONO - Filas actualizadas: %', (SELECT COUNT(*) FROM tb_instances WHERE coll_method_fk = v_coll_method_id);
    END $$;

-- TRANSCRIPCION_DOCUMENTO
DO $$
    DECLARE
        v_coll_method_id INTEGER;
    BEGIN
        -- Recuperar el valor en la variable
        SELECT id
        INTO v_coll_method_id
        FROM TB_LIS_COLL_METHODS
        WHERE identifier = 'PORDEFINIR';

        -- Validar que se encontró el registro
        IF v_coll_method_id IS NULL THEN
            RAISE EXCEPTION 'No se encontró el identifier PORDEFINIR en TB_LIS_COLL_METHODS';
        END IF;

        -- Hacer el UPDATE usando la variable
        UPDATE tb_instances
        SET coll_method_fk = v_coll_method_id
        WHERE id IN (SELECT ID FROM TB_LIS_COLL_METHODS WHERE Identifier = 'TRANSCRIPCION_DOCUMENTO');

        -- Mostrar cuántas filas se actualizaron
        RAISE NOTICE 'TRANSCRIPCION_DOCUMENTO - Filas actualizadas: %', (SELECT COUNT(*) FROM tb_instances WHERE coll_method_fk = v_coll_method_id);
    END $$;

-- OBSERVACION_DIRECTA
DO $$
    DECLARE
        v_coll_method_id INTEGER;
    BEGIN
        -- Recuperar el valor en la variable
        SELECT id
        INTO v_coll_method_id
        FROM TB_LIS_COLL_METHODS
        WHERE identifier = 'PORDEFINIR';

        -- Validar que se encontró el registro
        IF v_coll_method_id IS NULL THEN
            RAISE EXCEPTION 'No se encontró el identifier PORDEFINIR en TB_LIS_COLL_METHODS';
        END IF;

        -- Hacer el UPDATE usando la variable
        UPDATE tb_instances
        SET coll_method_fk = v_coll_method_id
        WHERE id IN (SELECT ID FROM TB_LIS_COLL_METHODS WHERE Identifier = 'OBSERVACION_DIRECTA');

        -- Mostrar cuántas filas se actualizaron
        RAISE NOTICE 'OBSERVACION_DIRECTA - Filas actualizadas: %', (SELECT COUNT(*) FROM tb_instances WHERE coll_method_fk = v_coll_method_id);
    END $$;

-- FORMAS_MIXTAS
DO $$
    DECLARE
        v_coll_method_id INTEGER;
    BEGIN
        -- Recuperar el valor en la variable
        SELECT id
        INTO v_coll_method_id
        FROM TB_LIS_COLL_METHODS
        WHERE identifier = 'PORDEFINIR';

        -- Validar que se encontró el registro
        IF v_coll_method_id IS NULL THEN
            RAISE EXCEPTION 'No se encontró el identifier PORDEFINIR en TB_LIS_COLL_METHODS';
        END IF;

        -- Hacer el UPDATE usando la variable
        UPDATE tb_instances
        SET coll_method_fk = v_coll_method_id
        WHERE id IN (SELECT ID FROM TB_LIS_COLL_METHODS WHERE Identifier = 'FORMAS_MIXTAS');

        -- Mostrar cuántas filas se actualizaron
        RAISE NOTICE 'FORMAS_MIXTAS - Filas actualizadas: %', (SELECT COUNT(*) FROM tb_instances WHERE coll_method_fk = v_coll_method_id);
    END $$;

-- OTRAS
DO $$
    DECLARE
        v_coll_method_id INTEGER;
    BEGIN
        -- Recuperar el valor en la variable
        SELECT id
        INTO v_coll_method_id
        FROM TB_LIS_COLL_METHODS
        WHERE identifier = 'PORDEFINIR';

        -- Validar que se encontró el registro
        IF v_coll_method_id IS NULL THEN
            RAISE EXCEPTION 'No se encontró el identifier PORDEFINIR en TB_LIS_COLL_METHODS';
        END IF;

        -- Hacer el UPDATE usando la variable
        UPDATE tb_instances
        SET coll_method_fk = v_coll_method_id
        WHERE id IN (SELECT ID FROM TB_LIS_COLL_METHODS WHERE Identifier = 'OTRAS');

        -- Mostrar cuántas filas se actualizaron
        RAISE NOTICE 'OTRAS - Filas actualizadas: %', (SELECT COUNT(*) FROM tb_instances WHERE coll_method_fk = v_coll_method_id);
    END $$;

-- NO_APLICABLE
DO $$
    DECLARE
        v_coll_method_id INTEGER;
    BEGIN
        -- Recuperar el valor en la variable
        SELECT id
        INTO v_coll_method_id
        FROM TB_LIS_COLL_METHODS
        WHERE identifier = 'PORDEFINIR';

        -- Validar que se encontró el registro
        IF v_coll_method_id IS NULL THEN
            RAISE EXCEPTION 'No se encontró el identifier PORDEFINIR en TB_LIS_COLL_METHODS';
        END IF;

        -- Hacer el UPDATE usando la variable
        UPDATE tb_instances
        SET coll_method_fk = v_coll_method_id
        WHERE id IN (SELECT ID FROM TB_LIS_COLL_METHODS WHERE Identifier = 'NO_APLICABLE');

        -- Mostrar cuántas filas se actualizaron
        RAISE NOTICE 'NO_APLICABLE - Filas actualizadas: %', (SELECT COUNT(*) FROM tb_instances WHERE coll_method_fk = v_coll_method_id);
    END $$;

-- INDETERMINADA
DO $$
    DECLARE
        v_coll_method_id INTEGER;
    BEGIN
        -- Recuperar el valor en la variable
        SELECT id
        INTO v_coll_method_id
        FROM TB_LIS_COLL_METHODS
        WHERE identifier = 'PORDEFINIR';

        -- Validar que se encontró el registro
        IF v_coll_method_id IS NULL THEN
            RAISE EXCEPTION 'No se encontró el identifier PORDEFINIR en TB_LIS_COLL_METHODS';
        END IF;

        -- Hacer el UPDATE usando la variable
        UPDATE tb_instances
        SET coll_method_fk = v_coll_method_id
        WHERE id IN (SELECT ID FROM TB_LIS_COLL_METHODS WHERE Identifier = 'INDETERMINADA');

        -- Mostrar cuántas filas se actualizaron
        RAISE NOTICE 'INDETERMINADA - Filas actualizadas: %', (SELECT COUNT(*) FROM tb_instances WHERE coll_method_fk = v_coll_method_id);
    END $$;

COMMIT;


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
DO
$$
    DECLARE
        v_coll_method_id_target INTEGER;
        v_coll_method_id_source INTEGER;
    BEGIN
        SELECT id INTO v_coll_method_id_target FROM TB_LIS_COLL_METHODS WHERE identifier = '02';
        IF v_coll_method_id_target IS NULL THEN
            RAISE EXCEPTION 'No se encontró el identifier 02 en TB_LIS_COLL_METHODS';
        END IF;

        SELECT id INTO v_coll_method_id_source FROM TB_LIS_COLL_METHODS WHERE identifier = 'AUTOENUMERACION';
        IF v_coll_method_id_source IS NULL THEN
            RAISE EXCEPTION 'No se encontró AUTOENUMERACION';
        END IF;

        UPDATE tb_instances SET coll_method_fk = v_coll_method_id_target WHERE coll_method_fk = v_coll_method_id_source;


        RAISE NOTICE 'AUTOENUMERACION → 02 - Filas actualizadas: %', (SELECT COUNT(*)
                                                                      FROM tb_instances
                                                                      WHERE coll_method_fk = v_coll_method_id_target);
    END
$$;

-- ENTREVISTA_DIRECTA → 01 (Recogida directa de datos estadísticos)
DO
$$
    DECLARE
        v_coll_method_id_target INTEGER;
        v_coll_method_id_source INTEGER;
    BEGIN
        SELECT id INTO v_coll_method_id_target FROM TB_LIS_COLL_METHODS WHERE identifier = '01';
        IF v_coll_method_id_target IS NULL THEN
            RAISE EXCEPTION 'No se encontró el identifier 01 en TB_LIS_COLL_METHODS';
        END IF;

        SELECT id INTO v_coll_method_id_source FROM TB_LIS_COLL_METHODS WHERE identifier = 'ENTREVISTA_DIRECTA';
        IF v_coll_method_id_source IS NULL THEN
            RAISE EXCEPTION 'No se encontró ENTREVISTA_DIRECTA';
        END IF;

        UPDATE tb_instances SET coll_method_fk = v_coll_method_id_target WHERE coll_method_fk = v_coll_method_id_source;


        RAISE NOTICE 'ENTREVISTA_DIRECTA → 01 - Filas actualizadas: %', (SELECT COUNT(*)
                                                                         FROM tb_instances
                                                                         WHERE coll_method_fk = v_coll_method_id_target);
    END
$$;

-- CONVERSACION_TELEFONO → 01 (Recogida directa de datos estadísticos)
DO
$$
    DECLARE
        v_coll_method_id_target INTEGER;
        v_coll_method_id_source INTEGER;

    BEGIN
        SELECT id INTO v_coll_method_id_target FROM TB_LIS_COLL_METHODS WHERE identifier = '01';
        IF v_coll_method_id_target IS NULL THEN
            RAISE EXCEPTION 'No se encontró el identifier 01 en TB_LIS_COLL_METHODS';
        END IF;

        SELECT id INTO v_coll_method_id_source FROM TB_LIS_COLL_METHODS WHERE identifier = 'CONVERSACION_TELEFONO';
        IF v_coll_method_id_source IS NULL THEN
            RAISE EXCEPTION 'No se encontró CONVERSACION_TELEFONO';
        END IF;

        UPDATE tb_instances SET coll_method_fk = v_coll_method_id_target WHERE coll_method_fk = v_coll_method_id_source;


        RAISE NOTICE 'CONVERSACION_TELEFONO → 01 - Filas actualizadas: %', (SELECT COUNT(*)
                                                                            FROM tb_instances
                                                                            WHERE coll_method_fk = v_coll_method_id_target);
    END
$$;

-- TRANSCRIPCION_DOCUMENTO → 02 (Utilización de datos administrativos)
DO
$$
    DECLARE
        v_coll_method_id_target INTEGER;
        v_coll_method_id_source INTEGER;
    BEGIN
        SELECT id INTO v_coll_method_id_target FROM TB_LIS_COLL_METHODS WHERE identifier = '02';
        IF v_coll_method_id_target IS NULL THEN
            RAISE EXCEPTION 'No se encontró el identifier 02 en TB_LIS_COLL_METHODS';
        END IF;

        SELECT id INTO v_coll_method_id_source FROM TB_LIS_COLL_METHODS WHERE identifier = 'TRANSCRIPCION_DOCUMENTO';
        IF v_coll_method_id_source IS NULL THEN
            RAISE EXCEPTION 'No se encontró TRANSCRIPCION_DOCUMENTO';
        END IF;

        UPDATE tb_instances SET coll_method_fk = v_coll_method_id_target WHERE coll_method_fk = v_coll_method_id_source;


        RAISE NOTICE 'TRANSCRIPCION_DOCUMENTO → 02 - Filas actualizadas: %', (SELECT COUNT(*)
                                                                              FROM tb_instances
                                                                              WHERE coll_method_fk = v_coll_method_id_target);
    END
$$;

-- OBSERVACION_DIRECTA → 01 (Recogida directa de datos estadísticos)
DO
$$
    DECLARE
        v_coll_method_id_target INTEGER;
        v_coll_method_id_source INTEGER;
    BEGIN
        SELECT id INTO v_coll_method_id_target FROM TB_LIS_COLL_METHODS WHERE identifier = '01';
        IF v_coll_method_id_target IS NULL THEN
            RAISE EXCEPTION 'No se encontró el identifier 01 en TB_LIS_COLL_METHODS';
        END IF;

        SELECT id INTO v_coll_method_id_source FROM TB_LIS_COLL_METHODS WHERE identifier = 'OBSERVACION_DIRECTA';
        IF v_coll_method_id_source IS NULL THEN
            RAISE EXCEPTION 'No se encontró OBSERVACION_DIRECTA';
        END IF;

        UPDATE tb_instances SET coll_method_fk = v_coll_method_id_target WHERE coll_method_fk = v_coll_method_id_source;


        RAISE NOTICE 'OBSERVACION_DIRECTA → 01 - Filas actualizadas: %', (SELECT COUNT(*)
                                                                          FROM tb_instances
                                                                          WHERE coll_method_fk = v_coll_method_id_target);
    END
$$;

-- FORMAS_MIXTAS → 03 (Utilización conjunta de datos estadísticos y administrativos)
DO
$$
    DECLARE
        v_coll_method_id_target INTEGER;
        v_coll_method_id_source INTEGER;

    BEGIN
        SELECT id INTO v_coll_method_id_target FROM TB_LIS_COLL_METHODS WHERE identifier = '03';
        IF v_coll_method_id_target IS NULL THEN
            RAISE EXCEPTION 'No se encontró el identifier 03 en TB_LIS_COLL_METHODS';
        END IF;

        SELECT id INTO v_coll_method_id_source FROM TB_LIS_COLL_METHODS WHERE identifier = 'FORMAS_MIXTAS';
        IF v_coll_method_id_source IS NULL THEN
            RAISE EXCEPTION 'No se encontró FORMAS_MIXTAS';
        END IF;

        UPDATE tb_instances SET coll_method_fk = v_coll_method_id_target WHERE coll_method_fk = v_coll_method_id_source;


        RAISE NOTICE 'FORMAS_MIXTAS → 03 - Filas actualizadas: %', (SELECT COUNT(*)
                                                                    FROM tb_instances
                                                                    WHERE coll_method_fk = v_coll_method_id_target);
    END
$$;

-- OTRAS → 07 (Otras formas de obtención de datos)
DO
$$
    DECLARE
        v_coll_method_id_target INTEGER;
        v_coll_method_id_source INTEGER;

    BEGIN
        SELECT id INTO v_coll_method_id_target FROM TB_LIS_COLL_METHODS WHERE identifier = '07';
        IF v_coll_method_id_target IS NULL THEN
            RAISE EXCEPTION 'No se encontró el identifier 07 en TB_LIS_COLL_METHODS';
        END IF;

        SELECT id INTO v_coll_method_id_source FROM TB_LIS_COLL_METHODS WHERE identifier = 'OTRAS';
        IF v_coll_method_id_source IS NULL THEN
            RAISE EXCEPTION 'No se encontró OTRAS';
        END IF;

        UPDATE tb_instances SET coll_method_fk = v_coll_method_id_target WHERE coll_method_fk = v_coll_method_id_source;


        RAISE NOTICE 'OTRAS → 07 - Filas actualizadas: %', (SELECT COUNT(*)
                                                            FROM tb_instances
                                                            WHERE coll_method_fk = v_coll_method_id_target);
    END
$$;

-- NO_APLICABLE → 08 (No aplicable)
DO
$$
    DECLARE
        v_coll_method_id_target INTEGER;
        v_coll_method_id_source INTEGER;

    BEGIN
        SELECT id INTO v_coll_method_id_target FROM TB_LIS_COLL_METHODS WHERE identifier = '08';
        IF v_coll_method_id_target IS NULL THEN
            RAISE EXCEPTION 'No se encontró el identifier 08 en TB_LIS_COLL_METHODS';
        END IF;

        SELECT id INTO v_coll_method_id_source FROM TB_LIS_COLL_METHODS WHERE identifier = 'NO_APLICABLE';
        IF v_coll_method_id_source IS NULL THEN
            RAISE EXCEPTION 'No se encontró NO_APLICABLE';
        END IF;

        UPDATE tb_instances SET coll_method_fk = v_coll_method_id_target WHERE coll_method_fk = v_coll_method_id_source;

        RAISE NOTICE 'NO_APLICABLE → 08 - Filas actualizadas: %', (SELECT COUNT(*)
                                                                   FROM tb_instances
                                                                   WHERE coll_method_fk = v_coll_method_id_target);
    END
$$;

-- INDETERMINADA → 09 (Aún no determinada)
DO
$$
    DECLARE
        v_coll_method_id_target INTEGER;
        v_coll_method_id_source INTEGER;

    BEGIN
        SELECT id INTO v_coll_method_id_target FROM TB_LIS_COLL_METHODS WHERE identifier = '09';
        IF v_coll_method_id_target IS NULL THEN
            RAISE EXCEPTION 'No se encontró el identifier 09 en TB_LIS_COLL_METHODS';
        END IF;

        SELECT id INTO v_coll_method_id_source FROM TB_LIS_COLL_METHODS WHERE identifier = 'INDETERMINADA';
        IF v_coll_method_id_source IS NULL THEN
            RAISE EXCEPTION 'No se encontró INDETERMINADA';
        END IF;

        UPDATE tb_instances SET coll_method_fk = v_coll_method_id_target WHERE coll_method_fk = v_coll_method_id_source;

        RAISE NOTICE 'INDETERMINADA → 09 - Filas actualizadas: %', (SELECT COUNT(*)
                                                                    FROM tb_instances
                                                                    WHERE coll_method_fk = v_coll_method_id_target);
    END
$$;

COMMIT;

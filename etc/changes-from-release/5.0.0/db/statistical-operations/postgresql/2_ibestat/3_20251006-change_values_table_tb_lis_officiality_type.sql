-- --------------------------------------------------------------------------------------------------
---- EDATOS-5286 - Modificar la tabla que alimenta OFFICIALITY_TYPE para que sea jerárquica
---- Actualizar los valores antiguos de las operaciones existentes al nuevo valor de oficialidad indicado por ibestat.
-- Desde ibestat se indican que los que están, están todos asociados al valor OFICIAL. Y se deben asignar al valor “Operaciones estadísticas propiamente dichas” que tiene código 01_02

--------------------------------------------------------------------------------------------------------------------------
DO $$
DECLARE
    v_officiality_id INTEGER;
BEGIN
    -- Recuperar el valor en la variable
    SELECT id 
    INTO v_officiality_id
    FROM TB_LIS_OFFICIALITY_TYPES 
    WHERE identifier = '01_02';
    
    -- Validar que se encontró el registro
    IF v_officiality_id IS NULL THEN
        RAISE EXCEPTION 'No se encontró el identifier 01_02 en TB_LIS_OFFICIALITY_TYPES';
    END IF;
    
    -- Hacer el UPDATE usando la variable
    UPDATE tb_operations
    SET officiality_type_fk = v_officiality_id
    WHERE officiality_type_fk = 2;  --LOS QUE TENÍAN EL VALOR OFICIAL
    
    -- Mostrar cuántas filas se actualizaron
    RAISE NOTICE 'Filas actualizadas: %', (SELECT COUNT(*) FROM tb_operations WHERE officiality_type_fk = v_officiality_id);
END $$;


COMMIT;


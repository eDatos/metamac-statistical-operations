-- --------------------------------------------------------------------------------------------------
-- EDATOS-4125 - Eliminar la propiedad del dialecto de BBDD
-- 
-- Se elimina la propiedad con el valor metamac.statistical_operations.db.dialect
-- --------------------------------------------------------------------------------------------------

delete from tb_data_configurations where conf_key = 'deprecated.metamac.statistical_operations.db.dialect';
commit;
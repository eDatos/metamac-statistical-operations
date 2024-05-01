-- --------------------------------------------------------------------------------------------------
-- EDATOS-4425 - Crear clave primaria borrada para realizar migración.
-- 
-- Se crea otra vez la clave primaria de tabla tb_ei_secondary_areas
-- --------------------------------------------------------------------------------------------------


 ALTER TABLE tb_ei_secondary_areas ADD CONSTRAINT pk_tb_ei_secondary_areas PRIMARY KEY (secun_subject_areas_fk,tb_operations);
 
 commit;
-- --------------------------------------------------------------------------------------------------
-- EDATOS-4425 - Aplicar elementos de tema en el inventario de operaciones estadísticas
-- 
-- Se depreca el campo subject_area_fk
-- --------------------------------------------------------------------------------------------------

ALTER TABLE TB_OPERATIONS RENAME COLUMN subject_area_fk TO deprecated_subject_area_fk;
ALTER TABLE TB_OPERATIONS DROP CONSTRAINT fk_tb_operations_subject_area_fk;

ALTER TABLE TB_EI_SECONDARY_AREAS RENAME COLUMN secun_subject_areas_fk TO deprecated_secun_subject_areas_fk;
ALTER TABLE TB_EI_SECONDARY_AREAS DROP CONSTRAINT pk_tb_ei_secondary_areas;
ALTER TABLE TB_EI_SECONDARY_AREAS DROP CONSTRAINT fk_tb_ei_secondary_areas_secun_subject_areas_fk;
ALTER TABLE TB_EI_SECONDARY_AREAS ALTER COLUMN deprecated_secun_subject_areas_fk drop not null;


commit;
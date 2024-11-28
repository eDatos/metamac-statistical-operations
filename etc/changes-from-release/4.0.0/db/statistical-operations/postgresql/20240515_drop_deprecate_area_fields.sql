-- --------------------------------------------------------------------------------------------------
-- EDATOS-4490 - Tarea relacionada con edatos-4425 donde se pasan a "deprecados" los campos áreas que contienen enlaces a temas (external-items) y ahora
-- pasarán a estar enlazados a elementos de tema.
-- Una vez se comprueba que todo está ok con la release anterior se pueden eliminar los campos deprecados que conservan los valores antiguos.
-- 
-- 
-- --------------------------------------------------------------------------------------------------

ALTER TABLE TB_OPERATIONS DROP deprecated_subject_area_fk;
ALTER TABLE TB_EI_SECONDARY_AREAS DROP deprecated_secun_subject_areas_fk;

commit;
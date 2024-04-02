-- --------------------------------------------------------------------------------------------------
-- EDATOS-4425 - Aplicar elementos de tema en el inventario de operaciones estadísticas
-- 
-- Se crea el campo subject_area_fk y sus índices y constraints
-- --------------------------------------------------------------------------------------------------

-- External items new index to title_fk for deleting better performance
CREATE INDEX pk_tb_external_items_title_fk ON tb_external_items USING btree (title_fk);

-------------------------
----TB_OPERATIONS

 ALTER TABLE TB_OPERATIONS ADD COLUMN SUBJECT_AREA_FK BIGINT;
 ALTER TABLE tb_operations ADD CONSTRAINT fk_tb_operations_subject_area_fk FOREIGN KEY (subject_area_fk) REFERENCES tb_external_items(id); 
 -- tb_operations new index to subject_area_fk for deleting better performance
  CREATE INDEX pk_tb_operations_subject_area_fk ON tb_operations USING btree (subject_area_fk);

-------------------------
----TB_EI_SECONDARY_AREAS
 
 ALTER TABLE TB_EI_SECONDARY_AREAS ADD COLUMN SECUN_SUBJECT_AREAS_FK BIGINT;
 ALTER TABLE tb_ei_secondary_areas ADD CONSTRAINT fk_tb_ei_secondary_areas_secun_subject_areas_fk FOREIGN KEY (secun_subject_areas_fk) REFERENCES tb_external_items(id);
-- tb_ei_secondary_areas new index to subject_area_fk for deleting better performance
CREATE INDEX pk_tb_ei_secondary_areas_subject_area_fk ON tb_ei_secondary_areas USING btree (secun_subject_areas_fk);	

commit;
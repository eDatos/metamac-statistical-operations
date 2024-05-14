-- --------------------------------------------------------------------------------------------------
-- EDATOS-4425 - Se obtiene la información de los elementos de temas.
-- 
-- Obtener información de los elementos de tema.
-- --------------------------------------------------------------------------------------------------

-- Paso 1 Obtener la información de cada elemento de tema que se encuentra en la tabla  temp_category_element_by_category previamente cargada con los valores básicos a partir de hoja excel de relaciones

-- Paso 1.1 Coger la 6ª columna de la hoja excel que contiene los inserts con las relaciones informadas y volcarla sobre la tabla "temp_category_element_by_category" de la base de datos de srm
--ATENCIÓN!! Asegurarse que los códigos puestos en la columna "Elementos de tema" no tienen espacios porque se ha detectado algún espacio al final en algún caso. 


-- Paso 1.2 Terminar de rellenar los datos que faltan lanzando el siguiente update: 


update temp_category_element_by_category
set 
urn_provider = ve.urn_provider,
uri = '/latest/categoryelements/' || ve.code,
management_app_url = '/#structuralResources/categoryElement;id=' || ve.code,
 label_es = (select "label" from tb_localised_strings co_title where co_title.international_string_fk = ve.short_name_fk and co_title.locale = 'es'), 
 label_ca =  (select "label" from tb_localised_strings co_title where co_title.international_string_fk = ve.short_name_fk and co_title.locale = 'ca'),
 label_en = (select "label" from tb_localised_strings co_title where co_title.international_string_fk = ve.short_name_fk and co_title.locale = 'en')
from (
select t.code, t.category_element_urn, tre.short_name_fk, taa.urn_provider 
from temp_category_element_by_category t, tb_cat_resource_elements tcre, tb_annotable_artefacts taa, tb_resource_elements tre 
where 
taa.code = t.code
and tcre.tb_resource_elements = tre.id 
and tre.identifiable_artefact_fk = taa.id ) ve
where temp_category_element_by_category.category_element_urn = ve.category_element_urn;


-- Paso 2. Exportar los datos de la tabla a CSV. Para ello botón derecho sobre la tabla "Export data-> CSV"

-- Paso 3. Importar el CSV obtenido en el paso anterior en la tabla temp_category_element_by_category de la base de datos de statistical-operations

-- Paso 4. Borrar la tabla temp_category_element_by_category de la base de datos de srm
drop table temp_category_element_by_category;

-- Paso 5. En statistical-operations. Generar el external item para  cada operación y  actualizar el área de la operación con el elemento de tema
---- 5.1 Generar insert y update 
select 
'INSERT INTO TB_INTERNATIONAL_STRINGS (ID, VERSION) VALUES (GET_NEXT_SEQUENCE_VALUE(''I18NSTRS''), 1);  
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (GET_NEXT_SEQUENCE_VALUE(''L10NSTRS''), ''' || replace(t.label_es, '''', '''''')  || ''', ''es'', GET_NEXT_SEQUENCE_VALUE(''I18NSTRS''), 1);
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = ''L10NSTRS'';'
|| case when (t.label_ca is not null and t.label_ca <> '') then '
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (GET_NEXT_SEQUENCE_VALUE(''L10NSTRS''), ''' || replace(t.label_ca, '''', '''''')   || ''', ''ca'', GET_NEXT_SEQUENCE_VALUE(''I18NSTRS''), 1);
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = ''L10NSTRS'';' else '' end
|| 
case when (t.label_en is not null and t.label_en <> '') then '
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (GET_NEXT_SEQUENCE_VALUE(''L10NSTRS''), ''' || replace(t.label_en, '''', '''''')   || ''', ''en'', GET_NEXT_SEQUENCE_VALUE(''I18NSTRS''), 1);
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = ''L10NSTRS'';' else '' end
|| '
INSERT INTO TB_EXTERNAL_ITEMS(ID, CODE, URI, URN, MANAGEMENT_APP_URL, VERSION, TYPE, TITLE_FK) values (GET_NEXT_SEQUENCE_VALUE(''EXTERNAL_ITEMS''), ''' || t.code  || ''', ''' || t.uri  || ''', ''' || t.category_element_urn  || ''', ''' || t.management_app_url  || ''', ''' || t."version"  || ''', ''' ||  t."type"  || ''', GET_NEXT_SEQUENCE_VALUE(''I18NSTRS''));
UPDATE tb_operations SET subject_area_fk = GET_NEXT_SEQUENCE_VALUE(''EXTERNAL_ITEMS'') where id =' || to2.id || ';
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = ''I18NSTRS''; 
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = ''EXTERNAL_ITEMS'';'
from tb_operations to2, tb_external_items tei,  temp_category_element_by_category t  where  
to2.deprecated_subject_area_fk is not null
and tei.id = to2.deprecated_subject_area_fk 
and tei.urn = t.category_urn;

----5.2 Ejecutar los scripts generados en el paso anterior.

---- 5.3 Comprobar que todo ha ido bien si la consulta siguiente no devuelve resultados
select count(*) from tb_operations to2 where subject_area_fk is null;

----------------------

-- Paso 6. En statistical-operations. Generar el external item para  cada área secundaria de cada operación y  actualizar las áreas secundarias de la operación con el elemento de tema
---- 6.1 Generar insert y update para las áreas secundarias 
select  
'INSERT INTO TB_INTERNATIONAL_STRINGS (ID, VERSION) VALUES (GET_NEXT_SEQUENCE_VALUE(''I18NSTRS''), 1);  
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (GET_NEXT_SEQUENCE_VALUE(''L10NSTRS''), ''' || replace(t.label_es, '''', '''''')  || ''', ''es'', GET_NEXT_SEQUENCE_VALUE(''I18NSTRS''), 1);
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = ''L10NSTRS'';'
|| case when (t.label_ca is not null and t.label_ca <> '') then '
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (GET_NEXT_SEQUENCE_VALUE(''L10NSTRS''), ''' || replace(t.label_ca, '''', '''''')   || ''', ''ca'', GET_NEXT_SEQUENCE_VALUE(''I18NSTRS''), 1);
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = ''L10NSTRS'';' else '' end
|| 
case when (t.label_en is not null and t.label_en <> '') then '
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (GET_NEXT_SEQUENCE_VALUE(''L10NSTRS''), ''' || replace(t.label_en, '''', '''''')   || ''', ''en'', GET_NEXT_SEQUENCE_VALUE(''I18NSTRS''), 1);
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = ''L10NSTRS'';' else '' end
|| '
INSERT INTO TB_EXTERNAL_ITEMS(ID, CODE, URI, URN, MANAGEMENT_APP_URL, VERSION, TYPE, TITLE_FK) values (GET_NEXT_SEQUENCE_VALUE(''EXTERNAL_ITEMS''), ''' || t.code  || ''', ''' || t.uri  || ''', ''' || t.category_element_urn  || ''', ''' || t.management_app_url  || ''', ''' || t."version"  || ''', ''' ||  t."type"  || ''', GET_NEXT_SEQUENCE_VALUE(''I18NSTRS''));
UPDATE tb_ei_secondary_areas SET secun_subject_areas_fk = GET_NEXT_SEQUENCE_VALUE(''EXTERNAL_ITEMS'') where deprecated_secun_subject_areas_fk =' || tesa.deprecated_secun_subject_areas_fk  || ';
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = ''I18NSTRS''; 
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = ''EXTERNAL_ITEMS'';'
from tb_ei_secondary_areas tesa , tb_external_items tei,  temp_category_element_by_category t  where  
tesa.deprecated_secun_subject_areas_fk  is not null
and tei.id = tesa.deprecated_secun_subject_areas_fk 
and tei.urn = t.category_urn;

----6.2 Ejecutar los scripts generados en el paso anterior.

---- 6.3 Comprobar que todo ha ido bien si la consulta siguiente no devuelve resultados
select count(*) from tb_ei_secondary_areas tesa where secun_subject_areas_fk  is  null;


--7. Borrar tabla temporal de statistical-operations
drop table temp_category_element_by_category;

commit;



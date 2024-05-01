-- --------------------------------------------------------------------------------------------------
-- EDATOS-4425 - Crear la tabla temporal que relaciona los temas que hay en el statistical-operations con su elemento de tema asociado.
-- 
-- 
-- --------------------------------------------------------------------------------------------------

-- Paso 1 Crear la siguiente tabla en bases de datos
---- srm
---- statistical-operations

CREATE TABLE temp_category_element_by_category (
	category_code varchar(255) NOT NULL,
	category_element_urn varchar(255) NOT NULL,
	code varchar(255) NOT NULL,
	category_urn varchar(4000),
	code_nested varchar(255),
	uri varchar(4000), 
	urn_provider varchar(4000),
	management_app_url varchar(4000),
	"version" int8 NOT NULL,
	label_es VARCHAR(4000),
	label_ca VARCHAR(4000),
	label_en VARCHAR(4000),
	type varchar(255) NOT NULL
);

commit;


-- -------------------------------------------------------------
-- EDATOS-5208 - Revisar la implementación del metadato MEASURES
-- -------------------------------------------------------------

-- elimina valores antiguos de unidades de medidas correspondientes a conceptos, dado
-- que ahora se usan clasificaciones

DELETE FROM tb_ei_units_measure unit_measure
WHERE EXISTS (
  SELECT 1
  FROM tb_external_items external_item
  WHERE external_item.id = unit_measure.unit_measure_fk
    AND external_item.type IN ('structuralResources#concept', 'structuralResources#conceptScheme')
);

COMMIT;

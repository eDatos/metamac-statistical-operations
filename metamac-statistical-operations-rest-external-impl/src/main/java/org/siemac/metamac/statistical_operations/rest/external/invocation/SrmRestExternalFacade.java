package org.siemac.metamac.statistical_operations.rest.external.invocation;

import java.util.List;
import java.util.Map;

import org.siemac.metamac.rest.structural_resources.v1_0.domain.Category;
import org.siemac.metamac.rest.structural_resources.v1_0.domain.CategoryResource;

public interface SrmRestExternalFacade {

    public List<org.siemac.metamac.rest.structural_resources.v1_0.domain.ConceptResource> retrieveConceptsByConceptScheme(String urn);
    CategoryResource retrieveCategoryByCategoryElement(String categorySchemeUrn, String categoryElementCode);
    public Map<String, CategoryResource> retrieveDefaultCategoriesByCategoryElementCode(String categorySchemeUrn);
    public Category retrieveCategoryByUrn(String categoryUrn);
}

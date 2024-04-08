package org.siemac.metamac.statistical_operations.rest.internal.invocation;

import java.util.List;
import java.util.Map;

import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Category;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CategoryResourceInternal;

public interface SrmRestInternalFacade {

    public List<org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptResourceInternal> retrieveConceptsByConceptScheme(String urn);
    CategoryResourceInternal retrieveCategoryByCategoryElement(String categorySchemeUrn, String categoryElementCode);
    public Map<String, CategoryResourceInternal> retrieveDefaultCategoriesByCategoryElementCode(String categorySchemeUrn);
    public Category retrieveCategoryByUrn(String categoryUrn);
}

package org.siemac.metamac.statistical.operations.core.invocation.service;

import java.util.Map;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CategoryResourceInternal;

public interface SrmRestInternalService {

    public static final String BEAN_ID = "srmRestInternalService";

    public CategoryResourceInternal retrieveCategoryByCategoryElement(String categorySchemeUrn, String categoryElementCode) throws MetamacException;
    public Map<String, CategoryResourceInternal> retrieveDefaultCategoriesByCategoryElementCode(String categorySchemeUrn) throws MetamacException;
}

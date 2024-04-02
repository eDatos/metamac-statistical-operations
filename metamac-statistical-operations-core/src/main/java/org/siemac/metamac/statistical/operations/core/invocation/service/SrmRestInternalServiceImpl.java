package org.siemac.metamac.statistical.operations.core.invocation.service;

import java.util.HashMap;
import java.util.Map;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Categories;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CategoryResourceInternal;
import org.siemac.metamac.statistical.operations.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.operations.core.error.ServiceExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component(SrmRestInternalService.BEAN_ID)
public class SrmRestInternalServiceImpl implements SrmRestInternalService {

    @Autowired
    @Qualifier("metamacApisLocatorCore")
    private MetamacApisLocator restApiLocator;

    // -------------------------------------------------------------------------------------------------
    // CATEGORIES
    // -------------------------------------------------------------------------------------------------

    @Override
    public CategoryResourceInternal retrieveCategoryByCategoryElement(String categorySchemeUrn, String categoryElementCode) throws MetamacException {
        try {
            String fields = "+categoryElement";

            String[] params = UrnUtils.splitUrnItemScheme(categorySchemeUrn);
            String agencyId = params[0];
            String resourceId = params[1];
            String version = params[2];

            Categories categories = restApiLocator.getSrmRestInternalFacadeV10().findCategories(agencyId, resourceId, version, null, null, null, null, fields);
            if (categories.getCategories() != null && !categories.getCategories().isEmpty()) {
                for (CategoryResourceInternal category : categories.getCategories()) {
                    if (category.getCategoryElement() != null && category.getCategoryElement().getId().equals(categoryElementCode)) {
                        return category;
                    }
                }
            }

            return null;
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    private MetamacException manageSrmInternalRestException(Exception e) throws MetamacException {
        return ServiceExceptionUtils.manageMetamacRestException(e, ServiceExceptionParameters.API_SRM_INTERNAL, restApiLocator.getSrmRestInternalFacadeV10());
    }

    @Override
    public Map<String, CategoryResourceInternal> retrieveDefaultCategoriesByCategoryElementCode(String categorySchemeUrn) throws MetamacException {
        try {
            String fields = "+categoryElement";
            Map<String, CategoryResourceInternal> categoriesByCategoryElementCode = new HashMap<String, CategoryResourceInternal>();

            String[] params = UrnUtils.splitUrnItemScheme(categorySchemeUrn);
            String agencyId = params[0];
            String resourceId = params[1];
            String version = params[2];

            Categories categories = restApiLocator.getSrmRestInternalFacadeV10().findCategories(agencyId, resourceId, version, null, null, null, null, fields);

            if (categories != null) {
                for (CategoryResourceInternal category : categories.getCategories()) {
                    if (category.getCategoryElement() != null) {
                        categoriesByCategoryElementCode.put(category.getCategoryElement().getId(), category);
                    }
                }
            }
            return categoriesByCategoryElementCode;

        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

}

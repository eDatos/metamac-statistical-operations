package org.siemac.metamac.statistical_operations.rest.internal.invocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.jaxrs.client.WebClient;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.rest.api.constants.RestApiConstants;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Categories;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Category;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CategoryResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("srmRestInternalFacade")
public class SrmRestInternalFacadeImpl implements SrmRestInternalFacade {

    private final Logger       logger = LoggerFactory.getLogger(SrmRestInternalFacadeImpl.class);

    @Autowired
    private MetamacApisLocator restApiLocator;

    @Override
    public List<ConceptResourceInternal> retrieveConceptsByConceptScheme(String urn) {
        try {
            String[] urnSplited = UrnUtils.splitUrnItemScheme(urn);
            String agencyID = urnSplited[0];
            String resourceID = urnSplited[1];
            String version = urnSplited[2];
            Concepts concepts = restApiLocator.getSrmRestInternalFacadeV10().findConcepts(agencyID, resourceID, version, null, null, null, null, null);
            return concepts.getConcepts();
        } catch (Exception e) {
            throw toRestException(e);
        }
    }

    @Override
    public Map<String, CategoryResourceInternal> retrieveDefaultCategoriesByCategoryElementCode(String categorySchemeUrn) {
        try {
            String fields = "+categoryElement";
            Map<String, CategoryResourceInternal> categoriesByCategoryElementCode = new HashMap<String, CategoryResourceInternal>();

            String[] params = UrnUtils.splitUrnItemScheme(categorySchemeUrn);
            String agencyId = params[0];
            String resourceId = params[1];
            String version = params[2];
            String query = "CATEGORY_ELEMENT_CODE IS_NOT_NULL";

            Categories categories = restApiLocator.getSrmRestInternalFacadeV10().findCategories(agencyId, resourceId, version, query, null, RestApiConstants.MAXIMUM_LIMIT.toString(), null, fields);

            if (categories != null) {
                for (CategoryResourceInternal category : categories.getCategories()) {
                    if (category.getCategoryElement() != null) {
                        categoriesByCategoryElementCode.put(category.getCategoryElement().getId(), category);
                    }
                }
            }
            return categoriesByCategoryElementCode;

        } catch (Exception e) {
            throw toRestException(e);
        }
    }

    @Override
    public CategoryResourceInternal retrieveCategoryByCategoryElement(String categorySchemeUrn, String categoryElementCode) {
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
            throw toRestException(e);
        }
    }

    @Override
    public Category retrieveCategoryByUrn(String categoryUrn) {
        try {

            String[] params = UrnUtils.splitUrnItem(categoryUrn);
            String agencyId = params[0];
            String resourceId = params[1];
            String version = params[2];
            String categoryId = params[3];

            return restApiLocator.getSrmRestInternalFacadeV10().retrieveCategory(agencyId, resourceId, version, categoryId);

        } catch (Exception e) {
            throw toRestException(e);
        }
    }

    private RestException toRestException(Exception e) {
        logger.error("Error", e);
        return RestExceptionUtils.toRestException(e, WebClient.client(restApiLocator.getSrmRestInternalFacadeV10()));
    }
}

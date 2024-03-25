package org.siemac.metamac.statistical_operations.rest.external.invocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.jaxrs.client.WebClient;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.rest.structural_resources.v1_0.domain.Categories;
import org.siemac.metamac.rest.structural_resources.v1_0.domain.Category;
import org.siemac.metamac.rest.structural_resources.v1_0.domain.CategoryResource;
import org.siemac.metamac.rest.structural_resources.v1_0.domain.ConceptResource;
import org.siemac.metamac.rest.structural_resources.v1_0.domain.Concepts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("srmRestInternalFacade")
public class SrmRestExternalFacadeImpl implements SrmRestExternalFacade {

    private final Logger       logger = LoggerFactory.getLogger(SrmRestExternalFacadeImpl.class);

    @Autowired
    private MetamacApisLocator restApiLocator;

    @Override
    public List<ConceptResource> retrieveConceptsByConceptScheme(String urn) {
        try {
            String[] urnSplited = UrnUtils.splitUrnItemScheme(urn);
            String agencyID = urnSplited[0];
            String resourceID = urnSplited[1];
            String version = urnSplited[2];
            Concepts concepts = restApiLocator.getSrmRestExternalFacadeV10().findConcepts(agencyID, resourceID, version, null, null, null, null, null);
            return concepts.getConcepts();
        } catch (Exception e) {
            throw toRestException(e);
        }
    }

    @Override
    public Map<String, CategoryResource> retrieveDefaultCategoriesByCategoryElementCode(String categorySchemeUrn) {
        try {
            String fields = "+categoryElement";
            Map<String, CategoryResource> categoriesByCategoryElementCode = new HashMap<String, CategoryResource>();

            String[] params = UrnUtils.splitUrnItemScheme(categorySchemeUrn);
            String agencyId = params[0];
            String resourceId = params[1];
            String version = params[2];

            Categories categories = restApiLocator.getSrmRestExternalFacadeV10().findCategories(agencyId, resourceId, version, null, null, null, null, fields);

            if (categories != null) {
                for (CategoryResource category : categories.getCategories()) {
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
    public CategoryResource retrieveCategoryByCategoryElement(String categorySchemeUrn, String categoryElementCode) {
        try {
            String fields = "+categoryElement";

            String[] params = UrnUtils.splitUrnItemScheme(categorySchemeUrn);
            String agencyId = params[0];
            String resourceId = params[1];
            String version = params[2];

            Categories categories = restApiLocator.getSrmRestExternalFacadeV10().findCategories(agencyId, resourceId, version, null, null, null, null, fields);
            if (categories.getCategories() != null && !categories.getCategories().isEmpty()) {
                for (CategoryResource category : categories.getCategories()) {
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
            String fields = "+categoryElement";

            String[] params = UrnUtils.splitUrnItem(categoryUrn);
            String agencyId = params[0];
            String resourceId = params[1];
            String version = params[2];
            String categoryId = params[3];

            return restApiLocator.getSrmRestExternalFacadeV10().retrieveCategory(agencyId, resourceId, version, categoryId, fields);

        } catch (Exception e) {
            throw toRestException(e);
        }
    }

    private RestException toRestException(Exception e) {
        logger.error("Error", e);
        return RestExceptionUtils.toRestException(e, WebClient.client(restApiLocator.getSrmRestExternalFacadeV10()));
    }

}

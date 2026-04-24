package org.siemac.metamac.statistical_operations.rest.internal.invocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.jaxrs.client.WebClient;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria.Operator;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.rest.api.constants.RestApiConstants;
import org.siemac.metamac.rest.common.query.domain.OperationTypeEnum;
import org.siemac.metamac.rest.common.v1_0.domain.ComparisonOperator;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Categories;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Category;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CategoryCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CategoryResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Organisation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("srmRestInternalFacade")
public class SrmRestInternalFacadeImpl implements SrmRestInternalFacade {

    private final Logger       logger       = LoggerFactory.getLogger(SrmRestInternalFacadeImpl.class);
    public static final String DOUBLE_QUOTE = "\"";

    @Autowired
    private MetamacApisLocator restApiLocator;

    @Override
    public List<ConceptResourceInternal> retrieveConceptsByConceptScheme(String urn) {
        try {
            String[] urnSplited = UrnUtils.splitUrnItemScheme(urn);
            String agencyID = urnSplited[0];
            String resourceID = urnSplited[1];
            String version = urnSplited[2];
            Concepts concepts = restApiLocator.getSrmRestInternalFacadeV10().findConcepts(agencyID, resourceID, version, null, null, null, null, null, null);
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

    /*
     * this function allows searches like this:
     * https://estadisticas.arte-consultores.com/structural-resources/v1.0/categoryschemes/ISTAC/TEMAS_CANARIAS/01.001/categories?query=URN like
     * "urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:TEMAS_CANARIAS(01.001).80%" and category_element_urn is not null
     * where the category scheme is the default category scheme in the "metamac.srm.default.category_scheme.urn" property of common-metadata.
     * and
     * https://estadisticas.arte-consultores.com/structural-resources/v1.0/categoryschemes/ISTAC/TEMAS_CANARIAS/01.001/categories?query=URN IN
     * ("urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:TEMAS_CANARIAS(01.001).80%",
     * "urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:TEMAS_CANARIAS(01.001).30%") and category_element_urn is not null
     * where the category scheme is the default category scheme in the "metamac.srm.default.category_scheme.urn" property of common-metadata.
     */
    @Override
    public Categories retrieveCategoriesByUrn(String categorySchemeUrn, List<String> urnCategories, OperationTypeEnum operationType) {
        try {

            if (urnCategories == null || urnCategories.isEmpty()) {
                return null;
            }

            String fields = "+categoryElement";

            String[] params = UrnUtils.splitUrnItemScheme(categorySchemeUrn);
            String agencyId = params[0];
            String resourceId = params[1];
            String version = params[2];

            String query = buildQueryCategories(urnCategories, operationType);

            return restApiLocator.getSrmRestInternalFacadeV10().findCategories(agencyId, resourceId, version, query, null, RestApiConstants.MAXIMUM_LIMIT.toString(), null, fields);

        } catch (Exception e) {
            throw toRestException(e);
        }
    }

    // build queries for LIKE, ILIKE AND IN
    private String buildQueryCategories(List<String> urnCategories, OperationTypeEnum operationType) {
        StringBuilder queryBuilder = new StringBuilder(CategoryCriteriaPropertyRestriction.URN.value());

        if (OperationTypeEnum.IN.equals(operationType)) {
            queryBuilder.append(RestApiConstants.BLANK).append(operationType).append(RestApiConstants.BLANK).append(RestApiConstants.LEFT_PARENTHESIS);
            for (int i = 0; i < urnCategories.size(); i++) {
                queryBuilder.append(DOUBLE_QUOTE).append(urnCategories.get(i)).append(DOUBLE_QUOTE);

                if (urnCategories.size() > 1 && i < urnCategories.size() - 1) {
                    queryBuilder.append(RestApiConstants.COMMA);
                }
            }

            queryBuilder.append(RestApiConstants.RIGHT_PARENTHESIS);

        } else { // LIKE, ILIKE
            queryBuilder.append(RestApiConstants.BLANK).append(operationType).append(RestApiConstants.BLANK).append(DOUBLE_QUOTE).append(urnCategories.get(0)).append(DOUBLE_QUOTE);
        }

        queryBuilder.append(RestApiConstants.BLANK).append(Operator.And).append(RestApiConstants.BLANK).append(CategoryCriteriaPropertyRestriction.CATEGORY_ELEMENT_CODE).append(RestApiConstants.BLANK)
                .append(ComparisonOperator.IS_NOT_NULL);

        return queryBuilder.toString();
    }
    @Override
    public Organisation retrieveOrganisation(String urn) {
        try {
            String[] urnSplited = UrnUtils.splitUrnItem(urn);
            String agencyID = urnSplited[0];
            String resourceID = urnSplited[1];
            String version = urnSplited[2];
            String organisationID = urnSplited[3];
            return restApiLocator.getSrmRestInternalFacadeV10().retrieveOrganisation(agencyID, resourceID, version, organisationID);
        } catch (Exception e) {
            throw toRestException(e);
        }
    }

    private RestException toRestException(Exception e) {
        logger.error("Error", e);
        return RestExceptionUtils.toRestException(e, WebClient.client(restApiLocator.getSrmRestInternalFacadeV10()));
    }

}

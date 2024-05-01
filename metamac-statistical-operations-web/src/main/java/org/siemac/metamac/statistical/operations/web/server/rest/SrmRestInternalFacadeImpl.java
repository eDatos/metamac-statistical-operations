package org.siemac.metamac.statistical.operations.web.server.rest;

import static org.siemac.metamac.rest.api.constants.RestApiConstants.WILDCARD_ALL;
import static org.siemac.metamac.rest.api.utils.RestCriteriaUtils.appendConditionToQuery;
import static org.siemac.metamac.rest.api.utils.RestCriteriaUtils.fieldComparison;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.ServerWebApplicationException;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.rest.common.v1_0.domain.ComparisonOperator;
import org.siemac.metamac.rest.common.v1_0.domain.LogicalOperator;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Categories;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CategoryCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CategoryResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CategorySchemes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codelists;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptSchemes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataProviderSchemes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataProviders;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.OrganisationSchemes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.OrganisationUnitSchemes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.OrganisationUnits;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Organisations;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.statistical.operations.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.operations.web.server.rest.utils.ExternalItemUtils;
import org.siemac.metamac.statistical.operations.web.server.rest.utils.RestQueryUtils;
import org.siemac.metamac.statistical.operations.web.shared.external.ConceptRestCriteria;
import org.siemac.metamac.statistical.operations.web.shared.external.ConceptSchemeRestCriteria;
import org.siemac.metamac.statistical.operations.web.shared.external.OrganisationRestCriteria;
import org.siemac.metamac.statistical.operations.web.shared.external.OrganisationSchemeRestCriteria;
import org.siemac.metamac.web.common.server.rest.utils.RestExceptionUtils;
import org.siemac.metamac.web.common.shared.criteria.ExternalResourceWebCriteria;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.base.HasSimpleCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SrmRestInternalFacadeImpl implements SrmRestInternalFacade {

    @Autowired
    private RestApiLocator     restApiLocator;

    @Autowired
    private RestExceptionUtils restExceptionUtils;

    //
    // CATEGORY SCHEMES
    //

    @Override
    public ExternalItemsResult findCategorySchemes(ServiceContext serviceContext, SrmExternalResourceRestCriteria criteria, int firstResult, int maxResults) throws MetamacWebException {

        String limit = String.valueOf(maxResults);
        String offset = String.valueOf(firstResult);
        String orderBy = null;
        String query = RestQueryUtils.buildCategorySchemeQuery(criteria);

        try {
            CategorySchemes categorySchemes = restApiLocator.getSrmRestInternalFacadeV10().findCategorySchemes(query, orderBy, limit, offset);
            return ExternalItemUtils.getCategorySchemesAsExternalItemsResult(categorySchemes);
        } catch (Exception e) {
            throw manageSrmInternalRestException(serviceContext, e);
        }
    }

    //
    // CATEGORIES
    //

    @Override
    public ExternalItemsResult findCategories(ServiceContext serviceContext, SrmItemRestCriteria itemWebCriteria, int firstResult, int maxResults) throws MetamacWebException {

        String limit = String.valueOf(maxResults);
        String offset = String.valueOf(firstResult);
        String orderBy = null;
        String query = RestQueryUtils.buildCategoryQuery(itemWebCriteria);

        try {
            Categories categories = restApiLocator.getSrmRestInternalFacadeV10().findCategories(WILDCARD_ALL, WILDCARD_ALL, WILDCARD_ALL, query, orderBy, limit, offset, null);
            return ExternalItemUtils.getCategoriesAsExternalItemsResult(categories);
        } catch (Exception e) {
            throw manageSrmInternalRestException(serviceContext, e);
        }
    }

    //
    // CODELISTS
    //

    @Override
    public ExternalItemsResult findCodelists(ServiceContext serviceContext, SrmExternalResourceRestCriteria criteria, int firstResult, int maxResults) throws MetamacWebException {

        String limit = String.valueOf(maxResults);
        String offset = String.valueOf(firstResult);
        String orderBy = null;
        String query = RestQueryUtils.buildCodelistQuery(criteria);

        try {
            Codelists codelists = restApiLocator.getSrmRestInternalFacadeV10().findCodelists(query, orderBy, limit, offset);
            return ExternalItemUtils.getCodelistsAsExternalItemsResult(codelists);
        } catch (Exception e) {
            throw manageSrmInternalRestException(serviceContext, e);
        }
    }

    //
    // CODES
    //

    @Override
    public ExternalItemsResult findCodes(ServiceContext serviceContext, SrmItemRestCriteria itemWebCriteria, int firstResult, int maxResults) throws MetamacWebException {

        String limit = String.valueOf(maxResults);
        String offset = String.valueOf(firstResult);
        String orderBy = null;
        String query = RestQueryUtils.buildCodeQuery(itemWebCriteria);
        String fields = null;

        try {
            Codes codes = restApiLocator.getSrmRestInternalFacadeV10().findCodes(WILDCARD_ALL, WILDCARD_ALL, WILDCARD_ALL, query, orderBy, limit, offset, null, null, null, null, fields);
            return ExternalItemUtils.getCodesAsExternalItemsResult(codes);
        } catch (Exception e) {
            throw manageSrmInternalRestException(serviceContext, e);
        }
    }

    //
    // CONCEPT SCHEMES
    //

    @Override
    public ExternalItemsResult findConceptSchemes(ServiceContext serviceContext, ConceptSchemeRestCriteria criteria, int firstResult, int maxResults) throws MetamacWebException {

        String limit = String.valueOf(maxResults);
        String offset = String.valueOf(firstResult);
        String orderBy = null;
        String query = RestQueryUtils.buildConceptSchemeQuery(criteria.getCriteria(), criteria.getConceptSchemeTypes(), criteria.getStatisticalOperationUrn());

        try {
            ConceptSchemes conceptSchemes = restApiLocator.getSrmRestInternalFacadeV10().findConceptSchemes(query, orderBy, limit, offset);
            return ExternalItemUtils.getConceptSchemesAsExternalItemsResult(conceptSchemes);
        } catch (Exception e) {
            throw manageSrmInternalRestException(serviceContext, e);
        }
    }

    //
    // CONCEPTS
    //

    @Override
    public ExternalItemsResult findConcepts(ServiceContext serviceContext, ConceptRestCriteria criteria, int firstResult, int maxResults) throws MetamacWebException {

        String limit = String.valueOf(maxResults);
        String offset = String.valueOf(firstResult);
        String orderBy = null;
        String query = RestQueryUtils.buildConceptQuery(criteria.getCriteria(), criteria.getConceptSchemeTypes(), criteria.getStatisticalOperationUrn());

        try {
            Concepts concepts = restApiLocator.getSrmRestInternalFacadeV10().findConcepts(WILDCARD_ALL, WILDCARD_ALL, WILDCARD_ALL, query, orderBy, limit, offset, null);
            return ExternalItemUtils.getConceptsAsExternalItemsResult(concepts);
        } catch (Exception e) {
            throw manageSrmInternalRestException(serviceContext, e);
        }
    }

    //
    // ORGANISATION UNIT SCHEMES
    //

    @Override
    public ExternalItemsResult findOrganisationUnitSchemes(ServiceContext serviceContext, SrmExternalResourceRestCriteria criteria, int firstResult, int maxResults) throws MetamacWebException {

        String limit = String.valueOf(maxResults);
        String offset = String.valueOf(firstResult);
        String orderBy = null;
        String query = RestQueryUtils.buildOrganisationSchemeQuery(criteria);

        try {
            OrganisationUnitSchemes organisationUnitSchemes = restApiLocator.getSrmRestInternalFacadeV10().findOrganisationUnitSchemes(query, orderBy, limit, offset);
            return ExternalItemUtils.getOrganisationUnitSchemesAsExternalItemsResult(organisationUnitSchemes);
        } catch (Exception e) {
            throw manageSrmInternalRestException(serviceContext, e);
        }
    }

    //
    // ORGANISATION UNITS
    //

    @Override
    public ExternalItemsResult findOrganisationUnits(ServiceContext serviceContext, SrmItemRestCriteria itemWebCriteria, int firstResult, int maxResults) throws MetamacWebException {

        String limit = String.valueOf(maxResults);
        String offset = String.valueOf(firstResult);
        String orderBy = null;
        String query = RestQueryUtils.buildOrganisationQuery(itemWebCriteria);

        try {
            OrganisationUnits organisationUnits = restApiLocator.getSrmRestInternalFacadeV10().findOrganisationUnits(WILDCARD_ALL, WILDCARD_ALL, WILDCARD_ALL, query, orderBy, limit, offset, null);
            return ExternalItemUtils.getOrganisationUnitsAsExternalItemsResult(organisationUnits);
        } catch (Exception e) {
            throw manageSrmInternalRestException(serviceContext, e);
        }
    }

    //
    // DATA PROVIDER SCHEMES
    //

    @Override
    public ExternalItemsResult findDataProviderSchemes(ServiceContext serviceContext, SrmExternalResourceRestCriteria criteria, int firstResult, int maxResults) throws MetamacWebException {

        String limit = String.valueOf(maxResults);
        String offset = String.valueOf(firstResult);
        String orderBy = null;
        String query = RestQueryUtils.buildOrganisationSchemeQuery(criteria);

        try {
            DataProviderSchemes dataProviderSchemes = restApiLocator.getSrmRestInternalFacadeV10().findDataProviderSchemes(query, orderBy, limit, offset);
            return ExternalItemUtils.getDataProviderSchemesAsExternalItemsResult(dataProviderSchemes);
        } catch (Exception e) {
            throw manageSrmInternalRestException(serviceContext, e);
        }
    }

    //
    // DATA PROVIDERS
    //

    @Override
    public ExternalItemsResult findDataProviders(ServiceContext serviceContext, SrmItemRestCriteria itemWebCriteria, int firstResult, int maxResults) throws MetamacWebException {

        String limit = String.valueOf(maxResults);
        String offset = String.valueOf(firstResult);
        String orderBy = null;
        String query = RestQueryUtils.buildOrganisationQuery(itemWebCriteria);

        try {

            DataProviders dataProviders = restApiLocator.getSrmRestInternalFacadeV10().findDataProviders(WILDCARD_ALL, WILDCARD_ALL, WILDCARD_ALL, query, orderBy, limit, offset, null);
            return ExternalItemUtils.getDataProvidersAsExternalItemsResult(dataProviders);
        } catch (Exception e) {
            throw manageSrmInternalRestException(serviceContext, e);
        }
    }

    //
    // ORGANISATION SCHEMES
    //

    @Override
    public ExternalItemsResult findOrganisationSchemes(ServiceContext serviceContext, OrganisationSchemeRestCriteria criteria, int firstResult, int maxResults) throws MetamacWebException {

        String limit = String.valueOf(maxResults);
        String offset = String.valueOf(firstResult);
        String orderBy = null;
        String query = RestQueryUtils.buildOrganisationSchemeQuery(criteria);

        try {
            OrganisationSchemes organisationSchemes = restApiLocator.getSrmRestInternalFacadeV10().findOrganisationSchemes(query, orderBy, limit, offset);
            return ExternalItemUtils.getOrganisationSchemesAsExternalItemsResult(organisationSchemes);
        } catch (Exception e) {
            throw manageSrmInternalRestException(serviceContext, e);
        }
    }

    //
    // ORGANISATIONS
    //

    @Override
    public ExternalItemsResult findOrganisations(ServiceContext serviceContext, OrganisationRestCriteria itemWebCriteria, int firstResult, int maxResults) throws MetamacWebException {

        String limit = String.valueOf(maxResults);
        String offset = String.valueOf(firstResult);
        String orderBy = null;
        String query = RestQueryUtils.buildOrganisationQuery(itemWebCriteria);

        try {

            Organisations organisations = restApiLocator.getSrmRestInternalFacadeV10().findOrganisations(WILDCARD_ALL, WILDCARD_ALL, WILDCARD_ALL, query, orderBy, limit, offset, null);
            return ExternalItemUtils.getOrganisationsAsExternalItemsResult(organisations);
        } catch (ServerWebApplicationException e) {
            throw manageSrmInternalRestException(serviceContext, e);
        }
    }

    private Categories retrieveCategoriesByCategoryScheme(ServiceContext serviceContext, String categorySchemeUrn, ExternalResourceWebCriteria condition, int firstResult, int maxResults)
            throws MetamacWebException {
        String limit = String.valueOf(maxResults);
        String offset = String.valueOf(firstResult);
        String orderBy = null;
        String query = null;
        if (condition != null) {
            query = buildCategoryQueryCode(condition);
        }

        try {
            String fields = "+categoryElement";

            String[] params = UrnUtils.splitUrnItemScheme(categorySchemeUrn);
            String agencyId = params[0];
            String resourceId = params[1];
            String version = params[2];

            return restApiLocator.getSrmRestInternalFacadeV10().findCategories(agencyId, resourceId, version, query, orderBy, limit, offset, fields);
        } catch (ServerWebApplicationException e) {
            throw manageSrmInternalRestException(serviceContext, e);
        }
    }

    @Override
    public ExternalItemsResult retrieveCategoryElementsByCategoryScheme(ServiceContext serviceContext, String categorySchemeUrn, ExternalResourceWebCriteria condition, int firstResult, int maxResults)
            throws MetamacWebException {

        Categories categories = retrieveCategoriesByCategoryScheme(serviceContext, categorySchemeUrn, condition, firstResult, maxResults);

        try {
            List<ResourceInternal> categoryElements = new ArrayList<ResourceInternal>();

            for (CategoryResourceInternal category : categories.getCategories()) {
                if (category.getCategoryElement() != null) {
                    categoryElements.add(category.getCategoryElement());
                }
            }

            ExternalItemsResult result = ExternalItemUtils.getCategoryElementsAsExternalItemsResult(categoryElements);
            result.setFirstResult(categories.getOffset() != null ? categories.getOffset().intValue() : 0);
            result.setTotalResults(categories.getOffset() != null ? categories.getTotal().intValue() : 0);

            return result;

        } catch (Exception e) {
            throw manageSrmInternalRestException(serviceContext, e);
        }
    }

    public static String buildCategoryQueryCode(MetamacWebCriteria webCriteria) {
        StringBuilder queryBuilder = new StringBuilder();
        if (webCriteria != null) {
            addSimpleRestCriteria(queryBuilder, webCriteria, CategoryCriteriaPropertyRestriction.CATEGORY_ELEMENT_SHORT_NAME, CategoryCriteriaPropertyRestriction.CATEGORY_ELEMENT_CODE,
                    CategoryCriteriaPropertyRestriction.CATEGORY_ELEMENT_URN);
        }
        appendConditionToQuery(queryBuilder, "CATEGORY_ELEMENT_CODE IS_NOT_NULL");
        return queryBuilder.toString();
    }

    @SuppressWarnings("rawtypes")
    private static void addSimpleRestCriteria(StringBuilder queryBuilder, HasSimpleCriteria criteria, Enum... fields) {
        String simpleCriteria = criteria.getCriteria();
        if (StringUtils.isNotBlank(simpleCriteria)) {
            StringBuilder conditionBuilder = new StringBuilder();

            List<String> conditions = new ArrayList<String>();
            for (Enum field : fields) {
                conditions.add(fieldComparison(field, ComparisonOperator.ILIKE, simpleCriteria));
            }

            conditionBuilder.append("(");
            for (int i = 0; i < conditions.size(); i++) {
                if (i > 0) {
                    conditionBuilder.append(" ").append(LogicalOperator.OR).append(" ");
                }
                conditionBuilder.append(conditions.get(i));
            }
            conditionBuilder.append(")");
            appendConditionToQuery(queryBuilder, conditionBuilder.toString());
        }
    }

    //
    // EXCEPTION HANDLERS
    //

    private MetamacWebException manageSrmInternalRestException(ServiceContext ctx, Exception e) throws MetamacWebException {
        return restExceptionUtils.manageMetamacRestException(ctx, e, ServiceExceptionParameters.API_SRM_INTERNAL, restApiLocator.getSrmRestInternalFacadeV10());
    }
}

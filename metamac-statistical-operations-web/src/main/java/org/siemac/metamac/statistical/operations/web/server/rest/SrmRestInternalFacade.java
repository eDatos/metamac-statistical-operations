package org.siemac.metamac.statistical.operations.web.server.rest;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.statistical.operations.web.shared.external.ConceptRestCriteria;
import org.siemac.metamac.statistical.operations.web.shared.external.ConceptSchemeRestCriteria;
import org.siemac.metamac.statistical.operations.web.shared.external.OrganisationRestCriteria;
import org.siemac.metamac.statistical.operations.web.shared.external.OrganisationSchemeRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.ExternalResourceWebCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;

public interface SrmRestInternalFacade {

    ExternalItemsResult findCategorySchemes(ServiceContext serviceContext, SrmExternalResourceRestCriteria criteria, int firstResult, int maxResults) throws MetamacWebException;
    ExternalItemsResult findCategories(ServiceContext serviceContext, SrmItemRestCriteria itemWebCriteria, int firstResult, int maxResults) throws MetamacWebException;

    ExternalItemsResult findCodelists(ServiceContext serviceContext, SrmExternalResourceRestCriteria criteria, int firstResult, int maxResults) throws MetamacWebException;
    ExternalItemsResult findCodes(ServiceContext serviceContext, SrmItemRestCriteria itemWebCriteria, int firstResult, int maxResults) throws MetamacWebException;

    ExternalItemsResult findConceptSchemes(ServiceContext serviceContext, ConceptSchemeRestCriteria criteria, int firstResult, int maxResults) throws MetamacWebException;
    ExternalItemsResult findConcepts(ServiceContext serviceContext, ConceptRestCriteria criteria, int firstResult, int maxResults) throws MetamacWebException;

    ExternalItemsResult findOrganisationUnitSchemes(ServiceContext serviceContext, SrmExternalResourceRestCriteria criteria, int firstResult, int maxResults) throws MetamacWebException;
    ExternalItemsResult findOrganisationUnits(ServiceContext serviceContext, SrmItemRestCriteria itemWebCriteria, int firstResult, int maxResults) throws MetamacWebException;

    ExternalItemsResult findDataProviderSchemes(ServiceContext serviceContext, SrmExternalResourceRestCriteria criteria, int firstResult, int maxResults) throws MetamacWebException;
    ExternalItemsResult findDataProviders(ServiceContext serviceContext, SrmItemRestCriteria itemWebCriteria, int firstResult, int maxResults) throws MetamacWebException;

    ExternalItemsResult findOrganisationSchemes(ServiceContext serviceContext, OrganisationSchemeRestCriteria criteria, int firstResult, int maxResults) throws MetamacWebException;
    ExternalItemsResult findOrganisations(ServiceContext serviceContext, OrganisationRestCriteria itemWebCriteria, int firstResult, int maxResults) throws MetamacWebException;

    ExternalItemsResult retrieveCategoryElementsByCategoryScheme(ServiceContext serviceContext, String categorySchemeUrn, ExternalResourceWebCriteria condition, int firstResult, int maxResults)
            throws MetamacWebException;
}

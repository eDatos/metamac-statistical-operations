package org.siemac.metamac.statistical.operations.web.client.instance.view.handlers;

import org.siemac.metamac.statistical.operations.core.dto.InstanceDto;
import org.siemac.metamac.statistical.operations.web.shared.external.ConceptSchemeTypeEnum;
import org.siemac.metamac.web.common.client.view.handlers.SrmExternalResourcesUiHandlers;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;

public interface InstanceUiHandlers extends SrmExternalResourcesUiHandlers {

    void saveInstance(InstanceDto instanceDto);
    void deleteInstance(InstanceDto instanceDto);
    void retrieveConceptSchemes(String formItemName, SrmExternalResourceRestCriteria srmItemSchemeRestCriteria, int firstResult, int maxResults, ConceptSchemeTypeEnum[] types);
    void retrieveConcepts(String formItemName, SrmItemRestCriteria itemWebCriteria, int firstResult, int maxResults, ConceptSchemeTypeEnum[] types);

    void retrieveCodelists(String formItemName, SrmExternalResourceRestCriteria srmItemSchemeRestCriteria, int firstResult, int maxResults);
    void retrieveCodes(String formItemName, SrmItemRestCriteria itemWebCriteria, int firstResult, int maxResults);
}

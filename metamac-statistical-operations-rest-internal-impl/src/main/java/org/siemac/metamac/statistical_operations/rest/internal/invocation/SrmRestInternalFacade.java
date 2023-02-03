package org.siemac.metamac.statistical_operations.rest.internal.invocation;

import java.util.List;

public interface SrmRestInternalFacade {

    public List<org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptResourceInternal> retrieveConceptsByConceptScheme(String urn);
}

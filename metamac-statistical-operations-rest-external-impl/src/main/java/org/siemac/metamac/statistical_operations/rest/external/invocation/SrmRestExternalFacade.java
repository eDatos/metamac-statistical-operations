package org.siemac.metamac.statistical_operations.rest.external.invocation;

import java.util.List;

public interface SrmRestExternalFacade {

    public List<org.siemac.metamac.rest.structural_resources.v1_0.domain.ConceptResource> retrieveConceptsByConceptScheme(String urn);
}

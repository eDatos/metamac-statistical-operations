package org.siemac.metamac.statistical.operations.core.invocation.service;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.siemac.edatos.core.common.constants.CoreCommonConstants;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.srm.rest.internal.v1_0.service.SrmRestInternalFacadeV10;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("metamacApisLocatorCore")
public class MetamacApisLocator {

    @Autowired
    private ConfigurationService     configurationService;

    private SrmRestInternalFacadeV10 srmRestInternalFacadeV10 = null;

    public SrmRestInternalFacadeV10 getSrmRestInternalFacadeV10() throws MetamacException {
        if (srmRestInternalFacadeV10 == null) {
            String baseApi = configurationService.retrieveSrmInternalApiUrlBase();
            srmRestInternalFacadeV10 = JAXRSClientFactory.create(baseApi, SrmRestInternalFacadeV10.class, null, true); // true to do thread safe
        }
        String apiKey = configurationService.retrieveStatisticalOperationsApiKey();
        // reset thread context
        WebClient.client(srmRestInternalFacadeV10).reset();
        WebClient.client(srmRestInternalFacadeV10).accept("application/xml").header(CoreCommonConstants.API_KEY_PARAMETER, apiKey);

        return srmRestInternalFacadeV10;
    }
}

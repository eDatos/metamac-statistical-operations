package org.siemac.metamac.statistical_operations.rest.internal.invocation;

import javax.annotation.PostConstruct;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.siemac.metamac.access_control.rest.internal.v1_0.service.AccessControlRestInternalFacadeV1_0;
import org.siemac.metamac.common_metadata.rest.external.v1_0.service.CommonMetadataV1_0;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.srm.rest.internal.v1_0.service.SrmRestInternalFacadeV10;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MetamacApisLocator {

    @Autowired
    private ConfigurationService                configurationService;

    private CommonMetadataV1_0                  commonMetadataRestExternalFacadeV10 = null;
    private SrmRestInternalFacadeV10            srmRestInternalFacadeV10            = null;

    private AccessControlRestInternalFacadeV1_0 accessControlRestInternalFacadeV1_0 = null;

    @PostConstruct
    public void initService() throws Exception {
        String commonMetadataExternalApi = configurationService.retrieveCommonMetadataExternalApiUrlBase();
        commonMetadataRestExternalFacadeV10 = JAXRSClientFactory.create(commonMetadataExternalApi, CommonMetadataV1_0.class, null, true); // true to do thread safe

        String srmInternalApi = configurationService.retrieveSrmInternalApiUrlBase();
        srmRestInternalFacadeV10 = JAXRSClientFactory.create(srmInternalApi, SrmRestInternalFacadeV10.class, null, true); // true to do thread safe

        String accessControlInternalApi = configurationService.retrieveAccessControlInternalApiUrlBase();
        accessControlRestInternalFacadeV1_0 = JAXRSClientFactory.create(accessControlInternalApi, AccessControlRestInternalFacadeV1_0.class, null, true); // true to do thread safe
    }

    public CommonMetadataV1_0 getCommonMetadataRestExternalFacadeV10() {
        // reset thread context
        WebClient.client(commonMetadataRestExternalFacadeV10).reset();
        WebClient.client(commonMetadataRestExternalFacadeV10).accept("application/xml");

        return commonMetadataRestExternalFacadeV10;
    }

    public SrmRestInternalFacadeV10 getSrmRestInternalFacadeV10() {
        // reset thread context
        WebClient.client(srmRestInternalFacadeV10).reset();
        WebClient.client(srmRestInternalFacadeV10).accept("application/xml");

        return srmRestInternalFacadeV10;
    }

    public AccessControlRestInternalFacadeV1_0 getAccessControlRestInternalFacadeV1_0() {
        // reset thread context
        WebClient.client(accessControlRestInternalFacadeV1_0).reset();
        WebClient.client(accessControlRestInternalFacadeV1_0).accept("application/xml");
        return accessControlRestInternalFacadeV1_0;
    }
}

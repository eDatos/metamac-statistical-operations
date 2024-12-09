package org.siemac.metamac.statistical.operations.web.server.rest;

import javax.annotation.PostConstruct;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.siemac.edatos.core.common.constants.CoreCommonConstants;
import org.siemac.metamac.access_control.rest.internal.v1_0.service.AccessControlRestInternalFacadeV1_0;
import org.siemac.metamac.common_metadata.rest.external.v1_0.service.CommonMetadataV1_0;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.notices.rest.internal.v1_0.service.NoticesV1_0;
import org.siemac.metamac.srm.rest.internal.v1_0.service.SrmRestInternalFacadeV10;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestApiLocator {

    @Autowired
    private ConfigurationService                configurationService;

    private CommonMetadataV1_0                  commonMetadataRestExternalFacadeV10 = null;
    private SrmRestInternalFacadeV10            srmRestInternalFacadeV10            = null;
    private NoticesV1_0                         noticesRestInternalFacadeV10        = null;

    private AccessControlRestInternalFacadeV1_0 accessControlRestInternalFacadeV1_0 = null;
    private String                              apiKey;

    @PostConstruct
    public void initService() throws Exception {
        apiKey = configurationService.retrieveStatisticalOperationsApiKey();

        String commonMetadataBaseApi = configurationService.retrieveCommonMetadataExternalApiUrlBase();
        commonMetadataRestExternalFacadeV10 = JAXRSClientFactory.create(commonMetadataBaseApi, CommonMetadataV1_0.class, null, true); // true to do thread safe

        String srmBaseApi = configurationService.retrieveSrmInternalApiUrlBase();
        srmRestInternalFacadeV10 = JAXRSClientFactory.create(srmBaseApi, SrmRestInternalFacadeV10.class, null, true);

        String noticesBaseApi = configurationService.retrieveNoticesInternalApiUrlBase();
        noticesRestInternalFacadeV10 = JAXRSClientFactory.create(noticesBaseApi, NoticesV1_0.class, null, true);

        String accessControlInternalApi = configurationService.retrieveAccessControlInternalApiUrlBase();
        accessControlRestInternalFacadeV1_0 = JAXRSClientFactory.create(accessControlInternalApi, AccessControlRestInternalFacadeV1_0.class, null, true); // true to do thread safe
    }

    public CommonMetadataV1_0 getCommonMetadataRestExternalFacadeV10() {
        // reset thread context
        WebClient.client(commonMetadataRestExternalFacadeV10).reset();
        WebClient.client(commonMetadataRestExternalFacadeV10).accept("application/xml").header(CoreCommonConstants.API_KEY_PARAMETER, apiKey);

        return commonMetadataRestExternalFacadeV10;
    }

    public SrmRestInternalFacadeV10 getSrmRestInternalFacadeV10() {
        // reset thread context
        WebClient.client(srmRestInternalFacadeV10).reset();
        WebClient.client(srmRestInternalFacadeV10).accept("application/xml").header(CoreCommonConstants.API_KEY_PARAMETER, apiKey);

        return srmRestInternalFacadeV10;
    }

    public NoticesV1_0 getNoticesRestInternalFacadeV10() {
        // reset thread context
        WebClient.client(noticesRestInternalFacadeV10).reset();
        WebClient.client(noticesRestInternalFacadeV10).accept("application/xml").header(CoreCommonConstants.API_KEY_PARAMETER, apiKey);

        return noticesRestInternalFacadeV10;
    }

    public AccessControlRestInternalFacadeV1_0 getAccessControlRestInternalFacadeV1_0() {
        // reset thread context
        WebClient.client(accessControlRestInternalFacadeV1_0).reset();
        WebClient.client(accessControlRestInternalFacadeV1_0).accept("application/xml").header(CoreCommonConstants.API_KEY_PARAMETER, apiKey);
        return accessControlRestInternalFacadeV1_0;
    }
}

package org.siemac.metamac.statistical_operations.rest.external.invocation;

import javax.annotation.PostConstruct;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.siemac.metamac.common_metadata.rest.external.v1_0.service.CommonMetadataV1_0;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.srm.rest.external.v1_0.service.SrmRestExternalFacadeV10;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MetamacApisLocator {

    @Autowired
    private ConfigurationService     configurationService;

    private CommonMetadataV1_0       commonMetadataRestExternalFacadeV10 = null;

    private SrmRestExternalFacadeV10 srmRestExternalFacadeV10            = null;

    @PostConstruct
    public void initService() throws Exception {
        String baseApi = configurationService.retrieveCommonMetadataExternalApiUrlBase();
        commonMetadataRestExternalFacadeV10 = JAXRSClientFactory.create(baseApi, CommonMetadataV1_0.class, null, true); // true to do thread safe
    }

    public CommonMetadataV1_0 getCommonMetadataRestExternalFacadeV10() {
        // reset thread context
        WebClient.client(commonMetadataRestExternalFacadeV10).reset();
        WebClient.client(commonMetadataRestExternalFacadeV10).accept("application/xml");

        return commonMetadataRestExternalFacadeV10;
    }

    public SrmRestExternalFacadeV10 getSrmRestExternalFacadeV10() {
        // reset thread context
        WebClient.client(srmRestExternalFacadeV10).reset();
        WebClient.client(srmRestExternalFacadeV10).accept("application/xml");

        return srmRestExternalFacadeV10;
    }
}

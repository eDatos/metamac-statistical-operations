package org.siemac.metamac.statistical.operations.web.external;

import javax.servlet.ServletContextEvent;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.listener.ApplicationStartupListener;
import org.siemac.metamac.core.common.util.WebUtils;
import org.siemac.metamac.statistical.operations.core.constants.StatisticalOperationsConfigurationConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationStartup extends ApplicationStartupListener {

    private static final Logger log = LoggerFactory.getLogger(ApplicationStartup.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        super.contextInitialized(sce);
        try {
            WebUtils.setAppsBaseUrl(configurationService.retrieveAppsExternalWebApplicationUrlBase());
            WebUtils.setApiBaseURL(configurationService.retrieveStatisticalOperationsExternalApiUrlBase());

            WebUtils.setApiStyleHeaderUrl(configurationService.retrieveApiStyleHeaderUrl());
            WebUtils.setApiStyleFooterUrl(configurationService.retrieveApiStyleFooterUrl());
        } catch (MetamacException e) {
            log.error("Error retrieving application configuration", e);
        }
    }

    @Override
    public String projectName() {
        return "statistical-operations";
    }

    @Override
    public void checkApplicationProperties() throws MetamacException {
        // Datasource
        checkRequiredProperty(StatisticalOperationsConfigurationConstants.DB_DRIVER_NAME);
        checkRequiredProperty(StatisticalOperationsConfigurationConstants.DB_URL);
        checkRequiredProperty(StatisticalOperationsConfigurationConstants.DB_USERNAME);
        checkRequiredProperty(StatisticalOperationsConfigurationConstants.DB_PASSWORD);

        // Api
        checkRequiredProperty(StatisticalOperationsConfigurationConstants.ENDPOINT_STATISTICAL_OPERATIONS_EXTERNAL_API);
        checkRequiredProperty(StatisticalOperationsConfigurationConstants.ENDPOINT_COMMON_METADATA_EXTERNAL_API);
        checkRequiredProperty(StatisticalOperationsConfigurationConstants.ENDPOINT_SRM_EXTERNAL_API);
    }
}

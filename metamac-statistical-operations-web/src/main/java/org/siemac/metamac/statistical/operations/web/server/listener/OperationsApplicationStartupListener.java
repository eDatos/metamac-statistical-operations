package org.siemac.metamac.statistical.operations.web.server.listener;

import org.siemac.metamac.core.common.constants.shared.ConfigurationConstants;
import org.siemac.metamac.statistical.operations.core.constants.StatisticalOperationsConfigurationConstants;
import org.siemac.metamac.web.common.server.listener.InternalApplicationStartupListener;

public class OperationsApplicationStartupListener extends InternalApplicationStartupListener {

    @Override
    public String projectName() {
        return "statistical-operations-internal";
    }

    @Override
    public void checkDatasourceProperties() {
        checkRequiredProperty(StatisticalOperationsConfigurationConstants.DB_DRIVER_NAME);
        checkRequiredProperty(StatisticalOperationsConfigurationConstants.DB_URL);
        checkRequiredProperty(StatisticalOperationsConfigurationConstants.DB_USERNAME);
        checkRequiredProperty(StatisticalOperationsConfigurationConstants.DB_PASSWORD);
    }

    @Override
    public void checkWebApplicationsProperties() {
        checkRequiredProperty(ConfigurationConstants.WEB_APPLICATION_STATISTICAL_OPERATIONS_INTERNAL_WEB);
        checkRequiredProperty(ConfigurationConstants.WEB_APPLICATION_SRM_INTERNAL_WEB);
        checkRequiredProperty(ConfigurationConstants.WEB_APPLICATION_COMMON_METADATA_INTERNAL_WEB);
    }

    @Override
    public void checkApiProperties() {
        checkRequiredProperty(ConfigurationConstants.ENDPOINT_STATISTICAL_OPERATIONS_INTERNAL_API);
        checkRequiredProperty(ConfigurationConstants.ENDPOINT_SRM_INTERNAL_API);
        checkRequiredProperty(ConfigurationConstants.ENDPOINT_COMMON_METADATA_EXTERNAL_API);
        checkRequiredProperty(ConfigurationConstants.ENDPOINT_ACCESS_CONTROL_INTERNAL_API);
    }

    @Override
    public void checkOtherModuleProperties() {
        checkOptionalDefaultCodelistTemporalGranularityUrn();
        checkOptionalDefaultCodelistGeographicalGranularityUrn();

        checkRequiredProperty(StatisticalOperationsConfigurationConstants.HELP_URL);
    }
}

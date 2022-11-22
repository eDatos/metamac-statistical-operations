package org.siemac.metamac.statistical.operations.core.conf;

import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.exception.MetamacException;

public interface StatisticalOperationsConfigurationService extends ConfigurationService {

    public String retrieveHelpUrl() throws MetamacException;
}

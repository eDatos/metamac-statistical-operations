package org.siemac.metamac.statistical.operations.web.client.operation.view.handlers;

import java.util.List;

import org.siemac.metamac.statistical.operations.core.dto.InstanceDto;
import org.siemac.metamac.statistical.operations.core.dto.OperationDto;
import org.siemac.metamac.web.common.client.view.handlers.SrmExternalResourcesUiHandlers;
import org.siemac.metamac.web.common.shared.criteria.CommonConfigurationRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public interface OperationUiHandlers extends SrmExternalResourcesUiHandlers {

    void saveOperation(OperationDto operationDto);
    void goToFamily(String familyCode);
    void saveInstance(InstanceDto instanceDto);
    void goToInstance(String instanceCode);
    void deleteInstances(List<Long> instanceIds);
    void updateOperationFamilies(List<Long> familiesToAdd, List<Long> familiesToRemove);
    void retrieveFamilies(int firstResult, int maxResults, MetamacWebCriteria metamacWebCriteria);

    void updateInstancesOrder(List<Long> instancesIds);

    void deleteOperation(OperationDto operationDto);

    // External resources

    void retrieveCommonMetadataConfigurations(CommonConfigurationRestCriteria criteria);

    void retrieveUsersAccessControl();
}

package org.siemac.metamac.statistical.operations.web.client.operation.view.handlers;

import org.siemac.metamac.statistical.operations.core.dto.OperationDto;
import org.siemac.metamac.web.common.client.view.handlers.SrmExternalResourcesUiHandlers;

import java.util.List;

public interface OperationListUiHandlers extends SrmExternalResourcesUiHandlers {

    void retrieveOperationList(int firstResult, int maxResults, String operation);

    void createOperation(OperationDto operationDto);
    void goToOperation(String operationCode);
    void deleteOperations(List<Long> operationDtos);

    void exportOperationsTsv();
}

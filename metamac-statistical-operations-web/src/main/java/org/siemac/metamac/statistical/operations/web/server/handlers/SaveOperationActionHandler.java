package org.siemac.metamac.statistical.operations.web.server.handlers;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.operations.core.dto.OperationDto;
import org.siemac.metamac.statistical.operations.core.serviceapi.StatisticalOperationsServiceFacade;
import org.siemac.metamac.statistical.operations.web.server.handlers.utils.HandlersUtils;
import org.siemac.metamac.statistical.operations.web.server.rest.AccessControlRestInternalFacade;
import org.siemac.metamac.statistical.operations.web.shared.SaveOperationAction;
import org.siemac.metamac.statistical.operations.web.shared.SaveOperationResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class SaveOperationActionHandler extends SecurityActionHandler<SaveOperationAction, SaveOperationResult> {

    @Autowired
    private StatisticalOperationsServiceFacade statisticalOperationsServiceFacade;

    @Autowired
    private AccessControlRestInternalFacade    accessControlRestInternalFacade;

    public SaveOperationActionHandler() {
        super(SaveOperationAction.class);
    }

    @Override
    public SaveOperationResult executeSecurityAction(SaveOperationAction action) throws ActionException {
        OperationDto operationToSave = action.getOperationDto();
        if (operationToSave.getId() == null) {
            // Create operation
            try {
                OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(ServiceContextHolder.getCurrentServiceContext(), operationToSave);
                return new SaveOperationResult(operationDto);
            } catch (MetamacException e) {
                throw WebExceptionUtils.createMetamacWebException(e);
            }
        } else {
            // Update operation
            try {
                OperationDto operationDto = statisticalOperationsServiceFacade.updateOperation(ServiceContextHolder.getCurrentServiceContext(), operationToSave);
                HandlersUtils.setFullnameUserByUsername(operationDto, accessControlRestInternalFacade);
                return new SaveOperationResult(operationDto);
            } catch (MetamacException e) {
                throw WebExceptionUtils.createMetamacWebException(e);
            }
        }
    }
}

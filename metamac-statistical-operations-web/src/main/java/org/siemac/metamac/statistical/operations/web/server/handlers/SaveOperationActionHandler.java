package org.siemac.metamac.statistical.operations.web.server.handlers;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.operations.core.dto.OperationDto;
import org.siemac.metamac.statistical.operations.core.serviceapi.StatisticalOperationsServiceFacade;
import org.siemac.metamac.statistical.operations.web.server.rest.AccessControlRestInternalFacade;
import org.siemac.metamac.statistical.operations.web.shared.SaveOperationAction;
import org.siemac.metamac.statistical.operations.web.shared.SaveOperationResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
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
                setFullnameUserByUsername(operationDto);
                return new SaveOperationResult(operationDto);
            } catch (MetamacException e) {
                throw WebExceptionUtils.createMetamacWebException(e);
            }
        } else {
            // Update operation
            try {
                OperationDto operationDto = statisticalOperationsServiceFacade.updateOperation(ServiceContextHolder.getCurrentServiceContext(), operationToSave);
                setFullnameUserByUsername(operationDto);
                return new SaveOperationResult(operationDto);
            } catch (MetamacException e) {
                throw WebExceptionUtils.createMetamacWebException(e);
            }
        }
    }

    private void setFullnameUserByUsername(OperationDto operationDto) throws MetamacWebException {
        operationDto.setAssistantTechnician(operationDto.getAssistantTechnician() != null ? accessControlRestInternalFacade.findFullnameUserByUsername(operationDto.getAssistantTechnician()) : null);
        operationDto.setTechnicianInCharge(operationDto.getTechnicianInCharge() != null ? accessControlRestInternalFacade.findFullnameUserByUsername(operationDto.getTechnicianInCharge()) : null);
    }
}

package org.siemac.metamac.statistical.operations.web.server.handlers;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.operations.core.dto.FamilyBaseDto;
import org.siemac.metamac.statistical.operations.core.dto.InstanceBaseDto;
import org.siemac.metamac.statistical.operations.core.dto.OperationDto;
import org.siemac.metamac.statistical.operations.core.serviceapi.StatisticalOperationsServiceFacade;
import org.siemac.metamac.statistical.operations.web.server.rest.AccessControlRestInternalFacade;
import org.siemac.metamac.statistical.operations.web.shared.GetOperationAndInstancesAction;
import org.siemac.metamac.statistical.operations.web.shared.GetOperationAndInstancesResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetOperationAndInstancesActionHandler extends SecurityActionHandler<GetOperationAndInstancesAction, GetOperationAndInstancesResult> {

    @Autowired
    private StatisticalOperationsServiceFacade statisticalOperationsServiceFacade;

    @Autowired
    private AccessControlRestInternalFacade    accessControlRestInternalFacade;

    public GetOperationAndInstancesActionHandler() {
        super(GetOperationAndInstancesAction.class);
    }

    @Override
    public GetOperationAndInstancesResult executeSecurityAction(GetOperationAndInstancesAction action) throws ActionException {
        try {
            OperationDto operationDto = statisticalOperationsServiceFacade.findOperationByUrn(ServiceContextHolder.getCurrentServiceContext(), action.getOperationUrn());
            setFullnameUserByUsername(operationDto);
            List<FamilyBaseDto> familyBaseDtos = statisticalOperationsServiceFacade.findFamiliesForOperation(ServiceContextHolder.getCurrentServiceContext(), operationDto.getId());
            List<InstanceBaseDto> instanceDtos = statisticalOperationsServiceFacade.findInstancesForOperation(ServiceContextHolder.getCurrentServiceContext(), operationDto.getId());
            return new GetOperationAndInstancesResult(operationDto, instanceDtos, familyBaseDtos);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

    @Override
    public void undo(GetOperationAndInstancesAction action, GetOperationAndInstancesResult result, ExecutionContext context) throws ActionException {

    }

    private void setFullnameUserByUsername(OperationDto operationDto) throws MetamacWebException {
        operationDto.setAssistantTechnician(operationDto.getAssistantTechnician() != null ? accessControlRestInternalFacade.findFullnameUserByUsername(operationDto.getAssistantTechnician()) : null);
        operationDto.setTechnicianInCharge(operationDto.getTechnicianInCharge() != null ? accessControlRestInternalFacade.findFullnameUserByUsername(operationDto.getTechnicianInCharge()) : null);
    }

}

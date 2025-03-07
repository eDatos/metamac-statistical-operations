package org.siemac.metamac.statistical.operations.web.server.handlers;

import java.io.Serializable;
import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.operations.core.dto.FamilyBaseDto;
import org.siemac.metamac.statistical.operations.core.dto.InstanceBaseDto;
import org.siemac.metamac.statistical.operations.core.dto.OperationDto;
import org.siemac.metamac.statistical.operations.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.operations.core.serviceapi.StatisticalOperationsServiceFacade;
import org.siemac.metamac.statistical.operations.web.server.handlers.utils.HandlersUtils;
import org.siemac.metamac.statistical.operations.web.server.rest.AccessControlRestInternalFacade;
import org.siemac.metamac.statistical.operations.web.shared.GetOperationAndInstancesAction;
import org.siemac.metamac.statistical.operations.web.shared.GetOperationAndInstancesResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

import static org.siemac.metamac.web.common.server.utils.WebExceptionUtils.createMetamacWebExceptionTranslated;

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
            List<FamilyBaseDto> familyBaseDtos = statisticalOperationsServiceFacade.findFamiliesForOperation(ServiceContextHolder.getCurrentServiceContext(), operationDto.getId());
            List<InstanceBaseDto> instanceDtos = statisticalOperationsServiceFacade.findInstancesForOperation(ServiceContextHolder.getCurrentServiceContext(), operationDto.getId());
;
            if (HandlersUtils.doesTechniciansFullnameExist(operationDto, accessControlRestInternalFacade)) {
                return new GetOperationAndInstancesResult(operationDto, instanceDtos, familyBaseDtos,
                        createMetamacWebExceptionTranslated(ServiceContextHolder.getCurrentServiceContext(),
                                ServiceExceptionType.OPERATION_NON_EXISTING_TECHNICIAN, (Serializable[]) null));
            }
            return new GetOperationAndInstancesResult(operationDto, instanceDtos, familyBaseDtos, null);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

    @Override
    public void undo(GetOperationAndInstancesAction action, GetOperationAndInstancesResult result, ExecutionContext context) throws ActionException {

    }
}

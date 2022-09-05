package org.siemac.metamac.statistical.operations.web.server.handlers.utils;

import org.siemac.metamac.statistical.operations.core.dto.OperationDto;
import org.siemac.metamac.statistical.operations.web.server.rest.AccessControlRestInternalFacade;

public class HandlersUtils {

    public static void setFullnameUserByUsername(OperationDto operationDto, AccessControlRestInternalFacade accessControlRestInternalFacade) {
        operationDto.setAssistantTechnician(operationDto.getAssistantTechnician() != null ? accessControlRestInternalFacade.findFullnameUserByUsername(operationDto.getAssistantTechnician()) : null);
        operationDto.setTechnicianInCharge(operationDto.getTechnicianInCharge() != null ? accessControlRestInternalFacade.findFullnameUserByUsername(operationDto.getTechnicianInCharge()) : null);
    }

}

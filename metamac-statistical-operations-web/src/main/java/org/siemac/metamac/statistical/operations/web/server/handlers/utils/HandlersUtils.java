package org.siemac.metamac.statistical.operations.web.server.handlers.utils;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.statistical.operations.core.dto.OperationDto;
import org.siemac.metamac.statistical.operations.web.server.rest.AccessControlRestInternalFacade;

public class HandlersUtils {

    public static void setFullnameUserByUsername(OperationDto operationDto, AccessControlRestInternalFacade accessControlRestInternalFacade) {
        operationDto.setAssistantTechnician(
                StringUtils.isNotEmpty(operationDto.getAssistantTechnician()) ? accessControlRestInternalFacade.findFullnameUserByUsername(operationDto.getAssistantTechnician()) : null);
        operationDto.setTechnicianInCharge(
                StringUtils.isNotEmpty(operationDto.getTechnicianInCharge()) ? accessControlRestInternalFacade.findFullnameUserByUsername(operationDto.getTechnicianInCharge()) : null);
    }

}

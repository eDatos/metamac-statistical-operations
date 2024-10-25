package org.siemac.metamac.statistical.operations.web.server.handlers.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.operations.core.dto.OperationDto;
import org.siemac.metamac.statistical.operations.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.operations.core.serviceimpl.result.PublishExternallyOperationServiceResult;
import org.siemac.metamac.statistical.operations.web.server.rest.AccessControlRestInternalFacade;
import org.siemac.metamac.statistical.operations.web.server.rest.NoticesRestInternalFacade;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;

public class HandlersUtils {

    public static void setFullnameUserByUsername(OperationDto operationDto, AccessControlRestInternalFacade accessControlRestInternalFacade) {
        operationDto.setAssistantTechnician(
                StringUtils.isNotEmpty(operationDto.getAssistantTechnician()) ? accessControlRestInternalFacade.findFullnameUserByUsername(operationDto.getAssistantTechnician()) : null);
        operationDto.setTechnicianInCharge(
                StringUtils.isNotEmpty(operationDto.getTechnicianInCharge()) ? accessControlRestInternalFacade.findFullnameUserByUsername(operationDto.getTechnicianInCharge()) : null);
    }

    public static MetamacException doesTechniciansFullnameExist(OperationDto operationDto, AccessControlRestInternalFacade accessControlRestInternalFacade) throws MetamacException{

        String technicianInCharge = StringUtils.isNotEmpty(operationDto.getTechnicianInCharge()) ? operationDto.getTechnicianInCharge() : "";
        String technicianInChargeAccessControl = StringUtils.isNotEmpty(technicianInCharge) ? accessControlRestInternalFacade.findFullnameUserByUsername(technicianInCharge) : "";
        String assistantTechnician = StringUtils.isNotEmpty(operationDto.getAssistantTechnician()) ? operationDto.getAssistantTechnician() : "";
        String assistantTechnicianAccessControl = StringUtils.isNotEmpty(assistantTechnician) ? accessControlRestInternalFacade.findFullnameUserByUsername(assistantTechnician) : "";

        if ((!StringUtils.isEmpty(technicianInCharge) && technicianInChargeAccessControl.equalsIgnoreCase(technicianInCharge)) ||
                ((!StringUtils.isEmpty(assistantTechnician) && assistantTechnicianAccessControl.equalsIgnoreCase(assistantTechnician)))) {
            operationDto.setAssistantTechnician(assistantTechnician);
            operationDto.setTechnicianInCharge(technicianInCharge);

            return new MetamacException(ServiceExceptionType.OPERATION_NON_EXISTING_TECHNICIAN);
        }

        operationDto.setAssistantTechnician(assistantTechnicianAccessControl);
        operationDto.setTechnicianInCharge(technicianInChargeAccessControl);

        return null;
    }

    public static MetamacWebException getExceptionsPublishExternalOperation(ServiceContext serviceContext, PublishExternallyOperationServiceResult result,
            NoticesRestInternalFacade noticesRestInternalFacade, boolean sendSuccessPublication) {
        MetamacWebException operationException = null;
        if (!result.isOk()) {
            MetamacException e = result.getMainException();
            List<MetamacExceptionItem> list = new ArrayList<>();
            for (MetamacException exception : result.getSecondaryExceptions()) {
                list.addAll(exception.getExceptionItems());
            }
            e.getExceptionItems().addAll(list);
            operationException = WebExceptionUtils.createMetamacWebException(e);
            try {
                noticesRestInternalFacade.createNotificationForStreamError(serviceContext, result.getContent());
            } catch (MetamacWebException noticeException) {
                operationException.getWebExceptionItems().addAll(noticeException.getWebExceptionItems());
            }
        }

        if (sendSuccessPublication) {
            try {
                noticesRestInternalFacade.createNotificationForPublishExternallyOperation(serviceContext, result.getContent());
            } catch (MetamacWebException e) {
                if (operationException == null) {
                    operationException = e;
                } else {
                    operationException.getWebExceptionItems().addAll(e.getWebExceptionItems());
                }
            }
        }
        return operationException;
    }

}

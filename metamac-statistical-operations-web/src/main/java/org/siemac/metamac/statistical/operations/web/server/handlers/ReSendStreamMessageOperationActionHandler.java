package org.siemac.metamac.statistical.operations.web.server.handlers;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.operations.core.serviceapi.StatisticalOperationsServiceFacade;
import org.siemac.metamac.statistical.operations.core.serviceimpl.result.ReSendStreamMessageOperationServiceResult;
import org.siemac.metamac.statistical.operations.web.server.rest.NoticesRestInternalFacade;
import org.siemac.metamac.statistical.operations.web.shared.ReSendStreamMessageOperationAction;
import org.siemac.metamac.statistical.operations.web.shared.ReSendStreamMessageOperationResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class ReSendStreamMessageOperationActionHandler extends SecurityActionHandler<ReSendStreamMessageOperationAction, ReSendStreamMessageOperationResult> {

    @Autowired
    private StatisticalOperationsServiceFacade statisticalOperationsServiceFacade;

    @Autowired
    private NoticesRestInternalFacade          noticesRestInternalFacade;

    public ReSendStreamMessageOperationActionHandler() {
        super(ReSendStreamMessageOperationAction.class);
    }

    @Override
    public ReSendStreamMessageOperationResult executeSecurityAction(ReSendStreamMessageOperationAction action) throws ActionException {
        ServiceContext serviceContext = ServiceContextHolder.getCurrentServiceContext();
        ReSendStreamMessageOperationServiceResult result;

        try {
            result = statisticalOperationsServiceFacade.republishExternallyOperation(ServiceContextHolder.getCurrentServiceContext(), action.getOperationId());
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }

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
                result.setContent(statisticalOperationsServiceFacade.findOperationById(serviceContext, action.getOperationId()));
                noticesRestInternalFacade.createNotificationForStreamError(serviceContext, result.getContent());
            } catch (MetamacWebException noticeException) {
                operationException.getWebExceptionItems().addAll(noticeException.getWebExceptionItems());
            } catch (MetamacException ex) {
                throw new RuntimeException(ex);
            }
        }

        return new ReSendStreamMessageOperationResult(result.getContent(), operationException);
    }

}

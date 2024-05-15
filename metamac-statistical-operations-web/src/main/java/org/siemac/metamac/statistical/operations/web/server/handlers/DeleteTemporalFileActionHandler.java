package org.siemac.metamac.statistical.operations.web.server.handlers;

import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.siemac.metamac.statistical.operations.web.shared.DeleteTemporalFileAction;
import org.siemac.metamac.statistical.operations.web.shared.DeleteTemporalFileResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.operations.core.serviceapi.StatisticalOperationsServiceFacade;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class DeleteTemporalFileActionHandler extends SecurityActionHandler<DeleteTemporalFileAction, DeleteTemporalFileResult> {

    @Autowired
    private StatisticalOperationsServiceFacade statisticalOperationsServiceFacade;

    public DeleteTemporalFileActionHandler() {
        super(DeleteTemporalFileAction.class);
    }
    @Override
    public DeleteTemporalFileResult executeSecurityAction(DeleteTemporalFileAction action) throws ActionException {
        try {
            statisticalOperationsServiceFacade.deleteTemporalFile(ServiceContextHolder.getCurrentServiceContext(), action.getFileName());
            return new DeleteTemporalFileResult();
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

}

package org.siemac.metamac.statistical.operations.web.server.handlers;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.operations.core.serviceapi.StatisticalOperationsServiceFacade;
import org.siemac.metamac.statistical.operations.web.shared.ExportOperationListAction;
import org.siemac.metamac.statistical.operations.web.shared.ExportOperationListResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class ExportOperationListActionHandler extends SecurityActionHandler<ExportOperationListAction, ExportOperationListResult> {

    @Autowired
    private StatisticalOperationsServiceFacade statisticalOperationsServiceFacade;

    public ExportOperationListActionHandler() {
            super(ExportOperationListAction.class);
        }

        @Override
        public ExportOperationListResult executeSecurityAction(ExportOperationListAction action) throws ActionException {
            String fileName = null;

            try {
                fileName = statisticalOperationsServiceFacade.exportOperationsTsv(ServiceContextHolder.getCurrentServiceContext(),action.getIdOperations());
            } catch (MetamacException e) {
                throw WebExceptionUtils.createMetamacWebException(e);
            }
            return new ExportOperationListResult(fileName);
        }

        @Override
        public void undo(ExportOperationListAction action, ExportOperationListResult result, ExecutionContext context) throws ActionException {

        }
}

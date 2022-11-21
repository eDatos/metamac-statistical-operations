package org.siemac.metamac.statistical.operations.web.server.handlers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.criteria.MetamacCriteria;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaDisjunctionRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.shared.MetamacCriteriaOrder;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.operations.core.criteria.OperationCriteriaOrderEnum;
import org.siemac.metamac.statistical.operations.core.criteria.OperationCriteriaPropertyEnum;
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
            MetamacCriteria criteria = new MetamacCriteria();
            // Order
            MetamacCriteriaOrder order = new MetamacCriteriaOrder();
            order.setType(MetamacCriteriaOrder.OrderTypeEnum.DESC);
            order.setPropertyName(OperationCriteriaOrderEnum.LAST_UPDATED.name());
            List<MetamacCriteriaOrder> criteriaOrders = new ArrayList<MetamacCriteriaOrder>();
            criteriaOrders.add(order);
            criteria.setOrdersBy(criteriaOrders);

            MetamacCriteriaDisjunctionRestriction disjuction = new MetamacCriteriaDisjunctionRestriction();
            if (!StringUtils.isBlank(action.getOperation())) {
                disjuction.getRestrictions()
                        .add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.CODE.name(), action.getOperation(), MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
                disjuction.getRestrictions()
                        .add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.TITLE.name(), action.getOperation(), MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
                disjuction.getRestrictions()
                        .add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.DESCRIPTION.name(), action.getOperation(), MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
                disjuction.getRestrictions()
                        .add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.ACRONYM.name(), action.getOperation(), MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
                disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.TECHNICIAN_IN_CHARGE.name(), action.getOperation(),
                        MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
                disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.ASSISTANT_TECHNICIAN.name(), action.getOperation(),
                        MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
            }
            criteria.setRestriction(disjuction);

            fileName = statisticalOperationsServiceFacade.exportOperationsTsv(ServiceContextHolder.getCurrentServiceContext(), criteria);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
        return new ExportOperationListResult(fileName);
    }

    @Override
    public void undo(ExportOperationListAction action, ExportOperationListResult result, ExecutionContext context) throws ActionException {

    }
}

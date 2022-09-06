package org.siemac.metamac.statistical.operations.web.server.handlers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.criteria.MetamacCriteria;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaDisjunctionRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPaginator;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction.OperationType;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.criteria.shared.MetamacCriteriaOrder;
import org.siemac.metamac.core.common.criteria.shared.MetamacCriteriaOrder.OrderTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.operations.core.criteria.OperationCriteriaOrderEnum;
import org.siemac.metamac.statistical.operations.core.criteria.OperationCriteriaPropertyEnum;
import org.siemac.metamac.statistical.operations.core.dto.OperationBaseDto;
import org.siemac.metamac.statistical.operations.core.serviceapi.StatisticalOperationsServiceFacade;
import org.siemac.metamac.statistical.operations.web.shared.GetOperationPaginatedListAction;
import org.siemac.metamac.statistical.operations.web.shared.GetOperationPaginatedListResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetOperationPaginatedListActionHandler extends SecurityActionHandler<GetOperationPaginatedListAction, GetOperationPaginatedListResult> {

    @Autowired
    private StatisticalOperationsServiceFacade statisticalOperationsServiceFacade;

    public GetOperationPaginatedListActionHandler() {
        super(GetOperationPaginatedListAction.class);
    }

    @Override
    public GetOperationPaginatedListResult executeSecurityAction(GetOperationPaginatedListAction action) throws ActionException {
        try {
            MetamacCriteria criteria = new MetamacCriteria();
            criteria.setPaginator(new MetamacCriteriaPaginator());
            criteria.getPaginator().setFirstResult(action.getFirstResult());
            criteria.getPaginator().setMaximumResultSize(action.getMaxResults());
            criteria.getPaginator().setCountTotalResults(true);

            // Order
            MetamacCriteriaOrder order = new MetamacCriteriaOrder();
            order.setType(OrderTypeEnum.DESC);
            order.setPropertyName(OperationCriteriaOrderEnum.LAST_UPDATED.name());
            List<MetamacCriteriaOrder> criteriaOrders = new ArrayList<MetamacCriteriaOrder>();
            criteriaOrders.add(order);
            criteria.setOrdersBy(criteriaOrders);

            MetamacCriteriaDisjunctionRestriction disjuction = new MetamacCriteriaDisjunctionRestriction();
            if (!StringUtils.isBlank(action.getOperation())) {
                disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.CODE.name(), action.getOperation(), OperationType.ILIKE));
                disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.TITLE.name(), action.getOperation(), OperationType.ILIKE));
                disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.DESCRIPTION.name(), action.getOperation(), OperationType.ILIKE));
                disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.ACRONYM.name(), action.getOperation(), OperationType.ILIKE));
                disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.TECHNICIAN_IN_CHARGE.name(), action.getOperation(), OperationType.ILIKE));
                disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.ASSISTANT_TECHNICIAN.name(), action.getOperation(), OperationType.ILIKE));
            }
            criteria.setRestriction(disjuction);

            MetamacCriteriaResult<OperationBaseDto> result = statisticalOperationsServiceFacade.findOperationsByCondition(ServiceContextHolder.getCurrentServiceContext(), criteria);
            return new GetOperationPaginatedListResult(result.getResults(), result.getPaginatorResult().getFirstResult(), result.getPaginatorResult().getTotalResults());
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}

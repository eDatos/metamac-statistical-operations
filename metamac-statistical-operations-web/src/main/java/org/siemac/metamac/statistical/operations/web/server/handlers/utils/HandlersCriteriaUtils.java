package org.siemac.metamac.statistical.operations.web.server.handlers.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.criteria.MetamacCriteria;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaDisjunctionRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPaginator;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.shared.MetamacCriteriaOrder;
import org.siemac.metamac.statistical.operations.core.criteria.FamilyCriteriaPropertyEnum;
import org.siemac.metamac.statistical.operations.core.criteria.OperationCriteriaOrderEnum;
import org.siemac.metamac.statistical.operations.core.criteria.OperationCriteriaPropertyEnum;

public class HandlersCriteriaUtils {

    public static MetamacCriteria defaultCriteriaPaginator(int firstResult, int maxResults) {
        MetamacCriteria criteria = new MetamacCriteria();
        criteria.setPaginator(new MetamacCriteriaPaginator());
        criteria.getPaginator().setFirstResult(firstResult);
        criteria.getPaginator().setMaximumResultSize(maxResults);
        criteria.getPaginator().setCountTotalResults(true);

        return criteria;
    }

    public static void defaultCriteriaOrder(MetamacCriteria criteria) {
        MetamacCriteriaOrder order = new MetamacCriteriaOrder();
        order.setType(MetamacCriteriaOrder.OrderTypeEnum.DESC);
        order.setPropertyName(OperationCriteriaOrderEnum.LAST_UPDATED.name());
        List<MetamacCriteriaOrder> criteriaOrders = new ArrayList<MetamacCriteriaOrder>();
        criteriaOrders.add(order);
        criteria.setOrdersBy(criteriaOrders);
    }

    public static void defaultCriteriaDisjunctionRestrictionOperations(MetamacCriteria criteria, String operation) {
        MetamacCriteriaDisjunctionRestriction disjuction = new MetamacCriteriaDisjunctionRestriction();
        if (!StringUtils.isBlank(operation)) {
            // @formatter:off
            disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.CODE.name(), operation, MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
            disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.TITLE.name(), operation, MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
            disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.DESCRIPTION.name(), operation, MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
            disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.ACRONYM.name(), operation, MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
            disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.TECHNICIAN_IN_CHARGE.name(), operation, MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
            disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.ASSISTANT_TECHNICIAN.name(), operation, MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
            // @formatter:on
        }
        criteria.setRestriction(disjuction);
    }

    public static void defaultCriteriaDisjunctionRestrictionFamily(MetamacCriteria criteria, String family) {
        MetamacCriteriaDisjunctionRestriction disjuction = new MetamacCriteriaDisjunctionRestriction();
        if (!StringUtils.isBlank(family)) {
            disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.CODE.name(), family, MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
            disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.TITLE.name(), family, MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
            disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.DESCRIPTION.name(), family, MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
            disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.ACRONYM.name(), family, MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
        }
        criteria.setRestriction(disjuction);
    }
}

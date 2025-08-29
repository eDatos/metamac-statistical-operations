package org.siemac.metamac.statistical.operations.web.server.handlers.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.criteria.MetamacCriteria;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaDisjunctionRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPaginator;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaRestriction;
import org.siemac.metamac.core.common.criteria.shared.MetamacCriteriaOrder;
import org.siemac.metamac.statistical.operations.core.criteria.FamilyCriteriaPropertyEnum;
import org.siemac.metamac.statistical.operations.core.criteria.OperationCriteriaOrderEnum;
import org.siemac.metamac.statistical.operations.core.criteria.OperationCriteriaPropertyEnum;
import org.siemac.metamac.statistical.operations.core.enume.domain.StatusEnum;
import org.siemac.metamac.statistical.operations.web.shared.GetOperationPaginatedListAction;
import org.siemac.metamac.statistical.operations.web.shared.criteria.OperationCriteria;

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
            disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.STATISTIC_PLAN_CODE.name(), operation, MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
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

    public static void buildMetamacCriteriaFromOperationCriteria(MetamacCriteria criteria, GetOperationPaginatedListAction action) {
        MetamacCriteriaDisjunctionRestriction disjuction = new MetamacCriteriaDisjunctionRestriction();

        OperationCriteria operationCriteria = action.getOperation();
        String operation = operationCriteria.getCriteria();
        if (!StringUtils.isBlank(operation)) {
            // @formatter:off
            disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.CODE.name(), operation, MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
            disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.TITLE.name(), operation, MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
            disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.DESCRIPTION.name(), operation, MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
            disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.ACRONYM.name(), operation, MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
            disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.TECHNICIAN_IN_CHARGE.name(), operation, MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
            disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.ASSISTANT_TECHNICIAN.name(), operation, MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
            disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.STATISTIC_PLAN_CODE.name(), operation, MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
            // @formatter:on
        }
        addRestrictionIfExists(disjuction, buildCodeCriteria(operationCriteria));
        addRestrictionIfExists(disjuction, buildTitleCriteria(operationCriteria));
        addRestrictionIfExists(disjuction, buildStatusCriteria(operationCriteria));
        addRestrictionIfExists(disjuction, buildDisaggregationBySexCriteria(operationCriteria));
        addRestrictionIfExists(disjuction, buildDisaggregationByAgeCriteria(operationCriteria));
        addRestrictionIfExists(disjuction, buildDisaggregationByNationalityCriteria(operationCriteria));
        addRestrictionIfExists(disjuction, buildDisaggregationByDisabilityCriteria(operationCriteria));

        criteria.setRestriction(disjuction);
    }

    private static void addRestrictionIfExists(MetamacCriteriaDisjunctionRestriction criteria, MetamacCriteriaRestriction restriction) {
        if (restriction != null) {
            criteria.getRestrictions().add(restriction);
        }
    }

    private static MetamacCriteriaRestriction buildCodeCriteria(OperationCriteria criteria) {
        if (StringUtils.isNotBlank(criteria.getCode())) {
            return new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.CODE.name(), criteria.getCode(), MetamacCriteriaPropertyRestriction.OperationType.ILIKE);
        }
        return null;
    }

    private static MetamacCriteriaRestriction buildTitleCriteria(OperationCriteria criteria) {
        if (StringUtils.isNotBlank(criteria.getTitle())) {
            return new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.TITLE.name(), criteria.getTitle(), MetamacCriteriaPropertyRestriction.OperationType.ILIKE);
        }
        return null;
    }

    private static MetamacCriteriaRestriction buildStatusCriteria(OperationCriteria criteria) {
        if (criteria != null && StringUtils.isNotBlank(criteria.getStatus())) {
            return new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.STATUS.name(), StatusEnum.valueOf(criteria.getStatus()), MetamacCriteriaPropertyRestriction.OperationType.EQ);
        }
        return null;
    }

    private static MetamacCriteriaRestriction buildDisaggregationBySexCriteria(OperationCriteria criteria) {
        if (criteria != null && StringUtils.isNotBlank(criteria.getDisaggregationBySex())) {
            return new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.DISAGGREGATION_BY_SEX.name(), Boolean.valueOf(criteria.getDisaggregationBySex()),
                    MetamacCriteriaPropertyRestriction.OperationType.EQ);
        }
        return null;
    }

    private static MetamacCriteriaRestriction buildDisaggregationByAgeCriteria(OperationCriteria criteria) {
        if (criteria != null && StringUtils.isNotBlank(criteria.getDisaggregationByAge())) {
            return new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.DISAGGREGATION_BY_AGE.name(), Boolean.valueOf(criteria.getDisaggregationByAge()),
                    MetamacCriteriaPropertyRestriction.OperationType.EQ);
        }
        return null;
    }

    private static MetamacCriteriaRestriction buildDisaggregationByNationalityCriteria(OperationCriteria criteria) {
        if (criteria != null && StringUtils.isNotBlank(criteria.getDisaggregationByNationality())) {
            return new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.DISAGGREGATION_BY_NATIONALITY.name(), Boolean.valueOf(criteria.getDisaggregationByNationality()),
                    MetamacCriteriaPropertyRestriction.OperationType.EQ);
        }
        return null;
    }

    private static MetamacCriteriaRestriction buildDisaggregationByDisabilityCriteria(OperationCriteria criteria) {
        if (criteria != null && StringUtils.isNotBlank(criteria.getDisaggregationByDisability())) {
            return new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.DISAGGREGATION_BY_DISABILITY.name(), Boolean.valueOf(criteria.getDisaggregationByDisability()),
                    MetamacCriteriaPropertyRestriction.OperationType.EQ);
        }
        return null;
    }
}

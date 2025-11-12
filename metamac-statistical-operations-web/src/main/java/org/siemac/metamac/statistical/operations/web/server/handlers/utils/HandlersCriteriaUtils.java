package org.siemac.metamac.statistical.operations.web.server.handlers.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.criteria.MetamacCriteria;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaConjunctionRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaDisjunctionRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPaginator;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction.OperationType;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaRestriction;
import org.siemac.metamac.core.common.criteria.shared.MetamacCriteriaOrder;
import org.siemac.metamac.statistical.operations.core.criteria.FamilyCriteriaPropertyEnum;
import org.siemac.metamac.statistical.operations.core.criteria.OperationCriteriaOrderEnum;
import org.siemac.metamac.statistical.operations.core.criteria.OperationCriteriaPropertyEnum;
import org.siemac.metamac.statistical.operations.core.enume.domain.EdatosMigrationStatusEnum;
import org.siemac.metamac.statistical.operations.core.enume.domain.StatusEnum;
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

    public static void defaultCriteriaDisjunctionRestrictionOperations(MetamacCriteria criteria, OperationCriteria operation) {
        buildMetamacCriteriaFromOperationCriteria(criteria, operation, operation.getCriteria());
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

    public static void buildMetamacCriteriaFromOperationCriteria(MetamacCriteria webCriteria, OperationCriteria operationCriteria, String operation) {
        MetamacCriteriaConjunctionRestriction criteria = new MetamacCriteriaConjunctionRestriction();

        if (!StringUtils.isBlank(operation)) {
            criteria.getRestrictions().add(buildSimpleSearch(operation));
        }

        MetamacCriteriaConjunctionRestriction advanced = buildAdvancedSearch(operationCriteria);
        if (!advanced.getRestrictions().isEmpty()) {
            criteria.getRestrictions().add(advanced);
        }

        webCriteria.setRestriction(criteria);
    }

    private static MetamacCriteriaDisjunctionRestriction buildSimpleSearch(String operation) {
        MetamacCriteriaDisjunctionRestriction disjuction = new MetamacCriteriaDisjunctionRestriction();

        disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.CODE.name(), operation, MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
        disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.TITLE.name(), operation, MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
        disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.DESCRIPTION.name(), operation, MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
        disjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.ACRONYM.name(), operation, MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
        disjuction.getRestrictions()
                .add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.TECHNICIAN_IN_CHARGE.name(), operation, MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
        disjuction.getRestrictions()
                .add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.ASSISTANT_TECHNICIAN.name(), operation, MetamacCriteriaPropertyRestriction.OperationType.ILIKE));
        disjuction.getRestrictions()
                .add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.STATISTIC_PLAN_CODE.name(), operation, MetamacCriteriaPropertyRestriction.OperationType.ILIKE));

        return disjuction;
    }
    private static MetamacCriteriaConjunctionRestriction buildAdvancedSearch(OperationCriteria operationCriteria) {
        MetamacCriteriaConjunctionRestriction advanced = new MetamacCriteriaConjunctionRestriction();

        addRestrictionIfExists(advanced, buildCodeCriteria(operationCriteria));
        addRestrictionIfExists(advanced, buildTitleCriteria(operationCriteria));
        addRestrictionIfExists(advanced, buildStatusCriteria(operationCriteria));
        addRestrictionIfExists(advanced, buildEdatosMigrationStatusCriteria(operationCriteria));
        addRestrictionIfExists(advanced, buildDisaggregationBySexCriteria(operationCriteria));
        addRestrictionIfExists(advanced, buildDisaggregationByAgeCriteria(operationCriteria));
        addRestrictionIfExists(advanced, buildDisaggregationByNationalityCriteria(operationCriteria));
        addRestrictionIfExists(advanced, buildDisaggregationByDisabilityCriteria(operationCriteria));
        addRestrictionIfExists(advanced, buildOfficialityTypeCriteria(operationCriteria));
        addRestrictionIfExists(advanced, buildNewnessUntilDateCriteria(operationCriteria));
        addRestrictionIfExists(advanced, buildFeaturedUntilDateCriteria(operationCriteria));
        return advanced;
    }

    private static void addRestrictionIfExists(MetamacCriteriaConjunctionRestriction criteria, MetamacCriteriaRestriction restriction) {
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

    private static MetamacCriteriaRestriction buildEdatosMigrationStatusCriteria(OperationCriteria criteria) {
        if (criteria != null && StringUtils.isNotBlank(criteria.getEdatosMigrationStatus())) {
            return new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.EDATOS_MIGRATION_STATUS.name(), EdatosMigrationStatusEnum.valueOf(criteria.getEdatosMigrationStatus()),
                    MetamacCriteriaPropertyRestriction.OperationType.EQ);
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

    private static MetamacCriteriaRestriction buildOfficialityTypeCriteria(OperationCriteria criteria) {
        if (criteria != null && StringUtils.isNotBlank(criteria.getOfficialityType())) {
            return new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.OFFICIALITY_TYPE.name(), criteria.getOfficialityType(), MetamacCriteriaPropertyRestriction.OperationType.EQ);
        }
        return null;
    }
    private static MetamacCriteriaRestriction buildNewnessUntilDateCriteria(OperationCriteria criteria) {
        if (criteria.getNewnessUntilDate() != null) {
            return new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.NEWNESS_UNTIL_DATE.name(), criteria.getNewnessUntilDate(), OperationType.GE);
        }
        return null;
    }

    private static MetamacCriteriaRestriction buildFeaturedUntilDateCriteria(OperationCriteria criteria) {
        if (criteria.getFeaturedUntilDate() != null) {
            return new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.FEATURED_UNTIL_DATE.name(), criteria.getFeaturedUntilDate(), OperationType.GE);
        }
        return null;
    }

}

package org.siemac.metamac.statistical.operations.core.criteria;

public enum OperationCriteriaPropertyEnum {

    CODE,
    TITLE,
    ACRONYM,
    DESCRIPTION,
    PROC_STATUS,
    FAMILY_CODE,
    FAMILY_ID,
    TECHNICIAN_IN_CHARGE,
    ASSISTANT_TECHNICIAN,
    STATISTIC_PLAN_CODE,
    STATUS,
    DISAGGREGATION_BY_SEX,
    DISAGGREGATION_BY_AGE,
    DISAGGREGATION_BY_NATIONALITY,
    DISAGGREGATION_BY_DISABILITY;

    public String value() {
        return name();
    }

    public static OperationCriteriaPropertyEnum fromValue(String v) {
        return valueOf(v);
    }
}
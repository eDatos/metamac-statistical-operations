package org.siemac.metamac.statistical.operations.core.criteria;

public enum InstanceCriteriaPropertyEnum {

    CODE, TITLE, ACRONYM, DATA_DESCRIPTION, PROC_STATUS, OPERATION_CODE, OPERATION_ID, DISAGGREGATION_BY_SEX,DISAGGREGATION_BY_AGE, DISAGGREGATION_BY_NATIONALITY, DISAGGREGATION_BY_DISABILITY;

    public String value() {
        return name();
    }

    public static InstanceCriteriaPropertyEnum fromValue(String v) {
        return valueOf(v);
    }
}
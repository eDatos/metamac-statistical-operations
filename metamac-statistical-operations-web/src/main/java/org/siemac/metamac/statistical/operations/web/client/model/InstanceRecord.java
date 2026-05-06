package org.siemac.metamac.statistical.operations.web.client.model;

import org.siemac.metamac.statistical.operations.web.client.model.ds.InstanceDS;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class InstanceRecord extends ListGridRecord {

    public InstanceRecord() {
        super();
    }

    public Long getId() {
        return getAttributeAsLong(InstanceDS.ID);
    }

    public void setId(Long value) {
        setAttribute(InstanceDS.ID, value);
    }

    public String getCode() {
        return getAttributeAsString(InstanceDS.CODE);
    }

    public void setCode(String value) {
        setAttribute(InstanceDS.CODE, value);
    }

    public String getTitle() {
        return getAttributeAsString(InstanceDS.TITLE);
    }

    public void setTitle(String value) {
        setAttribute(InstanceDS.TITLE, value);
    }

    public String getProcStatus() {
        return getAttributeAsString(InstanceDS.PROC_STATUS);
    }

    public void setProcStatus(String value) {
        setAttribute(InstanceDS.PROC_STATUS, value);
    }

    public Integer getOrder() {
        return getAttributeAsInt(InstanceDS.ORDER);
    }

    public void setOrder(Integer value) {
        setAttribute(InstanceDS.ORDER, value);
    }

    public void setUrn(String value) {
        setAttribute(InstanceDS.URN, value);
    }

    public void setAcronym(String value) {
        setAttribute(InstanceDS.ACRONYM, value);
    }

    public void setInstaceType(String value) {
        setAttribute(InstanceDS.INSTANCE_TYPE, value);
    }

    public void setCreatedDate(String value) {
        setAttribute(InstanceDS.CREATED_DATE, value);
    }

    public void setInternalInventoryDate(String value) {
        setAttribute(InstanceDS.INTERNAL_INVENTORY_DATE, value);
    }
    
    public void setDisaggregationBySex(String value) {
        setAttribute(InstanceDS.DISAGGREGATION_BY_SEX, value);
    }
    public void setDisaggregationByAge(String value) {
        setAttribute(InstanceDS.DISAGGREGATION_BY_AGE, value);
    }
    public void setDisaggregationByDisability(String value) {
        setAttribute(InstanceDS.DISAGGREGATION_BY_DISABILITY, value);
    }
    public void setDisaggregationByNationality(String value) {
        setAttribute(InstanceDS.DISAGGREGATION_BY_NATIONALITY, value);
    }
}

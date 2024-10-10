package org.siemac.metamac.statistical.operations.web.client.model;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.operations.core.dto.OfficialityTypeDto;
import org.siemac.metamac.statistical.operations.core.dto.OperationBaseDto;
import org.siemac.metamac.statistical.operations.core.dto.SurveyTypeDto;
import org.siemac.metamac.statistical.operations.core.enume.domain.StreamMessageStatusEnum;
import org.siemac.metamac.statistical.operations.web.client.model.ds.OperationDS;
import org.siemac.metamac.statistical.operations.web.client.utils.CommonUtils;
import org.siemac.metamac.web.common.client.resources.GlobalResources;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class OperationRecord extends ListGridRecord {

    public OperationRecord() {
        super();
    }

    public void setId(Long value) {
        setAttribute(OperationDS.ID, value);
    }

    public Long getId() {
        return getAttributeAsLong(OperationDS.ID);
    }

    public String getCode() {
        return getAttributeAsString(OperationDS.CODE);
    }

    public void setCode(String value) {
        setAttribute(OperationDS.CODE, value);
    }

    public void setUrn(String value) {
        setAttribute(OperationDS.URN, value);
    }

    public void setTitle(String value) {
        setAttribute(OperationDS.TITLE, value);
    }

    public void setAcronym(String value) {
        setAttribute(OperationDS.ACRONYM, value);
    }

    public void setStatisticPlanCode(String value) {
        setAttribute(OperationDS.STATISTIC_PLAN, value);
    }

    public void setProcStatus(String value) {
        setAttribute(OperationDS.PROC_STATUS, value);
    }

    public void setOperationBaseDto(OperationBaseDto operationBaseDto) {
        setAttribute(OperationDS.DTO, operationBaseDto);
    }

    public OperationBaseDto getOperationBaseDto() {
        return (OperationBaseDto) getAttributeAsObject(OperationDS.DTO);
    }

    public void setStatus(String value) {
        setAttribute(OperationDS.STATUS, value);
    }

    public void setIndicatorsSystem(Boolean value) {
        if (value != null && value) {
            setAttribute(OperationDS.INDICATOR_SYSTEM, GlobalResources.RESOURCE.success().getURL());
        }
    }

    public void setSubjectArea(ExternalItemDto value) {
        if (value != null) {
            setAttribute(OperationDS.SUBJECT_AREA, CommonWebUtils.getElementName(value.getCode(), value.getTitle()));
        }
    }

    public void setSurveyType(SurveyTypeDto value) {
        if (value != null) {
            setAttribute(OperationDS.STATISTICAL_OPERATION_TYPE, CommonWebUtils.getElementName(value.getIdentifier(), value.getDescription()));
        }
    }

    public void setOfficialityType(OfficialityTypeDto value) {
        if (value != null) {
            setAttribute(OperationDS.OFFICIALITY_TYPE, CommonWebUtils.getElementName(value.getIdentifier(), value.getDescription()));
        }
    }

    public void setCreatedDate(String value) {
        setAttribute(OperationDS.CREATED_DATE, value);
    }

    public void setInternalInventoryDate(String value) {
        setAttribute(OperationDS.INTERNAL_INVENTORY_DATE, value);
    }

    public void setCurrentlyActive(String value) {
        setAttribute(OperationDS.CURRENTLY_ACTIVE, value);
    }

    public void setPublicationStreamStatus(StreamMessageStatusEnum status) {
        setAttribute(OperationDS.PUBLISH_MSG_STATUS_KAFKA, StreamMessageStatusEnum.PENDING.equals(status) ? null : CommonUtils.getPublicationStreamStatusIcon(status));
    }

    public void setTechnicianInCharge(String value) {
        setAttribute(OperationDS.TECHNICIAN_IN_CHARGE, value);
    }

    public void setAssistantTechnician(String value) {
        setAttribute(OperationDS.ASSISTANT_TECHNICIAN, value);
    }

}

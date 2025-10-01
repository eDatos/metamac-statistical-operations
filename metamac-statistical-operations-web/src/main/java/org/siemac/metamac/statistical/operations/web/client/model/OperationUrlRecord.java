package org.siemac.metamac.statistical.operations.web.client.model;

import org.siemac.metamac.statistical.operations.core.dto.OperationUrlDto;
import org.siemac.metamac.statistical.operations.web.client.model.ds.OperationUrlDS;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class OperationUrlRecord extends ListGridRecord {

    public OperationUrlRecord() {
    }

    public OperationUrlRecord(Long id, String url, Boolean isUrlEditable, OperationUrlDto operationUrlDto) {
        setId(id);
        setUrl(url);
        setIsUrlEditable(isUrlEditable);
        setOperationUrlDto(operationUrlDto);
    }

    public void setId(Long id) {
        setAttribute(OperationUrlDS.ID, id);
    }

    public Long getId() {
        return getAttributeAsLong(OperationUrlDS.ID);
    }

    public void setUrl(String value) {
        setAttribute(OperationUrlDS.URL, value);
    }

    public String getUrl() {
        return getAttributeAsString(OperationUrlDS.URL);
    }

    public void setOperationUrlDto(OperationUrlDto value) {
        setAttribute(OperationUrlDS.OPERATION_URL_DTO, value);
    }

    public OperationUrlDto getOperationUrlDto() {
        return (OperationUrlDto) getAttributeAsObject(OperationUrlDS.OPERATION_URL_DTO);
    }

    public void setIsUrlEditable(Boolean value) {
        setAttribute(OperationUrlDS.IS_URL_EDITABLE, value);
    }

    public Boolean getIsUrlEditable() {
        return getAttributeAsBoolean(OperationUrlDS.IS_URL_EDITABLE);
    }
}

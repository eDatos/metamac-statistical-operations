package org.siemac.metamac.statistical.operations.web.client.model;

import org.siemac.metamac.statistical.operations.core.dto.OperationUrlDto;
import org.siemac.metamac.statistical.operations.web.client.model.ds.OperationUrlDS;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class OperationUrlRecord extends ListGridRecord {

	public OperationUrlRecord() {
	}

	public OperationUrlRecord(Long id, String url, OperationUrlDto operationUrlDto, String name) {
		setId(id);
		setUrl(url);
		setOperationUrlDto(operationUrlDto);
		setName(name);
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

	public void setName(String value) {
		setAttribute(OperationUrlDS.URL_NAME, value);
	}

	public String getName() {
		return getAttributeAsString(OperationUrlDS.URL_NAME);
	}

	public OperationUrlDto getOperationUrlDto() {
		return (OperationUrlDto) getAttributeAsObject(OperationUrlDS.OPERATION_URL_DTO);
	}

}

package org.siemac.metamac.statistical.operations.web.client.model.ds;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;

public class OperationUrlDS extends DataSource {

    public static final String ID                  = "op-url-id";

    public static final String URL                 = "op-url-url";
    public static final String OPERATION_URL_DTO      = "op-url-dto";

    public OperationUrlDS() {
        DataSourceIntegerField id = new DataSourceIntegerField(ID, "identifier");
        id.setPrimaryKey(true);
        addField(id);
    }
}

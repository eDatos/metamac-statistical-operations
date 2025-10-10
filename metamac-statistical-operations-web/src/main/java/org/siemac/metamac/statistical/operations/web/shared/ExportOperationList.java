package org.siemac.metamac.statistical.operations.web.shared;

import org.siemac.metamac.statistical.operations.web.shared.criteria.OperationCriteria;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class ExportOperationList {

    @In(1)
    OperationCriteria operation;

    @Out(1)
    String fileName;

}

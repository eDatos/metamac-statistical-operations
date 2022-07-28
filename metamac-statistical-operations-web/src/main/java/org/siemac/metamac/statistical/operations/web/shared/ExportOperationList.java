package org.siemac.metamac.statistical.operations.web.shared;

import java.util.List;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;



@GenDispatch(isSecure = false)
public class ExportOperationList {

    @In(1) List<Long> idOperations;

    @Out(1)
    String fileName;


}

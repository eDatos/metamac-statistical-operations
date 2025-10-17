package org.siemac.metamac.statistical.operations.web.shared;

import java.util.List;

import org.siemac.metamac.statistical.operations.core.dto.OperationBaseDto;
import org.siemac.metamac.statistical.operations.web.shared.criteria.OperationCriteria;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetOperationPaginatedList {

    @In(1)
    int                    firstResult;

    @In(2)
    int                    maxResults;

    @In(3)
    OperationCriteria       operation;

    @Out(1)
    List<OperationBaseDto> operationBaseDtos;

    @Out(2)
    int                    firstResultOut;

    @Out(3)
    int                    totalResults;

}

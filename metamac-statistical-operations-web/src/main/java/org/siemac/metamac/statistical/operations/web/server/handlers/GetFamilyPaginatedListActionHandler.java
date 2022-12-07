package org.siemac.metamac.statistical.operations.web.server.handlers;

import org.siemac.metamac.core.common.criteria.MetamacCriteria;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.operations.core.dto.FamilyBaseDto;
import org.siemac.metamac.statistical.operations.core.serviceapi.StatisticalOperationsServiceFacade;
import org.siemac.metamac.statistical.operations.web.server.handlers.utils.HandlersCriteriaUtils;
import org.siemac.metamac.statistical.operations.web.shared.GetFamilyPaginatedListAction;
import org.siemac.metamac.statistical.operations.web.shared.GetFamilyPaginatedListResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetFamilyPaginatedListActionHandler extends SecurityActionHandler<GetFamilyPaginatedListAction, GetFamilyPaginatedListResult> {

    @Autowired
    private StatisticalOperationsServiceFacade statisticalOperationsServiceFacade;

    public GetFamilyPaginatedListActionHandler() {
        super(GetFamilyPaginatedListAction.class);
    }

    @Override
    public GetFamilyPaginatedListResult executeSecurityAction(GetFamilyPaginatedListAction action) throws ActionException {
        try {
            MetamacCriteria criteria = HandlersCriteriaUtils.defaultCriteriaPaginator(action.getFirstResult(), action.getMaxResults());

            // Order
            HandlersCriteriaUtils.defaultCriteriaOrder(criteria);
            // Restriction
            HandlersCriteriaUtils.defaultCriteriaDisjunctionRestrictionFamily(criteria, action.getFamily());

            MetamacCriteriaResult<FamilyBaseDto> result = statisticalOperationsServiceFacade.findFamilyByCondition(ServiceContextHolder.getCurrentServiceContext(), criteria);
            return new GetFamilyPaginatedListResult(result.getResults(), result.getPaginatorResult().getFirstResult(), result.getPaginatorResult().getTotalResults());
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}

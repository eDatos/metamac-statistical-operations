package org.siemac.metamac.statistical.operations.web.shared.criteria;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.criteria.shared.MetamacCriteriaOrder;
import org.siemac.metamac.statistical.operations.web.client.operation.presenter.OperationListPresenter;
import org.siemac.metamac.web.common.client.constants.CommonWebConstants;
import org.siemac.metamac.web.common.shared.criteria.PaginationWebCriteria;

import es.gobcan.istac.indicators.core.enume.domain.IndicatorProcStatusEnum;
import es.gobcan.istac.indicators.web.client.utils.IndicatorsWebConstants;

public class OperationCriteria extends PaginationWebCriteria {

    private static final long          serialVersionUID = -6655051147299387214L;

    private String                     code;
    private String                     title;

    private List<MetamacCriteriaOrder> orders           = new ArrayList<MetamacCriteriaOrder>();

    public OperationCriteria() {
        setFirstResult(OperationListPresenter.OPERATION_LIST_FIRST_RESULT);
        setMaxResults(CommonWebConstants.MAIN_LIST_MAX_RESULTS);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MetamacCriteriaOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<MetamacCriteriaOrder> orders) {
        this.orders = orders;
    }


    public String getCategoryElementCode() {
        return categoryElementCode;
    }

    public void setCategoryElementCode(String categoryElementCode) {
        this.categoryElementCode = categoryElementCode;
    }
}

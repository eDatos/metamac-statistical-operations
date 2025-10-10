package org.siemac.metamac.statistical.operations.web.shared.criteria;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.criteria.shared.MetamacCriteriaOrder;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public class OperationCriteria extends MetamacWebCriteria {

    private static final long serialVersionUID = -6655051147299387214L;

    private String code;
    private String title;
    private String status;

    private String disaggregationBySex;
    private String disaggregationByAge;
    private String disaggregationByNationality;
    private String disaggregationByDisability;

    private List<MetamacCriteriaOrder> orders = new ArrayList<MetamacCriteriaOrder>();

    public OperationCriteria() {
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

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getDisaggregationBySex() {
        return disaggregationBySex;
    }
    public void setDisaggregationBySex(String disaggregationBySex) {
        this.disaggregationBySex = disaggregationBySex;
    }

    public String getDisaggregationByAge() {
        return disaggregationByAge;
    }
    public void setDisaggregationByAge(String disaggregationByAge) {
        this.disaggregationByAge = disaggregationByAge;
    }

    public String getDisaggregationByNationality() {
        return disaggregationByNationality;
    }
    public void setDisaggregationByNationality(String disaggregationByNationality) {
        this.disaggregationByNationality = disaggregationByNationality;
    }

    public String getDisaggregationByDisability() {
        return disaggregationByDisability;
    }
    public void setDisaggregationByDisability(String disaggregationByDisability) {
        this.disaggregationByDisability = disaggregationByDisability;
    }

    public List<MetamacCriteriaOrder> getOrders() {
        return orders;
    }
    public void setOrders(List<MetamacCriteriaOrder> orders) {
        this.orders = orders;
    }
}

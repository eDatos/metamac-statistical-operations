package org.siemac.metamac.statistical.operations.web.shared.criteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.siemac.metamac.core.common.criteria.shared.MetamacCriteriaOrder;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public class OperationCriteria extends MetamacWebCriteria {

    private static final long          serialVersionUID = -6655051147299387214L;

    private String                     code;
    private String                     title;
    private String                     status;
    private String                     edatosMigrationStatus;

    private String                     officialityType;
    private Date                       newnessUntilDate;
    private Date                       featuredUntilDate;

    private List<MetamacCriteriaOrder> orders           = new ArrayList<MetamacCriteriaOrder>();

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


    public List<MetamacCriteriaOrder> getOrders() {
        return orders;
    }
    public void setOrders(List<MetamacCriteriaOrder> orders) {
        this.orders = orders;
    }

    public String getEdatosMigrationStatus() {
        return edatosMigrationStatus;
    }
    public void setEdatosMigrationStatus(String edatosMigrationStatus) {
        this.edatosMigrationStatus = edatosMigrationStatus;
    }

    public String getOfficialityType() {
        return officialityType;
    }

    public void setOfficialityType(String officialityType) {
        this.officialityType = officialityType;
    }
    public Date getNewnessUntilDate() {
        return newnessUntilDate;
    }
    public void setNewnessUntilDate(Date newnessUntilDate) {
        this.newnessUntilDate = newnessUntilDate;
    }
    public Date getFeaturedUntilDate() {
        return featuredUntilDate;
    }
    public void setFeaturedUntilDate(Date featuredUntilDate) {
        this.featuredUntilDate = featuredUntilDate;
    }
}

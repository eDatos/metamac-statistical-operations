package org.siemac.metamac.statistical.operations.web.client.widgets;

import static org.siemac.metamac.statistical.operations.web.client.OperationsWeb.getConstants;

import org.siemac.metamac.core.common.criteria.shared.MetamacCriteriaOrder;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.operations.web.client.model.ds.InstanceDS;
import org.siemac.metamac.statistical.operations.web.client.operation.view.handlers.OperationListUiHandlers;
import org.siemac.metamac.statistical.operations.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.operations.web.shared.criteria.OperationCriteria;
import org.siemac.metamac.web.common.client.MetamacWebCommon;
import org.siemac.metamac.web.common.client.widgets.BaseAdvancedSearchSectionStack;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomButtonItem;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

public class OperationsSearchSectionStack extends BaseAdvancedSearchSectionStack {

    private OperationListUiHandlers    uiHandlers;

   // private CategoryElementSelectItem categoryElementSelectItem;

    public OperationsSearchSectionStack() {
    }

    @Override
    protected void createAdvancedSearchForm() {
        advancedSearchForm = new GroupDynamicForm(StringUtils.EMPTY);
        advancedSearchForm.setPadding(5);
        advancedSearchForm.setMargin(5);
        advancedSearchForm.setVisible(false);
        TextItem title = new TextItem(InstanceDS.TITLE, "titulo");
        TextItem code = new TextItem(InstanceDS.CODE, "codigo");

//        TextItem title = new TextItem(IndicatorDS.TITLE, getConstants().indicDetailTitle());
//        categoryElementSelectItem = new CategoryElementSelectItem(advancedSearchForm, true, null);
//        SelectItem productionVersionProcStatus = new SelectItem(IndicatorDS.PROC_STATUS, getConstants().indicatorProductionEnvironmentProcStatus());
//        productionVersionProcStatus.setValueMap(CommonUtils.getProcStatusValueMap());
//        SelectItem diffusionVersionProcStatus = new SelectItem(IndicatorDS.PROC_STATUS_DIFF, getConstants().indicatorDiffusionEnvironmentProcStatus());
//        diffusionVersionProcStatus.setValueMap(CommonUtils.getProcStatusValueMap());
//
//        SelectItem orderBy = new SelectItem(IndicatorDS.ORDER_BY, getConstants().orderBy());
//        orderBy.setValueMap(CommonUtils.getIndicatorOrderValueMap());
//        orderBy.setStartRow(true);
//        orderBy.setWidth(200);
//        orderBy.addChangedHandler(FormItemUtils.getMarkForRedrawChangedHandler(advancedSearchForm));
//
        SelectItem orderType = new SelectItem(InstanceDS.ORDER, getConstants().instanceOrder());
        orderType.setValueMap(CommonUtils.getOrderTypeValueMap());
        orderType.setDefaultValue(MetamacCriteriaOrder.OrderTypeEnum.ASC.name());
        orderType.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return !StringUtils.isBlank(form.getValueAsString(InstanceDS.ORDER));
            }
        });
//
        CustomButtonItem searchItem = new CustomButtonItem(ADVANCED_SEARCH_ITEM_NAME, MetamacWebCommon.getConstants().search());
        searchItem.setColSpan(4);
        searchItem.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                retrieveResources();
            }
        });


        SelectItem disaggregationBySex = new SelectItem(InstanceDS.DISAGGREGATION_BY_SEX, getConstants().operationDisaggregationBySex());
        disaggregationBySex.setValueMap(CommonUtils.getYesOrNoValueMap());

        SelectItem disaggregationByAge = new SelectItem(InstanceDS.DISAGGREGATION_BY_AGE, getConstants().operationDisaggregationByAge());
        disaggregationByAge.setValueMap(CommonUtils.getYesOrNoValueMap());

        SelectItem disaggregationByNationality = new SelectItem(InstanceDS.DISAGGREGATION_BY_NATIONALITY, getConstants().operationDisaggregationByNationality());
        disaggregationByNationality.setValueMap(CommonUtils.getYesOrNoValueMap());

        SelectItem disaggregationByDisability = new SelectItem(InstanceDS.DISAGGREGATION_BY_DISABILITY, getConstants().operationDisaggregationByDisability());
        disaggregationByDisability.setValueMap(CommonUtils.getYesOrNoValueMap());

        FormItem[] advancedSearchFormItems = new FormItem[]{code, title, disaggregationBySex, disaggregationByAge, disaggregationByNationality, disaggregationByDisability, searchItem};
        setFormItemsInAdvancedSearchForm(advancedSearchFormItems);
    }

    @Override
    protected void retrieveResources() {
        //getUiHandlers().retrieveOperationList(getSearchCriteria().toString());
    }

    @Override
    protected void showAdvancedSearchSection() {
        super.showAdvancedSearchSection();
    }

    public OperationCriteria getSearchCriteria() {
        OperationCriteria criteria = new OperationCriteria();
        criteria.setCriteria(searchForm.getValueAsString(SEARCH_ITEM_NAME));
        criteria.setCode(advancedSearchForm.getValueAsString(InstanceDS.CODE));
        criteria.setTitle(advancedSearchForm.getValueAsString(InstanceDS.TITLE));


//        IndicatorCriteriaOrderEnum indicatorCriteriaOrderEnum = CommonUtils.getIndicatorCriteriaOrderEnum(advancedSearchForm.getValueAsString(IndicatorDS.ORDER_BY));
//        if (indicatorCriteriaOrderEnum != null) {
//            OrderTypeEnum orderTypeEnum = CommonUtils.getOrderTypeEnum(advancedSearchForm.getValueAsString(IndicatorDS.ORDER_TYPE));
//            criteria.setOrders(ClientCriteriaUtils.buildCriteriaOrder(orderTypeEnum, indicatorCriteriaOrderEnum));
//        }
//
        return criteria;
    }


//    public void setCategoryElementExternalItem(List<ExternalItemDto> categoryElementExternalItem, int firstResult, int totalResults) {
//        categoryElementSelectItem.setCategoryElementExternalItem(categoryElementExternalItem, firstResult, totalResults);
//    }

    public void setUiHandlers(OperationListUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
//        categoryElementSelectItem.setUiHandlers(uiHandlers);
    }

    public OperationListUiHandlers getUiHandlers() {
        return uiHandlers;
    }
}

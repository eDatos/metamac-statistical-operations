package org.siemac.metamac.statistical.operations.web.client.widgets;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.operations.web.client.model.ds.InstanceDS;
import org.siemac.metamac.statistical.operations.web.client.operation.view.handlers.OperationListUiHandlers;
import org.siemac.metamac.statistical.operations.web.shared.criteria.OperationCriteria;
import org.siemac.metamac.web.common.client.MetamacWebCommon;
import org.siemac.metamac.web.common.client.widgets.BaseAdvancedSearchSectionStack;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomButtonItem;

import com.smartgwt.client.widgets.form.fields.FormItem;
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
        TextItem title = new TextItem();

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
//        SelectItem orderType = new SelectItem(IndicatorDS.ORDER_TYPE, getConstants().orderType());
//        orderType.setValueMap(CommonUtils.getOrderTypeValueMap());
//        orderType.setDefaultValue(OrderTypeEnum.ASC.name());
//        orderType.setShowIfCondition(new FormItemIfFunction() {
//
//            @Override
//            public boolean execute(FormItem item, Object value, DynamicForm form) {
//                return !StringUtils.isBlank(form.getValueAsString(IndicatorDS.ORDER_BY));
//            }
//        });
//
        CustomButtonItem searchItem = new CustomButtonItem(ADVANCED_SEARCH_ITEM_NAME, MetamacWebCommon.getConstants().search());
        searchItem.setColSpan(4);
        searchItem.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                retrieveResources();
            }
        });
//
//        SelectItem notifyPopulationErrors = new SelectItem(IndicatorDS.NOTIFY_POPULATION_ERRORS, getConstants().indicDetailNotifyPopulationErrors());
//        notifyPopulationErrors.setValueMap(CommonUtils.getIndicatorNotifyPopulationErrorsValueMap());
//        notifyPopulationErrors.setWidth(200);
//
//        SelectItem mainIndicator = new SelectItem(IndicatorDS.MAIN_INDICATOR, getConstants().indicatorMain());
//        mainIndicator.setValueMap(CommonUtils.getYesOrNoValueMap());

        FormItem[] advancedSearchFormItems = new FormItem[]{title, searchItem};
        setFormItemsInAdvancedSearchForm(advancedSearchFormItems);
    }

    @Override
    protected void retrieveResources() {
        getUiHandlers().retrieveOperationList(getSearchCriteria());
//        getUiHandlers().retrieveOperationList(getIndicatorCriteria());
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

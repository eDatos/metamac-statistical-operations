package org.siemac.metamac.statistical.operations.web.client.widgets;

import static org.siemac.metamac.statistical.operations.web.client.OperationsWeb.getConstants;

import java.util.List;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.operations.core.dto.OfficialityTypeDto;
import org.siemac.metamac.statistical.operations.web.client.model.ds.OperationDS;
import org.siemac.metamac.statistical.operations.web.client.operation.presenter.OperationListPresenter;
import org.siemac.metamac.statistical.operations.web.client.operation.view.handlers.OperationListUiHandlers;
import org.siemac.metamac.statistical.operations.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.operations.web.client.utils.OperationsListUtils;
import org.siemac.metamac.statistical.operations.web.shared.criteria.OperationCriteria;
import org.siemac.metamac.web.common.client.MetamacWebCommon;
import org.siemac.metamac.web.common.client.constants.CommonWebConstants;
import org.siemac.metamac.web.common.client.widgets.BaseAdvancedSearchSectionStack;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomButtonItem;

import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

public class OperationsSearchSectionStack extends BaseAdvancedSearchSectionStack {

    private OperationListUiHandlers uiHandlers;

    SelectItem                      officialityTypes;
    List<OfficialityTypeDto>        officialityTypeDtos;

    public OperationsSearchSectionStack() {

    }

    public void clearSearchSection() {
        searchForm.clearValues();
        clearAndHideAdvancedSearchSection();
    }
    @Override
    protected void createAdvancedSearchForm() {
        advancedSearchForm = new GroupDynamicForm(StringUtils.EMPTY);
        advancedSearchForm.setPadding(5);
        advancedSearchForm.setMargin(5);
        advancedSearchForm.setVisible(false);
        TextItem title = new TextItem(OperationDS.TITLE, getConstants().operationTitle());
        TextItem code = new TextItem(OperationDS.CODE, getConstants().operationCode());
        SelectItem productionVersionProcStatus = new SelectItem(OperationDS.STATUS, getConstants().operationStatus());
        productionVersionProcStatus.setValueMap(CommonUtils.getStatusEnumHashMap());
        SelectItem edatosMigrationStatus = new SelectItem(OperationDS.EDATOS_MIGRATION_STATUS, getConstants().edatosMigrationStatus());
        edatosMigrationStatus.setValueMap(CommonUtils.getEdatosMigrationStatusEnumHashMap());

        CustomButtonItem searchItem = new CustomButtonItem(ADVANCED_SEARCH_ITEM_NAME, MetamacWebCommon.getConstants().search());
        searchItem.setColSpan(4);
        searchItem.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                retrieveResources();
            }
        });

        officialityTypes = new SelectItem(OperationDS.OFFICIALITY_TYPE, getConstants().operationOfficialityType());

        FormItem[] advancedSearchFormItems = new FormItem[]{code, title, productionVersionProcStatus, edatosMigrationStatus,officialityTypes, searchItem};
        setFormItemsInAdvancedSearchForm(advancedSearchFormItems);
    }

    @Override
    protected void retrieveResources() {
        getUiHandlers().retrieveOperationList(OperationListPresenter.OPERATION_LIST_FIRST_RESULT, CommonWebConstants.MAIN_LIST_MAX_RESULTS, getSearchCriteria());

    }

    @Override
    protected void showAdvancedSearchSection() {
        super.showAdvancedSearchSection();
    }

    public OperationCriteria getSearchCriteria() {
        OperationCriteria criteria = new OperationCriteria();

        criteria.setCriteria(searchForm.getValueAsString(SEARCH_ITEM_NAME));

        criteria.setCode(advancedSearchForm.getValueAsString(OperationDS.CODE));
        criteria.setTitle(advancedSearchForm.getValueAsString(OperationDS.TITLE));
        criteria.setStatus(advancedSearchForm.getValueAsString(OperationDS.STATUS));
        criteria.setEdatosMigrationStatus(advancedSearchForm.getValueAsString(OperationDS.EDATOS_MIGRATION_STATUS));


        OfficialityTypeDto officialityTypeDto = OperationsListUtils.getOfficialityTypeDto(advancedSearchForm.getValueAsString(OperationDS.OFFICIALITY_TYPE), this.officialityTypeDtos);
        if (officialityTypeDto != null) {
            criteria.setOfficialityType(officialityTypeDto.getIdentifier());
        }

        return criteria;
    }

    public void setUiHandlers(OperationListUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    public OperationListUiHandlers getUiHandlers() {
        return uiHandlers;
    }

    public void setOperationsLists(List<OfficialityTypeDto> officialityTypeDtos) {
        this.officialityTypeDtos = officialityTypeDtos;
        officialityTypes.setValueMap(OperationsListUtils.getOfficialityTypeHashMap(officialityTypeDtos));
    }
}

package org.siemac.metamac.statistical.operations.web.client.widgets;

import static org.siemac.metamac.statistical.operations.web.client.OperationsWeb.getConstants;

import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.statistical.operations.core.dto.OperationDto;
import org.siemac.metamac.statistical.operations.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.operations.web.client.constants.StatisticalOperationsWebConstants;
import org.siemac.metamac.statistical.operations.web.client.utils.CommonUtils;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.view.handlers.SrmExternalResourcesUiHandlers;
import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomButtonItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomCheckboxItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.SearchSrmItemLinkItemWithSchemeFilterItem;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;

import com.smartgwt.client.types.FormErrorOrientation;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.form.fields.events.HasClickHandlers;

public class NewOperationForm extends CustomDynamicForm {

    private static final int                          FORM_ITEM_CUSTOM_WIDTH = 300;

    private RequiredTextItem                          identifier;
    private RequiredTextItem                          title;
    private SearchSrmItemLinkItemWithSchemeFilterItem subjectAreasItem;
    private CustomCheckboxItem                        indSystem;
    private CustomButtonItem                          saveButton;

    private SrmExternalResourcesUiHandlers            uiHandlers;

    public NewOperationForm() {
        super();

        setValidateOnChange(true);

        identifier = new RequiredTextItem("op-id", getConstants().operationCode());
        identifier.setWidth(FORM_ITEM_CUSTOM_WIDTH);
        identifier.setValidators(CommonWebUtils.getSemanticIdentifierCustomValidator(), CommonUtils.getOperationCodeLengthValidator());

        title = new RequiredTextItem("op-title", getConstants().operationTitle());
        title.setWidth(FORM_ITEM_CUSTOM_WIDTH);

        subjectAreasItem = createSubjectAreaItem("op-subject", getConstants().operationSubjectArea());
        subjectAreasItem.setRequired(true);

        indSystem = new CustomCheckboxItem("op-ind-sys", getConstants().operationIndicatorSystem());
        indSystem.setWidth(FORM_ITEM_CUSTOM_WIDTH);
        indSystem.setTitleStyle("requiredFormLabel");

        saveButton = new CustomButtonItem("op-save", getConstants().actionCreateOperation());
        saveButton.setWidth(110);

        setHeight100();
        setWidth100();
        setPadding(5);
        setMargin(5);
        setErrorOrientation(FormErrorOrientation.RIGHT);
        setLayoutAlign(VerticalAlignment.BOTTOM);
        setFields(identifier, title, subjectAreasItem, indSystem, saveButton);
    }

    public OperationDto getOperation() {
        OperationDto operationDto = new OperationDto();
        operationDto.setCode(identifier.getValueAsString());
        operationDto.setTitle(InternationalStringUtils.updateInternationalString(new InternationalStringDto(), title.getValueAsString()));
        operationDto.setReleaseCalendar(false);
        operationDto.setProcStatus(ProcStatusEnum.DRAFT);
        operationDto.setSubjectArea(subjectAreasItem.getExternalItemDto());
        operationDto.setIndicatorSystem(indSystem.getValueAsBoolean() == null ? false : indSystem.getValueAsBoolean());
        return operationDto;
    }

    public HasClickHandlers getSave() {
        return saveButton;
    }

    public void setUiHandlers(SrmExternalResourcesUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    // ------------------------------------------------------------------------------------------------------------
    // EXTERNAL RESOURCES DATA SETTERS
    // ------------------------------------------------------------------------------------------------------------

    public void setItemSchemes(String formItemName, ExternalItemsResult result) {
        subjectAreasItem.setFilterResources(result.getExternalItemDtos(), result.getFirstResult(), result.getExternalItemDtos().size(), result.getTotalResults());
    }

    public void setItems(String formItemName, ExternalItemsResult result) {
        subjectAreasItem.setResources(result.getExternalItemDtos(), result.getFirstResult(), result.getExternalItemDtos().size(), result.getTotalResults());
    }

    private SearchSrmItemLinkItemWithSchemeFilterItem createSubjectAreaItem(final String name, String title) {
        final SearchSrmItemLinkItemWithSchemeFilterItem item = new SearchSrmItemLinkItemWithSchemeFilterItem(name, title, StatisticalOperationsWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveItems(int firstResult, int maxResults, SrmItemRestCriteria webCriteria) {
                webCriteria.setExternalArtifactType(TypeExternalArtefactsEnum.CATEGORY);
                uiHandlers.retrieveItems(name, webCriteria, firstResult, maxResults);
            }

            @Override
            protected void retrieveItemSchemes(int firstResult, int maxResults, SrmExternalResourceRestCriteria webCriteria) {
                webCriteria.setExternalArtifactType(TypeExternalArtefactsEnum.CATEGORY_SCHEME);
                uiHandlers.retrieveItemSchemes(name, webCriteria, firstResult, maxResults);
            }
        };
        return item;
    }
}

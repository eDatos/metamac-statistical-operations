package org.siemac.metamac.statistical.operations.web.client.widgets;

import static org.siemac.metamac.statistical.operations.web.client.OperationsWeb.getConstants;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.siemac.metamac.statistical.operations.core.dto.OperationUrlDto;
import org.siemac.metamac.statistical.operations.web.client.model.OperationUrlRecord;
import org.siemac.metamac.statistical.operations.web.client.model.ds.OperationUrlDS;
import org.siemac.metamac.statistical.operations.web.client.resources.GlobalResources;
import org.siemac.metamac.statistical.operations.web.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.MetamacWebCommon;
import org.siemac.metamac.web.common.client.utils.ApplicationEditionLanguages;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.utils.UrlUtils;

import com.google.gwt.resources.client.ImageResource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.EditCompleteEvent;
import com.smartgwt.client.widgets.grid.events.EditCompleteHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class OperationUrlsPanel extends VLayout {

    private static String         REMOVE_FIELD_NAME = "remove-field";

    private OperationUrlsListGrid listGrid;
    private DynamicForm           form;
    private SelectItem            selectItem;
    private boolean               translationsShowed;

    private boolean               viewMode;

    private Img                   operationUrlImg;
    private Img                   addOperationUrlImg;

    public OperationUrlsPanel(boolean viewMode) {
        super();
        this.viewMode = viewMode;

        HLayout imgLayout = new HLayout();
        imgLayout.setBorder("1px solid #A7ABB4");
        imgLayout.setMembersMargin(10);
        imgLayout.setAutoHeight();
        imgLayout.setBackgroundColor("#fff");
        imgLayout.setLayoutMargin(5);
        imgLayout.setStyleName("operationUrlPanel");

        // OperationUrl icon
        operationUrlImg = new Img(GlobalResources.RESOURCE.link().getURL());
        operationUrlImg.setTooltip(getConstants().statisticalOperationUrls());
        operationUrlImg.setSize(20);
        operationUrlImg.setAlign(Alignment.LEFT);
        imgLayout.addMember(operationUrlImg);

        // Add operationUrl icon
        addOperationUrlImg = new Img(GlobalResources.RESOURCE.addLink().getURL());
        addOperationUrlImg.setTooltip(getConstants().addStatisticalOperationUrl());
        addOperationUrlImg.setCursor(Cursor.POINTER);
        addOperationUrlImg.setName("note-img");
        addOperationUrlImg.setSize(20);
        addOperationUrlImg.setAlign(Alignment.LEFT);
        addOperationUrlImg.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                listGrid.startEditingNew();
            }
        });
        imgLayout.addMember(addOperationUrlImg);

        // Show translations form

        form = new DynamicForm();
        form.setAutoHeight();
        selectItem = new SelectItem("lang");
        selectItem.setShowTitle(false);
        LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> valueIcons = new LinkedHashMap<String, String>();
        for (final String locale : ApplicationEditionLanguages.getLocales()) {
            String iconSrc = ((ImageResource) org.siemac.metamac.web.common.client.resources.GlobalResources.RESOURCE.getResource(locale)).getURL();
            valueMap.put(locale, MetamacWebCommon.getEnumConstants().getString(MetamacWebCommon.getEnumConstants().locale() + locale)); // Set locale name (English, Spanish...)
            valueIcons.put(locale, iconSrc);
        }
        selectItem.setValueMap(valueMap);
        selectItem.setValueIcons(valueIcons);
        selectItem.setValue(ApplicationEditionLanguages.getCurrentLocale());
        selectItem.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return translationsShowed;
            }
        });
        selectItem.addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                if (event.getValue() != null && event.getValue() instanceof String) {
                    String selectedLocale = (String) event.getValue();
                    changeOperationUrlsLanguage(selectedLocale);
                }
            }
        });
        form.setFields(selectItem);
        imgLayout.addMember(form);

        // OperationUrls list

        listGrid = new OperationUrlsListGrid();
        listGrid.setCanFocus(false);
        listGrid.setAutoFitMaxRecords(10);
        listGrid.setAutoFitData(Autofit.VERTICAL);
        listGrid.setShowRowNumbers(true);
        listGrid.setLeaveScrollbarGap(false);
        listGrid.setAlternateRecordStyles(false);
        listGrid.setAnimateRollUnder(true);
        listGrid.setSelectionType(SelectionStyle.SIMPLE);
        listGrid.setShowSelectionCanvas(true);
        listGrid.setAnimateSelectionUnder(true);
        listGrid.setWrapCells(true);
        listGrid.setBorder("1px solid #A7ABB4");
        listGrid.setEditEvent(ListGridEditEvent.CLICK);
        listGrid.setCanEdit(!viewMode);
        listGrid.setCanRemoveRecords(!viewMode);
        listGrid.setRemoveFieldTitle(getConstants().actionDelete());
        listGrid.setRemoveIcon(org.siemac.metamac.web.common.client.resources.GlobalResources.RESOURCE.deleteListGrid().getURL());
        listGrid.setRemoveIconSize(14);
        listGrid.setShowHeaderContextMenu(false); // Do not show menu options (avoid to show remove field column)
        listGrid.setShowAllRecords(true);
        listGrid.addEditCompleteHandler(new EditCompleteHandler() {
            @Override
            public void onEditComplete(EditCompleteEvent event) {
                if (event.getNewValues() != null && event.getNewValues().size() > 0) {
                    Record record = listGrid.getRecord(event.getRowNum());
                    OperationUrlDto operationUrlDto = new OperationUrlDto();
                    if (record.getAttributeAsObject(OperationUrlDS.OPERATION_URL_DTO) != null && record.getAttributeAsObject(OperationUrlDS.OPERATION_URL_DTO) instanceof OperationUrlDto) {
                        operationUrlDto = (OperationUrlDto) record.getAttributeAsObject(OperationUrlDS.OPERATION_URL_DTO);
                    }
                    if (event.getNewValues().containsKey(OperationUrlDS.URL)) {
                        String locale = translationsShowed ? selectItem.getValueAsString() : ApplicationEditionLanguages.getCurrentLocale();
                        String rawUrl = (String) event.getNewValues().get(OperationUrlDS.URL);

                        String normalizedUrl = null;
                        if (rawUrl != null && !rawUrl.trim().isEmpty()) {
                            normalizedUrl = UrlUtils.addHttpPrefixIfNeeded(rawUrl);
                            if (!CommonWebUtils.isValidUrl(normalizedUrl)) {
                                normalizedUrl = null;
                            }
                        }
                        operationUrlDto.setUrl(InternationalStringUtils.updateInternationalString(locale, operationUrlDto.getUrl(), normalizedUrl));
                    }

                    listGrid.getRecord(event.getRowNum()).setAttribute(OperationUrlDS.OPERATION_URL_DTO, operationUrlDto);
                }
            }
        });

        // ListGrid fields

        ListGridField urlField = new ListGridField(OperationUrlDS.URL, getConstants().statisticalOperationUrlText());
        urlField.setShowHover(true);
        urlField.setType(ListGridFieldType.LINK);
        urlField.setHoverCustomizer(new HoverCustomizer() {

            @Override
            public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
                return record.getAttribute(OperationUrlDS.URL);
            }
        });
        urlField.setWidth("50%");
        urlField.setValidators(CommonWebUtils.getUrlValidator());

        ListGridField removeField = new ListGridField(REMOVE_FIELD_NAME, getConstants().actionDelete());
        removeField.setIsRemoveField(true);

        listGrid.setFields(urlField, removeField);
        // ListGrid style

        Canvas rollUnderCanvasProperties = new Canvas();
        rollUnderCanvasProperties.setAnimateFadeTime(600);
        rollUnderCanvasProperties.setAnimateShowEffect(AnimationEffect.FADE);
        rollUnderCanvasProperties.setBackgroundColor("#ffe973");
        rollUnderCanvasProperties.setOpacity(50);
        listGrid.setRollUnderCanvasProperties(rollUnderCanvasProperties);
        Canvas background = new Canvas();
        background.setBackgroundColor("#FFFFE0");
        listGrid.setBackgroundComponent(background);

        addMember(imgLayout);
        addMember(listGrid);
    }

    public void setOperationUrls(List<OperationUrlDto> operationUrls) {
        // Clear Operation URLs
        listGrid.selectAllRecords();
        listGrid.removeSelectedData();
        listGrid.deselectAllRecords();
        String selectedLocale = ApplicationEditionLanguages.getCurrentLocale();
        if (selectItem.getValueAsString() != null && !selectItem.getValueAsString().isEmpty()) {
            selectedLocale = selectItem.getValueAsString();
        }

        // Set  Operation URLs in the selected locale
        for (OperationUrlDto operationUrlDto : operationUrls) {
            OperationUrlRecord record = RecordUtils.getOperationUrlRecord(operationUrlDto, selectedLocale);
            listGrid.addData(record);
        }

        // Show/hide Add and Remove icons
        setCanAddOrRemoveOperationUrls(viewMode);
    }

    public Set<OperationUrlDto> getOperationUrls() {
        Set<OperationUrlDto> operationsUrls = new HashSet<OperationUrlDto>();
        ListGridRecord[] records = listGrid.getRecords();
        for (int i = 0; i < records.length; i++) {
            OperationUrlDto annotationDto = (OperationUrlDto) records[i].getAttributeAsObject(OperationUrlDS.OPERATION_URL_DTO);
            operationsUrls.add(annotationDto);
        }
        return operationsUrls;
    }

    public void setTranslationsShowed(boolean translationsShowed) {
        this.translationsShowed = translationsShowed;
        form.markForRedraw();
        // Show operationUrls in current locale
        if (!ApplicationEditionLanguages.getCurrentLocale().equals(selectItem.getValueAsString())) {
            changeOperationUrlsLanguage(ApplicationEditionLanguages.getCurrentLocale());
        }
        if (translationsShowed) {
            selectItem.setValue(ApplicationEditionLanguages.getCurrentLocale());
        }
    }

    private void changeOperationUrlsLanguage(String locale) {
        for (int i = 0; i < listGrid.getRecords().length; i++) {
            if (listGrid.getRecord(i).getAttribute(OperationUrlDS.OPERATION_URL_DTO) != null) {
                OperationUrlDto operationUrlDto = (OperationUrlDto) listGrid.getRecord(i).getAttributeAsObject(OperationUrlDS.OPERATION_URL_DTO);
                listGrid.getRecord(i).setAttribute(OperationUrlDS.URL, InternationalStringUtils.getLocalisedString(operationUrlDto.getUrl(), locale));
            }
        }
        listGrid.redraw();
    }

    private void setCanAddOrRemoveOperationUrls(boolean viewMode) {
        addOperationUrlImg.hide();
        operationUrlImg.hide();

        if (!viewMode) {
            // URL can be created: edition mode is selected
            addOperationUrlImg.show();
            listGrid.showField(REMOVE_FIELD_NAME);
        } else {
            operationUrlImg.show();
            listGrid.hideField(REMOVE_FIELD_NAME);
        }
    }

    private class OperationUrlsListGrid extends ListGrid {

        @Override
        protected boolean canEditCell(int rowNum, int colNum) {
            // In view mode, NEVER edit cell values
            return !viewMode;
        }
    }
}

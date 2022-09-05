package org.siemac.metamac.statistical.operations.web.client.operation.view;

import static org.siemac.metamac.statistical.operations.web.client.OperationsWeb.getConstants;
import static org.siemac.metamac.statistical.operations.web.client.OperationsWeb.getMessages;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.operations.core.dto.FamilyBaseDto;
import org.siemac.metamac.statistical.operations.core.dto.InstanceBaseDto;
import org.siemac.metamac.statistical.operations.core.dto.InstanceDto;
import org.siemac.metamac.statistical.operations.core.dto.OfficialityTypeDto;
import org.siemac.metamac.statistical.operations.core.dto.OperationDto;
import org.siemac.metamac.statistical.operations.core.dto.SurveyTypeDto;
import org.siemac.metamac.statistical.operations.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.operations.core.enume.domain.StatusEnum;
import org.siemac.metamac.statistical.operations.core.enume.domain.StreamMessageStatusEnum;
import org.siemac.metamac.statistical.operations.web.client.OperationsWeb;
import org.siemac.metamac.statistical.operations.web.client.constants.StatisticalOperationsWebConstants;
import org.siemac.metamac.statistical.operations.web.client.enums.ToolStripButtonEnum;
import org.siemac.metamac.statistical.operations.web.client.family.view.FamilyViewImpl;
import org.siemac.metamac.statistical.operations.web.client.model.InstanceRecord;
import org.siemac.metamac.statistical.operations.web.client.model.ds.InstanceDS;
import org.siemac.metamac.statistical.operations.web.client.model.ds.OperationDS;
import org.siemac.metamac.statistical.operations.web.client.operation.presenter.OperationPresenter;
import org.siemac.metamac.statistical.operations.web.client.operation.view.handlers.OperationUiHandlers;
import org.siemac.metamac.statistical.operations.web.client.resources.GlobalResources;
import org.siemac.metamac.statistical.operations.web.client.utils.ClientSecurityUtils;
import org.siemac.metamac.statistical.operations.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.operations.web.client.utils.OperationsListUtils;
import org.siemac.metamac.statistical.operations.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.operations.web.client.utils.RecordUtils;
import org.siemac.metamac.statistical.operations.web.client.utils.RequiredFieldUtils;
import org.siemac.metamac.statistical.operations.web.client.utils.ResourceListFieldUtils;
import org.siemac.metamac.statistical.operations.web.client.widgets.InstancesOrderFormLayout;
import org.siemac.metamac.statistical.operations.web.client.widgets.ListGridToolStrip;
import org.siemac.metamac.statistical.operations.web.client.widgets.ModalWindow;
import org.siemac.metamac.statistical.operations.web.client.widgets.NewInstanceForm;
import org.siemac.metamac.statistical.operations.web.client.widgets.OperationMainFormLayout;
import org.siemac.metamac.statistical.operations.web.shared.external.GetUsersAccessControlListResult;
import org.siemac.metamac.web.common.client.MetamacWebCommon;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;
import org.siemac.metamac.web.common.client.utils.CustomRequiredValidator;
import org.siemac.metamac.web.common.client.utils.FormItemUtils;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;
import org.siemac.metamac.web.common.client.widgets.BaseCustomListGrid;
import org.siemac.metamac.web.common.client.widgets.CustomListGrid;
import org.siemac.metamac.web.common.client.widgets.CustomListGridSectionStack;
import org.siemac.metamac.web.common.client.widgets.TitleLabel;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomCheckboxItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomLinkItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomSelectItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemLinkItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageRichTextEditorItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.ExternalItemListItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.SearchExternalItemLinkItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.SearchSingleCommonConfigurationItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.SearchSrmItemLinkItemWithSchemeFilterItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.SearchSrmListItemWithSchemeFilterItem;
import org.siemac.metamac.web.common.client.widgets.handlers.CustomLinkItemNavigationClickHandler;
import org.siemac.metamac.web.common.client.widgets.windows.search.SearchMultipleExternalItemPaginatedWindow;
import org.siemac.metamac.web.common.shared.criteria.CommonConfigurationRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.HasRecordClickHandlers;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class OperationViewImpl extends ViewWithUiHandlers<OperationUiHandlers> implements OperationPresenter.OperationView {

    public static final int                           FAMILY_LIST_MAX_RESULTS = 17;

    private VLayout                                   panel;

    private OperationMainFormLayout                   mainFormLayout;

    private OperationDto                              operationDto;

    // IDENTIFIERS
    private GroupDynamicForm                          identifiersForm;
    private GroupDynamicForm                          identifiersEditionForm;

    // CONTENT CLASSIFIERS
    private GroupDynamicForm                          contentClassifiersForm;
    private GroupDynamicForm                          contentClassifiersEditionForm;

    // CONTENT DESCRIPTORS
    private GroupDynamicForm                          contentViewForm;
    private GroupDynamicForm                          contentEditionForm;

    // CLASS DESCRIPTORS
    private GroupDynamicForm                          classForm;
    private GroupDynamicForm                          classDescriptorsEditionForm;
    private CustomSelectItem                          surveyType;
    private CustomSelectItem                          officialityType;
    private CustomCheckboxItem                        indSystem;

    // PRODUCTION DESCRIPTORS
    private CustomSelectItem                          technicianInCharge;
    private CustomSelectItem                          assistantTechnician;
    private GroupDynamicForm                          productionDescriptorsForm;
    private GroupDynamicForm                          productionDescriptorsEditionForm;
    private CustomCheckboxItem                        currentlyActiveItem;
    private CustomSelectItem                          statusItem;

    // DIFUSSION AND PUBLICATION
    private GroupDynamicForm                          diffusionForm;
    private GroupDynamicForm                          diffusionEditionForm;
    private CustomCheckboxItem                        releaseCalendar;
    private CustomTextItem                            releaseCalendarAccess;

    // LEGAL ACTS
    private GroupDynamicForm                          legalActsForm;
    private GroupDynamicForm                          legalActsEditionForm;

    // ANNOTATIONS
    private GroupDynamicForm                          annotationsViewForm;
    private GroupDynamicForm                          annotationsEditionForm;

    // INSTANCES

    private ListGridToolStrip                         instanceListGridToolStrip;
    private CustomListGrid                            instanceListGrid;
    private InstancesOrderFormLayout                  instancesOrderFormLayout;
    // Instance modal window
    private ModalWindow                               newInstanceWindow;
    private NewInstanceForm                           newInstanceForm;

    // FAMILIES

    private ToolStrip                                 familiesToolStrip;
    private ToolStripButton                           editFamiliesToolStripButton;
    private BaseCustomListGrid                        familyListGrid;
    private SearchMultipleExternalItemPaginatedWindow windowToAddFamiliesToOperation;

    private List<FamilyBaseDto>                       familyBaseDtos;

    private List<SurveyTypeDto>                       surveyTypeDtos;
    private List<OfficialityTypeDto>                  officialityTypeDtos;

    public OperationViewImpl() {
        super();
        panel = new VLayout();

        // OPERATION

        mainFormLayout = new OperationMainFormLayout();
        mainFormLayout.getTranslateToolStripButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                setTranslationsShowed(mainFormLayout.getTranslateToolStripButton().isSelected());
            }
        });
        mainFormLayout.getDeleteConfirmationWindow().getYesButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().deleteOperation(operationDto);
            }
        });
        createViewForm();
        mainFormLayout.getEditToolStripButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().retrieveUsersAccessControl();
            }
        });
        createEditionForm();

        // INSTANCES

        newInstanceForm = new NewInstanceForm();

        newInstanceWindow = new ModalWindow();
        newInstanceWindow.setTitle(getConstants().actionNewInstance());
        newInstanceWindow.setAutoSize(true);
        newInstanceWindow.addItem(newInstanceForm);

        instanceListGridToolStrip = new ListGridToolStrip(getConstants().instanceDeleteConfirmation());
        instanceListGridToolStrip.getNewButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                // Clear new instance form
                newInstanceForm.clearValues();

                newInstanceWindow.show();
            }
        });

        // Instances list
        instanceListGrid = new CustomListGrid();
        instanceListGrid.setHeight(150);
        instanceListGrid.setFields(ResourceListFieldUtils.getInstanceFields());
        instanceListGrid.addSelectionChangedHandler(new SelectionChangedHandler() {

            @Override
            public void onSelectionChanged(SelectionEvent event) {
                if (instanceListGrid.getSelectedRecords() != null && instanceListGrid.getSelectedRecords().length == 1) {
                    InstanceRecord record = (InstanceRecord) instanceListGrid.getSelectedRecord();
                    selectInstance(record.getId());
                } else {
                    // No record selected
                    deselectInstance();
                    if (instanceListGrid.getSelectedRecords().length > 1) {
                        // Delete more than one Instance with one click
                        showInstanceListGridDeleteButton();
                    }
                }
            }
        });

        CustomListGridSectionStack instancesSectionStack = new CustomListGridSectionStack(instanceListGrid, getConstants().instances(), "sectionStackStyle");
        instancesSectionStack.setMargin(15);
        instancesSectionStack.getDefaultSection().setItems(instanceListGridToolStrip, instanceListGrid);
        instancesSectionStack.getDefaultSection().setExpanded(true);

        // Instances order
        instancesOrderFormLayout = new InstancesOrderFormLayout();
        instancesOrderFormLayout.setMargin(0);
        instancesOrderFormLayout.getSave().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().updateInstancesOrder(instancesOrderFormLayout.getInstancesOrder());
            }
        });

        SectionStackSection ordersSection = new SectionStackSection(getConstants().instancesOrder());
        ordersSection.setExpanded(true);
        ordersSection.setItems(instancesOrderFormLayout);

        instancesSectionStack.addSection(ordersSection);

        // FAMILIES

        familiesToolStrip = new ToolStrip();
        familiesToolStrip.setWidth100();
        editFamiliesToolStripButton = new ToolStripButton(getConstants().actionEdit(), GlobalResources.RESOURCE.editListGrid().getURL());
        editFamiliesToolStripButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {

                windowToAddFamiliesToOperation = new SearchMultipleExternalItemPaginatedWindow(OperationsWeb.getConstants().actionAddOperationsToFamily(), FamilyViewImpl.OPERATION_LIST_MAX_RESULTS,
                        new SearchPaginatedAction<MetamacWebCriteria>() {

                            @Override
                            public void retrieveResultSet(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
                                getUiHandlers().retrieveFamilies(firstResult, maxResults, webCriteria);

                            }
                        });
                windowToAddFamiliesToOperation.retrieveItems();
                windowToAddFamiliesToOperation.setSelectedResources(CommonUtils.createFamilies(familyBaseDtos));
                windowToAddFamiliesToOperation.setSaveAction(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

                    @Override
                    public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {

                        List<Long> familiesToAdd = new ArrayList<Long>();
                        List<Long> familiesToRemove = new ArrayList<Long>();
                        CommonUtils.calculateFamiliesToUpdateInOperation(familyBaseDtos, windowToAddFamiliesToOperation.getSelectedResources(), familiesToAdd, familiesToRemove);
                        getUiHandlers().updateOperationFamilies(familiesToAdd, familiesToRemove);
                        windowToAddFamiliesToOperation.markForDestroy();
                    }
                });

            }
        });

        familiesToolStrip.addButton(editFamiliesToolStripButton);

        TitleLabel familiesTitleLabel = new TitleLabel(getConstants().families());
        familiesTitleLabel.setStyleName("sectionTitleLeftMargin");

        familyListGrid = new BaseCustomListGrid();
        familyListGrid.setHeight(150);
        familyListGrid.setFields(ResourceListFieldUtils.getFamilyFields());

        CustomListGridSectionStack familiesSectionStack = new CustomListGridSectionStack(familyListGrid, getConstants().families(), "sectionStackStyle");
        familiesSectionStack.setMargin(15);
        familiesSectionStack.getDefaultSection().setItems(familiesToolStrip, familyListGrid);

        VLayout subPanel = new VLayout();
        subPanel.setHeight100();
        subPanel.setOverflow(Overflow.SCROLL);
        subPanel.addMember(mainFormLayout);

        subPanel.addMember(instancesSectionStack);

        subPanel.addMember(familiesSectionStack);

        panel.addMember(subPanel);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setUiHandlers(OperationUiHandlers uiHandlers) {
        super.setUiHandlers(uiHandlers);
    }

    /*
     * GWTP will call setInSlot when a child presenter asks to be added under this view
     */
    @Override
    public void setInSlot(Object slot, Widget content) {
        if (slot == OperationPresenter.TYPE_SetContextAreaContentToolBar) {
            if (content != null) {
                Canvas[] canvas = ((ToolStrip) content).getMembers();
                for (int i = 0; i < canvas.length; i++) {
                    if (canvas[i] instanceof ToolStripButton) {
                        if (ToolStripButtonEnum.OPERATIONS.getValue().equals(((ToolStripButton) canvas[i]).getID())) {
                            ((ToolStripButton) canvas[i]).select();
                        }
                    }
                }
                panel.addMember(content, 0);
            }
        } else {
            // To support inheritance in your views it is good practice to call super.setInSlot when you can't handle the call.
            // Who knows, maybe the parent class knows what to do with this slot.
            super.setInSlot(slot, content);
        }
    }

    @Override
    public HasRecordClickHandlers getSelectedFamily() {
        return familyListGrid;
    }

    @Override
    public HasRecordClickHandlers getSelectedInstance() {
        return instanceListGrid;
    }

    @Override
    public void setOperation(OperationDto operationDto, List<InstanceBaseDto> instanceBaseDtos, List<FamilyBaseDto> familyBaseDtos) {
        this.operationDto = operationDto;

        // Security
        mainFormLayout.setCanEdit(ClientSecurityUtils.canUpdateOperation(operationDto.getCode()));
        mainFormLayout.setCanDelete(ClientSecurityUtils.canDeleteOperation(operationDto.getCode(), operationDto.getProcStatus()));
        mainFormLayout.setOperationCode(operationDto.getCode());
        instancesOrderFormLayout.setCanEdit(ClientSecurityUtils.canUpdateInstancesOrder(operationDto.getCode()));
        instanceListGridToolStrip.getNewButton().setVisibility(ClientSecurityUtils.canCreateInstance(operationDto.getCode()) ? Visibility.VISIBLE : Visibility.HIDDEN);
        editFamiliesToolStripButton.setVisibility(ClientSecurityUtils.canAddFamilyToOperation(operationDto.getCode()) ? Visibility.VISIBLE : Visibility.HIDDEN);

        // Operation
        setOperation(operationDto);

        // Set Instances
        setInstances(instanceBaseDtos);

        // Set Families
        setOperationFamilies(familyBaseDtos);
    }

    @Override
    public OperationDto getOperation(OperationDto operationDto) {

        // IDENTIFIERS

        operationDto.setCode(identifiersEditionForm.getValueAsString(OperationDS.CODE));
        operationDto.setTitle(identifiersEditionForm.getValueAsInternationalStringDto(OperationDS.TITLE));
        operationDto.setAcronym(identifiersEditionForm.getValueAsInternationalStringDto(OperationDS.ACRONYM));

        // CONTENT CLASSIFIERS

        operationDto.setSubjectArea(contentClassifiersEditionForm.getValueAsExternalItemDto(OperationDS.SUBJECT_AREA));

        List<ExternalItemDto> secondarySubjectAreas = ((ExternalItemListItem) contentClassifiersEditionForm.getItem(OperationDS.SECONDARY_SUBJECT_AREAS)).getExternalItemDtos();
        operationDto.getSecondarySubjectAreas().clear();
        operationDto.getSecondarySubjectAreas().addAll(secondarySubjectAreas);

        // CONTENT DESCRIPTORS

        operationDto.setDescription(contentEditionForm.getValueAsInternationalStringDto(OperationDS.DESCRIPTION));
        operationDto.setObjective(contentEditionForm.getValueAsInternationalStringDto(OperationDS.OBJECTIVE));

        // CLASS DESCRIPTORS

        operationDto.setSurveyType(OperationsListUtils.getSurveyTypeDto(surveyType.getValueAsString(), surveyTypeDtos));
        operationDto.setOfficialityType(OperationsListUtils.getOfficialityTypeDto(officialityType.getValueAsString(), officialityTypeDtos));
        operationDto.setIndicatorSystem(indSystem.getValueAsBoolean() == null ? false : indSystem.getValueAsBoolean());

        // PRODUCTION DESCRIPTORS
        operationDto.setTechnicianInCharge(technicianInCharge.getValueAsString() != null ? technicianInCharge.getValue().toString() : null);
        operationDto.setAssistantTechnician(assistantTechnician.getValueAsString() != null ? assistantTechnician.getValue().toString() : null);
        List<ExternalItemDto> producers = ((ExternalItemListItem) productionDescriptorsEditionForm.getItem(OperationDS.PRODUCER)).getExternalItemDtos();
        operationDto.getProducer().clear();
        operationDto.getProducer().addAll(producers);

        List<ExternalItemDto> regionalResponsibles = ((ExternalItemListItem) productionDescriptorsEditionForm.getItem(OperationDS.REG_RESPONSIBLE)).getExternalItemDtos();
        operationDto.getRegionalResponsible().clear();
        operationDto.getRegionalResponsible().addAll(regionalResponsibles);

        List<ExternalItemDto> regionalContributors = ((ExternalItemListItem) productionDescriptorsEditionForm.getItem(OperationDS.REG_CONTRIBUTOR)).getExternalItemDtos();
        operationDto.getRegionalContributor().clear();
        operationDto.getRegionalContributor().addAll(regionalContributors);

        operationDto.setCurrentlyActive(currentlyActiveItem.getValueAsBoolean());
        operationDto.setStatus(statusItem.getValueAsString() != null ? StatusEnum.valueOf(statusItem.getValueAsString()) : null);

        // DIFFUSION AND PUBLICATION

        List<ExternalItemDto> publishers = ((ExternalItemListItem) diffusionEditionForm.getItem(OperationDS.PUBLISHER)).getExternalItemDtos();
        operationDto.getPublisher().clear();
        operationDto.getPublisher().addAll(publishers);

        operationDto.setCommonMetadata(diffusionEditionForm.getValueAsExternalItemDto(OperationDS.COMMON_METADATA));

        operationDto.setRelPolUsAc(diffusionEditionForm.getValueAsInternationalStringDto(OperationDS.RE_POL_US_AC));
        operationDto.setReleaseCalendar(releaseCalendar.getValueAsBoolean());
        operationDto.setReleaseCalendarAccess(releaseCalendarAccess.getValueAsString());

        List<ExternalItemDto> updateFrequencies = ((ExternalItemListItem) diffusionEditionForm.getItem(OperationDS.UPDATE_FREQUENCY)).getExternalItemDtos();
        operationDto.getUpdateFrequency().clear();
        operationDto.getUpdateFrequency().addAll(updateFrequencies);

        operationDto.setRevPolicy(diffusionEditionForm.getValueAsInternationalStringDto(OperationDS.REV_POLICY));
        operationDto.setRevPractice(diffusionEditionForm.getValueAsInternationalStringDto(OperationDS.REV_PRACTICE));

        // LEGAL ACTS
        operationDto.setSpecificLegalActs(legalActsEditionForm.getValueAsInternationalStringDto(OperationDS.SPECIFIC_LEGAL_ACTS));
        operationDto.setSpecificDataSharing(legalActsEditionForm.getValueAsInternationalStringDto(OperationDS.SPECIFIC_DATA_SHARING));

        // ANNOTATIONS

        operationDto.setComment(annotationsEditionForm.getValueAsInternationalStringDto(OperationDS.COMMENTS));
        operationDto.setNotes(annotationsEditionForm.getValueAsInternationalStringDto(OperationDS.NOTES));
        return operationDto;
    }

    @Override
    public HasClickHandlers getSave() {
        return mainFormLayout.getSave();
    }

    @Override
    public void onOperationSaved(OperationDto operationDto) {
        setOperation(operationDto);
    }

    @Override
    public boolean validate() {
        return identifiersEditionForm.validate(false) && productionDescriptorsEditionForm.validate(false) && contentEditionForm.validate(false) && contentClassifiersEditionForm.validate(false)
                && diffusionEditionForm.validate(false) && classDescriptorsEditionForm.validate(false);
    }

    @Override
    public com.smartgwt.client.widgets.form.fields.events.HasClickHandlers getSaveNewInstance() {
        return newInstanceForm.getSave();
    }

    @Override
    public InstanceDto getNewInstace() {
        return newInstanceForm.getInstance();
    }

    @Override
    public void onInstanceSaved(InstanceDto instanceDto) {
        InstanceRecord record = RecordUtils.getInstanceRecord(instanceDto);
        instanceListGrid.addData(record);
        instanceListGrid.sort(InstanceDS.ORDER, SortDirection.DESCENDING);

        instancesOrderFormLayout.addInstance(instanceDto);
    }

    @Override
    public boolean validateNewInstance() {
        return newInstanceForm.validate();
    }

    @Override
    public void closeInstanceWindow() {
        newInstanceWindow.hide();
    }

    @Override
    public HasClickHandlers getDeleteInstance() {
        return instanceListGridToolStrip.getDeleteConfirmationWindow().getYesButton();
    }

    @Override
    public List<Long> getSelectedInstances() {
        List<Long> selectedInstances = new ArrayList<Long>();
        if (instanceListGrid.getSelectedRecords() != null) {
            ListGridRecord[] records = instanceListGrid.getSelectedRecords();
            for (int i = 0; i < records.length; i++) {
                InstanceRecord record = (InstanceRecord) records[i];
                selectedInstances.add(record.getId());
            }
        }
        return selectedInstances;
    }

    @Override
    public void setInstances(List<InstanceBaseDto> instanceBaseDtos) {
        // Instances list
        instanceListGrid.removeAllData();
        if (instanceBaseDtos != null) {
            for (InstanceBaseDto instanceBaseDto : instanceBaseDtos) {
                instanceListGrid.addData(RecordUtils.getInstanceRecord(instanceBaseDto));
            }
        }
        deselectInstance();

        // Instances order
        instancesOrderFormLayout.setInstances(instanceBaseDtos);
        if (instanceBaseDtos == null || instanceBaseDtos.isEmpty()) {
            instancesOrderFormLayout.hide();
        } else {
            instancesOrderFormLayout.show();
        }
    }

    public void setOperationFamilies(List<FamilyBaseDto> familyBaseDtos) {
        this.familyBaseDtos = familyBaseDtos;
        // Set families in listGrid
        familyListGrid.selectAllRecords();
        familyListGrid.removeSelectedData();
        familyListGrid.deselectAllRecords();
        if (familyBaseDtos != null) {
            for (FamilyBaseDto familyBaseDto : familyBaseDtos) {
                familyListGrid.addData(RecordUtils.getFamilyRecord(familyBaseDto));
            }
        }
        familyListGrid.setAutoFitMaxRecords(familyBaseDtos.size());
    }

    private void setOperation(OperationDto operationDto) {
        mainFormLayout.setViewMode();
        mainFormLayout.updatePublishSection(operationDto.getProcStatus());
        // Set Family
        mainFormLayout.setTitleLabelContents(InternationalStringUtils.getLocalisedString(operationDto.getTitle()));
        // Form
        setOperationViewMode(operationDto);
        setOperationEditionMode(operationDto);
    }

    private void createViewForm() {
        // Identifiers
        identifiersForm = new GroupDynamicForm(getConstants().operationIdentifiers());
        ViewTextItem identifier = new ViewTextItem(OperationDS.CODE, getConstants().operationCode());
        ViewMultiLanguageTextItem title = new ViewMultiLanguageTextItem(OperationDS.TITLE, getConstants().operationTitle());
        ViewMultiLanguageTextItem acronym = new ViewMultiLanguageTextItem(OperationDS.ACRONYM, getConstants().operationAcronym());
        ViewTextItem urn = new ViewTextItem(OperationDS.URN, getConstants().operationUrn());
        ViewTextItem publicationStreamStatus = new ViewTextItem(OperationDS.PUBLICATION_STREAM_STATUS, getConstants().lifeCycleStatisticalResourceStreamMsgStatus());
        publicationStreamStatus.setWidth(20);

        identifiersForm.setFields(identifier, title, acronym, urn, publicationStreamStatus);

        // Content Classifiers
        contentClassifiersForm = new GroupDynamicForm(getConstants().operationContentClassifiers());
        ExternalItemLinkItem subjectArea = new ExternalItemLinkItem(OperationDS.SUBJECT_AREA, getConstants().operationSubjectArea());
        ExternalItemListItem secondarySubject = new ExternalItemListItem(OperationDS.SECONDARY_SUBJECT_AREAS, getConstants().operationSubjectAreasSecondary(), false);
        contentClassifiersForm.setFields(subjectArea, secondarySubject);

        // Content Descriptors
        contentViewForm = new GroupDynamicForm(getConstants().operationContentDescriptors());
        ViewMultiLanguageTextItem description = new ViewMultiLanguageTextItem(OperationDS.DESCRIPTION, getConstants().operationDescription());
        ViewMultiLanguageTextItem objective = new ViewMultiLanguageTextItem(OperationDS.OBJECTIVE, getConstants().operationObjective());
        contentViewForm.setFields(objective, description);

        // Class Descriptors
        classForm = new GroupDynamicForm(getConstants().operationClassDescriptors());
        ViewTextItem survey = new ViewTextItem(OperationDS.STATISTICAL_OPERATION_TYPE, getConstants().operationSurveyType());
        ViewTextItem officiality = new ViewTextItem(OperationDS.OFFICIALITY_TYPE, getConstants().operationOfficialityType());
        ViewTextItem indSystem = new ViewTextItem(OperationDS.INDICATOR_SYSTEM, getConstants().operationIndicatorSystem());
        classForm.setFields(survey, officiality, indSystem);

        // Production descriptors
        ViewTextItem techinicianInCharge = new ViewTextItem(OperationDS.TECHNICIAN_IN_CHARGE, getConstants().operationTechnicianInCharge());
        ViewTextItem assistantTechnician = new ViewTextItem(OperationDS.ASSISTANT_TECHNICIAN, getConstants().operationAssistantTechnician());
        productionDescriptorsForm = new GroupDynamicForm(getConstants().operationProductionDescriptors());
        ExternalItemListItem producer = new ExternalItemListItem(OperationDS.PRODUCER, getConstants().operationProducers(), false);
        ExternalItemListItem regionalResposible = new ExternalItemListItem(OperationDS.REG_RESPONSIBLE, getConstants().operationRegionalResponsibles(), false);
        ExternalItemListItem regionalContibutor = new ExternalItemListItem(OperationDS.REG_CONTRIBUTOR, getConstants().operationRegionalContributors(), false);
        ViewTextItem createdDate = new ViewTextItem(OperationDS.CREATED_DATE, getConstants().operationCreatedDate());
        ViewTextItem inventoryDate = new ViewTextItem(OperationDS.INTERNAL_INVENTORY_DATE, getConstants().operationInternalInventoryDate());
        ViewTextItem currentlyActive = new ViewTextItem(OperationDS.CURRENTLY_ACTIVE, getConstants().operationCurrentlyActive());
        ViewTextItem status = new ViewTextItem(OperationDS.STATUS, getConstants().operationStatus());
        ViewTextItem procStatus = new ViewTextItem(OperationDS.PROC_STATUS, getConstants().operationProcStatus());
        productionDescriptorsForm.setFields(techinicianInCharge, assistantTechnician, producer, regionalResposible, regionalContibutor, createdDate, inventoryDate, currentlyActive, status,
                procStatus);

        // Diffusion Descriptors
        diffusionForm = new GroupDynamicForm(getConstants().operationDiffusionAndPublication());
        ExternalItemListItem publisher = new ExternalItemListItem(OperationDS.PUBLISHER, getConstants().operationPublisher(), false);
        ExternalItemLinkItem commonMetadata = new ExternalItemLinkItem(OperationDS.COMMON_METADATA, getConstants().operationCommonMetadata());
        ViewMultiLanguageTextItem staticRelPolUsAc = new ViewMultiLanguageTextItem(OperationDS.RE_POL_US_AC, getConstants().operationReleaseUsersPolicy());
        ViewTextItem releaseCalendar = new ViewTextItem(OperationDS.RELEASE_CALENDAR, getConstants().operationReleaseCalendar());
        ViewTextItem releaseCalendarAccess = new ViewTextItem(OperationDS.RELEASE_CALENDAR_ACCESS, getConstants().operationReleaseCalendarAccess());
        ExternalItemListItem updateFreq = new ExternalItemListItem(OperationDS.UPDATE_FREQUENCY, getConstants().operationUpdateFrequency(), false);
        CustomLinkItem currentInst = new CustomLinkItem(OperationDS.CURRENT_INSTANCE, getConstants().operationCurrentInstance(), getCustomLinkItemNavigationClickHandler());
        CustomLinkItem currentInternalInst = new CustomLinkItem(OperationDS.CURRENT_INTERNAL_INSTANCE, getConstants().operationCurrentInternalInstance(), getCustomLinkItemNavigationClickHandler());
        ViewTextItem invDate = new ViewTextItem(OperationDS.INVENTORY_DATE, getConstants().operationInventoryDate());
        ViewMultiLanguageTextItem staticRevPolicyItem = new ViewMultiLanguageTextItem(OperationDS.REV_POLICY, getConstants().operationRevPolicy());
        ViewMultiLanguageTextItem staticRevPracticeItem = new ViewMultiLanguageTextItem(OperationDS.REV_PRACTICE, getConstants().operationRevPractice());
        diffusionForm.setFields(publisher, commonMetadata, staticRelPolUsAc, releaseCalendar, releaseCalendarAccess, updateFreq, currentInst, currentInternalInst, invDate, staticRevPolicyItem,
                staticRevPracticeItem);

        // Legal acts
        legalActsForm = new GroupDynamicForm(getConstants().formLegalActs());
        ViewMultiLanguageTextItem specificLegalActs = new ViewMultiLanguageTextItem(OperationDS.SPECIFIC_LEGAL_ACTS, getConstants().operationSpecificLegalActs());
        ViewMultiLanguageTextItem specificDataSharing = new ViewMultiLanguageTextItem(OperationDS.SPECIFIC_DATA_SHARING, getConstants().operationSpecificDataSharing());
        legalActsForm.setFields(specificLegalActs, specificDataSharing);

        // Annotations
        annotationsViewForm = new GroupDynamicForm(getConstants().operationAnnotations());
        ViewMultiLanguageTextItem staticCommentItem = new ViewMultiLanguageTextItem(OperationDS.COMMENTS, getConstants().operationComments());
        ViewMultiLanguageTextItem staticNotesItem = new ViewMultiLanguageTextItem(OperationDS.NOTES, getConstants().operationNotes());
        annotationsViewForm.setFields(staticCommentItem, staticNotesItem);

        // Add to main layout
        mainFormLayout.addViewCanvas(identifiersForm);
        mainFormLayout.addViewCanvas(contentClassifiersForm);
        mainFormLayout.addViewCanvas(contentViewForm);
        mainFormLayout.addViewCanvas(classForm);
        mainFormLayout.addViewCanvas(productionDescriptorsForm);
        mainFormLayout.addViewCanvas(diffusionForm);
        mainFormLayout.addViewCanvas(legalActsForm);
        mainFormLayout.addViewCanvas(annotationsViewForm);
    }

    private void createEditionForm() {

        // IDENTIFIERS

        identifiersEditionForm = new GroupDynamicForm(getConstants().operationIdentifiers());

        RequiredTextItem code = new RequiredTextItem(OperationDS.CODE, getConstants().operationCode());
        code.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return canOperationCodeBeEdited();
            }
        });
        code.setValidators(CommonWebUtils.getSemanticIdentifierCustomValidator(), CommonUtils.getOperationCodeLengthValidator());
        ViewTextItem staticCode = new ViewTextItem(OperationDS.CODE_VIEW, getConstants().operationCode());
        staticCode.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return !canOperationCodeBeEdited();
            }
        });

        MultiLanguageTextItem title = new MultiLanguageTextItem(OperationDS.TITLE, getConstants().operationTitle());
        title.setRequired(true);
        MultiLanguageTextItem acronym = new MultiLanguageTextItem(OperationDS.ACRONYM, getConstants().operationAcronym());
        ViewTextItem urn = new ViewTextItem(OperationDS.URN, getConstants().operationUrn());
        identifiersEditionForm.setFields(staticCode, code, title, acronym, urn);

        // CONTENT CLASSIFIERS

        contentClassifiersEditionForm = new GroupDynamicForm(getConstants().operationContentClassifiers());
        SearchExternalItemLinkItem subjectAreaItem = createSubjectAreaItem(OperationDS.SUBJECT_AREA, getConstants().operationSubjectArea());
        subjectAreaItem.setRequired(true);
        SearchSrmListItemWithSchemeFilterItem secondarySubjectAreasItem = createSecondarySubjectAreasItem();
        contentClassifiersEditionForm.setFields(subjectAreaItem, secondarySubjectAreasItem);

        // CONTENT DESCRIPTORS

        contentEditionForm = new GroupDynamicForm(getConstants().operationContentDescriptors());
        MultiLanguageRichTextEditorItem description = new MultiLanguageRichTextEditorItem(OperationDS.DESCRIPTION, getConstants().operationDescription());

        final MultiLanguageRichTextEditorItem objective = new MultiLanguageRichTextEditorItem(OperationDS.OBJECTIVE, getConstants().operationObjective());
        objective.setValidators(new CustomRequiredValidator() {

            @Override
            protected boolean condition(Object value) {
                return CommonUtils.isInternallyOrExternallyPublished(operationDto) ? objective.getValue() != null : true;
            }
        });

        contentEditionForm.setFields(objective, description);

        // CLASS DESCRIPTORS

        classDescriptorsEditionForm = new GroupDynamicForm(getConstants().operationClassDescriptors());

        surveyType = new CustomSelectItem(OperationDS.STATISTICAL_OPERATION_TYPE, getConstants().operationSurveyType());
        surveyType.setValidators(new CustomRequiredValidator() {

            @Override
            protected boolean condition(Object value) {
                return CommonUtils.isInternallyOrExternallyPublished(operationDto) ? !StringUtils.isBlank(surveyType.getValueAsString()) : true;
            }
        });

        officialityType = new CustomSelectItem(OperationDS.OFFICIALITY_TYPE, getConstants().operationOfficialityType());
        officialityType.setValidators(new CustomRequiredValidator() {

            @Override
            protected boolean condition(Object value) {
                return CommonUtils.isInternallyOrExternallyPublished(operationDto) ? !StringUtils.isBlank(officialityType.getValueAsString()) : true;
            }
        });

        indSystem = new CustomCheckboxItem(OperationDS.INDICATOR_SYSTEM, getConstants().operationIndicatorSystem());
        indSystem.setTitleStyle("requiredFormLabel");
        classDescriptorsEditionForm.setFields(surveyType, officialityType, indSystem);

        // PRODUCTION DESCRIPTORS
        productionDescriptorsEditionForm = new GroupDynamicForm(getConstants().operationProductionDescriptors());
        technicianInCharge = new CustomSelectItem(OperationDS.TECHNICIAN_IN_CHARGE, getConstants().operationTechnicianInCharge());

        technicianInCharge.setValidators(getTechnicianInChargeUserNotTheSameAsAssistantTechnicianUserValidator());

        assistantTechnician = new CustomSelectItem(OperationDS.ASSISTANT_TECHNICIAN, getConstants().operationAssistantTechnician());
        assistantTechnician.setValidators(getTechnicianInChargeUserNotTheSameAsAssistantTechnicianUserValidator());

        final SearchSrmListItemWithSchemeFilterItem producerItem = createProducersItem();
        producerItem.setValidators(new CustomRequiredValidator() {

            @Override
            protected boolean condition(Object value) {
                return CommonUtils.isInternallyOrExternallyPublished(operationDto) ? !producerItem.getExternalItemDtos().isEmpty() : true;
            }
        });

        final SearchSrmListItemWithSchemeFilterItem regionalResponsibleItem = createRegionaleResponsiblesItem();
        regionalResponsibleItem.setValidators(new CustomRequiredValidator() {

            @Override
            protected boolean condition(Object value) {
                return CommonUtils.isInternallyOrExternallyPublished(operationDto) ? !regionalResponsibleItem.getExternalItemDtos().isEmpty() : true;
            }
        });

        SearchSrmListItemWithSchemeFilterItem regionalContributorItem = createRegionaleContributorsItem();

        ViewTextItem createdDate = new ViewTextItem(OperationDS.CREATED_DATE, getConstants().operationCreatedDate());
        ViewTextItem internalInventoryDate = new ViewTextItem(OperationDS.INTERNAL_INVENTORY_DATE, getConstants().operationInternalInventoryDate());
        currentlyActiveItem = new CustomCheckboxItem(OperationDS.CURRENTLY_ACTIVE, getConstants().operationCurrentlyActive());

        statusItem = new CustomSelectItem(OperationDS.STATUS, getConstants().operationStatus());
        statusItem.setValueMap(CommonUtils.getStatusEnumHashMap());
        statusItem.setValidators(new CustomRequiredValidator() {

            @Override
            protected boolean condition(Object value) {
                return CommonUtils.isInternallyOrExternallyPublished(operationDto) ? !StringUtils.isBlank(statusItem.getValueAsString()) : true;
            }
        });

        ViewTextItem procStatus = new ViewTextItem(OperationDS.PROC_STATUS, getConstants().operationProcStatus());
        ViewTextItem staticProcStatus = new ViewTextItem(OperationDS.PROC_STATUS_VIEW, getConstants().operationProcStatus());
        staticProcStatus.setShowIfCondition(FormItemUtils.getFalseFormItemIfFunction());
        productionDescriptorsEditionForm.setFields(technicianInCharge, assistantTechnician, producerItem, regionalResponsibleItem, regionalContributorItem, createdDate, internalInventoryDate,
                currentlyActiveItem, statusItem, staticProcStatus, procStatus);

        // DIFFUSION AND PUBLICATION

        diffusionEditionForm = new GroupDynamicForm(getConstants().operationDiffusionAndPublication());

        final SearchSrmListItemWithSchemeFilterItem publishersItem = createPublishersItem();
        publishersItem.setValidators(new CustomRequiredValidator() {

            @Override
            protected boolean condition(Object value) {
                return CommonUtils.isInternallyOrExternallyPublished(operationDto) ? !publishersItem.getExternalItemDtos().isEmpty() : true;
            }
        });

        final SearchSingleCommonConfigurationItem commonMetadataItem = createCommonMetadataItem(OperationDS.COMMON_METADATA, getConstants().operationCommonMetadata());
        commonMetadataItem.setValidators(new CustomRequiredValidator() {

            @Override
            protected boolean condition(Object value) {
                return CommonUtils.isInternallyOrExternallyPublished(operationDto) ? commonMetadataItem.getExternalItemDto() != null : true;
            }
        });

        MultiLanguageRichTextEditorItem relPolUsAc = new MultiLanguageRichTextEditorItem(OperationDS.RE_POL_US_AC, getConstants().operationReleaseUsersPolicy());
        releaseCalendar = new CustomCheckboxItem(OperationDS.RELEASE_CALENDAR, getConstants().operationReleaseCalendar());
        releaseCalendarAccess = new CustomTextItem(OperationDS.RELEASE_CALENDAR_ACCESS, getConstants().operationReleaseCalendarAccess());
        releaseCalendarAccess.setValidators(CommonWebUtils.getUrlValidator());
        ExternalItemListItem updateFrequencyItem = createUpdateFrequencyItem();
        CustomLinkItem currentInst = new CustomLinkItem(OperationDS.CURRENT_INSTANCE, getConstants().operationCurrentInstance(), getCustomLinkItemNavigationClickHandler());
        CustomLinkItem currentInternalInst = new CustomLinkItem(OperationDS.CURRENT_INTERNAL_INSTANCE, getConstants().operationCurrentInternalInstance(), getCustomLinkItemNavigationClickHandler());
        ViewTextItem invDate = new ViewTextItem(OperationDS.INVENTORY_DATE, getConstants().operationInventoryDate());
        MultiLanguageRichTextEditorItem revPolicyItem = new MultiLanguageRichTextEditorItem(OperationDS.REV_POLICY, getConstants().operationRevPolicy());
        MultiLanguageRichTextEditorItem revPracticeItem = new MultiLanguageRichTextEditorItem(OperationDS.REV_PRACTICE, getConstants().operationRevPractice());
        diffusionEditionForm.setFields(publishersItem, commonMetadataItem, relPolUsAc, releaseCalendar, releaseCalendarAccess, updateFrequencyItem, currentInst, currentInternalInst, invDate,
                revPolicyItem, revPracticeItem);

        // LEGAL ACTS

        legalActsEditionForm = new GroupDynamicForm(getConstants().formLegalActs());
        MultiLanguageRichTextEditorItem specificLegalActs = new MultiLanguageRichTextEditorItem(OperationDS.SPECIFIC_LEGAL_ACTS, getConstants().operationSpecificLegalActs());
        MultiLanguageRichTextEditorItem specificDataSharing = new MultiLanguageRichTextEditorItem(OperationDS.SPECIFIC_DATA_SHARING, getConstants().operationSpecificDataSharing());
        legalActsEditionForm.setFields(specificLegalActs, specificDataSharing);

        // ANNOTATIONS

        annotationsEditionForm = new GroupDynamicForm(getConstants().operationAnnotations());
        MultiLanguageRichTextEditorItem commentItem = new MultiLanguageRichTextEditorItem(OperationDS.COMMENTS, getConstants().operationComments());
        MultiLanguageRichTextEditorItem notesItem = new MultiLanguageRichTextEditorItem(OperationDS.NOTES, getConstants().operationNotes());
        annotationsEditionForm.setFields(commentItem, notesItem);

        // Add to main layout
        mainFormLayout.addEditionCanvas(identifiersEditionForm);
        mainFormLayout.addEditionCanvas(contentClassifiersEditionForm);
        mainFormLayout.addEditionCanvas(contentEditionForm);
        mainFormLayout.addEditionCanvas(classDescriptorsEditionForm);
        mainFormLayout.addEditionCanvas(productionDescriptorsEditionForm);
        mainFormLayout.addEditionCanvas(diffusionEditionForm);
        mainFormLayout.addEditionCanvas(legalActsEditionForm);
        mainFormLayout.addEditionCanvas(annotationsEditionForm);
    }

    private void setOperationViewMode(OperationDto operationDto) {
        // IDENTIFIERS

        identifiersForm.setValue(OperationDS.CODE, operationDto.getCode());
        identifiersForm.setValue(OperationDS.TITLE, operationDto.getTitle());
        identifiersForm.setValue(OperationDS.ACRONYM, operationDto.getAcronym());
        identifiersForm.setValue(OperationDS.URN, operationDto.getUrn());
        identifiersForm.getItem(OperationDS.PUBLICATION_STREAM_STATUS)
                .setIcons(StreamMessageStatusEnum.PENDING.equals(operationDto.getStreamMessageStatus()) ? null : CommonUtils.getPublicationStreamStatusIcon(operationDto.getStreamMessageStatus()));

        // CONTENT CLASSIFIERS

        contentClassifiersForm.setValue(OperationDS.SUBJECT_AREA, operationDto.getSubjectArea());
        ((ExternalItemListItem) contentClassifiersForm.getItem(OperationDS.SECONDARY_SUBJECT_AREAS)).setExternalItems(operationDto.getSecondarySubjectAreas());

        // CONTENT DESCRIPTORS

        contentViewForm.setValue(OperationDS.DESCRIPTION, operationDto.getDescription());
        contentViewForm.setValue(OperationDS.OBJECTIVE, operationDto.getObjective());

        // CLASS DESCRIPTORS

        classForm.setValue(OperationDS.STATISTICAL_OPERATION_TYPE,
                operationDto.getSurveyType() == null ? "" : CommonWebUtils.getElementName(operationDto.getSurveyType().getIdentifier(), operationDto.getSurveyType().getDescription()));
        classForm.setValue(OperationDS.OFFICIALITY_TYPE,
                operationDto.getOfficialityType() == null ? "" : CommonWebUtils.getElementName(operationDto.getOfficialityType().getIdentifier(), operationDto.getOfficialityType().getDescription()));
        classForm.setValue(OperationDS.INDICATOR_SYSTEM,
                (operationDto.getIndicatorSystem() != null && operationDto.getIndicatorSystem()) ? MetamacWebCommon.getConstants().yes() : MetamacWebCommon.getConstants().no());

        // PRODUCTION DESCRIPTORS

        productionDescriptorsForm.setValue(OperationDS.TECHNICIAN_IN_CHARGE, operationDto.getTechnicianInCharge() == null ? "" : operationDto.getTechnicianInCharge());
        productionDescriptorsForm.setValue(OperationDS.ASSISTANT_TECHNICIAN, operationDto.getAssistantTechnician() == null ? "" : operationDto.getAssistantTechnician());
        ((ExternalItemListItem) productionDescriptorsForm.getItem(OperationDS.PRODUCER)).setExternalItems(operationDto.getProducer());
        ((ExternalItemListItem) productionDescriptorsForm.getItem(OperationDS.REG_RESPONSIBLE)).setExternalItems(operationDto.getRegionalResponsible());
        ((ExternalItemListItem) productionDescriptorsForm.getItem(OperationDS.REG_CONTRIBUTOR)).setExternalItems(operationDto.getRegionalContributor());
        productionDescriptorsForm.setValue(OperationDS.CREATED_DATE, operationDto.getCreatedDate());
        productionDescriptorsForm.setValue(OperationDS.INTERNAL_INVENTORY_DATE, operationDto.getInternalInventoryDate());
        productionDescriptorsForm.setValue(OperationDS.CURRENTLY_ACTIVE,
                (operationDto.getCurrentlyActive() != null && operationDto.getCurrentlyActive()) ? MetamacWebCommon.getConstants().yes() : MetamacWebCommon.getConstants().no());
        productionDescriptorsForm.setValue(OperationDS.STATUS, CommonUtils.getStatusName(operationDto.getStatus()));
        productionDescriptorsForm.setValue(OperationDS.PROC_STATUS, CommonUtils.getProcStatusName(operationDto.getProcStatus()));

        // DIFFUSION AND PUBLICATION

        ((ExternalItemListItem) diffusionForm.getItem(OperationDS.PUBLISHER)).setExternalItems(operationDto.getPublisher());

        diffusionForm.setValue(OperationDS.COMMON_METADATA, operationDto.getCommonMetadata());

        diffusionForm.setValue(OperationDS.RE_POL_US_AC, operationDto.getRelPolUsAc());
        diffusionForm.setValue(OperationDS.RELEASE_CALENDAR,
                (operationDto.getReleaseCalendar() != null && operationDto.getReleaseCalendar()) ? MetamacWebCommon.getConstants().yes() : MetamacWebCommon.getConstants().no());
        diffusionForm.setValue(OperationDS.RELEASE_CALENDAR_ACCESS, operationDto.getReleaseCalendarAccess());

        ((ExternalItemListItem) diffusionForm.getItem(OperationDS.UPDATE_FREQUENCY)).setExternalItems(operationDto.getUpdateFrequency());

        if (operationDto.getCurrentInstance() != null) {
            ((CustomLinkItem) diffusionForm.getItem(OperationDS.CURRENT_INSTANCE)).setValue(
                    CommonWebUtils.getElementName(operationDto.getCurrentInstance().getCode(), operationDto.getCurrentInstance().getTitle()),
                    PlaceRequestUtils.buildAbsoluteInstancePlaceRequest(operationDto.getCode(), operationDto.getCurrentInstance().getCode()));
        } else {
            ((CustomLinkItem) diffusionForm.getItem(OperationDS.CURRENT_INSTANCE)).clearValue();
        }

        if (operationDto.getCurrentInternalInstance() != null) {
            ((CustomLinkItem) diffusionForm.getItem(OperationDS.CURRENT_INTERNAL_INSTANCE)).setValue(
                    CommonWebUtils.getElementName(operationDto.getCurrentInternalInstance().getCode(), operationDto.getCurrentInternalInstance().getTitle()),
                    PlaceRequestUtils.buildAbsoluteInstancePlaceRequest(operationDto.getCode(), operationDto.getCurrentInternalInstance().getCode()));
        } else {
            ((CustomLinkItem) diffusionForm.getItem(OperationDS.CURRENT_INTERNAL_INSTANCE)).clearValue();
        }

        diffusionForm.setValue(OperationDS.INVENTORY_DATE, operationDto.getInventoryDate());
        diffusionForm.setValue(OperationDS.REV_POLICY, operationDto.getRevPolicy());
        diffusionForm.setValue(OperationDS.REV_PRACTICE, operationDto.getRevPractice());

        // LEGAL ACTS
        legalActsForm.setValue(OperationDS.SPECIFIC_LEGAL_ACTS, operationDto.getSpecificLegalActs());
        legalActsForm.setValue(OperationDS.SPECIFIC_DATA_SHARING, operationDto.getSpecificDataSharing());

        // ANNOTATIONS

        annotationsViewForm.setValue(OperationDS.COMMENTS, operationDto.getComment());
        annotationsViewForm.setValue(OperationDS.NOTES, operationDto.getNotes());
    }

    private void setOperationEditionMode(OperationDto operationDto) {

        String[] requiredFieldsToNextProcStatus = RequiredFieldUtils.getOperationRequiredFieldsToNextProcStatus(operationDto.getProcStatus());

        // IDENTIFIERS

        identifiersEditionForm.setValue(OperationDS.CODE, operationDto.getCode());
        identifiersEditionForm.setValue(OperationDS.CODE_VIEW, operationDto.getCode());
        identifiersEditionForm.setValue(OperationDS.TITLE, operationDto.getTitle());
        identifiersEditionForm.setValue(OperationDS.ACRONYM, operationDto.getAcronym());
        identifiersEditionForm.setValue(OperationDS.URN, operationDto.getUrn());
        identifiersEditionForm.setRequiredTitleSuffix(requiredFieldsToNextProcStatus);
        identifiersEditionForm.markForRedraw();

        // CONTENT CLASSIFIERS

        contentClassifiersEditionForm.setValue(OperationDS.SUBJECT_AREA, operationDto.getSubjectArea());
        ((ExternalItemListItem) contentClassifiersEditionForm.getItem(OperationDS.SECONDARY_SUBJECT_AREAS)).setExternalItems(operationDto.getSecondarySubjectAreas());
        contentClassifiersEditionForm.setRequiredTitleSuffix(requiredFieldsToNextProcStatus);
        contentClassifiersEditionForm.markForRedraw();

        // CONTENT DESCRIPTORS

        contentEditionForm.setValue(OperationDS.DESCRIPTION, operationDto.getDescription());
        contentEditionForm.setValue(OperationDS.OBJECTIVE, operationDto.getObjective());
        contentEditionForm.setRequiredTitleSuffix(requiredFieldsToNextProcStatus);
        contentEditionForm.markForRedraw();

        // CLASS DESCRIPTORS

        surveyType.setValue(operationDto.getSurveyType() != null ? operationDto.getSurveyType().getId() : null);
        officialityType.setValue(operationDto.getOfficialityType() != null ? operationDto.getOfficialityType().getId() : null);
        indSystem.setValue(operationDto.getIndicatorSystem() == null ? false : operationDto.getIndicatorSystem());
        classDescriptorsEditionForm.setRequiredTitleSuffix(requiredFieldsToNextProcStatus);
        classDescriptorsEditionForm.markForRedraw();

        // PRODUCTION DESCRIPTORS

        technicianInCharge.setValue(operationDto.getTechnicianInCharge() != null ? CommonUtils.getUsernameUser(operationDto.getTechnicianInCharge()) : null);
        assistantTechnician.setValue(operationDto.getAssistantTechnician() != null ? CommonUtils.getUsernameUser(operationDto.getAssistantTechnician()) : null);
        ((ExternalItemListItem) productionDescriptorsEditionForm.getItem(OperationDS.PRODUCER)).setExternalItems(operationDto.getProducer());
        ((ExternalItemListItem) productionDescriptorsEditionForm.getItem(OperationDS.REG_RESPONSIBLE)).setExternalItems(operationDto.getRegionalResponsible());
        ((ExternalItemListItem) productionDescriptorsEditionForm.getItem(OperationDS.REG_CONTRIBUTOR)).setExternalItems(operationDto.getRegionalContributor());
        productionDescriptorsEditionForm.setValue(OperationDS.CREATED_DATE, operationDto.getCreatedDate());
        productionDescriptorsEditionForm.setValue(OperationDS.INTERNAL_INVENTORY_DATE, operationDto.getInternalInventoryDate());
        currentlyActiveItem.setValue(operationDto.getCurrentlyActive() != null ? operationDto.getCurrentlyActive() : false);
        statusItem.setValue(operationDto.getStatus() == null ? null : operationDto.getStatus().toString());
        productionDescriptorsEditionForm.setValue(OperationDS.PROC_STATUS, CommonUtils.getProcStatusName(operationDto.getProcStatus()));
        productionDescriptorsEditionForm.setValue(OperationDS.PROC_STATUS_VIEW, operationDto.getProcStatus().toString());

        productionDescriptorsEditionForm.setRequiredTitleSuffix(requiredFieldsToNextProcStatus);
        productionDescriptorsEditionForm.markForRedraw();

        // DIFFUSION AND PUBLICATION

        ((ExternalItemListItem) diffusionEditionForm.getItem(OperationDS.PUBLISHER)).setExternalItems(operationDto.getPublisher());

        diffusionEditionForm.setValue(OperationDS.COMMON_METADATA, operationDto.getCommonMetadata());

        diffusionEditionForm.setValue(OperationDS.RE_POL_US_AC, operationDto.getRelPolUsAc());
        releaseCalendar.setValue(operationDto.getReleaseCalendar());
        releaseCalendarAccess.setValue(operationDto.getReleaseCalendarAccess());

        ((ExternalItemListItem) diffusionEditionForm.getItem(OperationDS.UPDATE_FREQUENCY)).setExternalItems(operationDto.getUpdateFrequency());

        if (operationDto.getCurrentInstance() != null) {
            ((CustomLinkItem) diffusionEditionForm.getItem(OperationDS.CURRENT_INSTANCE)).setValue(
                    CommonWebUtils.getElementName(operationDto.getCurrentInstance().getCode(), operationDto.getCurrentInstance().getTitle()),
                    PlaceRequestUtils.buildAbsoluteInstancePlaceRequest(operationDto.getCode(), operationDto.getCurrentInstance().getCode()));
        } else {
            ((CustomLinkItem) diffusionEditionForm.getItem(OperationDS.CURRENT_INSTANCE)).clearValue();
        }

        if (operationDto.getCurrentInternalInstance() != null) {
            ((CustomLinkItem) diffusionEditionForm.getItem(OperationDS.CURRENT_INTERNAL_INSTANCE)).setValue(
                    CommonWebUtils.getElementName(operationDto.getCurrentInternalInstance().getCode(), operationDto.getCurrentInternalInstance().getTitle()),
                    PlaceRequestUtils.buildAbsoluteInstancePlaceRequest(operationDto.getCode(), operationDto.getCurrentInternalInstance().getCode()));
        } else {
            ((CustomLinkItem) diffusionEditionForm.getItem(OperationDS.CURRENT_INTERNAL_INSTANCE)).clearValue();
        }

        diffusionEditionForm.setValue(OperationDS.INVENTORY_DATE, operationDto.getInventoryDate());
        diffusionEditionForm.setValue(OperationDS.REV_POLICY, operationDto.getRevPolicy());
        diffusionEditionForm.setValue(OperationDS.REV_PRACTICE, operationDto.getRevPractice());

        diffusionEditionForm.setRequiredTitleSuffix(requiredFieldsToNextProcStatus);
        diffusionEditionForm.markForRedraw();

        // LEGAL ACTS

        legalActsEditionForm.setValue(OperationDS.SPECIFIC_LEGAL_ACTS, operationDto.getSpecificLegalActs());
        legalActsEditionForm.setValue(OperationDS.SPECIFIC_DATA_SHARING, operationDto.getSpecificDataSharing());
        legalActsEditionForm.setRequiredTitleSuffix(requiredFieldsToNextProcStatus);
        legalActsEditionForm.markForRedraw();

        // ANNOTATIONS

        annotationsEditionForm.setValue(OperationDS.COMMENTS, operationDto.getComment());
        annotationsEditionForm.setValue(OperationDS.NOTES, operationDto.getNotes());
        annotationsEditionForm.setRequiredTitleSuffix(requiredFieldsToNextProcStatus);
        annotationsEditionForm.markForRedraw();

        identifiersEditionForm.markForRedraw();
        productionDescriptorsEditionForm.markForRedraw();
    }

    @Override
    public HasClickHandlers getPublishOperationInternally() {
        return mainFormLayout.getPublishInternally();
    }

    @Override
    public HasClickHandlers getPublishOperationExternally() {
        return mainFormLayout.getPublishExternally();
    }

    @Override
    public HasClickHandlers getReSendStreamMessageOperation() {
        return mainFormLayout.getLifeCycleReSendStreamMessage();
    }

    /**
     * Select Instance in ListGrid
     *
     * @param id
     */
    private void selectInstance(Long id) {
        if (id == null) {
            // New instance
            instanceListGridToolStrip.getDeleteButton().hide();
            instanceListGrid.deselectAllRecords();
        } else {
            showInstanceListGridDeleteButton();
        }
    }

    /**
     * DeSelect Instance in ListGrid
     */
    private void deselectInstance() {
        instanceListGridToolStrip.getDeleteButton().hide();
    }

    @Override
    public void setOperationsLists(List<SurveyTypeDto> surveyTypeDtos, List<OfficialityTypeDto> officialityTypeDtos) {
        this.surveyTypeDtos = surveyTypeDtos;
        this.officialityTypeDtos = officialityTypeDtos;
        surveyType.setValueMap(OperationsListUtils.getSurveyTypeHashMap(surveyTypeDtos));
        officialityType.setValueMap(OperationsListUtils.getOfficialityTypeHashMap(officialityTypeDtos));
    }

    @Override
    public void setCommonMetadataConfigurations(List<ExternalItemDto> commonMetadataConfigurations) {
        ((SearchSingleCommonConfigurationItem) diffusionEditionForm.getItem(OperationDS.COMMON_METADATA)).setCommonConfigurationsList(commonMetadataConfigurations);
    }

    private void setTranslationsShowed(boolean translationsShowed) {
        // Set translationsShowed value to international fields
        identifiersForm.setTranslationsShowed(translationsShowed);
        identifiersEditionForm.setTranslationsShowed(translationsShowed);
        contentViewForm.setTranslationsShowed(translationsShowed);
        contentEditionForm.setTranslationsShowed(translationsShowed);
        diffusionForm.setTranslationsShowed(translationsShowed);
        diffusionEditionForm.setTranslationsShowed(translationsShowed);
        annotationsViewForm.setTranslationsShowed(translationsShowed);
        annotationsEditionForm.setTranslationsShowed(translationsShowed);
    }

    private void showInstanceListGridDeleteButton() {
        if (ClientSecurityUtils.canDeleteInstance(operationDto.getCode(), operationDto.getProcStatus())) {
            instanceListGridToolStrip.getDeleteButton().show();
        }
    }

    private boolean canOperationCodeBeEdited() {
        // Operation code can be edited only when ProcStatus is DRAFT
        return (productionDescriptorsEditionForm.getValue(OperationDS.PROC_STATUS_VIEW) != null
                && ProcStatusEnum.DRAFT.toString().equals(productionDescriptorsEditionForm.getValue(OperationDS.PROC_STATUS_VIEW)));
    }

    public boolean isOperationInternallyPublished() {
        return ProcStatusEnum.PUBLISH_INTERNALLY.equals(operationDto.getProcStatus());
    }

    public boolean isOperationExternallyPublished() {
        return ProcStatusEnum.PUBLISH_EXTERNALLY.equals(operationDto.getProcStatus());
    }

    @Override
    public void setFamilies(List<ExternalItemDto> families, int firstResult, int totalResults) {
        if (windowToAddFamiliesToOperation != null) {
            windowToAddFamiliesToOperation.setResources(families);
            windowToAddFamiliesToOperation.refreshSourcePaginationInfo(firstResult, families.size(), totalResults);
        }
    }
    @Override
    public void setUsersAccessControl(GetUsersAccessControlListResult result) {
        technicianInCharge.setValueMap(result.getUsers());
        assistantTechnician.setValueMap(result.getUsers());
    }

    // ------------------------------------------------------------------------------------------------------------
    // EXTERNAL RESOURCES DATA SETTERS
    // ------------------------------------------------------------------------------------------------------------

    @Override
    public void setItemSchemes(String formItemName, ExternalItemsResult result) {
        if (StringUtils.equals(OperationDS.SUBJECT_AREA, formItemName)) {
            ((SearchSrmItemLinkItemWithSchemeFilterItem) contentClassifiersEditionForm.getItem(formItemName)).setFilterResources(result.getExternalItemDtos(), result.getFirstResult(),
                    result.getExternalItemDtos().size(), result.getTotalResults());

        } else if (StringUtils.equals(OperationDS.SECONDARY_SUBJECT_AREAS, formItemName)) {
            ((SearchSrmListItemWithSchemeFilterItem) contentClassifiersEditionForm.getItem(formItemName)).setFilterResources(result.getExternalItemDtos(), result.getFirstResult(),
                    result.getTotalResults());

        } else if (StringUtils.equals(OperationDS.PRODUCER, formItemName)) {
            ((SearchSrmListItemWithSchemeFilterItem) productionDescriptorsEditionForm.getItem(formItemName)).setFilterResources(result.getExternalItemDtos(), result.getFirstResult(),
                    result.getTotalResults());

        } else if (StringUtils.equals(OperationDS.REG_RESPONSIBLE, formItemName)) {
            ((SearchSrmListItemWithSchemeFilterItem) productionDescriptorsEditionForm.getItem(formItemName)).setFilterResources(result.getExternalItemDtos(), result.getFirstResult(),
                    result.getTotalResults());

        } else if (StringUtils.equals(OperationDS.REG_CONTRIBUTOR, formItemName)) {
            ((SearchSrmListItemWithSchemeFilterItem) productionDescriptorsEditionForm.getItem(formItemName)).setFilterResources(result.getExternalItemDtos(), result.getFirstResult(),
                    result.getTotalResults());

        } else if (StringUtils.equals(OperationDS.PUBLISHER, formItemName)) {
            ((SearchSrmListItemWithSchemeFilterItem) diffusionEditionForm.getItem(formItemName)).setFilterResources(result.getExternalItemDtos(), result.getFirstResult(), result.getTotalResults());

        } else if (StringUtils.equals(OperationDS.UPDATE_FREQUENCY, formItemName)) {
            ((SearchSrmListItemWithSchemeFilterItem) diffusionEditionForm.getItem(formItemName)).setFilterResources(result.getExternalItemDtos(), result.getFirstResult(), result.getTotalResults());
        }
    }

    @Override
    public void setItems(String formItemName, ExternalItemsResult result) {
        if (StringUtils.equals(OperationDS.SUBJECT_AREA, formItemName)) {
            ((SearchSrmItemLinkItemWithSchemeFilterItem) contentClassifiersEditionForm.getItem(formItemName)).setResources(result.getExternalItemDtos(), result.getFirstResult(),
                    result.getExternalItemDtos().size(), result.getTotalResults());

        } else if (StringUtils.equals(OperationDS.SECONDARY_SUBJECT_AREAS, formItemName)) {
            ((SearchSrmListItemWithSchemeFilterItem) contentClassifiersEditionForm.getItem(formItemName)).setResources(result.getExternalItemDtos(), result.getFirstResult(), result.getTotalResults());

        } else if (StringUtils.equals(OperationDS.PRODUCER, formItemName)) {
            ((SearchSrmListItemWithSchemeFilterItem) productionDescriptorsEditionForm.getItem(formItemName)).setResources(result.getExternalItemDtos(), result.getFirstResult(),
                    result.getTotalResults());

        } else if (StringUtils.equals(OperationDS.REG_RESPONSIBLE, formItemName)) {
            ((SearchSrmListItemWithSchemeFilterItem) productionDescriptorsEditionForm.getItem(formItemName)).setResources(result.getExternalItemDtos(), result.getFirstResult(),
                    result.getTotalResults());

        } else if (StringUtils.equals(OperationDS.REG_CONTRIBUTOR, formItemName)) {
            ((SearchSrmListItemWithSchemeFilterItem) productionDescriptorsEditionForm.getItem(formItemName)).setResources(result.getExternalItemDtos(), result.getFirstResult(),
                    result.getTotalResults());

        } else if (StringUtils.equals(OperationDS.PUBLISHER, formItemName)) {
            ((SearchSrmListItemWithSchemeFilterItem) diffusionEditionForm.getItem(formItemName)).setResources(result.getExternalItemDtos(), result.getFirstResult(), result.getTotalResults());

        } else if (StringUtils.equals(OperationDS.UPDATE_FREQUENCY, formItemName)) {
            ((SearchSrmListItemWithSchemeFilterItem) diffusionEditionForm.getItem(formItemName)).setResources(result.getExternalItemDtos(), result.getFirstResult(), result.getTotalResults());
        }
    }

    // ------------------------------------------------------------------------------------------------------------
    // EXTERNAL RESOURCES ITEMS
    // ------------------------------------------------------------------------------------------------------------

    private SearchSrmItemLinkItemWithSchemeFilterItem createSubjectAreaItem(final String name, String title) {
        final SearchSrmItemLinkItemWithSchemeFilterItem item = new SearchSrmItemLinkItemWithSchemeFilterItem(name, title, StatisticalOperationsWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveItems(int firstResult, int maxResults, SrmItemRestCriteria webCriteria) {
                webCriteria.setExternalArtifactType(TypeExternalArtefactsEnum.CATEGORY);
                getUiHandlers().retrieveItems(name, webCriteria, firstResult, maxResults);
            }

            @Override
            protected void retrieveItemSchemes(int firstResult, int maxResults, SrmExternalResourceRestCriteria webCriteria) {
                webCriteria.setExternalArtifactType(TypeExternalArtefactsEnum.CATEGORY_SCHEME);
                getUiHandlers().retrieveItemSchemes(name, webCriteria, firstResult, maxResults);
            }
        };
        return item;
    }

    private SearchSrmListItemWithSchemeFilterItem createSecondarySubjectAreasItem() {
        final String field = OperationDS.SECONDARY_SUBJECT_AREAS;
        final SearchSrmListItemWithSchemeFilterItem item = new SearchSrmListItemWithSchemeFilterItem(field, getConstants().operationSubjectAreasSecondary(),
                StatisticalOperationsWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveItemSchemes(int firstResult, int maxResults, SrmExternalResourceRestCriteria webCriteria) {
                getUiHandlers().retrieveItemSchemes(field, webCriteria, new TypeExternalArtefactsEnum[]{TypeExternalArtefactsEnum.CATEGORY_SCHEME}, firstResult, maxResults);
            }

            @Override
            protected void retrieveItems(int firstResult, int maxResults, SrmItemRestCriteria webCriteria) {
                getUiHandlers().retrieveItems(field, webCriteria, new TypeExternalArtefactsEnum[]{TypeExternalArtefactsEnum.CATEGORY}, firstResult, maxResults);
            }
        };
        return item;
    }

    private SearchSrmListItemWithSchemeFilterItem createProducersItem() {
        final String field = OperationDS.PRODUCER;
        final SearchSrmListItemWithSchemeFilterItem item = new SearchSrmListItemWithSchemeFilterItem(field, getConstants().operationProducers(),
                StatisticalOperationsWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveItemSchemes(int firstResult, int maxResults, SrmExternalResourceRestCriteria webCriteria) {
                getUiHandlers().retrieveItemSchemes(field, webCriteria, new TypeExternalArtefactsEnum[]{TypeExternalArtefactsEnum.ORGANISATION_UNIT_SCHEME}, firstResult, maxResults);
            }

            @Override
            protected void retrieveItems(int firstResult, int maxResults, SrmItemRestCriteria webCriteria) {
                getUiHandlers().retrieveItems(field, webCriteria, new TypeExternalArtefactsEnum[]{TypeExternalArtefactsEnum.ORGANISATION_UNIT}, firstResult, maxResults);
            }
        };
        return item;
    }

    private SearchSrmListItemWithSchemeFilterItem createRegionaleResponsiblesItem() {
        final String field = OperationDS.REG_RESPONSIBLE;
        final SearchSrmListItemWithSchemeFilterItem item = new SearchSrmListItemWithSchemeFilterItem(field, getConstants().operationRegionalResponsibles(),
                StatisticalOperationsWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveItemSchemes(int firstResult, int maxResults, SrmExternalResourceRestCriteria webCriteria) {
                getUiHandlers().retrieveItemSchemes(field, webCriteria, new TypeExternalArtefactsEnum[]{TypeExternalArtefactsEnum.ORGANISATION_UNIT_SCHEME}, firstResult, maxResults);
            }

            @Override
            protected void retrieveItems(int firstResult, int maxResults, SrmItemRestCriteria webCriteria) {
                getUiHandlers().retrieveItems(field, webCriteria, new TypeExternalArtefactsEnum[]{TypeExternalArtefactsEnum.ORGANISATION_UNIT}, firstResult, maxResults);
            }
        };
        return item;
    }

    private SearchSrmListItemWithSchemeFilterItem createRegionaleContributorsItem() {
        final String field = OperationDS.REG_CONTRIBUTOR;
        final SearchSrmListItemWithSchemeFilterItem item = new SearchSrmListItemWithSchemeFilterItem(field, getConstants().operationRegionalContributors(),
                StatisticalOperationsWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveItemSchemes(int firstResult, int maxResults, SrmExternalResourceRestCriteria webCriteria) {
                getUiHandlers().retrieveItemSchemes(field, webCriteria,
                        new TypeExternalArtefactsEnum[]{TypeExternalArtefactsEnum.ORGANISATION_UNIT_SCHEME, TypeExternalArtefactsEnum.DATA_PROVIDER_SCHEME}, firstResult, maxResults);
            }

            @Override
            protected void retrieveItems(int firstResult, int maxResults, SrmItemRestCriteria webCriteria) {
                getUiHandlers().retrieveItems(field, webCriteria, new TypeExternalArtefactsEnum[]{TypeExternalArtefactsEnum.ORGANISATION_UNIT, TypeExternalArtefactsEnum.DATA_PROVIDER}, firstResult,
                        maxResults);
            }
        };
        return item;
    }

    private SearchSrmListItemWithSchemeFilterItem createPublishersItem() {
        final String field = OperationDS.PUBLISHER;
        final SearchSrmListItemWithSchemeFilterItem item = new SearchSrmListItemWithSchemeFilterItem(field, getConstants().operationPublisher(),
                StatisticalOperationsWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveItemSchemes(int firstResult, int maxResults, SrmExternalResourceRestCriteria webCriteria) {
                getUiHandlers().retrieveItemSchemes(field, webCriteria, new TypeExternalArtefactsEnum[]{TypeExternalArtefactsEnum.ORGANISATION_UNIT_SCHEME}, firstResult, maxResults);
            }

            @Override
            protected void retrieveItems(int firstResult, int maxResults, SrmItemRestCriteria webCriteria) {
                getUiHandlers().retrieveItems(field, webCriteria, new TypeExternalArtefactsEnum[]{TypeExternalArtefactsEnum.ORGANISATION_UNIT}, firstResult, maxResults);
            }
        };
        return item;
    }

    private SearchSrmListItemWithSchemeFilterItem createUpdateFrequencyItem() {
        final String field = OperationDS.UPDATE_FREQUENCY;
        final SearchSrmListItemWithSchemeFilterItem item = new SearchSrmListItemWithSchemeFilterItem(field, getConstants().operationUpdateFrequency(),
                StatisticalOperationsWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveItemSchemes(int firstResult, int maxResults, SrmExternalResourceRestCriteria webCriteria) {
                getUiHandlers().retrieveItemSchemes(field, webCriteria, new TypeExternalArtefactsEnum[]{TypeExternalArtefactsEnum.CODELIST}, firstResult, maxResults);
            }

            @Override
            protected void retrieveItems(int firstResult, int maxResults, SrmItemRestCriteria webCriteria) {
                getUiHandlers().retrieveItems(field, webCriteria, new TypeExternalArtefactsEnum[]{TypeExternalArtefactsEnum.CODE}, firstResult, maxResults);
            }
        };
        return item;
    }

    private SearchSingleCommonConfigurationItem createCommonMetadataItem(String name, String title) {
        final SearchSingleCommonConfigurationItem item = new SearchSingleCommonConfigurationItem(name, title) {

            @Override
            protected void retrieveCommonConfigurations(CommonConfigurationRestCriteria criteria) {
                getUiHandlers().retrieveCommonMetadataConfigurations(criteria);
            }
        };
        return item;
    }

    // ------------------------------------------------------------------------------------------------------------
    // CLICK HANDLERS
    // ------------------------------------------------------------------------------------------------------------

    private CustomLinkItemNavigationClickHandler getCustomLinkItemNavigationClickHandler() {
        return new CustomLinkItemNavigationClickHandler() {

            @Override
            public BaseUiHandlers getBaseUiHandlers() {
                return getUiHandlers();
            }
        };
    }

    // ------------------------------------------------------------------------------------------------------------
    // VALIDATORS
    // ------------------------------------------------------------------------------------------------------------
    private CustomValidator getTechnicianInChargeUserNotTheSameAsAssistantTechnicianUserValidator() {
        CustomValidator customValidator = new CustomValidator() {

            @Override
            protected boolean condition(Object value) {
                return CommonUtils.isTechnicianInChargeUserNotTheSameAsAssistantTechnicianUser(technicianInCharge.getValueAsString(), assistantTechnician.getValueAsString());
            }
        };

        customValidator.setErrorMessage(getMessages().validatorMessageTechnicianInChargeEqualsAssitantTechnician());
        return customValidator;
    }
}

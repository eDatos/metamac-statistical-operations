package org.siemac.metamac.statistical.operations.web.client.instance.view;

import static org.siemac.metamac.statistical.operations.web.client.OperationsWeb.getConstants;
import static org.siemac.metamac.statistical.operations.web.client.OperationsWeb.getMessages;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.operations.core.dto.CollMethodDto;
import org.siemac.metamac.statistical.operations.core.dto.CostDto;
import org.siemac.metamac.statistical.operations.core.dto.InstanceDto;
import org.siemac.metamac.statistical.operations.core.dto.InstanceTypeDto;
import org.siemac.metamac.statistical.operations.core.dto.SurveySourceDto;
import org.siemac.metamac.statistical.operations.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.operations.web.client.constants.StatisticalOperationsWebConstants;
import org.siemac.metamac.statistical.operations.web.client.enums.ToolStripButtonEnum;
import org.siemac.metamac.statistical.operations.web.client.instance.presenter.InstancePresenter;
import org.siemac.metamac.statistical.operations.web.client.instance.view.handlers.InstanceUiHandlers;
import org.siemac.metamac.statistical.operations.web.client.model.ds.InstanceDS;
import org.siemac.metamac.statistical.operations.web.client.utils.ClientSecurityUtils;
import org.siemac.metamac.statistical.operations.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.operations.web.client.utils.ConfigurationPropertiesUtils;
import org.siemac.metamac.statistical.operations.web.client.utils.OperationsListUtils;
import org.siemac.metamac.statistical.operations.web.client.utils.RequiredFieldUtils;
import org.siemac.metamac.statistical.operations.web.client.widgets.InstanceMainFormLayout;
import org.siemac.metamac.statistical.operations.web.client.widgets.external.SearchSrmListConceptAndConceptSchemeItem;
import org.siemac.metamac.statistical.operations.web.shared.external.ConceptSchemeTypeEnum;
import org.siemac.metamac.statistical.operations.web.shared.external.RestWebCriteriaUtils;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;
import org.siemac.metamac.web.common.client.utils.CustomRequiredValidator;
import org.siemac.metamac.web.common.client.utils.FormItemUtils;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.utils.TimeVariableWebUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomSelectItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageRichTextEditorItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.ExternalItemListItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.SearchMultiExternalItemSimpleItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.SearchSrmListItemWithSchemeFilterItem;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class InstanceViewImpl extends ViewWithUiHandlers<InstanceUiHandlers> implements InstancePresenter.InstanceView {

    private VLayout                panel;

    private InstanceMainFormLayout mainFormLayout;

    // IDENTIFIERS
    private GroupDynamicForm       identifiersViewForm;
    private GroupDynamicForm       identifiersEditionForm;
    private RequiredTextItem       code;

    // CONTENT DESCRIPTORS
    private GroupDynamicForm       contentDescriptorsForm;
    private GroupDynamicForm       contentDescriptorsEditionForm;

    // CLASS DESCRIPTORS
    private GroupDynamicForm       classViewForm;
    private GroupDynamicForm       classDescriptorsEditionForm;
    private CustomSelectItem       instanceTypeItem;

    // PRODUCTION DESCRIPTORS
    private GroupDynamicForm       productionDescriptorsForm;
    private GroupDynamicForm       productionDescriptorsEditionForm;
    private CustomSelectItem       collMethodItem;
    private CustomSelectItem       surveySourceItem;
    private CustomSelectItem       costItem;

    // DIFFUSION AND PUBLICATION
    private GroupDynamicForm       diffusionViewForm;
    private GroupDynamicForm       diffusionEditionForm;

    // QUALITY DESCRIPTORS
    private GroupDynamicForm       qualityViewForm;
    private GroupDynamicForm       qualityEditionForm;

    // ANNOTATIONS
    private GroupDynamicForm       annotationsViewForm;
    private GroupDynamicForm       annotationsEditionForm;

    private List<InstanceTypeDto>  instanceTypeDtos;
    private List<SurveySourceDto>  surveySourceDtos;
    private List<CollMethodDto>    collMethodDtos;
    private List<CostDto>          costDtos;

    private InstanceDto            instanceDto;
    public String                  operationCode;

    public InstanceViewImpl() {
        super();
        panel = new VLayout();

        // Instance

        mainFormLayout = new InstanceMainFormLayout();
        mainFormLayout.getTranslateToolStripButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                setTranslationsShowed(mainFormLayout.getTranslateToolStripButton().isSelected());
            }
        });
        mainFormLayout.getDeleteConfirmationWindow().getYesButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().deleteInstance(instanceDto);
            }
        });
        createViewForm();
        createEditionForm();

        VLayout subPanel = new VLayout();
        subPanel.setHeight100();
        subPanel.setOverflow(Overflow.SCROLL);
        subPanel.addMember(mainFormLayout);

        panel.addMember(subPanel);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setUiHandlers(InstanceUiHandlers uiHandlers) {
        super.setUiHandlers(uiHandlers);

        // Set uiHandlers in formItems

        ((SearchSrmListConceptAndConceptSchemeItem) contentDescriptorsEditionForm.getItem(InstanceDS.STAT_CONC_DEF)).setUiHandlers(uiHandlers);
        ((SearchSrmListConceptAndConceptSchemeItem) contentDescriptorsEditionForm.getItem(InstanceDS.MEASURES)).setUiHandlers(uiHandlers);
    }

    /*
     * GWTP will call setInSlot when a child presenter asks to be added under this view
     */
    @Override
    public void setInSlot(Object slot, Widget content) {
        if (slot == InstancePresenter.TYPE_SetContextAreaContentToolBar) {
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
    public void setInstance(InstanceDto instanceDto, String operationCode) {
        this.instanceDto = instanceDto;
        this.operationCode = operationCode;

        // Security
        mainFormLayout.setCanEdit(ClientSecurityUtils.canUpdateInstance(operationCode));
        mainFormLayout.setCanDelete(ClientSecurityUtils.canDeleteInstance(operationCode, instanceDto.getProcStatus()));
        mainFormLayout.setOperationCode(operationCode);

        mainFormLayout.setViewMode();
        mainFormLayout.updatePublishSection(instanceDto.getProcStatus());
        // Set Instance
        mainFormLayout.setTitleLabelContents(InternationalStringUtils.getLocalisedString(instanceDto.getTitle()));
        setInstanceViewMode(instanceDto);
        setInstanceEditionMode(instanceDto);
    }

    @Override
    public HasClickHandlers getSave() {
        return mainFormLayout.getSave();
    }

    @Override
    public void onInstanceSaved(InstanceDto instanceDto) {
        setInstance(instanceDto, this.operationCode); // Operation code should be the same
    }

    @Override
    public InstanceDto getInstance(InstanceDto instanceDto) {

        // IDENTIFIERS

        instanceDto.setCode(code.getValueAsString());
        instanceDto.setTitle(identifiersEditionForm.getValueAsInternationalStringDto(InstanceDS.TITLE));
        instanceDto.setAcronym(identifiersEditionForm.getValueAsInternationalStringDto(InstanceDS.ACRONYM));

        // CONTENT CLASSIFIERS

        // CONTENT DESCRIPTORS

        instanceDto.setDataDescription(contentDescriptorsEditionForm.getValueAsInternationalStringDto(InstanceDS.DATA_DESCRIPTION));
        instanceDto.setStatisticalPopulation(contentDescriptorsEditionForm.getValueAsInternationalStringDto(InstanceDS.STATISTICAL_POPULATION));

        List<ExternalItemDto> statisticalUnits = ((ExternalItemListItem) contentDescriptorsEditionForm.getItem(InstanceDS.STATISTICAL_UNIT)).getExternalItemDtos();
        instanceDto.getStatisticalUnit().clear();
        instanceDto.getStatisticalUnit().addAll(statisticalUnits);

        List<ExternalItemDto> geographicGranularities = ((ExternalItemListItem) contentDescriptorsEditionForm.getItem(InstanceDS.GEOGRAPHIC_GRANULARITIES)).getExternalItemDtos();
        instanceDto.getGeographicGranularity().clear();
        instanceDto.getGeographicGranularity().addAll(geographicGranularities);

        instanceDto.setGeographicComparability(contentDescriptorsEditionForm.getValueAsInternationalStringDto(InstanceDS.GEOGRAPHIC_COMPARABILITY));

        List<ExternalItemDto> temporalGranularities = ((ExternalItemListItem) contentDescriptorsEditionForm.getItem(InstanceDS.TEMPORAL_GRANULARITIES)).getExternalItemDtos();
        instanceDto.getTemporalGranularity().clear();
        instanceDto.getTemporalGranularity().addAll(temporalGranularities);

        instanceDto.setTemporalComparability(contentDescriptorsEditionForm.getValueAsInternationalStringDto(InstanceDS.TEMPORAL_COMPARABILITY));
        instanceDto.setBasePeriod(contentDescriptorsEditionForm.getValueAsString(InstanceDS.BASE_PERIOD));

        List<ExternalItemDto> measures = ((ExternalItemListItem) contentDescriptorsEditionForm.getItem(InstanceDS.MEASURES)).getExternalItemDtos();
        instanceDto.getUnitMeasure().clear();
        instanceDto.getUnitMeasure().addAll(measures);

        instanceDto.setStatConcDef(contentDescriptorsEditionForm.getValueAsInternationalStringDto(InstanceDS.STAT_CONC_DEF_DESCRIPTION));

        List<ExternalItemDto> statConcDef = ((ExternalItemListItem) contentDescriptorsEditionForm.getItem(InstanceDS.STAT_CONC_DEF)).getExternalItemDtos();
        instanceDto.getStatConcDefList().clear();
        instanceDto.getStatConcDefList().addAll(statConcDef);

        instanceDto.setClassSystem(contentDescriptorsEditionForm.getValueAsInternationalStringDto(InstanceDS.CLASS_SYSTEM_DESCRIPTION));

        List<ExternalItemDto> classSystems = ((ExternalItemListItem) contentDescriptorsEditionForm.getItem(InstanceDS.CLASS_SYSTEM_LIST)).getExternalItemDtos();
        instanceDto.getClassSystemList().clear();
        instanceDto.getClassSystemList().addAll(classSystems);

        // CLASS DESCRIPTORS

        instanceDto.setInstanceType(OperationsListUtils.getInstanceTypeDto(instanceTypeItem.getValueAsString(), instanceTypeDtos));

        // PRODUCTION DESCRIPTORS

        instanceDto.setDocMethod(productionDescriptorsEditionForm.getValueAsInternationalStringDto(InstanceDS.DOC_METHOD));
        instanceDto.setSurveySource(OperationsListUtils.getSurveySourceDto(surveySourceItem.getValueAsString(), surveySourceDtos));
        instanceDto.setCollMethod(OperationsListUtils.getCollMethodDto(collMethodItem.getValueAsString(), collMethodDtos));

        List<ExternalItemDto> informationSuppliers = ((ExternalItemListItem) productionDescriptorsEditionForm.getItem(InstanceDS.INFORMATION_SUPPLIERS)).getExternalItemDtos();
        instanceDto.getInformationSuppliers().clear();
        instanceDto.getInformationSuppliers().addAll(informationSuppliers);

        List<ExternalItemDto> freqColls = ((ExternalItemListItem) productionDescriptorsEditionForm.getItem(InstanceDS.FREQ_COLL)).getExternalItemDtos();
        instanceDto.getFreqColl().clear();
        instanceDto.getFreqColl().addAll(freqColls);

        instanceDto.setDataValidation(productionDescriptorsEditionForm.getValueAsInternationalStringDto(InstanceDS.DATA_VALIDATION));
        instanceDto.setDataCompilation(productionDescriptorsEditionForm.getValueAsInternationalStringDto(InstanceDS.DATA_COMPILATION));
        instanceDto.setAdjustment(productionDescriptorsEditionForm.getValueAsInternationalStringDto(InstanceDS.ADJUSTMENT));
        instanceDto.setSeasonalAdjustment(productionDescriptorsEditionForm.getValueAsInternationalStringDto(InstanceDS.SEASONAL_ADJUSTMENT));
        instanceDto.setCostBurden(productionDescriptorsEditionForm.getValueAsInternationalStringDto(InstanceDS.COST_BURDEN));
        instanceDto.getCost().clear();
        instanceDto.getCost().addAll(OperationsListUtils.getCostDtos(costItem.getValues(), costDtos));

        // QUALITY DESCRIPTORS

        instanceDto.setQualityDoc(qualityEditionForm.getValueAsInternationalStringDto(InstanceDS.QUALITY_DOC));
        instanceDto.setQualityAssure(qualityEditionForm.getValueAsInternationalStringDto(InstanceDS.QUALITY_ASSURE));
        instanceDto.setQualityAssmnt(qualityEditionForm.getValueAsInternationalStringDto(InstanceDS.QUALITY_ASSMNT));

        instanceDto.setUserNeeds(qualityEditionForm.getValueAsInternationalStringDto(InstanceDS.USER_NEEDS));
        instanceDto.setUserSat(qualityEditionForm.getValueAsInternationalStringDto(InstanceDS.USER_SAT));
        instanceDto.setCompleteness(qualityEditionForm.getValueAsInternationalStringDto(InstanceDS.COMPLETENESS));
        instanceDto.setTimeliness(qualityEditionForm.getValueAsInternationalStringDto(InstanceDS.TIMELINESS));
        instanceDto.setPunctuality(qualityEditionForm.getValueAsInternationalStringDto(InstanceDS.PUNCTUALITY));
        instanceDto.setAccuracyOverall(qualityEditionForm.getValueAsInternationalStringDto(InstanceDS.ACCURACY_OVERALL));
        instanceDto.setSamplingErr(qualityEditionForm.getValueAsInternationalStringDto(InstanceDS.SAMPLING_ERROR));
        instanceDto.setNonsamplingErr(qualityEditionForm.getValueAsInternationalStringDto(InstanceDS.NONSAMPLING_ERR));

        instanceDto.setCoverageErr(qualityEditionForm.getValueAsInternationalStringDto(InstanceDS.COVERAGE_ERROR));
        instanceDto.setMeasurementErr(qualityEditionForm.getValueAsInternationalStringDto(InstanceDS.MEASUREMENT_ERROR));
        instanceDto.setNonresponseErr(qualityEditionForm.getValueAsInternationalStringDto(InstanceDS.NONRESPONSE_ERROR));
        instanceDto.setProcessingErr(qualityEditionForm.getValueAsInternationalStringDto(InstanceDS.PROCESSING_ERROR));
        instanceDto.setModelErr(qualityEditionForm.getValueAsInternationalStringDto(InstanceDS.MODEL_ERROR));

        instanceDto.setCoherXDomain(qualityEditionForm.getValueAsInternationalStringDto(InstanceDS.COHER_X_DOM));
        instanceDto.setCoherSubAnualAnual(qualityEditionForm.getValueAsInternationalStringDto(InstanceDS.COHER_SUBANUAL_ANUAL));
        instanceDto.setCoherNationalAccounts(qualityEditionForm.getValueAsInternationalStringDto(InstanceDS.COHER_NATIONAL_ACCOUNTS));
        instanceDto.setCoherInternal(qualityEditionForm.getValueAsInternationalStringDto(InstanceDS.COHER_INTERNAL));

        // ANNOTATIONS

        instanceDto.setComment(annotationsEditionForm.getValueAsInternationalStringDto(InstanceDS.COMMENTS));
        instanceDto.setNotes(annotationsEditionForm.getValueAsInternationalStringDto(InstanceDS.NOTES));

        return instanceDto;
    }

    @Override
    public boolean validate() {
        return identifiersEditionForm.validate(false) &&

        contentDescriptorsEditionForm.validate(false) && classDescriptorsEditionForm.validate(false) && productionDescriptorsEditionForm.validate(false) && diffusionEditionForm.validate(false)
                && qualityEditionForm.validate(false) && annotationsEditionForm.validate(false);
    }

    private void createViewForm() {
        // Identifiers
        identifiersViewForm = new GroupDynamicForm(getConstants().operationIdentifiers());
        ViewTextItem identifier = new ViewTextItem(InstanceDS.CODE, getConstants().instanceCode());
        ViewMultiLanguageTextItem title = new ViewMultiLanguageTextItem(InstanceDS.TITLE, getConstants().instanceTitle());
        ViewMultiLanguageTextItem alternativeTitle = new ViewMultiLanguageTextItem(InstanceDS.ACRONYM, getConstants().instanceAcronym());
        ViewTextItem urn = new ViewTextItem(InstanceDS.URN, getConstants().instanceUrn());
        identifiersViewForm.setFields(identifier, title, alternativeTitle, urn);

        // Content classifiers

        // Content descriptors
        contentDescriptorsForm = new GroupDynamicForm(getConstants().instanceContentDescriptors());
        ViewMultiLanguageTextItem dataDescription = new ViewMultiLanguageTextItem(InstanceDS.DATA_DESCRIPTION, getConstants().instanceDataDescription());
        ViewMultiLanguageTextItem statisticalPopulation = new ViewMultiLanguageTextItem(InstanceDS.STATISTICAL_POPULATION, getConstants().instanceStatisticalPopulation());
        ExternalItemListItem statisticalUnit = new ExternalItemListItem(InstanceDS.STATISTICAL_UNIT, getConstants().instanceStatisticalUnit(), false);
        ExternalItemListItem geographicGranularities = new ExternalItemListItem(InstanceDS.GEOGRAPHIC_GRANULARITIES, getConstants().instanceGeographicGranularity(), false);
        ViewMultiLanguageTextItem geographicComparability = new ViewMultiLanguageTextItem(InstanceDS.GEOGRAPHIC_COMPARABILITY, getConstants().instanceGeographicComparability());
        ExternalItemListItem temporalGranularities = new ExternalItemListItem(InstanceDS.TEMPORAL_GRANULARITIES, getConstants().instanceTemporalGranularity(), false);
        ViewMultiLanguageTextItem temporalComparability = new ViewMultiLanguageTextItem(InstanceDS.TEMPORAL_COMPARABILITY, getConstants().instanceTemporalComparability());
        ViewTextItem basePeriodItem = new ViewTextItem(InstanceDS.BASE_PERIOD, getConstants().instanceBasePeriod());
        ExternalItemListItem measures = new ExternalItemListItem(InstanceDS.MEASURES, getConstants().instanceUnitMeasure(), false);
        ViewMultiLanguageTextItem statConcDefDescription = new ViewMultiLanguageTextItem(InstanceDS.STAT_CONC_DEF_DESCRIPTION, getConstants().instanceStatisticalConceptDefinition());
        ExternalItemListItem statConcDefList = new ExternalItemListItem(InstanceDS.STAT_CONC_DEF, getConstants().instanceStatisticalConceptsDefinitions(), false);
        ExternalItemListItem classSystemList = new ExternalItemListItem(InstanceDS.CLASS_SYSTEM_LIST, getConstants().instanceClassSystemList(), false);
        ViewMultiLanguageTextItem classSystemDescription = new ViewMultiLanguageTextItem(InstanceDS.CLASS_SYSTEM_DESCRIPTION, getConstants().instanceClassSystem());
        contentDescriptorsForm.setFields(dataDescription, statisticalPopulation, statisticalUnit, basePeriodItem, geographicGranularities, temporalGranularities, geographicComparability,
                temporalComparability, measures, statConcDefDescription, statConcDefList, classSystemList, classSystemDescription);

        // Class descriptors
        classViewForm = new GroupDynamicForm(getConstants().instanceClassDescriptors());
        ViewTextItem instanceType = new ViewTextItem(InstanceDS.INSTANCE_TYPE, getConstants().instanceType());
        classViewForm.setFields(instanceType);

        // Production descriptors
        productionDescriptorsForm = new GroupDynamicForm(getConstants().instanceProductionDescriptors());
        ViewTextItem createdDate = new ViewTextItem(InstanceDS.CREATED_DATE, getConstants().instanceCreatedDate());
        ViewTextItem internalInventoryDate = new ViewTextItem(InstanceDS.INTERNAL_INVENTORY_DATE, getConstants().instanceInternalInventoryDate());
        ViewTextItem procStatus = new ViewTextItem(InstanceDS.PROC_STATUS, getConstants().instanceProcStatus());
        ViewMultiLanguageTextItem staticDocMethodItem = new ViewMultiLanguageTextItem(InstanceDS.DOC_METHOD, getConstants().instanceDocMethod());
        ViewTextItem surveySource = new ViewTextItem(InstanceDS.STATISTICAL_OPERATION_SOURCE, getConstants().instanceStatisticalOperationSource());
        ViewTextItem collMethod = new ViewTextItem(InstanceDS.COLL_METHOD, getConstants().instanceCollMethod());
        ExternalItemListItem informationSuppliers = new ExternalItemListItem(InstanceDS.INFORMATION_SUPPLIERS, getConstants().instanceInformationSuppliers(), false);
        ExternalItemListItem freqColl = new ExternalItemListItem(InstanceDS.FREQ_COLL, getConstants().instanceFreqColl(), false);
        ViewMultiLanguageTextItem staticDataValidationItem = new ViewMultiLanguageTextItem(InstanceDS.DATA_VALIDATION, getConstants().instanceDataValidation());
        ViewMultiLanguageTextItem staticDataCompilationItem = new ViewMultiLanguageTextItem(InstanceDS.DATA_COMPILATION, getConstants().instanceDataCompilation());
        ViewMultiLanguageTextItem staticAdjustmentItem = new ViewMultiLanguageTextItem(InstanceDS.ADJUSTMENT, getConstants().instanceAdjustment());
        ViewMultiLanguageTextItem staticSeasonAdjustmentItem = new ViewMultiLanguageTextItem(InstanceDS.SEASONAL_ADJUSTMENT, getConstants().instanceSeasonalAdjustment());
        ViewMultiLanguageTextItem staticCostBurdenItem = new ViewMultiLanguageTextItem(InstanceDS.COST_BURDEN, getConstants().instanceCostBurden());
        ViewTextItem cost = new ViewTextItem(InstanceDS.COST, getConstants().instanceCost());
        productionDescriptorsForm.setFields(createdDate, internalInventoryDate, procStatus, staticDocMethodItem, surveySource, collMethod, informationSuppliers, freqColl, staticDataValidationItem,
                staticDataCompilationItem, staticAdjustmentItem, staticSeasonAdjustmentItem, staticCostBurdenItem, cost);

        // Diffusion and Publication
        diffusionViewForm = new GroupDynamicForm(getConstants().instanceDiffusionDescriptors());
        ViewTextItem inventoryDate = new ViewTextItem(InstanceDS.INVENTORY_DATE, getConstants().instanceInventoryDate());
        diffusionViewForm.setFields(inventoryDate);

        // Quality descriptors
        qualityViewForm = new GroupDynamicForm(getConstants().instanceQualityDescriptors());
        ViewMultiLanguageTextItem staticQualityDocItem = new ViewMultiLanguageTextItem(InstanceDS.QUALITY_DOC, getConstants().instanceQDoc());
        ViewMultiLanguageTextItem staticQualityAssureItem = new ViewMultiLanguageTextItem(InstanceDS.QUALITY_ASSURE, getConstants().instanceQAssure());
        ViewMultiLanguageTextItem staticQualityAssesmentItem = new ViewMultiLanguageTextItem(InstanceDS.QUALITY_ASSMNT, getConstants().instanceQAssmnt());
        ViewMultiLanguageTextItem staticUserNeedsItem = new ViewMultiLanguageTextItem(InstanceDS.USER_NEEDS, getConstants().instanceUserNeeds());
        ViewMultiLanguageTextItem staticUserSatItem = new ViewMultiLanguageTextItem(InstanceDS.USER_SAT, getConstants().instanceUserSat());
        ViewMultiLanguageTextItem staticCompletenessItem = new ViewMultiLanguageTextItem(InstanceDS.COMPLETENESS, getConstants().instanceCompleteness());
        ViewMultiLanguageTextItem staticTimelinessItem = new ViewMultiLanguageTextItem(InstanceDS.TIMELINESS, getConstants().instanceTimeliness());
        ViewMultiLanguageTextItem staticPunctualityItem = new ViewMultiLanguageTextItem(InstanceDS.PUNCTUALITY, getConstants().instancePunctuality());
        ViewMultiLanguageTextItem staticAccuracyOverallItem = new ViewMultiLanguageTextItem(InstanceDS.ACCURACY_OVERALL, getConstants().instanceAccuracyOverall());
        ViewMultiLanguageTextItem staticSamplingErrItem = new ViewMultiLanguageTextItem(InstanceDS.SAMPLING_ERROR, getConstants().instanceSamplingErr());
        ViewMultiLanguageTextItem staticNonSamplingErrItem = new ViewMultiLanguageTextItem(InstanceDS.NONSAMPLING_ERR, getConstants().instanceNonSamplingErr());

        ViewMultiLanguageTextItem staticCoverageErrItem = new ViewMultiLanguageTextItem(InstanceDS.COVERAGE_ERROR, getConstants().instanceCoverageErr());
        ViewMultiLanguageTextItem staticMeasurementErrItem = new ViewMultiLanguageTextItem(InstanceDS.MEASUREMENT_ERROR, getConstants().instanceMeasurementErr());
        ViewMultiLanguageTextItem staticNonResponseErrItem = new ViewMultiLanguageTextItem(InstanceDS.NONRESPONSE_ERROR, getConstants().instanceNonResponseErr());
        ViewMultiLanguageTextItem staticProcessingErrItem = new ViewMultiLanguageTextItem(InstanceDS.PROCESSING_ERROR, getConstants().instanceProcessingErr());
        ViewMultiLanguageTextItem staticModelItem = new ViewMultiLanguageTextItem(InstanceDS.MODEL_ERROR, getConstants().instanceModelErr());

        ViewMultiLanguageTextItem staticCoherXDomItem = new ViewMultiLanguageTextItem(InstanceDS.COHER_X_DOM, getConstants().instanceCoherXDom());

        ViewMultiLanguageTextItem staticCoherSubAnualItem = new ViewMultiLanguageTextItem(InstanceDS.COHER_SUBANUAL_ANUAL, getConstants().instanceCoherSubAnual());
        ViewMultiLanguageTextItem staticCoherNationalAccountsItem = new ViewMultiLanguageTextItem(InstanceDS.COHER_NATIONAL_ACCOUNTS, getConstants().instanceCoherNationalAccounts());

        ViewMultiLanguageTextItem staticCoherInternalItem = new ViewMultiLanguageTextItem(InstanceDS.COHER_INTERNAL, getConstants().instanceCoherInter());
        qualityViewForm.setFields(staticQualityDocItem, staticQualityAssureItem, staticQualityAssesmentItem, staticUserNeedsItem, staticUserSatItem, staticCompletenessItem, staticTimelinessItem,
                staticPunctualityItem, staticAccuracyOverallItem, staticSamplingErrItem, staticNonSamplingErrItem, staticCoverageErrItem, staticMeasurementErrItem, staticNonResponseErrItem,
                staticProcessingErrItem, staticModelItem, staticCoherXDomItem, staticCoherSubAnualItem, staticCoherNationalAccountsItem, staticCoherInternalItem);

        // Annotations
        annotationsViewForm = new GroupDynamicForm(getConstants().instanceAnnotations());
        ViewMultiLanguageTextItem staticCommentItem = new ViewMultiLanguageTextItem(InstanceDS.COMMENTS, getConstants().instanceComments());
        ViewMultiLanguageTextItem staticNotesItem = new ViewMultiLanguageTextItem(InstanceDS.NOTES, getConstants().instanceNotes());
        annotationsViewForm.setFields(staticCommentItem, staticNotesItem);

        mainFormLayout.addViewCanvas(identifiersViewForm);

        mainFormLayout.addViewCanvas(contentDescriptorsForm);
        mainFormLayout.addViewCanvas(classViewForm);
        mainFormLayout.addViewCanvas(productionDescriptorsForm);
        mainFormLayout.addViewCanvas(diffusionViewForm);
        mainFormLayout.addViewCanvas(qualityViewForm);
        mainFormLayout.addViewCanvas(annotationsViewForm);
    }

    private void createEditionForm() {
        // Identifiers
        identifiersEditionForm = new GroupDynamicForm(getConstants().instanceIdentifiers());

        code = new RequiredTextItem(InstanceDS.CODE, getConstants().instanceCode());
        code.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return canInstanceCodeBeEdited();
            }
        });
        code.setValidators(CommonWebUtils.getSemanticIdentifierCustomValidator());
        ViewTextItem staticCode = new ViewTextItem(InstanceDS.CODE_VIEW, getConstants().instanceCode());
        staticCode.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return !canInstanceCodeBeEdited();
            }
        });

        MultiLanguageTextItem title = new MultiLanguageTextItem(InstanceDS.TITLE, getConstants().instanceTitle());
        title.setRequired(true);
        MultiLanguageTextItem acronym = new MultiLanguageTextItem(InstanceDS.ACRONYM, getConstants().instanceAcronym());
        ViewTextItem urn = new ViewTextItem(InstanceDS.URN, getConstants().instanceUrn());
        identifiersEditionForm.setFields(staticCode, code, title, acronym, urn);

        // Content classifiers

        // Content descriptors
        contentDescriptorsEditionForm = new GroupDynamicForm(getConstants().instanceContentDescriptors());
        MultiLanguageRichTextEditorItem dataDescriptionItem = new MultiLanguageRichTextEditorItem(InstanceDS.DATA_DESCRIPTION, getConstants().instanceDataDescription());
        MultiLanguageRichTextEditorItem statisticalPopulationItem = new MultiLanguageRichTextEditorItem(InstanceDS.STATISTICAL_POPULATION, getConstants().instanceStatisticalPopulation());
        ExternalItemListItem statisticalUnitItem = createStatisticalUnitsItem();
        ExternalItemListItem geographicGranularities = createGeographicGranularities();
        MultiLanguageRichTextEditorItem geographicalComparabilityItem = new MultiLanguageRichTextEditorItem(InstanceDS.GEOGRAPHIC_COMPARABILITY, getConstants().instanceGeographicComparability());
        ExternalItemListItem temporalGranularities = createTemporalGranularities();
        MultiLanguageRichTextEditorItem temporalComparabilityItem = new MultiLanguageRichTextEditorItem(InstanceDS.TEMPORAL_COMPARABILITY, getConstants().instanceTemporalComparability());
        TextItem basePeriodItem = new TextItem(InstanceDS.BASE_PERIOD, getConstants().instanceBasePeriod());
        basePeriodItem.setValidators(TimeVariableWebUtils.getTimeCustomValidator());

        ExternalItemListItem measuresItem = createMeasures();

        MultiLanguageRichTextEditorItem statConcDefDescriptionItem = new MultiLanguageRichTextEditorItem(InstanceDS.STAT_CONC_DEF_DESCRIPTION, getConstants().instanceStatisticalConceptDefinition());

        ExternalItemListItem statConcDefItem = createStatConcDef();

        ExternalItemListItem classSystemItem = createClassSystemItem();

        MultiLanguageRichTextEditorItem classSystemDescriptionItem = new MultiLanguageRichTextEditorItem(InstanceDS.CLASS_SYSTEM_DESCRIPTION, getConstants().instanceClassSystem());

        contentDescriptorsEditionForm.setFields(dataDescriptionItem, statisticalPopulationItem, statisticalUnitItem, basePeriodItem, geographicGranularities, temporalGranularities,
                geographicalComparabilityItem, temporalComparabilityItem, measuresItem, statConcDefDescriptionItem, statConcDefItem, classSystemItem, classSystemDescriptionItem);

        // Class descriptors
        classDescriptorsEditionForm = new GroupDynamicForm(getConstants().instanceClassDescriptors());

        instanceTypeItem = new CustomSelectItem(InstanceDS.INSTANCE_TYPE, getConstants().instanceType());
        instanceTypeItem.setValidators(new CustomRequiredValidator() {

            @Override
            protected boolean condition(Object value) {
                return CommonUtils.isInternallyOrExternallyPublished(instanceDto) ? !StringUtils.isBlank(instanceTypeItem.getValueAsString()) : true;
            }
        });

        classDescriptorsEditionForm.setFields(instanceTypeItem);

        // Production descriptors
        productionDescriptorsEditionForm = new GroupDynamicForm(getConstants().instanceProductionDescriptors());
        ViewTextItem createdDate = new ViewTextItem(InstanceDS.CREATED_DATE, getConstants().instanceCreatedDate());
        ViewTextItem internalInventoryDate = new ViewTextItem(InstanceDS.INTERNAL_INVENTORY_DATE, getConstants().instanceInternalInventoryDate());

        ViewTextItem procStatus = new ViewTextItem(InstanceDS.PROC_STATUS, getConstants().instanceProcStatus());
        ViewTextItem staticProcStatus = new ViewTextItem(InstanceDS.PROC_STATUS_VIEW, getConstants().instanceProcStatus());
        staticProcStatus.setShowIfCondition(FormItemUtils.getFalseFormItemIfFunction());

        MultiLanguageRichTextEditorItem docMethodItem = new MultiLanguageRichTextEditorItem(InstanceDS.DOC_METHOD, getConstants().instanceDocMethod());
        surveySourceItem = new CustomSelectItem(InstanceDS.STATISTICAL_OPERATION_SOURCE, getConstants().instanceStatisticalOperationSource());
        collMethodItem = new CustomSelectItem(InstanceDS.COLL_METHOD, getConstants().instanceCollMethod());
        ExternalItemListItem informationSuppliersItem = createInformationSuppliersItem();
        ExternalItemListItem freqCollItem = createFreqColl();
        MultiLanguageRichTextEditorItem dataValidationItem = new MultiLanguageRichTextEditorItem(InstanceDS.DATA_VALIDATION, getConstants().instanceDataValidation());
        MultiLanguageRichTextEditorItem dataCompilationItem = new MultiLanguageRichTextEditorItem(InstanceDS.DATA_COMPILATION, getConstants().instanceDataCompilation());
        MultiLanguageRichTextEditorItem adjustmentItem = new MultiLanguageRichTextEditorItem(InstanceDS.ADJUSTMENT, getConstants().instanceAdjustment());
        MultiLanguageRichTextEditorItem seasonAdjustmentItem = new MultiLanguageRichTextEditorItem(InstanceDS.SEASONAL_ADJUSTMENT, getConstants().instanceSeasonalAdjustment());
        MultiLanguageRichTextEditorItem costBurdenItem = new MultiLanguageRichTextEditorItem(InstanceDS.COST_BURDEN, getConstants().instanceCostBurden());
        costItem = new CustomSelectItem(InstanceDS.COST, getConstants().instanceCost());
        costItem.setMultiple(true);
        productionDescriptorsEditionForm.setFields(createdDate, internalInventoryDate, staticProcStatus, procStatus, docMethodItem, surveySourceItem, collMethodItem, informationSuppliersItem,
                freqCollItem, dataValidationItem, dataCompilationItem, adjustmentItem, seasonAdjustmentItem, costBurdenItem, costItem);

        // Diffusion and Publication
        diffusionEditionForm = new GroupDynamicForm(getConstants().instanceDiffusionDescriptors());
        ViewTextItem inventoryDate = new ViewTextItem(InstanceDS.INVENTORY_DATE, getConstants().instanceInventoryDate());
        diffusionEditionForm.setFields(inventoryDate);

        // Quality Descriptors
        qualityEditionForm = new GroupDynamicForm(getConstants().instanceQualityDescriptors());
        MultiLanguageRichTextEditorItem qualityDocItem = new MultiLanguageRichTextEditorItem(InstanceDS.QUALITY_DOC, getConstants().instanceQDoc());
        MultiLanguageRichTextEditorItem qualityAssureItem = new MultiLanguageRichTextEditorItem(InstanceDS.QUALITY_ASSURE, getConstants().instanceQAssure());
        MultiLanguageRichTextEditorItem qualityAssesmentItem = new MultiLanguageRichTextEditorItem(InstanceDS.QUALITY_ASSMNT, getConstants().instanceQAssmnt());
        MultiLanguageRichTextEditorItem userNeedsItem = new MultiLanguageRichTextEditorItem(InstanceDS.USER_NEEDS, getConstants().instanceUserNeeds());
        MultiLanguageRichTextEditorItem userSatItem = new MultiLanguageRichTextEditorItem(InstanceDS.USER_SAT, getConstants().instanceUserSat());
        MultiLanguageRichTextEditorItem completenessItem = new MultiLanguageRichTextEditorItem(InstanceDS.COMPLETENESS, getConstants().instanceCompleteness());
        MultiLanguageRichTextEditorItem timelinessItem = new MultiLanguageRichTextEditorItem(InstanceDS.TIMELINESS, getConstants().instanceTimeliness());
        MultiLanguageRichTextEditorItem punctualityItem = new MultiLanguageRichTextEditorItem(InstanceDS.PUNCTUALITY, getConstants().instancePunctuality());
        MultiLanguageRichTextEditorItem accuracyOverallItem = new MultiLanguageRichTextEditorItem(InstanceDS.ACCURACY_OVERALL, getConstants().instanceAccuracyOverall());
        MultiLanguageRichTextEditorItem samplingErrItem = new MultiLanguageRichTextEditorItem(InstanceDS.SAMPLING_ERROR, getConstants().instanceSamplingErr());
        MultiLanguageRichTextEditorItem nonSamplingErrItem = new MultiLanguageRichTextEditorItem(InstanceDS.NONSAMPLING_ERR, getConstants().instanceNonSamplingErr());

        MultiLanguageRichTextEditorItem staticCoverageErrItem = new MultiLanguageRichTextEditorItem(InstanceDS.COVERAGE_ERROR, getConstants().instanceCoverageErr());
        MultiLanguageRichTextEditorItem staticMeasurementErrItem = new MultiLanguageRichTextEditorItem(InstanceDS.MEASUREMENT_ERROR, getConstants().instanceMeasurementErr());
        MultiLanguageRichTextEditorItem staticNonResponseErrItem = new MultiLanguageRichTextEditorItem(InstanceDS.NONRESPONSE_ERROR, getConstants().instanceNonResponseErr());
        MultiLanguageRichTextEditorItem staticProcessingErrItem = new MultiLanguageRichTextEditorItem(InstanceDS.PROCESSING_ERROR, getConstants().instanceProcessingErr());
        MultiLanguageRichTextEditorItem staticModelItem = new MultiLanguageRichTextEditorItem(InstanceDS.MODEL_ERROR, getConstants().instanceModelErr());

        MultiLanguageRichTextEditorItem coherXDomItem = new MultiLanguageRichTextEditorItem(InstanceDS.COHER_X_DOM, getConstants().instanceCoherXDom());
        MultiLanguageRichTextEditorItem coherSubanualAnualItem = new MultiLanguageRichTextEditorItem(InstanceDS.COHER_SUBANUAL_ANUAL, getConstants().instanceCoherSubAnual());
        MultiLanguageRichTextEditorItem coherNationalAccountsItem = new MultiLanguageRichTextEditorItem(InstanceDS.COHER_NATIONAL_ACCOUNTS, getConstants().instanceCoherNationalAccounts());
        MultiLanguageRichTextEditorItem coherInternalItem = new MultiLanguageRichTextEditorItem(InstanceDS.COHER_INTERNAL, getConstants().instanceCoherInter());
        qualityEditionForm.setFields(qualityDocItem, qualityAssureItem, qualityAssesmentItem, userNeedsItem, userSatItem, completenessItem, timelinessItem, punctualityItem, accuracyOverallItem,
                samplingErrItem, nonSamplingErrItem, staticCoverageErrItem, staticMeasurementErrItem, staticNonResponseErrItem, staticProcessingErrItem, staticModelItem, coherXDomItem,
                coherSubanualAnualItem, coherNationalAccountsItem, coherInternalItem);

        // Annotations
        annotationsEditionForm = new GroupDynamicForm(getConstants().instanceAnnotations());
        MultiLanguageRichTextEditorItem commentItem = new MultiLanguageRichTextEditorItem(InstanceDS.COMMENTS, getConstants().instanceComments());
        MultiLanguageRichTextEditorItem notesItem = new MultiLanguageRichTextEditorItem(InstanceDS.NOTES, getConstants().instanceNotes());
        annotationsEditionForm.setFields(commentItem, notesItem);

        mainFormLayout.addEditionCanvas(identifiersEditionForm);

        mainFormLayout.addEditionCanvas(contentDescriptorsEditionForm);
        mainFormLayout.addEditionCanvas(classDescriptorsEditionForm);
        mainFormLayout.addEditionCanvas(productionDescriptorsEditionForm);
        mainFormLayout.addEditionCanvas(diffusionEditionForm);
        mainFormLayout.addEditionCanvas(qualityEditionForm);
        mainFormLayout.addEditionCanvas(annotationsEditionForm);
    }

    private void setInstanceViewMode(InstanceDto instanceDto) {

        // IDENTIFIERS

        identifiersViewForm.setValue(InstanceDS.CODE, instanceDto.getCode());
        identifiersViewForm.setValue(InstanceDS.TITLE, instanceDto.getTitle());
        identifiersViewForm.setValue(InstanceDS.ACRONYM, instanceDto.getAcronym());
        identifiersViewForm.setValue(InstanceDS.URN, instanceDto.getUrn());

        // CONTENT CLASSIFIERS

        // CONTENT DESCRIPTORS

        contentDescriptorsForm.setValue(InstanceDS.DATA_DESCRIPTION, instanceDto.getDataDescription());
        contentDescriptorsForm.setValue(InstanceDS.STATISTICAL_POPULATION, instanceDto.getStatisticalPopulation());

        ((ExternalItemListItem) contentDescriptorsForm.getItem(InstanceDS.STATISTICAL_UNIT)).setExternalItems(instanceDto.getStatisticalUnit());

        ((ExternalItemListItem) contentDescriptorsForm.getItem(InstanceDS.GEOGRAPHIC_GRANULARITIES)).setExternalItems(instanceDto.getGeographicGranularity());

        contentDescriptorsForm.setValue(InstanceDS.GEOGRAPHIC_COMPARABILITY, instanceDto.getGeographicComparability());

        ((ExternalItemListItem) contentDescriptorsForm.getItem(InstanceDS.TEMPORAL_GRANULARITIES)).setExternalItems(instanceDto.getTemporalGranularity());

        contentDescriptorsForm.setValue(InstanceDS.TEMPORAL_COMPARABILITY, instanceDto.getTemporalComparability());
        contentDescriptorsForm.setValue(InstanceDS.BASE_PERIOD, instanceDto.getBasePeriod());

        ((ExternalItemListItem) contentDescriptorsForm.getItem(InstanceDS.MEASURES)).setExternalItems(instanceDto.getUnitMeasure());

        contentDescriptorsForm.setValue(InstanceDS.STAT_CONC_DEF_DESCRIPTION, instanceDto.getStatConcDef());

        ((ExternalItemListItem) contentDescriptorsForm.getItem(InstanceDS.STAT_CONC_DEF)).setExternalItems(instanceDto.getStatConcDefList());

        contentDescriptorsForm.setValue(InstanceDS.CLASS_SYSTEM_DESCRIPTION, instanceDto.getClassSystem());
        ((ExternalItemListItem) contentDescriptorsForm.getItem(InstanceDS.CLASS_SYSTEM_LIST)).setExternalItems(instanceDto.getClassSystemList());

        // CLASS DESCRIPTORS

        classViewForm.setValue(InstanceDS.INSTANCE_TYPE,
                instanceDto.getInstanceType() != null ? CommonWebUtils.getElementName(instanceDto.getInstanceType().getIdentifier(), instanceDto.getInstanceType().getDescription()) : "");

        // PRODUCTION DESCRIPTORS

        productionDescriptorsForm.setValue(InstanceDS.CREATED_DATE, instanceDto.getCreatedDate());
        productionDescriptorsForm.setValue(InstanceDS.INTERNAL_INVENTORY_DATE, instanceDto.getInternalInventoryDate());
        productionDescriptorsForm.setValue(InstanceDS.PROC_STATUS, CommonUtils.getProcStatusName(instanceDto.getProcStatus()));

        productionDescriptorsForm.setValue(InstanceDS.DOC_METHOD, instanceDto.getDocMethod());
        productionDescriptorsForm.setValue(InstanceDS.STATISTICAL_OPERATION_SOURCE,
                instanceDto.getSurveySource() != null ? CommonWebUtils.getElementName(instanceDto.getSurveySource().getIdentifier(), instanceDto.getSurveySource().getDescription()) : "");
        productionDescriptorsForm.setValue(InstanceDS.COLL_METHOD,
                instanceDto.getCollMethod() != null ? CommonWebUtils.getElementName(instanceDto.getCollMethod().getIdentifier(), instanceDto.getCollMethod().getDescription(),0,false) : "");
        ((ExternalItemListItem) productionDescriptorsForm.getItem(InstanceDS.INFORMATION_SUPPLIERS)).setExternalItems(instanceDto.getInformationSuppliers());
        ((ExternalItemListItem) productionDescriptorsForm.getItem(InstanceDS.FREQ_COLL)).setExternalItems(instanceDto.getFreqColl());
        productionDescriptorsForm.setValue(InstanceDS.DATA_VALIDATION, instanceDto.getDataValidation());
        productionDescriptorsForm.setValue(InstanceDS.DATA_COMPILATION, instanceDto.getDataCompilation());
        productionDescriptorsForm.setValue(InstanceDS.ADJUSTMENT, instanceDto.getAdjustment());
        productionDescriptorsForm.setValue(InstanceDS.SEASONAL_ADJUSTMENT, instanceDto.getSeasonalAdjustment());
        productionDescriptorsForm.setValue(InstanceDS.COST_BURDEN, instanceDto.getCostBurden());

        productionDescriptorsForm.setValue(InstanceDS.COST, OperationsListUtils.getCostDtoListToString(instanceDto.getCost()));

        // DIFFUSION AND PUBLICATION

        diffusionViewForm.setValue(InstanceDS.INVENTORY_DATE, instanceDto.getInventoryDate());

        // QUALITY DESCRIPTORS

        qualityViewForm.setValue(InstanceDS.QUALITY_DOC, instanceDto.getQualityDoc());
        qualityViewForm.setValue(InstanceDS.QUALITY_ASSURE, instanceDto.getQualityAssure());
        qualityViewForm.setValue(InstanceDS.QUALITY_ASSMNT, instanceDto.getQualityAssmnt());
        qualityViewForm.setValue(InstanceDS.USER_NEEDS, instanceDto.getUserNeeds());
        qualityViewForm.setValue(InstanceDS.USER_SAT, instanceDto.getUserSat());
        qualityViewForm.setValue(InstanceDS.COMPLETENESS, instanceDto.getCompleteness());
        qualityViewForm.setValue(InstanceDS.TIMELINESS, instanceDto.getTimeliness());
        qualityViewForm.setValue(InstanceDS.PUNCTUALITY, instanceDto.getPunctuality());
        qualityViewForm.setValue(InstanceDS.ACCURACY_OVERALL, instanceDto.getAccuracyOverall());
        qualityViewForm.setValue(InstanceDS.SAMPLING_ERROR, instanceDto.getSamplingErr());

        qualityViewForm.setValue(InstanceDS.NONSAMPLING_ERR, instanceDto.getNonsamplingErr());
        qualityViewForm.setValue(InstanceDS.COVERAGE_ERROR, instanceDto.getCoverageErr());
        qualityViewForm.setValue(InstanceDS.MEASUREMENT_ERROR, instanceDto.getMeasurementErr());
        qualityViewForm.setValue(InstanceDS.NONRESPONSE_ERROR, instanceDto.getNonresponseErr());
        qualityViewForm.setValue(InstanceDS.PROCESSING_ERROR, instanceDto.getProcessingErr());
        qualityViewForm.setValue(InstanceDS.MODEL_ERROR, instanceDto.getModelErr());

        qualityViewForm.setValue(InstanceDS.COHER_X_DOM, instanceDto.getCoherXDomain());
        qualityViewForm.setValue(InstanceDS.COHER_SUBANUAL_ANUAL, instanceDto.getCoherSubAnualAnual());
        qualityViewForm.setValue(InstanceDS.COHER_NATIONAL_ACCOUNTS, instanceDto.getCoherNationalAccounts());
        qualityViewForm.setValue(InstanceDS.COHER_INTERNAL, instanceDto.getCoherInternal());
        qualityViewForm.redraw();
        qualityViewForm.setRedrawOnResize(true);

        // ANNOTATIONS

        annotationsViewForm.setValue(InstanceDS.COMMENTS, instanceDto.getComment());
        annotationsViewForm.setValue(InstanceDS.NOTES, instanceDto.getNotes());
    }

    private void setInstanceEditionMode(InstanceDto instanceDto) {

        String[] requiredFieldsToNextProcStatus = RequiredFieldUtils.getInstanceRequiredFieldsToNextProcStatus(instanceDto.getProcStatus());

        // IDENTIFIERS

        code.setValue(instanceDto.getCode());
        identifiersEditionForm.setValue(InstanceDS.CODE_VIEW, instanceDto.getCode());
        identifiersEditionForm.setValue(InstanceDS.TITLE, instanceDto.getTitle());
        identifiersEditionForm.setValue(InstanceDS.ACRONYM, instanceDto.getAcronym());
        identifiersEditionForm.setValue(InstanceDS.URN, instanceDto.getUrn());
        identifiersEditionForm.setRequiredTitleSuffix(requiredFieldsToNextProcStatus);
        identifiersEditionForm.markForRedraw();

        // CONTENT CLASSIFIERS

        // CONTENT DESCRIPTORS

        contentDescriptorsEditionForm.setValue(InstanceDS.DATA_DESCRIPTION, instanceDto.getDataDescription());
        contentDescriptorsEditionForm.setValue(InstanceDS.STATISTICAL_POPULATION, instanceDto.getStatisticalPopulation());

        ((ExternalItemListItem) contentDescriptorsEditionForm.getItem(InstanceDS.STATISTICAL_UNIT)).setExternalItems(instanceDto.getStatisticalUnit());

        ((ExternalItemListItem) contentDescriptorsEditionForm.getItem(InstanceDS.GEOGRAPHIC_GRANULARITIES)).setExternalItems(instanceDto.getGeographicGranularity());

        contentDescriptorsEditionForm.setValue(InstanceDS.GEOGRAPHIC_COMPARABILITY, instanceDto.getGeographicComparability());

        ((ExternalItemListItem) contentDescriptorsEditionForm.getItem(InstanceDS.TEMPORAL_GRANULARITIES)).setExternalItems(instanceDto.getTemporalGranularity());

        contentDescriptorsEditionForm.setValue(InstanceDS.TEMPORAL_COMPARABILITY, instanceDto.getTemporalComparability());
        contentDescriptorsEditionForm.setValue(InstanceDS.BASE_PERIOD, instanceDto.getBasePeriod());

        ((ExternalItemListItem) contentDescriptorsEditionForm.getItem(InstanceDS.MEASURES)).setExternalItems(instanceDto.getUnitMeasure());

        contentDescriptorsEditionForm.setValue(InstanceDS.STAT_CONC_DEF_DESCRIPTION, instanceDto.getStatConcDef());

        ((ExternalItemListItem) contentDescriptorsEditionForm.getItem(InstanceDS.STAT_CONC_DEF)).setExternalItems(instanceDto.getStatConcDefList());

        contentDescriptorsEditionForm.setValue(InstanceDS.CLASS_SYSTEM_DESCRIPTION, instanceDto.getClassSystem());
        ((ExternalItemListItem) contentDescriptorsEditionForm.getItem(InstanceDS.CLASS_SYSTEM_LIST)).setExternalItems(instanceDto.getClassSystemList());

        contentDescriptorsEditionForm.setRequiredTitleSuffix(requiredFieldsToNextProcStatus);
        contentDescriptorsEditionForm.markForRedraw();

        // CLASS DESCRIPTORS

        instanceTypeItem.setValue(instanceDto.getInstanceType() != null ? instanceDto.getInstanceType().getId().toString() : "");
        classDescriptorsEditionForm.setRequiredTitleSuffix(requiredFieldsToNextProcStatus);
        classDescriptorsEditionForm.markForRedraw();

        // PRODUCTION DESCRIPTORS

        productionDescriptorsEditionForm.setValue(InstanceDS.CREATED_DATE, instanceDto.getCreatedDate());
        productionDescriptorsEditionForm.setValue(InstanceDS.INTERNAL_INVENTORY_DATE, instanceDto.getInventoryDate());

        productionDescriptorsEditionForm.setValue(InstanceDS.PROC_STATUS, CommonUtils.getProcStatusName(instanceDto.getProcStatus()));
        productionDescriptorsEditionForm.setValue(InstanceDS.PROC_STATUS_VIEW, instanceDto.getProcStatus().getName());

        productionDescriptorsEditionForm.setValue(InstanceDS.DOC_METHOD, instanceDto.getDocMethod());
        surveySourceItem.setValue(instanceDto.getSurveySource() != null ? instanceDto.getSurveySource().getId() : "");
        collMethodItem.setValue(instanceDto.getCollMethod() != null ? OperationsListUtils.getCollMethodArtificialKey(collMethodDtos, instanceDto.getCollMethod().getId()) : null);
        ((ExternalItemListItem) productionDescriptorsEditionForm.getItem(InstanceDS.INFORMATION_SUPPLIERS)).setExternalItems(instanceDto.getInformationSuppliers());
        ((ExternalItemListItem) productionDescriptorsEditionForm.getItem(InstanceDS.FREQ_COLL)).setExternalItems(instanceDto.getFreqColl());
        productionDescriptorsEditionForm.setValue(InstanceDS.DATA_VALIDATION, instanceDto.getDataValidation());
        productionDescriptorsEditionForm.setValue(InstanceDS.DATA_COMPILATION, instanceDto.getDataCompilation());
        productionDescriptorsEditionForm.setValue(InstanceDS.ADJUSTMENT, instanceDto.getAdjustment());

        productionDescriptorsEditionForm.setValue(InstanceDS.SEASONAL_ADJUSTMENT, instanceDto.getSeasonalAdjustment());

        productionDescriptorsEditionForm.setValue(InstanceDS.COST_BURDEN, instanceDto.getCostBurden());
        costItem.setValues(getCostIds(instanceDto.getCost()));
        productionDescriptorsEditionForm.setRequiredTitleSuffix(requiredFieldsToNextProcStatus);
        productionDescriptorsEditionForm.markForRedraw();

        // DIFFUSION AND PUBLICATION

        diffusionEditionForm.setValue(InstanceDS.INVENTORY_DATE, instanceDto.getInventoryDate());
        diffusionEditionForm.setRequiredTitleSuffix(requiredFieldsToNextProcStatus);
        diffusionEditionForm.markForRedraw();

        // QUALITY DESCRIPTORS

        qualityEditionForm.setValue(InstanceDS.QUALITY_DOC, instanceDto.getQualityDoc());
        qualityEditionForm.setValue(InstanceDS.QUALITY_ASSURE, instanceDto.getQualityAssure());
        qualityEditionForm.setValue(InstanceDS.QUALITY_ASSMNT, instanceDto.getQualityAssmnt());
        qualityEditionForm.setValue(InstanceDS.USER_NEEDS, instanceDto.getUserNeeds());
        qualityEditionForm.setValue(InstanceDS.USER_SAT, instanceDto.getUserSat());
        qualityEditionForm.setValue(InstanceDS.COMPLETENESS, instanceDto.getCompleteness());
        qualityEditionForm.setValue(InstanceDS.TIMELINESS, instanceDto.getTimeliness());
        qualityEditionForm.setValue(InstanceDS.PUNCTUALITY, instanceDto.getPunctuality());
        qualityEditionForm.setValue(InstanceDS.ACCURACY_OVERALL, instanceDto.getAccuracyOverall());
        qualityEditionForm.setValue(InstanceDS.SAMPLING_ERROR, instanceDto.getSamplingErr());
        qualityEditionForm.setValue(InstanceDS.NONSAMPLING_ERR, instanceDto.getNonsamplingErr());

        qualityEditionForm.setValue(InstanceDS.COVERAGE_ERROR, instanceDto.getCoverageErr());
        qualityEditionForm.setValue(InstanceDS.MEASUREMENT_ERROR, instanceDto.getMeasurementErr());
        qualityEditionForm.setValue(InstanceDS.NONRESPONSE_ERROR, instanceDto.getNonresponseErr());
        qualityEditionForm.setValue(InstanceDS.PROCESSING_ERROR, instanceDto.getProcessingErr());
        qualityEditionForm.setValue(InstanceDS.MODEL_ERROR, instanceDto.getModelErr());

        qualityEditionForm.setValue(InstanceDS.COHER_X_DOM, instanceDto.getCoherXDomain());

        qualityEditionForm.setValue(InstanceDS.COHER_SUBANUAL_ANUAL, instanceDto.getCoherSubAnualAnual());
        qualityEditionForm.setValue(InstanceDS.COHER_NATIONAL_ACCOUNTS, instanceDto.getCoherNationalAccounts());

        qualityEditionForm.setValue(InstanceDS.COHER_INTERNAL, instanceDto.getCoherInternal());
        qualityEditionForm.setRequiredTitleSuffix(requiredFieldsToNextProcStatus);
        qualityEditionForm.markForRedraw();

        // ANNOTATIONS

        annotationsEditionForm.setValue(InstanceDS.COMMENTS, instanceDto.getComment());
        annotationsEditionForm.setValue(InstanceDS.NOTES, instanceDto.getNotes());
        annotationsEditionForm.setRequiredTitleSuffix(requiredFieldsToNextProcStatus);
        annotationsEditionForm.markForRedraw();

        identifiersEditionForm.markForRedraw();
        productionDescriptorsEditionForm.markForRedraw();
    }

    @Override
    public HasClickHandlers getPublishInstanceInternally() {
        return mainFormLayout.getPublishInternally();
    }

    @Override
    public HasClickHandlers getPublishInstanceExternally() {
        return mainFormLayout.getPublishExternally();
    }

    private String[] getCostIds(Set<CostDto> costDtos) {
        List<String> list = new ArrayList<String>();
        for (CostDto costDto : costDtos) {
            list.add(costDto.getId().toString());
        }
        return list.toArray(new String[0]);
    }

    @Override
    public void setOperationsLists(List<InstanceTypeDto> instanceTypeDtos, List<SurveySourceDto> surveySourceDtos, List<CollMethodDto> collMethodDtos, List<CostDto> costDtos) {
        this.instanceTypeDtos = instanceTypeDtos;
        this.surveySourceDtos = surveySourceDtos;
        this.collMethodDtos = collMethodDtos;
        this.costDtos = costDtos;
        instanceTypeItem.setValueMap(OperationsListUtils.getInstanceTypeHashMap(instanceTypeDtos));
        surveySourceItem.setValueMap(OperationsListUtils.getSurveySourceHashMap(surveySourceDtos));
        collMethodItem.setValueMap(OperationsListUtils.getCollMethodsHashMap(collMethodDtos));
        costItem.setValueMap(OperationsListUtils.getCostHashMap(costDtos));
    }

    private void setTranslationsShowed(boolean translationsShowed) {
        // Set translationsShowed value to international fields
        identifiersViewForm.setTranslationsShowed(translationsShowed);
        identifiersEditionForm.setTranslationsShowed(translationsShowed);
        contentDescriptorsForm.setTranslationsShowed(translationsShowed);
        contentDescriptorsEditionForm.setTranslationsShowed(translationsShowed);
        productionDescriptorsForm.setTranslationsShowed(translationsShowed);
        productionDescriptorsEditionForm.setTranslationsShowed(translationsShowed);
        qualityViewForm.setTranslationsShowed(translationsShowed);
        qualityEditionForm.setTranslationsShowed(translationsShowed);
        annotationsViewForm.setTranslationsShowed(translationsShowed);
        annotationsEditionForm.setTranslationsShowed(translationsShowed);
    }

    private boolean canInstanceCodeBeEdited() {
        // Operation code can be edited only when ProcStatus is DRAFT
        return (productionDescriptorsEditionForm.getValue(InstanceDS.PROC_STATUS_VIEW) != null && ProcStatusEnum.DRAFT.toString().equals(
                productionDescriptorsEditionForm.getValue(InstanceDS.PROC_STATUS_VIEW)));
    }

    public boolean isInstanceInternallyPublished() {
        return ProcStatusEnum.PUBLISH_INTERNALLY.equals(instanceDto.getProcStatus());
    }

    public boolean isInstanceExternallyPublished() {
        return ProcStatusEnum.PUBLISH_EXTERNALLY.equals(instanceDto.getProcStatus());
    }

    // ------------------------------------------------------------------------------------------------------------
    // EXTERNAL RESOURCES DATA SETTERS
    // ------------------------------------------------------------------------------------------------------------

    @Override
    public void setItemSchemes(String formItemName, ExternalItemsResult result) {
        if (StringUtils.equals(InstanceDS.INFORMATION_SUPPLIERS, formItemName)) {
            ((SearchSrmListItemWithSchemeFilterItem) productionDescriptorsEditionForm.getItem(formItemName)).setFilterResources(result.getExternalItemDtos(), result.getFirstResult(),
                    result.getTotalResults());

        } else if (StringUtils.equals(InstanceDS.STATISTICAL_UNIT, formItemName)) {
            ((SearchSrmListItemWithSchemeFilterItem) contentDescriptorsEditionForm.getItem(formItemName)).setFilterResources(result.getExternalItemDtos(), result.getFirstResult(),
                    result.getTotalResults());

        } else if (StringUtils.equals(InstanceDS.CLASS_SYSTEM_LIST, formItemName)) {
            ((SearchSrmListItemWithSchemeFilterItem) contentDescriptorsEditionForm.getItem(formItemName)).setResources(result.getExternalItemDtos(), result.getFirstResult(), result.getTotalResults());

        } else if (StringUtils.equals(InstanceDS.FREQ_COLL, formItemName)) {
            ((SearchSrmListItemWithSchemeFilterItem) productionDescriptorsEditionForm.getItem(formItemName)).setFilterResources(result.getExternalItemDtos(), result.getFirstResult(),
                    result.getTotalResults());

        } else if (StringUtils.equals(InstanceDS.STAT_CONC_DEF, formItemName)) {
            ((SearchSrmListConceptAndConceptSchemeItem) contentDescriptorsEditionForm.getItem(formItemName)).setItemSchemes(result);

        } else if (StringUtils.equals(InstanceDS.MEASURES, formItemName)) {
            ((SearchSrmListConceptAndConceptSchemeItem) contentDescriptorsEditionForm.getItem(formItemName)).setItemSchemes(result);
        }
    }

    @Override
    public void setItems(String formItemName, ExternalItemsResult result) {
        if (StringUtils.equals(InstanceDS.INFORMATION_SUPPLIERS, formItemName)) {
            ((SearchSrmListItemWithSchemeFilterItem) productionDescriptorsEditionForm.getItem(formItemName)).setResources(result.getExternalItemDtos(), result.getFirstResult(),
                    result.getTotalResults());

        } else if (StringUtils.equals(InstanceDS.STATISTICAL_UNIT, formItemName)) {
            ((SearchSrmListItemWithSchemeFilterItem) contentDescriptorsEditionForm.getItem(formItemName)).setResources(result.getExternalItemDtos(), result.getFirstResult(), result.getTotalResults());

        } else if (StringUtils.equals(InstanceDS.GEOGRAPHIC_GRANULARITIES, formItemName)) {
            ((SearchMultiExternalItemSimpleItem) contentDescriptorsEditionForm.getItem(formItemName)).setResources(result.getExternalItemDtos(), result.getFirstResult(), result.getTotalResults());

        } else if (StringUtils.equals(InstanceDS.TEMPORAL_GRANULARITIES, formItemName)) {
            ((SearchMultiExternalItemSimpleItem) contentDescriptorsEditionForm.getItem(formItemName)).setResources(result.getExternalItemDtos(), result.getFirstResult(), result.getTotalResults());

        } else if (StringUtils.equals(InstanceDS.FREQ_COLL, formItemName)) {
            ((SearchSrmListItemWithSchemeFilterItem) productionDescriptorsEditionForm.getItem(formItemName)).setResources(result.getExternalItemDtos(), result.getFirstResult(),
                    result.getTotalResults());

        } else if (StringUtils.equals(InstanceDS.STAT_CONC_DEF, formItemName)) {
            ((SearchSrmListConceptAndConceptSchemeItem) contentDescriptorsEditionForm.getItem(formItemName)).setItems(result);

        } else if (StringUtils.equals(InstanceDS.MEASURES, formItemName)) {
            ((SearchSrmListConceptAndConceptSchemeItem) contentDescriptorsEditionForm.getItem(formItemName)).setItems(result);
        }
    }

    // ------------------------------------------------------------------------------------------------------------
    // EXTERNAL RESOURCES ITEMS
    // ------------------------------------------------------------------------------------------------------------

    private SearchSrmListItemWithSchemeFilterItem createStatisticalUnitsItem() {
        final String field = InstanceDS.STATISTICAL_UNIT;
        final ConceptSchemeTypeEnum[] conceptTypes = RestWebCriteriaUtils.getConceptSchemeTypesForInstanceStatisticalUnit();
        final SearchSrmListItemWithSchemeFilterItem item = new SearchSrmListItemWithSchemeFilterItem(field, getConstants().instanceStatisticalUnit(),
                StatisticalOperationsWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveItemSchemes(int firstResult, int maxResults, SrmExternalResourceRestCriteria webCriteria) {
                getUiHandlers().retrieveConceptSchemes(field, webCriteria, firstResult, maxResults, conceptTypes);
            }

            @Override
            protected void retrieveItems(int firstResult, int maxResults, SrmItemRestCriteria webCriteria) {
                getUiHandlers().retrieveConcepts(field, webCriteria, firstResult, maxResults, conceptTypes);
            }
        };
        return item;
    }

    private SearchSrmListItemWithSchemeFilterItem createInformationSuppliersItem() {
        final String field = InstanceDS.INFORMATION_SUPPLIERS;
        final SearchSrmListItemWithSchemeFilterItem item = new SearchSrmListItemWithSchemeFilterItem(field, getConstants().instanceInformationSuppliers(),
                StatisticalOperationsWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveItemSchemes(int firstResult, int maxResults, SrmExternalResourceRestCriteria webCriteria) {
                getUiHandlers().retrieveItemSchemes(field, webCriteria, new TypeExternalArtefactsEnum[]{TypeExternalArtefactsEnum.DATA_PROVIDER_SCHEME}, firstResult, maxResults);
            }

            @Override
            protected void retrieveItems(int firstResult, int maxResults, SrmItemRestCriteria webCriteria) {
                getUiHandlers().retrieveItems(field, webCriteria, new TypeExternalArtefactsEnum[]{TypeExternalArtefactsEnum.DATA_PROVIDER}, firstResult, maxResults);
            }
        };
        return item;
    }

    private SearchSrmListItemWithSchemeFilterItem createClassSystemItem() {
        final String field = InstanceDS.CLASS_SYSTEM_LIST;
        final SearchSrmListItemWithSchemeFilterItem item = new SearchSrmListItemWithSchemeFilterItem(field, getConstants().instanceClassSystemList(),
                StatisticalOperationsWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveItemSchemes(int firstResult, int maxResults, SrmExternalResourceRestCriteria webCriteria) {
                // Do nothing (there is no filter for item schemes)
            }

            @Override
            protected void retrieveItems(int firstResult, int maxResults, SrmItemRestCriteria webCriteria) {
                SrmExternalResourceRestCriteria criteria = RestWebCriteriaUtils.buildSrmExternalResourceRestCriteriaFromSrmItemRestCriteria(webCriteria);
                getUiHandlers().retrieveItemSchemes(field, criteria, new TypeExternalArtefactsEnum[]{TypeExternalArtefactsEnum.CODELIST}, firstResult, maxResults);
            }

            @Override
            protected boolean isItemSchemeFilterFacetVisible() {
                return false;
            }
        };
        return item;
    }

    private SearchMultiExternalItemSimpleItem createGeographicGranularities() {
        final String field = InstanceDS.GEOGRAPHIC_GRANULARITIES;
        return new SearchMultiExternalItemSimpleItem(field, getConstants().instanceGeographicGranularity(), StatisticalOperationsWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveResources(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
                SrmItemRestCriteria criteria = RestWebCriteriaUtils.buildSrmItemRestCriteriaFromMetamacWebCriteria(webCriteria);
                criteria.setExternalArtifactType(TypeExternalArtefactsEnum.CODE);
                criteria.setItemSchemeUrn(ConfigurationPropertiesUtils.getDefaultCodelistGeographicalGranularityUrn());
                getUiHandlers().retrieveItems(field, criteria, firstResult, maxResults);
            }

            @Override
            public String getInformationLabelContents() {
                return getMessages().instanceGeographicGranularityCodesInfoMesage();
            }
        };
    }

    private SearchMultiExternalItemSimpleItem createTemporalGranularities() {
        final String field = InstanceDS.TEMPORAL_GRANULARITIES;
        return new SearchMultiExternalItemSimpleItem(field, getConstants().instanceTemporalGranularity(), StatisticalOperationsWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveResources(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
                SrmItemRestCriteria criteria = RestWebCriteriaUtils.buildSrmItemRestCriteriaFromMetamacWebCriteria(webCriteria);
                criteria.setExternalArtifactType(TypeExternalArtefactsEnum.CODE);
                criteria.setItemSchemeUrn(ConfigurationPropertiesUtils.getDefaultCodelistTemporalGranularityUrn());
                getUiHandlers().retrieveItems(field, criteria, firstResult, maxResults);
            }

            @Override
            public String getInformationLabelContents() {
                return getMessages().instanceTemporalGranularityCodesInfoMesage();
            }
        };
    }

    private SearchSrmListItemWithSchemeFilterItem createFreqColl() {
        final String field = InstanceDS.FREQ_COLL;
        final SearchSrmListItemWithSchemeFilterItem item = new SearchSrmListItemWithSchemeFilterItem(field, getConstants().instanceFreqColl(), StatisticalOperationsWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveItemSchemes(int firstResult, int maxResults, SrmExternalResourceRestCriteria webCriteria) {
                getUiHandlers().retrieveItemSchemes(field, webCriteria, new TypeExternalArtefactsEnum[]{TypeExternalArtefactsEnum.CODELIST}, firstResult, maxResults);
            }

            @Override
            protected void retrieveItems(int firstResult, int maxResults, SrmItemRestCriteria webCriteria) {
                getUiHandlers().retrieveItems(field, webCriteria, new TypeExternalArtefactsEnum[]{TypeExternalArtefactsEnum.CODE}, firstResult, maxResults);
            }

            @Override
            protected ExternalItemDto getDefaultItemSchemeForFilterForm() {
                return ConfigurationPropertiesUtils.getDefaultCodelistTemporalGranularity();
            }
        };
        return item;
    }

    private SearchSrmListConceptAndConceptSchemeItem createMeasures() {
        final String field = InstanceDS.MEASURES;
        final SearchSrmListConceptAndConceptSchemeItem item = new SearchSrmListConceptAndConceptSchemeItem(field, getConstants().instanceUnitMeasure(),
                StatisticalOperationsWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected ConceptSchemeTypeEnum[] getConceptSchemeTypes() {
                return RestWebCriteriaUtils.getConceptSchemeTypesForInstanceMeasures();
            }
        };
        return item;
    }

    private SearchSrmListConceptAndConceptSchemeItem createStatConcDef() {
        final String field = InstanceDS.STAT_CONC_DEF;
        final SearchSrmListConceptAndConceptSchemeItem item = new SearchSrmListConceptAndConceptSchemeItem(field, getConstants().instanceStatisticalConceptsDefinitions(),
                StatisticalOperationsWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected ConceptSchemeTypeEnum[] getConceptSchemeTypes() {
                return RestWebCriteriaUtils.getConceptSchemeTypesForInstanceStatConcDef();
            }
        };
        return item;
    }
}

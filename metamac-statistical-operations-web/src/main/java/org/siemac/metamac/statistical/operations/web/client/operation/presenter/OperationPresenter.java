package org.siemac.metamac.statistical.operations.web.client.operation.presenter;

import static org.siemac.metamac.statistical.operations.web.client.OperationsWeb.getConstants;
import static org.siemac.metamac.statistical.operations.web.client.OperationsWeb.getMessages;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.statistical.operations.core.dto.FamilyBaseDto;
import org.siemac.metamac.statistical.operations.core.dto.InstanceBaseDto;
import org.siemac.metamac.statistical.operations.core.dto.InstanceDto;
import org.siemac.metamac.statistical.operations.core.dto.OfficialityTypeDto;
import org.siemac.metamac.statistical.operations.core.dto.OperationDto;
import org.siemac.metamac.statistical.operations.core.dto.SurveyTypeDto;
import org.siemac.metamac.statistical.operations.navigation.shared.NameTokens;
import org.siemac.metamac.statistical.operations.navigation.shared.PlaceRequestParams;
import org.siemac.metamac.statistical.operations.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.operations.web.client.OperationsWeb;
import org.siemac.metamac.statistical.operations.web.client.enums.ToolStripButtonEnum;
import org.siemac.metamac.statistical.operations.web.client.events.SelectMenuButtonEvent;
import org.siemac.metamac.statistical.operations.web.client.events.UpdateCommonMetadataEvent;
import org.siemac.metamac.statistical.operations.web.client.events.UpdateCommonMetadataEvent.UpdateCommonMetadataHandler;
import org.siemac.metamac.statistical.operations.web.client.events.UpdateOperationsListsEvent;
import org.siemac.metamac.statistical.operations.web.client.events.UpdateOperationsListsEvent.UpdateOperationsListsHandler;
import org.siemac.metamac.statistical.operations.web.client.model.FamilyRecord;
import org.siemac.metamac.statistical.operations.web.client.model.InstanceRecord;
import org.siemac.metamac.statistical.operations.web.client.operation.view.handlers.OperationUiHandlers;
import org.siemac.metamac.statistical.operations.web.client.presenter.MainPagePresenter;
import org.siemac.metamac.statistical.operations.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.operations.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.operations.web.shared.DeleteInstanceListAction;
import org.siemac.metamac.statistical.operations.web.shared.DeleteInstanceListResult;
import org.siemac.metamac.statistical.operations.web.shared.DeleteOperationListAction;
import org.siemac.metamac.statistical.operations.web.shared.DeleteOperationListResult;
import org.siemac.metamac.statistical.operations.web.shared.GetFamilyPaginatedListAction;
import org.siemac.metamac.statistical.operations.web.shared.GetFamilyPaginatedListResult;
import org.siemac.metamac.statistical.operations.web.shared.GetInstanceListAction;
import org.siemac.metamac.statistical.operations.web.shared.GetInstanceListResult;
import org.siemac.metamac.statistical.operations.web.shared.GetOperationAndInstancesAction;
import org.siemac.metamac.statistical.operations.web.shared.GetOperationAndInstancesResult;
import org.siemac.metamac.statistical.operations.web.shared.PublishExternallyOperationAction;
import org.siemac.metamac.statistical.operations.web.shared.PublishExternallyOperationResult;
import org.siemac.metamac.statistical.operations.web.shared.PublishInternallyOperationAction;
import org.siemac.metamac.statistical.operations.web.shared.PublishInternallyOperationResult;
import org.siemac.metamac.statistical.operations.web.shared.ReSendStreamMessageOperationAction;
import org.siemac.metamac.statistical.operations.web.shared.ReSendStreamMessageOperationResult;
import org.siemac.metamac.statistical.operations.web.shared.SaveInstanceAction;
import org.siemac.metamac.statistical.operations.web.shared.SaveInstanceResult;
import org.siemac.metamac.statistical.operations.web.shared.SaveOperationAction;
import org.siemac.metamac.statistical.operations.web.shared.SaveOperationResult;
import org.siemac.metamac.statistical.operations.web.shared.UpdateInstancesOrderAction;
import org.siemac.metamac.statistical.operations.web.shared.UpdateInstancesOrderResult;
import org.siemac.metamac.statistical.operations.web.shared.UpdateOperationFamiliesAction;
import org.siemac.metamac.statistical.operations.web.shared.UpdateOperationFamiliesResult;
import org.siemac.metamac.statistical.operations.web.shared.external.GetCommonMetadataConfigurationsAction;
import org.siemac.metamac.statistical.operations.web.shared.external.GetCommonMetadataConfigurationsResult;
import org.siemac.metamac.statistical.operations.web.shared.external.GetExternalResourcesAction;
import org.siemac.metamac.statistical.operations.web.shared.external.GetExternalResourcesResult;
import org.siemac.metamac.statistical.operations.web.shared.external.GetUsersAccessControlListAction;
import org.siemac.metamac.statistical.operations.web.shared.external.GetUsersAccessControlListResult;
import org.siemac.metamac.statistical.operations.web.shared.external.RestWebCriteriaUtils;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
import org.siemac.metamac.web.common.client.utils.WaitingAsyncCallbackHandlingError;
import org.siemac.metamac.web.common.shared.criteria.CommonConfigurationRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;

import com.google.gwt.core.client.Scheduler;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.annotations.TitleFunction;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.grid.events.HasRecordClickHandlers;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

public class OperationPresenter extends Presenter<OperationPresenter.OperationView, OperationPresenter.OperationProxy>
        implements
            OperationUiHandlers,
            UpdateOperationsListsHandler,
            UpdateCommonMetadataHandler {

    private final DispatchAsync   dispatcher;
    private final PlaceManager    placeManager;

    private OperationDto          operationDto;
    private List<InstanceBaseDto> instanceBaseDtos;
    private List<FamilyBaseDto>   familyBaseDtos;

    public static final Object    TYPE_SetContextAreaContentToolBar = new Object();

    @ProxyCodeSplit
    @NameToken(NameTokens.operationPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface OperationProxy extends Proxy<OperationPresenter>, Place {

    }

    @TitleFunction
    public static String getTranslatedTitle(PlaceRequest placeRequest) {
        return placeRequest.getParameter(PlaceRequestParams.operationParam, getConstants().breadcrumbStatisticalOperation());
    }

    public interface OperationView extends View, HasUiHandlers<OperationUiHandlers> {

        // Operations

        void setOperation(OperationDto operationDto, List<InstanceBaseDto> instanceBaseDtos, List<FamilyBaseDto> familyBaseDtos);

        OperationDto getOperation(OperationDto operationDto);

        HasClickHandlers getSave();

        void onOperationSaved(OperationDto operationDto);

        boolean validate();

        HasClickHandlers getPublishOperationInternally();

        HasClickHandlers getPublishOperationExternally();

        HasClickHandlers getReSendStreamMessageOperation();

        void setOperationsLists(List<SurveyTypeDto> surveyTypeDtos, List<OfficialityTypeDto> officialityTypeDtos);

        // External resources

        void setCommonMetadataConfigurations(List<ExternalItemDto> commonMetadataList);

        void setItemSchemes(String formItemName, ExternalItemsResult result);

        void setItems(String formItemName, ExternalItemsResult result);

        // Instances

        HasRecordClickHandlers getSelectedInstance();

        void setInstances(List<InstanceBaseDto> instanceBaseDtos);

        com.smartgwt.client.widgets.form.fields.events.HasClickHandlers getSaveNewInstance();

        InstanceDto getNewInstace();

        void onInstanceSaved(InstanceDto instanceDto);

        boolean validateNewInstance();

        void closeInstanceWindow();

        HasClickHandlers getDeleteInstance();

        List<Long> getSelectedInstances();

        // Families
        HasRecordClickHandlers getSelectedFamily();

        void setFamilies(List<ExternalItemDto> families, int firstResult, int totalResults);

        void setUsersAccessControl(GetUsersAccessControlListResult result);
    }

    @Inject
    public OperationPresenter(EventBus eventBus, OperationView operationView, OperationProxy operationProxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, operationView, operationProxy);
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
        getView().setUiHandlers(this);
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, MainPagePresenter.TYPE_SetContextAreaContent, this);
    }

    @Override
    protected void onBind() {
        super.onBind();

        registerHandler(getView().getSelectedFamily().addRecordClickHandler(new RecordClickHandler() {

            @Override
            public void onRecordClick(RecordClickEvent event) {
                FamilyRecord record = (FamilyRecord) event.getRecord();
                goToFamily(record.getCode());
            }
        }));

        registerHandler(getView().getSelectedInstance().addRecordClickHandler(new RecordClickHandler() {

            @Override
            public void onRecordClick(RecordClickEvent event) {
                if (event.getFieldNum() != 0) {
                    InstanceRecord record = (InstanceRecord) event.getRecord();
                    goToInstance(record.getCode());
                }
            }
        }));

        registerHandler(getView().getSave().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (getView().validate()) {
                    // See: METAMAC-2516
                    // Two invokes to getXXXDto() is needed for Chrome, please don't remove this two call fix.
                    getView().getOperation(operationDto);
                    Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {

                        @Override
                        public void execute() {
                            saveOperation(getView().getOperation(operationDto));
                        }
                    });
                }
            }
        }));

        registerHandler(getView().getSaveNewInstance().addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                if (getView().validateNewInstance()) {
                    saveInstance(getView().getNewInstace());
                }
            }
        }));

        registerHandler(getView().getDeleteInstance().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                deleteInstances(getView().getSelectedInstances());
            }
        }));

        registerHandler(getView().getPublishOperationInternally().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                publishOperationInternally();
            }
        }));

        registerHandler(getView().getPublishOperationExternally().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                publishOperationExternally();
            }
        }));

        registerHandler(getView().getReSendStreamMessageOperation().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                reSendStreamMessageOperation();
            }
        }));
    }

    @Override
    protected void onReveal() {
        super.onReveal();
        MainPagePresenter.getMasterHead().setTitleLabel(getConstants().statisticalOperation());
        SelectMenuButtonEvent.fire(this, ToolStripButtonEnum.OPERATIONS);
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);
        String operationCode = request.getParameter(PlaceRequestParams.operationParam, null);
        if (!StringUtils.isBlank(operationCode)) {
            retrieveOperation(operationCode);
        } else {
            OperationsWeb.showErrorPage();
        }
    }

    @Override
    public void saveOperation(OperationDto operationToSave) {
        dispatcher.execute(new SaveOperationAction(operationToSave), new WaitingAsyncCallbackHandlingError<SaveOperationResult>(this) {

            @Override
            public void onWaitSuccess(SaveOperationResult result) {

                if (result.getNotificationException() != null) {
                    ShowMessageEvent.fireWarningMessageWithError(OperationPresenter.this, getMessages().operationExternallyPublishedWithNotificationError(), result.getNotificationException());
                } else {
                    ShowMessageEvent.fireSuccessMessage(OperationPresenter.this, getMessages().operationSaved());
                }

                operationDto = result.getOperationSaved();
                getView().onOperationSaved(operationDto);

                // Update URL
                PlaceRequest placeRequest = PlaceRequestUtils.buildRelativeOperationPlaceRequest(operationDto.getCode());
                placeManager.updateHistory(placeRequest, true);
            }
        });
    }

    @Override
    public void deleteOperation(OperationDto operationDto) {
        List<Long> ids = new ArrayList<Long>();
        ids.add(operationDto.getId());
        dispatcher.execute(new DeleteOperationListAction(ids), new WaitingAsyncCallbackHandlingError<DeleteOperationListResult>(this) {

            @Override
            public void onWaitSuccess(DeleteOperationListResult result) {
                placeManager.revealRelativePlace(-1);
            }
        });
    }

    @Override
    public void goToFamily(String familyCode) {
        if (!StringUtils.isBlank(familyCode)) {
            PlaceRequest familyListRequest = new PlaceRequest(NameTokens.familyListPage);
            PlaceRequest familyRequest = PlaceRequestUtils.buildRelativeFamilyPlaceRequest(familyCode);
            List<PlaceRequest> placeRequestHierarchy = new ArrayList<PlaceRequest>();
            placeRequestHierarchy.add(familyListRequest);
            placeRequestHierarchy.add(familyRequest);
            placeManager.revealPlaceHierarchy(placeRequestHierarchy);
        }
    }

    @Override
    public void saveInstance(InstanceDto instanceDto) {
        dispatcher.execute(new SaveInstanceAction(operationDto.getId(), instanceDto), new WaitingAsyncCallbackHandlingError<SaveInstanceResult>(this) {

            @Override
            protected void afterFailure() {
                getView().closeInstanceWindow();
            }

            @Override
            public void onWaitSuccess(SaveInstanceResult result) {
                ShowMessageEvent.fireSuccessMessage(OperationPresenter.this, getMessages().instanceSaved());
                getView().closeInstanceWindow();
                retrieveOperation(operationDto.getCode());
            }
        });
    }

    @Override
    public void goToInstance(String instanceCode) {
        if (!StringUtils.isBlank(instanceCode)) {
            placeManager.revealRelativePlace(PlaceRequestUtils.buildRelativeInstancePlaceRequest(instanceCode));
        }
    }

    @Override
    public void deleteInstances(final List<Long> instanceIds) {
        dispatcher.execute(new DeleteInstanceListAction(instanceIds), new WaitingAsyncCallbackHandlingError<DeleteInstanceListResult>(this) {

            @Override
            protected void afterFailure() {
                retrieveInstances();
            }

            @Override
            public void onWaitSuccess(DeleteInstanceListResult result) {
                retrieveOperation(operationDto.getCode());
                String message = instanceIds.size() > 1 ? getMessages().instancesDeleted() : getMessages().instanceDeleted();
                ShowMessageEvent.fireSuccessMessage(OperationPresenter.this, message);
            }
        });
    }

    @Override
    public void updateOperationFamilies(List<Long> familiesToAdd, List<Long> familiesToRemove) {
        dispatcher.execute(new UpdateOperationFamiliesAction(operationDto.getId(), familiesToAdd, familiesToRemove), new WaitingAsyncCallbackHandlingError<UpdateOperationFamiliesResult>(this) {

            @Override
            public void onWaitSuccess(UpdateOperationFamiliesResult result) {
                retrieveOperation(operationDto.getCode());
                ShowMessageEvent.fireSuccessMessage(OperationPresenter.this, getMessages().familiesAddedToOperation());
            }
        });
    }

    private void retrieveOperation(String operationCode) {
        String operationUrn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_OPERATION_PREFIX, operationCode);
        dispatcher.execute(new GetOperationAndInstancesAction(operationUrn), new WaitingAsyncCallbackHandlingError<GetOperationAndInstancesResult>(this) {

            @Override
            protected void afterFailure() {
                OperationsWeb.showErrorPage();
            }

            @Override
            public void onWaitSuccess(GetOperationAndInstancesResult result) {
                operationDto = result.getOperationDto();
                instanceBaseDtos = result.getInstanceBaseDtos();
                familyBaseDtos = result.getFamilyBaseDtos();
                MainPagePresenter.getMasterHead().setTitleLabel(getMessages().titleStatisticalOperation(operationDto.getCode()));
                getView().setOperation(operationDto, instanceBaseDtos, familyBaseDtos);
            }
        });
    }

    private void retrieveInstances() {
        dispatcher.execute(new GetInstanceListAction(operationDto.getId()), new WaitingAsyncCallbackHandlingError<GetInstanceListResult>(this) {

            @Override
            public void onWaitSuccess(GetInstanceListResult result) {
                getView().setInstances(result.getInstanceBaseDtos());
            }
        });
    }

    private void publishOperationInternally() {
        dispatcher.execute(new PublishInternallyOperationAction(operationDto.getId()), new WaitingAsyncCallbackHandlingError<PublishInternallyOperationResult>(this) {

            @Override
            public void onWaitSuccess(PublishInternallyOperationResult result) {
                retrieveOperation(operationDto.getCode());

                if (result.getNotificationException() != null) {
                    ShowMessageEvent.fireWarningMessageWithError(OperationPresenter.this, getMessages().operationInternallyPublishedWithNotificationError(), result.getNotificationException());
                } else {
                    ShowMessageEvent.fireSuccessMessage(OperationPresenter.this, getMessages().operationInternallyPublished());
                }
            }
        });
    }

    private void publishOperationExternally() {
        dispatcher.execute(new PublishExternallyOperationAction(operationDto.getId()), new WaitingAsyncCallbackHandlingError<PublishExternallyOperationResult>(this) {

            @Override
            public void onWaitSuccess(PublishExternallyOperationResult result) {
                retrieveOperation(operationDto.getCode());

                if (result.getNotificationException() != null) {
                    ShowMessageEvent.fireWarningMessageWithError(OperationPresenter.this, getMessages().operationExternallyPublishedWithNotificationError(), result.getNotificationException());
                } else {
                    ShowMessageEvent.fireSuccessMessage(OperationPresenter.this, getMessages().operationExternallyPublished());
                }
            }
        });
    }

    private void reSendStreamMessageOperation() {
        dispatcher.execute(new ReSendStreamMessageOperationAction(operationDto.getId()), new WaitingAsyncCallbackHandlingError<ReSendStreamMessageOperationResult>(this) {

            @Override
            public void onWaitSuccess(ReSendStreamMessageOperationResult result) {
                retrieveOperation(operationDto.getCode());

                if (result.getNotificationException() != null) {
                    ShowMessageEvent.fireWarningMessageWithError(OperationPresenter.this, getMessages().operationlifeCycleReSendStreamMessageError(), result.getNotificationException());
                } else {
                    ShowMessageEvent.fireSuccessMessage(OperationPresenter.this, getMessages().operationlifeCycleReSendStreamMessagePublished());
                }
            }
        });
    }

    @ProxyEvent
    @Override
    public void onUpdateOperationsLists(UpdateOperationsListsEvent event) {
        getView().setOperationsLists(event.getSurveyTypeDtos(), event.getOfficialityTypeDtos());
    }

    @ProxyEvent
    @Override
    public void onUpdateCommonMetadata(UpdateCommonMetadataEvent event) {
        getView().setCommonMetadataConfigurations(event.getCommonMetadata());
    }

    @Override
    public void updateInstancesOrder(List<Long> instancesIds) {
        dispatcher.execute(new UpdateInstancesOrderAction(operationDto.getId(), instancesIds), new WaitingAsyncCallbackHandlingError<UpdateInstancesOrderResult>(this) {

            @Override
            public void onWaitSuccess(UpdateInstancesOrderResult result) {
                getView().setInstances(result.getInstances());
            }
        });
    }

    @Override
    public void retrieveFamilies(int firstResult, int maxResults, MetamacWebCriteria metamacWebCriteria) {
        dispatcher.execute(new GetFamilyPaginatedListAction(firstResult, maxResults, metamacWebCriteria.getCriteria()), new WaitingAsyncCallbackHandlingError<GetFamilyPaginatedListResult>(this) {

            @Override
            public void onWaitSuccess(GetFamilyPaginatedListResult result) {
                List<ExternalItemDto> families = CommonUtils.createFamilies(result.getFamilyBaseDtos());
                getView().setFamilies(families, result.getFirstResultOut(), result.getTotalResults());
            }
        });
    }

    //
    // EXTERNAL RESOURCES
    //

    @Override
    public void retrieveCommonMetadataConfigurations(CommonConfigurationRestCriteria criteria) {
        dispatcher.execute(new GetCommonMetadataConfigurationsAction(criteria), new WaitingAsyncCallbackHandlingError<GetCommonMetadataConfigurationsResult>(this) {

            @Override
            public void onWaitSuccess(GetCommonMetadataConfigurationsResult result) {
                getView().setCommonMetadataConfigurations(result.getConfigurations());
            }
        });
    }

    @Override
    public void retrieveItemSchemes(final String formItemName, SrmExternalResourceRestCriteria srmItemSchemeRestCriteria, TypeExternalArtefactsEnum[] types, int firstResult, int maxResults) {
        srmItemSchemeRestCriteria = RestWebCriteriaUtils.buildItemSchemeWebCriteria(srmItemSchemeRestCriteria, types);
        retrieveItemSchemes(formItemName, srmItemSchemeRestCriteria, firstResult, maxResults);
    }

    @Override
    public void retrieveItemSchemes(final String formItemName, SrmExternalResourceRestCriteria srmItemSchemeRestCriteria, int firstResult, int maxResults) {
        dispatcher.execute(new GetExternalResourcesAction(srmItemSchemeRestCriteria, firstResult, maxResults), new WaitingAsyncCallbackHandlingError<GetExternalResourcesResult>(this) {

            @Override
            public void onWaitSuccess(GetExternalResourcesResult result) {
                getView().setItemSchemes(formItemName, result.getExternalItemsResult());
            }
        });
    }

    @Override
    public void retrieveItems(final String formItemName, SrmItemRestCriteria itemRestCriteria, TypeExternalArtefactsEnum[] types, int firstResult, int maxResults) {
        itemRestCriteria = RestWebCriteriaUtils.buildItemWebCriteria(itemRestCriteria, types);
        retrieveItems(formItemName, itemRestCriteria, firstResult, maxResults);
    }

    @Override

    public void retrieveItems(final String formItemName, SrmItemRestCriteria itemWebCriteria, int firstResult, int maxResults) {
        dispatcher.execute(new GetExternalResourcesAction(itemWebCriteria, firstResult, maxResults), new WaitingAsyncCallbackHandlingError<GetExternalResourcesResult>(this) {

            @Override
            public void onWaitSuccess(GetExternalResourcesResult result) {
                getView().setItems(formItemName, result.getExternalItemsResult());
            }
        });
    }

    @Override
    public void retrieveUsersAccessControl() {
        dispatcher.execute(new GetUsersAccessControlListAction(), new WaitingAsyncCallbackHandlingError<GetUsersAccessControlListResult>(this) {

            @Override
            public void onWaitSuccess(GetUsersAccessControlListResult result) {
                getView().setUsersAccessControl(result);
            }
        });
    }

    //
    // NAVIGATION
    //

    @Override
    public void goTo(List<PlaceRequest> location) {
        if (location != null && !location.isEmpty()) {
            placeManager.revealPlaceHierarchy(location);
        }
    }
}

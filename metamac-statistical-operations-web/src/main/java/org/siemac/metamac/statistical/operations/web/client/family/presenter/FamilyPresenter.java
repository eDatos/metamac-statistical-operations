package org.siemac.metamac.statistical.operations.web.client.family.presenter;

import static org.siemac.metamac.statistical.operations.web.client.OperationsWeb.getConstants;
import static org.siemac.metamac.statistical.operations.web.client.OperationsWeb.getMessages;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.statistical.operations.core.dto.FamilyDto;
import org.siemac.metamac.statistical.operations.core.dto.OperationBaseDto;
import org.siemac.metamac.statistical.operations.navigation.shared.NameTokens;
import org.siemac.metamac.statistical.operations.navigation.shared.PlaceRequestParams;
import org.siemac.metamac.statistical.operations.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.operations.web.client.OperationsWeb;
import org.siemac.metamac.statistical.operations.web.client.enums.ToolStripButtonEnum;
import org.siemac.metamac.statistical.operations.web.client.events.SelectMenuButtonEvent;
import org.siemac.metamac.statistical.operations.web.client.family.view.handlers.FamilyUiHandlers;
import org.siemac.metamac.statistical.operations.web.client.model.OperationRecord;
import org.siemac.metamac.statistical.operations.web.client.presenter.MainPagePresenter;
import org.siemac.metamac.statistical.operations.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.operations.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.operations.web.shared.GetFamilyAndOperationsAction;
import org.siemac.metamac.statistical.operations.web.shared.GetFamilyAndOperationsResult;
import org.siemac.metamac.statistical.operations.web.shared.GetOperationPaginatedListAction;
import org.siemac.metamac.statistical.operations.web.shared.GetOperationPaginatedListResult;
import org.siemac.metamac.statistical.operations.web.shared.PublishExternallyFamilyAction;
import org.siemac.metamac.statistical.operations.web.shared.PublishExternallyFamilyResult;
import org.siemac.metamac.statistical.operations.web.shared.PublishInternallyFamilyAction;
import org.siemac.metamac.statistical.operations.web.shared.PublishInternallyFamilyResult;
import org.siemac.metamac.statistical.operations.web.shared.SaveFamilyAction;
import org.siemac.metamac.statistical.operations.web.shared.SaveFamilyResult;
import org.siemac.metamac.statistical.operations.web.shared.UpdateFamilyOperationsAction;
import org.siemac.metamac.statistical.operations.web.shared.UpdateFamilyOperationsResult;
import org.siemac.metamac.statistical.operations.web.shared.criteria.OperationCriteria;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
import org.siemac.metamac.web.common.client.utils.WaitingAsyncCallbackHandlingError;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
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

public class FamilyPresenter extends Presenter<FamilyPresenter.FamilyView, FamilyPresenter.FamilyProxy> implements FamilyUiHandlers {

    private final DispatchAsync    dispatcher;
    private final PlaceManager     placeManager;

    private FamilyDto              familyDto;
    private List<OperationBaseDto> operationBaseDtos;

    public static final Object     TYPE_SetContextAreaContentToolBar = new Object();

    @ProxyCodeSplit
    @NameToken(NameTokens.familyPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface FamilyProxy extends Proxy<FamilyPresenter>, Place {

    }

    @TitleFunction
    public static String getTranslatedTitle(PlaceRequest request) {
        String familyCode = request.getParameter(PlaceRequestParams.familyParam, null);
        return familyCode != null ? familyCode : getConstants().breadcrumbStatisticalFamily();
    }

    public interface FamilyView extends View, HasUiHandlers<FamilyUiHandlers> {

        // Family
        void setFamily(FamilyDto familyDto, List<OperationBaseDto> operationBaseDtos);
        FamilyDto getFamily(FamilyDto familyDto);
        HasClickHandlers getSave();
        void onFamilySaved(FamilyDto familyDto);
        HasClickHandlers getPublishFamilyInternally();
        HasClickHandlers getPublishFamilyExternally();
        boolean validate();

        // Operations
        HasRecordClickHandlers getSelectedOperation();
        void setOperations(List<ExternalItemDto> operations, int firstResult, int totalResults);
    }

    @Inject
    public FamilyPresenter(EventBus eventBus, FamilyView familyView, FamilyProxy familyProxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, familyView, familyProxy);
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

        registerHandler(getView().getSelectedOperation().addRecordClickHandler(new RecordClickHandler() {

            @Override
            public void onRecordClick(RecordClickEvent event) {
                OperationRecord record = (OperationRecord) event.getRecord();
                goToOperation(record.getCode());
            }
        }));

        registerHandler(getView().getSave().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (getView().validate()) {
                    saveFamily(getView().getFamily(familyDto));
                }
            }
        }));

        registerHandler(getView().getPublishFamilyInternally().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                publishFamilyInternally();
            }
        }));

        registerHandler(getView().getPublishFamilyExternally().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                publishFamilyExternally();
            }
        }));
    }

    @Override
    protected void onReveal() {
        super.onReveal();
        MainPagePresenter.getMasterHead().setTitleLabel(getConstants().statisticalFamily());
        SelectMenuButtonEvent.fire(this, ToolStripButtonEnum.FAMILIES);
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);
        String familyCode = request.getParameter(PlaceRequestParams.familyParam, null);
        if (!StringUtils.isBlank(familyCode)) {
            retrieveFamily(familyCode);
        } else {
            OperationsWeb.showErrorPage();
        }
    }

    private void retrieveFamily(String familyCode) {
        String familyUrn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_FAMILY_PREFIX, familyCode);
        dispatcher.execute(new GetFamilyAndOperationsAction(familyUrn), new WaitingAsyncCallbackHandlingError<GetFamilyAndOperationsResult>(this) {

            @Override
            protected void afterFailure() {
                OperationsWeb.showErrorPage();
            }

            @Override
            public void onWaitSuccess(GetFamilyAndOperationsResult result) {
                familyDto = result.getFamilyDto();
                operationBaseDtos = result.getOperationBaseDtos();
                MainPagePresenter.getMasterHead().setTitleLabel(getMessages().titleStatisticalFamily(familyDto.getCode()));
                getView().setFamily(familyDto, operationBaseDtos);
            }
        });
    }

    @Override
    public void saveFamily(FamilyDto familyToSave) {
        dispatcher.execute(new SaveFamilyAction(familyToSave), new WaitingAsyncCallbackHandlingError<SaveFamilyResult>(this) {

            @Override
            public void onWaitSuccess(SaveFamilyResult result) {
                ShowMessageEvent.fireSuccessMessage(FamilyPresenter.this, getMessages().familySaved());
                familyDto = result.getFamilySaved();
                getView().onFamilySaved(familyDto);

                // Update URL
                PlaceRequest placeRequest = PlaceRequestUtils.buildRelativeFamilyPlaceRequest(familyDto.getCode());
                placeManager.updateHistory(placeRequest, true);
            }
        });
    }

    @Override
    public void goToOperation(String operationCode) {
        if (!StringUtils.isBlank(operationCode)) {
            PlaceRequest operationListRequest = new PlaceRequest(NameTokens.operationListPage);
            PlaceRequest operationRequest = PlaceRequestUtils.buildRelativeOperationPlaceRequest(operationCode);
            List<PlaceRequest> placeRequests = new ArrayList<PlaceRequest>();
            placeRequests.add(operationListRequest);
            placeRequests.add(operationRequest);
            placeManager.revealPlaceHierarchy(placeRequests);
        }
    }

    @Override
    public void updateFamilyOperations(List<Long> operationsToAdd, List<Long> operationsToRemove) {
        dispatcher.execute(new UpdateFamilyOperationsAction(familyDto.getId(), operationsToAdd, operationsToRemove), new WaitingAsyncCallbackHandlingError<UpdateFamilyOperationsResult>(this) {

            @Override
            public void onWaitSuccess(UpdateFamilyOperationsResult result) {
                operationBaseDtos = result.getOperationDtos();
                ShowMessageEvent.fireSuccessMessage(FamilyPresenter.this, getMessages().operationsAddedToFamily());
                retrieveFamily(familyDto.getCode());
            }
        });
    }

    private void publishFamilyInternally() {
        dispatcher.execute(new PublishInternallyFamilyAction(familyDto.getId()), new WaitingAsyncCallbackHandlingError<PublishInternallyFamilyResult>(this) {

            @Override
            public void onWaitSuccess(PublishInternallyFamilyResult result) {
                familyDto = result.getFamilySaved();
                getView().onFamilySaved(familyDto);
                ShowMessageEvent.fireSuccessMessage(FamilyPresenter.this, getMessages().familyInternallyPublished());
            }
        });
    }

    private void publishFamilyExternally() {
        dispatcher.execute(new PublishExternallyFamilyAction(familyDto.getId()), new WaitingAsyncCallbackHandlingError<PublishExternallyFamilyResult>(this) {

            @Override
            public void onWaitSuccess(PublishExternallyFamilyResult result) {
                familyDto = result.getFamilySaved();
                getView().onFamilySaved(familyDto);
                ShowMessageEvent.fireSuccessMessage(FamilyPresenter.this, getMessages().familyExternallyPublished());
            }
        });
    }

    @Override
    public void retrieveOperations(int firstResult, int maxResults, MetamacWebCriteria metamacWebCriteria) {

        OperationCriteria operationCriteria = new OperationCriteria();
        operationCriteria.setCriteria(metamacWebCriteria.getCriteria());
        dispatcher.execute(new GetOperationPaginatedListAction(firstResult, maxResults, operationCriteria),
                new WaitingAsyncCallbackHandlingError<GetOperationPaginatedListResult>(this) {

                    @Override
                    public void onWaitSuccess(GetOperationPaginatedListResult result) {
                        List<ExternalItemDto> operations = CommonUtils.createOperations(result.getOperationBaseDtos());
                        getView().setOperations(operations, result.getFirstResultOut(), result.getTotalResults());
                    }
                });
    }
}

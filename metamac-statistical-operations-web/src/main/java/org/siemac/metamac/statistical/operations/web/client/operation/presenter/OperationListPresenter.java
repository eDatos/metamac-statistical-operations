package org.siemac.metamac.statistical.operations.web.client.operation.presenter;

import static org.siemac.metamac.statistical.operations.web.client.OperationsWeb.getConstants;
import static org.siemac.metamac.statistical.operations.web.client.OperationsWeb.getMessages;

import java.util.List;

import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.operations.core.dto.OperationBaseDto;
import org.siemac.metamac.statistical.operations.core.dto.OperationDto;
import org.siemac.metamac.statistical.operations.navigation.shared.NameTokens;
import org.siemac.metamac.statistical.operations.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.operations.web.client.constants.StatisticalOperationsWebConstants;
import org.siemac.metamac.statistical.operations.web.client.enums.ToolStripButtonEnum;
import org.siemac.metamac.statistical.operations.web.client.events.SelectMenuButtonEvent;
import org.siemac.metamac.statistical.operations.web.client.model.OperationRecord;
import org.siemac.metamac.statistical.operations.web.client.operation.view.handlers.OperationListUiHandlers;
import org.siemac.metamac.statistical.operations.web.client.presenter.MainPagePresenter;
import org.siemac.metamac.statistical.operations.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.operations.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.operations.web.shared.DeleteOperationListAction;
import org.siemac.metamac.statistical.operations.web.shared.DeleteOperationListResult;
import org.siemac.metamac.statistical.operations.web.shared.ExportOperationListAction;
import org.siemac.metamac.statistical.operations.web.shared.ExportOperationListResult;
import org.siemac.metamac.statistical.operations.web.shared.GetOperationPaginatedListAction;
import org.siemac.metamac.statistical.operations.web.shared.GetOperationPaginatedListResult;
import org.siemac.metamac.statistical.operations.web.shared.SaveOperationAction;
import org.siemac.metamac.statistical.operations.web.shared.SaveOperationResult;
import org.siemac.metamac.statistical.operations.web.shared.external.GetExternalResourcesAction;
import org.siemac.metamac.statistical.operations.web.shared.external.GetExternalResourcesResult;
import org.siemac.metamac.statistical.operations.web.shared.DeleteTemporalFileAction;
import org.siemac.metamac.statistical.operations.web.shared.DeleteTemporalFileResult;
import org.siemac.metamac.statistical.operations.web.shared.external.RestWebCriteriaUtils;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
import org.siemac.metamac.web.common.client.utils.WaitingAsyncCallbackHandlingError;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;

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
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.HasClickHandlers;
import com.smartgwt.client.widgets.grid.events.HasRecordClickHandlers;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

public class OperationListPresenter extends Presenter<OperationListPresenter.OperationListView, OperationListPresenter.OperationsListProxy> implements OperationListUiHandlers {

    public final static int     OPERATION_LIST_FIRST_RESULT       = 0;

    private final DispatchAsync dispatcher;
    private final PlaceManager  placeManager;

    public static final Object  TYPE_SetContextAreaContentToolBar = new Object();

    @ProxyCodeSplit
    @NameToken(NameTokens.operationListPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface OperationsListProxy extends Proxy<OperationListPresenter>, Place {

    }

    @TitleFunction
    public static String getTranslatedTitle() {
        return getConstants().breadcrumbStatisticalOperations();
    }

    public interface OperationListView extends View, HasUiHandlers<OperationListUiHandlers> {

        void setOperations(List<OperationBaseDto> operationBaseDtos, int firstResult, int totalResults);
        HasRecordClickHandlers getSelectedOperation();
        HasClickHandlers getSaveNewOperation();
        OperationDto getOperation();
        boolean validate();
        void closeOperationWindow();
        com.smartgwt.client.widgets.events.HasClickHandlers getDelete();
        List<Long> getSelectedOperations();

        // Search
        void clearSearchSection();
        String getOperationCriteria();

        // External resources

        void setItemSchemes(String formItemName, ExternalItemsResult result);
        void setItems(String formItemName, ExternalItemsResult result);
    }

    @Inject
    public OperationListPresenter(EventBus eventBus, OperationListView operationListView, OperationsListProxy operationsListProxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, operationListView, operationsListProxy);
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
                if (event.getFieldNum() != 0) {
                    OperationRecord record = (OperationRecord) event.getRecord();
                    goToOperation(record.getCode());
                }
            }
        }));

        registerHandler(getView().getSaveNewOperation().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (getView().validate()) {
                    createOperation(getView().getOperation());
                }
            }
        }));

        registerHandler(getView().getDelete().addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                deleteOperations(getView().getSelectedOperations());
            }
        }));
    }

    @Override
    protected void onReset() {
        super.onReset();
        retrieveOperationList(OPERATION_LIST_FIRST_RESULT, StatisticalOperationsWebConstants.MAIN_LIST_MAX_RESULTS, null);
    }

    @Override
    protected void onReveal() {
        super.onReveal();
        MainPagePresenter.getMasterHead().setTitleLabel(getConstants().statisticalOperations());
        SelectMenuButtonEvent.fire(this, ToolStripButtonEnum.OPERATIONS);
    }

    @Override
    public void goToOperation(String operationCode) {
        if (!StringUtils.isBlank(operationCode)) {
            placeManager.revealRelativePlace(PlaceRequestUtils.buildRelativeOperationPlaceRequest(operationCode));
        }
    }

    @Override
    public void createOperation(OperationDto operationToSave) {
        dispatcher.execute(new SaveOperationAction(operationToSave), new WaitingAsyncCallbackHandlingError<SaveOperationResult>(this) {

            @Override
            protected void afterFailure() {
                getView().closeOperationWindow();
            }

            @Override
            public void onWaitSuccess(SaveOperationResult result) {
                getView().closeOperationWindow();
                retrieveOperationList(OPERATION_LIST_FIRST_RESULT, StatisticalOperationsWebConstants.MAIN_LIST_MAX_RESULTS, getView().getOperationCriteria());
                ShowMessageEvent.fireSuccessMessage(OperationListPresenter.this, getMessages().operationSaved());
            }
        });
    }

    @Override
    public void retrieveOperationList(int firstResult, int maxResults, final String operation) {
        dispatcher.execute(new GetOperationPaginatedListAction(firstResult, maxResults, operation), new WaitingAsyncCallbackHandlingError<GetOperationPaginatedListResult>(this) {

            @Override
            public void onWaitSuccess(GetOperationPaginatedListResult result) {
                getView().setOperations(result.getOperationBaseDtos(), result.getFirstResultOut(), result.getTotalResults());
                if (StringUtils.isBlank(operation)) {
                    getView().clearSearchSection();
                }
            }
        });
    }

    @Override
    public void deleteOperations(List<Long> operationIds) {
        dispatcher.execute(new DeleteOperationListAction(operationIds), new WaitingAsyncCallbackHandlingError<DeleteOperationListResult>(this) {

            @Override
            public void onWaitSuccess(DeleteOperationListResult result) {
                ShowMessageEvent.fireSuccessMessage(OperationListPresenter.this, getMessages().operationDeleted());
            }

            @Override
            protected void afterResult() {
                retrieveOperationList(OPERATION_LIST_FIRST_RESULT, StatisticalOperationsWebConstants.MAIN_LIST_MAX_RESULTS, getView().getOperationCriteria());
            }
        });
    }

    @Override
    public void exportOperationsTsv(String operation) {
        dispatcher.execute(new ExportOperationListAction(operation), new WaitingAsyncCallbackHandlingError<ExportOperationListResult>(this) {

            @Override
            public void onWaitSuccess(ExportOperationListResult result) {
                CommonUtils.downloadFile(result.getFileName());
                deleteTemporalFile(result.getFileName());
            }
            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(OperationListPresenter.this, caught);
            }

        });
    }

    private void deleteTemporalFile(String fileName) {
        dispatcher.execute(new DeleteTemporalFileAction(fileName), new WaitingAsyncCallbackHandlingError<DeleteTemporalFileResult>(this) {
            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(OperationListPresenter.this, caught);
            }

            @Override
            public void onWaitSuccess(DeleteTemporalFileResult result) {
                // TODO Auto-generated method stub
                
            }
        });
    }
    //
    // EXTERNAL RESOURCES
    //

    @Override
    public void retrieveItemSchemes(final String formItemName, SrmExternalResourceRestCriteria itemSchemeRestCriteria, TypeExternalArtefactsEnum[] types, int firstResult, int maxResults) {
        itemSchemeRestCriteria = RestWebCriteriaUtils.buildItemSchemeWebCriteria(itemSchemeRestCriteria, types);
        retrieveItemSchemes(formItemName, itemSchemeRestCriteria, firstResult, maxResults);
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
    public void retrieveItems(final String formItemName, SrmItemRestCriteria itemRestCriteria, int firstResult, int maxResults) {
        dispatcher.execute(new GetExternalResourcesAction(itemRestCriteria, firstResult, maxResults), new WaitingAsyncCallbackHandlingError<GetExternalResourcesResult>(this) {

            @Override
            public void onWaitSuccess(GetExternalResourcesResult result) {
                getView().setItems(formItemName, result.getExternalItemsResult());
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

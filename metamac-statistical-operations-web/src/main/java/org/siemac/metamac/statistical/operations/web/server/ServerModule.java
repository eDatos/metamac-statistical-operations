package org.siemac.metamac.statistical.operations.web.server;

import org.siemac.metamac.statistical.operations.web.server.handlers.DeleteFamilyListActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.DeleteInstanceListActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.DeleteOperationListActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.DeleteTemporalFileActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.ExportOperationListActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.GetFamilyActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.GetFamilyAndOperationsActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.GetFamilyPaginatedListActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.GetHelpUrlActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.GetInstanceActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.GetInstanceListActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.GetOperationAndInstancesActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.GetOperationPaginatedListActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.GetOperationsListsActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.PublishExternallyFamilyActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.PublishExternallyInstanceActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.PublishExternallyOperationActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.PublishInternallyFamilyActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.PublishInternallyInstanceActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.PublishInternallyOperationActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.ReSendStreamMessageOperationActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.SaveFamilyActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.SaveInstanceActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.SaveOperationActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.UpdateFamilyOperationsActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.UpdateInstancesOrderActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.UpdateOperationFamiliesActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.ValidateTicketActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.external.GetCommonMetadataConfigurationsActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.external.GetExternalResourcesActionHandler;
import org.siemac.metamac.statistical.operations.web.server.handlers.external.GetUsersAccessControlListActionHandler;
import org.siemac.metamac.statistical.operations.web.shared.DeleteFamilyListAction;
import org.siemac.metamac.statistical.operations.web.shared.DeleteInstanceListAction;
import org.siemac.metamac.statistical.operations.web.shared.DeleteOperationListAction;
import org.siemac.metamac.statistical.operations.web.shared.ExportOperationListAction;
import org.siemac.metamac.statistical.operations.web.shared.GetFamilyAction;
import org.siemac.metamac.statistical.operations.web.shared.GetFamilyAndOperationsAction;
import org.siemac.metamac.statistical.operations.web.shared.GetFamilyPaginatedListAction;
import org.siemac.metamac.statistical.operations.web.shared.GetHelpUrlAction;
import org.siemac.metamac.statistical.operations.web.shared.GetInstanceAction;
import org.siemac.metamac.statistical.operations.web.shared.GetInstanceListAction;
import org.siemac.metamac.statistical.operations.web.shared.GetOperationAndInstancesAction;
import org.siemac.metamac.statistical.operations.web.shared.GetOperationPaginatedListAction;
import org.siemac.metamac.statistical.operations.web.shared.GetOperationsListsAction;
import org.siemac.metamac.statistical.operations.web.shared.PublishExternallyFamilyAction;
import org.siemac.metamac.statistical.operations.web.shared.PublishExternallyInstanceAction;
import org.siemac.metamac.statistical.operations.web.shared.PublishExternallyOperationAction;
import org.siemac.metamac.statistical.operations.web.shared.PublishInternallyFamilyAction;
import org.siemac.metamac.statistical.operations.web.shared.PublishInternallyInstanceAction;
import org.siemac.metamac.statistical.operations.web.shared.PublishInternallyOperationAction;
import org.siemac.metamac.statistical.operations.web.shared.ReSendStreamMessageOperationAction;
import org.siemac.metamac.statistical.operations.web.shared.SaveFamilyAction;
import org.siemac.metamac.statistical.operations.web.shared.SaveInstanceAction;
import org.siemac.metamac.statistical.operations.web.shared.SaveOperationAction;
import org.siemac.metamac.statistical.operations.web.shared.UpdateFamilyOperationsAction;
import org.siemac.metamac.statistical.operations.web.shared.UpdateInstancesOrderAction;
import org.siemac.metamac.statistical.operations.web.shared.UpdateOperationFamiliesAction;
import org.siemac.metamac.statistical.operations.web.shared.external.GetCommonMetadataConfigurationsAction;
import org.siemac.metamac.statistical.operations.web.shared.external.GetExternalResourcesAction;
import org.siemac.metamac.statistical.operations.web.shared.external.GetUsersAccessControlListAction;
import org.siemac.metamac.statistical.operations.web.shared.DeleteTemporalFileAction;
import org.siemac.metamac.web.common.server.handlers.CloseSessionActionHandler;
import org.siemac.metamac.web.common.server.handlers.GetLoginPageUrlActionHandler;
import org.siemac.metamac.web.common.server.handlers.GetNavigationBarUrlActionHandler;
import org.siemac.metamac.web.common.server.handlers.LoadConfigurationPropertiesActionHandler;
import org.siemac.metamac.web.common.server.handlers.MockCASUserActionHandler;
import org.siemac.metamac.web.common.shared.CloseSessionAction;
import org.siemac.metamac.web.common.shared.GetLoginPageUrlAction;
import org.siemac.metamac.web.common.shared.GetNavigationBarUrlAction;
import org.siemac.metamac.web.common.shared.LoadConfigurationPropertiesAction;
import org.siemac.metamac.web.common.shared.MockCASUserAction;
import org.siemac.metamac.web.common.shared.ValidateTicketAction;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.server.spring.HandlerModule;

/**
 * Module which binds the handlers and configurations.
 */
@Component
public class ServerModule extends HandlerModule {

    public ServerModule() {
    }

    @Override
    protected void configureHandlers() {

        // Families
        bindHandler(GetFamilyPaginatedListAction.class, GetFamilyPaginatedListActionHandler.class);
        bindHandler(GetFamilyAction.class, GetFamilyActionHandler.class);
        bindHandler(GetFamilyAndOperationsAction.class, GetFamilyAndOperationsActionHandler.class);
        bindHandler(SaveFamilyAction.class, SaveFamilyActionHandler.class);
        bindHandler(DeleteFamilyListAction.class, DeleteFamilyListActionHandler.class);
        bindHandler(UpdateFamilyOperationsAction.class, UpdateFamilyOperationsActionHandler.class);
        bindHandler(PublishInternallyFamilyAction.class, PublishInternallyFamilyActionHandler.class);
        bindHandler(PublishExternallyFamilyAction.class, PublishExternallyFamilyActionHandler.class);

        // Operations
        bindHandler(GetOperationPaginatedListAction.class, GetOperationPaginatedListActionHandler.class);
        bindHandler(GetOperationsListsAction.class, GetOperationsListsActionHandler.class);
        bindHandler(SaveOperationAction.class, SaveOperationActionHandler.class);
        bindHandler(GetOperationAndInstancesAction.class, GetOperationAndInstancesActionHandler.class);
        bindHandler(DeleteOperationListAction.class, DeleteOperationListActionHandler.class);
        bindHandler(DeleteTemporalFileAction.class, DeleteTemporalFileActionHandler.class);
        bindHandler(ExportOperationListAction.class, ExportOperationListActionHandler.class);
        bindHandler(UpdateOperationFamiliesAction.class, UpdateOperationFamiliesActionHandler.class);
        bindHandler(PublishInternallyOperationAction.class, PublishInternallyOperationActionHandler.class);
        bindHandler(PublishExternallyOperationAction.class, PublishExternallyOperationActionHandler.class);
        bindHandler(ReSendStreamMessageOperationAction.class, ReSendStreamMessageOperationActionHandler.class);

        // Instances
        bindHandler(SaveInstanceAction.class, SaveInstanceActionHandler.class);
        bindHandler(GetInstanceAction.class, GetInstanceActionHandler.class);
        bindHandler(DeleteInstanceListAction.class, DeleteInstanceListActionHandler.class);
        bindHandler(GetInstanceListAction.class, GetInstanceListActionHandler.class);
        bindHandler(PublishInternallyInstanceAction.class, PublishInternallyInstanceActionHandler.class);
        bindHandler(PublishExternallyInstanceAction.class, PublishExternallyInstanceActionHandler.class);
        bindHandler(UpdateInstancesOrderAction.class, UpdateInstancesOrderActionHandler.class);

        bindHandler(GetCommonMetadataConfigurationsAction.class, GetCommonMetadataConfigurationsActionHandler.class);

        // External
        bindHandler(GetExternalResourcesAction.class, GetExternalResourcesActionHandler.class);

        bindHandler(GetUsersAccessControlListAction.class, GetUsersAccessControlListActionHandler.class);

        bindHandler(ValidateTicketAction.class, ValidateTicketActionHandler.class);
        bindHandler(GetLoginPageUrlAction.class, GetLoginPageUrlActionHandler.class);
        bindHandler(CloseSessionAction.class, CloseSessionActionHandler.class);
        bindHandler(GetNavigationBarUrlAction.class, GetNavigationBarUrlActionHandler.class);

        bindHandler(LoadConfigurationPropertiesAction.class, LoadConfigurationPropertiesActionHandler.class);
        bindHandler(GetHelpUrlAction.class, GetHelpUrlActionHandler.class);

        // This action should be removed to use CAS authentication
        bindHandler(MockCASUserAction.class, MockCASUserActionHandler.class);
    }
}

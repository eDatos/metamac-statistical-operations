package org.siemac.metamac.statistical.operations.web.server.handlers;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.operations.core.dto.OperationDto;
import org.siemac.metamac.statistical.operations.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.operations.core.serviceapi.StatisticalOperationsServiceFacade;
import org.siemac.metamac.statistical.operations.core.serviceimpl.result.PublishExternallyOperationServiceResult;
import org.siemac.metamac.statistical.operations.web.server.handlers.utils.HandlersUtils;
import org.siemac.metamac.statistical.operations.web.server.rest.NoticesRestInternalFacade;
import org.siemac.metamac.statistical.operations.web.server.rest.serviceapi.ExternalItemValidator;
import org.siemac.metamac.statistical.operations.web.shared.PublishExternallyOperationAction;
import org.siemac.metamac.statistical.operations.web.shared.PublishExternallyOperationResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class PublishExternallyOperationActionHandler extends SecurityActionHandler<PublishExternallyOperationAction, PublishExternallyOperationResult> {

    @Autowired
    private StatisticalOperationsServiceFacade statisticalOperationsServiceFacade;

    @Autowired
    private ExternalItemValidator              externalItemValidator;

    @Autowired
    private NoticesRestInternalFacade          noticesRestInternalFacade;

    public PublishExternallyOperationActionHandler() {
        super(PublishExternallyOperationAction.class);
    }

    @Override
    public PublishExternallyOperationResult executeSecurityAction(PublishExternallyOperationAction action) throws ActionException {
        ServiceContext serviceContext = ServiceContextHolder.getCurrentServiceContext();
        PublishExternallyOperationServiceResult result;

        try {
            OperationDto operationToPublish = statisticalOperationsServiceFacade.findOperationById(ServiceContextHolder.getCurrentServiceContext(), action.getOperationId());
            checkExternalItemsAreExternallyPublished(serviceContext, operationToPublish);
            result = statisticalOperationsServiceFacade.publishExternallyOperation(ServiceContextHolder.getCurrentServiceContext(), action.getOperationId());
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }

        MetamacWebException operationException = HandlersUtils.getExceptionsPublishExternalOperation(serviceContext, result, noticesRestInternalFacade, true);

        return new PublishExternallyOperationResult(result.getContent(), operationException);
    }

    /**
     * Check that all the external items of an {@link OperationDto} are externally published
     *
     * @param operationDto
     * @throws MetamacWebException
     */
    private void checkExternalItemsAreExternallyPublished(ServiceContext serviceContext, OperationDto operationDto) throws MetamacWebException {

        MetamacWebException metamacWebException = new MetamacWebException();

        // CommonMetadata should be always be externally published, so there is no need to check it

        // OPERATION_SUBJECT_AREA and OPERATION_SECONDARY_SUBJECT_AREAS are category elements and they are not published. The associated category scheme is in a constant and
        // it is not selected by users. Because of this, it is externally published.

        externalItemValidator.checkExternalItemsAreExternallyPublished(serviceContext, ServiceExceptionParameters.OPERATION_PRODUCER, operationDto.getProducer(), metamacWebException);
        externalItemValidator.checkExternalItemsAreExternallyPublished(serviceContext, ServiceExceptionParameters.OPERATION_RESPONSIBLE, operationDto.getResponsible(),
                metamacWebException);
        externalItemValidator.checkExternalItemsAreExternallyPublished(serviceContext, ServiceExceptionParameters.OPERATION_CONTRIBUTOR, operationDto.getContributor(),
                metamacWebException);
        externalItemValidator.checkExternalItemsAreExternallyPublished(serviceContext, ServiceExceptionParameters.OPERATION_PUBLISHER, operationDto.getPublisher(), metamacWebException);
        externalItemValidator.checkExternalItemsAreExternallyPublished(serviceContext, ServiceExceptionParameters.OPERATION_UPDATE_FREQUENCY, operationDto.getUpdateFrequency(), metamacWebException);

        if (metamacWebException.getWebExceptionItems() != null && !metamacWebException.getWebExceptionItems().isEmpty()) {
            throw metamacWebException;
        }
    }
}

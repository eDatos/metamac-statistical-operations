package org.siemac.metamac.statistical.operations.web.server.rest;

import java.util.Locale;

import javax.ws.rs.core.Response;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.lang.LocaleUtil;
import org.siemac.metamac.core.common.util.ServiceContextUtils;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.notices.v1_0.domain.Message;
import org.siemac.metamac.rest.notices.v1_0.domain.Notice;
import org.siemac.metamac.rest.notices.v1_0.domain.ResourceInternal;
import org.siemac.metamac.rest.notices.v1_0.domain.enume.MetamacApplicationsEnum;
import org.siemac.metamac.rest.notices.v1_0.domain.enume.MetamacRolesEnum;
import org.siemac.metamac.rest.notices.v1_0.domain.utils.MessageBuilder;
import org.siemac.metamac.rest.notices.v1_0.domain.utils.NoticeBuilder;
import org.siemac.metamac.statistical.operations.core.conf.StatisticalOperationsConfigurationService;
import org.siemac.metamac.statistical.operations.core.dto.OperationDto;
import org.siemac.metamac.statistical.operations.core.mapper.Dto2DoMapper;
import org.siemac.metamac.statistical.operations.core.notices.ServiceNoticeAction;
import org.siemac.metamac.statistical.operations.core.notices.ServiceNoticeMessage;
import org.siemac.metamac.statistical_operations.rest.common.StatisticalOperationsRestConstants;
import org.siemac.metamac.statistical_operations.rest.internal.v1_0.mapper.Do2RestInternalMapperV10;
import org.siemac.metamac.web.common.server.rest.utils.RestExceptionUtils;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(NoticesRestInternalFacade.BEAN_ID)
public class NoticesRestInternalFacadeImpl implements NoticesRestInternalFacade {

    @Autowired
    private RestApiLocator                    restApiLocator;

    @Autowired
    private RestExceptionUtils                restExceptionUtils;

    @Autowired
    StatisticalOperationsConfigurationService configurationService;

    @Autowired
    Dto2DoMapper                              dto2DoMapper;

    @Autowired
    Do2RestInternalMapperV10                  do2RestInternalMapperV10;

    // ---------------------------------------------------------------------------------
    // NOTIFICATIONS
    // ---------------------------------------------------------------------------------

    @Override
    public void createNotificationForPublishExternallyOperation(ServiceContext ctx, OperationDto operation) throws MetamacWebException {
        ResourceInternal resource = this.operationDtoToResourceInternal(ctx, operation);
        ResourceInternal[] noticeResource = {resource};
        MetamacRolesEnum[] roles = {MetamacRolesEnum.TECNICO_PLANIFICACION, MetamacRolesEnum.TECNICO_APOYO_PLANIFICACION, MetamacRolesEnum.TECNICO_PRODUCCION};
        
        this.createPublicationNotification(ctx, ServiceNoticeAction.OPERATION_PUBLISH_EXTERNALLY, ServiceNoticeMessage.OPERATION_PUBLISH_EXTERNALLY_OK, noticeResource, roles);
    }

    @Override
    public void createNotificationForStreamError(ServiceContext ctx, OperationDto operation) throws MetamacWebException {
        ResourceInternal[] resourceInternal = {operationDtoToResourceInternal(ctx, operation)};
        createErrorOnStreamMessagingNotification(ctx, resourceInternal);
    }

    @Override
    public void createNotificationForPublishInternallyOperation(ServiceContext ctx, OperationDto operation) throws MetamacWebException {
        ResourceInternal resource = this.operationDtoToResourceInternal(ctx, operation);
        ResourceInternal[] noticeResource = {resource};
        MetamacRolesEnum[] roles = {MetamacRolesEnum.TECNICO_PLANIFICACION, MetamacRolesEnum.TECNICO_APOYO_PLANIFICACION, MetamacRolesEnum.TECNICO_PRODUCCION};
        
        this.createPublicationNotification(ctx, ServiceNoticeAction.OPERATION_PUBLISH_INTERNALLY, ServiceNoticeMessage.OPERATION_PUBLISH_INTERNALLY_OK, noticeResource, roles);
    }

    private void createPublicationNotification(ServiceContext ctx, String actionCode, String messageCode, ResourceInternal[] resources, MetamacRolesEnum[] roles) throws MetamacWebException {
        Locale locale = ServiceContextUtils.getLocale(ctx);
        String subject = LocaleUtil.getMessageForCode(actionCode, locale);
        String localisedMessage = LocaleUtil.getMessageForCode(messageCode, locale);
        String sendingApp = MetamacApplicationsEnum.GESTOR_OPERACIONES.getName();
        MetamacApplicationsEnum[] applications = {MetamacApplicationsEnum.GESTOR_OPERACIONES};
        
        // @formatter:off
		Message message = MessageBuilder.message().withText(localisedMessage)
				.withResources(resources).build();

		Notice notification = NoticeBuilder.notification()
				.withMessages(message).withSendingApplication(sendingApp)
				.withSendingUser(ctx.getUserId()).withSubject(subject)
				.withApplications(applications)
				.withRoles(roles)
				.build();
		// @formatter:on

        Response response = restApiLocator.getNoticesRestInternalFacadeV10().createNotice(notification);
        restExceptionUtils.checkSendNotificationRestResponseAndThrowErrorIfApplicable(ctx, response);
    }

    private void createErrorOnStreamMessagingNotification(ServiceContext ctx, ResourceInternal[] resources) throws MetamacWebException {
        Locale locale = ServiceContextUtils.getLocale(ctx);
        String subject = LocaleUtil.getMessageForCode(ServiceNoticeAction.STREAM_MESSAGE_SEND, locale);
        String localisedMessage = LocaleUtil.getMessageForCode(ServiceNoticeMessage.STREAM_MESSAGE_SEND_ERROR, locale);
        String sendingApp = MetamacApplicationsEnum.GESTOR_OPERACIONES.getName();

        // @formatter:off
		Message message = MessageBuilder.message().withText(localisedMessage)
				.withResources(resources).build();

		Notice notification = NoticeBuilder.notification()
				.withMessages(message).withSendingApplication(sendingApp)
				.withSendingUser(ctx.getUserId()).withSubject(subject)
                .withRoles(MetamacRolesEnum.ADMINISTRADOR).build();
		// @formatter:on

        Response response = restApiLocator.getNoticesRestInternalFacadeV10().createNotice(notification);
        restExceptionUtils.checkSendNotificationRestResponseAndThrowErrorIfApplicable(ctx, response);
    }

    private ResourceInternal operationDtoToResourceInternal(ServiceContext ctx, OperationDto operationDto) {
        ResourceInternal resource = new ResourceInternal();

        if (operationDto != null) {
            resource.setId(operationDto.getCode());
            resource.setUrn(operationDto.getUrn());
            resource.setKind(StatisticalOperationsRestConstants.KIND_OPERATION);
            resource.setName(this.toInternationalString(operationDto.getTitle()));
            resource.setManagementAppLink(this.toOperationManagementApplicationLink(operationDto));
            resource.setSelfLink(this.toOperationSelfLink(operationDto));
        }
        return resource;
    }

    private ResourceLink toOperationSelfLink(OperationDto source) {
        return this.do2RestInternalMapperV10.toOperationSelfLink(source.getCode());
    }

    private String toOperationManagementApplicationLink(OperationDto source) {
        return this.do2RestInternalMapperV10.toOperationManagementApplicationLink(source.getCode());
    }

    private InternationalString toInternationalString(InternationalStringDto sources) {
        if (sources == null) {
            return null;
        }
        InternationalString targets = new InternationalString();
        for (LocalisedStringDto source : sources.getTexts()) {
            LocalisedString target = new LocalisedString();
            target.setValue(source.getLabel());
            target.setLang(source.getLocale());
            targets.getTexts().add(target);
        }
        return targets;
    }

}

package org.siemac.metamac.statistical.operations.core.security;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.sso.client.SsoClientConstants;
import org.siemac.metamac.statistical.operations.core.enume.domain.StatisticalOperationsRoleEnum;
import org.siemac.metamac.statistical.operations.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.operations.core.security.shared.SharedSecurityUtils;

public class SecurityUtils extends SharedSecurityUtils {

    /**
     * Checks user can execute any operation, if has any role of requested roles
     */
    public static void checkServiceOperationAllowed(ServiceContext ctx, StatisticalOperationsRoleEnum... roles) throws MetamacException {

        MetamacPrincipal metamacPrincipal = getMetamacPrincipal(ctx);

        // Administration has total control
        if (isAdministrador(metamacPrincipal)) {
            return;
        }
        // Checks user has any role of requested
        if (roles != null) {
            for (int i = 0; i < roles.length; i++) {
                StatisticalOperationsRoleEnum role = roles[i];
                if (isUserInRol(metamacPrincipal, role)) {
                    return;
                }
            }
        }
        throw new MetamacException(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED, metamacPrincipal.getUserId());
    }

    /**
     * Checks user has access to an operation or indicators system. To have access, user must have this indicators system in any role of requested roles
     */
    public static void checkResourceOperationAllowed(ServiceContext ctx, String operationCode, StatisticalOperationsRoleEnum... roles) throws MetamacException {

        MetamacPrincipal metamacPrincipal = getMetamacPrincipal(ctx);

        // Administration has total control in all indicators systems
        if (isAdministrador(metamacPrincipal)) {
            return;
        }

        // Checks if operation is in any role
        if (roles != null) {
            for (int i = 0; i < roles.length; i++) {
                StatisticalOperationsRoleEnum role = roles[i];
                if (haveAccessToOperationInRol(metamacPrincipal, role, operationCode)) {
                    return;
                }
            }
        }
        throw new MetamacException(ServiceExceptionType.SECURITY_ACCESS_OPERATION_NOT_ALLOWED, operationCode, metamacPrincipal.getUserId());
    }

    /**
     * Retrieves MetamacPrincipal in ServiceContext
     */
    private static MetamacPrincipal getMetamacPrincipal(ServiceContext ctx) throws MetamacException {
        Object principalProperty = ctx.getProperty(SsoClientConstants.PRINCIPAL_ATTRIBUTE);
        if (principalProperty == null) {
            throw new MetamacException(ServiceExceptionType.SECURITY_PRINCIPAL_NOT_FOUND);
        }
        MetamacPrincipal metamacPrincipal = (MetamacPrincipal) principalProperty;
        if (!metamacPrincipal.getUserId().equals(ctx.getUserId())) {
            throw new MetamacException(ServiceExceptionType.SECURITY_PRINCIPAL_NOT_FOUND);
        }
        return metamacPrincipal;
    }

}
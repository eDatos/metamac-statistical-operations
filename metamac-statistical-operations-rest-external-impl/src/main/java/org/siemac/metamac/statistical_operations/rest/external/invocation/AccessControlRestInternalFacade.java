package org.siemac.metamac.statistical_operations.rest.external.invocation;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.access_control.v1_0.domain.User;

public interface AccessControlRestInternalFacade {

    public List<User> findAllUsers() throws MetamacException;
}

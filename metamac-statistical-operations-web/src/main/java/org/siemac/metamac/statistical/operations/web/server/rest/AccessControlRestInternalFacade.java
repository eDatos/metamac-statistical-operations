package org.siemac.metamac.statistical.operations.web.server.rest;

import java.util.List;

import org.siemac.metamac.rest.access_control.v1_0.domain.User;
import org.siemac.metamac.rest.exception.RestException;

public interface AccessControlRestInternalFacade {

    List<User> findAllUsers() throws RestException;
}

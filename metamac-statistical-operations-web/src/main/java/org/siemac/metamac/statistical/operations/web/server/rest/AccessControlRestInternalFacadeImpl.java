package org.siemac.metamac.statistical.operations.web.server.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.client.ServerWebApplicationException;
import org.apache.cxf.jaxrs.client.WebClient;
import org.siemac.metamac.rest.access_control.v1_0.domain.User;
import org.siemac.metamac.rest.access_control.v1_0.domain.Users;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.statistical_operations.rest.internal.invocation.MetamacApisLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("accessControlRestInternalFacade")
public class AccessControlRestInternalFacadeImpl implements AccessControlRestInternalFacade {

    private final Logger       logger = LoggerFactory.getLogger(AccessControlRestInternalFacadeImpl.class);

    @Autowired
    private MetamacApisLocator restApiLocator;

    @Override
    public List<User> findAllUsers() throws RestException {

        try {
            String limit = "1000";
            int offset = 0;
            List<User> results = new ArrayList<>();
            Users users = null;
            do {
                users = restApiLocator.getAccessControlRestInternalFacadeV1_0().findUsers(null, limit, String.valueOf(offset));
                results.addAll(users.getUsers());
                offset += users.getUsers().size(); // next page
            } while (users.getTotal().intValue() != results.size());
            return results;

        } catch (ServerWebApplicationException e) {
            throw toRestException(e);
        }
    }

    private RestException toRestException(Exception e) {
        logger.error("Error", e);
        return RestExceptionUtils.toRestException(e, WebClient.client(restApiLocator.getAccessControlRestInternalFacadeV1_0()));
    }
}

package org.siemac.metamac.statistical.operations.web.server.handlers.external;

import java.util.LinkedHashMap;
import java.util.List;

import org.siemac.metamac.rest.access_control.v1_0.domain.User;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.statistical.operations.web.server.rest.AccessControlRestInternalFacade;
import org.siemac.metamac.statistical.operations.web.shared.external.GetUsersAccessControlListAction;
import org.siemac.metamac.statistical.operations.web.shared.external.GetUsersAccessControlListResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetUsersAccessControlListActionHandler extends SecurityActionHandler<GetUsersAccessControlListAction, GetUsersAccessControlListResult> {

    @Autowired
    private AccessControlRestInternalFacade accessControlRestInternalFacade;

    public GetUsersAccessControlListActionHandler() {
        super(GetUsersAccessControlListAction.class);
    }

    @Override
    public GetUsersAccessControlListResult executeSecurityAction(GetUsersAccessControlListAction action) throws ActionException {
        LinkedHashMap<String, String> usersByUsername = new LinkedHashMap<String, String>();
        try {

            List<User> users = accessControlRestInternalFacade.findAllUsers();
            String userFullname = "";
            usersByUsername.put(new String(), new String());
            for (User user : users) {
                userFullname = user.getName() + " " + user.getSurname() + " - " + user.getUsername();
                usersByUsername.put(user.getUsername(), userFullname);
            }
        } catch (RestException e) {
            throw WebExceptionUtils.createMetamacWebException(e.getException());
        }
        return new GetUsersAccessControlListResult(usersByUsername);
    }

}

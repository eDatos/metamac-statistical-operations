package org.siemac.metamac.statistical.operations.web.shared.external;

import java.util.LinkedHashMap;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetUsersAccessControlList {

    @Out(1)
    LinkedHashMap<String, String> users;

}

package org.siemac.metamac.statistical.operations.core.utils;

import java.util.List;

import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;

public class InternationalStringUtils {

    public static InternationalString getCommonInternationalStringFromRestInternationalString(org.siemac.metamac.rest.common.v1_0.domain.InternationalString restInternationalString) {
        if (restInternationalString != null) {
            InternationalString commonInternationalString = new InternationalString();
            List<org.siemac.metamac.rest.common.v1_0.domain.LocalisedString> restLocalisedStrings = restInternationalString.getTexts();
            for (org.siemac.metamac.rest.common.v1_0.domain.LocalisedString restLocalisedString : restLocalisedStrings) {
                LocalisedString commonLocalisedString = new LocalisedString();
                commonLocalisedString.setLocale(restLocalisedString.getLang());
                commonLocalisedString.setLabel(restLocalisedString.getValue());
                commonInternationalString.addText(commonLocalisedString);
            }
            return commonInternationalString;
        }
        return null;
    }

}

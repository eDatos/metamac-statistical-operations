package org.siemac.metamac.statistical.operations.core.utils.mocks;

import org.siemac.metamac.common.test.utils.MetamacMocks;
import org.siemac.metamac.core.common.constants.CoreCommonConstants;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CategoryResourceInternal;

public class StatisticalOperationsMocks extends MetamacMocks {

    // -----------------------------------------------------------------
    // INTERNATIONAL STRING
    // -----------------------------------------------------------------

    public static InternationalString mockInternationalString() {
        InternationalString internationalString = new InternationalString();
        LocalisedString es = new LocalisedString();
        es.setLabel(mockString(10) + " en Espanol");
        es.setLocale("es");
        es.setVersion(Long.valueOf(0));
        LocalisedString en = new LocalisedString();
        en.setLabel(mockString(10) + " in English");
        en.setLocale("en");
        en.setVersion(Long.valueOf(0));
        internationalString.addText(es);
        internationalString.addText(en);
        internationalString.setVersion(Long.valueOf(0));
        return internationalString;
    }

    /**
     * Mock an InternationalString with one locale
     */
    public static InternationalString mockInternationalString(String locale, String label) {
        InternationalString target = new InternationalString();
        LocalisedString localisedString = new LocalisedString();
        localisedString.setLocale(locale);
        localisedString.setLabel(label);
        target.addText(localisedString);
        return target;
    }

    // -----------------------------------------------------------------
    // EXTERNAL ITEM
    // -----------------------------------------------------------------

    public static ExternalItem mockAgencyExternalItem() {
        String code = mockCode();
        return mockSrmAppExternalItem(code, mockAgencyUrn(code), TypeExternalArtefactsEnum.AGENCY);
    }

    public static ExternalItem mockCodeExternalItem() {
        String code = mockCode();
        return mockSrmAppExternalItem(code, mockCodeUrn(code), TypeExternalArtefactsEnum.CODE);
    }

    public static ExternalItem mockExternalItem(String code, String uri, String urn, String urnProvider, TypeExternalArtefactsEnum type) {
        ExternalItem target = new ExternalItem();
        target.setVersion(Long.valueOf(0));
        target.setCode(code);
        target.setUri(uri);
        target.setUrn(urn);
        target.setUrnProvider(urnProvider);
        target.setType(type);
        return target;
    }

    public static ExternalItem mockExternalItem(String code, String uri, String urn, String urnProvider, TypeExternalArtefactsEnum type, InternationalString title, String managementAppUrl) {
        ExternalItem target = mockExternalItem(code, uri, urn, urnProvider, type);
        target.setTitle(title);
        target.setManagementAppUrl(managementAppUrl);
        return target;
    }

    // SRM Category resource internal
    public static CategoryResourceInternal mockCategoryResourceInternal(String code, String uri, String urn, String urnProvider) {
        CategoryResourceInternal categoryResourceInternal = new CategoryResourceInternal();
        categoryResourceInternal.setId(code);
        categoryResourceInternal.setUrn(urn);
        categoryResourceInternal.setUrnProvider(urnProvider);
        categoryResourceInternal.setManagementAppLink(uri);
        return categoryResourceInternal;
    }

    // -----------------------------------------------------------------
    // PRIVATE
    // -----------------------------------------------------------------

    private static ExternalItem mockSrmAppExternalItem(String code, String urn, TypeExternalArtefactsEnum type) {
        return mockExternalItem(code, CoreCommonConstants.API_LATEST_WITH_SLASHES + code, urn, urn + ":provider", type, mockInternationalString(), CoreCommonConstants.URL_SEPARATOR + code);
    }
}

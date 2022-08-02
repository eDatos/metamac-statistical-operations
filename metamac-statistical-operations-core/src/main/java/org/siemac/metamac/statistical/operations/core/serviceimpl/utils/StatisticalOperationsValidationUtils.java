package org.siemac.metamac.statistical.operations.core.serviceimpl.utils;

import java.util.Collection;
import java.util.List;

import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.enume.utils.TypeExternalArtefactsEnumUtils;
import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils;
import org.siemac.metamac.statistical.operations.core.error.ServiceExceptionType;


public class StatisticalOperationsValidationUtils extends ValidationUtils {

    /**
     * Check for a required metadata and add an exception for a failed validation.
     * 
     * @param parameter
     * @param parameterName
     * @param exceptions
     */
    public static void checkMetadataRequired(InternationalString parameter, String parameterName, List<MetamacExceptionItem> exceptions) {
        if (isEmpty(parameter)) {
            exceptions.add(new MetamacExceptionItem(CommonServiceExceptionType.METADATA_REQUIRED, parameterName));
        }
    }

    /**
     * Check for a required metadata and add an exception for a failed validation.
     * 
     * @param parameter
     * @param parameterName
     * @param exceptions
     */
    public static void checkMetadataRequired(ExternalItem parameter, String parameterName, List<MetamacExceptionItem> exceptions) {
        if (isEmpty(parameter)) {
            exceptions.add(new MetamacExceptionItem(CommonServiceExceptionType.METADATA_REQUIRED, parameterName));
        } else {
            checkUrnExternalItemRequired(parameter, parameterName, exceptions);
        }
    }

    private static void checkUrnExternalItemRequired(ExternalItem parameter, String parameterName, List<MetamacExceptionItem> exceptions) {
        if (TypeExternalArtefactsEnumUtils.isExternalItemOfCommonMetadataApp(parameter.getType())) {
            checkMetadataRequired(parameter.getUrn(), parameterName, exceptions);
            checkMetadataEmpty(parameter.getUrnProvider(), parameterName, exceptions);
        } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfStatisticalOperationsApp(parameter.getType())) {
            checkMetadataRequired(parameter.getUrn(), parameterName, exceptions);
            checkMetadataEmpty(parameter.getUrnProvider(), parameterName, exceptions);
        } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfSrmApp(parameter.getType())) {
            checkMetadataRequired(parameter.getUrn(), parameterName, exceptions);
            checkMetadataOptionalIsValid(parameter.getUrnProvider(), parameterName, exceptions);
        } else {
            exceptions.add(new MetamacExceptionItem(ServiceExceptionType.UNKNOWN, "Unknown type of ExternalItem"));
        }
    }

    /**
     * Check InternationalString is valid
     */
    public static void checkMetadataOptionalIsValid(InternationalString parameter, String parameterName, List<MetamacExceptionItem> exceptions) {
        if (parameter == null) {
            return;
        }

        // if it is not null, it must be complete
        if (isEmpty(parameter)) {
            exceptions.add(new MetamacExceptionItem(CommonServiceExceptionType.METADATA_INCORRECT, parameterName));
        }
    }

    /**
     * Check ExternalItem is valid
     */
    public static void checkMetadataOptionalIsValid(ExternalItem parameter, String parameterName, List<MetamacExceptionItem> exceptions) {
        if (parameter == null) {
            return;
        }

        // if it is not null, it must be complete
        if (isEmpty(parameter)) {
            exceptions.add(new MetamacExceptionItem(CommonServiceExceptionType.METADATA_INCORRECT, parameterName));
        } else {
            checkUrnExternalItemRequired(parameter, parameterName, exceptions);
        }
    }

    /**
     * Check if a collection metadata is valid.
     * 
     * @param parameter
     * @param parameterName
     * @param exceptions
     */

    @SuppressWarnings("rawtypes")
    public static void checkListMetadataOptionalIsValid(Collection parameter, String parameterName, List<MetamacExceptionItem> exceptions) {

        if (parameter == null) {
            return;
        }

        int exceptionSize = exceptions.size();

        for (Object item : parameter) {
            if (InternationalString.class.isInstance(item)) {
                checkMetadataOptionalIsValid((InternationalString) item, parameterName, exceptions);
            } else if (ExternalItem.class.isInstance(item)) {
                checkMetadataOptionalIsValid((ExternalItem) item, parameterName, exceptions);
            } else {
                checkMetadataOptionalIsValid(item, parameterName, exceptions);
            }

            // With one incorrect item is enough
            if (exceptions.size() > exceptionSize) {
                return;
            }
        }
    }

    /**
     * Check if an InternationalString is empty.
     */
    private static Boolean isEmpty(InternationalString parameter) {
        if (parameter == null) {
            return Boolean.TRUE;
        }
        if (parameter.getTexts().size() == 0) {
            return Boolean.TRUE;
        }
        for (LocalisedString localisedString : parameter.getTexts()) {
            if (isEmpty(localisedString.getLabel()) || isEmpty(localisedString.getLocale())) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * Check if an ExternalItem is empty.
     * 
     * @param parameter
     * @return
     */
    private static Boolean isEmpty(ExternalItem parameter) {
        if (parameter == null) {
            return Boolean.TRUE;
        }
        return isEmpty(parameter.getCode()) || isEmpty(parameter.getUri()) || isEmpty(parameter.getType());
    }
    
}

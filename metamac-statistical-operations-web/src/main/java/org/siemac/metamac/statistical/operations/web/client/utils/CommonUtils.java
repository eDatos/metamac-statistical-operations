package org.siemac.metamac.statistical.operations.web.client.utils;

import static org.siemac.metamac.statistical.operations.web.client.OperationsWeb.getCoreMessages;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.operations.core.dto.FamilyBaseDto;
import org.siemac.metamac.statistical.operations.core.dto.InstanceDto;
import org.siemac.metamac.statistical.operations.core.dto.OperationBaseDto;
import org.siemac.metamac.statistical.operations.core.dto.OperationDto;
import org.siemac.metamac.statistical.operations.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.operations.core.enume.domain.StatusEnum;
import org.siemac.metamac.statistical.operations.core.enume.domain.StreamMessageStatusEnum;
import org.siemac.metamac.statistical.operations.web.client.OperationsWeb;
import org.siemac.metamac.statistical.operations.web.client.constants.StatisticalOperationsWebConstants;
import org.siemac.metamac.web.common.client.resources.GlobalResources;
import org.siemac.metamac.web.common.shared.utils.SharedTokens;

import com.google.gwt.http.client.URL;
import com.google.gwt.resources.client.ImageResource;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.validator.LengthRangeValidator;

public class CommonUtils {

    private static LinkedHashMap<String, String>                         statusEnumHashMap;

    private static final EnumMap<StreamMessageStatusEnum, ImageResource> ICON_STREAM_MESSAGE_STATUS = new EnumMap(StreamMessageStatusEnum.class);
    static {
        ICON_STREAM_MESSAGE_STATUS.put(StreamMessageStatusEnum.FAILED, GlobalResources.RESOURCE.errorSmart());
        ICON_STREAM_MESSAGE_STATUS.put(StreamMessageStatusEnum.PENDING, GlobalResources.RESOURCE.warn());
        ICON_STREAM_MESSAGE_STATUS.put(StreamMessageStatusEnum.SENT, GlobalResources.RESOURCE.success());
    }

    public static LinkedHashMap<String, String> getStatusEnumHashMap() {
        if (statusEnumHashMap == null) {
            statusEnumHashMap = new LinkedHashMap<String, String>();
            for (StatusEnum s : StatusEnum.values()) {
                String value = getCoreMessages().getString(getCoreMessages().statusEnum() + s.getName());
                statusEnumHashMap.put(s.toString(), value);
            }
        }
        return statusEnumHashMap;
    }

    public static String getProcStatusName(ProcStatusEnum procStatusEnum) {
        if (procStatusEnum != null) {
            return getCoreMessages().getString(getCoreMessages().procStatusEnum() + procStatusEnum.getName());
        }
        return StringUtils.EMPTY;
    }

    public static String getStatusName(StatusEnum statusEnum) {
        if (statusEnum != null) {
            return getCoreMessages().getString(getCoreMessages().statusEnum() + statusEnum.getName());
        }
        return StringUtils.EMPTY;
    }

    public static boolean isInternallyOrExternallyPublished(OperationDto operationDto) {
        return isInternallyOrExternallyPublished(operationDto.getProcStatus());
    }

    public static boolean isInternallyOrExternallyPublished(InstanceDto instanceDto) {
        return isInternallyOrExternallyPublished(instanceDto.getProcStatus());
    }

    private static boolean isInternallyOrExternallyPublished(ProcStatusEnum procStatusEnum) {
        if (ProcStatusEnum.PUBLISH_INTERNALLY.equals(procStatusEnum) || ProcStatusEnum.PUBLISH_EXTERNALLY.equals(procStatusEnum)) {
            return true;
        }
        return false;
    }

    public static ExternalItemDto create(OperationBaseDto operationBaseDto) {
        ExternalItemDto externalItemDto = new ExternalItemDto();
        externalItemDto.setId(operationBaseDto.getId());
        externalItemDto.setCode(operationBaseDto.getCode());
        externalItemDto.setTitle(operationBaseDto.getTitle());
        externalItemDto.setUrn(operationBaseDto.getUrn());
        return externalItemDto;
    }

    public static List<ExternalItemDto> createOperations(List<OperationBaseDto> operations) {
        List<ExternalItemDto> externalItemDtos = new ArrayList<ExternalItemDto>();
        for (OperationBaseDto operation : operations) {
            externalItemDtos.add(create(operation));
        }
        return externalItemDtos;
    }

    public static ExternalItemDto create(FamilyBaseDto familyBaseDto) {
        ExternalItemDto externalItemDto = new ExternalItemDto();
        externalItemDto.setId(familyBaseDto.getId());
        externalItemDto.setCode(familyBaseDto.getCode());
        externalItemDto.setTitle(familyBaseDto.getTitle());
        externalItemDto.setUrn(familyBaseDto.getUrn());
        return externalItemDto;
    }

    public static List<ExternalItemDto> createFamilies(List<FamilyBaseDto> families) {
        List<ExternalItemDto> externalItemDtos = new ArrayList<ExternalItemDto>(families.size());
        for (FamilyBaseDto family : families) {
            externalItemDtos.add(create(family));
        }
        return externalItemDtos;
    }

    public static void calculateOperationsToUpdateInFamily(List<OperationBaseDto> initialSelectedOperations, List<ExternalItemDto> selectedOperations, List<Long> operationsToAdd,
            List<Long> operationsToRemove) {
        List<Long> initialOperationsIds = getOperationIds(initialSelectedOperations);
        List<Long> selectedOperationIds = getExternalItemIds(selectedOperations);
        calculateResourcesToUpdate(initialOperationsIds, selectedOperationIds, operationsToAdd, operationsToRemove);
    }

    public static void calculateFamiliesToUpdateInOperation(List<FamilyBaseDto> initialSelectedFamilies, List<ExternalItemDto> selectedOperations, List<Long> operationsToAdd,
            List<Long> operationsToRemove) {
        List<Long> initialFamiliesIds = getFamilyIds(initialSelectedFamilies);
        List<Long> selectedFamilyIds = getExternalItemIds(selectedOperations);
        calculateResourcesToUpdate(initialFamiliesIds, selectedFamilyIds, operationsToAdd, operationsToRemove);
    }

    private static void calculateResourcesToUpdate(List<Long> initialResourcesIds, List<Long> selectedOperationIds, List<Long> resourcesToAdd, List<Long> resourcesToRemove) {
        for (Long id : selectedOperationIds) {
            if (!initialResourcesIds.contains(id)) {
                resourcesToAdd.add(id);
            }
        }
        for (Long id : initialResourcesIds) {
            if (!selectedOperationIds.contains(id)) {
                resourcesToRemove.add(id);
            }
        }
    }

    public static List<Long> getOperationIds(List<OperationBaseDto> operationBaseDtos) {
        List<Long> ids = new ArrayList<Long>(operationBaseDtos.size());
        for (OperationBaseDto operationBaseDto : operationBaseDtos) {
            ids.add(operationBaseDto.getId());
        }
        return ids;
    }

    public static List<Long> getFamilyIds(List<FamilyBaseDto> familyBaseDtos) {
        List<Long> ids = new ArrayList<Long>(familyBaseDtos.size());
        for (FamilyBaseDto familyBaseDto : familyBaseDtos) {
            ids.add(familyBaseDto.getId());
        }
        return ids;
    }

    public static List<Long> getExternalItemIds(List<ExternalItemDto> externalItemDtos) {
        List<Long> ids = new ArrayList<Long>(externalItemDtos.size());
        for (ExternalItemDto externalItemDto : externalItemDtos) {
            ids.add(externalItemDto.getId());
        }
        return ids;
    }

    // Operation code length validator

    public static LengthRangeValidator getOperationCodeLengthValidator() {
        LengthRangeValidator lengthRangeValidator = new LengthRangeValidator();
        lengthRangeValidator.setMin(0);
        lengthRangeValidator.setMax(StatisticalOperationsWebConstants.OPERATION_CODE_MAX_LENGTH);
        return lengthRangeValidator;
    }

    // STATUS KAFKA MESSAGE OPERATIONS

    public static FormItemIcon getPublicationStreamStatusIcon(StreamMessageStatusEnum status) {
        if (status == null) {
            return null;
        }
        FormItemIcon icon = new FormItemIcon();
        icon.setSrc(ICON_STREAM_MESSAGE_STATUS.get(status).getURL());

        return icon;
    }

    // EXPORTATION UTILS

    public static void downloadFile(String fileName) {
        StringBuffer url = new StringBuffer();
        url.append(URL.encode(OperationsWeb.getRelativeURL(SharedTokens.FILE_DOWNLOAD_DIR_PATH)));
        url.append("?").append(URL.encode(SharedTokens.PARAM_FILE_NAME)).append("=").append(URL.encode(fileName));
        downloadUrl(url.toString());
    }

    private static native void downloadUrl(String url) /*-{
		$wnd.location = url;
    }-*/;


}

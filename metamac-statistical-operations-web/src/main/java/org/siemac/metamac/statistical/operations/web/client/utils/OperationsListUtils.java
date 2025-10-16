package org.siemac.metamac.statistical.operations.web.client.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.siemac.edatos.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.operations.core.dto.CollMethodDto;
import org.siemac.metamac.statistical.operations.core.dto.CostDto;
import org.siemac.metamac.statistical.operations.core.dto.InstanceTypeDto;
import org.siemac.metamac.statistical.operations.core.dto.OfficialityTypeDto;
import org.siemac.metamac.statistical.operations.core.dto.SurveySourceDto;
import org.siemac.metamac.statistical.operations.core.dto.SurveyTypeDto;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;

public class OperationsListUtils {

    private static final String LEFT_PADDED              = "00000";
    private static final String ARTIFICIAL_KEY_SEPARATOR = "_";

    /**
     * Returns {@link LinkedHashMap} of {@link SurveyTypeDto}
     * 
     * @return
     */
    public static LinkedHashMap<String, String> getSurveyTypeHashMap(List<SurveyTypeDto> list) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put(new String(), new String());
        for (SurveyTypeDto type : list) {
            hashMap.put(type.getId().toString(), CommonWebUtils.getElementName(type.getIdentifier(), type.getDescription()));
        }
        return hashMap;
    }

    /**
     * Returns {@link LinkedHashMap} of {@link InstanceTypeDto}
     * 
     * @return
     */
    public static LinkedHashMap<String, String> getInstanceTypeHashMap(List<InstanceTypeDto> list) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put(new String(), new String());
        for (InstanceTypeDto type : list) {
            hashMap.put(type.getId().toString(), CommonWebUtils.getElementName(type.getIdentifier(), type.getDescription()));
        }
        return hashMap;
    }

    /**
     * Returns {@link LinkedHashMap} of {@link OfficialityTypeDto}
     * 
     * @return
     */
    public static LinkedHashMap<String, String> getOfficialityTypeHashMap(List<OfficialityTypeDto> list) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put(new String(), new String());
        for (OfficialityTypeDto type : list) {
            hashMap.put(type.getId().toString(), CommonWebUtils.getElementName(type.getIdentifier(), type.getDescription()));
        }
        return hashMap;
    }

    /**
     * Returns {@link LinkedHashMap} of {@link SurveySourceDto}
     * 
     * @return
     */
    public static LinkedHashMap<String, String> getSurveySourceHashMap(List<SurveySourceDto> list) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put(new String(), new String());
        for (SurveySourceDto type : list) {
            hashMap.put(type.getId().toString(), CommonWebUtils.getElementName(type.getIdentifier(), type.getDescription()));
        }
        return hashMap;
    }

    /**
     * Returns {@link LinkedHashMap} of {@link CollMethodDto}
     * 
     * @return
     */
    public static LinkedHashMap<String, String> getCollMethodsHashMap(List<CollMethodDto> list) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("", "");
        for (int i = 0; i < list.size(); i++) {
            CollMethodDto dto = list.get(i);
            hashMap.put(getArtificialKey(i, dto.getId()), CommonWebUtils.getElementName(dto.getIdentifier(), dto.getDescription(), dto.getHierarchyLevel(),false));
        }
        return hashMap;
    }


    /**
     * Returns {@link LinkedHashMap} of {@link CollMethodDto}
     *
     * @return
     */
    public static String getCollMethodArtificialKey(List<CollMethodDto> collMethodDtosList, Long id) {
        String selectedKey = null;

        for (int i = 0; i < collMethodDtosList.size(); i++) {
            CollMethodDto dtoItem = collMethodDtosList.get(i);

            if (dtoItem.getId().equals(id)) {
                selectedKey = getArtificialKey(i, dtoItem.getId());
                break;
            }
        }

        return selectedKey;

    }

    private static String getArtificialKey(int positionInList, Long itemId) {
        String paddedIndex = (LEFT_PADDED + positionInList).substring((LEFT_PADDED + positionInList).length() - LEFT_PADDED.length());
        return paddedIndex + ARTIFICIAL_KEY_SEPARATOR + itemId;
    }

    /**
     * return real key from an artificial key with five number and "-" at the beggining of the real key.
     * Returns {@String}
     *
     * @param key
     * @return
     */
    private static String getCollMethodKey(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return key.split(ARTIFICIAL_KEY_SEPARATOR)[1];
    }

    /**
     * Returns {@link LinkedHashMap} of {@link CostDto}
     * 
     * @return
     */
    public static LinkedHashMap<String, String> getCostHashMap(List<CostDto> list) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put(new String(), new String());
        for (CostDto type : list) {
            hashMap.put(type.getId().toString(), CommonWebUtils.getElementName(type.getIdentifier(), type.getDescription()));
        }
        return hashMap;
    }

    /**
     * Returns {@link SurveyTypeDto} from id
     * 
     * @param officialityTypeDtos
     * @param id
     * @return
     */
    public static SurveyTypeDto getSurveyTypeDto(String id, List<SurveyTypeDto> list) {
        if (id != null && !id.isEmpty()) {
            Long idType = Long.valueOf(id);
            for (SurveyTypeDto o : list) {
                if (o.getId().compareTo(idType) == 0) {
                    return o;
                }
            }
        }
        return null;
    }

    /**
     * Returns {@link InstanceTypeDto} from id
     * 
     * @param officialityTypeDtos
     * @param id
     * @return
     */
    public static InstanceTypeDto getInstanceTypeDto(String id, List<InstanceTypeDto> list) {
        if (id != null && !id.isEmpty()) {
            Long idType = Long.valueOf(id);
            for (InstanceTypeDto o : list) {
                if (o.getId().compareTo(idType) == 0) {
                    return o;
                }
            }
        }
        return null;
    }

    /**
     * Returns {@link SurveySourceDto} from id
     * 
     * @param officialityTypeDtos
     * @param id
     * @return
     */
    public static SurveySourceDto getSurveySourceDto(String id, List<SurveySourceDto> list) {
        if (id != null && !id.isEmpty()) {
            Long idType = Long.valueOf(id);
            for (SurveySourceDto o : list) {
                if (o.getId().compareTo(idType) == 0) {
                    return o;
                }
            }
        }
        return null;
    }

    /**
     * Returns {@link OfficialityTypeDto} from id
     * 
     * @param officialityTypeDtos
     * @param id
     * @return
     */
    public static OfficialityTypeDto getOfficialityTypeDto(String id, List<OfficialityTypeDto> list) {
        if (id != null && !id.isEmpty()) {
            Long idOfficialityType = Long.valueOf(id);
            for (OfficialityTypeDto o : list) {
                if (o.getId().compareTo(idOfficialityType) == 0) {
                    return o;
                }
            }
        }
        return null;
    }

    /**
     * Returns {@link CollMethodDto} from id
     * 
     * @param officialityTypeDtos
     * @param id
     * @return
     */
    public static CollMethodDto getCollMethodDto(String id, List<CollMethodDto> list) {
        if (id != null && !id.isEmpty()) {
            id = getCollMethodKey(id);
            Long idType = Long.valueOf(id);
            for (CollMethodDto o : list) {
                if (o.getId().compareTo(idType) == 0) {
                    return o;
                }
            }
        }
        return null;
    }

    /**
     * Returns {@link CostDto} from id
     * 
     * @param officialityTypeDtos
     * @param id
     * @return
     */
    public static CostDto getCostDto(String id, List<CostDto> list) {
        if (id != null && !id.isEmpty()) {
            Long idType = Long.valueOf(id);
            for (CostDto o : list) {
                if (o.getId().compareTo(idType) == 0) {
                    return o;
                }
            }
        }
        return null;
    }

    /**
     * Returns a list of {@link CostDto} from list of identifiers
     * 
     * @param officialityTypeDtos
     * @param id
     * @return
     */
    public static List<CostDto> getCostDtos(String[] ids, List<CostDto> list) {
        List<CostDto> costDtos = new ArrayList<CostDto>();
        for (String id : ids) {
            CostDto costDto = getCostDto(id, list);
            if (costDto != null) {
                costDtos.add(costDto);
            }
        }
        return costDtos;
    }

    /**
     * Returns {@link String} with list of identifiers from a list of {@link CostDto}
     * 
     * @param list
     * @return
     */
    public static String getCostDtoListToString(Set<CostDto> set) {
        List<CostDto> list = new ArrayList<CostDto>(set);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            builder.append(i != 0 ? ",  " : "");
            builder.append(list.get(i).getIdentifier());
        }
        return builder.toString();
    }

}

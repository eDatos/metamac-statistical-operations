package org.siemac.metamac.statistical.operations.web.server.rest.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.rest.common.v1_0.domain.ListBase;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Categories;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CategorySchemes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CodeResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codelists;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptSchemes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataProviderSchemes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataProviders;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ItemResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.OrganisationSchemes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.OrganisationUnitSchemes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.OrganisationUnits;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Organisations;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.web.common.server.utils.DtoUtils;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;

public class ExternalItemUtils extends org.siemac.metamac.web.common.client.utils.ExternalItemUtils {

    //
    // COMMON METADATA
    //

    public static ExternalItemDto getConfigurationAsExternalItemDto(org.siemac.metamac.rest.common_metadata.v1_0.domain.ResourceInternal resourceInternal) {
        return getExternalItemDtoFromCommonMetadataResourceInternal(resourceInternal);
    }

    //
    // CATEGORIES
    //

    // Category Schemes

    public static ExternalItemsResult getCategorySchemesAsExternalItemsResult(CategorySchemes categorySchemes) {
        ExternalItemsResult result = getListBaseAsExternalItemsResult(categorySchemes);
        result.setExternalItemDtos(getExternalItemDtosFromResourceInternals(categorySchemes.getCategorySchemes()));
        return result;
    }

    // Categories
    @SuppressWarnings("unchecked")
    public static ExternalItemsResult getCategoriesAsExternalItemsResult(Categories categories) {
        ExternalItemsResult result = getListBaseAsExternalItemsResult(categories);
        result.setExternalItemDtos(getExternalItemDtosFromItemResourceInternals((List<ItemResourceInternal>)(List<?>)categories.getCategories()));
        return result;
    }

    //
    // CODES
    //

    // Codelist

    @SuppressWarnings("unchecked")
    public static ExternalItemsResult getCodelistsAsExternalItemsResult(Codelists codelists) {
        ExternalItemsResult result = getListBaseAsExternalItemsResult(codelists);
        result.setExternalItemDtos(getExternalItemDtosFromResourceInternals((List<ResourceInternal>)(List<?>)codelists.getCodelists()));
        return result;
    }

    // Codes

    public static ExternalItemsResult getCodesAsExternalItemsResult(Codes codes) {
        ExternalItemsResult result = getListBaseAsExternalItemsResult(codes);
        result.setExternalItemDtos(getExternalItemDtosFromCodeResourceInternals(codes.getCodes()));
        return result;
    }

    //
    // CONCEPTS
    //

    // ConceptSchemes

    public static ExternalItemsResult getConceptSchemesAsExternalItemsResult(ConceptSchemes conceptSchemes) {
        ExternalItemsResult result = getListBaseAsExternalItemsResult(conceptSchemes);
        result.setExternalItemDtos(getExternalItemDtosFromResourceInternals(conceptSchemes.getConceptSchemes()));
        return result;
    }

    // Concepts

    public static ExternalItemsResult getConceptsAsExternalItemsResult(Concepts concepts) {
        ExternalItemsResult result = getListBaseAsExternalItemsResult(concepts);
        result.setExternalItemDtos(getExternalItemDtosFromItemResourceInternals(concepts.getConcepts()));
        return result;
    }

    //
    // ORGANISATIONS
    //

    // Organisation schemes

    public static ExternalItemsResult getOrganisationSchemesAsExternalItemsResult(OrganisationSchemes organisationSchemes) {
        ExternalItemsResult result = getListBaseAsExternalItemsResult(organisationSchemes);
        result.setExternalItemDtos(getExternalItemDtosFromResourceInternals(organisationSchemes.getOrganisationSchemes()));
        return result;
    }

    // Organisations

    public static ExternalItemsResult getOrganisationsAsExternalItemsResult(Organisations organisations) {
        ExternalItemsResult result = getListBaseAsExternalItemsResult(organisations);
        result.setExternalItemDtos(getExternalItemDtosFromItemResourceInternals(organisations.getOrganisations()));
        return result;
    }

    // Organisation Unit Schemes

    public static ExternalItemsResult getOrganisationUnitSchemesAsExternalItemsResult(OrganisationUnitSchemes organisationUnitSchemes) {
        ExternalItemsResult result = getListBaseAsExternalItemsResult(organisationUnitSchemes);
        result.setExternalItemDtos(getExternalItemDtosFromResourceInternals(organisationUnitSchemes.getOrganisationUnitSchemes()));
        return result;
    }

    // Organisation units

    public static ExternalItemsResult getOrganisationUnitsAsExternalItemsResult(OrganisationUnits organisationUnits) {
        ExternalItemsResult result = getListBaseAsExternalItemsResult(organisationUnits);
        result.setExternalItemDtos(getExternalItemDtosFromItemResourceInternals(organisationUnits.getOrganisationUnits()));
        return result;
    }

    // Data Provider Schemes

    public static ExternalItemsResult getDataProviderSchemesAsExternalItemsResult(DataProviderSchemes dataProviderSchemes) {
        ExternalItemsResult result = getListBaseAsExternalItemsResult(dataProviderSchemes);
        result.setExternalItemDtos(getExternalItemDtosFromResourceInternals(dataProviderSchemes.getDataProviderSchemes()));
        return result;
    }

    // Data providers

    public static ExternalItemsResult getDataProvidersAsExternalItemsResult(DataProviders dataProviders) {
        ExternalItemsResult result = getListBaseAsExternalItemsResult(dataProviders);
        result.setExternalItemDtos(getExternalItemDtosFromItemResourceInternals(dataProviders.getDataProviders()));
        return result;
    }

    //
    // COMMON METHODS
    //

    private static ExternalItemsResult getListBaseAsExternalItemsResult(ListBase listBase) {
        ExternalItemsResult result = new ExternalItemsResult();
        if (listBase != null) {
            result.setFirstResult(listBase.getOffset() != null ? listBase.getOffset().intValue() : 0);
            result.setTotalResults(listBase.getOffset() != null ? listBase.getTotal().intValue() : 0);
        }
        return result;
    }

    private static ExternalItemDto getExternalItemDtoFromSrmResourceInternal(ResourceInternal resourceInternal) {
        ExternalItemDto externalItemDto = new ExternalItemDto();
        externalItemDto.setCode(resourceInternal.getId());
        externalItemDto.setCodeNested(resourceInternal.getNestedId());
        externalItemDto.setUri(resourceInternal.getSelfLink().getHref());
        externalItemDto.setUrn(resourceInternal.getUrn());
        externalItemDto.setUrnProvider(resourceInternal.getUrnProvider());
        externalItemDto.setType(TypeExternalArtefactsEnum.fromValue(resourceInternal.getKind()));
        externalItemDto.setTitle(DtoUtils.getInternationalStringDtoFromInternationalString(resourceInternal.getName()));
        externalItemDto.setManagementAppUrl(resourceInternal.getManagementAppLink());
        return externalItemDto;
    }

    private static ExternalItemDto getExternalItemDtoFromCommonMetadataResourceInternal(org.siemac.metamac.rest.common_metadata.v1_0.domain.ResourceInternal resourceInternal) {
        ExternalItemDto externalItemDto = new ExternalItemDto();
        externalItemDto.setCode(resourceInternal.getId());
        externalItemDto.setCodeNested(resourceInternal.getNestedId());
        externalItemDto.setUri(resourceInternal.getSelfLink().getHref());
        externalItemDto.setUrn(resourceInternal.getUrn());
        externalItemDto.setType(TypeExternalArtefactsEnum.fromValue(resourceInternal.getKind()));
        externalItemDto.setTitle(DtoUtils.getInternationalStringDtoFromInternationalString(resourceInternal.getName()));
        externalItemDto.setManagementAppUrl(resourceInternal.getManagementAppLink());
        return externalItemDto;
    }

    private static List<ExternalItemDto> getExternalItemDtosFromResourceInternals(List<ResourceInternal> resources) {
        List<ExternalItemDto> externalItemDtos = new ArrayList<ExternalItemDto>(resources.size());
        for (ResourceInternal resource : resources) {
            externalItemDtos.add(getExternalItemDtoFromSrmResourceInternal(resource));
        }
        return externalItemDtos;
    }

    private static List<ExternalItemDto> getExternalItemDtosFromItemResourceInternals(List<ItemResourceInternal> resources) {
        List<ExternalItemDto> externalItemDtos = new ArrayList<ExternalItemDto>(resources.size());
        for (ResourceInternal resource : resources) {
            externalItemDtos.add(getExternalItemDtoFromSrmResourceInternal(resource));
        }
        return externalItemDtos;
    }

    private static List<ExternalItemDto> getExternalItemDtosFromCodeResourceInternals(List<CodeResourceInternal> resources) {
        List<ExternalItemDto> externalItemDtos = new ArrayList<ExternalItemDto>(resources.size());
        for (ResourceInternal resource : resources) {
            externalItemDtos.add(getExternalItemDtoFromSrmResourceInternal(resource));
        }
        return externalItemDtos;
    }
}

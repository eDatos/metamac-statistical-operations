package org.siemac.metamac.statistical.operations.web.shared.external;

import static org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum.AGENCY;
import static org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum.AGENCY_SCHEME;
import static org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum.CODELIST;
import static org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum.DATA_CONSUMER;
import static org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum.DATA_CONSUMER_SCHEME;
import static org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum.DATA_PROVIDER;
import static org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum.DATA_PROVIDER_SCHEME;
import static org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum.ORGANISATION_UNIT;
import static org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum.ORGANISATION_UNIT_SCHEME;

import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;

public class RestWebCriteriaUtils {

    //
    // INSTACE: STATISTICAL UNIT
    //

    public static ConceptSchemeTypeEnum[] getConceptSchemeTypesForInstanceStatisticalUnit() {
        return new ConceptSchemeTypeEnum[]{ConceptSchemeTypeEnum.TRANSVERSAL, ConceptSchemeTypeEnum.GLOSSARY, ConceptSchemeTypeEnum.OPERATION, ConceptSchemeTypeEnum.MEASURE};
    }

    //
    // INSTACE: STAT CONC DEF
    //

    public static ConceptSchemeTypeEnum[] getConceptSchemeTypesForInstanceStatConcDef() {
        return new ConceptSchemeTypeEnum[]{ConceptSchemeTypeEnum.TRANSVERSAL, ConceptSchemeTypeEnum.OPERATION};
    }

    //
    // INSTACE: MEASURES
    //

    public static ConceptSchemeTypeEnum[] getConceptSchemeTypesForInstanceMeasures() {
        return new ConceptSchemeTypeEnum[]{ConceptSchemeTypeEnum.MEASURE};
    }

    //
    // COMMON CRITERIA
    //

    public static SrmExternalResourceRestCriteria buildItemSchemeWebCriteria(TypeExternalArtefactsEnum... types) {
        return buildItemSchemeWebCriteria(new SrmExternalResourceRestCriteria(), types);
    }

    public static SrmExternalResourceRestCriteria buildItemSchemeWebCriteria(SrmExternalResourceRestCriteria itemSchemeRestCriteria, TypeExternalArtefactsEnum[] types) {
        if (areOrganisationSchemeTypes(types)) {

            OrganisationSchemeRestCriteria organisationSchemeRestCriteria = new OrganisationSchemeRestCriteria();
            organisationSchemeRestCriteria.setExternalArtifactType(TypeExternalArtefactsEnum.ORGANISATION_SCHEME);
            organisationSchemeRestCriteria.setOrganisationSchemeTypes(types);
            organisationSchemeRestCriteria.setCriteria(itemSchemeRestCriteria.getCriteria());
            organisationSchemeRestCriteria.setOnlyExternallyPublished(itemSchemeRestCriteria.isOnlyExternallyPublished());
            organisationSchemeRestCriteria.setOnlyLastVersion(itemSchemeRestCriteria.isOnlyLastVersion());
            organisationSchemeRestCriteria.setUrn(itemSchemeRestCriteria.getUrn());
            organisationSchemeRestCriteria.setUrns(itemSchemeRestCriteria.getUrns());
            return organisationSchemeRestCriteria;

        } else if (isCodeListType(types[0])) {
            itemSchemeRestCriteria.setExternalArtifactType(types[0]);
        } else {
            itemSchemeRestCriteria.setExternalArtifactType(types[0]);
        }
        return itemSchemeRestCriteria;
    }

    public static SrmItemRestCriteria buildItemWebCriteria(SrmItemRestCriteria itemRestCriteria, TypeExternalArtefactsEnum[] types) {
        if (areOrganisationTypes(types)) {

            OrganisationRestCriteria organisationRestCriteria = new OrganisationRestCriteria();
            organisationRestCriteria.setExternalArtifactType(TypeExternalArtefactsEnum.ORGANISATION);
            organisationRestCriteria.setOrganisationTypes(types);
            organisationRestCriteria.setCriteria(itemRestCriteria.getCriteria());
            organisationRestCriteria.setUrn(itemRestCriteria.getUrn());
            organisationRestCriteria.setUrns(itemRestCriteria.getUrns());
            organisationRestCriteria.setItemSchemeExternallyPublished(itemRestCriteria.isItemSchemeExternallyPublished());
            organisationRestCriteria.setItemSchemeLastVersion(itemRestCriteria.isItemSchemeLastVersion());
            organisationRestCriteria.setItemSchemeUrn(itemRestCriteria.getItemSchemeUrn());
            return organisationRestCriteria;

        } else {
            itemRestCriteria.setExternalArtifactType(types[0]);
            return itemRestCriteria;
        }
    }

    private static boolean areOrganisationSchemeTypes(TypeExternalArtefactsEnum[] types) {
        for (TypeExternalArtefactsEnum type : types) {
            if (!ORGANISATION_UNIT_SCHEME.equals(type) && !AGENCY_SCHEME.equals(type) && !DATA_PROVIDER_SCHEME.equals(type) && !DATA_CONSUMER_SCHEME.equals(type)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isCodeListType(TypeExternalArtefactsEnum type) {
        return CODELIST.equals(type);
    }

    private static boolean areOrganisationTypes(TypeExternalArtefactsEnum[] types) {
        for (TypeExternalArtefactsEnum type : types) {
            if (!ORGANISATION_UNIT.equals(type) && !AGENCY.equals(type) && !DATA_PROVIDER.equals(type) && !DATA_CONSUMER.equals(type)) {
                return false;
            }
        }
        return true;
    }

    public static SrmExternalResourceRestCriteria buildSrmExternalResourceRestCriteriaFromSrmItemRestCriteria(SrmItemRestCriteria srmItemRestCriteria) {
        SrmExternalResourceRestCriteria criteria = new SrmExternalResourceRestCriteria();
        if (srmItemRestCriteria != null) {
            criteria.setCriteria(srmItemRestCriteria.getCriteria());
            criteria.setOnlyLastVersion(srmItemRestCriteria.isItemSchemeLastVersion());
            criteria.setExternalArtifactType(srmItemRestCriteria.getExternalArtifactType());
        }
        return criteria;
    }

    public static SrmItemRestCriteria buildSrmItemRestCriteriaFromMetamacWebCriteria(MetamacWebCriteria metamacWebCriteria) {
        SrmItemRestCriteria criteria = new SrmItemRestCriteria();
        if (metamacWebCriteria != null) {
            criteria.setCriteria(metamacWebCriteria.getCriteria());
        }
        return criteria;
    }
}

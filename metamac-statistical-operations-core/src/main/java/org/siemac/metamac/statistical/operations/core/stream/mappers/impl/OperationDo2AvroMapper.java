package org.siemac.metamac.statistical.operations.core.stream.mappers.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CategoryResourceInternal;
import org.siemac.metamac.statistical.operations.core.domain.Operation;
import org.siemac.metamac.statistical.operations.core.invocation.service.SrmRestInternalService;
import org.siemac.metamac.statistical.operations.core.stream.mappers.Do2AvroMapper;
import org.siemac.metamac.statistical.operations.core.stream.messages.ExternalItemAvro;
import org.siemac.metamac.statistical.operations.core.stream.messages.OperationAvro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OperationDo2AvroMapper implements Do2AvroMapper<Operation, OperationAvro> {

    @Autowired
    DatetimeDo2AvroMapper            datetimeDo2AvroMapper;

    @Autowired
    InternationalStringDo2AvroMapper internationalStringDo2AvroMapper;

    @Autowired
    ExternalItemDo2AvroMapper        externalItemDo2AvroMapper;

    @Autowired
    SurveyTypeDo2AvroMapper          surveyTypeDo2AvroMapper;

    @Autowired
    OfficialityTypeDo2AvroMapper     officialityTypeDo2AvroMapper;

    @Autowired
    ProcStatusDo2AvroMapper          procStatusDo2AvroMapper;

    @Autowired
    StatusDo2AvroMapper              statusDo2AvroMapper;

    @Autowired
    FamilyDo2AvroMapper              familyDo2AvroMapper;

    @Autowired
    CategoryRest2AvroMapper          categoryRest2AvroMapper;

    @Autowired
    private ConfigurationService     configurationService;

    @Autowired
    private SrmRestInternalService   srmRestInternalService;

    @Override
    public OperationAvro toAvro(Operation source) throws MetamacException {
        if (source == null) {
            return null;
        }

        String defaultCategoryScheme = configurationService.retrieveDefaultCategoryScheme();
        Map<String, CategoryResourceInternal> categories = srmRestInternalService.retrieveDefaultCategoriesByCategoryElementCode(defaultCategoryScheme);

        return OperationAvro.newBuilder().setCode(source.getCode()).setUrn(source.getUrn()).setIndicatorSystem(source.getIndicatorSystem())
                .setInternalInventoryDate(datetimeDo2AvroMapper.toAvro(source.getInternalInventoryDate())).setCurrentlyActive(source.getCurrentlyActive())
                .setReleaseCalendar(source.getReleaseCalendar()).setReleaseCalendarAccess(source.getReleaseCalendarAccess()).setInventoryDate(datetimeDo2AvroMapper.toAvro(source.getInventoryDate()))
                .setUpdateDate(datetimeDo2AvroMapper.toAvro(source.getUpdateDate())).setUuid(source.getUuid()).setCreatedDate(datetimeDo2AvroMapper.toAvro(source.getCreatedDate()))
                .setCreatedBy(source.getCreatedBy()).setLastUpdated(datetimeDo2AvroMapper.toAvro(source.getLastUpdated())).setLastUpdatedBy(source.getLastUpdatedBy()).setVersion(source.getVersion())
                .setCommonMetadata(externalItemDo2AvroMapper.toAvro(source.getCommonMetadata())).setTitle(internationalStringDo2AvroMapper.toAvro(source.getTitle()))
                .setAcronym(internationalStringDo2AvroMapper.toAvro(source.getAcronym())).setSubjectArea(getCategoryFromSubjectArea(categories, source.getSubjectArea()))
                .setObjective(internationalStringDo2AvroMapper.toAvro(source.getObjective())).setDescription(internationalStringDo2AvroMapper.toAvro(source.getDescription()))
                .setSurveyType(surveyTypeDo2AvroMapper.toAvro(source.getSurveyType())).setOfficialityType(officialityTypeDo2AvroMapper.toAvro(source.getOfficialityType()))
                .setProcStatus(procStatusDo2AvroMapper.toAvro(source.getProcStatus())).setStatus(statusDo2AvroMapper.toAvro(source.getStatus()))
                .setRelPolUsAc(internationalStringDo2AvroMapper.toAvro(source.getRelPolUsAc())).setRevPolicy(internationalStringDo2AvroMapper.toAvro(source.getRevPolicy()))
                .setRevPractice(internationalStringDo2AvroMapper.toAvro(source.getRevPractice())).setSpecificLegalActs(internationalStringDo2AvroMapper.toAvro(source.getSpecificLegalActs()))
                .setSpecificDataSharing(internationalStringDo2AvroMapper.toAvro(source.getSpecificDataSharing())).setComment(internationalStringDo2AvroMapper.toAvro(source.getComment()))
                .setNotes(internationalStringDo2AvroMapper.toAvro(source.getNotes())).setFamilies(familyDo2AvroMapper.toAvros(source.getFamilies()))
                .setSecondarySubjectAreas(getCategoriesFromSecondarySubjectAreas(categories, source.getSecondarySubjectAreas())).setProducer(externalItemDo2AvroMapper.toAvros(source.getProducer()))
                .setRegionalResponsible(externalItemDo2AvroMapper.toAvros(source.getRegionalResponsible())).setRegionalContributor(externalItemDo2AvroMapper.toAvros(source.getRegionalContributor()))
                .setPublisher(externalItemDo2AvroMapper.toAvros(source.getPublisher())).setUpdateFrequency(externalItemDo2AvroMapper.toAvros(source.getUpdateFrequency())).build();
    }

    private ExternalItemAvro getCategoryFromSubjectArea(Map<String, CategoryResourceInternal> categoriesByCategoryElement, ExternalItem subjectArea) throws MetamacException {
        if (subjectArea != null) {
            CategoryResourceInternal category = categoriesByCategoryElement.get(subjectArea.getCode());
            return categoryRest2AvroMapper.toAvro(category);
        }

        return null;
    }

    private List<ExternalItemAvro> getCategoriesFromSecondarySubjectAreas(Map<String, CategoryResourceInternal> categoriesByCategoryElement, Set<ExternalItem> secondarySubjectAreas)
            throws MetamacException {

        List<CategoryResourceInternal> categories = new ArrayList<CategoryResourceInternal>();
        for (ExternalItem categoryElement : secondarySubjectAreas) {
            if (categoryElement != null) {
                categories.add(categoriesByCategoryElement.get(categoryElement.getCode()));
            }
        }
        return categoryRest2AvroMapper.toAvros(categories);

    }
}

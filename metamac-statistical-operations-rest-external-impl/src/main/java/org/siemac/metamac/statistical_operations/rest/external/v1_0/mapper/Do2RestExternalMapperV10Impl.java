package org.siemac.metamac.statistical_operations.rest.external.v1_0.mapper;

import static org.siemac.metamac.core.common.util.rest.RequestUtil.containsField;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.Response.Status;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.common.v1_0.domain.ChildLinks;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.Item;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configuration;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.rest.search.criteria.mapper.SculptorCriteria2RestCriteria;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.ClassSystems;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.CollMethods;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Costs;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.DataSharings;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Families;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Family;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.FreqColls;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.GeographicGranularities;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.InformationSuppliers;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Instance;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.InstanceTypes;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Instances;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.LegalActs;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Measures;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.OfficialityTypes;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Operation;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Operations;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Producers;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Publishers;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Contributors;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Responsibles;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.ResourceWithSubjectArea;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.SecondarySubjectAreas;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.StatConcDefs;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.StatisticalOperationSources;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.StatisticalOperationTypes;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.StatisticalUnits;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.TemporalGranularities;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.UpdateFrequencies;
import org.siemac.metamac.rest.structural_resources.v1_0.domain.CategoryResource;
import org.siemac.metamac.rest.utils.RestUtils;
import org.siemac.metamac.srm.rest.common.SrmRestConstants;
import org.siemac.metamac.statistical.operations.core.domain.CollMethod;
import org.siemac.metamac.statistical.operations.core.domain.Cost;
import org.siemac.metamac.statistical.operations.core.domain.InstanceType;
import org.siemac.metamac.statistical.operations.core.domain.OfficialityType;
import org.siemac.metamac.statistical.operations.core.domain.SurveySource;
import org.siemac.metamac.statistical.operations.core.domain.SurveyType;
import org.siemac.metamac.statistical.operations.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.operations.core.enume.domain.StatusEnum;
import org.siemac.metamac.statistical_operations.rest.common.StatisticalOperationsRestConstants;
import org.siemac.metamac.statistical_operations.rest.external.exception.RestServiceExceptionType;
import org.siemac.metamac.statistical_operations.rest.external.invocation.CommonMetadataRestExternalFacade;
import org.siemac.metamac.statistical_operations.rest.external.invocation.SrmRestExternalFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Do2RestExternalMapperV10Impl implements Do2RestExternalMapperV10 {

    @Autowired
    private ConfigurationService             configurationService;

    @Autowired
    private CommonMetadataRestExternalFacade commonMetadataRestExternalFacade;

    @Autowired
    private SrmRestExternalFacade            srmRestExternalFacade;

    private String                           statisticalOperationsApiExternalEndpointV10;
    private String                           srmApiExternalEndpoint;

    @PostConstruct
    public void init() throws Exception {
        initEndpoints();
    }

    @Override
    public Operation toOperation(org.siemac.metamac.statistical.operations.core.domain.Operation source) throws MetamacException {
        if (source == null) {
            return null;
        }
        Operation target = new Operation();
        target.setId(source.getCode());
        target.setUrn(source.getUrn());
        target.setKind(StatisticalOperationsRestConstants.KIND_OPERATION);
        target.setSelfLink(toOperationSelfLink(source));
        target.setName(toInternationalString(source.getTitle()));
        target.setAcronym(toInternationalString(source.getAcronym()));
        target.setStatisticPlanCode(source.getStatisticPlanCode());
        setSubjectAreas(source, target);
        target.setObjective(toInternationalString(source.getObjective()));
        target.setDescription(toInternationalString(source.getDescription()));
        target.setStatisticalOperationType(toItem(source.getSurveyType()));
        target.setOfficialityType(toItem(source.getOfficialityType()));
        target.setIndicatorSystem(source.getIndicatorSystem());
        target.setProducers(toProducers(source.getProducer()));
        target.setResponsibles(toResponsibles(source.getResponsible()));
        target.setContributors(toContributors(source.getContributor()));
        target.setCurrentlyActive(source.getCurrentlyActive());
        target.setStatus(toStatus(source.getStatus()));
        target.setPublishers(toPublishers(source.getPublisher()));
        target.setRelPolUsAc(toInternationalString(source.getRelPolUsAc()));
        target.setReleaseCalendar(source.getReleaseCalendar());
        target.setDiffusionAndPublicationVisible(source.getDiffusionPublicationVisible());
        target.setReleaseCalendarAccess(source.getReleaseCalendarAccess());
        target.setUpdateFrequencies(toUpdateFrequencies(source.getUpdateFrequency()));
        target.setCurrentInstance(toResource(getInstanceInProcStatus(source.getInstances(), ProcStatusEnum.PUBLISH_EXTERNALLY)));
        target.setInventoryDate(toDate(source.getInventoryDate()));
        target.setRevPolicy(toInternationalString(source.getRevPolicy()));
        target.setRevPractice(toInternationalString(source.getRevPractice()));
        commonMetadataToOperation(source.getCommonMetadata(), target);
        target.setLegalActs(toOperationLegalActs(source.getSpecificLegalActs(), null, target.getLegalActs()));
        target.setDataSharings(toOperationDataSharings(source.getSpecificDataSharing(), null, target.getDataSharings()));
        target.setNotes(toInternationalString(source.getNotes()));
        target.setParentLink(toOperationParentLink());
        target.setChildLinks(toOperationChildLinks(source));
        target.setGenderPerspective(toInternationalString(source.getGenderPerspective()));
        return target;
    }

    private void setSubjectAreas(org.siemac.metamac.statistical.operations.core.domain.Operation source, Operation target) throws MetamacException {
        Map<String, CategoryResource> categoriesResourceByCategoryElementCode = srmRestExternalFacade
                .retrieveDefaultCategoriesByCategoryElementCode(configurationService.retrieveDefaultCategoryScheme());

        if (categoriesResourceByCategoryElementCode.isEmpty()) {
            return;
        }
        if (source.getSubjectArea() != null) {
            target.setSubjectArea(srmResourceToResource(categoriesResourceByCategoryElementCode.get(source.getSubjectArea().getCode())));

        }
        List<Resource> categories = new ArrayList<Resource>();
        for (ExternalItem categoryElement : source.getSecondarySubjectAreas()) {
            Resource categoryResource = srmResourceToResource(categoriesResourceByCategoryElementCode.get(categoryElement.getCode()));
            if (categoryResource != null) {
                categories.add(categoryResource);
            }
        }
        if (!categories.isEmpty()) {
            target.setSecondarySubjectAreas(toSecondarySubjectAreas(categories));
        }
    }

    private Resource getCategoryByCategoryElement(ExternalItem categoryElement) throws MetamacException {

        CategoryResource categoryResource = srmRestExternalFacade.retrieveCategoryByCategoryElement(configurationService.retrieveDefaultCategoryScheme(), categoryElement.getCode());
        if (categoryResource != null) {
            return srmResourceToResource(categoryResource);
        }
        return null;

    }

    @Override
    public Operations toOperations(PagedResult<org.siemac.metamac.statistical.operations.core.domain.Operation> sourcesPagedResult, String query, String orderBy, Integer limit,
            Set<String> parsedFields) throws MetamacException {

        Operations targets = new Operations();
        targets.setKind(StatisticalOperationsRestConstants.KIND_OPERATIONS);

        // Pagination
        String baseLink = toOperationsLink();
        SculptorCriteria2RestCriteria.toPagedResult(sourcesPagedResult, targets, query, orderBy, limit, baseLink);

        Map<String, CategoryResource> categoryResourceByDefaultCategoryScheme = srmRestExternalFacade
                .retrieveDefaultCategoriesByCategoryElementCode(configurationService.retrieveDefaultCategoryScheme());

        // Values
        for (org.siemac.metamac.statistical.operations.core.domain.Operation source : sourcesPagedResult.getValues()) {
            ResourceWithSubjectArea target = toResource(source, parsedFields, categoryResourceByDefaultCategoryScheme);
            targets.getOperations().add(target);
        }
        return targets;
    }

    @Override
    public Operations toOperationsByFamily(org.siemac.metamac.statistical.operations.core.domain.Family family,
            PagedResult<org.siemac.metamac.statistical.operations.core.domain.Operation> sourcesPagedResult, String query, String orderBy, Integer limit) throws MetamacException {

        Operations targets = new Operations();
        targets.setKind(StatisticalOperationsRestConstants.KIND_OPERATIONS);

        // Pagination
        String baseLink = toOperationsByFamilyLink(family);
        SculptorCriteria2RestCriteria.toPagedResult(sourcesPagedResult, targets, query, orderBy, limit, baseLink);

        // Values
        for (org.siemac.metamac.statistical.operations.core.domain.Operation source : sourcesPagedResult.getValues()) {
            ResourceWithSubjectArea target = toResource(source);
            targets.getOperations().add(target);
        }
        return targets;
    }

    @Override
    public Family toFamily(org.siemac.metamac.statistical.operations.core.domain.Family source) {
        if (source == null) {
            return null;
        }
        Family target = new Family();
        target.setId(source.getCode());
        target.setUrn(source.getUrn());
        target.setKind(StatisticalOperationsRestConstants.KIND_FAMILY);
        target.setSelfLink(toFamilySelfLink(source));
        target.setName(toInternationalString(source.getTitle()));
        target.setAcronym(toInternationalString(source.getAcronym()));
        target.setDescription(toInternationalString(source.getDescription()));
        target.setInventoryDate(toDate(source.getInventoryDate()));
        target.setParentLink(toFamilyParentLink());
        target.setChildLinks(toFamilyChildLinks(source));
        return target;
    }

    @Override
    public Families toFamilies(PagedResult<org.siemac.metamac.statistical.operations.core.domain.Family> sourcesPagedResult, String query, String orderBy, Integer limit) {

        Families targets = new Families();
        targets.setKind(StatisticalOperationsRestConstants.KIND_FAMILIES);

        // Pagination
        String baseLink = toFamiliesLink();
        SculptorCriteria2RestCriteria.toPagedResult(sourcesPagedResult, targets, query, orderBy, limit, baseLink);

        // Values
        for (org.siemac.metamac.statistical.operations.core.domain.Family source : sourcesPagedResult.getValues()) {
            Resource target = toResource(source);
            targets.getFamilies().add(target);
        }
        return targets;
    }

    @Override
    public Families toFamiliesByOperation(List<org.siemac.metamac.statistical.operations.core.domain.Family> sources) {

        Families targets = new Families();
        targets.setKind(StatisticalOperationsRestConstants.KIND_FAMILIES);

        if (sources == null) {
            targets.setTotal(BigInteger.ZERO);
        } else {
            for (org.siemac.metamac.statistical.operations.core.domain.Family source : sources) {
                Resource target = toResource(source);
                targets.getFamilies().add(target);
            }
            targets.setTotal(BigInteger.valueOf(sources.size()));
        }

        return targets;
    }

    @Override
    public Instance toInstance(org.siemac.metamac.statistical.operations.core.domain.Instance source) throws MetamacException {
        if (source == null) {
            return null;
        }
        Instance target = new Instance();
        target.setId(source.getCode());
        target.setUrn(source.getUrn());
        target.setKind(StatisticalOperationsRestConstants.KIND_INSTANCE);
        target.setSelfLink(toInstanceSelfLink(source));
        target.setName(toInternationalString(source.getTitle()));
        target.setAcronym(toInternationalString(source.getAcronym()));
        target.setStatisticalOperation(toResource(source.getOperation(), null, null));
        target.setPredecessor(toResource(getInstanceInOrder(source.getOperation().getInstances(), source.getOrder() - 1)));
        target.setSuccessor(toResource(getInstanceInOrder(source.getOperation().getInstances(), source.getOrder() + 1)));
        target.setDataDescription(toInternationalString(source.getDataDescription()));
        target.setStatisticalPopulation(toInternationalString(source.getStatisticalPopulation()));
        target.setStatisticalUnits(toStatisticalUnits(source.getStatisticalUnit()));
        target.setGeographicGranularity(toGeographicGranularities(source.getGeographicGranularity()));
        target.setGeographicComparability(toInternationalString(source.getGeographicComparability()));
        target.setTemporalGranularity(toTemporalGranularities(source.getTemporalGranularity()));
        target.setTemporalComparability(toInternationalString(source.getTemporalComparability()));
        target.setBasePeriod(source.getBasePeriod());
        target.setMeasures(toMeasures(source.getUnitMeasure()));
        target.setStatConcDefsDescription(toInternationalString(source.getStatConcDef()));
        target.setStatConcDefs(toStatConcDefs(source.getStatConcDefList()));
        target.setClassSystemsDescription(toInternationalString(source.getClassSystem()));
        target.setClassSystems(toClassSystems(source.getClassSystemList()));
        target.setDocMethod(toInternationalString(source.getDocMethod()));
        target.setStatisticalOperationSource(toItem(source.getSurveySource()));
        target.setCollMethod(toItem(source.getCollMethod()));
        target.setInformationSuppliers(toInformationSuppliers(source.getInformationSuppliers()));
        target.setFreqColls(toFreqColls(source.getFreqColl()));
        target.setDataValidation(toInternationalString(source.getDataValidation()));
        target.setDataCompilation(toInternationalString(source.getDataCompilation()));
        target.setAdjustment(toInternationalString(source.getAdjustment()));
        target.setSeasonalAdjustment(toInternationalString(source.getSeasonalAdjustment()));
        target.setInventoryDate(toDate(source.getInventoryDate()));
        target.setQualityDoc(toInternationalString(source.getQualityDoc()));
        target.setQualityAssure(toInternationalString(source.getQualityAssure()));
        target.setQualityAssmnt(toInternationalString(source.getQualityAssmnt()));
        target.setUserNeeds(toInternationalString(source.getUserNeeds()));
        target.setUserSat(toInternationalString(source.getUserSat()));
        target.setCompleteness(toInternationalString(source.getCompleteness()));
        target.setTimeliness(toInternationalString(source.getTimeliness()));
        target.setPunctuality(toInternationalString(source.getPunctuality()));
        target.setAccuracyOverall(toInternationalString(source.getAccuracyOverall()));
        target.setSamplingErr(toInternationalString(source.getSamplingErr()));
        target.setNonsamplingErr(toInternationalString(source.getNonsamplingErr()));
        target.setCoverageErr(toInternationalString(source.getCoverageErr()));
        target.setMeasurementErr(toInternationalString(source.getMeasurementErr()));
        target.setNonResponseErr(toInternationalString(source.getNonResponseErr()));
        target.setProcessingErr(toInternationalString(source.getProcessingErr()));
        target.setModelErr(toInternationalString(source.getModelErr()));
        target.setCoherXDom(toInternationalString(source.getCoherXDomain()));
        target.setCoherSubanualAnual(toInternationalString(source.getCoherSubanualAnual()));
        target.setCoherNationalAccounts(toInternationalString(source.getCoherNationalAccounts()));
        target.setCoherInternal(toInternationalString(source.getCoherInternal()));
        target.setComment(toInternationalString(source.getComment()));
        target.setParentLink(toInstanceParentLink(source));
        target.setChildLinks(toInstanceChildLinks(source));
        return target;
    }

    @Override
    public Instances toInstances(org.siemac.metamac.statistical.operations.core.domain.Operation operation,
            PagedResult<org.siemac.metamac.statistical.operations.core.domain.Instance> sourcesPagedResult, String query, String orderBy, Integer limit) {

        Instances targets = new Instances();
        targets.setKind(StatisticalOperationsRestConstants.KIND_INSTANCES);

        // Pagination
        String baseLink = toInstancesLink(operation);
        SculptorCriteria2RestCriteria.toPagedResult(sourcesPagedResult, targets, query, orderBy, limit, baseLink);

        // Values
        for (org.siemac.metamac.statistical.operations.core.domain.Instance source : sourcesPagedResult.getValues()) {
            Resource target = toResource(source);
            targets.getInstances().add(target);
        }
        return targets;
    }

    @Override
    public StatisticalOperationTypes toStatisticalOperationTypes(List<SurveyType> sources) {
        StatisticalOperationTypes targets = new StatisticalOperationTypes();
        targets.setKind(StatisticalOperationsRestConstants.KIND_STATISTICAL_OPERATION_TYPES);

        if (sources == null) {
            targets.setTotal(BigInteger.ZERO);
        } else {
            for (org.siemac.metamac.statistical.operations.core.domain.SurveyType source : sources) {
                Item target = toItem(source);
                targets.getStatisticalOperationTypes().add(target);
            }
            targets.setTotal(BigInteger.valueOf(sources.size()));
        }

        return targets;
    }

    @Override
    public OfficialityTypes toOfficialityTypes(List<OfficialityType> sources) {
        OfficialityTypes targets = new OfficialityTypes();
        targets.setKind(StatisticalOperationsRestConstants.KIND_OFFICIALITY_TYPES);

        if (sources == null) {
            targets.setTotal(BigInteger.ZERO);
        } else {
            for (org.siemac.metamac.statistical.operations.core.domain.OfficialityType source : sources) {
                Item target = toItem(source);
                targets.getOfficialityTypes().add(target);
            }
            targets.setTotal(BigInteger.valueOf(sources.size()));
        }

        return targets;
    }

    @Override
    public InstanceTypes toInstanceTypes(List<InstanceType> sources) {
        InstanceTypes targets = new InstanceTypes();
        targets.setKind(StatisticalOperationsRestConstants.KIND_INSTANCE_TYPES);

        if (sources == null) {
            targets.setTotal(BigInteger.ZERO);
        } else {
            for (org.siemac.metamac.statistical.operations.core.domain.InstanceType source : sources) {
                Item target = toItem(source);
                targets.getInstanceTypes().add(target);
            }
            targets.setTotal(BigInteger.valueOf(sources.size()));
        }

        return targets;
    }

    @Override
    public StatisticalOperationSources toStatisticalOperationSources(List<SurveySource> sources) {
        StatisticalOperationSources targets = new StatisticalOperationSources();
        targets.setKind(StatisticalOperationsRestConstants.KIND_STATISTICAL_OPERATION_SOURCES);

        if (sources == null) {
            targets.setTotal(BigInteger.ZERO);
        } else {
            for (org.siemac.metamac.statistical.operations.core.domain.SurveySource source : sources) {
                Item target = toItem(source);
                targets.getStatisticalOperationSources().add(target);
            }
            targets.setTotal(BigInteger.valueOf(sources.size()));
        }

        return targets;
    }

    @Override
    public CollMethods toCollMethods(List<CollMethod> sources) {
        CollMethods targets = new CollMethods();
        targets.setKind(StatisticalOperationsRestConstants.KIND_COLL_METHODS);

        if (sources == null) {
            targets.setTotal(BigInteger.ZERO);
        } else {
            for (org.siemac.metamac.statistical.operations.core.domain.CollMethod source : sources) {
                Item target = toItem(source);
                targets.getCollMethods().add(target);
            }
            targets.setTotal(BigInteger.valueOf(sources.size()));
        }

        return targets;
    }

    @Override
    public Costs toCosts(List<Cost> sources) {
        return toCosts((Collection<Cost>) sources);
    }

    private Costs toCosts(Collection<Cost> sources) {
        Costs targets = new Costs();
        targets.setKind(StatisticalOperationsRestConstants.KIND_COSTS);

        if (sources == null) {
            targets.setTotal(BigInteger.ZERO);
        } else {
            for (org.siemac.metamac.statistical.operations.core.domain.Cost source : sources) {
                Item target = toItem(source);
                targets.getCosts().add(target);
            }
            targets.setTotal(BigInteger.valueOf(sources.size()));
        }

        return targets;
    }

    private void commonMetadataToOperation(ExternalItem commonMetadata, Operation target) {
        if (commonMetadata == null) {
            return;
        }
        // Calls to CommonMetadata API
        Configuration configuration = commonMetadataRestExternalFacade.retrieveConfigurationById(commonMetadata.getCode());

        // Transform
        target.setContact(configuration.getContact());
        target.setLegalActs(toOperationLegalActs(null, configuration.getLegalActs(), target.getLegalActs()));
        target.setDataSharings(toOperationDataSharings(null, configuration.getDataSharing(), target.getDataSharings()));
        target.setConfidentialityPolicy(configuration.getConfPolicy());
        target.setConfidentialityDataTreatment(configuration.getConfDataTreatment());
    }

    private LegalActs toOperationLegalActs(org.siemac.metamac.core.common.ent.domain.InternationalString source1, InternationalString source2, LegalActs target) {
        if (source1 == null && source2 == null) {
            return target; // it can have already other internationalString
        }
        if (target == null) {
            target = new LegalActs();
            target.setTotal(BigInteger.ZERO);
        }
        if (source1 != null) {
            target.getLegalActs().add(toInternationalString(source1));
            target.setTotal(target.getTotal().add(BigInteger.ONE));
        }
        if (source2 != null) {
            target.getLegalActs().add(source2);
            target.setTotal(target.getTotal().add(BigInteger.ONE));
        }
        return target;
    }

    private DataSharings toOperationDataSharings(org.siemac.metamac.core.common.ent.domain.InternationalString source1, InternationalString source2, DataSharings target) {
        if (source1 == null && source2 == null) {
            return target; // it can have already other internationalString
        }
        if (target == null) {
            target = new DataSharings();
            target.setTotal(BigInteger.ZERO);
        }
        if (source1 != null) {
            target.getDataSharings().add(toInternationalString(source1));
            target.setTotal(target.getTotal().add(BigInteger.ONE));
        }
        if (source2 != null) {
            target.getDataSharings().add(source2);
            target.setTotal(target.getTotal().add(BigInteger.ONE));
        }
        return target;
    }

    private ResourceWithSubjectArea toResource(org.siemac.metamac.statistical.operations.core.domain.Operation source) throws MetamacException {
        return toResource(source, null, null);
    }

    private ResourceWithSubjectArea toResource(org.siemac.metamac.statistical.operations.core.domain.Operation source, Set<String> parsedFields, Map<String, CategoryResource> categoryResourcesCache)
            throws MetamacException {
        if (source == null) {
            return null;
        }
        ResourceWithSubjectArea target = new ResourceWithSubjectArea();
        target.setId(source.getCode());
        target.setUrn(source.getUrn());
        target.setKind(StatisticalOperationsRestConstants.KIND_OPERATION);
        target.setSelfLink(toOperationSelfLink(source));
        target.setName(toInternationalString(source.getTitle()));
        target.setSubjectArea(getSubjectAreaFromCache(source.getSubjectArea(), parsedFields, categoryResourcesCache));
        target.setDiffusionAndPublicationVisible(getDiffusionAndPublicationVisible(source, parsedFields));

        return target;
    }

    private Resource getSubjectAreaFromCache(ExternalItem subjectArea, Set<String> parsedFields, Map<String, CategoryResource> categoryResourcesCache) throws MetamacException {
        boolean includeSubjectArea = containsField(parsedFields, StatisticalOperationsRestConstants.FIELD_INCLUDE_SUBJECT_AREA);
        Resource category = null;
        if (includeSubjectArea) {
            if (categoryResourcesCache != null && !categoryResourcesCache.isEmpty()) {
                category = srmResourceToResource(categoryResourcesCache.get(subjectArea.getCode()));
            }

            if (category == null) {
                category = getCategoryByCategoryElement(subjectArea);
            }
        }
        return category;
    }

    private Boolean getDiffusionAndPublicationVisible(org.siemac.metamac.statistical.operations.core.domain.Operation source, Set<String> parsedFields) throws MetamacException {
        boolean includeSubjectArea = containsField(parsedFields, StatisticalOperationsRestConstants.DIFUSION_PUBLICATION_VISIBLE);
        Boolean diffusionAndPublicationVisible = null;
        if (includeSubjectArea) {
            return source.getDiffusionPublicationVisible();
        }
        return diffusionAndPublicationVisible;
    }

    private Resource toResource(org.siemac.metamac.statistical.operations.core.domain.Family source) {
        if (source == null) {
            return null;
        }
        Resource target = new Resource();
        target.setId(source.getCode());
        target.setUrn(source.getUrn());
        target.setKind(StatisticalOperationsRestConstants.KIND_FAMILY);
        target.setSelfLink(toFamilySelfLink(source));
        target.setName(toInternationalString(source.getTitle()));
        return target;
    }

    private Resource toResource(org.siemac.metamac.statistical.operations.core.domain.Instance source) {
        if (source == null) {
            return null;
        }
        Resource target = new Resource();
        target.setId(source.getCode());
        target.setUrn(source.getUrn());
        target.setKind(StatisticalOperationsRestConstants.KIND_INSTANCE);
        target.setSelfLink(toInstanceSelfLink(source));
        target.setName(toInternationalString(source.getTitle()));
        return target;
    }

    private Item toItem(org.siemac.metamac.statistical.operations.core.domain.SurveyType source) {
        if (source == null) {
            return null;
        }
        Item target = new Item();
        target.setId(source.getIdentifier());
        target.setName(toInternationalString(source.getDescription()));
        return target;
    }

    private Item toItem(org.siemac.metamac.statistical.operations.core.domain.OfficialityType source) {
        if (source == null) {
            return null;
        }
        Item target = new Item();
        target.setId(source.getIdentifier());
        target.setName(toInternationalString(source.getDescription()));
        return target;
    }

    private Item toItem(org.siemac.metamac.statistical.operations.core.domain.SurveySource source) {
        if (source == null) {
            return null;
        }
        Item target = new Item();
        target.setId(source.getIdentifier());
        target.setName(toInternationalString(source.getDescription()));
        return target;
    }

    private Item toItem(org.siemac.metamac.statistical.operations.core.domain.InstanceType source) {
        if (source == null) {
            return null;
        }
        Item target = new Item();
        target.setId(source.getIdentifier());
        target.setName(toInternationalString(source.getDescription()));
        return target;
    }

    private Item toItem(org.siemac.metamac.statistical.operations.core.domain.CollMethod source) {
        if (source == null) {
            return null;
        }
        Item target = new Item();
        target.setId(source.getIdentifier());
        target.setName(toInternationalString(source.getDescription()));
        return target;
    }

    private Item toItem(org.siemac.metamac.statistical.operations.core.domain.Cost source) {
        if (source == null) {
            return null;
        }
        Item target = new Item();
        target.setId(source.getIdentifier());
        target.setName(toInternationalString(source.getDescription()));
        return target;
    }

    private void toResourcesExternalItems(Set<ExternalItem> sources, List<Resource> targets, String apiExternalItem) {
        if (sources == null) {
            return;
        }
        for (ExternalItem source : sources) {
            Resource target = toResourceExternalItem(source, apiExternalItem);
            targets.add(target);
        }
    }

    private void toResourcesExternalItemsSrm(Set<ExternalItem> sources, List<Resource> targets) {
        toResourcesExternalItems(sources, targets, srmApiExternalEndpoint);
    }

    private Resource toResourceExternalItemSrm(ExternalItem source) {
        if (source == null) {
            return null;
        }
        return toResourceExternalItem(source, srmApiExternalEndpoint);
    }

    private Resource toResourceExternalItem(ExternalItem source, String apiExternalItem) {
        if (source == null) {
            return null;
        }
        Resource target = new Resource();
        target.setId(source.getCode());
        target.setNestedId(source.getCodeNested());
        target.setUrn(source.getUrn());
        target.setKind(source.getType().getValue());
        target.setSelfLink(toResourceLink(target.getKind(), RestUtils.createLink(apiExternalItem, source.getUri())));
        target.setName(toInternationalString(source.getTitle()));
        return target;
    }

    private ResourceLink toOperationSelfLink(org.siemac.metamac.statistical.operations.core.domain.Operation operation) {
        return toResourceLink(StatisticalOperationsRestConstants.KIND_OPERATION, toOperationLink(operation));
    }

    private ResourceLink toOperationParentLink() {
        return toResourceLink(StatisticalOperationsRestConstants.KIND_OPERATIONS, toOperationsLink());
    }

    private ChildLinks toOperationChildLinks(org.siemac.metamac.statistical.operations.core.domain.Operation operation) {
        ChildLinks targets = new ChildLinks();

        targets.getChildLinks().add(toResourceLink(StatisticalOperationsRestConstants.KIND_INSTANCES, toInstancesLink(operation)));
        targets.getChildLinks().add(toResourceLink(StatisticalOperationsRestConstants.KIND_FAMILIES, toFamiliesByOperationLink(operation)));

        targets.setTotal(BigInteger.valueOf(targets.getChildLinks().size()));
        return targets;
    }

    private ResourceLink toFamilySelfLink(org.siemac.metamac.statistical.operations.core.domain.Family family) {
        return toResourceLink(StatisticalOperationsRestConstants.KIND_FAMILY, toFamilyLink(family));
    }

    private ResourceLink toFamilyParentLink() {
        return toResourceLink(StatisticalOperationsRestConstants.KIND_FAMILIES, toFamiliesLink());
    }

    private ChildLinks toFamilyChildLinks(org.siemac.metamac.statistical.operations.core.domain.Family family) {
        ChildLinks targets = new ChildLinks();

        // Operations of family
        targets.getChildLinks().add(toResourceLink(StatisticalOperationsRestConstants.KIND_OPERATIONS, toOperationsByFamilyLink(family)));

        targets.setTotal(BigInteger.valueOf(targets.getChildLinks().size()));
        return targets;
    }

    private ResourceLink toInstanceSelfLink(org.siemac.metamac.statistical.operations.core.domain.Instance instance) {
        return toResourceLink(StatisticalOperationsRestConstants.KIND_INSTANCE, toInstanceLink(instance));
    }

    private ResourceLink toInstanceParentLink(org.siemac.metamac.statistical.operations.core.domain.Instance instance) {
        return toResourceLink(StatisticalOperationsRestConstants.KIND_OPERATION, toOperationLink(instance.getOperation()));
    }

    private ChildLinks toInstanceChildLinks(org.siemac.metamac.statistical.operations.core.domain.Instance instance) {
        // No children
        return null;
    }

    private InternationalString toInternationalString(org.siemac.metamac.core.common.ent.domain.InternationalString sources) {
        if (sources == null) {
            return null;
        }
        InternationalString targets = new InternationalString();
        for (org.siemac.metamac.core.common.ent.domain.LocalisedString source : sources.getTexts()) {
            LocalisedString target = new LocalisedString();
            target.setValue(source.getLabel());
            target.setLang(source.getLocale());
            targets.getTexts().add(target);
        }
        return targets;
    }

    private Date toDate(DateTime source) {
        if (source == null) {
            return null;
        }
        return source.toDate();
    }

    // API/operations
    private String toOperationsLink() {
        return RestUtils.createLink(statisticalOperationsApiExternalEndpointV10, StatisticalOperationsRestConstants.LINK_SUBPATH_OPERATIONS);
    }

    // API/operations/OPERATION_ID
    private String toOperationLink(String operationCode) {
        String linkOperations = toOperationsLink();
        return RestUtils.createLink(linkOperations, operationCode);
    }

    private String toOperationLink(org.siemac.metamac.statistical.operations.core.domain.Operation operation) {
        return toOperationLink(operation.getCode());
    }

    // API/operations/OPERATION_ID/instances
    private String toInstancesLink(org.siemac.metamac.statistical.operations.core.domain.Operation operation) {
        String linkOperation = null;
        if (operation != null) {
            linkOperation = toOperationLink(operation);
        } else {
            linkOperation = toOperationLink(StatisticalOperationsRestConstants.WILDCARD_ALL);
        }
        return RestUtils.createLink(linkOperation, StatisticalOperationsRestConstants.LINK_SUBPATH_INSTANCES);
    }

    // API/operations/OPERATION_ID/instances/INSTANCE_ID
    private String toInstanceLink(org.siemac.metamac.statistical.operations.core.domain.Instance instance) {
        String linkOperation = toInstancesLink(instance.getOperation());
        return RestUtils.createLink(linkOperation, instance.getCode());
    }

    // API/families
    private String toFamiliesLink() {
        return RestUtils.createLink(statisticalOperationsApiExternalEndpointV10, StatisticalOperationsRestConstants.LINK_SUBPATH_FAMILIES);
    }

    // API/families/family
    private String toFamilyLink(org.siemac.metamac.statistical.operations.core.domain.Family family) {
        String linkFamilies = toFamiliesLink();
        return RestUtils.createLink(linkFamilies, family.getCode());
    }

    // API/operations/OPERATION_ID/families
    private String toFamiliesByOperationLink(org.siemac.metamac.statistical.operations.core.domain.Operation operation) {
        String linkFamily = toOperationLink(operation);
        return RestUtils.createLink(linkFamily, StatisticalOperationsRestConstants.LINK_SUBPATH_FAMILIES);
    }

    // API/families/FAMILY_ID/operations
    private String toOperationsByFamilyLink(org.siemac.metamac.statistical.operations.core.domain.Family family) {
        String linkFamily = toFamilyLink(family);
        return RestUtils.createLink(linkFamily, StatisticalOperationsRestConstants.LINK_SUBPATH_OPERATIONS);
    }

    private org.siemac.metamac.statistical.operations.core.domain.Instance getInstanceInProcStatus(List<org.siemac.metamac.statistical.operations.core.domain.Instance> instances,
            ProcStatusEnum procStatus) {

        for (org.siemac.metamac.statistical.operations.core.domain.Instance instance : instances) {
            if (procStatus.equals(instance.getProcStatus())) {
                return instance;
            }
        }
        return null;
    }

    private org.siemac.metamac.statistical.operations.core.domain.Instance getInstanceInOrder(List<org.siemac.metamac.statistical.operations.core.domain.Instance> instances, Integer order) {

        for (org.siemac.metamac.statistical.operations.core.domain.Instance instance : instances) {
            if (order.equals(instance.getOrder())) {
                if (ProcStatusEnum.PUBLISH_EXTERNALLY.equals(instance.getProcStatus())) {
                    return instance;
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    private org.siemac.metamac.rest.statistical_operations.v1_0.domain.Status toStatus(StatusEnum source) {
        if (source == null) {
            return null;
        }
        switch (source) {
            case PRE_PLANNING:
                return org.siemac.metamac.rest.statistical_operations.v1_0.domain.Status.PRE_PLANNING;
            case PLANNING:
                return org.siemac.metamac.rest.statistical_operations.v1_0.domain.Status.PLANNING;
            case DESIGN:
                return org.siemac.metamac.rest.statistical_operations.v1_0.domain.Status.DESIGN;
            case PRODUCTION:
                return org.siemac.metamac.rest.statistical_operations.v1_0.domain.Status.PRODUCTION;
            case OUT_OF_PRINT:
                return org.siemac.metamac.rest.statistical_operations.v1_0.domain.Status.OUT_OF_PRINT;
            default:
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
                throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
    }

    private SecondarySubjectAreas toSecondarySubjectAreas(List<Resource> categories) {
        if (categories == null || categories.size() == 0) {
            return null;
        }
        SecondarySubjectAreas targets = new SecondarySubjectAreas();
        targets.getSecondarySubjectAreas().addAll(categories);
        targets.setKind(SrmRestConstants.KIND_CATEGORIES);
        targets.setTotal(BigInteger.valueOf(targets.getSecondarySubjectAreas().size()));
        return targets;
    }

    private Producers toProducers(Set<ExternalItem> sources) {
        if (sources == null || sources.size() == 0) {
            return null;
        }
        Producers targets = new Producers();
        toResourcesExternalItemsSrm(sources, targets.getProducers());
        targets.setKind(SrmRestConstants.KIND_ORGANISATION_UNITS);
        targets.setTotal(BigInteger.valueOf(targets.getProducers().size()));
        return targets;
    }

    private Responsibles toResponsibles(Set<ExternalItem> sources) {
        if (sources == null || sources.size() == 0) {
            return null;
        }
        Responsibles targets = new Responsibles();
        toResourcesExternalItemsSrm(sources, targets.getResponsibles());
        targets.setKind(SrmRestConstants.KIND_ORGANISATION_UNITS);
        targets.setTotal(BigInteger.valueOf(targets.getResponsibles().size()));
        return targets;
    }

    private Contributors toContributors(Set<ExternalItem> sources) {
        if (sources == null || sources.size() == 0) {
            return null;
        }
        Contributors targets = new Contributors();
        toResourcesExternalItemsSrm(sources, targets.getContributors());
        targets.setKind(SrmRestConstants.KIND_ORGANISATIONS);
        targets.setTotal(BigInteger.valueOf(targets.getContributors().size()));
        return targets;
    }

    private Publishers toPublishers(Set<ExternalItem> sources) {
        if (sources == null || sources.size() == 0) {
            return null;
        }
        Publishers targets = new Publishers();
        toResourcesExternalItemsSrm(sources, targets.getPublishers());
        targets.setKind(SrmRestConstants.KIND_ORGANISATION_UNITS);
        targets.setTotal(BigInteger.valueOf(targets.getPublishers().size()));
        return targets;
    }

    private UpdateFrequencies toUpdateFrequencies(Set<ExternalItem> sources) {
        if (sources == null || sources.size() == 0) {
            return null;
        }
        UpdateFrequencies targets = new UpdateFrequencies();
        toResourcesExternalItemsSrm(sources, targets.getUpdateFrequencies());
        targets.setKind(SrmRestConstants.KIND_CODES);
        targets.setTotal(BigInteger.valueOf(targets.getUpdateFrequencies().size()));
        return targets;
    }

    private StatisticalUnits toStatisticalUnits(Set<ExternalItem> sources) {
        if (sources == null || sources.size() == 0) {
            return null;
        }
        StatisticalUnits targets = new StatisticalUnits();
        toResourcesExternalItemsSrm(sources, targets.getStatisticalUnits());
        targets.setKind(SrmRestConstants.KIND_CONCEPTS);
        targets.setTotal(BigInteger.valueOf(targets.getStatisticalUnits().size()));
        return targets;
    }

    private GeographicGranularities toGeographicGranularities(Set<ExternalItem> sources) {
        if (sources == null || sources.size() == 0) {
            return null;
        }
        GeographicGranularities targets = new GeographicGranularities();
        toResourcesExternalItemsSrm(sources, targets.getGeographicGranularities());
        targets.setKind(SrmRestConstants.KIND_CODES);
        targets.setTotal(BigInteger.valueOf(targets.getGeographicGranularities().size()));
        return targets;
    }

    private TemporalGranularities toTemporalGranularities(Set<ExternalItem> sources) {
        if (sources == null || sources.size() == 0) {
            return null;
        }
        TemporalGranularities targets = new TemporalGranularities();
        toResourcesExternalItemsSrm(sources, targets.getTemporalGranularities());
        targets.setKind(SrmRestConstants.KIND_CODES);
        targets.setTotal(BigInteger.valueOf(targets.getTemporalGranularities().size()));
        return targets;
    }

    private Measures toMeasures(Set<ExternalItem> sources) {
        if (sources == null || sources.size() == 0) {
            return null;
        }
        Measures targets = new Measures();
        toResourcesFromExternalItemWithConceptSchemesAndConcepts(sources, targets.getMeasures());
        targets.setKind(SrmRestConstants.KIND_CONCEPTS);
        targets.setTotal(BigInteger.valueOf(targets.getMeasures().size()));
        return targets;
    }

    private StatConcDefs toStatConcDefs(Set<ExternalItem> sources) {
        if (sources == null || sources.size() == 0) {
            return null;
        }
        StatConcDefs targets = new StatConcDefs();
        toResourcesFromExternalItemWithConceptSchemesAndConcepts(sources, targets.getStatConcDefs());
        targets.setKind(SrmRestConstants.KIND_CONCEPTS);
        targets.setTotal(BigInteger.valueOf(targets.getStatConcDefs().size()));
        return targets;
    }

    private void toResourcesFromExternalItemWithConceptSchemesAndConcepts(Set<ExternalItem> sources, List<Resource> targets) {
        if (sources == null || sources.size() == 0) {
            return;
        }
        for (ExternalItem source : sources) {
            if (TypeExternalArtefactsEnum.CONCEPT.equals(source.getType())) {
                Resource target = toResourceExternalItemSrm(source);
                targets.add(target);
            } else if (TypeExternalArtefactsEnum.CONCEPT_SCHEME.equals(source.getType())) {
                List<Resource> targetConcepts = srmConceptSchemeToResourceConcepts(source);
                targets.addAll(targetConcepts);
            } else {
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
                throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
            }
        }
    }

    private List<Resource> srmConceptSchemeToResourceConcepts(ExternalItem conceptSchemeSource) {
        // Return from API
        List<org.siemac.metamac.rest.structural_resources.v1_0.domain.ConceptResource> conceptSources = srmRestExternalFacade.retrieveConceptsByConceptScheme(conceptSchemeSource.getUrn());

        // Transform
        List<Resource> targets = new ArrayList<Resource>(conceptSources.size());
        for (org.siemac.metamac.rest.common.v1_0.domain.Resource conceptSource : conceptSources) {
            Resource conceptTarget = srmResourceToResource(conceptSource);
            targets.add(conceptTarget);
        }
        return targets;
    }

    private Resource srmResourceToResource(org.siemac.metamac.rest.common.v1_0.domain.Resource source) {
        if (source == null) {
            return null;
        }
        Resource target = new Resource();
        target.setId(source.getId());
        target.setUrn(source.getUrn());
        target.setKind(source.getKind());
        target.setSelfLink(source.getSelfLink());
        target.setName(source.getName());
        target.setNestedId(source.getNestedId());
        return target;
    }

    private ClassSystems toClassSystems(Set<ExternalItem> sources) {
        if (sources == null || sources.size() == 0) {
            return null;
        }
        ClassSystems targets = new ClassSystems();
        toResourcesExternalItemsSrm(sources, targets.getClassSystems());
        targets.setKind(SrmRestConstants.KIND_CODELISTS);
        targets.setTotal(BigInteger.valueOf(targets.getClassSystems().size()));
        return targets;
    }

    private InformationSuppliers toInformationSuppliers(Set<ExternalItem> sources) {
        if (sources == null || sources.size() == 0) {
            return null;
        }
        InformationSuppliers targets = new InformationSuppliers();
        toResourcesExternalItemsSrm(sources, targets.getInformationSuppliers());
        targets.setKind(SrmRestConstants.KIND_DATA_PROVIDERS);
        targets.setTotal(BigInteger.valueOf(targets.getInformationSuppliers().size()));
        return targets;
    }

    private FreqColls toFreqColls(Set<ExternalItem> sources) {
        if (sources == null || sources.size() == 0) {
            return null;
        }
        FreqColls targets = new FreqColls();
        toResourcesExternalItemsSrm(sources, targets.getFreqColls());
        targets.setKind(SrmRestConstants.KIND_CODES);
        targets.setTotal(BigInteger.valueOf(targets.getFreqColls().size()));
        return targets;
    }

    private ResourceLink toResourceLink(String kind, String href) {
        ResourceLink target = new ResourceLink();
        target.setKind(kind);
        target.setHref(href);
        return target;
    }

    private void initEndpoints() throws MetamacException {
        // Statistical operations External Api v1.0
        String statisticalOperationsApiExternalEndpoint = configurationService.retrieveStatisticalOperationsExternalApiUrlBase();
        statisticalOperationsApiExternalEndpointV10 = RestUtils.createLink(statisticalOperationsApiExternalEndpoint, StatisticalOperationsRestConstants.API_VERSION_1_0);

        // Srm External Api (do not add api version! it is already stored in database)
        srmApiExternalEndpoint = configurationService.retrieveSrmExternalApiUrlBase();
    }
}

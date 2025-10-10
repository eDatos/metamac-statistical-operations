package org.siemac.metamac.statistical_operations.rest.internal.v1_0.mapper;

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

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.common.v1_0.domain.ChildLinks;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.Item;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configuration;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.rest.search.criteria.mapper.SculptorCriteria2RestCriteria;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.ClassSystems;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.CollMethods;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Costs;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.DataSharings;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Families;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Family;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.FreqColls;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.GeographicGranularities;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.InformationSuppliers;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Instance;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.InstanceTypes;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Instances;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.LegalActs;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Measures;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.OfficialityTypes;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Operation;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Operations;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.ProcStatus;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Producers;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Publishers;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Contributors;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Responsibles;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.SecondarySubjectAreas;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.StatConcDefs;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.StatisticalOperationSources;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.StatisticalOperationTypes;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.StatisticalUnits;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.TemporalGranularities;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.UpdateFrequencies;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CategoryResourceInternal;
import org.siemac.metamac.rest.utils.RestUtils;
import org.siemac.metamac.srm.rest.common.SrmRestConstants;
import org.siemac.metamac.statistical.operations.core.conf.StatisticalOperationsConfigurationService;
import org.siemac.metamac.statistical.operations.core.domain.CollMethod;
import org.siemac.metamac.statistical.operations.core.domain.Cost;
import org.siemac.metamac.statistical.operations.core.domain.InstanceType;
import org.siemac.metamac.statistical.operations.core.domain.OfficialityType;
import org.siemac.metamac.statistical.operations.core.domain.SurveySource;
import org.siemac.metamac.statistical.operations.core.domain.SurveyType;
import org.siemac.metamac.statistical.operations.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.operations.core.enume.domain.StatusEnum;
import org.siemac.metamac.statistical_operations.rest.common.StatisticalOperationsRestConstants;
import org.siemac.metamac.statistical_operations.rest.internal.exception.RestServiceExceptionType;
import org.siemac.metamac.statistical_operations.rest.internal.invocation.CommonMetadataRestExternalFacade;
import org.siemac.metamac.statistical_operations.rest.internal.invocation.SrmRestInternalFacade;
import org.siemac.metamac.statistical_operations.rest.internal.v1_0.service.utils.InternalWebApplicationNavigation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Do2RestInternalMapperV10Impl implements Do2RestInternalMapperV10 {

    @Autowired
    private StatisticalOperationsConfigurationService configurationService;

    @Autowired
    private CommonMetadataRestExternalFacade          commonMetadataRestExternalFacade;

    @Autowired
    private SrmRestInternalFacade                     srmRestInternalFacade;

    private String                                    statisticalOperationsApiInternalEndpointV10;
    private String                                    statisticalOperationsInternalWebApplication;
    private String                                    srmApiInternalEndpoint;
    private String                                    srmInternalWebApplication;

    private InternalWebApplicationNavigation          internalWebApplicationNavigation;

    @PostConstruct
    public void init() throws Exception {
        this.initEndpoints();

        this.internalWebApplicationNavigation = new InternalWebApplicationNavigation(this.statisticalOperationsInternalWebApplication);
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
        target.setSelfLink(this.toOperationSelfLink(source.getCode()));
        target.setName(this.toInternationalString(source.getTitle()));
        target.setAcronym(this.toInternationalString(source.getAcronym()));
        target.setStatisticPlanCode(source.getStatisticPlanCode());
        setSubjectAreas(source, target);
        target.setObjective(this.toInternationalString(source.getObjective()));
        target.setDescription(this.toInternationalString(source.getDescription()));
        target.setStatisticalOperationType(this.toItem(source.getSurveyType()));
        target.setOfficialityType(this.toItem(source.getOfficialityType()));
        target.setIndicatorSystem(source.getIndicatorSystem());
        target.setProducers(this.toProducers(source.getProducer()));
        target.setResponsibles(this.toResponsibles(source.getResponsible()));
        target.setContributors(this.toContributors(source.getContributor()));
        target.setCreatedDate(this.toDate(source.getCreatedDate()));
        target.setInternalInventoryDate(this.toDate(source.getInternalInventoryDate()));
        target.setCurrentlyActive(source.getCurrentlyActive());
        target.setStatus(this.toStatus(source.getStatus()));
        target.setProcStatus(this.toProcStatus(source.getProcStatus()));
        target.setPublishers(this.toPublishers(source.getPublisher()));
        target.setRelPolUsAc(this.toInternationalString(source.getRelPolUsAc()));
        target.setReleaseCalendar(source.getReleaseCalendar());
        target.setDiffusionAndPublicationVisible(source.getDiffusionPublicationVisible());
        target.setReleaseCalendarAccess(source.getReleaseCalendarAccess());
        target.setUpdateFrequencies(this.toUpdateFrequencies(source.getUpdateFrequency()));
        target.setCurrentInstance(this.toResource(this.getInstanceInProcStatus(source.getInstances(), ProcStatusEnum.PUBLISH_EXTERNALLY)));
        target.setCurrentInternalInstance(this.toResource(this.getInstanceInProcStatus(source.getInstances(), ProcStatusEnum.PUBLISH_INTERNALLY)));
        target.setInventoryDate(this.toDate(source.getInventoryDate()));
        target.setRevPolicy(this.toInternationalString(source.getRevPolicy()));
        target.setRevPractice(this.toInternationalString(source.getRevPractice()));
        this.commonMetadataToOperation(source.getCommonMetadata(), target);
        target.setLegalActs(this.toOperationLegalActs(source.getSpecificLegalActs(), null, target.getLegalActs()));
        target.setDataSharings(this.toOperationDataSharings(source.getSpecificDataSharing(), null, target.getDataSharings()));
        target.setComment(this.toInternationalString(source.getComment()));
        target.setNotes(this.toInternationalString(source.getNotes()));
        target.setParentLink(this.toOperationParentLink());
        target.setChildLinks(this.toOperationChildLinks(source));
        target.setManagementAppLink(this.toOperationManagementApplicationLink(source.getCode()));
        target.setTechnicianInCharge(source.getTechnicianInCharge());
        target.setAssistantTechnician(source.getAssistantTechnician());
        target.setGenderPerspective(this.toInternationalString(source.getGenderPerspective()));
        target.setDisaggregationBySex(source.getDisaggregationBySex());
        target.setDisaggregationByAge(source.getDisaggregationByAge());
        target.setDisaggregationByNationality(source.getDisaggregationByNationality());
        target.setDisaggregationByDisability(source.getDisaggregationByDisability());
        return target;
    }

    private void setSubjectAreas(org.siemac.metamac.statistical.operations.core.domain.Operation source, Operation target) throws MetamacException {
        Map<String, CategoryResourceInternal> categoriesResourceByCategoryElementCode = srmRestInternalFacade
                .retrieveDefaultCategoriesByCategoryElementCode(configurationService.retrieveDefaultCategoryScheme());

        if (!categoriesResourceByCategoryElementCode.isEmpty()) {
            if (source.getSubjectArea() != null) {
                target.setSubjectArea(srmResourceInternalToResourceInternal(categoriesResourceByCategoryElementCode.get(source.getSubjectArea().getCode())));

            }
            List<ResourceInternal> categories = new ArrayList<ResourceInternal>();
            for (ExternalItem categoryElement : source.getSecondarySubjectAreas()) {
                ResourceInternal categoryResource = srmResourceInternalToResourceInternal(categoriesResourceByCategoryElementCode.get(categoryElement.getCode()));
                if (categoryResource != null) {
                    categories.add(categoryResource);
                }
            }
            if (!categories.isEmpty()) {
                target.setSecondarySubjectAreas(toSecondarySubjectAreas(categories));
            }
        }
    }

    private ResourceInternal getCategoryByCategoryElement(ExternalItem categoryElement) throws MetamacException {

        CategoryResourceInternal categoryResource = srmRestInternalFacade.retrieveCategoryByCategoryElement(configurationService.retrieveDefaultCategoryScheme(), categoryElement.getCode());
        if (categoryResource != null) {
            return srmResourceInternalToResourceInternal(categoryResource);
        }
        return null;

    }

    @Override
    public Operations toOperations(PagedResult<org.siemac.metamac.statistical.operations.core.domain.Operation> sourcesPagedResult, String query, String orderBy, Integer limit,
            Set<String> parsedFields) throws MetamacException {

        Operations targets = new Operations();
        targets.setKind(StatisticalOperationsRestConstants.KIND_OPERATIONS);

        // Pagination
        String baseLink = this.toOperationsLink();
        SculptorCriteria2RestCriteria.toPagedResult(sourcesPagedResult, targets, query, orderBy, limit, baseLink);

        Map<String, CategoryResourceInternal> categoryResourceByDefaultCategoryScheme = srmRestInternalFacade
                .retrieveDefaultCategoriesByCategoryElementCode(configurationService.retrieveDefaultCategoryScheme());

        // Values
        for (org.siemac.metamac.statistical.operations.core.domain.Operation source : sourcesPagedResult.getValues()) {
            ResourceInternal target = this.toResource(source, parsedFields, categoryResourceByDefaultCategoryScheme);
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
        String baseLink = this.toOperationsByFamilyLink(family);
        SculptorCriteria2RestCriteria.toPagedResult(sourcesPagedResult, targets, query, orderBy, limit, baseLink);

        // Values
        for (org.siemac.metamac.statistical.operations.core.domain.Operation source : sourcesPagedResult.getValues()) {
            ResourceInternal target = this.toResource(source);
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
        target.setSelfLink(this.toFamilySelfLink(source));
        target.setName(this.toInternationalString(source.getTitle()));
        target.setAcronym(this.toInternationalString(source.getAcronym()));
        target.setDescription(this.toInternationalString(source.getDescription()));
        target.setCreatedDate(this.toDate(source.getCreatedDate()));
        target.setInternalInventoryDate(this.toDate(source.getInternalInventoryDate()));
        target.setProcStatus(this.toProcStatus(source.getProcStatus()));
        target.setInventoryDate(this.toDate(source.getInventoryDate()));
        target.setParentLink(this.toFamilyParentLink());
        target.setChildLinks(this.toFamilyChildLinks(source));
        target.setManagementAppLink(this.toFamilyManagementApplicationLink(source));
        return target;
    }

    @Override
    public Families toFamilies(PagedResult<org.siemac.metamac.statistical.operations.core.domain.Family> sourcesPagedResult, String query, String orderBy, Integer limit) {

        Families targets = new Families();
        targets.setKind(StatisticalOperationsRestConstants.KIND_FAMILIES);

        // Pagination
        String baseLink = this.toFamiliesLink();
        SculptorCriteria2RestCriteria.toPagedResult(sourcesPagedResult, targets, query, orderBy, limit, baseLink);

        // Values
        for (org.siemac.metamac.statistical.operations.core.domain.Family source : sourcesPagedResult.getValues()) {
            ResourceInternal target = this.toResource(source);
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
                ResourceInternal target = this.toResource(source);
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
        target.setSelfLink(this.toInstanceSelfLink(source));
        target.setName(this.toInternationalString(source.getTitle()));
        target.setAcronym(this.toInternationalString(source.getAcronym()));
        target.setStatisticalOperation(this.toResource(source.getOperation()));
        target.setPredecessor(this.toResource(this.getInstanceInOrder(source.getOperation().getInstances(), source.getOrder() - 1)));
        target.setSuccessor(this.toResource(this.getInstanceInOrder(source.getOperation().getInstances(), source.getOrder() + 1)));
        target.setDataDescription(this.toInternationalString(source.getDataDescription()));
        target.setStatisticalPopulation(this.toInternationalString(source.getStatisticalPopulation()));
        target.setStatisticalUnits(this.toStatisticalUnits(source.getStatisticalUnit()));
        target.setGeographicGranularity(this.toGeographicGranularities(source.getGeographicGranularity()));
        target.setGeographicComparability(this.toInternationalString(source.getGeographicComparability()));
        target.setTemporalGranularity(this.toTemporalGranularities(source.getTemporalGranularity()));
        target.setTemporalComparability(this.toInternationalString(source.getTemporalComparability()));
        target.setBasePeriod(source.getBasePeriod());
        target.setMeasures(this.toMeasures(source.getUnitMeasure()));
        target.setStatConcDefsDescription(this.toInternationalString(source.getStatConcDef()));
        target.setStatConcDefs(this.toStatConcDefs(source.getStatConcDefList()));
        target.setClassSystemsDescription(this.toInternationalString(source.getClassSystem()));
        target.setClassSystems(this.toClassSystems(source.getClassSystemList()));
        target.setInstanceType(this.toItem(source.getInstanceType()));
        target.setCreatedDate(this.toDate(source.getCreatedDate()));
        target.setInternalInventoryDate(this.toDate(source.getInternalInventoryDate()));
        target.setProcStatus(this.toProcStatus(source.getProcStatus()));
        target.setDocMethod(this.toInternationalString(source.getDocMethod()));
        target.setStatisticalOperationSource(this.toItem(source.getSurveySource()));
        target.setCollMethod(this.toItem(source.getCollMethod()));
        target.setInformationSuppliers(this.toInformationSuppliers(source.getInformationSuppliers()));
        target.setFreqColls(this.toFreqColls(source.getFreqColl()));
        target.setDataValidation(this.toInternationalString(source.getDataValidation()));
        target.setDataCompilation(this.toInternationalString(source.getDataCompilation()));
        target.setAdjustment(this.toInternationalString(source.getAdjustment()));
        target.setSeasonalAdjustment(toInternationalString(source.getSeasonalAdjustment()));
        target.setCostBurden(this.toInternationalString(source.getCostBurden()));
        target.setCosts(this.toCosts(source.getCost()));
        target.setInventoryDate(this.toDate(source.getInventoryDate()));
        target.setQualityDoc(this.toInternationalString(source.getQualityDoc()));
        target.setQualityAssure(this.toInternationalString(source.getQualityAssure()));
        target.setQualityAssmnt(this.toInternationalString(source.getQualityAssmnt()));
        target.setUserNeeds(this.toInternationalString(source.getUserNeeds()));
        target.setUserSat(this.toInternationalString(source.getUserSat()));
        target.setCompleteness(this.toInternationalString(source.getCompleteness()));
        target.setTimeliness(this.toInternationalString(source.getTimeliness()));
        target.setPunctuality(this.toInternationalString(source.getPunctuality()));
        target.setAccuracyOverall(this.toInternationalString(source.getAccuracyOverall()));
        target.setSamplingErr(this.toInternationalString(source.getSamplingErr()));
        target.setNonsamplingErr(this.toInternationalString(source.getNonsamplingErr()));
        target.setCoverageErr(toInternationalString(source.getCoverageErr()));
        target.setMeasurementErr(toInternationalString(source.getMeasurementErr()));
        target.setNonResponseErr(toInternationalString(source.getNonResponseErr()));
        target.setProcessingErr(toInternationalString(source.getProcessingErr()));
        target.setModelErr(toInternationalString(source.getModelErr()));
        target.setCoherXDom(this.toInternationalString(source.getCoherXDomain()));
        target.setCoherSubanualAnual(toInternationalString(source.getCoherSubanualAnual()));
        target.setCoherNationalAccounts(toInternationalString(source.getCoherNationalAccounts()));
        target.setCoherInternal(this.toInternationalString(source.getCoherInternal()));
        target.setComment(this.toInternationalString(source.getComment()));
        target.setNotes(this.toInternationalString(source.getNotes()));
        target.setParentLink(this.toInstanceParentLink(source));
        target.setChildLinks(this.toInstanceChildLinks(source));
        target.setManagementAppLink(this.toInstanceManagementApplicationLink(source));
        return target;
    }

    @Override
    public Instances toInstances(org.siemac.metamac.statistical.operations.core.domain.Operation operation,
            PagedResult<org.siemac.metamac.statistical.operations.core.domain.Instance> sourcesPagedResult, String query, String orderBy, Integer limit) {

        Instances targets = new Instances();
        targets.setKind(StatisticalOperationsRestConstants.KIND_INSTANCES);

        // Pagination
        String baseLink = this.toInstancesLink(operation);
        SculptorCriteria2RestCriteria.toPagedResult(sourcesPagedResult, targets, query, orderBy, limit, baseLink);

        // Values
        for (org.siemac.metamac.statistical.operations.core.domain.Instance source : sourcesPagedResult.getValues()) {
            ResourceInternal target = this.toResource(source);
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
                Item target = this.toItem(source);
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
                Item target = this.toItem(source);
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
                Item target = this.toItem(source);
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
                Item target = this.toItem(source);
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
                Item target = this.toItem(source);
                targets.getCollMethods().add(target);
            }
            targets.setTotal(BigInteger.valueOf(sources.size()));
        }

        return targets;
    }

    @Override
    public Costs toCosts(List<Cost> sources) {
        return this.toCosts((Collection<Cost>) sources);
    }

    private Costs toCosts(Collection<Cost> sources) {
        Costs targets = new Costs();
        targets.setKind(StatisticalOperationsRestConstants.KIND_COSTS);

        if (sources == null) {
            targets.setTotal(BigInteger.ZERO);
        } else {
            for (org.siemac.metamac.statistical.operations.core.domain.Cost source : sources) {
                Item target = this.toItem(source);
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
        Configuration configuration = this.commonMetadataRestExternalFacade.retrieveConfigurationById(commonMetadata.getCode());

        // Transform
        target.setContact(this.commonMetadataResourceInternalToResourceInternal(configuration.getContact()));
        target.setLegalActs(this.toOperationLegalActs(null, configuration.getLegalActs(), target.getLegalActs()));
        target.setDataSharings(this.toOperationDataSharings(null, configuration.getDataSharing(), target.getDataSharings()));
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
            target.getLegalActs().add(this.toInternationalString(source1));
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
            // it can have already other internationalString
            return target;
        }
        if (target == null) {
            target = new DataSharings();
            target.setTotal(BigInteger.ZERO);
        }
        if (source1 != null) {
            target.getDataSharings().add(this.toInternationalString(source1));
            target.setTotal(target.getTotal().add(BigInteger.ONE));
        }
        if (source2 != null) {
            target.getDataSharings().add(source2);
            target.setTotal(target.getTotal().add(BigInteger.ONE));
        }
        return target;
    }

    @Override
    public ResourceInternal toResource(org.siemac.metamac.statistical.operations.core.domain.Operation source) throws MetamacException {
        return toResource(source, null, null);
    }

    private ResourceInternal toResource(org.siemac.metamac.statistical.operations.core.domain.Operation source, Set<String> parsedFields, Map<String, CategoryResourceInternal> categoryResourcesCache)
            throws MetamacException {
        if (source == null) {
            return null;
        }
        ResourceInternal target = new ResourceInternal();
        target.setId(source.getCode());
        target.setUrn(source.getUrn());
        target.setKind(StatisticalOperationsRestConstants.KIND_OPERATION);
        target.setSelfLink(this.toOperationSelfLink(source.getCode()));
        target.setManagementAppLink(this.toOperationManagementApplicationLink(source.getCode()));
        target.setName(this.toInternationalString(source.getTitle()));
        target.setSubjectArea(getSubjectAreaFromCache(source.getSubjectArea(), parsedFields, categoryResourcesCache));
        target.setDiffusionAndPublicationVisible(getDiffusionAndPublicationVisible(source, parsedFields));

        return target;
    }

    private ResourceInternal getSubjectAreaFromCache(ExternalItem subjectArea, Set<String> parsedFields, Map<String, CategoryResourceInternal> categoryResourcesCache) throws MetamacException {
        boolean includeSubjectArea = containsField(parsedFields, StatisticalOperationsRestConstants.FIELD_INCLUDE_SUBJECT_AREA);
        ResourceInternal category = null;
        if (includeSubjectArea) {
            if (categoryResourcesCache != null && !categoryResourcesCache.isEmpty()) {
                category = srmResourceInternalToResourceInternal(categoryResourcesCache.get(subjectArea.getCode()));
            }

            if (category == null) {
                category = getCategoryByCategoryElement(subjectArea);
            }
        }
        return category;
    }

    @javax.annotation.Nullable
    private Boolean getDiffusionAndPublicationVisible(org.siemac.metamac.statistical.operations.core.domain.Operation source, Set<String> parsedFields) throws MetamacException {
        boolean includeDiffusionAndPublicationVisible  = containsField(parsedFields, StatisticalOperationsRestConstants.DIFUSION_PUBLICATION_VISIBLE);
        if (includeDiffusionAndPublicationVisible) {
            return source.getDiffusionPublicationVisible();
        }
        return null;
    }

    private ResourceInternal toResource(org.siemac.metamac.statistical.operations.core.domain.Family source) {
        if (source == null) {
            return null;
        }
        ResourceInternal target = new ResourceInternal();
        target.setId(source.getCode());
        target.setUrn(source.getUrn());
        target.setKind(StatisticalOperationsRestConstants.KIND_FAMILY);
        target.setManagementAppLink(this.toFamilyManagementApplicationLink(source));
        target.setSelfLink(this.toFamilySelfLink(source));
        target.setName(this.toInternationalString(source.getTitle()));
        return target;
    }

    private ResourceInternal toResource(org.siemac.metamac.statistical.operations.core.domain.Instance source) {
        if (source == null) {
            return null;
        }
        ResourceInternal target = new ResourceInternal();
        target.setId(source.getCode());
        target.setUrn(source.getUrn());
        target.setKind(StatisticalOperationsRestConstants.KIND_INSTANCE);
        target.setSelfLink(this.toInstanceSelfLink(source));
        target.setManagementAppLink(this.toInstanceManagementApplicationLink(source));
        target.setName(this.toInternationalString(source.getTitle()));
        return target;
    }

    private Item toItem(org.siemac.metamac.statistical.operations.core.domain.SurveyType source) {
        if (source == null) {
            return null;
        }
        Item target = new Item();
        target.setId(source.getIdentifier());
        target.setName(this.toInternationalString(source.getDescription()));
        return target;
    }

    private Item toItem(org.siemac.metamac.statistical.operations.core.domain.OfficialityType source) {
        if (source == null) {
            return null;
        }
        Item target = new Item();
        target.setId(source.getIdentifier());
        target.setName(this.toInternationalString(source.getDescription()));
        return target;
    }

    private Item toItem(org.siemac.metamac.statistical.operations.core.domain.SurveySource source) {
        if (source == null) {
            return null;
        }
        Item target = new Item();
        target.setId(source.getIdentifier());
        target.setName(this.toInternationalString(source.getDescription()));
        return target;
    }

    private Item toItem(org.siemac.metamac.statistical.operations.core.domain.InstanceType source) {
        if (source == null) {
            return null;
        }
        Item target = new Item();
        target.setId(source.getIdentifier());
        target.setName(this.toInternationalString(source.getDescription()));
        return target;
    }

    private Item toItem(org.siemac.metamac.statistical.operations.core.domain.CollMethod source) {
        if (source == null) {
            return null;
        }
        Item target = new Item();
        target.setId(source.getIdentifier());
        target.setName(this.toInternationalString(source.getDescription()));
        return target;
    }

    private Item toItem(org.siemac.metamac.statistical.operations.core.domain.Cost source) {
        if (source == null) {
            return null;
        }
        Item target = new Item();
        target.setId(source.getIdentifier());
        target.setName(this.toInternationalString(source.getDescription()));
        return target;
    }

    private void toResourcesExternalItems(Set<ExternalItem> sources, List<ResourceInternal> targets, String apiExternalItem, String managementAppBaseUrl) {
        if (sources == null) {
            return;
        }
        for (ExternalItem source : sources) {
            ResourceInternal target = this.toResourceExternalItem(source, apiExternalItem, managementAppBaseUrl);
            targets.add(target);
        }
    }

    private void toResourcesExternalItemsSrm(Set<ExternalItem> sources, List<ResourceInternal> targets) {
        this.toResourcesExternalItems(sources, targets, this.srmApiInternalEndpoint, this.srmInternalWebApplication);
    }

    private ResourceInternal toResourceExternalItemSrm(ExternalItem source) {
        if (source == null) {
            return null;
        }
        return this.toResourceExternalItem(source, this.srmApiInternalEndpoint, this.srmInternalWebApplication);
    }

    private ResourceInternal toResourceExternalItem(ExternalItem source, String apiExternalItemBase, String managementAppBaseUrl) {
        if (source == null) {
            return null;
        }
        ResourceInternal target = new ResourceInternal();
        target.setId(source.getCode());
        target.setNestedId(source.getCodeNested());
        target.setUrn(source.getUrn());
        target.setKind(source.getType().getValue());
        target.setSelfLink(this.toResourceLink(target.getKind(), RestUtils.createLink(apiExternalItemBase, source.getUri())));
        if (source.getManagementAppUrl() != null) {
            target.setManagementAppLink(RestUtils.createLink(managementAppBaseUrl, source.getManagementAppUrl()));
        }
        target.setName(this.toInternationalString(source.getTitle()));
        return target;
    }

    @Override
    public ResourceLink toOperationSelfLink(String operationCode) {
        return this.toResourceLink(StatisticalOperationsRestConstants.KIND_OPERATION, this.toOperationLink(operationCode));
    }

    private ResourceLink toOperationParentLink() {
        return this.toResourceLink(StatisticalOperationsRestConstants.KIND_OPERATIONS, this.toOperationsLink());
    }

    private ChildLinks toOperationChildLinks(org.siemac.metamac.statistical.operations.core.domain.Operation operation) {
        ChildLinks targets = new ChildLinks();

        targets.getChildLinks().add(this.toResourceLink(StatisticalOperationsRestConstants.KIND_INSTANCES, this.toInstancesLink(operation)));
        targets.getChildLinks().add(this.toResourceLink(StatisticalOperationsRestConstants.KIND_FAMILIES, this.toFamiliesByOperationLink(operation)));

        targets.setTotal(BigInteger.valueOf(targets.getChildLinks().size()));
        return targets;
    }

    private ResourceLink toFamilySelfLink(org.siemac.metamac.statistical.operations.core.domain.Family source) {
        return this.toResourceLink(StatisticalOperationsRestConstants.KIND_FAMILY, this.toFamilyLink(source));
    }

    private ResourceLink toFamilyParentLink() {
        return this.toResourceLink(StatisticalOperationsRestConstants.KIND_FAMILIES, this.toFamiliesLink());
    }

    private ChildLinks toFamilyChildLinks(org.siemac.metamac.statistical.operations.core.domain.Family family) {
        ChildLinks targets = new ChildLinks();

        // Operations of family
        targets.getChildLinks().add(this.toResourceLink(StatisticalOperationsRestConstants.KIND_OPERATIONS, this.toOperationsByFamilyLink(family)));

        targets.setTotal(BigInteger.valueOf(targets.getChildLinks().size()));
        return targets;
    }

    private ResourceLink toInstanceSelfLink(org.siemac.metamac.statistical.operations.core.domain.Instance instance) {
        return this.toResourceLink(StatisticalOperationsRestConstants.KIND_INSTANCE, this.toInstanceLink(instance));
    }

    private ResourceLink toInstanceParentLink(org.siemac.metamac.statistical.operations.core.domain.Instance instance) {
        return this.toResourceLink(StatisticalOperationsRestConstants.KIND_OPERATION, this.toOperationLink(instance.getOperation().getCode()));
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
        return RestUtils.createLink(this.statisticalOperationsApiInternalEndpointV10, StatisticalOperationsRestConstants.LINK_SUBPATH_OPERATIONS);
    }

    // API/operations/OPERATION_ID
    private String toOperationLink(String operationCode) {
        String linkOperations = this.toOperationsLink();
        return RestUtils.createLink(linkOperations, operationCode);
    }

    // API/operations/OPERATION_ID/instances
    private String toInstancesLink(org.siemac.metamac.statistical.operations.core.domain.Operation operation) {
        String linkOperation = null;
        if (operation != null) {
            linkOperation = this.toOperationLink(operation.getCode());
        } else {
            linkOperation = this.toOperationLink(StatisticalOperationsRestConstants.WILDCARD_ALL);
        }
        return RestUtils.createLink(linkOperation, StatisticalOperationsRestConstants.LINK_SUBPATH_INSTANCES);
    }

    // API/operations/OPERATION_ID/instances/INSTANCE_ID
    private String toInstanceLink(org.siemac.metamac.statistical.operations.core.domain.Instance instance) {
        String linkOperation = this.toInstancesLink(instance.getOperation());
        return RestUtils.createLink(linkOperation, instance.getCode());
    }

    // API/families
    private String toFamiliesLink() {
        return RestUtils.createLink(this.statisticalOperationsApiInternalEndpointV10, StatisticalOperationsRestConstants.LINK_SUBPATH_FAMILIES);
    }

    // API/families/family
    private String toFamilyLink(org.siemac.metamac.statistical.operations.core.domain.Family family) {
        String linkFamilies = this.toFamiliesLink();
        return RestUtils.createLink(linkFamilies, family.getCode());
    }

    // API/operations/OPERATION_ID/families
    private String toFamiliesByOperationLink(org.siemac.metamac.statistical.operations.core.domain.Operation operation) {
        String linkFamily = this.toOperationLink(operation.getCode());
        return RestUtils.createLink(linkFamily, StatisticalOperationsRestConstants.LINK_SUBPATH_FAMILIES);
    }

    // API/families/FAMILY_ID/operations
    private String toOperationsByFamilyLink(org.siemac.metamac.statistical.operations.core.domain.Family family) {
        String linkFamily = this.toFamilyLink(family);
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
                if (ProcStatusEnum.PUBLISH_INTERNALLY.equals(instance.getProcStatus()) || ProcStatusEnum.PUBLISH_EXTERNALLY.equals(instance.getProcStatus())) {
                    return instance;
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    private ProcStatus toProcStatus(ProcStatusEnum source) {
        if (source == null) {
            return null;
        }
        switch (source) {
            case PUBLISH_INTERNALLY:
                return ProcStatus.INTERNALLY_PUBLISHED;
            case PUBLISH_EXTERNALLY:
                return ProcStatus.EXTERNALLY_PUBLISHED;
            default:
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
                throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
    }

    private org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Status toStatus(StatusEnum source) {
        if (source == null) {
            return null;
        }
        switch (source) {
            case PRE_PLANNING:
                return org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Status.PRE_PLANNING;
            case PLANNING:
                return org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Status.PLANNING;
            case DESIGN:
                return org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Status.DESIGN;
            case PRODUCTION:
                return org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Status.PRODUCTION;
            case OUT_OF_PRINT:
                return org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Status.OUT_OF_PRINT;
            default:
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
                throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
    }

    private SecondarySubjectAreas toSecondarySubjectAreas(List<ResourceInternal> categories) {
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
        this.toResourcesExternalItemsSrm(sources, targets.getProducers());
        targets.setKind(SrmRestConstants.KIND_ORGANISATION_UNITS);
        targets.setTotal(BigInteger.valueOf(targets.getProducers().size()));
        return targets;
    }

    private Responsibles toResponsibles(Set<ExternalItem> sources) {
        if (sources == null || sources.size() == 0) {
            return null;
        }
        Responsibles targets = new Responsibles();
        this.toResourcesExternalItemsSrm(sources, targets.getResponsibles());
        targets.setKind(SrmRestConstants.KIND_ORGANISATION_UNITS);
        targets.setTotal(BigInteger.valueOf(targets.getResponsibles().size()));
        return targets;
    }

    private Contributors toContributors(Set<ExternalItem> sources) {
        if (sources == null || sources.size() == 0) {
            return null;
        }
        Contributors targets = new Contributors();
        this.toResourcesExternalItemsSrm(sources, targets.getContributors());
        targets.setKind(SrmRestConstants.KIND_ORGANISATIONS);
        targets.setTotal(BigInteger.valueOf(targets.getContributors().size()));
        return targets;
    }

    private Publishers toPublishers(Set<ExternalItem> sources) {
        if (sources == null || sources.size() == 0) {
            return null;
        }
        Publishers targets = new Publishers();
        this.toResourcesExternalItemsSrm(sources, targets.getPublishers());
        targets.setKind(SrmRestConstants.KIND_ORGANISATION_UNITS);
        targets.setTotal(BigInteger.valueOf(targets.getPublishers().size()));
        return targets;
    }

    private UpdateFrequencies toUpdateFrequencies(Set<ExternalItem> sources) {
        if (sources == null || sources.size() == 0) {
            return null;
        }
        UpdateFrequencies targets = new UpdateFrequencies();
        this.toResourcesExternalItemsSrm(sources, targets.getUpdateFrequencies());
        targets.setKind(SrmRestConstants.KIND_CODES);
        targets.setTotal(BigInteger.valueOf(targets.getUpdateFrequencies().size()));
        return targets;
    }

    private StatisticalUnits toStatisticalUnits(Set<ExternalItem> sources) {
        if (sources == null || sources.size() == 0) {
            return null;
        }
        StatisticalUnits targets = new StatisticalUnits();
        this.toResourcesExternalItemsSrm(sources, targets.getStatisticalUnits());
        targets.setKind(SrmRestConstants.KIND_CONCEPTS);
        targets.setTotal(BigInteger.valueOf(targets.getStatisticalUnits().size()));
        return targets;
    }

    private GeographicGranularities toGeographicGranularities(Set<ExternalItem> sources) {
        if (sources == null || sources.size() == 0) {
            return null;
        }
        GeographicGranularities targets = new GeographicGranularities();
        this.toResourcesExternalItemsSrm(sources, targets.getGeographicGranularities());
        targets.setKind(SrmRestConstants.KIND_CODES);
        targets.setTotal(BigInteger.valueOf(targets.getGeographicGranularities().size()));
        return targets;
    }

    private TemporalGranularities toTemporalGranularities(Set<ExternalItem> sources) {
        if (sources == null || sources.size() == 0) {
            return null;
        }
        TemporalGranularities targets = new TemporalGranularities();
        this.toResourcesExternalItemsSrm(sources, targets.getTemporalGranularities());
        targets.setKind(SrmRestConstants.KIND_CODES);
        targets.setTotal(BigInteger.valueOf(targets.getTemporalGranularities().size()));
        return targets;
    }

    private Measures toMeasures(Set<ExternalItem> sources) {
        if (sources == null || sources.size() == 0) {
            return null;
        }
        Measures targets = new Measures();
        this.toResourcesInternalFromExternalItemWithConceptSchemesAndConcepts(sources, targets.getMeasures());
        targets.setKind(SrmRestConstants.KIND_CONCEPTS);
        targets.setTotal(BigInteger.valueOf(targets.getMeasures().size()));
        return targets;
    }

    private StatConcDefs toStatConcDefs(Set<ExternalItem> sources) {
        if (sources == null || sources.size() == 0) {
            return null;
        }
        StatConcDefs targets = new StatConcDefs();
        this.toResourcesInternalFromExternalItemWithConceptSchemesAndConcepts(sources, targets.getStatConcDefs());
        targets.setKind(SrmRestConstants.KIND_CONCEPTS);
        targets.setTotal(BigInteger.valueOf(targets.getStatConcDefs().size()));
        return targets;
    }

    private void toResourcesInternalFromExternalItemWithConceptSchemesAndConcepts(Set<ExternalItem> sources, List<ResourceInternal> targets) {
        if (sources == null || sources.size() == 0) {
            return;
        }
        for (ExternalItem source : sources) {
            if (TypeExternalArtefactsEnum.CONCEPT.equals(source.getType())) {
                ResourceInternal target = this.toResourceExternalItemSrm(source);
                targets.add(target);
            } else if (TypeExternalArtefactsEnum.CONCEPT_SCHEME.equals(source.getType())) {
                List<ResourceInternal> targetConcepts = this.srmConceptSchemeToResourceInternalConcepts(source);
                targets.addAll(targetConcepts);
            } else {
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
                throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
            }
        }
    }

    private ClassSystems toClassSystems(Set<ExternalItem> sources) {
        if (sources == null || sources.size() == 0) {
            return null;
        }
        ClassSystems targets = new ClassSystems();
        this.toResourcesExternalItemsSrm(sources, targets.getClassSystems());
        targets.setKind(SrmRestConstants.KIND_CODELISTS);
        targets.setTotal(BigInteger.valueOf(targets.getClassSystems().size()));
        return targets;
    }

    private InformationSuppliers toInformationSuppliers(Set<ExternalItem> sources) {
        if (sources == null || sources.size() == 0) {
            return null;
        }
        InformationSuppliers targets = new InformationSuppliers();
        this.toResourcesExternalItemsSrm(sources, targets.getInformationSuppliers());
        targets.setKind(SrmRestConstants.KIND_DATA_PROVIDERS);
        targets.setTotal(BigInteger.valueOf(targets.getInformationSuppliers().size()));
        return targets;
    }

    private FreqColls toFreqColls(Set<ExternalItem> sources) {
        if (sources == null || sources.size() == 0) {
            return null;
        }
        FreqColls targets = new FreqColls();
        this.toResourcesExternalItemsSrm(sources, targets.getFreqColls());
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
        // Srm internal application
        this.srmInternalWebApplication = this.configurationService.retrieveSrmInternalWebApplicationUrlBase();
        this.srmInternalWebApplication = StringUtils.removeEnd(this.srmInternalWebApplication, "/");

        // Statistical operations internal application
        this.statisticalOperationsInternalWebApplication = this.configurationService.retrieveStatisticalOperationsInternalWebApplicationUrlBase();
        this.statisticalOperationsInternalWebApplication = StringUtils.removeEnd(this.statisticalOperationsInternalWebApplication, "/");

        // Statistical operations Internal Api v1.0
        String statisticalOperationsApiInternalEndpoint = this.configurationService.retrieveStatisticalOperationsInternalApiUrlBase();
        this.statisticalOperationsApiInternalEndpointV10 = RestUtils.createLink(statisticalOperationsApiInternalEndpoint, StatisticalOperationsRestConstants.API_VERSION_1_0);

        // Srm Internal Api (do not add api version! it is already stored in database)
        this.srmApiInternalEndpoint = this.configurationService.retrieveSrmInternalApiUrlBase();
    }

    @Override
    public String toOperationManagementApplicationLink(String operationCode) {
        return this.internalWebApplicationNavigation.buildOperationUrl(operationCode);
    }

    private String toFamilyManagementApplicationLink(org.siemac.metamac.statistical.operations.core.domain.Family source) {
        return this.internalWebApplicationNavigation.buildFamilyUrl(source);
    }

    private String toInstanceManagementApplicationLink(org.siemac.metamac.statistical.operations.core.domain.Instance source) {
        return this.internalWebApplicationNavigation.buildInstanceUrl(source);
    }

    private List<ResourceInternal> srmConceptSchemeToResourceInternalConcepts(ExternalItem conceptSchemeSource) {
        // Return from API
        List<org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptResourceInternal> conceptSources = this.srmRestInternalFacade
                .retrieveConceptsByConceptScheme(conceptSchemeSource.getUrn());

        // Transform
        List<ResourceInternal> targets = new ArrayList<ResourceInternal>(conceptSources.size());
        for (org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal conceptSource : conceptSources) {
            ResourceInternal conceptTarget = this.srmResourceInternalToResourceInternal(conceptSource);
            targets.add(conceptTarget);
        }
        return targets;
    }

    private ResourceInternal commonMetadataResourceInternalToResourceInternal(org.siemac.metamac.rest.common_metadata.v1_0.domain.ResourceInternal source) {
        if (source == null) {
            return null;
        }
        ResourceInternal target = new ResourceInternal();
        target.setId(source.getId());
        target.setUrn(source.getUrn());
        target.setKind(source.getKind());
        target.setSelfLink(source.getSelfLink());
        target.setManagementAppLink(source.getManagementAppLink());
        target.setName(source.getName());
        return target;
    }

    private ResourceInternal srmResourceInternalToResourceInternal(org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal source) {
        if (source == null) {
            return null;
        }
        ResourceInternal target = new ResourceInternal();
        target.setId(source.getId());
        target.setUrn(source.getUrn());
        target.setKind(source.getKind());
        target.setSelfLink(source.getSelfLink());
        target.setManagementAppLink(source.getManagementAppLink());
        target.setName(source.getName());
        return target;
    }

}

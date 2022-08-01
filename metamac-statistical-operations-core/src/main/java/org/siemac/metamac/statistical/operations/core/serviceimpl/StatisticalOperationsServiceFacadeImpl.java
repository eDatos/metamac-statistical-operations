package org.siemac.metamac.statistical.operations.core.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.criteria.MetamacCriteria;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.criteria.SculptorCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.operations.core.domain.CollMethod;
import org.siemac.metamac.statistical.operations.core.domain.Cost;
import org.siemac.metamac.statistical.operations.core.domain.Family;
import org.siemac.metamac.statistical.operations.core.domain.Instance;
import org.siemac.metamac.statistical.operations.core.domain.InstanceType;
import org.siemac.metamac.statistical.operations.core.domain.OfficialityType;
import org.siemac.metamac.statistical.operations.core.domain.Operation;
import org.siemac.metamac.statistical.operations.core.domain.SurveySource;
import org.siemac.metamac.statistical.operations.core.domain.SurveyType;
import org.siemac.metamac.statistical.operations.core.dto.CollMethodDto;
import org.siemac.metamac.statistical.operations.core.dto.CostDto;
import org.siemac.metamac.statistical.operations.core.dto.FamilyBaseDto;
import org.siemac.metamac.statistical.operations.core.dto.FamilyDto;
import org.siemac.metamac.statistical.operations.core.dto.InstanceBaseDto;
import org.siemac.metamac.statistical.operations.core.dto.InstanceDto;
import org.siemac.metamac.statistical.operations.core.dto.InstanceTypeDto;
import org.siemac.metamac.statistical.operations.core.dto.OfficialityTypeDto;
import org.siemac.metamac.statistical.operations.core.dto.OperationBaseDto;
import org.siemac.metamac.statistical.operations.core.dto.OperationDto;
import org.siemac.metamac.statistical.operations.core.dto.SurveySourceDto;
import org.siemac.metamac.statistical.operations.core.dto.SurveyTypeDto;
import org.siemac.metamac.statistical.operations.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.operations.core.enume.domain.StatisticalOperationsRoleEnum;
import org.siemac.metamac.statistical.operations.core.mapper.Do2DtoMapper;
import org.siemac.metamac.statistical.operations.core.mapper.Dto2DoMapper;
import org.siemac.metamac.statistical.operations.core.mapper.MetamacCriteria2SculptorCriteriaMapper;
import org.siemac.metamac.statistical.operations.core.mapper.SculptorCriteria2MetamacCriteriaMapper;
import org.siemac.metamac.statistical.operations.core.security.SecurityUtils;
import org.siemac.metamac.statistical.operations.core.serviceapi.StatisticalOperationsListsService;
import org.siemac.metamac.statistical.operations.core.serviceapi.StreamMessagingServiceFacade;
import org.siemac.metamac.statistical.operations.core.serviceimpl.result.PublishExternallyOperationServiceResult;
import org.siemac.metamac.statistical.operations.core.serviceimpl.result.PublishInternallyOperationServiceResult;
import org.siemac.metamac.statistical.operations.core.serviceimpl.result.ReSendStreamMessageOperationServiceResult;
import org.siemac.metamac.statistical.operations.core.serviceimpl.result.SendStreamMessageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of StatisticalOperationsServiceFacade.
 */
@Service("statisticalOperationsServiceFacade")
public class StatisticalOperationsServiceFacadeImpl extends StatisticalOperationsServiceFacadeImplBase {

    @Autowired
    private Do2DtoMapper                           do2DtoMapper;

    @Autowired
    private Dto2DoMapper                           dto2DoMapper;

    @Autowired
    private MetamacCriteria2SculptorCriteriaMapper metamacCriteria2SculptorCriteriaMapper;

    @Autowired
    private SculptorCriteria2MetamacCriteriaMapper sculptorCriteria2MetamacCriteriaMapper;

    @Autowired
    private StatisticalOperationsListsService      statisticalOperationsListsService;

    @Autowired
    private StreamMessagingServiceFacade           streamMessagingServiceFacade;

    public StatisticalOperationsServiceFacadeImpl() {
    }

    /**************************************************************************
     * Dependences
     **************************************************************************/

    @Override
    protected StatisticalOperationsListsService getStatisticalOperationsListsService() {
        return statisticalOperationsListsService;
    }

    // --------------------------------------------------------------------------------------------------------------
    // ---------------------------------------------- OPERATION TYPES ----------------------------------------------
    // --------------------------------------------------------------------------------------------------------------

    @Override
    public List<SurveyTypeDto> findAllSurveyTypes(ServiceContext ctx) throws MetamacException {
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        List<SurveyType> surveyTypesList = getStatisticalOperationsListsService().findAllSurveyTypes(ctx);
        List<SurveyTypeDto> surveyTypesDtoList = new ArrayList<>();
        for (SurveyType item : surveyTypesList) {
            surveyTypesDtoList.add(do2DtoMapper.surveyTypeToDto(item));
        }
        return surveyTypesDtoList;
    }

    @Override
    public SurveyTypeDto findSurveyTypeById(ServiceContext ctx, Long id) throws MetamacException {
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        return do2DtoMapper.surveyTypeToDto(getStatisticalOperationsListsService().findSurveyTypeById(ctx, id));
    }

    // --------------------------------------------------------------------------------------------------------------
    // ---------------------------------------------- ACTIVITY TYPES -----------------------------------------------
    // --------------------------------------------------------------------------------------------------------------

    @Override
    public List<InstanceTypeDto> findAllInstanceTypes(ServiceContext ctx) throws MetamacException {
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        List<InstanceType> instanceTypesList = getStatisticalOperationsListsService().findAllInstanceTypes(ctx);
        List<InstanceTypeDto> instanceTypesDtoList = new ArrayList<InstanceTypeDto>();
        for (InstanceType item : instanceTypesList) {
            instanceTypesDtoList.add(do2DtoMapper.instanceTypeToDto(item));
        }
        return instanceTypesDtoList;
    }

    @Override
    public InstanceTypeDto findInstanceTypeById(ServiceContext ctx, Long id) throws MetamacException {
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        return do2DtoMapper.instanceTypeToDto(getStatisticalOperationsListsService().findInstanceTypeById(ctx, id));
    }

    // --------------------------------------------------------------------------------------------------------------
    // ----------------------------------------------- SOURCES DATA ------------------------------------------------
    // --------------------------------------------------------------------------------------------------------------

    @Override
    public List<SurveySourceDto> findAllSurveySources(ServiceContext ctx) throws MetamacException {
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        List<SurveySource> surveySourcesList = getStatisticalOperationsListsService().findAllSurveySources(ctx);
        List<SurveySourceDto> surveySourcesDtoList = new ArrayList<SurveySourceDto>();
        for (SurveySource item : surveySourcesList) {
            surveySourcesDtoList.add(do2DtoMapper.surveySourceToDto(item));
        }
        return surveySourcesDtoList;
    }

    @Override
    public SurveySourceDto findSurveySourceById(ServiceContext ctx, Long id) throws MetamacException {
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        return do2DtoMapper.surveySourceToDto(getStatisticalOperationsListsService().findSurveySourceById(ctx, id));
    }

    // ----------------------------------------------------------------------------------------------------------------
    // ---------------------------------------------- OFFICIALITY TYPES ----------------------------------------------
    // ----------------------------------------------------------------------------------------------------------------

    @Override
    public List<OfficialityTypeDto> findAllOfficialityTypes(ServiceContext ctx) throws MetamacException {
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        List<OfficialityType> officialityTypesList = getStatisticalOperationsListsService().findAllOfficialityTypes(ctx);
        List<OfficialityTypeDto> officialityTypesDtoList = new ArrayList<OfficialityTypeDto>();
        for (OfficialityType item : officialityTypesList) {
            officialityTypesDtoList.add(do2DtoMapper.officialityTypeToDto(item));
        }
        return officialityTypesDtoList;
    }

    @Override
    public OfficialityTypeDto findOfficialityTypeById(ServiceContext ctx, Long id) throws MetamacException {
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        return do2DtoMapper.officialityTypeToDto(getStatisticalOperationsListsService().findOfficialityTypeById(ctx, id));
    }

    // ----------------------------------------------------------------------------------------------------------------
    // ---------------------------------------------- COLL METHODS ----------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------------

    @Override
    public List<CollMethodDto> findAllCollMethods(ServiceContext ctx) throws MetamacException {
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        List<CollMethod> collMethodsList = getStatisticalOperationsListsService().findAllCollMethods(ctx);
        List<CollMethodDto> collMethodsDtoList = new ArrayList<CollMethodDto>();
        for (CollMethod item : collMethodsList) {
            collMethodsDtoList.add(do2DtoMapper.collMethodToDto(item));
        }
        return collMethodsDtoList;
    }

    @Override
    public CollMethodDto findCollMethodById(ServiceContext ctx, Long id) throws MetamacException {
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        return do2DtoMapper.collMethodToDto(getStatisticalOperationsListsService().findCollMethodById(ctx, id));
    }

    // ----------------------------------------------------------------------------------------------------------------
    // ------------------------------------------------ COSTS ---------------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------------

    @Override
    public List<CostDto> findAllCosts(ServiceContext ctx) throws MetamacException {
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        List<Cost> costsList = getStatisticalOperationsListsService().findAllCosts(ctx);
        List<CostDto> costsDtoList = new ArrayList<CostDto>();
        for (Cost item : costsList) {
            costsDtoList.add(do2DtoMapper.costToDto(item));
        }
        return costsDtoList;
    }

    @Override
    public CostDto findCostById(ServiceContext ctx, Long id) throws MetamacException {
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        return do2DtoMapper.costToDto(getStatisticalOperationsListsService().findCostById(ctx, id));
    }

    // --------------------------------------------------------------------------------------------------------------
    // ---------------------------------------------- FAMILY SERVICES ----------------------------------------------
    // --------------------------------------------------------------------------------------------------------------

    @Override
    public FamilyDto findFamilyById(ServiceContext ctx, Long identifier) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        // Service call
        Family family = getStatisticalOperationsBaseService().findFamilyById(ctx, identifier);

        // Transform to dto
        FamilyDto familyDto = do2DtoMapper.familyToDto(family);

        // Return
        return familyDto;
    }

    @Override
    public FamilyDto findFamilyByCode(ServiceContext ctx, String code) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        // Service call
        Family family = getStatisticalOperationsBaseService().findFamilyByCode(ctx, code);

        // Transform to dto
        FamilyDto familyDto = do2DtoMapper.familyToDto(family);

        // Return
        return familyDto;
    }

    @Override
    public FamilyDto findFamilyByUrn(ServiceContext ctx, String urn) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        // Service call
        Family family = getStatisticalOperationsBaseService().findFamilyByUrn(ctx, urn);

        // Transform to dto
        FamilyDto familyDto = do2DtoMapper.familyToDto(family);

        // Return
        return familyDto;
    }

    @Override
    public FamilyDto createFamily(ServiceContext ctx, FamilyDto familyDto) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION);

        // Transform to entity
        Family family = dto2DoMapper.familyDtoToEntity(familyDto, ctx);

        // Service call
        family = getStatisticalOperationsBaseService().createFamily(ctx, family);

        // Transform to dto
        familyDto = do2DtoMapper.familyToDto(family);

        // Return
        return familyDto;
    }

    @Override
    public FamilyDto updateFamily(ServiceContext ctx, FamilyDto familyDto) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION);

        // Transform to Entity
        Family family = dto2DoMapper.familyDtoToEntity(familyDto, ctx);

        // Service call
        family = getStatisticalOperationsBaseService().updateFamily(ctx, family);

        // Transform to Dto
        familyDto = do2DtoMapper.familyToDto(family);

        return familyDto;
    }

    @Override
    public void deleteFamily(ServiceContext ctx, Long familyId) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION);

        // Service call
        getStatisticalOperationsBaseService().deleteFamily(ctx, familyId);
    }

    @Override
    public List<FamilyBaseDto> findAllFamilies(ServiceContext ctx) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        // Service call
        List<Family> families = getStatisticalOperationsBaseService().findAllFamilies(ctx);

        // Transform to Dto
        List<FamilyBaseDto> familiesDto = familiesListDo2BaseDto(families);

        // Return
        return familiesDto;
    }

    @Override
    public MetamacCriteriaResult<FamilyBaseDto> findFamilyByCondition(ServiceContext ctx, MetamacCriteria criteria) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        // Transform
        SculptorCriteria sculptorCriteria = metamacCriteria2SculptorCriteriaMapper.getFamilyCriteriaMapper().metamacCriteria2SculptorCriteria(criteria);

        // Service call
        PagedResult<Family> families = getStatisticalOperationsBaseService().findFamilyByCondition(ctx, sculptorCriteria.getConditions(), sculptorCriteria.getPagingParameter());

        // Transform to Dto
        MetamacCriteriaResult<FamilyBaseDto> familiesDto = sculptorCriteria2MetamacCriteriaMapper.pageResultToMetamacCriteriaResultFamily(families, sculptorCriteria.getPageSize());

        // Return
        return familiesDto;
    }

    @Override
    public FamilyDto publishInternallyFamily(ServiceContext ctx, Long familyId) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION);

        // Service call
        Family family = getStatisticalOperationsBaseService().publishInternallyFamily(ctx, familyId);

        // Transform to Dto
        FamilyDto familyDto = do2DtoMapper.familyToDto(family);

        // Return
        return familyDto;
    }

    @Override
    public FamilyDto publishExternallyFamily(ServiceContext ctx, Long familyId) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION);

        // Service call
        Family family = getStatisticalOperationsBaseService().publishExternallyFamily(ctx, familyId);

        // Transform to Dto
        FamilyDto familyDto = do2DtoMapper.familyToDto(family);

        // Return
        return familyDto;
    }

    @Override
    public List<OperationBaseDto> findOperationsForFamily(ServiceContext ctx, Long familyId) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        // Service call
        Set<Operation> operations = getStatisticalOperationsBaseService().findFamilyById(ctx, familyId).getOperations();

        // Transform to Dto
        List<OperationBaseDto> operationsDtos = operationsSetDo2BaseDto(operations);

        // Return
        return operationsDtos;
    }

    @Override
    public List<OperationBaseDto> addOperationForFamily(ServiceContext ctx, Long familyId, Long operationId) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION);

        // Service call
        getStatisticalOperationsBaseService().addOperationFamilyAssociation(ctx, familyId, operationId);

        // Return
        return findOperationsForFamily(ctx, familyId);
    }

    @Override
    public List<OperationBaseDto> removeOperationForFamily(ServiceContext ctx, Long familyId, Long operationId) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION);

        // Service call
        getStatisticalOperationsBaseService().removeOperationFamilyAssociation(ctx, familyId, operationId);

        // Return
        return findOperationsForFamily(ctx, familyId);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // ---------------------------------------------- OPERATION SERVICES ----------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public OperationDto createOperation(ServiceContext ctx, OperationDto operationDto) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION);

        // Transform to entity
        Operation operation = dto2DoMapper.operationDtoToEntity(operationDto, ctx);

        // Service call
        operation = getStatisticalOperationsBaseService().createOperation(ctx, operation);

        // Transform to dto
        operationDto = operationToDto(ctx, operation);

        // Return
        return operationDto;
    }

    @Override
    public OperationDto updateOperation(ServiceContext ctx, OperationDto operationDto) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION, StatisticalOperationsRoleEnum.TECNICO_APOYO_PLANIFICACION,
                StatisticalOperationsRoleEnum.TECNICO_PRODUCCION);
        checkAccessOperationByCode(ctx, operationDto.getCode(), StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION, StatisticalOperationsRoleEnum.TECNICO_APOYO_PLANIFICACION,
                StatisticalOperationsRoleEnum.TECNICO_PRODUCCION);

        // Transform to Entity
        Operation operation = dto2DoMapper.operationDtoToEntity(operationDto, ctx);

        // Service call
        operation = getStatisticalOperationsBaseService().updateOperation(ctx, operation);

        // Transform to Dto
        operationDto = operationToDto(ctx, operation);

        // Return
        return operationDto;
    }

    @Override
    public void deleteOperation(ServiceContext ctx, Long operationId) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION);
        checkAccessOperationById(ctx, operationId, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION);

        // Service call
        getStatisticalOperationsBaseService().deleteOperation(ctx, operationId);
    }

    @Override
    public List<OperationBaseDto> findAllOperations(ServiceContext ctx) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        // Service call
        List<Operation> operations = getStatisticalOperationsBaseService().findAllOperations(ctx);

        // Transform to Dto
        List<OperationBaseDto> operationsDto = operationsListDo2BaseDto(operations);

        // Return
        return operationsDto;
    }

    @Override
    public MetamacCriteriaResult<OperationBaseDto> findOperationsByCondition(ServiceContext ctx, MetamacCriteria criteria) throws MetamacException {

        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        // Transform
        SculptorCriteria sculptorCriteria = metamacCriteria2SculptorCriteriaMapper.getOperationCriteriaMapper().metamacCriteria2SculptorCriteria(criteria);

        // Service call
        PagedResult<Operation> operations = getStatisticalOperationsBaseService().findOperationByCondition(ctx, sculptorCriteria.getConditions(), sculptorCriteria.getPagingParameter());

        // Transform to Dto
        MetamacCriteriaResult<OperationBaseDto> operationsDto = sculptorCriteria2MetamacCriteriaMapper.pageResultToMetamacCriteriaResultOperation(operations, sculptorCriteria.getPageSize());

        // Return
        return operationsDto;
    }

    @Override
    public OperationDto findOperationById(ServiceContext ctx, Long identifier) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        // Service call
        Operation operation = getStatisticalOperationsBaseService().findOperationById(ctx, identifier);

        // Transform to dto
        OperationDto operationDto = operationToDto(ctx, operation);

        // Return
        return operationDto;
    }

    @Override
    public OperationDto findOperationByCode(ServiceContext ctx, String code) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        // Service call
        Operation operation = getStatisticalOperationsBaseService().findOperationByCode(ctx, code);

        // Transform to dto
        OperationDto operationDto = operationToDto(ctx, operation);

        // Return
        return operationDto;
    }

    @Override
    public OperationDto findOperationByUrn(ServiceContext ctx, String urn) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        // Service call
        Operation operation = getStatisticalOperationsBaseService().findOperationByUrn(ctx, urn);

        // Transform to dto
        OperationDto operationDto = operationToDto(ctx, operation);

        // Return
        return operationDto;

    }

    @Override
    public PublishInternallyOperationServiceResult publishInternallyOperation(ServiceContext ctx, Long operationId) throws MetamacException {
        PublishInternallyOperationServiceResult result = new PublishInternallyOperationServiceResult();

        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION);
        checkAccessOperationById(ctx, operationId, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION);

        // Service call
        Operation operation = getStatisticalOperationsBaseService().publishInternallyOperation(ctx, operationId);

        // Transform to Dto
        OperationDto operationDto = operationToDto(ctx, operation);
        result.setContent(operationDto);

        // Send operation
        SendStreamMessageResult sendStreamMessageResult = streamMessagingServiceFacade.sendMessage(ctx, operation);
        result.getExceptions().addAll(sendStreamMessageResult.getExceptions());

        // Return
        return result;
    }

    @Override
    public PublishExternallyOperationServiceResult publishExternallyOperation(ServiceContext ctx, Long operationId) throws MetamacException {
        PublishExternallyOperationServiceResult result = new PublishExternallyOperationServiceResult();

        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION);
        checkAccessOperationById(ctx, operationId, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION);

        // Service call
        Operation operation = getStatisticalOperationsBaseService().publishExternallyOperation(ctx, operationId);

        // Transform to Dto
        OperationDto operationDto = operationToDto(ctx, operation);
        result.setContent(operationDto);

        // Send operation
        SendStreamMessageResult sendStreamMessageResult = streamMessagingServiceFacade.sendMessage(ctx, operation);
        result.getExceptions().addAll(sendStreamMessageResult.getExceptions());

        // Return
        return result;
    }

    @Override
    public ReSendStreamMessageOperationServiceResult republishExternallyOperation(ServiceContext ctx, Long operationId) throws MetamacException {
        ReSendStreamMessageOperationServiceResult result = new ReSendStreamMessageOperationServiceResult();

        Operation operation = getStatisticalOperationsBaseService().findOperationById(ctx, operationId);

        // Send operation
        SendStreamMessageResult sendStreamMessageResult = streamMessagingServiceFacade.sendMessage(ctx, operation);
        result.getExceptions().addAll(sendStreamMessageResult.getExceptions());

        // Return
        return result;
    }

    @Override
    public List<FamilyBaseDto> findFamiliesForOperation(ServiceContext ctx, Long operationId) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        // Service call
        Set<Family> families = getStatisticalOperationsBaseService().findOperationById(ctx, operationId).getFamilies();

        // Transform to Dto
        List<FamilyBaseDto> familiesDto = familiesSetDo2BaseDto(families);

        // Return
        return familiesDto;
    }

    /**
     * Retrive the instances associated with an operationId. Instances are returned in order of newest to oldest
     */
    @Override
    public List<InstanceBaseDto> findInstancesForOperation(ServiceContext ctx, Long operationId) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        // Service call
        Operation operation = getStatisticalOperationsBaseService().findOperationById(ctx, operationId);

        // Transform to Dto
        List<InstanceBaseDto> instancesDto = instancesListDo2BaseDto(operation.getInstances());

        // Return
        return instancesDto;
    }

    @Override
    public List<FamilyBaseDto> addFamilyForOperation(ServiceContext ctx, Long operationId, Long familyId) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION, StatisticalOperationsRoleEnum.TECNICO_APOYO_PLANIFICACION,
                StatisticalOperationsRoleEnum.TECNICO_PRODUCCION);
        checkAccessOperationById(ctx, operationId, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION, StatisticalOperationsRoleEnum.TECNICO_APOYO_PLANIFICACION,
                StatisticalOperationsRoleEnum.TECNICO_PRODUCCION);

        // Service call
        getStatisticalOperationsBaseService().addOperationFamilyAssociation(ctx, familyId, operationId);

        // Return
        return findFamiliesForOperation(ctx, operationId);
    }

    @Override
    public List<FamilyBaseDto> removeFamilyForOperation(ServiceContext ctx, Long operationId, Long familyId) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION, StatisticalOperationsRoleEnum.TECNICO_APOYO_PLANIFICACION,
                StatisticalOperationsRoleEnum.TECNICO_PRODUCCION);
        checkAccessOperationById(ctx, operationId, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION, StatisticalOperationsRoleEnum.TECNICO_APOYO_PLANIFICACION,
                StatisticalOperationsRoleEnum.TECNICO_PRODUCCION);

        // Service call
        getStatisticalOperationsBaseService().removeOperationFamilyAssociation(ctx, familyId, operationId);

        // Return
        return findFamiliesForOperation(ctx, operationId);
    }

    // ----------------------------------------------------------------------------------------------------------------
    // ---------------------------------------------- INSTANCE SERVICES ----------------------------------------------
    // ----------------------------------------------------------------------------------------------------------------

    @Override
    public InstanceDto createInstance(ServiceContext ctx, Long operationId, InstanceDto instanceDto) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION, StatisticalOperationsRoleEnum.TECNICO_APOYO_PLANIFICACION,
                StatisticalOperationsRoleEnum.TECNICO_PRODUCCION, StatisticalOperationsRoleEnum.TECNICO_APOYO_PRODUCCION);
        checkAccessOperationById(ctx, operationId, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION, StatisticalOperationsRoleEnum.TECNICO_APOYO_PLANIFICACION,
                StatisticalOperationsRoleEnum.TECNICO_PRODUCCION, StatisticalOperationsRoleEnum.TECNICO_APOYO_PRODUCCION);

        // Transform to entity
        Instance instance = dto2DoMapper.instanceDtoToEntity(instanceDto, ctx);

        // Service call
        instance = getStatisticalOperationsBaseService().createInstance(ctx, operationId, instance);

        // Transform to dto
        instanceDto = do2DtoMapper.instanceToDto(instance);

        // Return
        return instanceDto;
    }

    @Override
    public InstanceDto updateInstance(ServiceContext ctx, InstanceDto instanceDto) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION, StatisticalOperationsRoleEnum.TECNICO_APOYO_PLANIFICACION,
                StatisticalOperationsRoleEnum.TECNICO_PRODUCCION, StatisticalOperationsRoleEnum.TECNICO_APOYO_PRODUCCION);
        Long operationId = getStatisticalOperationsBaseService().findInstanceById(ctx, instanceDto.getId()).getOperation().getId();
        checkAccessOperationById(ctx, operationId, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION, StatisticalOperationsRoleEnum.TECNICO_APOYO_PLANIFICACION,
                StatisticalOperationsRoleEnum.TECNICO_PRODUCCION, StatisticalOperationsRoleEnum.TECNICO_APOYO_PRODUCCION);

        // Transform to Entity
        Instance instance = dto2DoMapper.instanceDtoToEntity(instanceDto, ctx);

        // Service call
        instance = getStatisticalOperationsBaseService().updateInstance(ctx, instance);

        // Transform to Dto
        instanceDto = do2DtoMapper.instanceToDto(instance);

        // Return
        return instanceDto;
    }

    /**
     * Upgrade the order of the instances of an operation. You must provide a list of id sorted oldest to newest
     */
    @Override
    public List<InstanceBaseDto> updateInstancesOrder(ServiceContext ctx, Long operationId, List<Long> instancesIdList) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION, StatisticalOperationsRoleEnum.TECNICO_APOYO_PLANIFICACION,
                StatisticalOperationsRoleEnum.TECNICO_PRODUCCION, StatisticalOperationsRoleEnum.TECNICO_APOYO_PRODUCCION);
        checkAccessOperationById(ctx, operationId, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION, StatisticalOperationsRoleEnum.TECNICO_APOYO_PLANIFICACION,
                StatisticalOperationsRoleEnum.TECNICO_PRODUCCION, StatisticalOperationsRoleEnum.TECNICO_APOYO_PRODUCCION);

        // Service call
        getStatisticalOperationsBaseService().updateInstancesOrder(ctx, operationId, instancesIdList);

        // Return
        return findInstancesForOperation(ctx, operationId);
    }

    @Override
    public void deleteInstance(ServiceContext ctx, Long instanceId) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION, StatisticalOperationsRoleEnum.TECNICO_PRODUCCION);
        Long operationId = getStatisticalOperationsBaseService().findInstanceById(ctx, instanceId).getOperation().getId();
        checkAccessOperationById(ctx, operationId, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION, StatisticalOperationsRoleEnum.TECNICO_PRODUCCION);

        // Service call
        getStatisticalOperationsBaseService().deleteInstance(ctx, instanceId);
    }

    @Override
    public List<InstanceBaseDto> findAllInstances(ServiceContext ctx) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        // Service call
        List<Instance> instancesList = getStatisticalOperationsBaseService().findAllInstances(ctx);

        // Transform to Dto
        List<InstanceBaseDto> instancesDto = instancesListDo2BaseDto(instancesList);

        // Return
        return instancesDto;
    }

    @Override
    public MetamacCriteriaResult<InstanceBaseDto> findInstanceByCondition(ServiceContext ctx, MetamacCriteria criteria) throws MetamacException {

        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        // Transform
        SculptorCriteria sculptorCriteria = metamacCriteria2SculptorCriteriaMapper.getInstanceCriteriaMapper().metamacCriteria2SculptorCriteria(criteria);

        // Service call
        PagedResult<Instance> instances = getStatisticalOperationsBaseService().findInstanceByCondition(ctx, sculptorCriteria.getConditions(), sculptorCriteria.getPagingParameter());

        // Transform to Dto
        MetamacCriteriaResult<InstanceBaseDto> instancesDto = sculptorCriteria2MetamacCriteriaMapper.pageResultToMetamacCriteriaResultInstance(instances, sculptorCriteria.getPageSize());

        // Return
        return instancesDto;
    }

    @Override
    public InstanceDto findInstanceById(ServiceContext ctx, Long identifier) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        // Service call
        Instance instance = getStatisticalOperationsBaseService().findInstanceById(ctx, identifier);

        // Transform to dto
        InstanceDto instanceDto = do2DtoMapper.instanceToDto(instance);

        // Return
        return instanceDto;
    }

    @Override
    public InstanceDto findInstanceByCode(ServiceContext ctx, String code) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        // Service call
        Instance instance = getStatisticalOperationsBaseService().findInstanceByCode(ctx, code);

        // Transform to dto
        InstanceDto instanceDto = do2DtoMapper.instanceToDto(instance);

        // Return
        return instanceDto;
    }

    @Override
    public InstanceDto findInstanceByUrn(ServiceContext ctx, String urn) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        // Service call
        Instance instance = getStatisticalOperationsBaseService().findInstanceByUrn(ctx, urn);

        // Transform to dto
        InstanceDto instanceDto = do2DtoMapper.instanceToDto(instance);

        // Return
        return instanceDto;

    }

    @Override
    public InstanceDto publishInternallyInstance(ServiceContext ctx, Long instanceId) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION, StatisticalOperationsRoleEnum.TECNICO_PRODUCCION);
        Long operationId = getStatisticalOperationsBaseService().findInstanceById(ctx, instanceId).getOperation().getId();
        checkAccessOperationById(ctx, operationId, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION, StatisticalOperationsRoleEnum.TECNICO_PRODUCCION);

        // Service call
        Instance instance = getStatisticalOperationsBaseService().publishInternallyInstance(ctx, instanceId);

        // Transform to Dto
        InstanceDto instanceDto = do2DtoMapper.instanceToDto(instance);

        // Return
        return instanceDto;
    }

    @Override
    public InstanceDto publishExternallyInstance(ServiceContext ctx, Long instanceId) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION, StatisticalOperationsRoleEnum.TECNICO_PRODUCCION);
        Long operationId = getStatisticalOperationsBaseService().findInstanceById(ctx, instanceId).getOperation().getId();
        checkAccessOperationById(ctx, operationId, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION, StatisticalOperationsRoleEnum.TECNICO_PRODUCCION);

        // Service call
        Instance instance = getStatisticalOperationsBaseService().publishExternallyInstance(ctx, instanceId);

        // Transform to Dto
        InstanceDto instanceDto = do2DtoMapper.instanceToDto(instance);

        // Return
        return instanceDto;
    }

    @Override
    public OperationBaseDto findOperationForInstance(ServiceContext ctx, Long instanceId) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        // Service call
        Operation operation = getStatisticalOperationsBaseService().findInstanceById(ctx, instanceId).getOperation();

        // Transform and return
        return do2DtoMapper.operationToBaseDto(operation);
    }

    @Override
    public String exportOperationsTsv(ServiceContext ctx, List<Long> operationsId) throws MetamacException {
        //Security
        SecurityUtils.checkServiceOperationAllowed(ctx,StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);



        // Export
        return getStatisticalOperationsBaseService().exportOperationsTsv(ctx, operationsId);
    }
    /*
         // Security
        SecurityUtils.canExportCategoriesTsv(ctx);
        List<String> languages = configurationService.retrieveLanguages();
        List<Operation> operations = getStatisticalOperationsBaseService().findOperationsByIdIn(ctx, operationId);
        List<OperationDto> operationsDto = operationsListDo2Dto(ctx, operations);

        for (OperationDto opDto : operationsDto){
            StatisticalOperationsValidationUtils.checkExportOperationsTsv(opDto.getId(), null);
        }

        // Export
        return TsvExportationUtils.exportStatisticalOperations(operationsDto, languages);
    }
    * */

    @Override
    public InstanceBaseDto findInstanceBaseById(ServiceContext ctx, Long id) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, StatisticalOperationsRoleEnum.ANY_ROLE_ALLOWED);

        // Return
        return do2DtoMapper.instanceToBaseDto(getStatisticalOperationsBaseService().findInstanceById(ctx, id));
    }

    // --------------------------------------------------------------------------------------------------------------
    // ---------------------------------------------- PRIVATE METHODS ----------------------------------------------
    // --------------------------------------------------------------------------------------------------------------

    private OperationDto operationToDto(ServiceContext ctx, Operation operation) throws MetamacException {
        OperationDto operationDto = do2DtoMapper.operationToDto(operation);

        List<InstanceBaseDto> instances = findInstancesForOperation(ctx, operationDto.getId());

        InstanceBaseDto currentInternalInstance = null;
        InstanceBaseDto currentInstance = null;

        for (InstanceBaseDto instanceBaseDto : instances) {
            if (currentInstance == null && ProcStatusEnum.PUBLISH_EXTERNALLY.equals(instanceBaseDto.getProcStatus())) {
                currentInstance = instanceBaseDto;
            }

            if (currentInternalInstance == null && ProcStatusEnum.PUBLISH_INTERNALLY.equals(instanceBaseDto.getProcStatus())) {
                currentInternalInstance = instanceBaseDto;
            }

            if (currentInstance != null && currentInternalInstance != null) {
                break;
            }
        }

        operationDto.setCurrentInstance(currentInstance);
        operationDto.setCurrentInternalInstance(currentInternalInstance);

        return operationDto;
    }

    // --------------------------------------------------------------------------------
    // TRANSFORM LISTS
    // --------------------------------------------------------------------------------
    private List<FamilyBaseDto> familiesListDo2BaseDto(List<Family> families) throws MetamacException {
        List<FamilyBaseDto> familiesDtos = new ArrayList<FamilyBaseDto>();
        for (Family family : families) {
            familiesDtos.add(do2DtoMapper.familyToBaseDto(family));
        }
        return familiesDtos;
    }

    private List<FamilyBaseDto> familiesSetDo2BaseDto(Set<Family> families) throws MetamacException {
        List<FamilyBaseDto> familiesDtos = new ArrayList<FamilyBaseDto>();
        for (Family item : families) {
            familiesDtos.add(do2DtoMapper.familyToBaseDto(item));
        }
        return familiesDtos;
    }

    private List<OperationBaseDto> operationsSetDo2BaseDto(Set<Operation> operations) throws MetamacException {
        List<OperationBaseDto> operationsDtos = new ArrayList<OperationBaseDto>();
        for (Operation operation : operations) {
            operationsDtos.add(do2DtoMapper.operationToBaseDto(operation));
        }

        return operationsDtos;
    }

    private List<OperationBaseDto> operationsListDo2BaseDto(List<Operation> operations) throws MetamacException {
        List<OperationBaseDto> operationsDtos = new ArrayList<OperationBaseDto>();
        for (Operation operation : operations) {
            operationsDtos.add(do2DtoMapper.operationToBaseDto(operation));
        }
        return operationsDtos;
    }

    private List<InstanceBaseDto> instancesListDo2BaseDto(List<Instance> instances) throws MetamacException {
        List<InstanceBaseDto> instancesDtos = new ArrayList<InstanceBaseDto>();

        if (!instances.isEmpty()) {
            for (Instance item : instances) {
                instancesDtos.add(do2DtoMapper.instanceToBaseDto(item));
            }
        }
        return instancesDtos;
    }

    /**
     * Checks user has access to operation by code
     */
    private void checkAccessOperationByCode(ServiceContext ctx, String code, StatisticalOperationsRoleEnum... roles) throws MetamacException {
        SecurityUtils.checkResourceOperationAllowed(ctx, code, roles);
    }

    private void checkAccessOperationById(ServiceContext ctx, Long operationId, StatisticalOperationsRoleEnum... roles) throws MetamacException {
        Operation operation = getStatisticalOperationsBaseService().findOperationById(ctx, operationId);
        checkAccessOperationByCode(ctx, operation.getCode(), roles);

    }

}

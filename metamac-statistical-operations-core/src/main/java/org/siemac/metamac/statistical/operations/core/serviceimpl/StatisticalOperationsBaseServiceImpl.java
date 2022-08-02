package org.siemac.metamac.statistical.operations.core.serviceimpl;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.criteria.utils.CriteriaUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.statistical.operations.core.domain.Family;
import org.siemac.metamac.statistical.operations.core.domain.FamilyRepository;
import org.siemac.metamac.statistical.operations.core.domain.Instance;
import org.siemac.metamac.statistical.operations.core.domain.InstanceProperties;
import org.siemac.metamac.statistical.operations.core.domain.InstanceRepository;
import org.siemac.metamac.statistical.operations.core.domain.Operation;
import org.siemac.metamac.statistical.operations.core.domain.OperationRepository;
import org.siemac.metamac.statistical.operations.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.operations.core.enume.domain.StatusEnum;
import org.siemac.metamac.statistical.operations.core.enume.domain.StreamMessageStatusEnum;
import org.siemac.metamac.statistical.operations.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.operations.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.operations.core.exception.FamilyNotFoundException;
import org.siemac.metamac.statistical.operations.core.exception.InstanceNotFoundException;
import org.siemac.metamac.statistical.operations.core.exception.OperationNotFoundException;
import org.siemac.metamac.statistical.operations.core.serviceimpl.utils.CheckMandatoryMetadataUtil;
import org.siemac.metamac.statistical.operations.core.serviceimpl.utils.StatisticalOperationsValidationUtils;
import org.siemac.metamac.statistical.operations.core.serviceimpl.utils.TsvExportationUtils;
import org.siemac.metamac.statistical.operations.core.serviceimpl.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of StatisticalOperationsBaseService.
 */
@Service("statisticalOperationsBaseService")
public class StatisticalOperationsBaseServiceImpl extends StatisticalOperationsBaseServiceImplBase {

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private InstanceRepository instanceRepository;

    @Autowired
    private ConfigurationService configurationService;


    public StatisticalOperationsBaseServiceImpl() {
    }

    // --------------------------------------------------------------------------------------------------------------
    // ---------------------------------------------- FAMILY SERVICES ----------------------------------------------
    // --------------------------------------------------------------------------------------------------------------

    @Override
    public Family findFamilyById(ServiceContext ctx, Long id) throws MetamacException {
        // Validations
        StatisticalOperationsValidationUtils.checkParameterRequired(id, ServiceExceptionParameters.ID, new ArrayList<MetamacExceptionItem>());

        // Return family
        try {
            return familyRepository.findById(id);
        } catch (FamilyNotFoundException e) {
            throw new MetamacException(ServiceExceptionType.FAMILY_NOT_FOUND);
        }
    }

    @Override
    public Family findFamilyByCode(ServiceContext ctx, String code) throws MetamacException {
        // Validations
        StatisticalOperationsValidationUtils.checkParameterRequired(code, ServiceExceptionParameters.CODE, new ArrayList<MetamacExceptionItem>());

        // Prepare criteria
        List<ConditionalCriteria> conditions = criteriaFor(Family.class).withProperty(org.siemac.metamac.statistical.operations.core.domain.FamilyProperties.code()).eq(code).distinctRoot().build();

        // Find
        List<Family> result = findFamilyByCondition(ctx, conditions);

        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.FAMILY_CODE_NOT_FOUND, code);
        } else if (result.size() > 1) {
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one family with code " + code);
        }

        // Return unique result
        return result.get(0);
    }

    @Override
    public Family findFamilyByUrn(ServiceContext ctx, String urn) throws MetamacException {
        // Validations
        StatisticalOperationsValidationUtils.checkParameterRequired(urn, ServiceExceptionParameters.URN, new ArrayList<MetamacExceptionItem>());

        // Prepare criteria
        List<ConditionalCriteria> conditions = criteriaFor(Family.class).withProperty(org.siemac.metamac.statistical.operations.core.domain.FamilyProperties.urn()).eq(urn).distinctRoot().build();

        // Find
        List<Family> result = findFamilyByCondition(ctx, conditions);

        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.FAMILY_NOT_FOUND, urn);
        } else if (result.size() > 1) {
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one family with urn " + urn);
        }

        // Return unique result
        return result.get(0);
    }

    @Override
    public Family createFamily(ServiceContext ctx, Family family) throws MetamacException {
        // Fill metadata
        family.setProcStatus(ProcStatusEnum.DRAFT);
        family.setUrn(GeneratorUrnUtils.generateSiemacStatisticalFamilyUrn(family.getCode()));

        // Validations
        validateFamilyCodeUnique(ctx, family.getCode(), null);
        CheckMandatoryMetadataUtil.checkCreateFamily(family);

        // Repository operation
        return familyRepository.save(family);
    }

    @Override
    public Family updateFamily(ServiceContext ctx, Family family) throws MetamacException {
        // Fill metadata

        // Validations
        Set<Operation> operations = family.getOperations();
        if (ProcStatusEnum.DRAFT.equals(family.getProcStatus())) {
            family.setUrn(GeneratorUrnUtils.generateSiemacStatisticalFamilyUrn(family.getCode()));
            validateFamilyCodeUnique(ctx, family.getCode(), family.getId());
            CheckMandatoryMetadataUtil.checkCreateFamily(family);
        }

        if (ProcStatusEnum.PUBLISH_INTERNALLY.equals(family.getProcStatus())) {
            CheckMandatoryMetadataUtil.checkFamilyForPublishInternally(family);
            ValidationUtil.validateOperationsForPublishFamilyInternally(operations);
        }

        if (ProcStatusEnum.PUBLISH_EXTERNALLY.equals(family.getProcStatus())) {
            CheckMandatoryMetadataUtil.checkFamilyForPublishExternally(family);
            ValidationUtil.validateOperationsForPublishFamilyExternally(operations);
        }

        // Repository operation
        return familyRepository.save(family);
    }

    @Override
    public void deleteFamily(ServiceContext ctx, Long familyId) throws MetamacException {
        // Retrieve
        Family family = findFamilyById(ctx, familyId);

        // Check if ProcStatus is DRAFT
        ValidationUtil.validateProcStatus(ProcStatusEnum.DRAFT, family.getProcStatus());

        // Remove related operations
        if (!family.getOperations().isEmpty()) {
            family.removeAllOperations();
        }

        familyRepository.delete(family);
    }

    @Override
    public List<Family> findAllFamilies(ServiceContext ctx) throws MetamacException {
        // Repository operation
        return familyRepository.findAll();
    }

    @Override
    public List<Family> findFamilyByCondition(ServiceContext ctx, List<ConditionalCriteria> condition) {
        // Validations

        // Initializations
        condition = CriteriaUtils.initConditions(condition, Family.class);

        // Repository operation
        return familyRepository.findByCondition(condition);
    }

    @Override
    public PagedResult<Family> findFamilyByCondition(ServiceContext ctx, List<ConditionalCriteria> condition, PagingParameter pagingParameter) throws MetamacException {
        // Validations

        // Initializations
        condition = CriteriaUtils.initConditions(condition, Family.class);
        pagingParameter = CriteriaUtils.initPagingParameter(pagingParameter);

        // Repository operation
        return familyRepository.findByCondition(condition, pagingParameter);
    }

    @Override
    public Family publishInternallyFamily(ServiceContext ctx, Long familyId) throws MetamacException {
        // Validations

        // Load entity
        Family family = findFamilyById(ctx, familyId);

        // Check ProcStatus
        ValidationUtil.validateFamilyProcStatusForPublishInternally(family);

        // Change state
        family.setProcStatus(ProcStatusEnum.PUBLISH_INTERNALLY);

        // Fill metadata
        family.setInternalInventoryDate(new DateTime());

        // Repository operation
        return updateFamily(ctx, family);
    }

    @Override
    public Family publishExternallyFamily(ServiceContext ctx, Long familyId) throws MetamacException {
        // Validations

        // Load entity
        Family family = findFamilyById(ctx, familyId);

        // Check ProcStatus
        ValidationUtil.validateProcStatus(ProcStatusEnum.PUBLISH_INTERNALLY, family.getProcStatus());

        // Change state
        family.setProcStatus(ProcStatusEnum.PUBLISH_EXTERNALLY);

        // Fill metadata
        family.setInventoryDate(new DateTime());

        // Save
        return updateFamily(ctx, family);
    }

    @Override
    public void addOperationFamilyAssociation(ServiceContext ctx, Long familyId, Long operationId) throws MetamacException {
        // Get operation
        Operation operation = findOperationById(ctx, operationId);

        // Get family
        Family family = findFamilyById(ctx, familyId);

        // Associate
        family.addOperation(operation);

        // Save family
        updateFamily(ctx, family);
    }

    @Override
    public void removeOperationFamilyAssociation(ServiceContext ctx, Long familyId, Long operationId) throws MetamacException {
        // Get operation
        Operation operation = findOperationById(ctx, operationId);

        // Get family
        Family family = findFamilyById(ctx, familyId);

        // Remove association
        family.removeOperation(operation);

        // Save
        updateFamily(ctx, family);
    }

    // --------------------------------------------------------------------------------------------------------------
    // -------------------------------------------- OPERATION SERVICES ----------------------------------------------
    // --------------------------------------------------------------------------------------------------------------

    @Override
    public Operation findOperationById(ServiceContext ctx, Long id) throws MetamacException {
        // Validations

        // Return
        try {
            return operationRepository.findById(id);
        } catch (OperationNotFoundException e) {
            throw new MetamacException(ServiceExceptionType.OPERATION_NOT_FOUND);
        }
    }

    @Override
    public Operation findOperationByCode(ServiceContext ctx, String code) throws MetamacException {
        // Validations
        StatisticalOperationsValidationUtils.checkParameterRequired(code, ServiceExceptionParameters.CODE, new ArrayList<MetamacExceptionItem>());

        // Prepare criteria
        List<ConditionalCriteria> conditions = criteriaFor(Operation.class).withProperty(org.siemac.metamac.statistical.operations.core.domain.OperationProperties.code()).eq(code).distinctRoot()
                .build();

        // Find
        List<Operation> result = findOperationByCondition(ctx, conditions);

        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.OPERATION_CODE_NOT_FOUND, code);
        } else if (result.size() > 1) {
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one operation with code " + code);
        }

        // Return unique result
        return result.get(0);
    }

    @Override
    public Operation findOperationByUrn(ServiceContext ctx, String urn) throws MetamacException {
        // Validations
        StatisticalOperationsValidationUtils.checkParameterRequired(urn, ServiceExceptionParameters.URN, new ArrayList<MetamacExceptionItem>());

        // Prepare criteria
        List<ConditionalCriteria> conditions = criteriaFor(Operation.class).withProperty(org.siemac.metamac.statistical.operations.core.domain.OperationProperties.urn()).eq(urn).distinctRoot()
                .build();

        // Find
        List<Operation> result = findOperationByCondition(ctx, conditions);

        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.OPERATION_NOT_FOUND, urn);
        } else if (result.size() > 1) {
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one operation with urn " + urn);
        }

        // Return unique result
        return result.get(0);
    }

    @Override
    public Operation createOperation(ServiceContext ctx, Operation operation) throws MetamacException {
        // Fill metadata
        operation.setUrn(GeneratorUrnUtils.generateSiemacStatisticalOperationUrn(operation.getCode()));
        operation.setProcStatus(ProcStatusEnum.DRAFT);
        operation.setStatus(StatusEnum.PLANNING);
        operation.setCurrentlyActive(Boolean.FALSE);
        operation.setStreamMessageStatus(StreamMessageStatusEnum.PENDING);

        // Validations
        validateOperationCodeUnique(ctx, operation.getCode(), null);
        CheckMandatoryMetadataUtil.checkCreateOperation(operation);

        // Repository operation
        return operationRepository.save(operation);
    }

    @Override
    public Operation updateOperation(ServiceContext ctx, Operation operation) throws MetamacException {
        // Validations
        if (ProcStatusEnum.DRAFT.equals(operation.getProcStatus())) {
            // We don't need to update the instances URN because we can't create instances in a draft operation
            operation.setUrn(GeneratorUrnUtils.generateSiemacStatisticalOperationUrn(operation.getCode()));
            validateOperationCodeUnique(ctx, operation.getCode(), operation.getId());
            CheckMandatoryMetadataUtil.checkCreateOperation(operation);
        }

        if (ProcStatusEnum.PUBLISH_INTERNALLY.equals(operation.getProcStatus())) {
            CheckMandatoryMetadataUtil.checkOperationForPublishInternally(operation);
        }

        if (ProcStatusEnum.PUBLISH_EXTERNALLY.equals(operation.getProcStatus())) {
            CheckMandatoryMetadataUtil.checkOperationForPublishExternally(operation);
        }

        // Repository operation
        return operationRepository.save(operation);
    }

    @Override
    public void deleteOperation(ServiceContext ctx, Long operationId) throws MetamacException {
        // Retrieve
        Operation operation = findOperationById(ctx, operationId);

        // Check if ProcStatus is DRAFT
        ValidationUtil.validateProcStatus(ProcStatusEnum.DRAFT, operation.getProcStatus());

        // Remove related families
        if (!operation.getFamilies().isEmpty()) {
            operation.removeAllFamilies();
        }

        operationRepository.delete(operation);
    }

    @Override
    public List<Operation> findAllOperations(ServiceContext ctx) throws MetamacException {
        // Repository operation
        return operationRepository.findAll();
    }

    @Override
    public List<Operation> findOperationByCondition(ServiceContext ctx, List<ConditionalCriteria> condition) throws MetamacException {
        // Validations

        // Initializations
        condition = CriteriaUtils.initConditions(condition, Operation.class);

        // Repository operation
        return operationRepository.findByCondition(condition);
    }

    @Override
    public PagedResult<Operation> findOperationByCondition(ServiceContext ctx, List<ConditionalCriteria> condition, PagingParameter pagingParameter) throws MetamacException {
        // Validations

        // Initializations
        condition = CriteriaUtils.initConditions(condition, Operation.class);
        pagingParameter = CriteriaUtils.initPagingParameter(pagingParameter);

        // Repository operation
        return operationRepository.findByCondition(condition, pagingParameter);
    }

    @Override
    public Operation publishInternallyOperation(ServiceContext ctx, Long operationId) throws MetamacException {
        // Validations

        // Load entity
        Operation operation = findOperationById(ctx, operationId);

        // Check ProcStatus
        ValidationUtil.validateOperationProcStatusForPublishInternally(operation);

        // Change state
        operation.setProcStatus(ProcStatusEnum.PUBLISH_INTERNALLY);

        // Fill metadata
        operation.setInternalInventoryDate(new DateTime());

        // Repository operation
        return updateOperation(ctx, operation);
    }

    @Override
    public Operation publishExternallyOperation(ServiceContext ctx, Long operationId) throws MetamacException {
        // Validations

        // Load entity
        Operation operation = findOperationById(ctx, operationId);

        // Check ProcStatus
        ValidationUtil.validateProcStatus(ProcStatusEnum.PUBLISH_INTERNALLY, operation.getProcStatus());

        // Change state
        operation.setProcStatus(ProcStatusEnum.PUBLISH_EXTERNALLY);

        // Fill metadata
        operation.setInventoryDate(new DateTime());

        // Save
        return updateOperation(ctx, operation);
    }

    @Override
    public String exportOperationsTsv(ServiceContext ctx,  List<Long> operationsId) throws MetamacException {
       //Security check
        StatisticalOperationsValidationUtils.checkParameterRequired(operationsId, ServiceExceptionParameters.ID, new ArrayList<MetamacExceptionItem>());
        
        // Export
        List<ConditionalCriteria> conditions = criteriaFor(Operation.class)
                .withProperty(org.siemac.metamac.statistical.operations.core.domain.OperationProperties.id())
                .in(operationsId).build();
        List<Operation> operations =  operationRepository.findByCondition(conditions);
        List<String> languages = configurationService.retrieveLanguages();

        // Export
        return TsvExportationUtils.exportStatisticalOperations(operations, languages);
    }

    // --------------------------------------------------------------------------------------------------------------
    // --------------------------------------------- INSTANCE SERVICES ----------------------------------------------
    // --------------------------------------------------------------------------------------------------------------

    @Override
    public Instance findInstanceById(ServiceContext ctx, Long id) throws MetamacException {
        // Validations

        // Return
        try {
            return instanceRepository.findById(id);
        } catch (InstanceNotFoundException e) {
            throw new MetamacException(ServiceExceptionType.INSTANCE_ID_NOT_FOUND);
        }
    }

    @Override
    public Instance findInstanceByCode(ServiceContext ctx, String code) throws MetamacException {
        // Validations
        StatisticalOperationsValidationUtils.checkParameterRequired(code, ServiceExceptionParameters.CODE, new ArrayList<MetamacExceptionItem>());

        // Prepare criteria
        List<ConditionalCriteria> conditions = criteriaFor(Instance.class).withProperty(org.siemac.metamac.statistical.operations.core.domain.InstanceProperties.code()).eq(code).distinctRoot()
                .build();

        // Find
        List<Instance> result = findInstanceByCondition(ctx, conditions);

        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.INSTANCE_CODE_NOT_FOUND, code);
        } else if (result.size() > 1) {
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one instance with code " + code);
        }

        // Return unique result
        return result.get(0);
    }

    @Override
    public Instance findInstanceByUrn(ServiceContext ctx, String urn) throws MetamacException {
        // Validations
        StatisticalOperationsValidationUtils.checkParameterRequired(urn, ServiceExceptionParameters.URN, new ArrayList<MetamacExceptionItem>());

        // Prepare criteria
        List<ConditionalCriteria> conditions = criteriaFor(Instance.class).withProperty(org.siemac.metamac.statistical.operations.core.domain.InstanceProperties.urn()).eq(urn).distinctRoot().build();

        // Find
        List<Instance> result = findInstanceByCondition(ctx, conditions);

        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.INSTANCE_NOT_FOUND, urn);
        } else if (result.size() > 1) {
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one instance with urn " + urn);
        }

        // Return unique result
        return result.get(0);
    }

    @Override
    public Instance createInstance(ServiceContext ctx, Long operationId, Instance instance) throws MetamacException {
        // Get information
        Operation operation = findOperationById(ctx, operationId);

        // Check operation for create instance. Operation PROC_STATUS can't be draft
        ValidationUtil.validateOperationProcStatusForSaveInstance(operation);

        Integer order = operation.getInstances().size();

        // Fill metadata
        instance.setUrn(GeneratorUrnUtils.generateSiemacStatisticalOperationInstanceUrn(operation.getCode(), instance.getCode()));
        instance.setOperation(operation);
        instance.setOrder(order);
        instance.setProcStatus(ProcStatusEnum.DRAFT);

        // Validations
        validateInstanceCodeUnique(ctx, instance, null);
        CheckMandatoryMetadataUtil.checkCreateInstance(instance);

        // Save instance
        instance = instanceRepository.save(instance);

        // Save operation
        operation.addInstance(instance);
        updateOperation(ctx, operation);

        // Save instance
        return findInstanceById(ctx, instance.getId());
    }

    @Override
    public Instance updateInstance(ServiceContext ctx, Instance instance) throws MetamacException {
        // Validations
        Operation operation = instance.getOperation();
        if (ProcStatusEnum.DRAFT.equals(instance.getProcStatus())) {
            instance.setUrn(GeneratorUrnUtils.generateSiemacStatisticalOperationInstanceUrn(operation.getCode(), instance.getCode()));
            validateInstanceCodeUnique(ctx, instance, instance.getId());
            CheckMandatoryMetadataUtil.checkCreateInstance(instance);
        }

        if (ProcStatusEnum.PUBLISH_INTERNALLY.equals(instance.getProcStatus())) {
            CheckMandatoryMetadataUtil.checkInstanceForPublishInternally(instance);
            ValidationUtil.validateOperationForPublishInstanceInternally(operation);
        }

        if (ProcStatusEnum.PUBLISH_EXTERNALLY.equals(instance.getProcStatus())) {
            CheckMandatoryMetadataUtil.checkInstanceForPublishExternally(instance);
            ValidationUtil.validateOperationForPublishInstanceExternally(operation);
        }

        // Operation PROC_STATUS can't be draft
        ValidationUtil.validateOperationProcStatusForSaveInstance(operation);

        // Repository operation
        return instanceRepository.save(instance);
    }

    @Override
    public List<Instance> updateInstancesOrder(ServiceContext ctx, Long operationId, List<Long> instancesIdList) throws MetamacException {
        // Validations
        List<Instance> instances = findOperationById(ctx, operationId).getInstances();
        ValidationUtil.checkUpdateInstancesOrder(instancesIdList.size(), instances.size());

        // Update order
        List<Instance> instancesList = new ArrayList<Instance>();
        Integer order = Integer.valueOf(0);

        for (Long instanceId : instancesIdList) {
            // Get instance
            Instance instance = findInstanceById(ctx, instanceId);

            // Set order
            instance.setOrder(order);

            // Add
            instancesList.add(instance);

            order++;
        }

        // Save operation
        Operation operation = findOperationById(ctx, operationId);
        operation.getInstances().clear();
        operation.getInstances().addAll(instancesList);

        operation = updateOperation(ctx, operation);

        return operation.getInstances();
    }

    @Override
    public void deleteInstance(ServiceContext ctx, Long instanceId) throws MetamacException {
        // Retrieve
        Instance instance = findInstanceById(ctx, instanceId);

        // Check if ProcStatus is DRAFT
        ValidationUtil.validateProcStatus(ProcStatusEnum.DRAFT, instance.getProcStatus());

        // Remove instance-operation relation
        Operation operation = findOperationById(ctx, instance.getOperation().getId());
        operation.removeInstance(instance);

        // Remove instance
        instanceRepository.delete(instance);

        // Reset order of instances
        List<Instance> instances = operation.getInstances();
        Collections.reverse(instances);

        List<Long> instancesIdList = new ArrayList<Long>();

        for (Instance item : instances) {
            instancesIdList.add(item.getId());
        }
        updateInstancesOrder(ctx, operation.getId(), instancesIdList);
    }

    @Override
    public List<Instance> findAllInstances(ServiceContext ctx) throws MetamacException {
        // Repository operation
        return instanceRepository.findAll();
    }

    @Override
    public List<Instance> findInstanceByCondition(ServiceContext ctx, List<ConditionalCriteria> condition) throws MetamacException {
        // Initializations
        condition = CriteriaUtils.initConditions(condition, Instance.class);

        // Repository operation
        return instanceRepository.findByCondition(condition);
    }

    @Override
    public PagedResult<Instance> findInstanceByCondition(ServiceContext ctx, List<ConditionalCriteria> condition, PagingParameter pagingParameter) throws MetamacException {
        // Initializations
        condition = CriteriaUtils.initConditions(condition, Instance.class);
        pagingParameter = CriteriaUtils.initPagingParameter(pagingParameter);

        // Repository operation
        return instanceRepository.findByCondition(condition, pagingParameter);
    }

    @Override
    public Instance publishInternallyInstance(ServiceContext ctx, Long instanceId) throws MetamacException {
        // Validations

        // Retrieve
        Instance instance = findInstanceById(ctx, instanceId);

        // Check procStatus
        ValidationUtil.validateInstanceProcStatusForPublishInternally(instance);

        // Change procStatus
        instance.setProcStatus(ProcStatusEnum.PUBLISH_INTERNALLY);

        // Fill metadata
        instance.setInternalInventoryDate(new DateTime());

        // Save instance
        return updateInstance(ctx, instance);
    }

    @Override
    public Instance publishExternallyInstance(ServiceContext ctx, Long instanceId) throws MetamacException {
        // Validations

        // Retrieve
        Instance instance = findInstanceById(ctx, instanceId);

        // Check procStatus
        ValidationUtil.validateProcStatus(ProcStatusEnum.PUBLISH_INTERNALLY, instance.getProcStatus());

        // Change procStatus
        instance.setProcStatus(ProcStatusEnum.PUBLISH_EXTERNALLY);

        // Fill metadata
        instance.setInventoryDate(new DateTime());

        // Return
        return updateInstance(ctx, instance);
    }

    // ----------------------------------------------------------------------
    // UTILS
    // ----------------------------------------------------------------------
    private void validateFamilyCodeUnique(ServiceContext ctx, String code, Long actualId) throws MetamacException {
        List<ConditionalCriteria> condition = criteriaFor(Family.class).withProperty(org.siemac.metamac.statistical.operations.core.domain.FamilyProperties.code()).ignoreCaseEq(code).build();

        List<Family> families = findFamilyByCondition(ctx, condition);

        if (families != null) {
            if (actualId == null) {
                if (families.size() == 1) {
                    throw new MetamacException(ServiceExceptionType.FAMILY_ALREADY_EXIST_CODE_DUPLICATED, code);
                } else if (families.size() > 1) {
                    throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one family with code " + code);
                }
            } else {
                if (families.size() == 2) {
                    throw new MetamacException(ServiceExceptionType.FAMILY_ALREADY_EXIST_CODE_DUPLICATED, code);
                } else if (families.size() > 2) {
                    throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one family with code " + code);
                }
            }
        }
    }

    private void validateOperationCodeUnique(ServiceContext ctx, String code, Long actualId) throws MetamacException {
        List<ConditionalCriteria> condition = criteriaFor(Operation.class).withProperty(org.siemac.metamac.statistical.operations.core.domain.OperationProperties.code()).ignoreCaseEq(code).build();

        List<Operation> operations = findOperationByCondition(ctx, condition);

        if (operations != null) {
            if (actualId == null) {
                if (operations.size() == 1) {
                    throw new MetamacException(ServiceExceptionType.OPERATION_ALREADY_EXIST_CODE_DUPLICATED, code);
                } else if (operations.size() > 1) {
                    throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one operation with code " + code);
                }
            } else {
                if (operations.size() == 2) {
                    throw new MetamacException(ServiceExceptionType.OPERATION_ALREADY_EXIST_CODE_DUPLICATED, code);
                } else if (operations.size() > 2) {
                    throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one operation with code " + code);
                }
            }
        }
    }

    private void validateInstanceCodeUnique(ServiceContext ctx, Instance instance, Long actualId) throws MetamacException {

        String code = instance.getCode();

        // @formatter:off
        List<ConditionalCriteria> condition = criteriaFor(Instance.class)
                                                .withProperty(InstanceProperties.code()).ignoreCaseEq(code)
                                                .and()
                                                .withProperty(InstanceProperties.operation().code()).eq(instance.getOperation().getCode()).build();
        // @formatter:on

        List<Instance> instances = findInstanceByCondition(ctx, condition);

        if (instances != null) {
            if (actualId == null) {
                if (instances.size() == 1) {
                    throw new MetamacException(ServiceExceptionType.INSTANCE_ALREADY_EXIST_CODE_DUPLICATED, code);
                } else if (instances.size() > 1) {
                    throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one instance with code " + code);
                }
            } else {
                if (instances.size() == 2) {
                    throw new MetamacException(ServiceExceptionType.INSTANCE_ALREADY_EXIST_CODE_DUPLICATED, code);
                } else if (instances.size() > 2) {
                    throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one instance with code " + code);
                }
            }
        }
    }
}

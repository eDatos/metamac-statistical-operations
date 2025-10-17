package org.siemac.metamac.statistical.operations.core.serviceimpl.utils;

import java.util.Set;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.operations.core.domain.Family;
import org.siemac.metamac.statistical.operations.core.domain.Instance;
import org.siemac.metamac.statistical.operations.core.domain.Operation;
import org.siemac.metamac.statistical.operations.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.operations.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.operations.core.error.ServiceExceptionType;

public class ValidationUtil {

    // -----------------------------------------------------------------------------------------------------------------
    // ---------------------------------------------- GENERIC VALIDATIONS ----------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Check if an expectedProcStatus is equals to a realProcStatus
     * 
     * @param expectedProcStatus
     * @param realProcStatus
     * @throws MetamacException
     */
    public static void validateProcStatus(ProcStatusEnum expectedProcStatus, ProcStatusEnum realProcStatus) throws MetamacException {
        if (!expectedProcStatus.equals(realProcStatus)) {
            throw new MetamacException(ServiceExceptionType.INVALID_PROC_STATUS, expectedProcStatus);
        }
    }

    // ----------------------------------------------------------------------------------------------------------------
    // ---------------------------------------------- FAMILY VALIDATIONS ----------------------------------------------
    // ----------------------------------------------------------------------------------------------------------------

    /**
     * Check if a family is related with some operations
     * 
     * @param familyDto
     * @param operationsForFamily
     * @throws MetamacException
     */
    public static void validateIfFamilyRelatedWithOperations(Set<Operation> operationsForFamily) throws MetamacException {
        if (operationsForFamily.isEmpty()) {
            throw new MetamacException(ServiceExceptionType.FAMILY_WITHOUT_OPERATIONS);
        }
    }

    /**
     * Check if a family is related with any operation with PUBLISH_INTERNALLY or PUBLISH_EXTERNALLY ProcStatus
     * 
     * @param operations
     * @throws MetamacException
     */
    public static void validateOperationsForPublishFamilyInternally(Set<Operation> operations) throws MetamacException {

        validateIfFamilyRelatedWithOperations(operations);

        for (Operation operation : operations) {
            if (!ProcStatusEnum.DRAFT.equals(operation)) {
                return;
            }
        }

        throw new MetamacException(ServiceExceptionType.FAMILY_WITHOUT_PUBLISHED_INTERNALLY_OPERATIONS);
    }

    /**
     * Check if a family is related with any operation with PUBLISH_EXTERNALLY ProcStatus
     * 
     * @param operations
     * @throws MetamacException
     */
    public static void validateOperationsForPublishFamilyExternally(Set<Operation> operations) throws MetamacException {
        for (Operation operation : operations) {
            if (ProcStatusEnum.PUBLISH_EXTERNALLY.equals(operation.getProcStatus())) {
                return;
            }
        }

        throw new MetamacException(ServiceExceptionType.FAMILY_WITHOUT_PUBLISHED_EXTERNALLY_OPERATIONS);
    }

    /**
     * Check if a family is in a correct procStatus for publish internally
     * 
     * @param familyDto
     * @throws MetamacException
     */
    public static void validateFamilyProcStatusForPublishInternally(Family family) throws MetamacException {
        if (!ProcStatusEnum.DRAFT.equals(family.getProcStatus())) {
            throw new MetamacException(ServiceExceptionType.INVALID_PROC_STATUS, ProcStatusEnum.DRAFT);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------
    // ---------------------------------------------- OPERATION VALIDATIONS ----------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------

    /**
     * Check if an operation is in a correct procStatus for publish internally
     * 
     * @param operation
     * @throws MetamacException
     */
    public static void validateOperationProcStatusForPublishInternally(Operation operation) throws MetamacException {
        if (!ProcStatusEnum.DRAFT.equals(operation.getProcStatus())) {
            throw new MetamacException(ServiceExceptionType.INVALID_PROC_STATUS, ProcStatusEnum.DRAFT);
        }
    }

    // ------------------------------------------------------------------------------------------------------------------
    // ---------------------------------------------- INSTANCE VALIDATIONS ----------------------------------------------
    // ------------------------------------------------------------------------------------------------------------------

    /**
     * Check if an instance is in a correct procStatus for publish internally
     * 
     * @param instance
     * @throws MetamacException
     */
    public static void validateInstanceProcStatusForPublishInternally(Instance instance) throws MetamacException {
        if (!ProcStatusEnum.DRAFT.equals(instance.getProcStatus())) {
            throw new MetamacException(ServiceExceptionType.INVALID_PROC_STATUS, ProcStatusEnum.DRAFT);
        }
    }

    /**
     * Check if the operation id is the same that was persisted
     * 
     * @param operationIdPersisted
     * @param operationIdForPersist
     * @throws MetamacException
     */
    public static void validateOperationForInstance(Long operationIdPersisted, Long operationIdForPersist) throws MetamacException {
        if (operationIdForPersist.compareTo(operationIdPersisted) != 0) {
            throw new MetamacException(ServiceExceptionType.INSTANCE_INCORRECT_OPERATION_ID);
        }

    }


    /**
     * Check if the instance is related at least with one published internally or externally operation
     * 
     * @param operation
     * @throws MetamacException
     */
    public static void validateOperationForPublishInstanceInternally(Operation operation) throws MetamacException {
        if (operation == null) {
            throw new MetamacException(ServiceExceptionType.INSTANCE_WITHOUT_OPERATION);
        }

        if (ProcStatusEnum.DRAFT.equals(operation.getProcStatus())) {
            throw new MetamacException(ServiceExceptionType.INSTANCE_WITHOUT_OPERATION_PUBLISHED);
        }
    }

    /**
     * Check if the instance is related at least with one published externally operation
     * 
     * @param operation
     * @throws MetamacException
     */
    public static void validateOperationForPublishInstanceExternally(Operation operation) throws MetamacException {
        if (operation == null) {
            throw new MetamacException(ServiceExceptionType.INSTANCE_WITHOUT_OPERATION);
        }

        if (!ProcStatusEnum.PUBLISH_EXTERNALLY.equals(operation.getProcStatus())) {
            throw new MetamacException(ServiceExceptionType.INSTANCE_WITHOUT_OPERATION_PUBLISHED_EXTERNALLY);
        }

    }

    /**
     * Check if the expectedSize and the realSize of an instancesList is correct
     * 
     * @param expectedSize
     * @param realSize
     * @throws MetamacException
     */
    public static void checkUpdateInstancesOrder(int expectedSize, int realSize) throws MetamacException {
        if (expectedSize != realSize) {
            throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, ServiceExceptionParameters.INSTANCES_ID_LIST_SIZE);
        }

    }

}

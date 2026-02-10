package org.siemac.metamac.statistical.operations.core.mapper;

import org.fornax.cartridges.sculptor.framework.domain.LeafProperty;
import org.fornax.cartridges.sculptor.framework.domain.Property;
import org.siemac.metamac.core.common.constants.CoreCommonConstants;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteria;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria.CriteriaCallback;
import org.siemac.metamac.core.common.criteria.shared.MetamacCriteriaOrder;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.operations.core.criteria.FamilyCriteriaOrderEnum;
import org.siemac.metamac.statistical.operations.core.criteria.FamilyCriteriaPropertyEnum;
import org.siemac.metamac.statistical.operations.core.criteria.InstanceCriteriaOrderEnum;
import org.siemac.metamac.statistical.operations.core.criteria.InstanceCriteriaPropertyEnum;
import org.siemac.metamac.statistical.operations.core.criteria.OperationCriteriaOrderEnum;
import org.siemac.metamac.statistical.operations.core.criteria.OperationCriteriaPropertyEnum;
import org.siemac.metamac.statistical.operations.core.domain.Family;
import org.siemac.metamac.statistical.operations.core.domain.FamilyProperties;
import org.siemac.metamac.statistical.operations.core.domain.Instance;
import org.siemac.metamac.statistical.operations.core.domain.InstanceProperties;
import org.siemac.metamac.statistical.operations.core.domain.Operation;
import org.siemac.metamac.statistical.operations.core.domain.OperationProperties;
import org.siemac.metamac.statistical.operations.core.error.ServiceExceptionType;
import org.springframework.stereotype.Component;

@Component
public class MetamacCriteria2SculptorCriteriaMapperImpl implements MetamacCriteria2SculptorCriteriaMapper {

    private MetamacCriteria2SculptorCriteria<Family>    familyCriteriaMapper    = null;
    private MetamacCriteria2SculptorCriteria<Operation> operationCriteriaMapper = null;
    private MetamacCriteria2SculptorCriteria<Instance>  instanceCriteriaMapper  = null;

    /**************************************************************************
     * Constructor
     **************************************************************************/

    public MetamacCriteria2SculptorCriteriaMapperImpl() throws MetamacException {
        familyCriteriaMapper = new MetamacCriteria2SculptorCriteria<Family>(Family.class, FamilyCriteriaOrderEnum.class, FamilyCriteriaPropertyEnum.class, new FamilyCriteriaCallback());
        operationCriteriaMapper = new MetamacCriteria2SculptorCriteria<Operation>(Operation.class, OperationCriteriaOrderEnum.class, OperationCriteriaPropertyEnum.class,
                new OperationCriteriaCallback());
        instanceCriteriaMapper = new MetamacCriteria2SculptorCriteria<Instance>(Instance.class, InstanceCriteriaOrderEnum.class, InstanceCriteriaPropertyEnum.class, new InstanceCriteriaCallback());
    }

    /**************************************************************************
     * Mappings
     **************************************************************************/

    @Override
    public MetamacCriteria2SculptorCriteria<Family> getFamilyCriteriaMapper() {
        return familyCriteriaMapper;
    }

    @Override
    public MetamacCriteria2SculptorCriteria<Operation> getOperationCriteriaMapper() {
        return operationCriteriaMapper;
    }

    @Override
    public MetamacCriteria2SculptorCriteria<Instance> getInstanceCriteriaMapper() {
        return instanceCriteriaMapper;
    }

    /**************************************************************************
     * CALLBACK CLASSES
     **************************************************************************/

    // --------------------------------------------------------------------------
    // FAMILY
    // --------------------------------------------------------------------------

    private class FamilyCriteriaCallback implements CriteriaCallback {

        @Override
        public SculptorPropertyCriteria retrieveProperty(MetamacCriteriaPropertyRestriction propertyRestriction) throws MetamacException {
            FamilyCriteriaPropertyEnum propertyNameCriteria = FamilyCriteriaPropertyEnum.fromValue(propertyRestriction.getPropertyName());
            switch (propertyNameCriteria) {
                case CODE:
                    return new SculptorPropertyCriteria(FamilyProperties.code(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
                case TITLE:
                    return new SculptorPropertyCriteria(FamilyProperties.title().texts().label(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
                case ACRONYM:
                    return new SculptorPropertyCriteria(FamilyProperties.acronym().texts().label(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
                case DESCRIPTION:
                    return new SculptorPropertyCriteria(FamilyProperties.description().texts().label(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
                case PROC_STATUS:
                    return new SculptorPropertyCriteria(FamilyProperties.procStatus(), propertyRestriction.getEnumValue(), propertyRestriction.getOperationType());
                case OPERATION_CODE:
                    return new SculptorPropertyCriteria(FamilyProperties.operations().code(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
                case OPERATION_ID:
                    return new SculptorPropertyCriteria(FamilyProperties.operations().id(), propertyRestriction.getLongValue(), propertyRestriction.getOperationType());
                default:
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, propertyRestriction.getPropertyName(), propertyRestriction.getOperationType());
            }
        }

        @SuppressWarnings("rawtypes")
        @Override
        public Property retrievePropertyOrder(MetamacCriteriaOrder order) throws MetamacException {
            FamilyCriteriaOrderEnum propertyNameCriteria = FamilyCriteriaOrderEnum.fromValue(order.getPropertyName());
            switch (propertyNameCriteria) {
                case CODE:
                    return FamilyProperties.code();
                case NAME:
                    return FamilyProperties.title().texts().label();
                case LAST_UPDATED:
                    return new LeafProperty<Family>(FamilyProperties.lastUpdated().getName(), CoreCommonConstants.CRITERIA_DATETIME_COLUMN_DATETIME, true, Family.class);
                default:
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, order.getPropertyName());
            }
        }

        @SuppressWarnings("rawtypes")
        @Override
        public Property retrievePropertyOrderDefault() throws MetamacException {
            return new LeafProperty<Family>(FamilyProperties.lastUpdated().getName(), CoreCommonConstants.CRITERIA_DATETIME_COLUMN_DATETIME, true, Family.class);
        }

    }

    // --------------------------------------------------------------------------
    // OPERATIONS
    // --------------------------------------------------------------------------

    private class OperationCriteriaCallback implements CriteriaCallback {

        @Override
        public SculptorPropertyCriteria retrieveProperty(MetamacCriteriaPropertyRestriction propertyRestriction) throws MetamacException {
            OperationCriteriaPropertyEnum propertyNameCriteria = OperationCriteriaPropertyEnum.fromValue(propertyRestriction.getPropertyName());
            switch (propertyNameCriteria) {
                case CODE:
                    return new SculptorPropertyCriteria(OperationProperties.code(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
                case TITLE:
                    return new SculptorPropertyCriteria(OperationProperties.title().texts().label(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
                case ACRONYM:
                    return new SculptorPropertyCriteria(OperationProperties.acronym().texts().label(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
                case DESCRIPTION:
                    return new SculptorPropertyCriteria(OperationProperties.description().texts().label(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
                case PROC_STATUS:
                    return new SculptorPropertyCriteria(OperationProperties.procStatus(), propertyRestriction.getEnumValue(), propertyRestriction.getOperationType());
                case FAMILY_CODE:
                    return new SculptorPropertyCriteria(OperationProperties.families().code(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
                case FAMILY_ID:
                    return new SculptorPropertyCriteria(OperationProperties.families().id(), propertyRestriction.getLongValue(), propertyRestriction.getOperationType());
                case TECHNICIAN_IN_CHARGE:
                    return new SculptorPropertyCriteria(OperationProperties.technicianInCharge(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
                case ASSISTANT_TECHNICIAN:
                    return new SculptorPropertyCriteria(OperationProperties.assistantTechnician(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
                case STATISTIC_PLAN_CODE:
                    return new SculptorPropertyCriteria(OperationProperties.statisticPlanCode(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
                case STATUS:
                    return new SculptorPropertyCriteria(OperationProperties.status(), propertyRestriction.getEnumValue(), propertyRestriction.getOperationType());
                case EDATOS_MIGRATION_STATUS:
                    return new SculptorPropertyCriteria(OperationProperties.edatosMigrationStatus(), propertyRestriction.getEnumValue(), propertyRestriction.getOperationType());
                case OFFICIALITY_TYPE:
                    return new SculptorPropertyCriteria(OperationProperties.officialityType().identifier(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
                default:
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, propertyRestriction.getPropertyName());
            }
        }

        @SuppressWarnings("rawtypes")
        @Override
        public Property retrievePropertyOrder(MetamacCriteriaOrder order) throws MetamacException {
            OperationCriteriaOrderEnum propertyNameCriteria = OperationCriteriaOrderEnum.fromValue(order.getPropertyName());
            switch (propertyNameCriteria) {
                case CODE:
                    return OperationProperties.code();
                case NAME:
                    return OperationProperties.title().texts().label();
                case LAST_UPDATED:
                    return new LeafProperty<Operation>(OperationProperties.lastUpdated().getName(), CoreCommonConstants.CRITERIA_DATETIME_COLUMN_DATETIME, true, Operation.class);
                default:
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, order.getPropertyName());
            }
        }

        @SuppressWarnings("rawtypes")
        @Override
        public Property retrievePropertyOrderDefault() throws MetamacException {
            return new LeafProperty<Operation>(OperationProperties.lastUpdated().getName(), CoreCommonConstants.CRITERIA_DATETIME_COLUMN_DATETIME, true, Operation.class);
        }

    }

    // --------------------------------------------------------------------------
    // INSTANCE
    // --------------------------------------------------------------------------

    private class InstanceCriteriaCallback implements CriteriaCallback {

        @Override
        public SculptorPropertyCriteria retrieveProperty(MetamacCriteriaPropertyRestriction propertyRestriction) throws MetamacException {
            InstanceCriteriaPropertyEnum propertyNameCriteria = InstanceCriteriaPropertyEnum.fromValue(propertyRestriction.getPropertyName());
            switch (propertyNameCriteria) {
                case CODE:
                    return new SculptorPropertyCriteria(InstanceProperties.code(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
                case TITLE:
                    return new SculptorPropertyCriteria(InstanceProperties.title().texts().label(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
                case ACRONYM:
                    return new SculptorPropertyCriteria(InstanceProperties.acronym().texts().label(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
                case DATA_DESCRIPTION:
                    return new SculptorPropertyCriteria(InstanceProperties.dataDescription().texts().label(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
                case PROC_STATUS:
                    return new SculptorPropertyCriteria(InstanceProperties.procStatus(), propertyRestriction.getEnumValue(), propertyRestriction.getOperationType());
                case OPERATION_CODE:
                    return new SculptorPropertyCriteria(InstanceProperties.operation().code(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
                case OPERATION_ID:
                    return new SculptorPropertyCriteria(InstanceProperties.operation().id(), propertyRestriction.getLongValue(), propertyRestriction.getOperationType());
                default:
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, propertyRestriction.getPropertyName());
            }
        }

        @SuppressWarnings("rawtypes")
        @Override
        public Property retrievePropertyOrder(MetamacCriteriaOrder order) throws MetamacException {
            InstanceCriteriaOrderEnum propertyNameCriteria = InstanceCriteriaOrderEnum.fromValue(order.getPropertyName());
            switch (propertyNameCriteria) {
                case CODE:
                    return InstanceProperties.code();
                case NAME:
                    return InstanceProperties.title().texts().label();
                case LAST_UPDATED:
                    return new LeafProperty<Instance>(InstanceProperties.lastUpdated().getName(), CoreCommonConstants.CRITERIA_DATETIME_COLUMN_DATETIME, true, Instance.class);
                default:
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, order.getPropertyName());
            }
        }

        @SuppressWarnings("rawtypes")
        @Override
        public Property retrievePropertyOrderDefault() throws MetamacException {
            return new LeafProperty<Instance>(InstanceProperties.lastUpdated().getName(), CoreCommonConstants.CRITERIA_DATETIME_COLUMN_DATETIME, true, Instance.class);
        }

    }

}

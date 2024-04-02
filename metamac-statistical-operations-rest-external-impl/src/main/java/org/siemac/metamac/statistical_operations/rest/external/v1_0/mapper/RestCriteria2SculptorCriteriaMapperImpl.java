package org.siemac.metamac.statistical_operations.rest.external.v1_0.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.fornax.cartridges.sculptor.framework.domain.LeafProperty;
import org.fornax.cartridges.sculptor.framework.domain.Property;
import org.siemac.metamac.core.common.constants.CoreCommonConstants;
import org.siemac.metamac.core.common.util.CoreCommonUtil;
import org.siemac.metamac.rest.common.query.domain.MetamacRestOrder;
import org.siemac.metamac.rest.common.query.domain.MetamacRestQueryPropertyRestriction;
import org.siemac.metamac.rest.exception.RestCommonServiceExceptionType;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.rest.search.criteria.SculptorPropertyCriteria;
import org.siemac.metamac.rest.search.criteria.mapper.RestCriteria2SculptorCriteria;
import org.siemac.metamac.rest.search.criteria.mapper.RestCriteria2SculptorCriteria.CriteriaCallback;
import org.siemac.metamac.rest.search.criteria.utils.CriteriaUtils;
import org.siemac.metamac.rest.search.criteria.utils.CriteriaUtils.PropertyValueRestToPropertyValueEntityInterface;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.FamilyCriteriaPropertyOrder;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.FamilyCriteriaPropertyRestriction;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.InstanceCriteriaPropertyOrder;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.InstanceCriteriaPropertyRestriction;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.OperationCriteriaPropertyOrder;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.OperationCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources.v1_0.domain.Category;
import org.siemac.metamac.statistical.operations.core.domain.Family;
import org.siemac.metamac.statistical.operations.core.domain.FamilyProperties;
import org.siemac.metamac.statistical.operations.core.domain.Instance;
import org.siemac.metamac.statistical.operations.core.domain.InstanceProperties;
import org.siemac.metamac.statistical.operations.core.domain.Operation;
import org.siemac.metamac.statistical.operations.core.domain.OperationProperties;
import org.siemac.metamac.statistical.operations.core.enume.domain.StatusEnum;
import org.siemac.metamac.statistical_operations.rest.external.exception.RestServiceExceptionType;
import org.siemac.metamac.statistical_operations.rest.external.invocation.SrmRestExternalFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestCriteria2SculptorCriteriaMapperImpl implements RestCriteria2SculptorCriteriaMapper {

    private RestCriteria2SculptorCriteria<Operation>        operationCriteriaMapper                = null;
    private RestCriteria2SculptorCriteria<Instance>         instanceCriteriaMapper                 = null;
    private RestCriteria2SculptorCriteria<Family>           familyCriteriaMapper                   = null;
    private PropertyValueRestToPropertyValueEntityInterface propertyValueRestToPropertyValueEntity = null;

    private final Logger                                    logger                                 = LoggerFactory.getLogger(RestCriteria2SculptorCriteriaMapperImpl.class);

    @Autowired
    private SrmRestExternalFacade                           srmRestExternalFacade;

    private enum PropertyTypeEnum {
        STRING, DATE, BOOLEAN, STATUS
    }

    public RestCriteria2SculptorCriteriaMapperImpl() {
        propertyValueRestToPropertyValueEntity = new PropertyValueRestToPropertyValueEntity();
        operationCriteriaMapper = new RestCriteria2SculptorCriteria<Operation>(Operation.class, OperationCriteriaPropertyOrder.class, OperationCriteriaPropertyRestriction.class,
                new OperationCriteriaCallback());
        familyCriteriaMapper = new RestCriteria2SculptorCriteria<Family>(Family.class, FamilyCriteriaPropertyOrder.class, FamilyCriteriaPropertyRestriction.class, new FamilyCriteriaCallback());
        instanceCriteriaMapper = new RestCriteria2SculptorCriteria<Instance>(Instance.class, InstanceCriteriaPropertyOrder.class, InstanceCriteriaPropertyRestriction.class,
                new InstanceCriteriaCallback());
    }

    @Override
    public RestCriteria2SculptorCriteria<Operation> getOperationCriteriaMapper() {
        return operationCriteriaMapper;
    }

    @Override
    public RestCriteria2SculptorCriteria<Instance> getInstanceCriteriaMapper() {
        return instanceCriteriaMapper;
    }

    @Override
    public RestCriteria2SculptorCriteria<Family> getFamilyCriteriaMapper() {
        return familyCriteriaMapper;
    }

    private class OperationCriteriaCallback implements CriteriaCallback {

        private RestException createInvalidParameterException(String parameter) throws RestException {
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestCommonServiceExceptionType.PARAMETER_INCORRECT, parameter);
            return new RestException(exception, Response.Status.INTERNAL_SERVER_ERROR);
        }

        private String getCategoryElementByCategoryUrn(String categoryUrn, String parameter) throws RestException {
            try {
                Category category = srmRestExternalFacade.retrieveCategoryByUrn(categoryUrn);

                if (category != null && category.getCategoryElement() != null) {
                    return category.getCategoryElement().getUrn();
                }
            } catch (Exception e) {
                logger.error("category element linked to category (SUBJECT_AREA_URN) " + categoryUrn + " not found in srm resource", e);
                throw createInvalidParameterException(parameter);
            }
            return null;
        }

        @Override
        public SculptorPropertyCriteria retrieveProperty(MetamacRestQueryPropertyRestriction propertyRestriction) throws RestException {
            OperationCriteriaPropertyRestriction propertyNameCriteria = OperationCriteriaPropertyRestriction.fromValue(propertyRestriction.getPropertyName());
            switch (propertyNameCriteria) {
                case ID:
                    return buildSculptorPropertyCriteria(OperationProperties.code(), PropertyTypeEnum.STRING, propertyRestriction);
                case URN:
                    return buildSculptorPropertyCriteria(OperationProperties.urn(), PropertyTypeEnum.STRING, propertyRestriction);
                case TITLE:
                    return buildSculptorPropertyCriteria(OperationProperties.title().texts().label(), PropertyTypeEnum.STRING, propertyRestriction);
                case ACRONYM:
                    return buildSculptorPropertyCriteria(OperationProperties.acronym().texts().label(), PropertyTypeEnum.STRING, propertyRestriction);
                case SUBJECT_AREA_URN:
                    propertyRestriction.setValue(getCategoryElementByCategoryUrn(propertyRestriction.getValue(), OperationCriteriaPropertyRestriction.SUBJECT_AREA_URN.name()));
                    return buildSculptorPropertyCriteria(OperationProperties.subjectArea().urn(), PropertyTypeEnum.STRING, propertyRestriction);
                case SECONDARY_SUBJECT_AREA_URN:
                    propertyRestriction.setValue(getCategoryElementByCategoryUrn(propertyRestriction.getValue(), OperationCriteriaPropertyRestriction.SECONDARY_SUBJECT_AREA_URN.name()));
                    return buildSculptorPropertyCriteria(OperationProperties.secondarySubjectAreas().urn(), PropertyTypeEnum.STRING, propertyRestriction);
                case DESCRIPTION:
                    return buildSculptorPropertyCriteria(OperationProperties.description().texts().label(), PropertyTypeEnum.STRING, propertyRestriction);
                case STATISTICAL_OPERATION_TYPE_ID:
                    return buildSculptorPropertyCriteria(OperationProperties.surveyType().identifier(), PropertyTypeEnum.STRING, propertyRestriction);
                case OFFICIALITY_TYPE_ID:
                    return buildSculptorPropertyCriteria(OperationProperties.officialityType().identifier(), PropertyTypeEnum.STRING, propertyRestriction);
                case IS_INDICATORS_SYSTEM:
                    return buildSculptorPropertyCriteria(OperationProperties.indicatorSystem(), PropertyTypeEnum.BOOLEAN, propertyRestriction);
                case PRODUCER_URN:
                    return buildSculptorPropertyCriteria(OperationProperties.producer().urn(), PropertyTypeEnum.STRING, propertyRestriction);
                case CURRENTLY_ACTIVE:
                    return buildSculptorPropertyCriteria(OperationProperties.currentlyActive(), PropertyTypeEnum.BOOLEAN, propertyRestriction);
                case STATUS:
                    return buildSculptorPropertyCriteria(OperationProperties.status(), PropertyTypeEnum.STATUS, propertyRestriction);
                case PUBLISHER_URN:
                    return buildSculptorPropertyCriteria(OperationProperties.publisher().urn(), PropertyTypeEnum.STRING, propertyRestriction);
                case INVENTORY_DATE:
                    return buildSculptorPropertyCriteria(
                            new LeafProperty<Operation>(OperationProperties.inventoryDate().getName(), CoreCommonConstants.CRITERIA_DATETIME_COLUMN_DATETIME, true, Operation.class),
                            PropertyTypeEnum.DATE, propertyRestriction);
                default:
                    throw toRestExceptionParameterIncorrect(propertyNameCriteria.name());
            }
        }

        @SuppressWarnings("rawtypes")
        @Override
        public Property retrievePropertyOrder(MetamacRestOrder order) throws RestException {
            OperationCriteriaPropertyOrder propertyNameCriteria = OperationCriteriaPropertyOrder.fromValue(order.getPropertyName());
            switch (propertyNameCriteria) {
                case ID:
                    return OperationProperties.code();
                default:
                    throw toRestExceptionParameterIncorrect(propertyNameCriteria.name());
            }
        }

        @SuppressWarnings("rawtypes")
        @Override
        public Property retrievePropertyOrderDefault() throws RestException {
            return OperationProperties.id();
        }
    }

    private class FamilyCriteriaCallback implements CriteriaCallback {

        @Override
        public SculptorPropertyCriteria retrieveProperty(MetamacRestQueryPropertyRestriction propertyRestriction) throws RestException {
            FamilyCriteriaPropertyRestriction propertyNameCriteria = FamilyCriteriaPropertyRestriction.fromValue(propertyRestriction.getPropertyName());
            switch (propertyNameCriteria) {
                case ID:
                    return buildSculptorPropertyCriteria(FamilyProperties.code(), PropertyTypeEnum.STRING, propertyRestriction);
                case URN:
                    return buildSculptorPropertyCriteria(FamilyProperties.urn(), PropertyTypeEnum.STRING, propertyRestriction);
                case TITLE:
                    return buildSculptorPropertyCriteria(FamilyProperties.title().texts().label(), PropertyTypeEnum.STRING, propertyRestriction);
                case ACRONYM:
                    return buildSculptorPropertyCriteria(FamilyProperties.acronym().texts().label(), PropertyTypeEnum.STRING, propertyRestriction);
                case DESCRIPTION:
                    return buildSculptorPropertyCriteria(FamilyProperties.description().texts().label(), PropertyTypeEnum.STRING, propertyRestriction);
                case INVENTORY_DATE:
                    return buildSculptorPropertyCriteria(
                            new LeafProperty<Family>(FamilyProperties.inventoryDate().getName(), CoreCommonConstants.CRITERIA_DATETIME_COLUMN_DATETIME, true, Family.class), PropertyTypeEnum.DATE,
                            propertyRestriction);
                default:
                    throw toRestExceptionParameterIncorrect(propertyNameCriteria.name());
            }
        }
        @SuppressWarnings("rawtypes")
        @Override
        public Property retrievePropertyOrder(MetamacRestOrder order) throws RestException {
            FamilyCriteriaPropertyOrder propertyNameCriteria = FamilyCriteriaPropertyOrder.fromValue(order.getPropertyName());
            switch (propertyNameCriteria) {
                case ID:
                    return FamilyProperties.code();
                default:
                    throw toRestExceptionParameterIncorrect(propertyNameCriteria.name());
            }
        }

        @SuppressWarnings("rawtypes")
        @Override
        public Property retrievePropertyOrderDefault() throws RestException {
            return FamilyProperties.id();
        }
    }

    private class InstanceCriteriaCallback implements CriteriaCallback {

        @Override
        public SculptorPropertyCriteria retrieveProperty(MetamacRestQueryPropertyRestriction propertyRestriction) throws RestException {
            InstanceCriteriaPropertyRestriction propertyNameCriteria = InstanceCriteriaPropertyRestriction.fromValue(propertyRestriction.getPropertyName());
            switch (propertyNameCriteria) {
                case ID:
                    return buildSculptorPropertyCriteria(InstanceProperties.code(), PropertyTypeEnum.STRING, propertyRestriction);
                case URN:
                    return buildSculptorPropertyCriteria(InstanceProperties.urn(), PropertyTypeEnum.STRING, propertyRestriction);
                case TITLE:
                    return buildSculptorPropertyCriteria(InstanceProperties.title().texts().label(), PropertyTypeEnum.STRING, propertyRestriction);
                case ACRONYM:
                    return buildSculptorPropertyCriteria(InstanceProperties.acronym().texts().label(), PropertyTypeEnum.STRING, propertyRestriction);
                case DATA_DESCRIPTION:
                    return buildSculptorPropertyCriteria(InstanceProperties.dataDescription().texts().label(), PropertyTypeEnum.STRING, propertyRestriction);
                case GEOGRAPHIC_GRANULARITY_URN:
                    return buildSculptorPropertyCriteria(InstanceProperties.geographicGranularity().urn(), PropertyTypeEnum.STRING, propertyRestriction);
                case TEMPORAL_GRANULARITY_URN:
                    return buildSculptorPropertyCriteria(InstanceProperties.temporalGranularity().urn(), PropertyTypeEnum.STRING, propertyRestriction);
                case INVENTORY_DATE:
                    return buildSculptorPropertyCriteria(
                            new LeafProperty<Instance>(InstanceProperties.inventoryDate().getName(), CoreCommonConstants.CRITERIA_DATETIME_COLUMN_DATETIME, true, Instance.class),
                            PropertyTypeEnum.DATE, propertyRestriction);
                default:
                    throw toRestExceptionParameterIncorrect(propertyNameCriteria.name());
            }
        }
        @SuppressWarnings("rawtypes")
        @Override
        public Property retrievePropertyOrder(MetamacRestOrder order) throws RestException {
            InstanceCriteriaPropertyOrder propertyNameCriteria = InstanceCriteriaPropertyOrder.fromValue(order.getPropertyName());
            switch (propertyNameCriteria) {
                case ID:
                    return InstanceProperties.code();
                default:
                    throw toRestExceptionParameterIncorrect(propertyNameCriteria.name());
            }
        }

        @SuppressWarnings("rawtypes")
        @Override
        public Property retrievePropertyOrderDefault() throws RestException {
            return InstanceProperties.id();
        }
    }

    private RestException toRestExceptionParameterIncorrect(String parameter) {
        org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.PARAMETER_INCORRECT, parameter);
        return new RestException(exception, Status.INTERNAL_SERVER_ERROR);
    }

    @SuppressWarnings("rawtypes")
    private SculptorPropertyCriteria buildSculptorPropertyCriteria(Property propertyEntity, PropertyTypeEnum propertyEntityType, MetamacRestQueryPropertyRestriction restPropertyRestriction) {
        return CriteriaUtils.buildSculptorPropertyCriteria(propertyEntity, propertyEntityType.name(), restPropertyRestriction, propertyValueRestToPropertyValueEntity);
    }

    private class PropertyValueRestToPropertyValueEntity implements PropertyValueRestToPropertyValueEntityInterface {

        @Override
        public Object restValueToEntityValue(String propertyName, String value, String propertyType) {
            if (value == null) {
                return null;
            }

            try {
                PropertyTypeEnum propertyTypeEnum = PropertyTypeEnum.valueOf(propertyType);
                switch (propertyTypeEnum) {
                    case STRING:
                        return value;
                    case DATE:
                        return CoreCommonUtil.transformISODateTimeLexicalRepresentationToDateTime(value).toDate();
                    case BOOLEAN:
                        return Boolean.valueOf(value);
                    case STATUS:
                        return StatusEnum.valueOf(value);
                    default:
                        throw toRestExceptionParameterIncorrect(propertyName);
                }
            } catch (Exception e) {
                logger.error("Error parsing Rest query", e);
                if (e instanceof RestException) {
                    throw (RestException) e;
                } else {
                    throw toRestExceptionParameterIncorrect(propertyName);
                }
            }
        }
    }
}

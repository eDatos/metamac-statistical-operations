package org.siemac.metamac.statistical.operations.core.serviceimpl;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.operations.core.domain.CollMethod;
import org.siemac.metamac.statistical.operations.core.domain.CollMethodProperties;
import org.siemac.metamac.statistical.operations.core.domain.CollMethodRepository;
import org.siemac.metamac.statistical.operations.core.domain.Cost;
import org.siemac.metamac.statistical.operations.core.domain.CostRepository;
import org.siemac.metamac.statistical.operations.core.domain.InstanceType;
import org.siemac.metamac.statistical.operations.core.domain.InstanceTypeRepository;
import org.siemac.metamac.statistical.operations.core.domain.OfficialityType;
import org.siemac.metamac.statistical.operations.core.domain.OfficialityTypeRepository;
import org.siemac.metamac.statistical.operations.core.domain.SurveySource;
import org.siemac.metamac.statistical.operations.core.domain.SurveySourceRepository;
import org.siemac.metamac.statistical.operations.core.domain.SurveyType;
import org.siemac.metamac.statistical.operations.core.domain.SurveyTypeRepository;
import org.siemac.metamac.statistical.operations.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.operations.core.exception.CollMethodNotFoundException;
import org.siemac.metamac.statistical.operations.core.exception.CostNotFoundException;
import org.siemac.metamac.statistical.operations.core.exception.InstanceTypeNotFoundException;
import org.siemac.metamac.statistical.operations.core.exception.OfficialityTypeNotFoundException;
import org.siemac.metamac.statistical.operations.core.exception.SurveySourceNotFoundException;
import org.siemac.metamac.statistical.operations.core.exception.SurveyTypeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of StatisticalOperationsListsService.
 */
@Service("statisticalOperationsListsService")
public class StatisticalOperationsListsServiceImpl extends org.siemac.metamac.statistical.operations.core.serviceimpl.StatisticalOperationsListsServiceImplBase {

    @Autowired
    private SurveyTypeRepository      surveyTypeRepository;
    @Autowired
    private InstanceTypeRepository    instanceTypeRepository;
    @Autowired
    private SurveySourceRepository    surveySourceRepository;
    @Autowired
    private OfficialityTypeRepository officialityTypeRepository;
    @Autowired
    private CollMethodRepository      collMethodRepository;
    @Autowired
    private CostRepository            costRepository;

    public StatisticalOperationsListsServiceImpl() {
    }

    /**
     * Delegates to {@link org.siemac.metamac.statistical.operations.lists.domain.SurveyTypeRepository#findById}
     */
    public SurveyType findSurveyTypeById(ServiceContext ctx, Long id) throws MetamacException {
        try {
            return surveyTypeRepository.findById(id);
        } catch (SurveyTypeNotFoundException e) {
            throw new MetamacException(ServiceExceptionType.SURVEY_TYPE_NOT_FOUND, id);
        }
    }

    /**
     * Delegates to {@link org.siemac.metamac.statistical.operations.lists.domain.SurveyTypeRepository#findAll}
     */
    public List<SurveyType> findAllSurveyTypes(ServiceContext ctx) {
        return surveyTypeRepository.findAll();
    }

    /**
     * Delegates to {@link org.siemac.metamac.statistical.operations.lists.domain.InstanceTypeRepository#findById}
     */
    public InstanceType findInstanceTypeById(ServiceContext ctx, Long id) throws MetamacException {
        try {
            return instanceTypeRepository.findById(id);
        } catch (InstanceTypeNotFoundException e) {
            throw new MetamacException(ServiceExceptionType.ACTIVITY_TYPE_NOT_FOUND, id);
        }
    }

    /**
     * Delegates to {@link org.siemac.metamac.statistical.operations.lists.domain.InstanceTypeRepository#findAll}
     */
    public List<InstanceType> findAllInstanceTypes(ServiceContext ctx) {
        return instanceTypeRepository.findAll();

    }

    /**
     * Delegates to {@link org.siemac.metamac.statistical.operations.lists.domain.SurveySourceRepository#findById}
     */
    public SurveySource findSurveySourceById(ServiceContext ctx, Long id) throws MetamacException {
        try {
            return surveySourceRepository.findById(id);
        } catch (SurveySourceNotFoundException e) {
            throw new MetamacException(ServiceExceptionType.SOURCE_DATA_NOT_FOUND, id);
        }
    }

    /**
     * Delegates to {@link org.siemac.metamac.statistical.operations.lists.domain.SurveySourceRepository#findAll}
     */
    public List<SurveySource> findAllSurveySources(ServiceContext ctx) {
        return surveySourceRepository.findAll();
    }

    /**
     * Delegates to {@link org.siemac.metamac.statistical.operations.lists.domain.OfficialityTypeRepository#findById}
     */
    public OfficialityType findOfficialityTypeById(ServiceContext ctx, Long id) throws MetamacException {
        try {
            return officialityTypeRepository.findById(id);
        } catch (OfficialityTypeNotFoundException e) {
            throw new MetamacException(ServiceExceptionType.OFFICIALITY_TYPE_NOT_FOUND, id);
        }
    }

    /**
     * Delegates to {@link org.siemac.metamac.statistical.operations.lists.domain.OfficialityTypeRepository#findAll}
     */
    public List<OfficialityType> findAllOfficialityTypes(ServiceContext ctx) {
        return officialityTypeRepository.findAll();
    }

    /**
     * Delegates to {@link org.siemac.metamac.statistical.operations.lists.domain.CollMethodRepository#findById}
     */
    public CollMethod findCollMethodById(ServiceContext ctx, Long id) throws MetamacException {
        try {
            return collMethodRepository.findById(id);
        } catch (CollMethodNotFoundException e) {
            throw new MetamacException(ServiceExceptionType.COLL_METHOD_NOT_FOUND, id);
        }
    }

    /**
     * Delegates to {@link org.siemac.metamac.statistical.operations.lists.domain.CollMethodsRepository#findByCondition}
     */
    public List<CollMethod> findAllCollMethods(ServiceContext ctx) {
        List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(CollMethod.class).orderBy(CollMethodProperties.visualisationOrder()).ascending().build();
        return collMethodRepository.findByCondition(conditions);
    }

    /**
     * Delegates to {@link org.siemac.metamac.statistical.operations.lists.domain.CostRepository#findById}
     */
    public Cost findCostById(ServiceContext ctx, Long id) throws MetamacException {
        try {
            return costRepository.findById(id);
        } catch (CostNotFoundException e) {
            throw new MetamacException(ServiceExceptionType.COST_NOT_FOUND, id);
        }
    }

    /**
     * Delegates to {@link org.siemac.metamac.statistical.operations.lists.domain.CostRepository#findAll}
     */
    public List<Cost> findAllCosts(ServiceContext ctx) {
        return costRepository.findAll();
    }
}

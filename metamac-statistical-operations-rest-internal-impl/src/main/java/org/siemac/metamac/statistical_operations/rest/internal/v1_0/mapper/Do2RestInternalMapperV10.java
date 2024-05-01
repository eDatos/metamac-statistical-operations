package org.siemac.metamac.statistical_operations.rest.internal.v1_0.mapper;

import java.util.List;
import java.util.Set;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.CollMethods;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Costs;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Families;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Family;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Instance;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.InstanceTypes;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Instances;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.OfficialityTypes;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Operation;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Operations;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.StatisticalOperationSources;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.StatisticalOperationTypes;
import org.siemac.metamac.statistical.operations.core.domain.CollMethod;
import org.siemac.metamac.statistical.operations.core.domain.Cost;
import org.siemac.metamac.statistical.operations.core.domain.InstanceType;
import org.siemac.metamac.statistical.operations.core.domain.OfficialityType;
import org.siemac.metamac.statistical.operations.core.domain.SurveySource;
import org.siemac.metamac.statistical.operations.core.domain.SurveyType;

public interface Do2RestInternalMapperV10 {

    // --------------
    // Operations
    // --------------
    Operation toOperation(org.siemac.metamac.statistical.operations.core.domain.Operation source) throws MetamacException;

    Operations toOperations(PagedResult<org.siemac.metamac.statistical.operations.core.domain.Operation> sources, String query, String orderBy, Integer limit, Set<String> parsedFields)
            throws MetamacException;

    Operations toOperationsByFamily(org.siemac.metamac.statistical.operations.core.domain.Family family, PagedResult<org.siemac.metamac.statistical.operations.core.domain.Operation> sources,
            String query, String orderBy, Integer limit) throws MetamacException;

    // --------------
    // Families
    // --------------
    Family toFamily(org.siemac.metamac.statistical.operations.core.domain.Family source);

    Families toFamilies(PagedResult<org.siemac.metamac.statistical.operations.core.domain.Family> sources, String query, String orderBy, Integer limit);

    Families toFamiliesByOperation(List<org.siemac.metamac.statistical.operations.core.domain.Family> sources);

    // --------------
    // Instances
    // --------------
    Instance toInstance(org.siemac.metamac.statistical.operations.core.domain.Instance source) throws MetamacException;

    Instances toInstances(org.siemac.metamac.statistical.operations.core.domain.Operation operation, PagedResult<org.siemac.metamac.statistical.operations.core.domain.Instance> sources, String query,
            String orderBy, Integer limit);

    // --------------
    // List of values
    // --------------
    StatisticalOperationTypes toStatisticalOperationTypes(List<SurveyType> sources);

    OfficialityTypes toOfficialityTypes(List<OfficialityType> entitiesResult);

    InstanceTypes toInstanceTypes(List<InstanceType> entitiesResult);

    StatisticalOperationSources toStatisticalOperationSources(List<SurveySource> entitiesResult);

    CollMethods toCollMethods(List<CollMethod> entitiesResult);

    Costs toCosts(List<Cost> entitiesResult);

    // -----------
    // Resources
    // -----------
    ResourceInternal toResource(org.siemac.metamac.statistical.operations.core.domain.Operation source) throws MetamacException;
    ResourceInternal toResource(org.siemac.metamac.statistical.operations.core.domain.Operation source, Set<String> parsedFields) throws MetamacException;

    // -----------
    // Links
    // -----------
    ResourceLink toOperationSelfLink(String operationCode);
    String toOperationManagementApplicationLink(String operationCode);
}

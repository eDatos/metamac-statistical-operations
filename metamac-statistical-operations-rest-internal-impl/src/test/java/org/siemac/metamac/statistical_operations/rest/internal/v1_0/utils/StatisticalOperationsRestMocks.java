package org.siemac.metamac.statistical_operations.rest.internal.v1_0.utils;

import static org.junit.Assert.fail;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.rest.common.test.utils.MetamacRestMocks;
import org.siemac.metamac.rest.common.v1_0.domain.ChildLinks;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.Item;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configuration;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.ClassSystems;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.CollMethods;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Costs;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.DataSharings;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Families;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Family;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.FreqColls;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.GeographicGranularities;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.PublicInformationSuppliers;
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
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Status;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.TemporalGranularities;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.UpdateFrequencies;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CategoryResourceInternal;
import org.siemac.metamac.srm.rest.common.SrmRestConstants;
import org.siemac.metamac.statistical_operations.rest.common.StatisticalOperationsRestConstants;
import org.siemac.metamac.statistical_operations.rest.internal.v1_0.service.StatisticalOperationsRestInternalFacadeV10Test;

public class StatisticalOperationsRestMocks {

    private final String statisticalOperationsApiBaseV10;
    private final String statisticalOperationsWebApplicationBase;
    private final String srmApiBaseV10;
    private final String srmWebApplicationBase;

    public StatisticalOperationsRestMocks(String statisticalOperationsApiBase, String statisticalOperationsWebApplicationBase, String srmApiBase, String srmWebApplicationBase) {
        this.statisticalOperationsApiBaseV10 = statisticalOperationsApiBase;
        this.statisticalOperationsWebApplicationBase = statisticalOperationsWebApplicationBase;
        this.srmApiBaseV10 = srmApiBase + "/v1.0";
        this.srmWebApplicationBase = srmWebApplicationBase;
    }

    public Operation mockOperation1() {
        return mockOperation("1", ProcStatus.INTERNALLY_PUBLISHED);
    }

    public Operation mockOperation2() {
        return mockOperation("2", ProcStatus.INTERNALLY_PUBLISHED);
    }

    public Operation mockOperation3() {
        return mockOperation("3", ProcStatus.EXTERNALLY_PUBLISHED);
    }

    public Operation mockOperation4() {
        return mockOperation("4", ProcStatus.INTERNALLY_PUBLISHED);
    }

    public Operation mockOperation5() {
        return mockOperation("5", ProcStatus.INTERNALLY_PUBLISHED);
    }

    public Operation mockOperation6() {
        return mockOperation("6", ProcStatus.EXTERNALLY_PUBLISHED);
    }

    public Family mockFamily1() {
        return mockFamily("1", ProcStatus.INTERNALLY_PUBLISHED);
    }

    public Family mockFamily2() {
        return mockFamily("2", ProcStatus.EXTERNALLY_PUBLISHED);
    }

    public Instance mockInstance1() {
        return mockInstance("1", "operation1", ProcStatus.INTERNALLY_PUBLISHED);
    }

    public Operations mockOperations(String limit, String offset) {
        Operations operations = new Operations();
        operations.setKind(StatisticalOperationsRestConstants.KIND_OPERATIONS);
        operations.setTotal(BigInteger.valueOf(10));
        if (limit == null && (offset == null || "0".equals(offset))) {
            operations.getOperations().add(mockOperation1Resource());
            operations.getOperations().add(mockOperation2Resource());
            operations.getOperations().add(mockOperation3Resource());
            operations.getOperations().add(mockOperation4Resource());
            operations.getOperations().add(mockOperation5Resource());
            operations.getOperations().add(mockOperation6Resource());
            operations.getOperations().add(mockOperation7Resource());
            operations.getOperations().add(mockOperation8Resource());
            operations.getOperations().add(mockOperation9Resource());
            operations.getOperations().add(mockOperation10Resource());
            operations.setOffset(BigInteger.valueOf(0));
            operations.setLimit(BigInteger.valueOf(25));
            operations.setFirstLink(null);
            operations.setPreviousLink(null);
            operations.setNextLink(null);
            operations.setLastLink(null);
        } else if ("10000".equals(limit) && offset == null) {
            operations.getOperations().add(mockOperation1Resource());
            operations.getOperations().add(mockOperation2Resource());
            operations.getOperations().add(mockOperation3Resource());
            operations.getOperations().add(mockOperation4Resource());
            operations.getOperations().add(mockOperation5Resource());
            operations.getOperations().add(mockOperation6Resource());
            operations.getOperations().add(mockOperation7Resource());
            operations.getOperations().add(mockOperation8Resource());
            operations.getOperations().add(mockOperation9Resource());
            operations.getOperations().add(mockOperation10Resource());
            operations.setOffset(BigInteger.valueOf(0));
            operations.setLimit(BigInteger.valueOf(1000));
            operations.setFirstLink(null);
            operations.setPreviousLink(null);
            operations.setNextLink(null);
            operations.setLastLink(null);
        } else if ("2".equals(limit) && "0".equals(offset)) {
            operations.getOperations().add(mockOperation1Resource());
            operations.getOperations().add(mockOperation2Resource());
            operations.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
            operations.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
            operations.setFirstLink(null);
            operations.setPreviousLink(null);
            operations.setNextLink(statisticalOperationsApiBaseV10 + "/operations?limit=2&offset=2");
            operations.setLastLink(statisticalOperationsApiBaseV10 + "/operations?limit=2&offset=8");
        } else if ("2".equals(limit) && "2".equals(offset)) {
            operations.getOperations().add(mockOperation3Resource());
            operations.getOperations().add(mockOperation4Resource());
            operations.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
            operations.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
            operations.setFirstLink(statisticalOperationsApiBaseV10 + "/operations?limit=2&offset=0");
            operations.setPreviousLink(statisticalOperationsApiBaseV10 + "/operations?limit=2&offset=0");
            operations.setNextLink(statisticalOperationsApiBaseV10 + "/operations?limit=2&offset=4");
            operations.setLastLink(statisticalOperationsApiBaseV10 + "/operations?limit=2&offset=8");
        } else if ("2".equals(limit) && "8".equals(offset)) {
            operations.getOperations().add(mockOperation9Resource());
            operations.getOperations().add(mockOperation10Resource());
            operations.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
            operations.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
            operations.setFirstLink(statisticalOperationsApiBaseV10 + "/operations?limit=2&offset=0");
            operations.setPreviousLink(statisticalOperationsApiBaseV10 + "/operations?limit=2&offset=6");
            operations.setNextLink(null);
            operations.setLastLink(null);
        } else if ("2".equals(limit) && "9".equals(offset)) {
            // no results
            operations.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
            operations.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
            operations.setFirstLink(statisticalOperationsApiBaseV10 + "/operations?limit=2&offset=0");
            operations.setPreviousLink(null);
            operations.setNextLink(null);
            operations.setLastLink(null);
        } else {
            fail("Limit or offset non supported. Limit = " + limit + ". Offset = " + offset);
        }
        return operations;
    }

    public Operations mockOperations(String limit, String offset, String query) {
        Operations operations = new Operations();
        operations.setKind(StatisticalOperationsRestConstants.KIND_OPERATIONS);
        String querySupported1 = StatisticalOperationsRestInternalFacadeV10Test.QUERY_OPERATION_ID_LIKE_1;
        String querySupported2 = StatisticalOperationsRestInternalFacadeV10Test.QUERY_OPERATION_ID_LIKE_1_AND_INDICATORS_SYSTEM;
        if (querySupported1.equals(query)) {
            operations.setTotal(BigInteger.valueOf(2));
            if (limit == null && (offset == null || "0".equals(offset))) {
                operations.getOperations().add(mockOperation1Resource());
                operations.getOperations().add(mockOperation10Resource());
                operations.setOffset(BigInteger.valueOf(0));
                operations.setLimit(BigInteger.valueOf(25));
                operations.setFirstLink(null);
                operations.setPreviousLink(null);
                operations.setNextLink(null);
                operations.setLastLink(null);
            } else if ("1".equals(limit) && "0".equals(offset)) {
                operations.getOperations().add(mockOperation1Resource());
                operations.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
                operations.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
                operations.setFirstLink(null);
                operations.setPreviousLink(null);
                operations.setNextLink(statisticalOperationsApiBaseV10 + "/operations?query=" + query + "&limit=1&offset=1");
                operations.setLastLink(statisticalOperationsApiBaseV10 + "/operations?query=" + query + "&limit=1&offset=1");
            } else if ("1".equals(limit) && "1".equals(offset)) {
                operations.getOperations().add(mockOperation3Resource());
                operations.getOperations().add(mockOperation4Resource());
                operations.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
                operations.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
                operations.setFirstLink(statisticalOperationsApiBaseV10 + "/operations?query=" + query + "&limit=1&offset=0");
                operations.setPreviousLink(statisticalOperationsApiBaseV10 + "/operations?query=" + query + "&limit=1&offset=0");
            } else {
                fail("Limit or offset non supported. Limit = " + limit + ". Offset = " + offset);
            }
        } else if (querySupported2.equals(query)) {
            operations.setTotal(BigInteger.valueOf(1));
            if (limit == null && (offset == null || "0".equals(offset))) {
                operations.getOperations().add(mockOperation1Resource());
                operations.setOffset(BigInteger.valueOf(0));
                operations.setLimit(BigInteger.valueOf(25));
                operations.setFirstLink(null);
                operations.setPreviousLink(null);
                operations.setNextLink(null);
                operations.setLastLink(null);
            } else {
                fail("Limit or offset non supported. Limit = " + limit + ". Offset = " + offset);
            }
        } else {
            fail("Query not supported = " + query);
        }
        return operations;
    }

    public Operations mockOperationsByFamily1(String limit, String offset) {
        Operations operations = new Operations();
        operations.setKind(StatisticalOperationsRestConstants.KIND_OPERATIONS);
        operations.setTotal(BigInteger.valueOf(6));
        if (limit == null && (offset == null || "0".equals(offset))) {
            operations.getOperations().add(mockOperation1Resource());
            operations.getOperations().add(mockOperation2Resource());
            operations.getOperations().add(mockOperation3Resource());
            operations.getOperations().add(mockOperation4Resource());
            operations.getOperations().add(mockOperation5Resource());
            operations.getOperations().add(mockOperation6Resource());
            operations.setOffset(BigInteger.valueOf(0));
            operations.setLimit(BigInteger.valueOf(25));
            operations.setFirstLink(null);
            operations.setPreviousLink(null);
            operations.setNextLink(null);
            operations.setLastLink(null);
        } else if ("10000".equals(limit) && offset == null) {
            operations.getOperations().add(mockOperation1Resource());
            operations.getOperations().add(mockOperation2Resource());
            operations.getOperations().add(mockOperation3Resource());
            operations.getOperations().add(mockOperation4Resource());
            operations.getOperations().add(mockOperation5Resource());
            operations.getOperations().add(mockOperation6Resource());
            operations.setOffset(BigInteger.valueOf(0));
            operations.setLimit(BigInteger.valueOf(1000));
            operations.setFirstLink(null);
            operations.setPreviousLink(null);
            operations.setNextLink(null);
            operations.setLastLink(null);
        } else if ("2".equals(limit) && "0".equals(offset)) {
            operations.getOperations().add(mockOperation1Resource());
            operations.getOperations().add(mockOperation2Resource());
            operations.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
            operations.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
            operations.setFirstLink(null);
            operations.setPreviousLink(null);
            operations.setNextLink(statisticalOperationsApiBaseV10 + "/families/family1/operations?limit=2&offset=2");
            operations.setLastLink(statisticalOperationsApiBaseV10 + "/families/family1/operations?limit=2&offset=4");
        } else if ("2".equals(limit) && "2".equals(offset)) {
            operations.getOperations().add(mockOperation3Resource());
            operations.getOperations().add(mockOperation4Resource());
            operations.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
            operations.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
            operations.setFirstLink(statisticalOperationsApiBaseV10 + "/families/family1/operations?limit=2&offset=0");
            operations.setPreviousLink(statisticalOperationsApiBaseV10 + "/families/family1/operations?limit=2&offset=0");
            operations.setNextLink(statisticalOperationsApiBaseV10 + "/families/family1/operations?limit=2&offset=4");
            operations.setLastLink(statisticalOperationsApiBaseV10 + "/families/family1/operations?limit=2&offset=4");
        } else if ("2".equals(limit) && "4".equals(offset)) {
            operations.getOperations().add(mockOperation5Resource());
            operations.getOperations().add(mockOperation6Resource());
            operations.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
            operations.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
            operations.setFirstLink(statisticalOperationsApiBaseV10 + "/families/family1/operations?limit=2&offset=0");
            operations.setPreviousLink(statisticalOperationsApiBaseV10 + "/families/family1/operations?limit=2&offset=2");
            operations.setNextLink(null);
            operations.setLastLink(null);
        } else if ("2".equals(limit) && "7".equals(offset)) {
            // no results
            operations.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
            operations.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
            operations.setFirstLink(statisticalOperationsApiBaseV10 + "/families/family1/operations?limit=2&offset=0");
            operations.setPreviousLink(null);
            operations.setNextLink(null);
            operations.setLastLink(null);
        } else {
            fail("Limit or offset non supported. Limit = " + limit + ". Offset = " + offset);
        }
        return operations;
    }

    public Operations mockOperationsByFamily2(String limit, String offset) {
        Operations operations = new Operations();
        operations.setKind(StatisticalOperationsRestConstants.KIND_OPERATIONS);
        operations.setTotal(BigInteger.valueOf(4));
        if ("1".equals(limit) && "2".equals(offset)) {
            operations.getOperations().add(mockOperation8Resource());
            operations.setOffset(BigInteger.valueOf(2));
            operations.setLimit(BigInteger.valueOf(1));
            operations.setFirstLink(statisticalOperationsApiBaseV10 + "/families/family2/operations?limit=1&offset=0");
            operations.setPreviousLink(statisticalOperationsApiBaseV10 + "/families/family2/operations?limit=1&offset=1");
            operations.setNextLink(statisticalOperationsApiBaseV10 + "/families/family2/operations?limit=1&offset=3");
            operations.setLastLink(statisticalOperationsApiBaseV10 + "/families/family2/operations?limit=1&offset=3");
        } else {
            fail("Limit or offset non supported. Limit = " + limit + ". Offset = " + offset);
        }
        return operations;
    }

    public Families mockFamilies(String limit, String offset) {
        Families families = new Families();
        families.setKind(StatisticalOperationsRestConstants.KIND_FAMILIES);
        families.setTotal(BigInteger.valueOf(5));
        if (limit == null && (offset == null || "0".equals(offset))) {
            families.getFamilies().add(mockFamily1Resource());
            families.getFamilies().add(mockFamily2Resource());
            families.getFamilies().add(mockFamily3Resource());
            families.getFamilies().add(mockFamily4Resource());
            families.getFamilies().add(mockFamily15Resource());
            families.setOffset(BigInteger.valueOf(0));
            families.setLimit(BigInteger.valueOf(25));
            families.setFirstLink(null);
            families.setPreviousLink(null);
            families.setNextLink(null);
            families.setLastLink(null);
        } else if ("10000".equals(limit) && offset == null) {
            families.getFamilies().add(mockFamily1Resource());
            families.getFamilies().add(mockFamily2Resource());
            families.getFamilies().add(mockFamily3Resource());
            families.getFamilies().add(mockFamily4Resource());
            families.getFamilies().add(mockFamily15Resource());
            families.setOffset(BigInteger.valueOf(0));
            families.setLimit(BigInteger.valueOf(1000));
            families.setFirstLink(null);
            families.setPreviousLink(null);
            families.setNextLink(null);
            families.setLastLink(null);
        } else if ("2".equals(limit) && "0".equals(offset)) {
            families.getFamilies().add(mockFamily1Resource());
            families.getFamilies().add(mockFamily2Resource());
            families.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
            families.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
            families.setFirstLink(null);
            families.setPreviousLink(null);
            families.setNextLink(statisticalOperationsApiBaseV10 + "/families?limit=2&offset=2");
            families.setLastLink(statisticalOperationsApiBaseV10 + "/families?limit=2&offset=4");
        } else if ("2".equals(limit) && "2".equals(offset)) {
            families.getFamilies().add(mockFamily3Resource());
            families.getFamilies().add(mockFamily4Resource());
            families.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
            families.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
            families.setFirstLink(statisticalOperationsApiBaseV10 + "/families?limit=2&offset=0");
            families.setPreviousLink(statisticalOperationsApiBaseV10 + "/families?limit=2&offset=0");
            families.setNextLink(statisticalOperationsApiBaseV10 + "/families?limit=2&offset=4");
            families.setLastLink(statisticalOperationsApiBaseV10 + "/families?limit=2&offset=4");
        } else if ("2".equals(limit) && "4".equals(offset)) {
            families.getFamilies().add(mockFamily15Resource());
            families.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
            families.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
            families.setFirstLink(statisticalOperationsApiBaseV10 + "/families?limit=2&offset=0");
            families.setPreviousLink(statisticalOperationsApiBaseV10 + "/families?limit=2&offset=2");
            families.setNextLink(null);
            families.setLastLink(null);
        } else if ("2".equals(limit) && "9".equals(offset)) {
            // no results
            families.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
            families.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
            families.setFirstLink(statisticalOperationsApiBaseV10 + "/families?limit=2&offset=0");
            families.setPreviousLink(null);
            families.setNextLink(null);
            families.setLastLink(null);
        } else {
            fail("Limit or offset non supported. Limit = " + limit + ". Offset = " + offset);
        }
        return families;
    }

    public Families mockFamilies(String limit, String offset, String query) {
        Families families = new Families();
        families.setKind(StatisticalOperationsRestConstants.KIND_FAMILIES);

        String querySupported1 = StatisticalOperationsRestInternalFacadeV10Test.QUERY_FAMILY_ID_LIKE_1;
        if (querySupported1.equals(query)) {
            families.setTotal(BigInteger.valueOf(2));
            if (limit == null && (offset == null || "0".equals(offset))) {
                families.getFamilies().add(mockFamily1Resource());
                families.getFamilies().add(mockFamily15Resource());
                families.setOffset(BigInteger.valueOf(0));
                families.setLimit(BigInteger.valueOf(25));
                families.setFirstLink(null);
                families.setPreviousLink(null);
                families.setNextLink(null);
                families.setLastLink(null);
            } else if ("1".equals(limit) && "0".equals(offset)) {
                families.getFamilies().add(mockFamily1Resource());
                families.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
                families.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
                families.setFirstLink(null);
                families.setPreviousLink(null);
                families.setNextLink(statisticalOperationsApiBaseV10 + "/families?query=" + query + "&limit=1&offset=1");
                families.setLastLink(statisticalOperationsApiBaseV10 + "/families?query=" + query + "&limit=1&offset=1");
            } else if ("1".equals(limit) && "1".equals(offset)) {
                families.getFamilies().add(mockFamily15Resource());
                families.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
                families.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
                families.setFirstLink(statisticalOperationsApiBaseV10 + "/families?query=" + query + "&limit=1&offset=0");
                families.setPreviousLink(statisticalOperationsApiBaseV10 + "/families?query=" + query + "&limit=1&offset=0");
                families.setNextLink(null);
                families.setLastLink(null);
            } else {
                fail("Limit or offset non supported. Limit = " + limit + ". Offset = " + offset);
            }
        } else {
            fail("Query not supported = " + query);
        }
        return families;
    }

    public Families mockFamiliesByOperation1() {
        Families families = new Families();
        families.setKind(StatisticalOperationsRestConstants.KIND_FAMILIES);
        families.setTotal(BigInteger.valueOf(2));
        families.getFamilies().add(mockFamily1Resource());
        families.getFamilies().add(mockFamily2Resource());
        return families;
    }

    public Instances mockInstancesByOperation1(String limit, String offset) {
        Instances instances = new Instances();
        instances.setKind(StatisticalOperationsRestConstants.KIND_INSTANCES);
        instances.setTotal(BigInteger.valueOf(5));
        String operation = StatisticalOperationsRestInternalFacadeV10Test.OPERATION_1;
        if (limit == null && (offset == null || "0".equals(offset))) {
            instances.getInstances().add(mockInstance1Resource());
            instances.getInstances().add(mockInstance2Resource());
            instances.getInstances().add(mockInstance3Resource());
            instances.getInstances().add(mockInstance4Resource());
            instances.getInstances().add(mockInstance15Resource());
            instances.setOffset(BigInteger.valueOf(0));
            instances.setLimit(BigInteger.valueOf(25));
            instances.setFirstLink(null);
            instances.setPreviousLink(null);
            instances.setNextLink(null);
            instances.setLastLink(null);
        } else if ("10000".equals(limit) && offset == null) {
            instances.getInstances().add(mockInstance1Resource());
            instances.getInstances().add(mockInstance2Resource());
            instances.getInstances().add(mockInstance3Resource());
            instances.getInstances().add(mockInstance4Resource());
            instances.getInstances().add(mockInstance15Resource());
            instances.setOffset(BigInteger.valueOf(0));
            instances.setLimit(BigInteger.valueOf(1000));
            instances.setFirstLink(null);
            instances.setPreviousLink(null);
            instances.setNextLink(null);
            instances.setLastLink(null);
        } else if ("2".equals(limit) && "0".equals(offset)) {
            instances.getInstances().add(mockInstance1Resource());
            instances.getInstances().add(mockInstance2Resource());
            instances.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
            instances.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
            instances.setFirstLink(null);
            instances.setPreviousLink(null);
            instances.setNextLink(statisticalOperationsApiBaseV10 + "/operations/" + operation + "/instances?limit=2&offset=2");
            instances.setLastLink(statisticalOperationsApiBaseV10 + "/operations/" + operation + "/instances?limit=2&offset=4");
        } else if ("2".equals(limit) && "2".equals(offset)) {
            instances.getInstances().add(mockInstance3Resource());
            instances.getInstances().add(mockInstance4Resource());
            instances.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
            instances.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
            instances.setFirstLink(statisticalOperationsApiBaseV10 + "/operations/" + operation + "/instances?limit=2&offset=0");
            instances.setPreviousLink(statisticalOperationsApiBaseV10 + "/operations/" + operation + "/instances?limit=2&offset=0");
            instances.setNextLink(statisticalOperationsApiBaseV10 + "/operations/" + operation + "/instances?limit=2&offset=4");
            instances.setLastLink(statisticalOperationsApiBaseV10 + "/operations/" + operation + "/instances?limit=2&offset=4");
        } else if ("2".equals(limit) && "4".equals(offset)) {
            instances.getInstances().add(mockInstance15Resource());
            instances.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
            instances.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
            instances.setFirstLink(statisticalOperationsApiBaseV10 + "/operations/" + operation + "/instances?limit=2&offset=0");
            instances.setPreviousLink(statisticalOperationsApiBaseV10 + "/operations/" + operation + "/instances?limit=2&offset=2");
            instances.setNextLink(null);
            instances.setLastLink(null);
        } else if ("2".equals(limit) && "9".equals(offset)) {
            // no results
            instances.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
            instances.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
            instances.setFirstLink(statisticalOperationsApiBaseV10 + "/operations/" + operation + "/instances?limit=2&offset=0");
            instances.setPreviousLink(null);
            instances.setNextLink(null);
            instances.setLastLink(null);
        } else {
            fail("Limit or offset non supported. Limit = " + limit + ". Offset = " + offset);
        }
        return instances;
    }

    public Instances mockInstancesByOperation1(String limit, String offset, String query) {
        Instances instances = new Instances();
        instances.setKind(StatisticalOperationsRestConstants.KIND_INSTANCES);
        String operation = StatisticalOperationsRestInternalFacadeV10Test.OPERATION_1;

        String querySupported1 = StatisticalOperationsRestInternalFacadeV10Test.QUERY_INSTANCE_ID_LIKE_1;
        if (querySupported1.equals(query)) {
            instances.setTotal(BigInteger.valueOf(2));
            if (limit == null && (offset == null || "0".equals(offset))) {
                instances.getInstances().add(mockInstance1Resource());
                instances.getInstances().add(mockInstance15Resource());
                instances.setOffset(BigInteger.valueOf(0));
                instances.setLimit(BigInteger.valueOf(25));
                instances.setFirstLink(null);
                instances.setPreviousLink(null);
                instances.setNextLink(null);
                instances.setLastLink(null);
            } else if ("1".equals(limit) && "0".equals(offset)) {
                instances.getInstances().add(mockInstance1Resource());
                instances.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
                instances.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
                instances.setFirstLink(null);
                instances.setPreviousLink(null);
                instances.setNextLink(statisticalOperationsApiBaseV10 + "/operations/" + operation + "/instances?query=" + query + "&limit=1&offset=1");
                instances.setLastLink(statisticalOperationsApiBaseV10 + "/operations/" + operation + "/instances?query=" + query + "&limit=1&offset=1");
            } else if ("1".equals(limit) && "1".equals(offset)) {
                instances.getInstances().add(mockInstance15Resource());
                instances.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
                instances.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
                instances.setFirstLink(statisticalOperationsApiBaseV10 + "/operations/" + operation + "/instances?query=" + query + "&limit=1&offset=0");
                instances.setPreviousLink(statisticalOperationsApiBaseV10 + "/operations/" + operation + "/instances?query=" + query + "&limit=1&offset=0");
                instances.setNextLink(null);
                instances.setLastLink(null);
            } else {
                fail("Limit or offset non supported. Limit = " + limit + ". Offset = " + offset);
            }
        } else {
            fail("Query not supported = " + query);
        }
        return instances;
    }

    public StatisticalOperationTypes mockStatisticalOperationTypes() {
        StatisticalOperationTypes statisticalOperationTypes = new StatisticalOperationTypes();
        statisticalOperationTypes.setKind(StatisticalOperationsRestConstants.KIND_STATISTICAL_OPERATION_TYPES);
        statisticalOperationTypes.setTotal(BigInteger.valueOf(2));
        statisticalOperationTypes.getStatisticalOperationTypes().add(mockItem("statisticalOperationType1"));
        statisticalOperationTypes.getStatisticalOperationTypes().add(mockItem("statisticalOperationType2"));
        return statisticalOperationTypes;
    }

    public OfficialityTypes mockOfficialityTypes() {
        OfficialityTypes officialityTypes = new OfficialityTypes();
        officialityTypes.setKind(StatisticalOperationsRestConstants.KIND_OFFICIALITY_TYPES);
        officialityTypes.setTotal(BigInteger.valueOf(3));
        officialityTypes.getOfficialityTypes().add(mockItem("officialityType1"));
        officialityTypes.getOfficialityTypes().add(mockItem("officialityType2"));
        officialityTypes.getOfficialityTypes().add(mockItem("officialityType3"));
        return officialityTypes;
    }

    public InstanceTypes mockInstanceTypes() {
        InstanceTypes instanceTypes = new InstanceTypes();
        instanceTypes.setKind(StatisticalOperationsRestConstants.KIND_INSTANCE_TYPES);
        instanceTypes.setTotal(BigInteger.valueOf(2));
        instanceTypes.getInstanceTypes().add(mockItem("instanceType1"));
        instanceTypes.getInstanceTypes().add(mockItem("instanceType2"));
        return instanceTypes;
    }

    public StatisticalOperationSources mockStatisticalOperationSources() {
        StatisticalOperationSources statisticalOperationSources = new StatisticalOperationSources();
        statisticalOperationSources.setKind(StatisticalOperationsRestConstants.KIND_STATISTICAL_OPERATION_SOURCES);
        statisticalOperationSources.setTotal(BigInteger.valueOf(2));
        statisticalOperationSources.getStatisticalOperationSources().add(mockItem("statisticalOperationSource1"));
        statisticalOperationSources.getStatisticalOperationSources().add(mockItem("statisticalOperationSource2"));
        return statisticalOperationSources;
    }

    public CollMethods mockCollMethods() {
        CollMethods collMethods = new CollMethods();
        collMethods.setKind(StatisticalOperationsRestConstants.KIND_COLL_METHODS);
        collMethods.setTotal(BigInteger.valueOf(2));
        collMethods.getCollMethods().add(mockItem("collMethod1"));
        collMethods.getCollMethods().add(mockItem("collMethod2"));
        return collMethods;
    }

    public Costs mockCosts() {
        Costs costs = new Costs();
        costs.setKind(StatisticalOperationsRestConstants.KIND_COSTS);
        costs.setTotal(BigInteger.valueOf(2));
        costs.getCosts().add(mockItem("cost1"));
        costs.getCosts().add(mockItem("cost2"));
        return costs;
    }

    private ResourceInternal mockOrganisationResourceFromExternalItemSrm(String id) {
        ResourceInternal resource = new ResourceInternal();
        resource.setId(id);
        resource.setUrn("urn:sdmx:org.sdmx.infomodel.base.Agency=SDMX:AGENCIES(1.0)." + id);
        resource.setKind("structuralResources#agency");
        resource.setSelfLink(MetamacRestMocks.mockResourceLink(resource.getKind(), srmApiBaseV10 + "/agencyschemes/SDMX/AGENCIES/1.0/agencies/" + id));
        resource.setManagementAppLink(srmWebApplicationBase + "/#structuralResources/organisationSchemes/organisationScheme;type=AGENCY_SCHEME;id=SDMX:AGENCIES(1.0)/organisation;id=" + id);
        resource.setName(mockInternationalString("es", id + " en Español"));
        return resource;
    }

    private org.siemac.metamac.rest.common_metadata.v1_0.domain.ResourceInternal mockCommonMetadataResourceInternal(String id) {
        org.siemac.metamac.rest.common_metadata.v1_0.domain.ResourceInternal resource = new org.siemac.metamac.rest.common_metadata.v1_0.domain.ResourceInternal();
        resource.setId(id);
        resource.setUrn("urn:sdmx:org.sdmx.infomodel.base.Agency=SDMX:AGENCIES(1.0)." + id);
        resource.setKind("structuralResources#agency");
        resource.setSelfLink(MetamacRestMocks.mockResourceLink(resource.getKind(), srmApiBaseV10 + "/agencyschemes/SDMX/AGENCIES/1.0/agencies/" + id));
        resource.setManagementAppLink(srmWebApplicationBase + "/#structuralResources/organisationSchemes/organisationScheme;type=AGENCY_SCHEME;id=SDMX:AGENCIES(1.0)/organisation;id=" + id);
        resource.setName(mockInternationalString("es", id + " en Español"));
        return resource;
    }

    public Configuration mockExternalApiCommonMetadataRetrieveConfiguration1ById() {
        Configuration configuration = new Configuration();
        configuration.setContact(mockCommonMetadataResourceInternal("contact1"));
        configuration.setLegalActs(mockInternationalStringMetadata("legalActs", "common1"));
        configuration.setDataSharing(mockInternationalStringMetadata("dataSharing", "common1"));
        configuration.setConfPolicy(mockInternationalStringMetadata("confidentialityPolicy", "1"));
        configuration.setConfDataTreatment(mockInternationalStringMetadata("confidentialityDataTreatment", "1"));
        return configuration;
    }

    public List<org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptResourceInternal> mockSrmInternalApiRetrieveConceptsByConceptScheme(String conceptSchemeId) {
        List<org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptResourceInternal> concepts = new ArrayList<org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptResourceInternal>();
        concepts.add(mockSrmConcept(conceptSchemeId, "concept1"));
        concepts.add(mockSrmConcept(conceptSchemeId, "concept2"));
        return concepts;
    }

    private ResourceInternal mockOperationResource(String subId) {
        String operationId = !subId.contains("operation") ? "operation" + subId : subId;
        ResourceInternal resource = new ResourceInternal();
        resource.setId(operationId);
        resource.setUrn("urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Operation=" + operationId);
        resource.setKind(StatisticalOperationsRestConstants.KIND_OPERATION);
        resource.setSelfLink(MetamacRestMocks.mockResourceLink(resource.getKind(), statisticalOperationsApiBaseV10 + "/operations/" + operationId));
        resource.setManagementAppLink(statisticalOperationsWebApplicationBase + "/#operations/operation;id=" + operationId);
        resource.setName(mockInternationalString("es", operationId + " en Español"));
        return resource;
    }

    private ResourceInternal mockFamilyResource(String subId) {
        String familyId = "family" + subId;
        ResourceInternal resource = new ResourceInternal();
        resource.setId(familyId);
        resource.setUrn("urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Family=" + familyId);
        resource.setKind(StatisticalOperationsRestConstants.KIND_FAMILY);
        resource.setSelfLink(MetamacRestMocks.mockResourceLink(resource.getKind(), statisticalOperationsApiBaseV10 + "/families/" + familyId));
        resource.setManagementAppLink(statisticalOperationsWebApplicationBase + "/#families/family;id=" + familyId);
        resource.setName(mockInternationalString("es", familyId + " en Español"));
        return resource;
    }

    private ResourceInternal mockInstanceResource(String operationId, String subId) {
        String instanceId = "instance" + subId;
        ResourceInternal resource = new ResourceInternal();
        resource.setId(instanceId);
        resource.setUrn("urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Instance=" + operationId + "." + instanceId);
        resource.setKind(StatisticalOperationsRestConstants.KIND_INSTANCE);
        resource.setSelfLink(MetamacRestMocks.mockResourceLink(resource.getKind(), statisticalOperationsApiBaseV10 + "/operations/" + operationId + "/instances/" + instanceId));
        resource.setManagementAppLink(statisticalOperationsWebApplicationBase + "/#operations/operation;id=" + operationId + "/instance;id=" + instanceId);
        resource.setName(mockInternationalString("es", instanceId + " en Español"));
        return resource;
    }

    private Operation mockOperation(String subId, ProcStatus procStatus) {
        Operation operation = new Operation();
        operation.setId("operation" + subId);
        operation.setUrn("urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Operation=" + operation.getId());
        operation.setKind(StatisticalOperationsRestConstants.KIND_OPERATION);
        operation.setSelfLink(MetamacRestMocks.mockResourceLink(StatisticalOperationsRestConstants.KIND_OPERATION, statisticalOperationsApiBaseV10 + "/operations/operation" + subId));
        operation.setName(mockInternationalStringMetadata("operation", subId));
        operation.setAcronym(mockInternationalStringMetadata("acronym", subId));
        operation.setSubjectArea(mockResourceInternalFromCategoryResourceInternal("subjectArea1"));
        operation.setSecondarySubjectAreas(new SecondarySubjectAreas());
        operation.getSecondarySubjectAreas().setTotal(BigInteger.valueOf(3));
        operation.getSecondarySubjectAreas().setKind(SrmRestConstants.KIND_CATEGORIES);
        operation.getSecondarySubjectAreas().getSecondarySubjectAreas().add(mockResourceInternalFromCategoryResourceInternal("secundarySubjectArea1"));
        operation.getSecondarySubjectAreas().getSecondarySubjectAreas().add(mockResourceInternalFromCategoryResourceInternal("secundarySubjectArea22"));
        operation.getSecondarySubjectAreas().getSecondarySubjectAreas().add(mockResourceInternalFromCategoryResourceInternal("secundarySubjectArea333"));
        operation.setObjective(mockInternationalStringMetadata("objetive", subId));
        operation.setDescription(mockInternationalStringMetadata("description", subId));
        operation.setStatisticalOperationType(mockItem("statisticalOperationIdentifier"));
        operation.setOfficialityType(mockItem("officialityType"));
        operation.setIndicatorSystem(Boolean.TRUE);
        operation.setProducers(new Producers());
        operation.getProducers().setTotal(BigInteger.valueOf(2));
        operation.getProducers().setKind(SrmRestConstants.KIND_ORGANISATION_UNITS);
        operation.getProducers().getProducers().add(mockResourceFromExternalItemSrm("producer1", "producers", "structuralResources#agency"));
        operation.getProducers().getProducers().add(mockResourceFromExternalItemSrm("producer22", "producers", "structuralResources#agency"));
        operation.setResponsibles(new Responsibles());
        operation.getResponsibles().setTotal(BigInteger.valueOf(3));
        operation.getResponsibles().setKind(SrmRestConstants.KIND_ORGANISATION_UNITS);
        operation.getResponsibles().getResponsibles().add(mockResourceFromExternalItemSrm("responsible1", "responsibles", "structuralResources#agency"));
        operation.getResponsibles().getResponsibles().add(mockResourceFromExternalItemSrm("responsible22", "responsibles", "structuralResources#agency"));
        operation.getResponsibles().getResponsibles().add(mockResourceFromExternalItemSrm("responsible333", "responsibles", "structuralResources#agency"));
        operation.setContributors(new Contributors());
        operation.getContributors().setTotal(BigInteger.valueOf(2));
        operation.getContributors().setKind(SrmRestConstants.KIND_ORGANISATIONS);
        operation.getContributors().getContributors().add(mockResourceFromExternalItemSrm("contributor1", "contributors", "structuralResources#agency"));
        operation.getContributors().getContributors().add(mockResourceFromExternalItemSrm("contributor22", "contributors", "structuralResources#agency"));
        operation.setCreatedDate(new DateTime(2011, 1, 2, 15, 14, 13, 0).toDate());
        operation.setInternalInventoryDate(new DateTime(2012, 12, 1, 13, 15, 14, 0).toDate());
        operation.setCurrentlyActive(Boolean.FALSE);
        operation.setStatus(Status.DESIGN);
        operation.setProcStatus(procStatus);
        operation.setPublishers(new Publishers());
        operation.getPublishers().setTotal(BigInteger.valueOf(3));
        operation.getPublishers().setKind(SrmRestConstants.KIND_ORGANISATION_UNITS);
        operation.getPublishers().getPublishers().add(mockResourceFromExternalItemSrm("publisher1", "publishers", "structuralResources#agency"));
        operation.getPublishers().getPublishers().add(mockResourceFromExternalItemSrm("publisher22", "publishers", "structuralResources#agency"));
        operation.getPublishers().getPublishers().add(mockResourceFromExternalItemSrm("publisher333", "publishers", "structuralResources#agency"));
        operation.setRelPolUsAc(mockInternationalStringMetadata("relPolUsAc", subId));
        operation.setReleaseCalendar(Boolean.TRUE);
        operation.setReleaseCalendarAccess("http://releaseCalendarAccess1");
        operation.setUpdateFrequencies(new UpdateFrequencies());
        operation.getUpdateFrequencies().setTotal(BigInteger.valueOf(4));
        operation.getUpdateFrequencies().setKind(SrmRestConstants.KIND_CODES);
        operation.getUpdateFrequencies().getUpdateFrequencies().add(mockResourceFromExternalItemSrm("updateFrequency1", "updateFrequencies", "structuralResources#code"));
        operation.getUpdateFrequencies().getUpdateFrequencies().add(mockResourceFromExternalItemSrm("updateFrequency22", "updateFrequencies", "structuralResources#code"));
        operation.getUpdateFrequencies().getUpdateFrequencies().add(mockResourceFromExternalItemSrm("updateFrequency333", "updateFrequencies", "structuralResources#code"));
        operation.getUpdateFrequencies().getUpdateFrequencies().add(mockResourceFromExternalItemSrm("updateFrequency4444", "updateFrequencies", "structuralResources#code"));
        operation.setCurrentInternalInstance(mockInstanceResource(operation.getId(), "22"));
        operation.setCurrentInstance(mockInstanceResource(operation.getId(), "333"));
        operation.setInventoryDate(new DateTime(2013, 2, 4, 13, 15, 14, 0).toDate());
        operation.setRevPolicy(mockInternationalStringMetadata("revPolicy", subId));
        operation.setRevPractice(mockInternationalStringMetadata("revPractice", subId));
        operation.setContact(mockOrganisationResourceFromExternalItemSrm("contact1"));
        operation.setLegalActs(new LegalActs());
        operation.getLegalActs().setTotal(BigInteger.valueOf(2));
        operation.getLegalActs().getLegalActs().add(mockInternationalStringMetadata("legalActs", "common1"));
        operation.getLegalActs().getLegalActs().add(mockInternationalStringMetadata("legalActs", "specific1"));
        operation.setDataSharings(new DataSharings());
        operation.getDataSharings().setTotal(BigInteger.valueOf(2));
        operation.getDataSharings().getDataSharings().add(mockInternationalStringMetadata("dataSharing", "common1"));
        operation.getDataSharings().getDataSharings().add(mockInternationalStringMetadata("dataSharing", "specific1"));
        operation.setConfidentialityPolicy(mockInternationalStringMetadata("confidentialityPolicy", "1"));
        operation.setConfidentialityDataTreatment(mockInternationalStringMetadata("confidentialityDataTreatment", "1"));
        operation.setComment(mockInternationalStringMetadata("comment", subId));
        operation.setNotes(mockInternationalStringMetadata("notes", subId));
        operation.setParentLink(MetamacRestMocks.mockResourceLink(StatisticalOperationsRestConstants.KIND_OPERATIONS, statisticalOperationsApiBaseV10 + "/operations"));
        operation.setChildLinks(new ChildLinks());
        operation.getChildLinks().setTotal(BigInteger.valueOf(2));
        operation.getChildLinks().getChildLinks()
                .add(MetamacRestMocks.mockResourceLink(StatisticalOperationsRestConstants.KIND_FAMILIES, statisticalOperationsApiBaseV10 + "/operations/operation" + subId + "/families"));
        operation.getChildLinks().getChildLinks()
                .add(MetamacRestMocks.mockResourceLink(StatisticalOperationsRestConstants.KIND_INSTANCES, statisticalOperationsApiBaseV10 + "/operations/operation" + subId + "/instances"));
        operation.setManagementAppLink(statisticalOperationsWebApplicationBase + "/#operations/operation;id=" + operation.getId());
        return operation;
    }

    private Family mockFamily(String subId, ProcStatus procStatus) {
        Family family = new Family();
        family.setId("family" + subId);
        family.setUrn("urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Family=" + family.getId());
        family.setKind(StatisticalOperationsRestConstants.KIND_FAMILY);
        family.setSelfLink(MetamacRestMocks.mockResourceLink(StatisticalOperationsRestConstants.KIND_FAMILY, statisticalOperationsApiBaseV10 + "/families/family" + subId));
        family.setName(mockInternationalStringMetadata("family", subId));
        family.setAcronym(mockInternationalStringMetadata("acronym", subId));
        family.setDescription(mockInternationalStringMetadata("description", subId));
        family.setCreatedDate(new DateTime(2011, 1, 2, 15, 14, 13, 0).toDate());
        family.setInternalInventoryDate(new DateTime(2012, 12, 1, 13, 15, 14, 0).toDate());
        family.setProcStatus(procStatus);
        family.setInventoryDate(new DateTime(2013, 2, 4, 13, 15, 14, 0).toDate());
        family.setParentLink(MetamacRestMocks.mockResourceLink(StatisticalOperationsRestConstants.KIND_FAMILIES, statisticalOperationsApiBaseV10 + "/families"));
        family.setChildLinks(new ChildLinks());
        family.getChildLinks().setTotal(BigInteger.valueOf(1));
        family.getChildLinks().getChildLinks()
                .add(MetamacRestMocks.mockResourceLink(StatisticalOperationsRestConstants.KIND_OPERATIONS, statisticalOperationsApiBaseV10 + "/families/family" + subId + "/operations"));
        family.setManagementAppLink(statisticalOperationsWebApplicationBase + "/#families/family;id=" + family.getId());
        return family;
    }

    private Instance mockInstance(String subId, String operation, ProcStatus procStatus) {

        Instance instance = new Instance();
        instance.setId("instance" + subId);
        instance.setUrn("urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Instance=" + operation + "." + instance.getId());
        instance.setKind(StatisticalOperationsRestConstants.KIND_INSTANCE);
        instance.setSelfLink(
                MetamacRestMocks.mockResourceLink(StatisticalOperationsRestConstants.KIND_INSTANCE, statisticalOperationsApiBaseV10 + "/operations/" + operation + "/instances/instance" + subId));
        instance.setName(mockInternationalStringMetadata("instance", subId));
        instance.setAcronym(mockInternationalStringMetadata("acronym", subId));
        instance.setStatisticalOperation(mockOperationResource(operation));
        instance.setPredecessor(mockInstanceResource(operation, "333"));
        instance.setSuccessor(mockInstanceResource(operation, "22"));
        instance.setDataDescription(mockInternationalStringMetadata("dataDescription", subId));
        instance.setStatisticalPopulation(mockInternationalStringMetadata("statisticalPopulation", subId));
        instance.setStatisticalUnits(new StatisticalUnits());
        instance.getStatisticalUnits().setTotal(BigInteger.valueOf(2));
        instance.getStatisticalUnits().setKind(SrmRestConstants.KIND_CONCEPTS);
        instance.getStatisticalUnits().getStatisticalUnits().add(mockResourceFromExternalItemSrm("statisticalUnit1", "statisticalUnits", "structuralResources#concept"));
        instance.getStatisticalUnits().getStatisticalUnits().add(mockResourceFromExternalItemSrm("statisticalUnit22", "statisticalUnits", "structuralResources#concept"));
        instance.setGeographicGranularity(new GeographicGranularities());
        instance.getGeographicGranularity().setTotal(BigInteger.valueOf(2));
        instance.getGeographicGranularity().setKind(SrmRestConstants.KIND_CODES);
        instance.getGeographicGranularity().getGeographicGranularities().add(mockResourceFromExternalItemSrm("geographicGranularity01", "codelists", "structuralResources#codelist"));
        instance.getGeographicGranularity().getGeographicGranularities().add(mockResourceFromExternalItemSrm("geographicGranularity02", "codelists", "structuralResources#codelist"));
        instance.setGeographicComparability(mockInternationalStringMetadata("geographicComparability", subId));
        instance.setTemporalGranularity(new TemporalGranularities());
        instance.getTemporalGranularity().setTotal(BigInteger.valueOf(2));
        instance.getTemporalGranularity().setKind(SrmRestConstants.KIND_CODES);
        instance.getTemporalGranularity().getTemporalGranularities().add(mockResourceFromExternalItemSrm("temporalGranularity01", "codelists", "structuralResources#codelist"));
        instance.getTemporalGranularity().getTemporalGranularities().add(mockResourceFromExternalItemSrm("temporalGranularity02", "codelists", "structuralResources#codelist"));
        instance.setTemporalComparability(mockInternationalStringMetadata("temporalComparability", subId));
        instance.setBasePeriod("2012");
        instance.setMeasures(new Measures());
        instance.getMeasures().setTotal(BigInteger.valueOf(3));
        instance.getMeasures().setKind(SrmRestConstants.KIND_CONCEPTS);
        instance.getMeasures().getMeasures().add(mockResourceFromExternalItemSrm("measure1", "concepts", "structuralResources#concept"));
        instance.getMeasures().getMeasures().add(mockResourceFromExternalItemSrm("measure2concept1", "concepts", "structuralResources#concept"));
        instance.getMeasures().getMeasures().add(mockResourceFromExternalItemSrm("measure2concept2", "concepts", "structuralResources#concept"));
        instance.setStatConcDefsDescription(mockInternationalStringMetadata("statConcDef", subId));
        instance.setStatConcDefs(new StatConcDefs());
        instance.getStatConcDefs().setTotal(BigInteger.valueOf(5));
        instance.getStatConcDefs().setKind(SrmRestConstants.KIND_CONCEPTS);
        instance.getStatConcDefs().getStatConcDefs().add(mockResourceFromExternalItemSrm("statConcDefList1", "concepts", "structuralResources#concept"));
        instance.getStatConcDefs().getStatConcDefs().add(mockResourceFromExternalItemSrm("statConcDefList22concept1", "concepts", "structuralResources#concept"));
        instance.getStatConcDefs().getStatConcDefs().add(mockResourceFromExternalItemSrm("statConcDefList22concept2", "concepts", "structuralResources#concept"));
        instance.getStatConcDefs().getStatConcDefs().add(mockResourceFromExternalItemSrm("statConcDefList333concept1", "concepts", "structuralResources#concept"));
        instance.getStatConcDefs().getStatConcDefs().add(mockResourceFromExternalItemSrm("statConcDefList333concept2", "concepts", "structuralResources#concept"));
        instance.setClassSystemsDescription(mockInternationalStringMetadata("classSystem", subId));
        instance.setClassSystems(new ClassSystems());
        instance.getClassSystems().setTotal(BigInteger.valueOf(2));
        instance.getClassSystems().setKind(SrmRestConstants.KIND_CODELISTS);
        instance.getClassSystems().getClassSystems().add(mockResourceFromExternalItemSrm("classSystemList1", "classSystemLists", "structuralResources#codelist"));
        instance.getClassSystems().getClassSystems().add(mockResourceFromExternalItemSrm("classSystemList22", "classSystemLists", "structuralResources#codelist"));
        instance.setInstanceType(mockItem("instanceType1"));
        instance.setCreatedDate(new DateTime(2011, 1, 2, 15, 14, 13, 0).toDate());
        instance.setInternalInventoryDate(new DateTime(2012, 12, 1, 13, 15, 14, 0).toDate());
        instance.setProcStatus(procStatus);
        instance.setDocMethod(mockInternationalStringMetadata("docMethod", subId));
        instance.setStatisticalOperationSource(mockItem("statisticalOperationSource1"));
        instance.setCollMethod(mockItem("collMethod1"));
        instance.setPublicInformationSuppliers(new PublicInformationSuppliers());
        instance.getPublicInformationSuppliers().setTotal(BigInteger.valueOf(1));
        instance.getPublicInformationSuppliers().setKind(SrmRestConstants.KIND_DATA_PROVIDERS);
        instance.getPublicInformationSuppliers().getPublicInformationSuppliers().add(mockResourceFromExternalItemSrm("publicInformationSupplier1", "publicInformationSuppliers", "structuralResources#concept"));
        instance.setFreqColls(new FreqColls());
        instance.getFreqColls().setTotal(BigInteger.valueOf(2));
        instance.getFreqColls().setKind(SrmRestConstants.KIND_CODES);
        instance.getFreqColls().getFreqColls().add(mockResourceFromExternalItemSrm("freqColl1", "freqColls", "structuralResources#categoryScheme"));
        instance.getFreqColls().getFreqColls().add(mockResourceFromExternalItemSrm("freqColl22", "freqColls", "structuralResources#categoryScheme"));
        instance.setDataValidation(mockInternationalStringMetadata("dataValidation", subId));
        instance.setDataCompilation(mockInternationalStringMetadata("dataCompilation", subId));
        instance.setAdjustment(mockInternationalStringMetadata("adjustment", subId));
        instance.setSeasonalAdjustment(mockInternationalStringMetadata("SeasonalAdjustment", subId));
        instance.setCostBurden(mockInternationalStringMetadata("costBurden", subId));
        instance.setCosts(new Costs());
        instance.getCosts().setTotal(BigInteger.valueOf(4));
        instance.getCosts().setKind(StatisticalOperationsRestConstants.KIND_COSTS);
        instance.getCosts().getCosts().add(mockItem("cost1"));
        instance.getCosts().getCosts().add(mockItem("cost22"));
        instance.getCosts().getCosts().add(mockItem("cost333"));
        instance.getCosts().getCosts().add(mockItem("cost4444"));
        instance.setInventoryDate(new DateTime(2013, 2, 4, 13, 15, 14, 0).toDate());
        instance.setQualityDoc(mockInternationalStringMetadata("qualityDoc", subId));
        instance.setQualityAssure(mockInternationalStringMetadata("qualityAssure", subId));
        instance.setQualityAssmnt(mockInternationalStringMetadata("qualityAssmnt", subId));
        instance.setUserNeeds(mockInternationalStringMetadata("userNeeds", subId));
        instance.setUserSat(mockInternationalStringMetadata("userSat", subId));
        instance.setCompleteness(mockInternationalStringMetadata("completeness", subId));
        instance.setTimeliness(mockInternationalStringMetadata("timeliness", subId));
        instance.setPunctuality(mockInternationalStringMetadata("punctuality", subId));
        instance.setAccuracyOverall(mockInternationalStringMetadata("accuracyOverall", subId));
        instance.setSamplingErr(mockInternationalStringMetadata("samplingErr", subId));
        instance.setNonsamplingErr(mockInternationalStringMetadata("nonsamplingErr", subId));
        instance.setCoverageErr(mockInternationalStringMetadata("coverageErr", subId));
        instance.setMeasurementErr(mockInternationalStringMetadata("measurementErr", subId));
        instance.setProcessingErr(mockInternationalStringMetadata("ProcessingErr", subId));
        instance.setNonResponseErr(mockInternationalStringMetadata("nonsResponseErr", subId));
        instance.setModelErr(mockInternationalStringMetadata("modelErr", subId));
        instance.setCoherXDom(mockInternationalStringMetadata("coherXDom", subId));
        instance.setCoherSubanualAnual(mockInternationalStringMetadata("coherAnualSubanual", subId));
        instance.setCoherNationalAccounts(mockInternationalStringMetadata("coherNationalAccounts", subId));
        instance.setCoherInternal(mockInternationalStringMetadata("coherInternal", subId));
        instance.setComment(mockInternationalStringMetadata("comment", subId));
        instance.setNotes(mockInternationalStringMetadata("notes", subId));
        instance.setParentLink(MetamacRestMocks.mockResourceLink(StatisticalOperationsRestConstants.KIND_OPERATION, statisticalOperationsApiBaseV10 + "/operations/" + operation));
        // no children
        instance.setManagementAppLink(statisticalOperationsWebApplicationBase + "/#operations/operation;id=" + operation + "/instance;id=" + instance.getId());
        return instance;
    }

    private ResourceInternal mockOperation1Resource() {
        return mockOperationResource("1");
    }

    private ResourceInternal mockOperation2Resource() {
        return mockOperationResource("2");
    }

    private ResourceInternal mockOperation3Resource() {
        return mockOperationResource("3");
    }

    private ResourceInternal mockOperation4Resource() {
        return mockOperationResource("4");
    }

    private ResourceInternal mockOperation5Resource() {
        return mockOperationResource("5");
    }

    private ResourceInternal mockOperation6Resource() {
        return mockOperationResource("6");
    }

    private ResourceInternal mockOperation7Resource() {
        return mockOperationResource("7");
    }

    private ResourceInternal mockOperation8Resource() {
        return mockOperationResource("8");
    }

    private ResourceInternal mockOperation9Resource() {
        return mockOperationResource("9");
    }

    private ResourceInternal mockOperation10Resource() {
        return mockOperationResource("10");
    }

    private ResourceInternal mockFamily1Resource() {
        return mockFamilyResource("1");
    }

    private ResourceInternal mockFamily2Resource() {
        return mockFamilyResource("2");
    }

    private ResourceInternal mockFamily3Resource() {
        return mockFamilyResource("3");
    }

    private ResourceInternal mockFamily4Resource() {
        return mockFamilyResource("4");
    }

    private ResourceInternal mockFamily15Resource() {
        return mockFamilyResource("15");
    }

    private ResourceInternal mockInstance1Resource() {
        return mockInstanceResource(StatisticalOperationsRestInternalFacadeV10Test.OPERATION_1, "1");
    }

    private ResourceInternal mockInstance2Resource() {
        return mockInstanceResource(StatisticalOperationsRestInternalFacadeV10Test.OPERATION_1, "2");
    }

    private ResourceInternal mockInstance3Resource() {
        return mockInstanceResource(StatisticalOperationsRestInternalFacadeV10Test.OPERATION_1, "3");
    }

    private ResourceInternal mockInstance4Resource() {
        return mockInstanceResource(StatisticalOperationsRestInternalFacadeV10Test.OPERATION_1, "4");
    }

    private ResourceInternal mockInstance15Resource() {
        return mockInstanceResource(StatisticalOperationsRestInternalFacadeV10Test.OPERATION_1, "15");
    }

    private InternationalString mockInternationalStringMetadata(String metadata, String subsubTitle) {
        String subTitle = subsubTitle != null ? metadata + subsubTitle : metadata;

        // Note: only one locale, because it is a set and change orden in xml responses, so only there are svn differences
        String locale = "es";
        String label = subTitle + " en Español";
        return mockInternationalString(locale, label);
    }

    private InternationalString mockInternationalString(String locale, String label) {
        // Note: only one locale, because it is a set and change orden in xml responses, so only there are svn differences
        InternationalString internationalString = new InternationalString();
        LocalisedString internationalStringLocale1 = new LocalisedString();
        internationalStringLocale1.setLang(locale);
        internationalStringLocale1.setValue(label);
        internationalString.getTexts().add(internationalStringLocale1);
        return internationalString;
    }

    private ResourceInternal mockResourceFromExternalItemSrm(String id, String sampleResourceSubpath, String kind) {
        return mockResourceFromExternalItem(id, srmApiBaseV10, srmWebApplicationBase, sampleResourceSubpath, kind);
    }

    private ResourceInternal mockResourceFromExternalItem(String id, String endpointApi, String managementApp, String sampleResourceSubpath, String kind) {
        ResourceInternal resource = new ResourceInternal();
        resource.setId(id);
        resource.setUrn("urn:" + id);
        resource.setKind(kind);
        resource.setSelfLink(MetamacRestMocks.mockResourceLink(kind, endpointApi + "/" + sampleResourceSubpath + "/" + id));
        resource.setManagementAppLink(managementApp + "/" + sampleResourceSubpath + "/" + id);
        resource.setName(mockInternationalString("es", id + " en Español"));
        if (TypeExternalArtefactsEnum.AGENCY.getValue().equals(kind) || TypeExternalArtefactsEnum.CATEGORY.getValue().equals(kind)) {
            resource.setNestedId(id + "Nested");
        }
        return resource;
    }

    private Item mockItem(String id) {
        Item item = new Item();
        item.setId(id);
        item.setName(mockInternationalString("es", id + " en Español"));
        return item;
    }

    private org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptResourceInternal mockSrmConcept(String conceptSchemeId, String conceptId) {
        org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptResourceInternal resource = new org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptResourceInternal();
        resource.setId(conceptSchemeId + conceptId);
        resource.setUrn("urn:" + resource.getId());
        resource.setKind(SrmRestConstants.KIND_CONCEPT);
        resource.setSelfLink(MetamacRestMocks.mockResourceLink(resource.getKind(), srmApiBaseV10 + "/concepts/" + resource.getId()));
        resource.setManagementAppLink(srmWebApplicationBase + "/concepts/" + resource.getId());
        resource.setName(mockInternationalString("es", resource.getId() + " en Español"));
        return resource;
    }

    // SRM Category resource internal
    public ResourceInternal mockResourceInternalFromCategoryResourceInternal(String code) {
        CategoryResourceInternal categoryResourceInternal = mockCategoryResourceInternal(code);

        ResourceInternal resource = new ResourceInternal();
        resource.setId(code);
        resource.setUrn(categoryResourceInternal.getUrn());
        resource.setKind(categoryResourceInternal.getKind());
        resource.setSelfLink(categoryResourceInternal.getSelfLink());
        resource.setManagementAppLink(categoryResourceInternal.getManagementAppLink());
        resource.setName(categoryResourceInternal.getName());
        resource.setNestedId(categoryResourceInternal.getNestedId());

        return resource;
    }

    // SRM Category resource internal
    public CategoryResourceInternal mockCategoryResourceInternal(String code) {
        CategoryResourceInternal categoryResourceInternal = new CategoryResourceInternal();
        categoryResourceInternal.setId(code);
        categoryResourceInternal.setUrn("urn:" + code);
        categoryResourceInternal.setUrnProvider(categoryResourceInternal.getUrn() + ":provider");
        categoryResourceInternal.setKind(SrmRestConstants.KIND_CATEGORY);
        categoryResourceInternal.setSelfLink(MetamacRestMocks.mockResourceLink(categoryResourceInternal.getKind(), srmApiBaseV10 + "/categories/" + code));
        categoryResourceInternal.setManagementAppLink(srmWebApplicationBase + "/categories/" + code);
        categoryResourceInternal.setName(mockInternationalString("es", code + " en Español"));
        return categoryResourceInternal;
    }
}
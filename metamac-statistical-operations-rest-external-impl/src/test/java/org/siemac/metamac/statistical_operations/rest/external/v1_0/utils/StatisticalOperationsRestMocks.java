package org.siemac.metamac.statistical_operations.rest.external.v1_0.utils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.math.BigInteger;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.rest.common.test.utils.MetamacRestMocks;
import org.siemac.metamac.rest.common.v1_0.domain.ChildLinks;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.Item;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configuration;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.ResourceInternal;
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
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.RegionalContributors;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.RegionalResponsibles;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.ResourceWithSubjectArea;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.SecondarySubjectAreas;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.StatConcDefs;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.StatisticalOperationSources;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.StatisticalOperationTypes;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.StatisticalUnits;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Status;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.TemporalGranularities;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.UpdateFrequencies;
import org.siemac.metamac.srm.rest.common.SrmRestConstants;
import org.siemac.metamac.statistical_operations.rest.common.StatisticalOperationsRestConstants;
import org.siemac.metamac.statistical_operations.rest.external.v1_0.service.StatisticalOperationsRestExternalFacadeV10Test;

public class StatisticalOperationsRestMocks {

    private final String statisticalOperationsExternalApiBase;
    private final String srmExternalApiBase;

    public StatisticalOperationsRestMocks(String statisticalOperationsExternalApiBase, String srmExternalApiBase) {
        assertNotNull(statisticalOperationsExternalApiBase);
        assertNotNull(srmExternalApiBase);

        this.statisticalOperationsExternalApiBase = statisticalOperationsExternalApiBase;
        this.srmExternalApiBase = srmExternalApiBase;
    }

    public Operation mockOperation1() {
        return mockOperation(statisticalOperationsExternalApiBase, "1");
    }

    public Operation mockOperation2() {
        return mockOperation(statisticalOperationsExternalApiBase, "2");
    }

    public Operation mockOperation3() {
        return mockOperation(statisticalOperationsExternalApiBase, "3");
    }

    public Operation mockOperation4() {
        return mockOperation(statisticalOperationsExternalApiBase, "4");
    }

    public Operation mockOperation5() {
        return mockOperation(statisticalOperationsExternalApiBase, "5");
    }

    public Operation mockOperation6() {
        return mockOperation(statisticalOperationsExternalApiBase, "6");
    }

    public Family mockFamily1() {
        return mockFamily(statisticalOperationsExternalApiBase, "1");
    }

    public Family mockFamily2() {
        return mockFamily(statisticalOperationsExternalApiBase, "2");
    }

    public Instance mockInstance1() {
        return mockInstance(statisticalOperationsExternalApiBase, "1", "operation1");
    }

    public Operations mockOperations(String baseApi, String limit, String offset) {
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
            operations.setNextLink(baseApi + "/operations?limit=2&offset=2");
            operations.setLastLink(baseApi + "/operations?limit=2&offset=8");
        } else if ("2".equals(limit) && "2".equals(offset)) {
            operations.getOperations().add(mockOperation3Resource());
            operations.getOperations().add(mockOperation4Resource());
            operations.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
            operations.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
            operations.setFirstLink(baseApi + "/operations?limit=2&offset=0");
            operations.setPreviousLink(baseApi + "/operations?limit=2&offset=0");
            operations.setNextLink(baseApi + "/operations?limit=2&offset=4");
            operations.setLastLink(baseApi + "/operations?limit=2&offset=8");
        } else if ("2".equals(limit) && "8".equals(offset)) {
            operations.getOperations().add(mockOperation9Resource());
            operations.getOperations().add(mockOperation10Resource());
            operations.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
            operations.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
            operations.setFirstLink(baseApi + "/operations?limit=2&offset=0");
            operations.setPreviousLink(baseApi + "/operations?limit=2&offset=6");
            operations.setNextLink(null);
            operations.setLastLink(null);
        } else if ("2".equals(limit) && "9".equals(offset)) {
            // no results
            operations.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
            operations.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
            operations.setFirstLink(baseApi + "/operations?limit=2&offset=0");
            operations.setPreviousLink(null);
            operations.setNextLink(null);
            operations.setLastLink(null);
        } else {
            fail("Limit or offset non supported. Limit = " + limit + ". Offset = " + offset);
        }
        return operations;
    }

    public Operations mockOperations(String baseApi, String limit, String offset, String query) {
        Operations operations = new Operations();
        operations.setKind(StatisticalOperationsRestConstants.KIND_OPERATIONS);
        String querySupported1 = StatisticalOperationsRestExternalFacadeV10Test.QUERY_OPERATION_ID_LIKE_1;
        String querySupported2 = StatisticalOperationsRestExternalFacadeV10Test.QUERY_OPERATION_ID_LIKE_1_AND_INDICATORS_SYSTEM;
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
                operations.setNextLink(baseApi + "/operations?query=" + query + "&limit=1&offset=1");
                operations.setLastLink(baseApi + "/operations?query=" + query + "&limit=1&offset=1");
            } else if ("1".equals(limit) && "1".equals(offset)) {
                operations.getOperations().add(mockOperation3Resource());
                operations.getOperations().add(mockOperation4Resource());
                operations.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
                operations.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
                operations.setFirstLink(baseApi + "/operations?query=" + query + "&limit=1&offset=0");
                operations.setPreviousLink(baseApi + "/operations?query=" + query + "&limit=1&offset=0");
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

    public Operations mockOperationsByFamily1(String baseApi, String limit, String offset) {
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
            operations.setNextLink(baseApi + "/families/family1/operations?limit=2&offset=2");
            operations.setLastLink(baseApi + "/families/family1/operations?limit=2&offset=4");
        } else if ("2".equals(limit) && "2".equals(offset)) {
            operations.getOperations().add(mockOperation3Resource());
            operations.getOperations().add(mockOperation4Resource());
            operations.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
            operations.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
            operations.setFirstLink(baseApi + "/families/family1/operations?limit=2&offset=0");
            operations.setPreviousLink(baseApi + "/families/family1/operations?limit=2&offset=0");
            operations.setNextLink(baseApi + "/families/family1/operations?limit=2&offset=4");
            operations.setLastLink(baseApi + "/families/family1/operations?limit=2&offset=4");
        } else if ("2".equals(limit) && "4".equals(offset)) {
            operations.getOperations().add(mockOperation5Resource());
            operations.getOperations().add(mockOperation6Resource());
            operations.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
            operations.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
            operations.setFirstLink(baseApi + "/families/family1/operations?limit=2&offset=0");
            operations.setPreviousLink(baseApi + "/families/family1/operations?limit=2&offset=2");
            operations.setNextLink(null);
            operations.setLastLink(null);
        } else if ("2".equals(limit) && "7".equals(offset)) {
            // no results
            operations.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
            operations.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
            operations.setFirstLink(baseApi + "/families/family1/operations?limit=2&offset=0");
            operations.setPreviousLink(null);
            operations.setNextLink(null);
            operations.setLastLink(null);
        } else {
            fail("Limit or offset non supported. Limit = " + limit + ". Offset = " + offset);
        }
        return operations;
    }

    public Operations mockOperationsByFamily2(String baseApi, String limit, String offset) {
        Operations operations = new Operations();
        operations.setKind(StatisticalOperationsRestConstants.KIND_OPERATIONS);
        operations.setTotal(BigInteger.valueOf(4));
        if ("1".equals(limit) && "2".equals(offset)) {
            operations.getOperations().add(mockOperation8Resource());
            operations.setOffset(BigInteger.valueOf(2));
            operations.setLimit(BigInteger.valueOf(1));
            operations.setFirstLink(baseApi + "/families/family2/operations?limit=1&offset=0");
            operations.setPreviousLink(baseApi + "/families/family2/operations?limit=1&offset=1");
            operations.setNextLink(baseApi + "/families/family2/operations?limit=1&offset=3");
            operations.setLastLink(baseApi + "/families/family2/operations?limit=1&offset=3");
        } else {
            fail("Limit or offset non supported. Limit = " + limit + ". Offset = " + offset);
        }
        return operations;
    }

    public Families mockFamilies(String baseApi, String limit, String offset) {
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
            families.setNextLink(baseApi + "/families?limit=2&offset=2");
            families.setLastLink(baseApi + "/families?limit=2&offset=4");
        } else if ("2".equals(limit) && "2".equals(offset)) {
            families.getFamilies().add(mockFamily3Resource());
            families.getFamilies().add(mockFamily4Resource());
            families.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
            families.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
            families.setFirstLink(baseApi + "/families?limit=2&offset=0");
            families.setPreviousLink(baseApi + "/families?limit=2&offset=0");
            families.setNextLink(baseApi + "/families?limit=2&offset=4");
            families.setLastLink(baseApi + "/families?limit=2&offset=4");
        } else if ("2".equals(limit) && "4".equals(offset)) {
            families.getFamilies().add(mockFamily15Resource());
            families.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
            families.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
            families.setFirstLink(baseApi + "/families?limit=2&offset=0");
            families.setPreviousLink(baseApi + "/families?limit=2&offset=2");
            families.setNextLink(null);
            families.setLastLink(null);
        } else if ("2".equals(limit) && "9".equals(offset)) {
            // no results
            families.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
            families.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
            families.setFirstLink(baseApi + "/families?limit=2&offset=0");
            families.setPreviousLink(null);
            families.setNextLink(null);
            families.setLastLink(null);
        } else {
            fail("Limit or offset non supported. Limit = " + limit + ". Offset = " + offset);
        }
        return families;
    }

    public Families mockFamilies(String baseApi, String limit, String offset, String query) {
        Families families = new Families();
        families.setKind(StatisticalOperationsRestConstants.KIND_FAMILIES);

        String querySupported1 = StatisticalOperationsRestExternalFacadeV10Test.QUERY_FAMILY_ID_LIKE_1;
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
                families.setNextLink(baseApi + "/families?query=" + query + "&limit=1&offset=1");
                families.setLastLink(baseApi + "/families?query=" + query + "&limit=1&offset=1");
            } else if ("1".equals(limit) && "1".equals(offset)) {
                families.getFamilies().add(mockFamily15Resource());
                families.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
                families.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
                families.setFirstLink(baseApi + "/families?query=" + query + "&limit=1&offset=0");
                families.setPreviousLink(baseApi + "/families?query=" + query + "&limit=1&offset=0");
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

    public Instances mockInstancesByOperation1(String baseApi, String limit, String offset) {
        Instances instances = new Instances();
        instances.setKind(StatisticalOperationsRestConstants.KIND_INSTANCES);
        instances.setTotal(BigInteger.valueOf(5));
        String operation = StatisticalOperationsRestExternalFacadeV10Test.OPERATION_1;
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
            instances.setNextLink(baseApi + "/operations/" + operation + "/instances?limit=2&offset=2");
            instances.setLastLink(baseApi + "/operations/" + operation + "/instances?limit=2&offset=4");
        } else if ("2".equals(limit) && "2".equals(offset)) {
            instances.getInstances().add(mockInstance3Resource());
            instances.getInstances().add(mockInstance4Resource());
            instances.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
            instances.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
            instances.setFirstLink(baseApi + "/operations/" + operation + "/instances?limit=2&offset=0");
            instances.setPreviousLink(baseApi + "/operations/" + operation + "/instances?limit=2&offset=0");
            instances.setNextLink(baseApi + "/operations/" + operation + "/instances?limit=2&offset=4");
            instances.setLastLink(baseApi + "/operations/" + operation + "/instances?limit=2&offset=4");
        } else if ("2".equals(limit) && "4".equals(offset)) {
            instances.getInstances().add(mockInstance15Resource());
            instances.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
            instances.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
            instances.setFirstLink(baseApi + "/operations/" + operation + "/instances?limit=2&offset=0");
            instances.setPreviousLink(baseApi + "/operations/" + operation + "/instances?limit=2&offset=2");
            instances.setNextLink(null);
            instances.setLastLink(null);
        } else if ("2".equals(limit) && "9".equals(offset)) {
            // no results
            instances.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
            instances.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
            instances.setFirstLink(baseApi + "/operations/" + operation + "/instances?limit=2&offset=0");
            instances.setPreviousLink(null);
            instances.setNextLink(null);
            instances.setLastLink(null);
        } else {
            fail("Limit or offset non supported. Limit = " + limit + ". Offset = " + offset);
        }
        return instances;
    }

    public Instances mockInstancesByOperation1(String baseApi, String limit, String offset, String query) {
        Instances instances = new Instances();
        instances.setKind(StatisticalOperationsRestConstants.KIND_INSTANCES);
        String operation = StatisticalOperationsRestExternalFacadeV10Test.OPERATION_1;

        String querySupported1 = StatisticalOperationsRestExternalFacadeV10Test.QUERY_INSTANCE_ID_LIKE_1;
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
                instances.setNextLink(baseApi + "/operations/" + operation + "/instances?query=" + query + "&limit=1&offset=1");
                instances.setLastLink(baseApi + "/operations/" + operation + "/instances?query=" + query + "&limit=1&offset=1");
            } else if ("1".equals(limit) && "1".equals(offset)) {
                instances.getInstances().add(mockInstance15Resource());
                instances.setOffset(BigInteger.valueOf(Integer.valueOf(offset).intValue()));
                instances.setLimit(BigInteger.valueOf(Integer.valueOf(limit).intValue()));
                instances.setFirstLink(baseApi + "/operations/" + operation + "/instances?query=" + query + "&limit=1&offset=0");
                instances.setPreviousLink(baseApi + "/operations/" + operation + "/instances?query=" + query + "&limit=1&offset=0");
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

    public Configuration mockExternalApiCommonMetadataRetrieveConfiguration1ById() {
        Configuration configuration = new Configuration();
        configuration.setContact(mockOrganisationResourceFromExternalItemSrm("contact1"));
        configuration.setLegalActs(mockInternationalStringMetadata("legalActs", "common1"));
        configuration.setDataSharing(mockInternationalStringMetadata("dataSharing", "common1"));
        configuration.setConfPolicy(mockInternationalStringMetadata("confidentialityPolicy", "1"));
        configuration.setConfDataTreatment(mockInternationalStringMetadata("confidentialityDataTreatment", "1"));
        return configuration;
    }

    private ResourceWithSubjectArea mockOperationResource(String subId) {
        String operationId = "operation" + subId;
        return mockResourceWithSubjectArea(operationId, "urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Operation=" + operationId, StatisticalOperationsRestConstants.KIND_OPERATION,
                statisticalOperationsExternalApiBase + "/operations/" + operationId);
    }

    private Resource mockFamilyResource(String subId) {
        String familyId = "family" + subId;
        return mockResource(familyId, "urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Family=" + familyId, StatisticalOperationsRestConstants.KIND_FAMILY,
                statisticalOperationsExternalApiBase + "/families/" + familyId);
    }

    private Resource mockInstanceResource(String operationId, String subId) {
        String instanceId = "instance" + subId;
        return mockResource(instanceId, "urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Instance=" + operationId + "." + instanceId, StatisticalOperationsRestConstants.KIND_INSTANCE,
                statisticalOperationsExternalApiBase + "/operations/" + operationId + "/instances/" + instanceId);
    }

    private Operation mockOperation(String baseApi, String subId) {

        Operation operation = new Operation();
        operation.setId("operation" + subId);
        operation.setUrn("urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Operation=" + operation.getId());
        operation.setKind(StatisticalOperationsRestConstants.KIND_OPERATION);
        operation.setSelfLink(MetamacRestMocks.mockResourceLink(operation.getKind(), baseApi + "/operations/operation" + subId));
        operation.setName(mockInternationalStringMetadata("operation", subId));
        operation.setAcronym(mockInternationalStringMetadata("acronym", subId));
        operation.setSubjectArea(mockResourceFromExternalItemSrm("subjectArea1", "subjectAreas", "structuralResources#category"));
        operation.setSecondarySubjectAreas(new SecondarySubjectAreas());
        operation.getSecondarySubjectAreas().setTotal(BigInteger.valueOf(3));
        operation.getSecondarySubjectAreas().setKind(SrmRestConstants.KIND_CATEGORIES);
        operation.getSecondarySubjectAreas().getSecondarySubjectAreas().add(mockResourceFromExternalItemSrm("secundarySubjectArea1", "secundarySubjectAreas", "structuralResources#category"));
        operation.getSecondarySubjectAreas().getSecondarySubjectAreas().add(mockResourceFromExternalItemSrm("secundarySubjectArea22", "secundarySubjectAreas", "structuralResources#category"));
        operation.getSecondarySubjectAreas().getSecondarySubjectAreas().add(mockResourceFromExternalItemSrm("secundarySubjectArea333", "secundarySubjectAreas", "structuralResources#category"));
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
        operation.setRegionalResponsibles(new RegionalResponsibles());
        operation.getRegionalResponsibles().setTotal(BigInteger.valueOf(3));
        operation.getRegionalResponsibles().setKind(SrmRestConstants.KIND_ORGANISATION_UNITS);
        operation.getRegionalResponsibles().getRegionalResponsibles().add(mockResourceFromExternalItemSrm("regionalResponsible1", "regionalResponsibles", "structuralResources#agency"));
        operation.getRegionalResponsibles().getRegionalResponsibles().add(mockResourceFromExternalItemSrm("regionalResponsible22", "regionalResponsibles", "structuralResources#agency"));
        operation.getRegionalResponsibles().getRegionalResponsibles().add(mockResourceFromExternalItemSrm("regionalResponsible333", "regionalResponsibles", "structuralResources#agency"));
        operation.setRegionalContributors(new RegionalContributors());
        operation.getRegionalContributors().setTotal(BigInteger.valueOf(2));
        operation.getRegionalContributors().setKind(SrmRestConstants.KIND_ORGANISATIONS);
        operation.getRegionalContributors().getRegionalContributors().add(mockResourceFromExternalItemSrm("regionalContributor1", "regionalContributors", "structuralResources#agency"));
        operation.getRegionalContributors().getRegionalContributors().add(mockResourceFromExternalItemSrm("regionalContributor22", "regionalContributors", "structuralResources#agency"));
        operation.setCurrentlyActive(Boolean.FALSE);
        operation.setStatus(Status.DESIGN);
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
        operation.setNotes(mockInternationalStringMetadata("notes", subId));
        operation.setParentLink(MetamacRestMocks.mockResourceLink(StatisticalOperationsRestConstants.KIND_OPERATIONS, baseApi + "/operations"));
        operation.setChildLinks(new ChildLinks());
        operation.getChildLinks().setTotal(BigInteger.valueOf(2));
        operation.getChildLinks().getChildLinks().add(MetamacRestMocks.mockResourceLink(StatisticalOperationsRestConstants.KIND_FAMILIES, baseApi + "/operations/operation" + subId + "/families"));
        operation.getChildLinks().getChildLinks().add(MetamacRestMocks.mockResourceLink(StatisticalOperationsRestConstants.KIND_INSTANCES, baseApi + "/operations/operation" + subId + "/instances"));
        return operation;
    }

    private Family mockFamily(String baseApi, String subId) {

        Family family = new Family();
        family.setId("family" + subId);
        family.setUrn("urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Family=" + family.getId());
        family.setKind(StatisticalOperationsRestConstants.KIND_FAMILY);
        family.setSelfLink(MetamacRestMocks.mockResourceLink(family.getKind(), baseApi + "/families/family" + subId));
        family.setName(mockInternationalStringMetadata("family", subId));
        family.setAcronym(mockInternationalStringMetadata("acronym", subId));
        family.setDescription(mockInternationalStringMetadata("description", subId));
        family.setInventoryDate(new DateTime(2013, 2, 4, 13, 15, 14, 0).toDate());
        family.setParentLink(MetamacRestMocks.mockResourceLink(StatisticalOperationsRestConstants.KIND_FAMILIES, baseApi + "/families"));
        family.setChildLinks(new ChildLinks());
        family.getChildLinks().setTotal(BigInteger.valueOf(1));
        family.getChildLinks().getChildLinks().add(MetamacRestMocks.mockResourceLink(StatisticalOperationsRestConstants.KIND_OPERATIONS, baseApi + "/families/family" + subId + "/operations"));
        return family;
    }

    private Instance mockInstance(String baseApi, String subId, String operation) {

        Instance instance = new Instance();
        instance.setId("instance" + subId);
        instance.setUrn("urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Instance=" + operation + "." + instance.getId());
        instance.setKind(StatisticalOperationsRestConstants.KIND_INSTANCE);
        instance.setSelfLink(MetamacRestMocks.mockResourceLink(instance.getKind(), baseApi + "/operations/" + operation + "/instances/instance" + subId));
        instance.setName(mockInternationalStringMetadata("instance", subId));
        instance.setAcronym(mockInternationalStringMetadata("acronym", subId));
        instance.setStatisticalOperation(mockResource(operation, "urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Operation=" + operation,
                StatisticalOperationsRestConstants.KIND_OPERATION, baseApi + "/operations/" + operation));
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
        instance.getMeasures().setTotal(BigInteger.valueOf(1));
        instance.getMeasures().setKind(SrmRestConstants.KIND_CONCEPTS);
        instance.getMeasures().getMeasures().add(mockResourceFromExternalItemSrm("measure1", "measures", "structuralResources#concept"));
        instance.setStatConcDefsDescription(mockInternationalStringMetadata("statConcDef", subId));
        instance.setStatConcDefs(new StatConcDefs());
        instance.getStatConcDefs().setTotal(BigInteger.valueOf(3));
        instance.getStatConcDefs().setKind(SrmRestConstants.KIND_CONCEPTS);
        instance.getStatConcDefs().getStatConcDefs().add(mockResourceFromExternalItemSrm("statConcDefList1", "statConcDefLists", "structuralResources#concept"));
        instance.getStatConcDefs().getStatConcDefs().add(mockResourceFromExternalItemSrm("statConcDefList22", "statConcDefLists", "structuralResources#concept"));
        instance.getStatConcDefs().getStatConcDefs().add(mockResourceFromExternalItemSrm("statConcDefList333", "statConcDefLists", "structuralResources#concept"));
        instance.setClassSystemsDescription(mockInternationalStringMetadata("classSystem", subId));
        instance.setClassSystems(new ClassSystems());
        instance.getClassSystems().setTotal(BigInteger.valueOf(2));
        instance.getClassSystems().setKind(SrmRestConstants.KIND_CODELISTS);
        instance.getClassSystems().getClassSystems().add(mockResourceFromExternalItemSrm("classSystemList1", "classSystemLists", "structuralResources#codelist"));
        instance.getClassSystems().getClassSystems().add(mockResourceFromExternalItemSrm("classSystemList22", "classSystemLists", "structuralResources#codelist"));
        instance.setDocMethod(mockInternationalStringMetadata("docMethod", subId));
        instance.setStatisticalOperationSource(mockItem("statisticalOperationSource1"));
        instance.setCollMethod(mockItem("collMethod1"));
        instance.setInformationSuppliers(new InformationSuppliers());
        instance.getInformationSuppliers().setTotal(BigInteger.valueOf(1));
        instance.getInformationSuppliers().setKind(SrmRestConstants.KIND_DATA_PROVIDERS);
        instance.getInformationSuppliers().getInformationSuppliers().add(mockResourceFromExternalItemSrm("informationSupplier1", "informationSuppliers", "structuralResources#concept"));
        instance.setFreqColls(new FreqColls());
        instance.getFreqColls().setTotal(BigInteger.valueOf(2));
        instance.getFreqColls().setKind(SrmRestConstants.KIND_CODES);
        instance.getFreqColls().getFreqColls().add(mockResourceFromExternalItemSrm("freqColl1", "freqColls", "structuralResources#categoryScheme"));
        instance.getFreqColls().getFreqColls().add(mockResourceFromExternalItemSrm("freqColl22", "freqColls", "structuralResources#categoryScheme"));
        instance.setDataValidation(mockInternationalStringMetadata("dataValidation", subId));
        instance.setDataCompilation(mockInternationalStringMetadata("dataCompilation", subId));
        instance.setAdjustment(mockInternationalStringMetadata("adjustment", subId));
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
        instance.setCoherXDom(mockInternationalStringMetadata("coherXDom", subId));
        instance.setCoherInternal(mockInternationalStringMetadata("coherInternal", subId));
        instance.setComment(mockInternationalStringMetadata("comment", subId));
        instance.setParentLink(MetamacRestMocks.mockResourceLink(StatisticalOperationsRestConstants.KIND_OPERATION, baseApi + "/operations/" + operation));
        // no children
        return instance;
    }

    private ResourceWithSubjectArea mockOperation1Resource() {
        return mockOperationResource("1");
    }

    private ResourceWithSubjectArea mockOperation2Resource() {
        return mockOperationResource("2");
    }

    private ResourceWithSubjectArea mockOperation3Resource() {
        return mockOperationResource("3");
    }

    private ResourceWithSubjectArea mockOperation4Resource() {
        return mockOperationResource("4");
    }

    private ResourceWithSubjectArea mockOperation5Resource() {
        return mockOperationResource("5");
    }

    private ResourceWithSubjectArea mockOperation6Resource() {
        return mockOperationResource("6");
    }

    private ResourceWithSubjectArea mockOperation7Resource() {
        return mockOperationResource("7");
    }

    private ResourceWithSubjectArea mockOperation8Resource() {
        return mockOperationResource("8");
    }

    private ResourceWithSubjectArea mockOperation9Resource() {
        return mockOperationResource("9");
    }

    private ResourceWithSubjectArea mockOperation10Resource() {
        return mockOperationResource("10");
    }

    private Resource mockFamily1Resource() {
        return mockFamilyResource("1");
    }

    private Resource mockFamily2Resource() {
        return mockFamilyResource("2");
    }

    private Resource mockFamily3Resource() {
        return mockFamilyResource("3");
    }

    private Resource mockFamily4Resource() {
        return mockFamilyResource("4");
    }

    private Resource mockFamily15Resource() {
        return mockFamilyResource("15");
    }

    private Resource mockInstance1Resource() {
        return mockInstanceResource(StatisticalOperationsRestExternalFacadeV10Test.OPERATION_1, "1");
    }

    private Resource mockInstance2Resource() {
        return mockInstanceResource(StatisticalOperationsRestExternalFacadeV10Test.OPERATION_1, "2");
    }

    private Resource mockInstance3Resource() {
        return mockInstanceResource(StatisticalOperationsRestExternalFacadeV10Test.OPERATION_1, "3");
    }

    private Resource mockInstance4Resource() {
        return mockInstanceResource(StatisticalOperationsRestExternalFacadeV10Test.OPERATION_1, "4");
    }

    private Resource mockInstance15Resource() {
        return mockInstanceResource(StatisticalOperationsRestExternalFacadeV10Test.OPERATION_1, "15");
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

    private Resource mockResourceFromExternalItemSrm(String id, String apiSubpath, String kind) {
        return mockResourceFromExternalItem(id, srmExternalApiBase + "/v1.0", apiSubpath, kind);
    }

    private Resource mockResourceFromExternalItem(String id, String endpointApi, String apiSubpath, String kind) {
        String urn = "urn:" + id;
        String selfLink = endpointApi + "/" + apiSubpath + "/" + id;
        Resource target = mockResource(id, urn, kind, selfLink);
        if (TypeExternalArtefactsEnum.AGENCY.getValue().equals(kind) || TypeExternalArtefactsEnum.CATEGORY.getValue().equals(kind)) {
            target.setNestedId(id + "Nested");
        }
        return target;
    }

    private ResourceInternal mockOrganisationResourceFromExternalItemSrm(String id) {
        ResourceInternal resource = new ResourceInternal();
        resource.setId(id);
        resource.setUrn("urn:sdmx:org.sdmx.infomodel.base.Agency=SDMX:AGENCIES(1.0)." + id);
        resource.setKind("structuralResources#agency");
        resource.setSelfLink(MetamacRestMocks.mockResourceLink(resource.getKind(), srmExternalApiBase + "/v1.0/agencyschemes/SDMX/AGENCIES/1.0/agencies/" + id));
        resource.setName(mockInternationalString("es", id + " en Español"));
        return resource;
    }

    private Resource mockResource(String id, String urn, String kind, String selfLink) {
        Resource resource = new Resource();
        resource.setId(id);
        resource.setUrn(urn);
        resource.setKind(kind);
        resource.setSelfLink(MetamacRestMocks.mockResourceLink(kind, selfLink));
        resource.setName(mockInternationalString("es", id + " en Español"));
        return resource;
    }

    private ResourceWithSubjectArea mockResourceWithSubjectArea(String id, String urn, String kind, String selfLink) {
        ResourceWithSubjectArea resource = new ResourceWithSubjectArea();
        resource.setId(id);
        resource.setUrn(urn);
        resource.setKind(kind);
        resource.setSelfLink(MetamacRestMocks.mockResourceLink(kind, selfLink));
        resource.setName(mockInternationalString("es", id + " en Español"));
        return resource;
    }

    private Item mockItem(String id) {
        Item item = new Item();
        item.setId(id);
        item.setName(mockInternationalString("es", id + " en Español"));
        return item;
    }
}
package org.siemac.metamac.statistical_operations.rest.internal.v1_0.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.siemac.metamac.rest.common.test.utils.MetamacRestAsserts;
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
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Producers;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Publishers;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.RegionalContributors;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.RegionalResponsibles;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.SecondarySubjectAreas;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.StatConcDefs;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.StatisticalOperationSources;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.StatisticalOperationTypes;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.StatisticalUnits;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.TemporalGranularities;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.UpdateFrequencies;

public class StatisticalOperationsRestAsserts {

    public static void assertEqualsOperation(Operation expected, Operation actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUrn(), actual.getUrn());
        assertEquals(expected.getKind(), actual.getKind());
        MetamacRestAsserts.assertEqualsResourceLink(expected.getSelfLink(), actual.getSelfLink());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getName(), actual.getName());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getAcronym(), actual.getAcronym());
        MetamacRestAsserts.assertEqualsResource(expected.getSubjectArea(), actual.getSubjectArea());
        assertEqualsSecondarySubjectAreas(expected.getSecondarySubjectAreas(), actual.getSecondarySubjectAreas());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getObjective(), actual.getObjective());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getDescription(), actual.getDescription());
        MetamacRestAsserts.assertEqualsItem(expected.getStatisticalOperationType(), actual.getStatisticalOperationType());
        MetamacRestAsserts.assertEqualsItem(expected.getOfficialityType(), actual.getOfficialityType());
        assertEquals(expected.getIndicatorSystem(), actual.getIndicatorSystem());
        assertEqualsProducers(expected.getProducers(), actual.getProducers());
        assertEqualsRegionalResponsibles(expected.getRegionalResponsibles(), actual.getRegionalResponsibles());
        assertEqualsRegionalContributors(expected.getRegionalContributors(), actual.getRegionalContributors());
        assertEquals(expected.getCreatedDate(), actual.getCreatedDate());
        assertEquals(expected.getInternalInventoryDate(), actual.getInternalInventoryDate());
        assertEquals(expected.getCurrentlyActive(), actual.getCurrentlyActive());
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getProcStatus(), actual.getProcStatus());
        assertEqualsPublishers(expected.getPublishers(), actual.getPublishers());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getRelPolUsAc(), actual.getRelPolUsAc());
        assertEquals(expected.getReleaseCalendar(), actual.getReleaseCalendar());
        assertEquals(expected.getReleaseCalendarAccess(), actual.getReleaseCalendarAccess());
        assertEqualsUpdateFrequencies(expected.getUpdateFrequencies(), actual.getUpdateFrequencies());
        MetamacRestAsserts.assertEqualsResource(expected.getCurrentInternalInstance(), actual.getCurrentInternalInstance());
        MetamacRestAsserts.assertEqualsResource(expected.getCurrentInstance(), actual.getCurrentInstance());
        assertEquals(expected.getInventoryDate(), actual.getInventoryDate());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getRevPolicy(), actual.getRevPolicy());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getRevPractice(), actual.getRevPractice());
        MetamacRestAsserts.assertEqualsResource(expected.getContact(), actual.getContact());
        assertEqualsLegalActs(expected.getLegalActs(), actual.getLegalActs());
        assertEqualsDataSharings(expected.getDataSharings(), actual.getDataSharings());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getConfidentialityPolicy(), actual.getConfidentialityPolicy());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getConfidentialityDataTreatment(), actual.getConfidentialityDataTreatment());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getNotes(), actual.getNotes());
        MetamacRestAsserts.assertEqualsResourceLink(expected.getParentLink(), actual.getParentLink());
        MetamacRestAsserts.assertEqualsChildLinks(expected.getChildLinks(), actual.getChildLinks());
        assertEquals(expected.getManagementAppLink(), actual.getManagementAppLink());
    }

    public static void assertEqualsFamily(Family expected, Family actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUrn(), actual.getUrn());
        assertEquals(expected.getKind(), actual.getKind());
        MetamacRestAsserts.assertEqualsResourceLink(expected.getSelfLink(), actual.getSelfLink());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getName(), actual.getName());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getAcronym(), actual.getAcronym());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getCreatedDate(), actual.getCreatedDate());
        assertEquals(expected.getInternalInventoryDate(), actual.getInternalInventoryDate());
        assertEquals(expected.getProcStatus(), actual.getProcStatus());
        assertEquals(expected.getInventoryDate(), actual.getInventoryDate());
        MetamacRestAsserts.assertEqualsResourceLink(expected.getParentLink(), actual.getParentLink());
        MetamacRestAsserts.assertEqualsChildLinks(expected.getChildLinks(), actual.getChildLinks());
        assertEquals(expected.getManagementAppLink(), actual.getManagementAppLink());
    }

    public static void assertEqualsInstance(Instance expected, Instance actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUrn(), actual.getUrn());
        assertEquals(expected.getKind(), actual.getKind());
        MetamacRestAsserts.assertEqualsResourceLink(expected.getSelfLink(), actual.getSelfLink());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getName(), actual.getName());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getAcronym(), actual.getAcronym());
        MetamacRestAsserts.assertEqualsResource(expected.getStatisticalOperation(), actual.getStatisticalOperation());
        MetamacRestAsserts.assertEqualsResource(expected.getSuccessor(), actual.getSuccessor());
        MetamacRestAsserts.assertEqualsResource(expected.getPredecessor(), actual.getPredecessor());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getDataDescription(), actual.getDataDescription());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getStatisticalPopulation(), actual.getStatisticalPopulation());
        assertEqualsStatisticalUnits(expected.getStatisticalUnits(), actual.getStatisticalUnits());
        assertEqualsGeographicGranularities(expected.getGeographicGranularity(), actual.getGeographicGranularity());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getGeographicComparability(), actual.getGeographicComparability());
        assertEqualsTemporalGranularities(expected.getTemporalGranularity(), actual.getTemporalGranularity());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getTemporalComparability(), actual.getTemporalComparability());
        assertEquals(expected.getBasePeriod(), actual.getBasePeriod());
        assertEqualsMeasures(expected.getMeasures(), actual.getMeasures());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getStatConcDefsDescription(), actual.getStatConcDefsDescription());
        assertEqualsStatConcDefs(expected.getStatConcDefs(), actual.getStatConcDefs());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getClassSystemsDescription(), actual.getClassSystemsDescription());
        assertEqualsClassSystems(expected.getClassSystems(), actual.getClassSystems());
        MetamacRestAsserts.assertEqualsItem(expected.getInstanceType(), actual.getInstanceType());
        assertEquals(expected.getCreatedDate(), actual.getCreatedDate());
        assertEquals(expected.getInternalInventoryDate(), actual.getInternalInventoryDate());
        assertEquals(expected.getProcStatus(), actual.getProcStatus());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getDocMethod(), actual.getDocMethod());
        MetamacRestAsserts.assertEqualsItem(expected.getStatisticalOperationSource(), actual.getStatisticalOperationSource());
        MetamacRestAsserts.assertEqualsItem(expected.getCollMethod(), actual.getCollMethod());
        assertEqualsInformationSuppliers(expected.getInformationSuppliers(), actual.getInformationSuppliers());
        assertEqualsFreqColls(expected.getFreqColls(), actual.getFreqColls());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getDataValidation(), actual.getDataValidation());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getDataCompilation(), actual.getDataCompilation());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getAdjustment(), actual.getAdjustment());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getCostBurden(), actual.getCostBurden());
        assertEqualsCosts(expected.getCosts(), actual.getCosts());
        assertEquals(expected.getInventoryDate(), actual.getInventoryDate());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getQualityDoc(), actual.getQualityDoc());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getQualityAssure(), actual.getQualityAssure());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getQualityAssmnt(), actual.getQualityAssmnt());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getUserNeeds(), actual.getUserNeeds());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getUserSat(), actual.getUserSat());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getCompleteness(), actual.getCompleteness());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getTimeliness(), actual.getTimeliness());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getPunctuality(), actual.getPunctuality());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getAccuracyOverall(), actual.getAccuracyOverall());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getSamplingErr(), actual.getSamplingErr());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getNonsamplingErr(), actual.getNonsamplingErr());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getCoherXDom(), actual.getCoherXDom());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getCoherInternal(), actual.getCoherInternal());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getComment(), actual.getComment());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getNotes(), actual.getNotes());
        MetamacRestAsserts.assertEqualsResourceLink(expected.getParentLink(), actual.getParentLink());
        MetamacRestAsserts.assertEqualsChildLinks(expected.getChildLinks(), actual.getChildLinks());
        assertEquals(expected.getManagementAppLink(), actual.getManagementAppLink());
    }

    public static void assertEqualsOperations(Operations expected, Operations actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        assertEqualsResourcesInternal(expected.getOperations(), actual.getOperations());
    }

    public static void assertEqualsFamilies(Families expected, Families actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        assertEqualsResourcesInternal(expected.getFamilies(), actual.getFamilies());
    }

    public static void assertEqualsInstances(Instances expected, Instances actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        assertEqualsResourcesInternal(expected.getInstances(), actual.getInstances());
    }

    public static void assertEqualsStatisticalOperationTypes(StatisticalOperationTypes expected, StatisticalOperationTypes actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsItems(expected.getStatisticalOperationTypes(), actual.getStatisticalOperationTypes());
        assertEquals(expected.getTotal(), actual.getTotal());
    }

    public static void assertEqualsOfficialityTypes(OfficialityTypes expected, OfficialityTypes actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsItems(expected.getOfficialityTypes(), actual.getOfficialityTypes());
        assertEquals(expected.getTotal(), actual.getTotal());
    }

    public static void assertEqualsStatisticalOperationSources(StatisticalOperationSources expected, StatisticalOperationSources actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsItems(expected.getStatisticalOperationSources(), actual.getStatisticalOperationSources());
        assertEquals(expected.getTotal(), actual.getTotal());
    }

    public static void assertEqualsInstanceTypes(InstanceTypes expected, InstanceTypes actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsItems(expected.getInstanceTypes(), actual.getInstanceTypes());
        assertEquals(expected.getTotal(), actual.getTotal());
    }

    public static void assertEqualsCollMethods(CollMethods expected, CollMethods actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsItems(expected.getCollMethods(), actual.getCollMethods());
        assertEquals(expected.getTotal(), actual.getTotal());
    }

    public static void assertEqualsCosts(Costs expected, Costs actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsItems(expected.getCosts(), actual.getCosts());
        assertEquals(expected.getTotal(), actual.getTotal());
    }

    private static void assertEqualsInformationSuppliers(InformationSuppliers expected, InformationSuppliers actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        assertEqualsResourcesInternal(expected.getInformationSuppliers(), actual.getInformationSuppliers());
    }

    private static void assertEqualsFreqColls(FreqColls expected, FreqColls actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        assertEqualsResourcesInternal(expected.getFreqColls(), actual.getFreqColls());
    }

    private static void assertEqualsClassSystems(ClassSystems expected, ClassSystems actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        assertEqualsResourcesInternal(expected.getClassSystems(), actual.getClassSystems());
    }

    private static void assertEqualsStatConcDefs(StatConcDefs expected, StatConcDefs actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        assertEqualsResourcesInternal(expected.getStatConcDefs(), actual.getStatConcDefs());
    }

    private static void assertEqualsMeasures(Measures expected, Measures actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        assertEqualsResourcesInternal(expected.getMeasures(), actual.getMeasures());
    }

    private static void assertEqualsStatisticalUnits(StatisticalUnits expected, StatisticalUnits actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        assertEqualsResourcesInternal(expected.getStatisticalUnits(), actual.getStatisticalUnits());
    }

    private static void assertEqualsGeographicGranularities(GeographicGranularities expected, GeographicGranularities actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        assertEqualsResourcesInternal(expected.getGeographicGranularities(), actual.getGeographicGranularities());
    }

    private static void assertEqualsTemporalGranularities(TemporalGranularities expected, TemporalGranularities actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        assertEqualsResourcesInternal(expected.getTemporalGranularities(), actual.getTemporalGranularities());
    }

    private static void assertEqualsSecondarySubjectAreas(SecondarySubjectAreas expected, SecondarySubjectAreas actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        assertEqualsResourcesInternal(expected.getSecondarySubjectAreas(), actual.getSecondarySubjectAreas());
    }

    private static void assertEqualsProducers(Producers expected, Producers actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        assertEqualsResourcesInternal(expected.getProducers(), actual.getProducers());
    }

    private static void assertEqualsRegionalResponsibles(RegionalResponsibles expected, RegionalResponsibles actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        assertEqualsResourcesInternal(expected.getRegionalResponsibles(), actual.getRegionalResponsibles());
    }

    private static void assertEqualsRegionalContributors(RegionalContributors expected, RegionalContributors actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        assertEqualsResourcesInternal(expected.getRegionalContributors(), actual.getRegionalContributors());
    }

    private static void assertEqualsPublishers(Publishers expected, Publishers actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        assertEqualsResourcesInternal(expected.getPublishers(), actual.getPublishers());
    }

    private static void assertEqualsLegalActs(LegalActs expected, LegalActs actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        MetamacRestAsserts.assertEqualsInternationalStrings(expected.getLegalActs(), actual.getLegalActs());
    }

    private static void assertEqualsDataSharings(DataSharings expected, DataSharings actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        MetamacRestAsserts.assertEqualsInternationalStrings(expected.getDataSharings(), actual.getDataSharings());
    }

    private static void assertEqualsUpdateFrequencies(UpdateFrequencies expected, UpdateFrequencies actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        assertEqualsResourcesInternal(expected.getUpdateFrequencies(), actual.getUpdateFrequencies());
    }

    public static void assertEqualsResourcesInternal(List<ResourceInternal> expecteds, List<ResourceInternal> actuals) {
        MetamacRestAsserts.assertEqualsNullability(expecteds, actuals);
        if (expecteds == null) {
            return;
        }
        assertEquals(expecteds.size(), actuals.size());
        for (ResourceInternal expected : expecteds) {
            boolean existsItem = false;
            for (ResourceInternal actual : actuals) {
                if (expected.getId().equals(actual.getId())) {
                    assertEqualsResource(expected, actual);
                    existsItem = true;
                }
            }
            assertTrue(existsItem);
        }
    }

    public static void assertEqualsResource(ResourceInternal expected, ResourceInternal actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        assertEquals(expected.getManagementAppLink(), actual.getManagementAppLink());
        MetamacRestAsserts.assertEqualsResource(expected, actual);
    }
}
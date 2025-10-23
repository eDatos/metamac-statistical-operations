package org.siemac.metamac.statistical_operations.rest.external.v1_0.utils;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.List;

import org.siemac.metamac.rest.common.test.utils.MetamacRestAsserts;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.ClassSystems;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.CollMethods;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Costs;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.DataSharings;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Families;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Family;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.FreqColls;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.GeographicGranularities;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.PublicInformationSuppliers;
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
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Contributors;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Responsibles;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.ResourceWithSubjectArea;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.SecondarySubjectAreas;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.StatConcDefs;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.StatisticalOperationSources;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.StatisticalOperationTypes;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.StatisticalUnits;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.TemporalGranularities;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.UpdateFrequencies;

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
        assertEqualsResponsibles(expected.getResponsibles(), actual.getResponsibles());
        assertEqualsContributors(expected.getContributors(), actual.getContributors());
        assertEquals(expected.getCurrentlyActive(), actual.getCurrentlyActive());
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEqualsPublishers(expected.getPublishers(), actual.getPublishers());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getRelPolUsAc(), actual.getRelPolUsAc());
        assertEquals(expected.getReleaseCalendar(), actual.getReleaseCalendar());
        assertEquals(expected.getReleaseCalendarAccess(), actual.getReleaseCalendarAccess());
        assertEqualsUpdateFrequencies(expected.getUpdateFrequencies(), actual.getUpdateFrequencies());
        MetamacRestAsserts.assertEqualsResource(expected.getCurrentInstance(), actual.getCurrentInstance());
        assertEquals(expected.getInventoryDate(), actual.getInventoryDate());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getRevPolicy(), actual.getRevPolicy());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getRevPractice(), actual.getRevPractice());
        MetamacRestAsserts.assertEqualsResource(expected.getContact(), actual.getContact());
        assertEqualsLegalActs(expected.getLegalActs(), actual.getLegalActs());
        assertEqualsDataSharings(expected.getDataSharings(), actual.getDataSharings());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getConfidentialityPolicy(), actual.getConfidentialityPolicy());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getConfidentialityDataTreatment(), actual.getConfidentialityDataTreatment());
        MetamacRestAsserts.assertEqualsResourceLink(expected.getParentLink(), actual.getParentLink());
        MetamacRestAsserts.assertEqualsChildLinks(expected.getChildLinks(), actual.getChildLinks());
    }

    public static void assertEqualsFamily(Family expected, Family actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUrn(), actual.getUrn());
        assertEquals(expected.getKind(), actual.getKind());
        MetamacRestAsserts.assertEqualsResourceLink(expected.getSelfLink(), actual.getSelfLink());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getName(), actual.getName());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getAcronym(), actual.getAcronym());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getInventoryDate(), actual.getInventoryDate());
        MetamacRestAsserts.assertEqualsResourceLink(expected.getParentLink(), actual.getParentLink());
        MetamacRestAsserts.assertEqualsChildLinks(expected.getChildLinks(), actual.getChildLinks());
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
        assertEqualsUnitMeasures(expected.getUnitMeasures(), actual.getUnitMeasures());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getStatConcDefsDescription(), actual.getStatConcDefsDescription());
        assertEqualsStatConcDefs(expected.getStatConcDefs(), actual.getStatConcDefs());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getClassSystemsDescription(), actual.getClassSystemsDescription());
        assertEqualsClassSystems(expected.getClassSystems(), actual.getClassSystems());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getDocMethod(), actual.getDocMethod());
        MetamacRestAsserts.assertEqualsItem(expected.getStatisticalOperationSource(), actual.getStatisticalOperationSource());
        MetamacRestAsserts.assertEqualsItem(expected.getCollMethod(), actual.getCollMethod());
        assertEqualsPublicInformationSuppliers(expected.getPublicInformationSuppliers(), actual.getPublicInformationSuppliers());
        assertEqualsFreqColls(expected.getFreqColls(), actual.getFreqColls());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getDataValidation(), actual.getDataValidation());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getDataCompilation(), actual.getDataCompilation());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getAdjustment(), actual.getAdjustment());
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
        MetamacRestAsserts.assertEqualsResourceLink(expected.getParentLink(), actual.getParentLink());
        MetamacRestAsserts.assertEqualsChildLinks(expected.getChildLinks(), actual.getChildLinks());
    }

    public static void assertEqualsOperations(Operations expected, Operations actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        MetamacRestAsserts.assertEqualsResources(Collections.unmodifiableList(expected.getOperations()), Collections.unmodifiableList(actual.getOperations()));
    }

    public static void assertEqualsOperationsWithSubjectArea(Operations expected, Operations actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        assertEqualsResourcesWithSubjectAreas(expected.getOperations(), actual.getOperations());
    }

    public static void assertEqualsFamilies(Families expected, Families actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        MetamacRestAsserts.assertEqualsResources(expected.getFamilies(), actual.getFamilies());
    }

    public static void assertEqualsInstances(Instances expected, Instances actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        MetamacRestAsserts.assertEqualsResources(expected.getInstances(), actual.getInstances());
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

    private static void assertEqualsPublicInformationSuppliers(PublicInformationSuppliers expected, PublicInformationSuppliers actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        MetamacRestAsserts.assertEqualsResources(expected.getPublicInformationSuppliers(), actual.getPublicInformationSuppliers());
    }

    private static void assertEqualsFreqColls(FreqColls expected, FreqColls actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        MetamacRestAsserts.assertEqualsResources(expected.getFreqColls(), actual.getFreqColls());
    }

    private static void assertEqualsClassSystems(ClassSystems expected, ClassSystems actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        MetamacRestAsserts.assertEqualsResources(expected.getClassSystems(), actual.getClassSystems());
    }

    private static void assertEqualsStatConcDefs(StatConcDefs expected, StatConcDefs actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        MetamacRestAsserts.assertEqualsResources(expected.getStatConcDefs(), actual.getStatConcDefs());
    }

    private static void assertEqualsUnitMeasures(UnitMeasures expected, UnitMeasures actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        MetamacRestAsserts.assertEqualsResources(expected.getMeasures(), actual.getMeasures());
    }

    private static void assertEqualsStatisticalUnits(StatisticalUnits expected, StatisticalUnits actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        MetamacRestAsserts.assertEqualsResources(expected.getStatisticalUnits(), actual.getStatisticalUnits());
    }

    private static void assertEqualsGeographicGranularities(GeographicGranularities expected, GeographicGranularities actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        MetamacRestAsserts.assertEqualsResources(expected.getGeographicGranularities(), actual.getGeographicGranularities());
    }

    private static void assertEqualsTemporalGranularities(TemporalGranularities expected, TemporalGranularities actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        MetamacRestAsserts.assertEqualsResources(expected.getTemporalGranularities(), actual.getTemporalGranularities());
    }

    private static void assertEqualsSecondarySubjectAreas(SecondarySubjectAreas expected, SecondarySubjectAreas actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        MetamacRestAsserts.assertEqualsResources(expected.getSecondarySubjectAreas(), actual.getSecondarySubjectAreas());
    }

    private static void assertEqualsProducers(Producers expected, Producers actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        MetamacRestAsserts.assertEqualsResources(expected.getProducers(), actual.getProducers());
    }

    private static void assertEqualsResponsibles(Responsibles expected, Responsibles actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        MetamacRestAsserts.assertEqualsResources(expected.getResponsibles(), actual.getResponsibles());
    }

    private static void assertEqualsContributors(Contributors expected, Contributors actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        MetamacRestAsserts.assertEqualsResources(expected.getContributors(), actual.getContributors());
    }

    private static void assertEqualsPublishers(Publishers expected, Publishers actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        MetamacRestAsserts.assertEqualsResources(expected.getPublishers(), actual.getPublishers());
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
        MetamacRestAsserts.assertEqualsResources(expected.getUpdateFrequencies(), actual.getUpdateFrequencies());
    }

    private static void assertEqualsResourcesWithSubjectAreas(List<ResourceWithSubjectArea> expecteds, List<ResourceWithSubjectArea> actuals) {
        MetamacRestAsserts.assertEqualsResources(Collections.unmodifiableList(expecteds), Collections.unmodifiableList(actuals));
        for (ResourceWithSubjectArea expected : expecteds) {
            for (ResourceWithSubjectArea actual : actuals) {
                if (expected.getId().equals(actual.getId())) {
                    MetamacRestAsserts.assertEqualsResource(expected.getSubjectArea(), actual.getSubjectArea());
                }
            }
        }
    }

}
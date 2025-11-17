package org.siemac.metamac.statistical_operations.rest.external.v1_0.utils;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.statistical.operations.core.domain.CollMethod;
import org.siemac.metamac.statistical.operations.core.domain.Cost;
import org.siemac.metamac.statistical.operations.core.domain.Family;
import org.siemac.metamac.statistical.operations.core.domain.Instance;
import org.siemac.metamac.statistical.operations.core.domain.InstanceType;
import org.siemac.metamac.statistical.operations.core.domain.OfficialityType;
import org.siemac.metamac.statistical.operations.core.domain.Operation;
import org.siemac.metamac.statistical.operations.core.domain.SurveySource;
import org.siemac.metamac.statistical.operations.core.domain.SurveyType;
import org.siemac.metamac.statistical.operations.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.operations.core.enume.domain.StatusEnum;
import org.siemac.metamac.statistical_operations.rest.external.v1_0.service.StatisticalOperationsRestExternalFacadeV10Test;

public class StatisticalOperationsCoreMocks {

    public Operation mockOperation1() {
        return mockOperation("1", ProcStatusEnum.PUBLISH_EXTERNALLY, Boolean.TRUE, mockFamily1RelatedEntity(), mockFamily2RelatedEntity());
    }

    public Operation mockOperation2() {
        return mockOperation("2", ProcStatusEnum.PUBLISH_EXTERNALLY, Boolean.TRUE, mockFamily1RelatedEntity());
    }

    public Operation mockOperation3() {
        return mockOperation("3", ProcStatusEnum.PUBLISH_EXTERNALLY, Boolean.TRUE, mockFamily1RelatedEntity());
    }

    public Operation mockOperation4() {
        return mockOperation("4", ProcStatusEnum.PUBLISH_EXTERNALLY, Boolean.TRUE, mockFamily1RelatedEntity());
    }

    public Operation mockOperation5() {
        return mockOperation("5", ProcStatusEnum.PUBLISH_EXTERNALLY, Boolean.TRUE, mockFamily1RelatedEntity());
    }

    public Operation mockOperation6() {
        return mockOperation("6", ProcStatusEnum.PUBLISH_EXTERNALLY, Boolean.TRUE, mockFamily1RelatedEntity());
    }

    public Operation mockOperation7() {
        return mockOperation("7", ProcStatusEnum.PUBLISH_EXTERNALLY, Boolean.TRUE, mockFamily2RelatedEntity());
    }

    public Operation mockOperation8() {
        return mockOperation("8", ProcStatusEnum.PUBLISH_EXTERNALLY, Boolean.TRUE, mockFamily2RelatedEntity());
    }

    public Operation mockOperation9() {
        return mockOperation("9", ProcStatusEnum.PUBLISH_EXTERNALLY, Boolean.TRUE, mockFamily2RelatedEntity());
    }

    public Operation mockOperation10() {
        return mockOperation("10", ProcStatusEnum.PUBLISH_EXTERNALLY, Boolean.FALSE, mockFamily2RelatedEntity());
    }

    public Family mockFamily1() {
        return mockFamily("1", ProcStatusEnum.PUBLISH_EXTERNALLY);
    }

    public Family mockFamily2() {
        return mockFamily("2", ProcStatusEnum.PUBLISH_EXTERNALLY);
    }

    public Family mockFamily3() {
        return mockFamily("3", ProcStatusEnum.PUBLISH_EXTERNALLY);
    }

    public Family mockFamily4() {
        return mockFamily("4", ProcStatusEnum.PUBLISH_EXTERNALLY);
    }

    public Family mockFamily15() {
        return mockFamily("15", ProcStatusEnum.PUBLISH_EXTERNALLY);
    }

    public Instance mockInstance1() {
        return mockInstance("1", ProcStatusEnum.PUBLISH_EXTERNALLY);
    }
    public Instance mockInstance2() {
        return mockInstance("2", ProcStatusEnum.PUBLISH_EXTERNALLY);
    }
    public Instance mockInstance3() {
        return mockInstance("3", ProcStatusEnum.PUBLISH_EXTERNALLY);
    }
    public Instance mockInstance4() {
        return mockInstance("4", ProcStatusEnum.PUBLISH_EXTERNALLY);
    }
    public Instance mockInstance15() {
        return mockInstance("15", ProcStatusEnum.PUBLISH_EXTERNALLY);
    }

    public PagedResult<Operation> mockOperationsPagedResult(String limit, String offset) {

        List<Operation> operations = new ArrayList<Operation>();
        int total = 10;
        int startRow = -1;
        int rowCount = -1;
        int pageSize = -1;
        if ((limit == null || "1000".equals(limit) || "25".equals(limit)) && (offset == null || "0".equals(offset))) {
            startRow = 0;
            rowCount = total;
            pageSize = total;
            operations.add(mockOperation1());
            operations.add(mockOperation2());
            operations.add(mockOperation3());
            operations.add(mockOperation4());
            operations.add(mockOperation5());
            operations.add(mockOperation6());
            operations.add(mockOperation7());
            operations.add(mockOperation8());
            operations.add(mockOperation9());
            operations.add(mockOperation10());
        } else if ("2".equals(limit) && "0".equals(offset)) {
            pageSize = Integer.valueOf(limit).intValue();
            startRow = Integer.valueOf(offset).intValue();
            rowCount = pageSize;
            operations.add(mockOperation1());
            operations.add(mockOperation2());
        } else if ("2".equals(limit) && "2".equals(offset)) {
            pageSize = Integer.valueOf(limit).intValue();
            startRow = Integer.valueOf(offset).intValue();
            rowCount = pageSize;
            operations.add(mockOperation3());
            operations.add(mockOperation4());
        } else if ("2".equals(limit) && "8".equals(offset)) {
            pageSize = Integer.valueOf(limit).intValue();
            startRow = Integer.valueOf(offset).intValue();
            rowCount = pageSize;
            operations.add(mockOperation9());
            operations.add(mockOperation10());
        } else if ("2".equals(limit) && "9".equals(offset)) {
            pageSize = Integer.valueOf(limit).intValue();
            startRow = Integer.valueOf(offset).intValue();
            // no results
            rowCount = 0;
        } else {
            fail("Limit or offset non supported. Limit = " + limit + ". Offset = " + offset);
        }

        return new PagedResult<Operation>(operations, startRow, rowCount, pageSize, total, -1);
    }

    public PagedResult<Operation> mockOperationsPagedResult(String limit, String offset, String query) {

        // Without query
        if (query == null) {
            return mockOperationsPagedResult(limit, offset);
        }

        // With query
        List<Operation> operations = new ArrayList<Operation>();
        int total = -1;
        int startRow = -1;
        int rowCount = -1;
        int pageSize = -1;

        String querySupported1 = StatisticalOperationsRestExternalFacadeV10Test.QUERY_OPERATION_ID_LIKE_1;
        String querySupported2 = StatisticalOperationsRestExternalFacadeV10Test.QUERY_OPERATION_ID_LIKE_1_AND_INDICATORS_SYSTEM;
        if (querySupported1.equals(query)) {
            total = 2;
            startRow = -1;
            rowCount = -1;
            pageSize = -1;
            if ((limit == null || "1000".equals(limit) || "25".equals(limit)) && (offset == null || "0".equals(offset))) {
                startRow = 0;
                rowCount = total;
                pageSize = total;
                operations.add(mockOperation1());
                operations.add(mockOperation10());
            } else if ("1".equals(limit) && "0".equals(offset)) {
                pageSize = Integer.valueOf(limit).intValue();
                startRow = Integer.valueOf(offset).intValue();
                rowCount = pageSize;
                operations.add(mockOperation1());
            } else if ("1".equals(limit) && "1".equals(offset)) {
                pageSize = Integer.valueOf(limit).intValue();
                startRow = Integer.valueOf(offset).intValue();
                rowCount = pageSize;
                operations.add(mockOperation10());
            } else {
                fail("Limit or offset non supported. Limit = " + limit + ". Offset = " + offset);
            }
        } else if (querySupported2.equals(query)) {
            total = 1;
            startRow = -1;
            rowCount = -1;
            pageSize = -1;
            if ((limit == null || "1000".equals(limit) || "25".equals(limit)) && (offset == null || "0".equals(offset))) {
                startRow = 0;
                rowCount = total;
                pageSize = total;
                operations.add(mockOperation1());
            } else {
                fail("Limit or offset non supported. Limit = " + limit + ". Offset = " + offset);
            }
        } else {
            fail("Query not supported = " + query);
        }
        return new PagedResult<Operation>(operations, startRow, rowCount, pageSize, total, -1);
    }

    public PagedResult<Operation> mockOperationsPagedResultByFamily1(String limit, String offset) {

        List<Operation> operations = new ArrayList<Operation>();
        int total = 6;
        int startRow = -1;
        int rowCount = -1;
        int pageSize = -1;
        if ((limit == null || "1000".equals(limit) || "25".equals(limit)) && (offset == null || "0".equals(offset))) {
            startRow = 0;
            rowCount = total;
            pageSize = total;
            operations.add(mockOperation1());
            operations.add(mockOperation2());
            operations.add(mockOperation3());
            operations.add(mockOperation4());
            operations.add(mockOperation5());
            operations.add(mockOperation6());
        } else if ("2".equals(limit) && "0".equals(offset)) {
            pageSize = Integer.valueOf(limit).intValue();
            startRow = Integer.valueOf(offset).intValue();
            rowCount = pageSize;
            operations.add(mockOperation1());
            operations.add(mockOperation2());
        } else if ("2".equals(limit) && "2".equals(offset)) {
            pageSize = Integer.valueOf(limit).intValue();
            startRow = Integer.valueOf(offset).intValue();
            rowCount = pageSize;
            operations.add(mockOperation3());
            operations.add(mockOperation4());
        } else if ("2".equals(limit) && "4".equals(offset)) {
            pageSize = Integer.valueOf(limit).intValue();
            startRow = Integer.valueOf(offset).intValue();
            rowCount = pageSize;
            operations.add(mockOperation5());
            operations.add(mockOperation6());
        } else if ("2".equals(limit) && "7".equals(offset)) {
            pageSize = Integer.valueOf(limit).intValue();
            startRow = Integer.valueOf(offset).intValue();
            // no results
            rowCount = 0;
        } else {
            fail("Limit or offset non supported. Limit = " + limit + ". Offset = " + offset);
        }

        return new PagedResult<Operation>(operations, startRow, rowCount, pageSize, total, -1);
    }

    public PagedResult<Operation> mockOperationsPagedResultByFamily2(String limit, String offset) {

        List<Operation> operations = new ArrayList<Operation>();
        int total = 4;
        int startRow = -1;
        int rowCount = -1;
        int pageSize = -1;
        if ("1".equals(limit) && "2".equals(offset)) {
            pageSize = Integer.valueOf(limit).intValue();
            startRow = Integer.valueOf(offset).intValue();
            rowCount = pageSize;
            operations.add(mockOperation8());
        } else {
            fail("Limit or offset non supported. Limit = " + limit + ". Offset = " + offset);
        }

        return new PagedResult<Operation>(operations, startRow, rowCount, pageSize, total, -1);
    }

    public PagedResult<Family> mockFamiliesPagedResult(String limit, String offset) {

        List<Family> families = new ArrayList<Family>();
        int total = 5;
        int startRow = -1;
        int rowCount = -1;
        int pageSize = -1;
        if ((limit == null || "1000".equals(limit) || "25".equals(limit)) && (offset == null || "0".equals(offset))) {
            startRow = 0;
            rowCount = total;
            pageSize = total;
            families.add(mockFamily1());
            families.add(mockFamily2());
            families.add(mockFamily3());
            families.add(mockFamily4());
            families.add(mockFamily15());
        } else if ("2".equals(limit) && "0".equals(offset)) {
            pageSize = Integer.valueOf(limit).intValue();
            startRow = Integer.valueOf(offset).intValue();
            rowCount = pageSize;
            families.add(mockFamily1());
            families.add(mockFamily2());
        } else if ("2".equals(limit) && "2".equals(offset)) {
            pageSize = Integer.valueOf(limit).intValue();
            startRow = Integer.valueOf(offset).intValue();
            rowCount = pageSize;
            families.add(mockFamily3());
            families.add(mockFamily4());
        } else if ("2".equals(limit) && "4".equals(offset)) {
            pageSize = Integer.valueOf(limit).intValue();
            startRow = Integer.valueOf(offset).intValue();
            rowCount = pageSize;
            families.add(mockFamily15());
        } else if ("2".equals(limit) && "9".equals(offset)) {
            pageSize = Integer.valueOf(limit).intValue();
            startRow = Integer.valueOf(offset).intValue();
            // no results
            rowCount = 0;
        } else {
            fail("Limit or offset non supported. Limit = " + limit + ". Offset = " + offset);
        }

        return new PagedResult<Family>(families, startRow, rowCount, pageSize, total, -1);
    }

    public PagedResult<Family> mockFamiliesPagedResult(String limit, String offset, String query) {

        // No queries
        if (query == null) {
            return mockFamiliesPagedResult(limit, offset);
        }

        // With queries
        int total = -1;
        int startRow = -1;
        int rowCount = -1;
        int pageSize = -1;
        List<Family> families = new ArrayList<Family>();

        String querySupported1 = StatisticalOperationsRestExternalFacadeV10Test.QUERY_FAMILY_ID_LIKE_1;
        if (querySupported1.equals(query)) {
            total = 2;
            startRow = -1;
            rowCount = -1;
            pageSize = -1;
            if ((limit == null || "1000".equals(limit) || "25".equals(limit)) && (offset == null || "0".equals(offset))) {
                startRow = 0;
                rowCount = total;
                pageSize = total;
                families.add(mockFamily1());
                families.add(mockFamily15());
            } else if ("1".equals(limit) && "0".equals(offset)) {
                pageSize = Integer.valueOf(limit).intValue();
                startRow = Integer.valueOf(offset).intValue();
                rowCount = pageSize;
                families.add(mockFamily1());
            } else if ("1".equals(limit) && "1".equals(offset)) {
                pageSize = Integer.valueOf(limit).intValue();
                startRow = Integer.valueOf(offset).intValue();
                rowCount = pageSize;
                families.add(mockFamily15());
            } else {
                fail("Limit or offset non supported. Limit = " + limit + ". Offset = " + offset);
            }
        } else {
            fail("Query non supported = " + query);
        }

        return new PagedResult<Family>(families, startRow, rowCount, pageSize, total, -1);
    }

    public PagedResult<Family> mockFamiliesNoPagedResultByOperation1() {

        List<Family> families = new ArrayList<Family>();
        families.add(mockFamily1());
        families.add(mockFamily2());

        int startRow = 0;
        int rowCount = 2;
        int pageSize = 2;
        int totalRows = 2;

        return new PagedResult<Family>(families, startRow, rowCount, pageSize, totalRows, -1);
    }

    public PagedResult<Instance> mockInstancesPagedResultByOperation1(String limit, String offset) {

        List<Instance> instances = new ArrayList<Instance>();
        int total = 5;
        int startRow = -1;
        int rowCount = -1;
        int pageSize = -1;
        if ((limit == null || "1000".equals(limit) || "25".equals(limit)) && (offset == null || "0".equals(offset))) {
            startRow = 0;
            rowCount = total;
            pageSize = total;
            instances.add(mockInstance1());
            instances.add(mockInstance2());
            instances.add(mockInstance3());
            instances.add(mockInstance4());
            instances.add(mockInstance15());
        } else if ("2".equals(limit) && "0".equals(offset)) {
            pageSize = Integer.valueOf(limit).intValue();
            startRow = Integer.valueOf(offset).intValue();
            rowCount = pageSize;
            instances.add(mockInstance1());
            instances.add(mockInstance2());
        } else if ("2".equals(limit) && "2".equals(offset)) {
            pageSize = Integer.valueOf(limit).intValue();
            startRow = Integer.valueOf(offset).intValue();
            rowCount = pageSize;
            instances.add(mockInstance3());
            instances.add(mockInstance4());
        } else if ("2".equals(limit) && "4".equals(offset)) {
            pageSize = Integer.valueOf(limit).intValue();
            startRow = Integer.valueOf(offset).intValue();
            rowCount = pageSize;
            instances.add(mockInstance15());
        } else if ("2".equals(limit) && "9".equals(offset)) {
            pageSize = Integer.valueOf(limit).intValue();
            startRow = Integer.valueOf(offset).intValue();
            // no results
            rowCount = 0;
        } else {
            fail("Limit or offset non supported. Limit = " + limit + ". Offset = " + offset);
        }

        return new PagedResult<Instance>(instances, startRow, rowCount, pageSize, total, -1);
    }

    public PagedResult<Instance> mockInstancesPagedResultByOperation1(String limit, String offset, String query) {

        // No queries
        if (query == null) {
            return mockInstancesPagedResultByOperation1(limit, offset);
        }

        // With queries
        int total = -1;
        int startRow = -1;
        int rowCount = -1;
        int pageSize = -1;
        List<Instance> instances = new ArrayList<Instance>();

        String querySupported1 = StatisticalOperationsRestExternalFacadeV10Test.QUERY_FAMILY_ID_LIKE_1;
        if (querySupported1.equals(query)) {
            total = 2;
            startRow = -1;
            rowCount = -1;
            pageSize = -1;
            if ((limit == null || "1000".equals(limit) || "25".equals(limit)) && (offset == null || "0".equals(offset))) {
                startRow = 0;
                rowCount = total;
                pageSize = total;
                instances.add(mockInstance1());
                instances.add(mockInstance15());
            } else if ("1".equals(limit) && "0".equals(offset)) {
                pageSize = Integer.valueOf(limit).intValue();
                startRow = Integer.valueOf(offset).intValue();
                rowCount = pageSize;
                instances.add(mockInstance1());
            } else if ("1".equals(limit) && "1".equals(offset)) {
                pageSize = Integer.valueOf(limit).intValue();
                startRow = Integer.valueOf(offset).intValue();
                rowCount = pageSize;
                instances.add(mockInstance15());
            } else {
                fail("Limit or offset non supported. Limit = " + limit + ". Offset = " + offset);
            }
        } else {
            fail("Query non supported = " + query);
        }

        return new PagedResult<Instance>(instances, startRow, rowCount, pageSize, total, -1);
    }

    /**
     * Operation with basic attributes. Do not use mockInstance to avoid cyclic method calls
     */
    private Operation mockOperationRelatedEntity(String subCode, ProcStatusEnum procStatus) {
        Operation operation = new Operation();
        operation.setCode("operation" + subCode);
        operation.setUrn(GeneratorUrnUtils.generateSiemacStatisticalOperationUrn(operation.getCode()));
        operation.setTitle(mockInternationalStringMetadata("operation", subCode));
        operation.setProcStatus(procStatus);
        operation.addInstance(mockInstanceRelatedEntity("4444", ProcStatusEnum.DRAFT, Integer.valueOf(0), operation.getCode()));
        operation.addInstance(mockInstanceRelatedEntity("22", ProcStatusEnum.PUBLISH_EXTERNALLY, Integer.valueOf(3), operation.getCode()));
        operation.addInstance(mockInstanceRelatedEntity("1", ProcStatusEnum.PUBLISH_EXTERNALLY, Integer.valueOf(2), operation.getCode()));
        operation.addInstance(mockInstanceRelatedEntity("333", ProcStatusEnum.PUBLISH_EXTERNALLY, Integer.valueOf(1), operation.getCode()));
        return operation;
    }

    private Family mockFamily1RelatedEntity() {
        return mockFamilyRelatedEntity("1", ProcStatusEnum.PUBLISH_INTERNALLY);
    }

    private Family mockFamily2RelatedEntity() {
        return mockFamilyRelatedEntity("2", ProcStatusEnum.PUBLISH_EXTERNALLY);
    }

    /**
     * Family with basic attributes. Do not use mockInstance to avoid cyclic method calls
     */
    private Family mockFamilyRelatedEntity(String subCode, ProcStatusEnum procStatus) {
        Family family = new Family();
        family.setCode("family" + subCode);
        family.setUrn(GeneratorUrnUtils.generateSiemacStatisticalFamilyUrn(family.getCode()));
        family.setTitle(mockInternationalStringMetadata("family", subCode));
        family.setProcStatus(procStatus);
        return family;
    }

    /**
     * Instance with basic attributes. Do not use mockInstance to avoid cyclic method calls
     */
    private Instance mockInstanceRelatedEntity(String subCode, ProcStatusEnum procStatus, Integer order, String operationCode) {
        Instance instance = new Instance();
        instance.setCode("instance" + subCode);
        instance.setUrn(GeneratorUrnUtils.generateSiemacStatisticalOperationInstanceUrn(operationCode, instance.getCode()));
        instance.setTitle(mockInternationalStringMetadata("instance", subCode));
        instance.setProcStatus(procStatus);
        instance.setOrder(order);
        return instance;
    }

    private SurveyType mockSurveyType(String code) {
        SurveyType surveyType = new SurveyType();
        surveyType.setDescription(mockInternationalStringMetadata(code, null));
        surveyType.setIdentifier(code);
        return surveyType;
    }

    private OfficialityType mockOfficialityType(String code) {
        OfficialityType officialityType = new OfficialityType();
        officialityType.setDescription(mockInternationalStringMetadata(code, null));
        officialityType.setIdentifier(code);
        return officialityType;
    }

    private InstanceType mockInstanceType(String code) {
        InstanceType instanceType = new InstanceType();
        instanceType.setDescription(mockInternationalStringMetadata(code, null));
        instanceType.setIdentifier(code);
        return instanceType;
    }

    private SurveySource mockSurveySource(String code) {
        SurveySource surveySource = new SurveySource();
        surveySource.setDescription(mockInternationalStringMetadata(code, null));
        surveySource.setIdentifier(code);
        return surveySource;
    }

    private CollMethod mockCollMethod(String code) {
        CollMethod collMethod = new CollMethod();
        collMethod.setDescription(mockInternationalStringMetadata(code, null));
        collMethod.setIdentifier(code);
        return collMethod;
    }

    private Cost mockCost(String code) {
        Cost cost = new Cost();
        cost.setDescription(mockInternationalStringMetadata(code, null));
        cost.setIdentifier(code);
        return cost;
    }

    private ExternalItem mockExternalItemSrm(String code, String subpathUrl, TypeExternalArtefactsEnum type) {
        ExternalItem target = new ExternalItem();
        target.setCode(code);
        if (TypeExternalArtefactsEnum.AGENCY.equals(type) || TypeExternalArtefactsEnum.CATEGORY.equals(type)) {
            target.setCodeNested(code + "Nested");
        }
        target.setUri("v1.0/" + subpathUrl + "/" + code);
        target.setUrn("urn:" + code);
        target.setUrnProvider(target.getUrn());
        target.setType(type);
        target.setTitle(mockInternationalStringMetadata(code, null));
        target.setManagementAppUrl(null);
        return target;
    }

    private ExternalItem mockExternalItemCommonMetadata(String code, String subpathUrl, TypeExternalArtefactsEnum type) {
        ExternalItem target = new ExternalItem();
        target.setCode(code);
        target.setCodeNested(null);
        target.setUri("v1.0/" + subpathUrl + "/" + code);
        target.setUrn("urn:" + code);
        target.setUrnProvider(target.getUrn());
        target.setType(type);
        target.setTitle(mockInternationalStringMetadata(code, null));
        target.setManagementAppUrl(null);
        return target;
    }

    private Operation mockOperation(String subCode, ProcStatusEnum procStatus, Boolean isIndicatorsSystem, Family... families) {

        Operation operation = new Operation();
        operation.setCode("operation" + subCode);
        operation.setUrn(GeneratorUrnUtils.generateSiemacStatisticalOperationUrn(operation.getCode()));
        operation.setTitle(mockInternationalStringMetadata("operation", subCode));
        operation.setAcronym(mockInternationalStringMetadata("acronym", subCode));
        if (families != null) {
            for (int i = 0; i < families.length; i++) {
                Family family = families[i];
                operation.addFamily(family);
            }
        }
        operation.setSubjectArea(mockExternalItemSrm("subjectArea1", "subjectAreas", TypeExternalArtefactsEnum.CATEGORY));
        operation.getSecondarySubjectAreas().add(mockExternalItemSrm("secundarySubjectArea1", "secundarySubjectAreas", TypeExternalArtefactsEnum.CATEGORY));
        operation.getSecondarySubjectAreas().add(mockExternalItemSrm("secundarySubjectArea22", "secundarySubjectAreas", TypeExternalArtefactsEnum.CATEGORY));
        operation.getSecondarySubjectAreas().add(mockExternalItemSrm("secundarySubjectArea333", "secundarySubjectAreas", TypeExternalArtefactsEnum.CATEGORY));
        operation.setObjective(mockInternationalStringMetadata("objetive", subCode));
        operation.setDescription(mockInternationalStringMetadata("description", subCode));
        operation.addInstance(mockInstanceRelatedEntity("4444", ProcStatusEnum.DRAFT, Integer.valueOf(0), operation.getCode()));
        operation.addInstance(mockInstanceRelatedEntity("22", ProcStatusEnum.PUBLISH_INTERNALLY, Integer.valueOf(3), operation.getCode()));
        operation.addInstance(mockInstanceRelatedEntity("1", ProcStatusEnum.PUBLISH_INTERNALLY, Integer.valueOf(2), operation.getCode()));
        operation.addInstance(mockInstanceRelatedEntity("333", ProcStatusEnum.PUBLISH_EXTERNALLY, Integer.valueOf(1), operation.getCode()));
        operation.setSurveyType(mockSurveyType("statisticalOperationIdentifier"));
        operation.setOfficialityType(mockOfficialityType("officialityType"));
        operation.setIndicatorSystem(isIndicatorsSystem);
        operation.getProducer().add(mockExternalItemSrm("producer1", "producers", TypeExternalArtefactsEnum.AGENCY));
        operation.getProducer().add(mockExternalItemSrm("producer22", "producers", TypeExternalArtefactsEnum.AGENCY));
        operation.getResponsible().add(mockExternalItemSrm("responsible1", "responsibles", TypeExternalArtefactsEnum.AGENCY));
        operation.getResponsible().add(mockExternalItemSrm("responsible22", "responsibles", TypeExternalArtefactsEnum.AGENCY));
        operation.getResponsible().add(mockExternalItemSrm("responsible333", "responsibles", TypeExternalArtefactsEnum.AGENCY));
        operation.getContributor().add(mockExternalItemSrm("contributor1", "contributors", TypeExternalArtefactsEnum.AGENCY));
        operation.getContributor().add(mockExternalItemSrm("contributor22", "contributors", TypeExternalArtefactsEnum.AGENCY));
        operation.setInternalInventoryDate(new DateTime(2012, 12, 1, 13, 15, 14, 0));
        operation.setCurrentlyActive(Boolean.FALSE);
        operation.setStatus(StatusEnum.DESIGN);
        operation.setProcStatus(procStatus);
        operation.getPublisher().add(mockExternalItemSrm("publisher1", "publishers", TypeExternalArtefactsEnum.AGENCY));
        operation.getPublisher().add(mockExternalItemSrm("publisher22", "publishers", TypeExternalArtefactsEnum.AGENCY));
        operation.getPublisher().add(mockExternalItemSrm("publisher333", "publishers", TypeExternalArtefactsEnum.AGENCY));
        operation.setRelPolUsAc(mockInternationalStringMetadata("relPolUsAc", subCode));
        operation.setReleaseCalendar(Boolean.TRUE);
        operation.setReleaseCalendarAccess("http://releaseCalendarAccess1");
        operation.getUpdateFrequency().add(mockExternalItemSrm("updateFrequency1", "updateFrequencies", TypeExternalArtefactsEnum.CODE));
        operation.getUpdateFrequency().add(mockExternalItemSrm("updateFrequency22", "updateFrequencies", TypeExternalArtefactsEnum.CODE));
        operation.getUpdateFrequency().add(mockExternalItemSrm("updateFrequency333", "updateFrequencies", TypeExternalArtefactsEnum.CODE));
        operation.getUpdateFrequency().add(mockExternalItemSrm("updateFrequency4444", "updateFrequencies", TypeExternalArtefactsEnum.CODE));
        operation.setInventoryDate(new DateTime(2013, 2, 4, 13, 15, 14, 0));
        operation.setRevPolicy(mockInternationalStringMetadata("revPolicy", subCode));
        operation.setRevPractice(mockInternationalStringMetadata("revPractice", subCode));
        operation.setCommonMetadata(mockExternalItemCommonMetadata("commonMetadata1", "nothing", TypeExternalArtefactsEnum.CONFIGURATION));
        operation.setSpecificLegalActs(mockInternationalStringMetadata("legalActs", "specific1"));
        operation.setSpecificDataSharing(mockInternationalStringMetadata("dataSharing", "specific1"));
        operation.setComment(mockInternationalStringMetadata("comment", subCode));
        operation.setNotes(mockInternationalStringMetadata("notes", subCode));

        return operation;
    }
    private Family mockFamily(String subCode, ProcStatusEnum procStatus) {

        Family family = new Family();
        family.setCode("family" + subCode);
        family.setUrn(GeneratorUrnUtils.generateSiemacStatisticalFamilyUrn(family.getCode()));
        family.setTitle(mockInternationalStringMetadata("family", subCode));
        family.setAcronym(mockInternationalStringMetadata("acronym", subCode));
        family.setDescription(mockInternationalStringMetadata("description", subCode));
        family.setInternalInventoryDate(new DateTime(2012, 12, 1, 13, 15, 14, 0));
        family.setProcStatus(procStatus);
        family.setInventoryDate(new DateTime(2013, 2, 4, 13, 15, 14, 0));
        family.addOperation(mockOperationRelatedEntity("1", ProcStatusEnum.PUBLISH_INTERNALLY));
        return family;
    }

    private Instance mockInstance(String subCode, ProcStatusEnum procStatus) {

        Instance instance = new Instance();
        instance.setOperation(mockOperationRelatedEntity("1", ProcStatusEnum.PUBLISH_INTERNALLY));
        instance.setCode("instance" + subCode);
        instance.setUrn(GeneratorUrnUtils.generateSiemacStatisticalOperationInstanceUrn(instance.getOperation().getCode(), instance.getCode()));
        instance.setTitle(mockInternationalStringMetadata("instance", subCode));
        instance.setAcronym(mockInternationalStringMetadata("acronym", subCode));
        instance.setOrder(Integer.valueOf(2));
        instance.setDataDescription(mockInternationalStringMetadata("dataDescription", subCode));
        instance.setStatisticalPopulation(mockInternationalStringMetadata("statisticalPopulation", subCode));
        instance.addStatisticalUnit(mockExternalItemSrm("statisticalUnit1", "statisticalUnits", TypeExternalArtefactsEnum.CONCEPT));
        instance.addStatisticalUnit(mockExternalItemSrm("statisticalUnit22", "statisticalUnits", TypeExternalArtefactsEnum.CONCEPT));
        instance.addGeographicGranularity(mockExternalItemSrm("geographicGranularity01", "codelists", TypeExternalArtefactsEnum.CODELIST));
        instance.addGeographicGranularity(mockExternalItemSrm("geographicGranularity02", "codelists", TypeExternalArtefactsEnum.CODELIST));
        instance.setGeographicComparability(mockInternationalStringMetadata("geographicComparability", subCode));
        instance.addTemporalGranularity(mockExternalItemSrm("temporalGranularity01", "codelists", TypeExternalArtefactsEnum.CODELIST));
        instance.addTemporalGranularity(mockExternalItemSrm("temporalGranularity02", "codelists", TypeExternalArtefactsEnum.CODELIST));
        instance.setTemporalComparability(mockInternationalStringMetadata("temporalComparability", subCode));
        instance.setBasePeriod("2012");
        instance.addUnitMeasure(mockExternalItemSrm("measure1", "codes", TypeExternalArtefactsEnum.CODE));
        instance.setStatConcDef(mockInternationalStringMetadata("statConcDef", subCode));
        instance.addStatConcDefList(mockExternalItemSrm("statConcDefList1", "statConcDefLists", TypeExternalArtefactsEnum.CONCEPT));
        instance.addStatConcDefList(mockExternalItemSrm("statConcDefList22", "statConcDefLists", TypeExternalArtefactsEnum.CONCEPT));
        instance.addStatConcDefList(mockExternalItemSrm("statConcDefList333", "statConcDefLists", TypeExternalArtefactsEnum.CONCEPT));
        instance.setClassSystem(mockInternationalStringMetadata("classSystem", subCode));
        instance.addClassSystemList(mockExternalItemSrm("classSystemList1", "classSystemLists", TypeExternalArtefactsEnum.CODELIST));
        instance.addClassSystemList(mockExternalItemSrm("classSystemList22", "classSystemLists", TypeExternalArtefactsEnum.CODELIST));
        instance.setInstanceType(mockInstanceType("instanceType1"));
        instance.setInternalInventoryDate(new DateTime(2012, 12, 1, 13, 15, 14, 0));
        instance.setProcStatus(procStatus);
        instance.setDocMethod(mockInternationalStringMetadata("docMethod", subCode));
        instance.setSurveySource(mockSurveySource("statisticalOperationSource1"));
        instance.setCollMethod(mockCollMethod("collMethod1"));
        instance.addPublicInformationSupplier(mockExternalItemSrm("publicInformationSupplier1", "publicInformationSuppliers", TypeExternalArtefactsEnum.CONCEPT));
        instance.addFreqColl(mockExternalItemSrm("freqColl1", "freqColls", TypeExternalArtefactsEnum.CATEGORY_SCHEME));
        instance.addFreqColl(mockExternalItemSrm("freqColl22", "freqColls", TypeExternalArtefactsEnum.CATEGORY_SCHEME));
        instance.setDataValidation(mockInternationalStringMetadata("dataValidation", subCode));
        instance.setDataCompilation(mockInternationalStringMetadata("dataCompilation", subCode));
        instance.setAdjustment(mockInternationalStringMetadata("adjustment", subCode));
        instance.setSeasonalAdjustment(mockInternationalStringMetadata("seasonalAdjustment", subCode));
        instance.setCostBurden(mockInternationalStringMetadata("costBurden", subCode));
        instance.addCost(mockCost("cost1"));
        instance.addCost(mockCost("cost22"));
        instance.addCost(mockCost("cost333"));
        instance.addCost(mockCost("cost4444"));
        instance.setInventoryDate(new DateTime(2013, 2, 4, 13, 15, 14, 0));
        instance.setQualityDoc(mockInternationalStringMetadata("qualityDoc", subCode));
        instance.setQualityAssure(mockInternationalStringMetadata("qualityAssure", subCode));
        instance.setQualityAssmnt(mockInternationalStringMetadata("qualityAssmnt", subCode));
        instance.setUserNeeds(mockInternationalStringMetadata("userNeeds", subCode));
        instance.setUserSat(mockInternationalStringMetadata("userSat", subCode));
        instance.setCompleteness(mockInternationalStringMetadata("completeness", subCode));
        instance.setTimeliness(mockInternationalStringMetadata("timeliness", subCode));
        instance.setPunctuality(mockInternationalStringMetadata("punctuality", subCode));
        instance.setAccuracyOverall(mockInternationalStringMetadata("accuracyOverall", subCode));
        instance.setSamplingErr(mockInternationalStringMetadata("samplingErr", subCode));
        instance.setNonsamplingErr(mockInternationalStringMetadata("nonsamplingErr", subCode));
        instance.setCoverageErr(mockInternationalStringMetadata("coverageErr", subCode));
        instance.setMeasurementErr(mockInternationalStringMetadata("measurementErr", subCode));
        instance.setNonResponseErr(mockInternationalStringMetadata("nonResponseErr", subCode));
        instance.setModelErr(mockInternationalStringMetadata("modelErr", subCode));
        instance.setProcessingErr(mockInternationalStringMetadata("processingErr", subCode));

        instance.setCoherXDomain(mockInternationalStringMetadata("coherXDom", subCode));
        instance.setCoherSubanualAnual(mockInternationalStringMetadata("coherSubanualAnual", subCode));
        instance.setCoherNationalAccounts(mockInternationalStringMetadata("coherNationalAccounts", subCode));
        instance.setCoherInternal(mockInternationalStringMetadata("coherInternal", subCode));
        instance.setComment(mockInternationalStringMetadata("comment", subCode));
        instance.setNotes(mockInternationalStringMetadata("notes", subCode));

        return instance;
    }
    public List<SurveyType> mockFindAllSurveyTypes() {
        List<SurveyType> surveyTypes = new ArrayList<SurveyType>();
        surveyTypes.add(mockSurveyType("statisticalOperationType1"));
        surveyTypes.add(mockSurveyType("statisticalOperationType2"));
        return surveyTypes;
    }

    public List<OfficialityType> mockFindAllOfficialityTypes() {
        List<OfficialityType> officialityTypes = new ArrayList<OfficialityType>();
        officialityTypes.add(mockOfficialityType("officialityType1"));
        officialityTypes.add(mockOfficialityType("officialityType2"));
        officialityTypes.add(mockOfficialityType("officialityType3"));
        return officialityTypes;
    }

    public List<InstanceType> mockFindAllInstanceTypes() {
        List<InstanceType> instanceTypes = new ArrayList<InstanceType>();
        instanceTypes.add(mockInstanceType("instanceType1"));
        instanceTypes.add(mockInstanceType("instanceType2"));
        return instanceTypes;
    }

    public List<SurveySource> mockFindAllSurveySources() {
        List<SurveySource> surveySources = new ArrayList<SurveySource>();
        surveySources.add(mockSurveySource("statisticalOperationSource1"));
        surveySources.add(mockSurveySource("statisticalOperationSource2"));
        return surveySources;
    }

    public List<CollMethod> mockFindAllCollMethods() {
        List<CollMethod> collMethods = new ArrayList<CollMethod>();
        collMethods.add(mockCollMethod("collMethod1"));
        collMethods.add(mockCollMethod("collMethod2"));
        return collMethods;
    }

    public List<Cost> mockFindAllCosts() {
        List<Cost> costs = new ArrayList<Cost>();
        costs.add(mockCost("cost1"));
        costs.add(mockCost("cost2"));
        return costs;
    }

    private InternationalString mockInternationalStringMetadata(String metadata, String subCode) {
        String subTitle = subCode != null ? metadata + subCode : metadata;
        String locale = "es";
        String label = subTitle + " en Español";
        return mockInternationalString(locale, label);
    }

    private InternationalString mockInternationalString(String locale, String label) {
        // Note: only one locale, because it is a set and change orden in xml responses, so only there are svn differences
        InternationalString internationalString = new InternationalString();
        LocalisedString internationalStringLocale1 = new LocalisedString();
        internationalStringLocale1.setLocale(locale);
        internationalStringLocale1.setLabel(label);
        internationalString.addText(internationalStringLocale1);
        return internationalString;
    }
}
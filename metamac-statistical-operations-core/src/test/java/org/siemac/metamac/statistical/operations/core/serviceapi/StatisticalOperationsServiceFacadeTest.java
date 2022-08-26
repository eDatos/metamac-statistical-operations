package org.siemac.metamac.statistical.operations.core.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.siemac.metamac.statistical.operations.core.utils.mocks.StatisticalOperationsDtoMocks.mockExternalItemDto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.common.test.utils.DirtyDatabase;
import org.siemac.metamac.common.test.utils.MetamacMocks;
import org.siemac.metamac.core.common.criteria.MetamacCriteria;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaDisjunctionRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPaginator;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction.OperationType;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.criteria.shared.MetamacCriteriaOrder;
import org.siemac.metamac.core.common.criteria.shared.MetamacCriteriaOrder.OrderTypeEnum;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.ent.domain.ExternalItemRepository;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.operations.core.criteria.FamilyCriteriaPropertyEnum;
import org.siemac.metamac.statistical.operations.core.criteria.InstanceCriteriaOrderEnum;
import org.siemac.metamac.statistical.operations.core.criteria.InstanceCriteriaPropertyEnum;
import org.siemac.metamac.statistical.operations.core.criteria.OperationCriteriaOrderEnum;
import org.siemac.metamac.statistical.operations.core.criteria.OperationCriteriaPropertyEnum;
import org.siemac.metamac.statistical.operations.core.dto.CollMethodDto;
import org.siemac.metamac.statistical.operations.core.dto.CostDto;
import org.siemac.metamac.statistical.operations.core.dto.FamilyBaseDto;
import org.siemac.metamac.statistical.operations.core.dto.FamilyDto;
import org.siemac.metamac.statistical.operations.core.dto.InstanceBaseDto;
import org.siemac.metamac.statistical.operations.core.dto.InstanceDto;
import org.siemac.metamac.statistical.operations.core.dto.InstanceTypeDto;
import org.siemac.metamac.statistical.operations.core.dto.OfficialityTypeDto;
import org.siemac.metamac.statistical.operations.core.dto.OperationBaseDto;
import org.siemac.metamac.statistical.operations.core.dto.OperationDto;
import org.siemac.metamac.statistical.operations.core.dto.SurveySourceDto;
import org.siemac.metamac.statistical.operations.core.dto.SurveyTypeDto;
import org.siemac.metamac.statistical.operations.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.operations.core.enume.domain.StatusEnum;
import org.siemac.metamac.statistical.operations.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.operations.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.operations.core.utils.StatisticalOperationsBaseTest;
import org.siemac.metamac.statistical.operations.core.utils.asserts.StatisticalOperationsAsserts;
import org.siemac.metamac.statistical.operations.core.utils.mocks.StatisticalOperationsMocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring based transactional test with DbUnit support.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-operations/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class StatisticalOperationsServiceFacadeTest extends StatisticalOperationsBaseTest implements StatisticalOperationsServiceFacadeTestBase {

    static final Logger                          LOGGER = LoggerFactory.getLogger(StatisticalOperationsServiceFacadeTest.class);

    @Autowired
    protected StatisticalOperationsServiceFacade statisticalOperationsServiceFacade;

    @Autowired
    protected ExternalItemRepository             externalItemRepository;

    /**************************************************************************
     * Survey Type
     **************************************************************************/

    @Override
    @Test
    @Transactional
    public void testFindAllSurveyTypes() throws MetamacException {
        List<SurveyTypeDto> surveyTypesList = statisticalOperationsServiceFacade.findAllSurveyTypes(getServiceContextAdministrador());
        assertTrue(!surveyTypesList.isEmpty());
    }

    @Override
    @Test
    @Transactional
    public void testFindSurveyTypeById() throws MetamacException {
        SurveyTypeDto surveyTypeDto = statisticalOperationsServiceFacade.findSurveyTypeById(getServiceContextAdministrador(), Long.valueOf(1));
        assertNotNull(surveyTypeDto);
    }

    /**************************************************************************
     * Instance Type
     **************************************************************************/

    @Override
    @Test
    @Transactional
    public void testFindAllInstanceTypes() throws MetamacException {
        List<InstanceTypeDto> instanceTypesList = statisticalOperationsServiceFacade.findAllInstanceTypes(getServiceContextAdministrador());
        assertTrue(!instanceTypesList.isEmpty());
    }

    @Override
    @Test
    @Transactional
    public void testFindInstanceTypeById() throws MetamacException {
        InstanceTypeDto instanceTypeDto = statisticalOperationsServiceFacade.findInstanceTypeById(getServiceContextAdministrador(), Long.valueOf(1));
        assertNotNull(instanceTypeDto);
    }

    /**************************************************************************
     * Survey Source
     **************************************************************************/

    @Override
    @Test
    @Transactional
    public void testFindAllSurveySources() throws MetamacException {
        List<SurveySourceDto> surveySourcesList = statisticalOperationsServiceFacade.findAllSurveySources(getServiceContextAdministrador());
        assertTrue(!surveySourcesList.isEmpty());
    }

    @Override
    @Test
    @Transactional
    public void testFindSurveySourceById() throws MetamacException {
        SurveySourceDto surveySourceDto = statisticalOperationsServiceFacade.findSurveySourceById(getServiceContextAdministrador(), Long.valueOf(1));
        assertNotNull(surveySourceDto);
    }

    /**************************************************************************
     * Officiality Type
     **************************************************************************/

    @Override
    @Test
    @Transactional
    public void testFindAllOfficialityTypes() throws MetamacException {
        List<OfficialityTypeDto> officialityTypesList = statisticalOperationsServiceFacade.findAllOfficialityTypes(getServiceContextAdministrador());
        assertTrue(!officialityTypesList.isEmpty());
    }

    @Override
    @Test
    @Transactional
    public void testFindOfficialityTypeById() throws MetamacException {
        OfficialityTypeDto officialityTypeDto = statisticalOperationsServiceFacade.findOfficialityTypeById(getServiceContextAdministrador(), Long.valueOf(1));
        assertNotNull(officialityTypeDto);
    }

    /**************************************************************************
     * Coll Methods
     **************************************************************************/

    @Override
    @Test
    @Transactional
    public void testFindAllCollMethods() throws Exception {
        List<CollMethodDto> collMethodsList = statisticalOperationsServiceFacade.findAllCollMethods(getServiceContextAdministrador());
        assertTrue(!collMethodsList.isEmpty());

    }

    @Override
    @Test
    @Transactional
    public void testFindCollMethodById() throws Exception {
        CollMethodDto collMethodDto = statisticalOperationsServiceFacade.findCollMethodById(getServiceContextAdministrador(), Long.valueOf(1));
        assertNotNull(collMethodDto);

    }

    /**************************************************************************
     * Costs
     **************************************************************************/

    @Override
    @Test
    @Transactional
    public void testFindAllCosts() throws Exception {
        List<CostDto> costsList = statisticalOperationsServiceFacade.findAllCosts(getServiceContextAdministrador());
        assertTrue(!costsList.isEmpty());

    }

    @Override
    @Test
    @Transactional
    public void testFindCostById() throws Exception {
        CostDto costDto = statisticalOperationsServiceFacade.findCostById(getServiceContextAdministrador(), Long.valueOf(1));
        assertNotNull(costDto);

    }

    /**************************************************************************
     * Family
     **************************************************************************/

    @Override
    @Test
    @Transactional
    public void testCreateFamily() throws MetamacException {
        int familiesBefore = statisticalOperationsServiceFacade.findAllFamilies(getServiceContextAdministrador()).size();

        FamilyDto familyDto = createFamilyDto();

        familyDto = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), familyDto);
        assertNotNull(familyDto);

        int familiesAfter = statisticalOperationsServiceFacade.findAllFamilies(getServiceContextAdministrador()).size();
        assertEquals(familiesBefore + 1, familiesAfter);
    }

    @Test
    @Transactional
    public void testCreateFamilyDuplicatedCode() throws MetamacException {
        FamilyDto persistedFamilyDto = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto());

        FamilyDto familyDto = createFamilyDto();
        familyDto.setCode(persistedFamilyDto.getCode());

        try {
            statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), familyDto);
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.FAMILY_ALREADY_EXIST_CODE_DUPLICATED.getCode(), e.getExceptionItems().get(0).getCode());
        }
    }

    @Override
    @Test
    @Transactional
    public void testUpdateFamily() throws MetamacException {
        String familyCode = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDtoForUpdate()).getCode();

        FamilyDto familyBeforeUpdate = statisticalOperationsServiceFacade.findFamilyByCode(getServiceContextAdministrador(), familyCode);

        // TITLE
        InternationalStringDto title = new InternationalStringDto();
        LocalisedStringDto title_es = new LocalisedStringDto();
        title_es.setLabel("MOD Título en español de familia");
        title_es.setLocale("es");
        LocalisedStringDto title_en = new LocalisedStringDto();
        title_en.setLabel("MOD Título en inglés de familia");
        title_en.setLocale("en");
        title.addText(title_es);
        title.addText(title_en);
        familyBeforeUpdate.setTitle(title);

        // DESCRIPTION
        InternationalStringDto description = new InternationalStringDto();
        LocalisedStringDto description_es = new LocalisedStringDto();
        description_es.setLabel("MOD Descripción en español de familia");
        description_es.setLocale("es");
        LocalisedStringDto description_en = new LocalisedStringDto();
        description_en.setLabel("MOD Descripción en inglés de familia");
        description_en.setLocale("en");
        description.addText(description_es);
        description.addText(description_en);
        familyBeforeUpdate.setDescription(description);

        FamilyDto familyAfterUpdate = statisticalOperationsServiceFacade.updateFamily(getServiceContextAdministrador(), familyBeforeUpdate);
        StatisticalOperationsAsserts.assertEqualsFamilyDto(familyBeforeUpdate, familyAfterUpdate);
    }

    @Test
    @Transactional
    public void testUpdateFamilyCodeUnmodifiable() throws MetamacException {
        // Create operation
        Long operationId = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing()).getId();
        statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationId);

        // Create family
        Long familyId = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDtoForUpdate()).getId();

        // Relate family with operation
        statisticalOperationsServiceFacade.addOperationForFamily(getServiceContextAdministrador(), familyId, operationId);

        // Publish family
        statisticalOperationsServiceFacade.publishInternallyFamily(getServiceContextTecnicoPlanificacion(), familyId);

        // Change family code
        FamilyDto familyDto = statisticalOperationsServiceFacade.findFamilyById(getServiceContextAdministrador(), familyId);
        familyDto.setCode("FAMILY_OTHER_CODE");

        try {
            statisticalOperationsServiceFacade.updateFamily(getServiceContextAdministrador(), familyDto);
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.METADATA_UNMODIFIABLE.getCode(), e.getExceptionItems().get(0).getCode());
        }
    }

    @Test
    @Transactional
    public void testUpdateFamilyWithoutDescription() throws MetamacException {
        FamilyDto familyDto = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDtoForUpdate());

        int familiesBefore = statisticalOperationsServiceFacade.findAllFamilies(getServiceContextAdministrador()).size();

        // TITLE
        InternationalStringDto title = new InternationalStringDto();
        LocalisedStringDto title_es = new LocalisedStringDto();
        title_es.setLabel("MOD Título en español de familia");
        title_es.setLocale("es");
        LocalisedStringDto title_en = new LocalisedStringDto();
        title_en.setLabel("MOD Título en inglés de familia");
        title_en.setLocale("en");
        title.addText(title_es);
        title.addText(title_en);
        familyDto.setTitle(title);

        // DESCRIPTION
        familyDto.setDescription(null);

        familyDto = statisticalOperationsServiceFacade.updateFamily(getServiceContextAdministrador(), familyDto);

        assertNotNull(familyDto);

        int familiesAfter = statisticalOperationsServiceFacade.findAllFamilies(getServiceContextAdministrador()).size();
        assertEquals(familiesBefore, familiesAfter);
    }

    @Test
    @Transactional
    public void testUpdateFamilyWithoutLocalisedString() throws MetamacException {
        FamilyDto familyDto = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDtoForUpdate());

        int familiesBefore = statisticalOperationsServiceFacade.findAllFamilies(getServiceContextAdministrador()).size();

        // TITLE
        InternationalStringDto title = new InternationalStringDto();
        LocalisedStringDto title_es = new LocalisedStringDto();
        title_es.setLabel("MOD Título en español de familia");
        title_es.setLocale("es");
        title.addText(title_es);
        familyDto.setTitle(title);

        // DESCRIPTION
        InternationalStringDto description = new InternationalStringDto();
        LocalisedStringDto description_es = new LocalisedStringDto();
        description_es.setLabel("MOD Descripción en español de familia");
        description_es.setLocale("es");
        description.addText(description_es);
        familyDto.setDescription(description);

        familyDto = statisticalOperationsServiceFacade.updateFamily(getServiceContextAdministrador(), familyDto);

        assertNotNull(familyDto);

        int familiesAfter = statisticalOperationsServiceFacade.findAllFamilies(getServiceContextAdministrador()).size();
        assertEquals(familiesBefore, familiesAfter);
    }

    @Test
    @Transactional
    public void testUpdateFamilyWithOperations() throws MetamacException {
        FamilyDto familyDto = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDtoForUpdate());

        int familiesBefore = statisticalOperationsServiceFacade.findAllFamilies(getServiceContextAdministrador()).size();

        OperationDto operation01 = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());
        statisticalOperationsServiceFacade.addOperationForFamily(getServiceContextAdministrador(), familyDto.getId(), operation01.getId());

        OperationDto operation02 = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());
        statisticalOperationsServiceFacade.addOperationForFamily(getServiceContextAdministrador(), familyDto.getId(), operation02.getId());

        // Check operations for family
        List<OperationBaseDto> operationsForFamilyBefore = statisticalOperationsServiceFacade.findOperationsForFamily(getServiceContextAdministrador(), familyDto.getId());
        assertEquals(2, operationsForFamilyBefore.size());

        // Get family after modified
        familyDto = statisticalOperationsServiceFacade.findFamilyById(getServiceContextAdministrador(), familyDto.getId());

        // TITLE
        InternationalStringDto title = new InternationalStringDto();
        LocalisedStringDto title_es = new LocalisedStringDto();
        title_es.setLabel("MOD Título en español de familia");
        title_es.setLocale("es");
        LocalisedStringDto title_en = new LocalisedStringDto();
        title_en.setLabel("MOD Título en inglés de familia");
        title_en.setLocale("en");
        title.addText(title_es);
        title.addText(title_en);
        familyDto.setTitle(title);

        // DESCRIPTION
        InternationalStringDto description = new InternationalStringDto();
        LocalisedStringDto description_es = new LocalisedStringDto();
        description_es.setLabel("MOD Descripción en español de familia");
        description_es.setLocale("es");
        LocalisedStringDto description_en = new LocalisedStringDto();
        description_en.setLabel("MOD Descripción en inglés de familia");
        description_en.setLocale("en");
        description.addText(description_es);
        description.addText(description_en);
        familyDto.setDescription(description);

        familyDto = statisticalOperationsServiceFacade.updateFamily(getServiceContextAdministrador(), familyDto);

        assertNotNull(familyDto);

        int familiesAfter = statisticalOperationsServiceFacade.findAllFamilies(getServiceContextAdministrador()).size();
        assertEquals(familiesBefore, familiesAfter);

        // Check operations for family
        List<OperationBaseDto> operationsForFamilyAfter = statisticalOperationsServiceFacade.findOperationsForFamily(getServiceContextAdministrador(), familyDto.getId());
        assertEquals(operationsForFamilyBefore.size(), operationsForFamilyAfter.size());
    }

    @Test
    @Transactional
    public void testUpdateFamilyOptimisticLockingError() throws Exception {
        Long id = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDtoForUpdate()).getId();

        // Retrieve family - session 1
        FamilyDto familyDtoSession1 = statisticalOperationsServiceFacade.findFamilyById(getServiceContextAdministrador(), id);
        assertEquals(Long.valueOf(0), familyDtoSession1.getOptimisticLockingVersion());
        familyDtoSession1.setCode("newCode");

        // Retrieve family - session 2
        FamilyDto familyDtoSession2 = statisticalOperationsServiceFacade.findFamilyById(getServiceContextAdministrador(), id);
        assertEquals(Long.valueOf(0), familyDtoSession1.getOptimisticLockingVersion());
        familyDtoSession2.setCode("newCode2");

        // Update family - session 1
        FamilyDto familyDtoSession1AfterUpdate = statisticalOperationsServiceFacade.updateFamily(getServiceContextAdministrador(), familyDtoSession1);
        assertEquals(Long.valueOf(1), familyDtoSession1AfterUpdate.getOptimisticLockingVersion());

        // Update family - session 2
        try {
            statisticalOperationsServiceFacade.updateFamily(getServiceContextAdministrador(), familyDtoSession2);
            fail("Optimistic locking");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEquals(ServiceExceptionType.OPTIMISTIC_LOCKING.getCode(), e.getExceptionItems().get(0).getCode());
            assertNull(e.getExceptionItems().get(0).getMessageParameters());
        }

        // Update family - session 1
        familyDtoSession1AfterUpdate.setCode("newCode1_secondUpdate");
        FamilyDto familyDtoSession1AfterUpdate2 = statisticalOperationsServiceFacade.updateFamily(getServiceContextAdministrador(), familyDtoSession1AfterUpdate);
        assertEquals(Long.valueOf(2), familyDtoSession1AfterUpdate2.getOptimisticLockingVersion());
    }

    @Override
    @Test
    @Transactional
    public void testDeleteFamily() throws MetamacException {
        // Create family
        FamilyDto familyDto = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDtoForUpdate());
        assertNotNull(familyDto);
        int numberFamilies = statisticalOperationsServiceFacade.findAllFamilies(getServiceContextAdministrador()).size();

        // Remove family
        statisticalOperationsServiceFacade.deleteFamily(getServiceContextAdministrador(), familyDto.getId());
        assertTrue(statisticalOperationsServiceFacade.findAllFamilies(getServiceContextAdministrador()).size() == (numberFamilies - 1));

        try {
            statisticalOperationsServiceFacade.findFamilyById(getServiceContextAdministrador(), familyDto.getId());
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.FAMILY_NOT_FOUND.getCode(), e.getExceptionItems().get(0).getCode());
        }
    }

    @Test
    @Transactional
    public void testDeleteFamilyWithOperations() throws MetamacException {
        // Create family
        FamilyDto familyDto = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDtoForUpdate());
        assertNotNull(familyDto);
        int numberFamilies = statisticalOperationsServiceFacade.findAllFamilies(getServiceContextAdministrador()).size();

        // Create operations
        OperationDto operation01 = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());
        statisticalOperationsServiceFacade.addOperationForFamily(getServiceContextAdministrador(), familyDto.getId(), operation01.getId());

        OperationDto operation02 = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());
        statisticalOperationsServiceFacade.addOperationForFamily(getServiceContextAdministrador(), familyDto.getId(), operation02.getId());

        // Check number of operations before delete
        int operationsBeforeDelete = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();

        // Remove family
        statisticalOperationsServiceFacade.deleteFamily(getServiceContextAdministrador(), familyDto.getId());
        assertTrue(statisticalOperationsServiceFacade.findAllFamilies(getServiceContextAdministrador()).size() == (numberFamilies - 1));

        // Check for deleted family
        try {
            statisticalOperationsServiceFacade.findFamilyById(getServiceContextAdministrador(), familyDto.getId());
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.FAMILY_NOT_FOUND.getCode(), e.getExceptionItems().get(0).getCode());
        }

        // Check number of operations after delete
        int operationsAfterDelete = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();
        assertEquals(operationsBeforeDelete, operationsAfterDelete);
    }

    @Test
    @Transactional
    public void testDeleteFamilyInternallyPublished() throws MetamacException {
        // Create family
        FamilyDto familyDto = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto());
        assertNotNull(familyDto);

        int familiesBefore = statisticalOperationsServiceFacade.findAllFamilies(getServiceContextAdministrador()).size();

        // Set PUBLISH_INTERNALLY procStatus
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto.getId());
        statisticalOperationsServiceFacade.addOperationForFamily(getServiceContextAdministrador(), familyDto.getId(), operationDto.getId());

        familyDto = statisticalOperationsServiceFacade.findFamilyById(getServiceContextAdministrador(), familyDto.getId());
        familyDto = statisticalOperationsServiceFacade.publishInternallyFamily(getServiceContextAdministrador(), familyDto.getId());

        // Delete family with an incorrect procStatus
        try {
            statisticalOperationsServiceFacade.deleteFamily(getServiceContextAdministrador(), familyDto.getId());
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.INVALID_PROC_STATUS.getCode(), e.getExceptionItems().get(0).getCode());
            assertEquals(1, e.getExceptionItems().size());
        }
        int familiesAfter = statisticalOperationsServiceFacade.findAllFamilies(getServiceContextAdministrador()).size();
        assertEquals(familiesBefore, familiesAfter);

    }

    @Test
    @Transactional
    public void testDeleteFamilyExternallyPublished() throws MetamacException {
        // Create family
        FamilyDto familyDto = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto());
        assertNotNull(familyDto);

        int familiesBefore = statisticalOperationsServiceFacade.findAllFamilies(getServiceContextAdministrador()).size();

        // Set operation procStatus
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        operationDto = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();
        operationDto = statisticalOperationsServiceFacade.publishExternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();
        statisticalOperationsServiceFacade.addOperationForFamily(getServiceContextAdministrador(), familyDto.getId(), operationDto.getId());

        // Set PUBLISH_EXTERNALLY procStatus
        familyDto = statisticalOperationsServiceFacade.findFamilyById(getServiceContextAdministrador(), familyDto.getId());
        familyDto = statisticalOperationsServiceFacade.publishInternallyFamily(getServiceContextAdministrador(), familyDto.getId());
        familyDto = statisticalOperationsServiceFacade.publishExternallyFamily(getServiceContextAdministrador(), familyDto.getId());

        // Delete family with an incorrect procStatus
        try {
            statisticalOperationsServiceFacade.deleteFamily(getServiceContextAdministrador(), familyDto.getId());
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.INVALID_PROC_STATUS.getCode(), e.getExceptionItems().get(0).getCode());
            assertEquals(1, e.getExceptionItems().size());
        }

        int familiesAfter = statisticalOperationsServiceFacade.findAllFamilies(getServiceContextAdministrador()).size();
        assertEquals(familiesBefore, familiesAfter);
    }

    @Override
    @Test
    @Transactional
    public void testFindAllFamilies() throws MetamacException {
        statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto());

        List<FamilyBaseDto> families = statisticalOperationsServiceFacade.findAllFamilies(getServiceContextAdministrador());
        assertTrue(!families.isEmpty());

    }

    @Override
    @Test
    @Transactional
    public void testFindFamilyByCondition() throws MetamacException {
        statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto());
        statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto());

        MetamacCriteria criteria = new MetamacCriteria();
        MetamacCriteriaResult<FamilyBaseDto> result = statisticalOperationsServiceFacade.findFamilyByCondition(getServiceContextAdministrador(), criteria);
        assertTrue(result.getResults().size() >= 2);
    }

    @Test
    @Transactional
    public void testFindFamilyByConditionWithoutConditions() throws MetamacException {
        statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto());
        statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto());

        MetamacCriteriaResult<FamilyBaseDto> result = statisticalOperationsServiceFacade.findFamilyByCondition(getServiceContextAdministrador(), null);
        assertTrue(result.getResults().size() >= 2);
    }

    @Test
    @Transactional
    public void testFindFamilyByConditionOrderByLastUpdatedDesc() throws MetamacException {
        Long id01 = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto()).getId();
        Long id02 = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto()).getId();
        Long id03 = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto()).getId();

        MetamacCriteria metamacCriteria = new MetamacCriteria();
        addOrderToCriteria(metamacCriteria, OperationCriteriaOrderEnum.LAST_UPDATED, OrderTypeEnum.DESC);

        MetamacCriteriaResult<FamilyBaseDto> families = statisticalOperationsServiceFacade.findFamilyByCondition(getServiceContextAdministrador(), metamacCriteria);
        assertTrue(families.getResults().get(0).getLastUpdated().after(families.getResults().get(1).getLastUpdated()));
        assertTrue(families.getResults().get(1).getLastUpdated().after(families.getResults().get(2).getLastUpdated()));
        assertEquals(id03, families.getResults().get(0).getId());
        assertEquals(id02, families.getResults().get(1).getId());
        assertEquals(id01, families.getResults().get(2).getId());
    }

    @Test
    @Transactional
    public void testFindFamilyByConditionCode() throws MetamacException {

        // Insert data
        FamilyDto familyDto01 = createFamilyDto();
        familyDto01.setCode("familyDto01");
        FamilyDto familyDto02 = createFamilyDto();
        familyDto02.setCode("familyDto02");
        FamilyDto familyDto03 = createFamilyDto();
        familyDto03.setCode("familyDto03");

        statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), familyDto01);
        statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), familyDto02);
        statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), familyDto03);

        // Retrieve "familyDto01" or "familyDto02"
        MetamacCriteria criteria = new MetamacCriteria();
        MetamacCriteriaDisjunctionRestriction disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.CODE.name(), "familyDto02", OperationType.EQ));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.CODE.name(), "familyDto01", OperationType.EQ));
        criteria.setRestriction(disjunction);

        MetamacCriteriaResult<FamilyBaseDto> result = statisticalOperationsServiceFacade.findFamilyByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Retrieve "familyDto01"
        criteria = new MetamacCriteria();
        criteria.setRestriction(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.CODE.name(), "familyDto01", OperationType.EQ));

        result = statisticalOperationsServiceFacade.findFamilyByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(1, result.getResults().size());

        // Retrieve "*Dto*"
        criteria = new MetamacCriteria();
        criteria.setRestriction(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.CODE.name(), "Dto", OperationType.ILIKE));

        result = statisticalOperationsServiceFacade.findFamilyByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(3, result.getResults().size());

    }

    @Test
    @Transactional
    public void testFindFamilyByConditionProcStatus() throws MetamacException {
        // Create and publish operation
        Long operation = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing()).getId();
        statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operation);
        statisticalOperationsServiceFacade.publishExternallyOperation(getServiceContextAdministrador(), operation);

        // Create families
        statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto());
        Long family02 = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto()).getId();
        Long family03 = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto()).getId();
        Long family04 = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto()).getId();
        Long family05 = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto()).getId();
        Long family06 = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto()).getId();

        // Relate families with operations
        statisticalOperationsServiceFacade.addOperationForFamily(getServiceContextAdministrador(), family02, operation);
        statisticalOperationsServiceFacade.addOperationForFamily(getServiceContextAdministrador(), family03, operation);
        statisticalOperationsServiceFacade.addOperationForFamily(getServiceContextAdministrador(), family04, operation);
        statisticalOperationsServiceFacade.addOperationForFamily(getServiceContextAdministrador(), family05, operation);
        statisticalOperationsServiceFacade.addOperationForFamily(getServiceContextAdministrador(), family06, operation);

        // Publish Families
        statisticalOperationsServiceFacade.publishInternallyFamily(getServiceContextAdministrador(), family02);
        statisticalOperationsServiceFacade.publishInternallyFamily(getServiceContextAdministrador(), family03);
        statisticalOperationsServiceFacade.publishInternallyFamily(getServiceContextAdministrador(), family04);
        statisticalOperationsServiceFacade.publishExternallyFamily(getServiceContextAdministrador(), family04);
        statisticalOperationsServiceFacade.publishInternallyFamily(getServiceContextAdministrador(), family05);
        statisticalOperationsServiceFacade.publishExternallyFamily(getServiceContextAdministrador(), family05);
        statisticalOperationsServiceFacade.publishInternallyFamily(getServiceContextAdministrador(), family06);
        statisticalOperationsServiceFacade.publishExternallyFamily(getServiceContextAdministrador(), family06);

        // Retrieve "PUBLISH_EXTERNALLY" or "PUBLISH_INTERNALLY"
        MetamacCriteria criteria = new MetamacCriteria();
        MetamacCriteriaDisjunctionRestriction disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.PROC_STATUS.name(), ProcStatusEnum.PUBLISH_EXTERNALLY, OperationType.EQ));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.PROC_STATUS.name(), ProcStatusEnum.PUBLISH_INTERNALLY, OperationType.EQ));
        criteria.setRestriction(disjunction);

        MetamacCriteriaResult<FamilyBaseDto> result = statisticalOperationsServiceFacade.findFamilyByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(5, result.getResults().size());

        // Retrieve "DRAFT"
        criteria = new MetamacCriteria();
        criteria.setRestriction(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.PROC_STATUS.name(), ProcStatusEnum.DRAFT, OperationType.EQ));

        result = statisticalOperationsServiceFacade.findFamilyByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(1, result.getResults().size());

        // Retrieve "PUBLISH_EXTERNALLY*"
        criteria = new MetamacCriteria();
        criteria.setRestriction(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.PROC_STATUS.name(), ProcStatusEnum.PUBLISH_EXTERNALLY, OperationType.EQ));

        result = statisticalOperationsServiceFacade.findFamilyByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(3, result.getResults().size());
    }

    @Test
    @Transactional
    public void testFindFamilyByConditionTitle() throws MetamacException {

        // Insert data
        FamilyDto familyDto01 = createFamilyDto();
        familyDto01.setCode("familyDto01");
        familyDto01.setTitle(MetamacMocks.mockInternationalStringDto("es", "Índice Precio de Consumo", "en", "Consumer Price Index"));

        FamilyDto familyDto02 = createFamilyDto();
        familyDto02.setCode("familyDto02");
        familyDto02.setTitle(MetamacMocks.mockInternationalStringDto("es", "Calidad de Vida", "en", "Quality of Life"));

        FamilyDto familyDto03 = createFamilyDto();
        familyDto03.setCode("familyDto03");
        familyDto03.setTitle(MetamacMocks.mockInternationalStringDto("es", "Condiciones de vida", "en", "Living"));

        statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), familyDto01);
        statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), familyDto02);
        statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), familyDto03);

        // Find "vida" or "quality" --> familyDto02 and familyDto03
        MetamacCriteria criteria = new MetamacCriteria();
        MetamacCriteriaDisjunctionRestriction disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.TITLE.name(), "vida", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.TITLE.name(), "quality", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        MetamacCriteriaResult<FamilyBaseDto> result = statisticalOperationsServiceFacade.findFamilyByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Find "vida" or "index" --> familyDto01, familyDto02 and familyDto03
        criteria = new MetamacCriteria();
        disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.TITLE.name(), "vida", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.TITLE.name(), "index", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        result = statisticalOperationsServiceFacade.findFamilyByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(3, result.getResults().size());

        // Find "vida" --> familyDto02 and familyDto03
        criteria = new MetamacCriteria();
        criteria.setRestriction(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.TITLE.name(), "vida", OperationType.ILIKE));

        result = statisticalOperationsServiceFacade.findFamilyByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Find title "index" or code "familyDto02"--> familyDto01 and familyDto02
        criteria = new MetamacCriteria();
        disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.CODE.name(), "familyDto02", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.TITLE.name(), "index", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        result = statisticalOperationsServiceFacade.findFamilyByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());
    }

    @Test
    @Transactional
    public void testFindFamilyByConditionAcronym() throws MetamacException {

        // Insert data
        FamilyDto familyDto01 = createFamilyDto();
        familyDto01.setCode("familyDto01");
        familyDto01.setAcronym(MetamacMocks.mockInternationalStringDto("es", "IPC", "en", "CPI"));

        FamilyDto familyDto02 = createFamilyDto();
        familyDto02.setCode("familyDto02");
        familyDto02.setAcronym(MetamacMocks.mockInternationalStringDto("es", "CALIDAD_VIDA", "en", "QL"));

        FamilyDto familyDto03 = createFamilyDto();
        familyDto03.setCode("familyDto03");
        familyDto03.setAcronym(MetamacMocks.mockInternationalStringDto("es", "CONDICIONES_VIDA", "en", "Living"));

        statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), familyDto01);
        statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), familyDto02);
        statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), familyDto03);

        // Find "vida" or "quality" --> familyDto02 and familyDto03
        MetamacCriteria criteria = new MetamacCriteria();
        MetamacCriteriaDisjunctionRestriction disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.ACRONYM.name(), "vida", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.ACRONYM.name(), "quality", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        MetamacCriteriaResult<FamilyBaseDto> result = statisticalOperationsServiceFacade.findFamilyByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Find "QL" and "condiciones" --> familyDto02 and familyDto03
        criteria = new MetamacCriteria();
        disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.ACRONYM.name(), "ql", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.ACRONYM.name(), "condiciones", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        result = statisticalOperationsServiceFacade.findFamilyByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Find "vida" --> familyDto02 and familyDto03
        criteria = new MetamacCriteria();
        criteria.setRestriction(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.ACRONYM.name(), "vida", OperationType.ILIKE));

        result = statisticalOperationsServiceFacade.findFamilyByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Find title "cp" or code "familyDto02"--> familyDto01 and familyDto02
        criteria = new MetamacCriteria();
        disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.CODE.name(), "familyDto02", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.ACRONYM.name(), "cp", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        result = statisticalOperationsServiceFacade.findFamilyByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());
    }

    @Test
    @Transactional
    public void testFindFamilyByConditionDescription() throws MetamacException {

        // Insert data
        FamilyDto familyDto01 = createFamilyDto();
        familyDto01.setCode("familyDto01");
        familyDto01.setDescription(MetamacMocks.mockInternationalStringDto("es", "Índice Precio de Consumo", "en", "Consumer Price Index"));

        FamilyDto familyDto02 = createFamilyDto();
        familyDto02.setCode("familyDto02");
        familyDto02.setDescription(MetamacMocks.mockInternationalStringDto("es", "Calidad de Vida", "en", "Quality of Life"));

        FamilyDto familyDto03 = createFamilyDto();
        familyDto03.setCode("familyDto03");
        familyDto03.setDescription(MetamacMocks.mockInternationalStringDto("es", "Condiciones de vida", "en", "Living"));

        statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), familyDto01);
        statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), familyDto02);
        statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), familyDto03);

        // Find "vida" or "quality" --> familyDto02 and familyDto03
        MetamacCriteria criteria = new MetamacCriteria();
        MetamacCriteriaDisjunctionRestriction disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.DESCRIPTION.name(), "vida", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.DESCRIPTION.name(), "quality", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        MetamacCriteriaResult<FamilyBaseDto> result = statisticalOperationsServiceFacade.findFamilyByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Find "vida" or "index" --> familyDto01, familyDto02 and familyDto03
        criteria = new MetamacCriteria();
        disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.DESCRIPTION.name(), "vida", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.DESCRIPTION.name(), "index", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        result = statisticalOperationsServiceFacade.findFamilyByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(3, result.getResults().size());

        // Find "vida" --> familyDto02 and familyDto03
        criteria = new MetamacCriteria();
        criteria.setRestriction(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.DESCRIPTION.name(), "vida", OperationType.ILIKE));

        result = statisticalOperationsServiceFacade.findFamilyByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Find title "index" or code "familyDto02"--> familyDto01 and familyDto02
        criteria = new MetamacCriteria();
        disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.CODE.name(), "familyDto02", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.DESCRIPTION.name(), "index", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        result = statisticalOperationsServiceFacade.findFamilyByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());
    }

    @Test
    @Transactional
    public void testFindFamilyByConditionOperationCode() throws MetamacException {

        // Insert data
        OperationDto operationDto01a = createOperationDtoForInternalPublishing();
        String operation01ACode = "ope01-a";
        operationDto01a.setCode(operation01ACode);
        OperationDto operationDto01b = createOperationDtoForInternalPublishing();
        String operation01BCode = "ope01-b";
        operationDto01b.setCode(operation01BCode);

        FamilyDto familyDto011 = createFamilyDto();
        familyDto011.setCode("familyDto011");
        FamilyDto familyDto012 = createFamilyDto();
        familyDto012.setCode("familyDto012");

        operationDto01a = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), operationDto01a);
        operationDto01b = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), operationDto01b);
        operationDto01a = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto01a.getId()).getContent();
        operationDto01b = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto01b.getId()).getContent();

        familyDto011 = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), familyDto011);
        familyDto012 = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), familyDto012);

        statisticalOperationsServiceFacade.addFamilyForOperation(getServiceContextAdministrador(), operationDto01a.getId(), familyDto011.getId());
        statisticalOperationsServiceFacade.addFamilyForOperation(getServiceContextAdministrador(), operationDto01b.getId(), familyDto011.getId());
        statisticalOperationsServiceFacade.addFamilyForOperation(getServiceContextAdministrador(), operationDto01a.getId(), familyDto012.getId());

        // Retrieve families with operation code "operationDto01-a" --> familyDto011 && familyDto012
        MetamacCriteria criteria = new MetamacCriteria();
        criteria.setRestriction(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.OPERATION_CODE.name(), operation01ACode, OperationType.EQ));

        MetamacCriteriaResult<FamilyBaseDto> result = statisticalOperationsServiceFacade.findFamilyByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Retrieve families with operation code "operationDto01-b" --> familyDto011
        criteria = new MetamacCriteria();
        criteria.setRestriction(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.OPERATION_CODE.name(), operation01BCode, OperationType.EQ));

        result = statisticalOperationsServiceFacade.findFamilyByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(1, result.getResults().size());

    }

    @Test
    @Transactional
    public void testFindFamilyByConditionOperationId() throws MetamacException {

        // Insert data
        OperationDto operationDto01a = createOperationDtoForInternalPublishing();
        String operation01ACode = "ope01-a";
        operationDto01a.setCode(operation01ACode);
        OperationDto operationDto01b = createOperationDtoForInternalPublishing();
        String operation01BCode = "ope01-b";
        operationDto01b.setCode(operation01BCode);

        FamilyDto familyDto011 = createFamilyDto();
        familyDto011.setCode("familyDto011");
        FamilyDto familyDto012 = createFamilyDto();
        familyDto012.setCode("familyDto012");

        operationDto01a = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), operationDto01a);
        operationDto01b = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), operationDto01b);
        operationDto01a = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto01a.getId()).getContent();
        operationDto01b = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto01b.getId()).getContent();

        familyDto011 = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), familyDto011);
        familyDto012 = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), familyDto012);

        statisticalOperationsServiceFacade.addFamilyForOperation(getServiceContextAdministrador(), operationDto01a.getId(), familyDto011.getId());
        statisticalOperationsServiceFacade.addFamilyForOperation(getServiceContextAdministrador(), operationDto01b.getId(), familyDto011.getId());
        statisticalOperationsServiceFacade.addFamilyForOperation(getServiceContextAdministrador(), operationDto01a.getId(), familyDto012.getId());

        // Retrieve families with operation code "operationDto01-a" --> familyDto011 && familyDto012
        MetamacCriteria criteria = new MetamacCriteria();
        criteria.setRestriction(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.OPERATION_ID.name(), operationDto01a.getId(), OperationType.EQ));

        MetamacCriteriaResult<FamilyBaseDto> result = statisticalOperationsServiceFacade.findFamilyByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Retrieve families with operation code "operationDto01-b" --> familyDto011
        criteria = new MetamacCriteria();
        criteria.setRestriction(new MetamacCriteriaPropertyRestriction(FamilyCriteriaPropertyEnum.OPERATION_ID.name(), operationDto01b.getId(), OperationType.EQ));

        result = statisticalOperationsServiceFacade.findFamilyByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(1, result.getResults().size());

    }

    @Test
    @Transactional
    public void testFindFamilyByConditionPaginated() throws MetamacException {
        statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto());
        statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto());
        statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto());

        MetamacCriteria criteria = new MetamacCriteria();

        MetamacCriteriaPaginator paginator = new MetamacCriteriaPaginator();
        paginator.setCountTotalResults(Boolean.TRUE);
        paginator.setMaximumResultSize(Integer.valueOf(2));
        criteria.setPaginator(paginator);

        {
            // Page 1
            criteria.getPaginator().setFirstResult(Integer.valueOf(0));

            MetamacCriteriaResult<FamilyBaseDto> result = statisticalOperationsServiceFacade.findFamilyByCondition(getServiceContextAdministrador(), criteria);
            assertEquals(2, result.getResults().size());
            assertTrue(result.getPaginatorResult().getTotalResults() >= 3);
            assertEquals(Integer.valueOf(0), result.getPaginatorResult().getFirstResult());
        }

        {
            // Page 2
            criteria.getPaginator().setFirstResult(Integer.valueOf(2));
            MetamacCriteriaResult<FamilyBaseDto> result = statisticalOperationsServiceFacade.findFamilyByCondition(getServiceContextAdministrador(), criteria);
            assertTrue(result.getPaginatorResult().getTotalResults() >= 1);
            assertEquals(Integer.valueOf(2), result.getPaginatorResult().getFirstResult());

        }
    }

    @Override
    @Test
    @Transactional
    public void testFindFamilyById() throws MetamacException {
        FamilyDto familyDto = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto());

        FamilyDto familyRetrieved = statisticalOperationsServiceFacade.findFamilyById(getServiceContextAdministrador(), familyDto.getId());

        assertNotNull(familyRetrieved);
        assertTrue(familyDto.getId().equals(familyRetrieved.getId()));
    }

    @Override
    @Test
    @Transactional
    public void testFindFamilyByCode() throws Exception {
        FamilyDto familyDto = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto());

        FamilyDto familyRetrieved = statisticalOperationsServiceFacade.findFamilyByCode(getServiceContextAdministrador(), familyDto.getCode());

        assertNotNull(familyRetrieved);
        assertTrue(familyDto.getCode().equals(familyRetrieved.getCode()));

    }

    @Override
    @Test
    @Transactional
    public void testFindFamilyByUrn() throws Exception {
        FamilyDto familyDto = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto());

        FamilyDto familyRetrieved = statisticalOperationsServiceFacade.findFamilyByUrn(getServiceContextAdministrador(), familyDto.getUrn());

        assertNotNull(familyRetrieved);
        assertTrue(familyDto.getUrn().equals(familyRetrieved.getUrn()));
    }

    @Test
    @Transactional
    public void testPublishInternallyFamilyError() throws MetamacException {
        FamilyDto familyDto = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto());
        int familiesBefore = statisticalOperationsServiceFacade.findAllFamilies(getServiceContextAdministrador()).size();

        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());
        statisticalOperationsServiceFacade.addOperationForFamily(getServiceContextAdministrador(), familyDto.getId(), operationDto.getId());

        familyDto = statisticalOperationsServiceFacade.findFamilyById(getServiceContextAdministrador(), familyDto.getId());

        try {
            familyDto = statisticalOperationsServiceFacade.publishInternallyFamily(getServiceContextAdministrador(), familyDto.getId());
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.FAMILY_WITHOUT_PUBLISHED_INTERNALLY_OPERATIONS.getCode(), e.getExceptionItems().get(0).getCode());
            assertEquals(1, e.getExceptionItems().size());
        }

        int familiesAfter = statisticalOperationsServiceFacade.findAllFamilies(getServiceContextAdministrador()).size();
        assertTrue(familiesBefore == familiesAfter);
    }

    @Override
    @Test
    @Transactional
    public void testPublishInternallyFamily() throws MetamacException {
        // Create operation with ProcStatus PUBLISH_EXTERNALLY
        OperationDto operationDto = createOperationDtoForInternalPublishing();
        operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), operationDto);
        operationDto = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();
        assertNotNull(operationDto.getInternalInventoryDate());
        assertEquals(ProcStatusEnum.PUBLISH_INTERNALLY, operationDto.getProcStatus());

        // Create family
        FamilyDto familyDto = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto());
        int familiesBefore = statisticalOperationsServiceFacade.findAllFamilies(getServiceContextAdministrador()).size();

        // Associate
        List<OperationBaseDto> operationsForFamily = statisticalOperationsServiceFacade.addOperationForFamily(getServiceContextAdministrador(), familyDto.getId(), operationDto.getId());
        assertNotNull(operationsForFamily);

        // Reload family
        familyDto = statisticalOperationsServiceFacade.findFamilyById(getServiceContextAdministrador(), familyDto.getId());

        // Publish family
        familyDto = statisticalOperationsServiceFacade.publishInternallyFamily(getServiceContextAdministrador(), familyDto.getId());

        // Validations
        assertNotNull(familyDto);
        assertNotNull(familyDto.getInternalInventoryDate());
        assertTrue(ProcStatusEnum.PUBLISH_INTERNALLY.equals(familyDto.getProcStatus()));
        int familiesAfter = statisticalOperationsServiceFacade.findAllFamilies(getServiceContextAdministrador()).size();
        assertTrue(familiesBefore == familiesAfter);
    }

    @Override
    @Test
    @Transactional
    public void testPublishExternallyFamily() throws MetamacException {

        // Create operation with ProcStatus PUBLISH_EXTERNALLY
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        operationDto = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();
        operationDto = statisticalOperationsServiceFacade.publishExternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();
        assertNotNull(operationDto.getInternalInventoryDate());
        assertNotNull(operationDto.getInventoryDate());
        assertEquals(ProcStatusEnum.PUBLISH_EXTERNALLY, operationDto.getProcStatus());

        // Create family
        FamilyDto familyDto = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto());

        int familiesBefore = statisticalOperationsServiceFacade.findAllFamilies(getServiceContextAdministrador()).size();

        // Associate
        List<OperationBaseDto> operationsForFamily = statisticalOperationsServiceFacade.addOperationForFamily(getServiceContextAdministrador(), familyDto.getId(), operationDto.getId());
        assertNotNull(operationsForFamily);

        // Reload family
        familyDto = statisticalOperationsServiceFacade.findFamilyById(getServiceContextAdministrador(), familyDto.getId());

        // Publish family
        familyDto = statisticalOperationsServiceFacade.publishInternallyFamily(getServiceContextAdministrador(), familyDto.getId());
        familyDto = statisticalOperationsServiceFacade.publishExternallyFamily(getServiceContextAdministrador(), familyDto.getId());
        assertTrue(ProcStatusEnum.PUBLISH_EXTERNALLY.equals(familyDto.getProcStatus()));

        // Validations
        assertNotNull(familyDto);
        assertNotNull(familyDto.getInternalInventoryDate());
        assertNotNull(familyDto.getInventoryDate());
        assertTrue(ProcStatusEnum.PUBLISH_EXTERNALLY.equals(familyDto.getProcStatus()));
        int familiesAfter = statisticalOperationsServiceFacade.findAllFamilies(getServiceContextAdministrador()).size();
        assertEquals(familiesBefore, familiesAfter);

        // Check that family can't be internally published
        try {
            statisticalOperationsServiceFacade.publishInternallyFamily(getServiceContextAdministrador(), familyDto.getId());
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.INVALID_PROC_STATUS.getCode(), e.getExceptionItems().get(0).getCode());
        }
    }

    @Override
    @Test
    @Transactional
    public void testFindOperationsForFamily() throws MetamacException {
        // Create operation
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());
        assertNotNull(operationDto);

        // Create family
        FamilyDto familyDto = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto());
        assertNotNull(familyDto);

        // Associate
        List<OperationBaseDto> operationsForFamily = statisticalOperationsServiceFacade.addOperationForFamily(getServiceContextAdministrador(), familyDto.getId(), operationDto.getId());

        assertNotNull(operationsForFamily);
    }

    @Override
    @Test
    @Transactional
    public void testAddOperationForFamily() throws MetamacException {

        // Create operation
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());
        assertNotNull(operationDto);

        // Create family
        FamilyDto familyDto = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto());
        assertNotNull(familyDto);

        int familiesBefore = statisticalOperationsServiceFacade.findAllFamilies(getServiceContextAdministrador()).size();

        // Associate
        statisticalOperationsServiceFacade.addOperationForFamily(getServiceContextAdministrador(), familyDto.getId(), operationDto.getId());

        assertNotNull(statisticalOperationsServiceFacade.findOperationsForFamily(getServiceContextAdministrador(), familyDto.getId()));

        // Check number of families
        int familiesAfter = statisticalOperationsServiceFacade.findAllFamilies(getServiceContextAdministrador()).size();
        assertEquals(familiesBefore, familiesAfter);
    }

    @Override
    @Test
    @Transactional
    public void testRemoveOperationForFamily() throws MetamacException {
        // Create operation
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());
        assertNotNull(operationDto);

        // Create family
        FamilyDto familyDto = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto());
        assertNotNull(familyDto);

        int familiesBefore = statisticalOperationsServiceFacade.findAllFamilies(getServiceContextAdministrador()).size();

        // Associate
        statisticalOperationsServiceFacade.addOperationForFamily(getServiceContextAdministrador(), familyDto.getId(), operationDto.getId());

        assertNotNull(statisticalOperationsServiceFacade.findOperationsForFamily(getServiceContextAdministrador(), familyDto.getId()));

        // Remove
        statisticalOperationsServiceFacade.removeOperationForFamily(getServiceContextAdministrador(), familyDto.getId(), operationDto.getId());
        assertTrue(statisticalOperationsServiceFacade.findOperationsForFamily(getServiceContextAdministrador(), familyDto.getId()).isEmpty());

        // Check number of families
        int familiesAfter = statisticalOperationsServiceFacade.findAllFamilies(getServiceContextAdministrador()).size();
        assertEquals(familiesBefore, familiesAfter);
    }

    /**************************************************************************
     * Operation
     **************************************************************************/

    @Override
    @Test
    @Transactional
    public void testCreateOperation() throws MetamacException {

        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());
        assertNotNull(operationDto);

        // Check number of operations
        int operationsAfter = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();
        assertEquals(1, operationsAfter);
    }

    @Test
    @Transactional
    public void testCreateOperationErrorCodeWithIllegalLength() throws MetamacException {
        expectedMetamacException(new MetamacException(ServiceExceptionType.UNKNOWN, "validation failed for: org.siemac.metamac.statistical.operations.core.domain.Operation"));

        OperationDto operationDto = createOperationDto();
        operationDto.setCode("CODE_HAS_INVALID_LENGTH");

        statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), operationDto);
    }

    @Test
    @Transactional
    public void testCreateOperationWithSpecificLegalActs() throws MetamacException {
        OperationDto expected = createOperationDto();
        expected.setSpecificLegalActs(StatisticalOperationsMocks.mockInternationalStringDto());

        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), expected);
        assertNotNull(operationDto);

        StatisticalOperationsAsserts.assertEqualsInternationalStringDto(expected.getSpecificLegalActs(), operationDto.getSpecificLegalActs());
    }

    @Test
    @Transactional
    public void testCreateOperationWithSpecificDataSharing() throws MetamacException {
        OperationDto expected = createOperationDto();
        expected.setSpecificDataSharing(StatisticalOperationsMocks.mockInternationalStringDto());

        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), expected);
        assertNotNull(operationDto);

        StatisticalOperationsAsserts.assertEqualsInternationalStringDto(expected.getSpecificDataSharing(), operationDto.getSpecificDataSharing());
    }

    @Test
    @Transactional
    public void testCreateOperationDuplicatedCode() throws MetamacException {
        OperationDto persistedOperationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());

        OperationDto operationDto = createOperationDto();
        operationDto.setCode(persistedOperationDto.getCode());

        try {
            statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), operationDto);
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.OPERATION_ALREADY_EXIST_CODE_DUPLICATED.getCode(), e.getExceptionItems().get(0).getCode());
        }
    }

    @Override
    @Test
    @Transactional
    public void testUpdateOperation() throws Exception {
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());
        assertNotNull(operationDto);

        int operationsBefore = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();

        // TITLE
        InternationalStringDto title = new InternationalStringDto();
        LocalisedStringDto title_es = new LocalisedStringDto();
        title_es.setLabel("MOD Título en español de operacion");
        title_es.setLocale("es");
        LocalisedStringDto title_en = new LocalisedStringDto();
        title_en.setLabel("MOD Título en inglés de operacion");
        title_en.setLocale("en");
        title.addText(title_es);
        title.addText(title_en);
        operationDto.setTitle(title);

        // DESCRIPTION
        InternationalStringDto description = new InternationalStringDto();
        LocalisedStringDto description_es = new LocalisedStringDto();
        description_es.setLabel("MOD Descripción en español de operacion");
        description_es.setLocale("es");
        LocalisedStringDto description_en = new LocalisedStringDto();
        description_en.setLabel("MOD Descripción en inglés de operacion");
        description_en.setLocale("en");
        description.addText(description_es);
        description.addText(description_en);
        operationDto.setDescription(description);

        operationDto = statisticalOperationsServiceFacade.updateOperation(getServiceContextAdministrador(), operationDto);

        assertNotNull(operationDto);

        // Check number of operations
        int operationsAfter = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();
        assertEquals(operationsBefore, operationsAfter);
    }

    @Test
    @Transactional
    public void testUpdateOperationCodeUnmodifiable() throws MetamacException {
        // Create operation
        Long operationId = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing()).getId();
        statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationId);

        // Change operation code
        OperationDto operationDto = statisticalOperationsServiceFacade.findOperationById(getServiceContextAdministrador(), operationId);
        operationDto.setCode("OPERATION_OTHER_CODE");

        try {
            statisticalOperationsServiceFacade.updateOperation(getServiceContextAdministrador(), operationDto);
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.METADATA_UNMODIFIABLE.getCode(), e.getExceptionItems().get(0).getCode());
        }
    }

    @Test
    @Transactional
    public void testUpdateOperationWithDescriptionWithoutLocales() throws Exception {
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());
        assertNotNull(operationDto);

        // DESCRIPTION
        InternationalStringDto description = new InternationalStringDto();
        operationDto.setDescription(description);

        try {
            statisticalOperationsServiceFacade.updateOperation(getServiceContextAdministrador(), operationDto);
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.METADATA_REQUIRED.getCode(), e.getExceptionItems().get(0).getCode());
            assertEquals(ServiceExceptionParameters.OPERATION_DESCRIPTION, e.getExceptionItems().get(0).getMessageParameters()[0]);
        }

    }

    @Test
    @Transactional
    public void testUpdateOperationStatus() throws Exception {
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());
        assertNotNull(operationDto);

        int operationsBefore = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();

        // STATUS
        operationDto.setStatus(StatusEnum.DESIGN);

        operationDto = statisticalOperationsServiceFacade.updateOperation(getServiceContextAdministrador(), operationDto);

        assertNotNull(operationDto);
        assertEquals(StatusEnum.DESIGN, operationDto.getStatus());

        // Check number of operations
        int operationsAfter = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();
        assertEquals(operationsBefore, operationsAfter);
    }

    @Test
    @Transactional
    public void testUpdateOperationWithExternalItems() throws Exception {
        // Create operation
        int externalItemsBefore = externalItemRepository.findAll().size();

        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoWithProducer());

        int externalItemsAfter = externalItemRepository.findAll().size();
        assertEquals(externalItemsBefore + 3, externalItemsAfter);

        // ADD PRODUCER
        externalItemsBefore = externalItemRepository.findAll().size();

        operationDto.addProducer(mockExternalItemDto("ISTAC", "/uri/test/agency?mod", "URN:AGENCY:ISTAC?MOD", null, TypeExternalArtefactsEnum.AGENCY));
        operationDto = statisticalOperationsServiceFacade.updateOperation(getServiceContextAdministrador(), operationDto);

        externalItemsAfter = externalItemRepository.findAll().size();
        assertEquals(externalItemsBefore + 1, externalItemsAfter);

        // PRODUCER: CLEAR AND ADD TWO ELEMENTS
        externalItemsBefore = externalItemRepository.findAll().size();

        operationDto.getProducer().clear();
        operationDto.addProducer(mockExternalItemDto("ISTAC", "/uri/test/agency", "URN:AGENCY:ISTAC?REMOVE", null, TypeExternalArtefactsEnum.AGENCY));
        operationDto.addProducer(mockExternalItemDto("INE", "/uri/test/agency", "URN:AGENCY:INE?REMOVE", null, TypeExternalArtefactsEnum.AGENCY));
        operationDto = statisticalOperationsServiceFacade.updateOperation(getServiceContextAdministrador(), operationDto);

        externalItemsAfter = externalItemRepository.findAll().size();
        assertEquals(externalItemsBefore - 1, externalItemsAfter);

        // PRODUCER: REMOVE ALL
        externalItemsBefore = externalItemRepository.findAll().size();

        operationDto.removeAllProducer();
        operationDto = statisticalOperationsServiceFacade.updateOperation(getServiceContextAdministrador(), operationDto);

        externalItemsAfter = externalItemRepository.findAll().size();
        assertEquals(externalItemsBefore - 2, externalItemsAfter);

    }

    @Test
    @Transactional
    public void testUpdateOperationUpdatingAddingAndRemovingExternalItems() throws Exception {

        OperationDto operationDto = createOperationDto();
        operationDto.getSecondarySubjectAreas().add(mockExternalItemDto("HEALTH1", "/uri/test/category1", "URN:CATEGORY:HEALTH", "URN:CATEGORY:HEALTH_provider", TypeExternalArtefactsEnum.CATEGORY));
        operationDto.getSecondarySubjectAreas().add(mockExternalItemDto("HEALTH2", "/uri/test/category2", "URN:CATEGORY:HEALTH2", null, TypeExternalArtefactsEnum.CATEGORY));
        operationDto.getSecondarySubjectAreas().add(mockExternalItemDto("HEALTH3", "/uri/test/category3", "URN:CATEGORY:HEALTH3", null, TypeExternalArtefactsEnum.CATEGORY));

        // Create
        operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), operationDto);
        assertEquals(3, operationDto.getSecondarySubjectAreas().size());
        assertNotNull(getExternalItemDtoByCode(operationDto.getSecondarySubjectAreas(), "HEALTH1"));
        assertNotNull(getExternalItemDtoByCode(operationDto.getSecondarySubjectAreas(), "HEALTH2"));
        assertNotNull(getExternalItemDtoByCode(operationDto.getSecondarySubjectAreas(), "HEALTH3"));
        for (ExternalItemDto externalItemDto : operationDto.getSecondarySubjectAreas()) {
            assertNotNull(externalItemDto.getId());
        }
        // Delete one external item
        ExternalItemDto externalItemDtoToRemove = getExternalItemDtoByCode(operationDto.getSecondarySubjectAreas(), "HEALTH2");
        operationDto.removeSecondarySubjectArea(externalItemDtoToRemove);
        // Add two
        operationDto.getSecondarySubjectAreas().add(mockExternalItemDto("HEALTH4", "/uri/test/category4", "URN:CATEGORY:HEALTH4", null, TypeExternalArtefactsEnum.CATEGORY));
        operationDto.getSecondarySubjectAreas().add(mockExternalItemDto("HEALTH5", "/uri/test/category5", "URN:CATEGORY:HEALTH5", null, TypeExternalArtefactsEnum.CATEGORY));

        // Update
        operationDto = statisticalOperationsServiceFacade.updateOperation(getServiceContextAdministrador(), operationDto);
        assertEquals(4, operationDto.getSecondarySubjectAreas().size());
        assertNotNull(getExternalItemDtoByCode(operationDto.getSecondarySubjectAreas(), "HEALTH1"));
        assertNotNull(getExternalItemDtoByCode(operationDto.getSecondarySubjectAreas(), "HEALTH3"));
        assertNotNull(getExternalItemDtoByCode(operationDto.getSecondarySubjectAreas(), "HEALTH4"));
        assertNotNull(getExternalItemDtoByCode(operationDto.getSecondarySubjectAreas(), "HEALTH5"));
    }

    @Test
    @Transactional
    public void testUpdateOperationWithoutExternalItemsPreviuslySave() throws Exception {
        // Create operation
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());

        // ADD REGIONAL CONTRIBUTOR
        int externalItemsBefore = externalItemRepository.findAll().size();

        operationDto.addRegionalContributor(mockExternalItemDto("ISTAC", "/uri/test/agency?remove", "URN:AGENCY:ISTAC?REMOVE", null, TypeExternalArtefactsEnum.AGENCY));
        operationDto.addRegionalContributor(mockExternalItemDto("INE", "/uri/test/agency?remove", "URN:AGENCY:INE?REMOVE", null, TypeExternalArtefactsEnum.AGENCY));

        operationDto = statisticalOperationsServiceFacade.updateOperation(getServiceContextAdministrador(), operationDto);

        int externalItemsAfter = externalItemRepository.findAll().size();
        assertEquals(externalItemsBefore + 2, externalItemsAfter);

        // CLEAR REGIONAL CONTRIBUTOR
        externalItemsBefore = externalItemRepository.findAll().size();

        operationDto.getRegionalContributor().clear();
        operationDto.getRegionalContributor().addAll(new ArrayList<ExternalItemDto>());

        operationDto = statisticalOperationsServiceFacade.updateOperation(getServiceContextAdministrador(), operationDto);

        externalItemsAfter = externalItemRepository.findAll().size();
        assertEquals(externalItemsBefore - 2, externalItemsAfter);

    }

    @Test
    @Transactional
    public void testCreateOperationWithIncorrectReleaseCalendarAccess() throws MetamacException {
        int operationsBefore = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();

        OperationDto operationDto = createOperationDto();
        operationDto.setReleaseCalendar(true);
        operationDto.setReleaseCalendarAccess("INCORRECT URL");

        try {
            operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), operationDto);
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.METADATA_INVALID_URL.getCode(), e.getExceptionItems().get(0).getCode());
        }

        // Check number of operations
        int operationsAfter = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();
        assertEquals(operationsBefore, operationsAfter);
    }

    @Test
    @Transactional
    public void testUpdateOperationWithList() throws Exception {
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoWithOfficialityType());
        assertNotNull(operationDto);
        int operationsBefore = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();

        // MODIFY OFFICIALITY TYPE
        int officialityTypesBefore = statisticalOperationsServiceFacade.findAllOfficialityTypes(getServiceContextAdministrador()).size();

        operationDto.setOfficialityType(statisticalOperationsServiceFacade.findOfficialityTypeById(getServiceContextAdministrador(), Long.valueOf(2)));
        operationDto = statisticalOperationsServiceFacade.updateOperation(getServiceContextAdministrador(), operationDto);

        int officialityTypesAfter = statisticalOperationsServiceFacade.findAllOfficialityTypes(getServiceContextAdministrador()).size();
        assertEquals(officialityTypesBefore, officialityTypesAfter);

        // REMOVE OFFICIALITY TYPE
        officialityTypesBefore = statisticalOperationsServiceFacade.findAllOfficialityTypes(getServiceContextAdministrador()).size();

        operationDto.setOfficialityType(null);
        operationDto = statisticalOperationsServiceFacade.updateOperation(getServiceContextAdministrador(), operationDto);

        officialityTypesAfter = statisticalOperationsServiceFacade.findAllOfficialityTypes(getServiceContextAdministrador()).size();
        assertEquals(officialityTypesBefore, officialityTypesAfter);

        // Check number of operations
        int operationsAfter = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();
        assertEquals(operationsBefore, operationsAfter);
    }

    @Test
    @Transactional
    public void testUpdateOperationOptimisticLockingError() throws Exception {
        Long id = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto()).getId();

        // Retrieve operation - session 1
        OperationDto operationDtoSession1 = statisticalOperationsServiceFacade.findOperationById(getServiceContextAdministrador(), id);
        assertEquals(Long.valueOf(0), operationDtoSession1.getOptimisticLockingVersion());
        operationDtoSession1.setCode("newCode");

        // Retrieve operation - session 2
        OperationDto operationDtoSession2 = statisticalOperationsServiceFacade.findOperationById(getServiceContextAdministrador(), id);
        assertEquals(Long.valueOf(0), operationDtoSession1.getOptimisticLockingVersion());
        operationDtoSession2.setCode("newCode2");

        // Update operation - session 1
        OperationDto operationDtoSession1AfterUpdate = statisticalOperationsServiceFacade.updateOperation(getServiceContextAdministrador(), operationDtoSession1);
        assertEquals(Long.valueOf(1), operationDtoSession1AfterUpdate.getOptimisticLockingVersion());

        // Update operation - session 2
        try {
            statisticalOperationsServiceFacade.updateOperation(getServiceContextAdministrador(), operationDtoSession2);
            fail("Optimistic locking");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEquals(ServiceExceptionType.OPTIMISTIC_LOCKING.getCode(), e.getExceptionItems().get(0).getCode());
            assertNull(e.getExceptionItems().get(0).getMessageParameters());
        }

        // Update operation - session 1
        operationDtoSession1AfterUpdate.setCode("code_2updat");
        OperationDto operationDtoSession1AfterUpdate2 = statisticalOperationsServiceFacade.updateOperation(getServiceContextAdministrador(), operationDtoSession1AfterUpdate);
        assertEquals(Long.valueOf(2), operationDtoSession1AfterUpdate2.getOptimisticLockingVersion());
    }

    @Test
    @Transactional
    public void testDeleteOperationWithList() throws Exception {
        // Create operation with officiality type
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoWithOfficialityType());
        assertNotNull(operationDto);
        int operationsBefore = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();

        // Delete operation
        int officialityTypesBefore = statisticalOperationsServiceFacade.findAllOfficialityTypes(getServiceContextAdministrador()).size();

        statisticalOperationsServiceFacade.deleteOperation(getServiceContextAdministrador(), operationDto.getId());

        int officialityTypesAfter = statisticalOperationsServiceFacade.findAllOfficialityTypes(getServiceContextAdministrador()).size();
        assertEquals(officialityTypesBefore, officialityTypesAfter);

        // Retrieve deleted operation
        try {
            statisticalOperationsServiceFacade.findOperationById(getServiceContextAdministrador(), operationDto.getId());
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.OPERATION_NOT_FOUND.getCode(), e.getExceptionItems().get(0).getCode());
        }

        // Check number of operations
        int operationsAfter = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();
        assertEquals(operationsBefore - 1, operationsAfter);
    }

    @Test
    @Transactional
    public void testDeleteOperationWithProducer() throws Exception {
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        assertNotNull(operationDto);
        int numberOperations = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();

        statisticalOperationsServiceFacade.deleteOperation(getServiceContextAdministrador(), operationDto.getId());
        assertTrue(statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size() == (numberOperations - 1));

        try {
            statisticalOperationsServiceFacade.findOperationById(getServiceContextAdministrador(), operationDto.getId());
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.OPERATION_NOT_FOUND.getCode(), e.getExceptionItems().get(0).getCode());
        }
    }

    @Override
    @Test
    @Transactional
    public void testDeleteOperation() throws MetamacException {
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());
        assertNotNull(operationDto);
        int numberOperations = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();

        statisticalOperationsServiceFacade.deleteOperation(getServiceContextAdministrador(), operationDto.getId());
        assertTrue(statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size() == (numberOperations - 1));

        try {
            statisticalOperationsServiceFacade.findOperationById(getServiceContextAdministrador(), operationDto.getId());
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.OPERATION_NOT_FOUND.getCode(), e.getExceptionItems().get(0).getCode());
        }
    }

    @Test
    @Transactional
    public void testDeleteOperationWithFamilies() throws MetamacException {
        // Create operation
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());
        assertNotNull(operationDto);
        int numberOperations = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();

        // Create families and associate
        FamilyDto family01 = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto());
        statisticalOperationsServiceFacade.addFamilyForOperation(getServiceContextAdministrador(), operationDto.getId(), family01.getId());

        FamilyDto family02 = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto());
        statisticalOperationsServiceFacade.addFamilyForOperation(getServiceContextAdministrador(), operationDto.getId(), family02.getId());

        // Count number of families before delete
        int familiesBeforeDelete = statisticalOperationsServiceFacade.findAllFamilies(getServiceContextAdministrador()).size();

        // Get operation
        operationDto = statisticalOperationsServiceFacade.findOperationById(getServiceContextAdministrador(), operationDto.getId());

        // Delete Operation
        statisticalOperationsServiceFacade.deleteOperation(getServiceContextAdministrador(), operationDto.getId());
        assertTrue(statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size() == (numberOperations - 1));

        // Check for deleted operation
        try {
            statisticalOperationsServiceFacade.findOperationById(getServiceContextAdministrador(), operationDto.getId());
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.OPERATION_NOT_FOUND.getCode(), e.getExceptionItems().get(0).getCode());
        }

        // Check number of families before delete
        int familiesAfterDelete = statisticalOperationsServiceFacade.findAllFamilies(getServiceContextAdministrador()).size();
        assertEquals(familiesBeforeDelete, familiesAfterDelete);
    }

    @Test
    @Transactional
    public void testDeleteOperationInternallyPublished() throws MetamacException {
        // Create operation
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        assertNotNull(operationDto);

        int operationsBefore = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();

        // Set PUBLISH_INTERNALLY procStatus
        operationDto = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();

        // Delete operation with an incorrect procStatus
        try {
            statisticalOperationsServiceFacade.deleteOperation(getServiceContextAdministrador(), operationDto.getId());
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.INVALID_PROC_STATUS.getCode(), e.getExceptionItems().get(0).getCode());
            assertEquals(1, e.getExceptionItems().size());
        }

        int operationsAfter = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();
        assertEquals(operationsBefore, operationsAfter);
    }

    @Test
    @Transactional
    public void testDeleteOperationExternallyPublished() throws MetamacException {
        // Create operation
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        assertNotNull(operationDto);

        int operationsBefore = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();

        // Set PUBLISH_INTERNALLY procStatus
        operationDto = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();
        operationDto = statisticalOperationsServiceFacade.publishExternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();

        // Delete operation with an incorrect procStatus
        try {
            statisticalOperationsServiceFacade.deleteOperation(getServiceContextAdministrador(), operationDto.getId());
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.INVALID_PROC_STATUS.getCode(), e.getExceptionItems().get(0).getCode());
            assertEquals(1, e.getExceptionItems().size());
        }

        int operationsAfter = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();
        assertEquals(operationsBefore, operationsAfter);
    }

    @Override
    @Test
    @Transactional
    public void testFindAllOperations() throws MetamacException {
        statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());
        List<OperationBaseDto> operations = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador());
        assertTrue(!operations.isEmpty());
    }

    @Override
    @Test
    @Transactional
    public void testFindOperationsByCondition() throws MetamacException {
        statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());
        statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());

        MetamacCriteria criteria = new MetamacCriteria();
        MetamacCriteriaResult<OperationBaseDto> result = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), criteria);
        assertTrue(result.getResults().size() >= 2);
    }

    @Test
    @Transactional
    public void testFindOperationsByConditionWithoutConditions() throws MetamacException {
        statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());
        statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());

        MetamacCriteriaResult<OperationBaseDto> result = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), null);
        assertTrue(result.getResults().size() >= 2);
    }

    @Test
    @Transactional
    public void testFindOperationsByConditionOrderByLastUpdatedDesc() throws MetamacException {
        Long id01 = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto()).getId();
        Long id02 = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto()).getId();
        Long id03 = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto()).getId();

        MetamacCriteria metamacCriteria = new MetamacCriteria();
        addOrderToCriteria(metamacCriteria, OperationCriteriaOrderEnum.LAST_UPDATED, OrderTypeEnum.DESC);

        MetamacCriteriaResult<OperationBaseDto> operations = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), metamacCriteria);
        assertTrue(operations.getResults().get(0).getLastUpdated().after(operations.getResults().get(1).getLastUpdated()));
        assertTrue(operations.getResults().get(1).getLastUpdated().after(operations.getResults().get(2).getLastUpdated()));
        assertEquals(id03, operations.getResults().get(0).getId());
        assertEquals(id02, operations.getResults().get(1).getId());
        assertEquals(id01, operations.getResults().get(2).getId());
    }

    @Test
    @Transactional
    public void testFindOperationByConditionCode() throws MetamacException {

        // Insert data
        OperationDto operationDto01 = createOperationDto();
        operationDto01.setCode("ope01");
        OperationDto operationDto02 = createOperationDto();
        operationDto02.setCode("ope02");
        OperationDto operationDto03 = createOperationDto();
        operationDto03.setCode("ope03");

        statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), operationDto01);
        statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), operationDto02);
        statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), operationDto03);

        // Retrieve "operationDto01" or "operationDto02"
        MetamacCriteria criteria = new MetamacCriteria();
        MetamacCriteriaDisjunctionRestriction disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.CODE.name(), "ope02", OperationType.EQ));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.CODE.name(), "ope01", OperationType.EQ));
        criteria.setRestriction(disjunction);

        MetamacCriteriaResult<OperationBaseDto> result = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Retrieve "operationDto01"
        criteria = new MetamacCriteria();
        criteria.setRestriction(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.CODE.name(), "ope01", OperationType.EQ));

        result = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(1, result.getResults().size());

        // Retrieve "*Dto*"
        criteria = new MetamacCriteria();
        criteria.setRestriction(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.CODE.name(), "0", OperationType.ILIKE));

        result = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(3, result.getResults().size());

    }

    @Test
    @Transactional
    public void testFindOperationByConditionTitle() throws MetamacException {

        // Insert data
        OperationDto operationDto01 = createOperationDto();
        String operation01Code = "ope01";
        operationDto01.setCode(operation01Code);
        operationDto01.setTitle(MetamacMocks.mockInternationalStringDto("es", "Índice Precio de Consumo", "en", "Consumer Price Index"));

        OperationDto operationDto02 = createOperationDto();
        String operation02Code = "ope02";
        operationDto02.setCode(operation02Code);
        operationDto02.setTitle(MetamacMocks.mockInternationalStringDto("es", "Calidad de Vida", "en", "Quality of Life"));

        OperationDto operationDto03 = createOperationDto();
        String operation03Code = "ope03";
        operationDto03.setCode(operation03Code);
        operationDto03.setTitle(MetamacMocks.mockInternationalStringDto("es", "Condiciones de vida", "en", "Living"));

        statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), operationDto01);
        statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), operationDto02);
        statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), operationDto03);

        // Find "vida" or "quality" --> operationDto02 and operationDto03
        MetamacCriteria criteria = new MetamacCriteria();
        MetamacCriteriaDisjunctionRestriction disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.TITLE.name(), "vida", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.TITLE.name(), "quality", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        MetamacCriteriaResult<OperationBaseDto> result = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Find "vida" or "index" --> operationDto01, operationDto02 and operationDto03
        criteria = new MetamacCriteria();
        disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.TITLE.name(), "vida", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.TITLE.name(), "index", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        result = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(3, result.getResults().size());

        // Find "vida" --> operationDto02 and operationDto03
        criteria = new MetamacCriteria();
        criteria.setRestriction(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.TITLE.name(), "vida", OperationType.ILIKE));

        result = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Find title "index" or code "operationDto02"--> operationDto01 and operationDto02
        criteria = new MetamacCriteria();
        disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.CODE.name(), operation02Code, OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.TITLE.name(), "index", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        result = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Find title "index" or "consumo"--> operationDto01
        criteria = new MetamacCriteria();
        disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.TITLE.name(), "consumo", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.TITLE.name(), "index", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        result = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(1, result.getResults().size());
    }

    @Test
    @Transactional
    public void testFindOperationByConditionAcronym() throws MetamacException {

        // Insert data
        OperationDto operationDto01 = createOperationDto();
        String operation01Code = "ope01";
        operationDto01.setCode(operation01Code);
        operationDto01.setAcronym(MetamacMocks.mockInternationalStringDto("es", "IPC", "en", "CPI"));

        OperationDto operationDto02 = createOperationDto();
        String operation02Code = "ope02";
        operationDto02.setCode(operation02Code);
        operationDto02.setAcronym(MetamacMocks.mockInternationalStringDto("es", "CALIDAD_VIDA", "en", "QL"));

        OperationDto operationDto03 = createOperationDto();
        String operation03Code = "ope03";
        operationDto03.setCode(operation03Code);
        operationDto03.setAcronym(MetamacMocks.mockInternationalStringDto("es", "CONDICIONES_VIDA", "en", "Living"));

        statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), operationDto01);
        statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), operationDto02);
        statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), operationDto03);

        // Find "vida" or "quality" --> operationDto02 and operationDto03
        MetamacCriteria criteria = new MetamacCriteria();
        MetamacCriteriaDisjunctionRestriction disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.ACRONYM.name(), "vida", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.ACRONYM.name(), "quality", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        MetamacCriteriaResult<OperationBaseDto> result = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Find "ql" or "condiciones" --> operationDto02 and operationDto03
        criteria = new MetamacCriteria();
        disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.ACRONYM.name(), "ql", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.ACRONYM.name(), "condiciones", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        result = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Find "vida" --> operationDto02 and operationDto03
        criteria = new MetamacCriteria();
        criteria.setRestriction(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.ACRONYM.name(), "vida", OperationType.ILIKE));

        result = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Find title "index" or code "operationDto02"--> operationDto01 and operationDto02
        criteria = new MetamacCriteria();
        disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.CODE.name(), operation02Code, OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.ACRONYM.name(), "cp", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        result = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Find title "ipc" or "cpi"--> operationDto01
        criteria = new MetamacCriteria();
        disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.ACRONYM.name(), "ipc", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.ACRONYM.name(), "cpi", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        result = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(1, result.getResults().size());
    }

    @Test
    @Transactional
    public void testFindOperationByConditionDescription() throws MetamacException {

        // Insert data
        OperationDto operationDto01 = createOperationDto();
        String operation01Code = "ope01";
        operationDto01.setCode(operation01Code);
        operationDto01.setDescription(MetamacMocks.mockInternationalStringDto("es", "Índice Precio de Consumo", "en", "Consumer Price Index"));

        OperationDto operationDto02 = createOperationDto();
        String operation02Code = "ope02";
        operationDto02.setCode(operation02Code);
        operationDto02.setDescription(MetamacMocks.mockInternationalStringDto("es", "Calidad de Vida", "en", "Quality of Life"));

        OperationDto operationDto03 = createOperationDto();
        String operation03Code = "ope03";
        operationDto03.setCode(operation03Code);
        operationDto03.setDescription(MetamacMocks.mockInternationalStringDto("es", "Condiciones de vida", "en", "Living"));

        statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), operationDto01);
        statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), operationDto02);
        statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), operationDto03);

        // Find "vida" or "quality" --> operationDto02 and operationDto03
        MetamacCriteria criteria = new MetamacCriteria();
        MetamacCriteriaDisjunctionRestriction disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.DESCRIPTION.name(), "vida", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.DESCRIPTION.name(), "quality", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        MetamacCriteriaResult<OperationBaseDto> result = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Find "vida" or "index" --> operationDto01, operationDto02 and operationDto03
        criteria = new MetamacCriteria();
        disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.DESCRIPTION.name(), "vida", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.DESCRIPTION.name(), "index", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        result = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(3, result.getResults().size());

        // Find "vida" --> operationDto02 and operationDto03
        criteria = new MetamacCriteria();
        criteria.setRestriction(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.DESCRIPTION.name(), "vida", OperationType.ILIKE));

        result = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Find title "index" or code "operationDto02"--> operationDto01 and operationDto02
        criteria = new MetamacCriteria();
        disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.CODE.name(), operation02Code, OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.DESCRIPTION.name(), "index", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        result = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Find title "index" or "consumo"--> operationDto01
        criteria = new MetamacCriteria();
        disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.DESCRIPTION.name(), "consumo", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.DESCRIPTION.name(), "index", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        result = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(1, result.getResults().size());
    }

    @Test
    @Transactional
    public void testFindOperationByConditionProcStatus() throws MetamacException {
        // Create operations
        statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());
        Long operation02 = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing()).getId();
        Long operation03 = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing()).getId();
        Long operation04 = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing()).getId();
        Long operation05 = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing()).getId();
        Long operation06 = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing()).getId();

        // Publish internally operations
        statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operation02);
        statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operation03);
        statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operation04);
        statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operation05);
        statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operation06);

        // Publish externally operations
        statisticalOperationsServiceFacade.publishExternallyOperation(getServiceContextAdministrador(), operation04);
        statisticalOperationsServiceFacade.publishExternallyOperation(getServiceContextAdministrador(), operation05);
        statisticalOperationsServiceFacade.publishExternallyOperation(getServiceContextAdministrador(), operation06);

        {
            // Retrieve "PUBLISH_EXTERNALLY" or "PUBLISH_INTERNALLY"
            MetamacCriteria criteria = new MetamacCriteria();
            MetamacCriteriaDisjunctionRestriction disjunction = new MetamacCriteriaDisjunctionRestriction();
            disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.PROC_STATUS.name(), ProcStatusEnum.PUBLISH_EXTERNALLY, OperationType.EQ));
            disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.PROC_STATUS.name(), ProcStatusEnum.PUBLISH_INTERNALLY, OperationType.EQ));
            criteria.setRestriction(disjunction);

            MetamacCriteriaResult<OperationBaseDto> result = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), criteria);
            assertEquals(5, result.getResults().size());
        }

        {
            // Retrieve "DRAFT"
            MetamacCriteria criteria = new MetamacCriteria();
            criteria.setRestriction(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.PROC_STATUS.name(), ProcStatusEnum.DRAFT, OperationType.EQ));

            MetamacCriteriaResult<OperationBaseDto> result = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), criteria);
            assertEquals(1, result.getResults().size());
        }

        {
            // Retrieve "PUBLISH_EXTERNALLY*"
            MetamacCriteria criteria = new MetamacCriteria();
            criteria.setRestriction(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.PROC_STATUS.name(), ProcStatusEnum.PUBLISH_EXTERNALLY, OperationType.EQ));

            MetamacCriteriaResult<OperationBaseDto> result = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), criteria);
            assertEquals(3, result.getResults().size());
        }
    }

    @Test
    @Transactional
    public void testFindOperationByConditionFamilyCode() throws MetamacException {

        // Insert data
        OperationDto operationDto01a = createOperationDtoForInternalPublishing();
        String operation01ACode = "ope01-a";
        operationDto01a.setCode(operation01ACode);
        OperationDto operationDto01b = createOperationDtoForInternalPublishing();
        String operation01B = "ope01-b";
        operationDto01b.setCode(operation01B);

        FamilyDto familyDto011 = createFamilyDto();
        familyDto011.setCode("familyDto011");
        FamilyDto familyDto012 = createFamilyDto();
        familyDto012.setCode("familyDto012");

        operationDto01a = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), operationDto01a);
        operationDto01b = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), operationDto01b);
        operationDto01a = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto01a.getId()).getContent();
        operationDto01b = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto01b.getId()).getContent();

        familyDto011 = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), familyDto011);
        familyDto012 = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), familyDto012);

        statisticalOperationsServiceFacade.addFamilyForOperation(getServiceContextAdministrador(), operationDto01a.getId(), familyDto011.getId());
        statisticalOperationsServiceFacade.addFamilyForOperation(getServiceContextAdministrador(), operationDto01b.getId(), familyDto011.getId());
        statisticalOperationsServiceFacade.addFamilyForOperation(getServiceContextAdministrador(), operationDto01a.getId(), familyDto012.getId());

        // Retrieve operations with family code "familyDto011" --> operationDto01-a && operationDto01-b
        MetamacCriteria criteria = new MetamacCriteria();
        criteria.setRestriction(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.FAMILY_CODE.name(), "familyDto011", OperationType.EQ));

        MetamacCriteriaResult<OperationBaseDto> result = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Retrieve operations with family code "familyDto012" --> operationDto01-a
        criteria = new MetamacCriteria();
        criteria.setRestriction(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.FAMILY_CODE.name(), "familyDto012", OperationType.EQ));

        result = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(1, result.getResults().size());

    }

    @Test
    @Transactional
    public void testFindOperationByConditionFamilyId() throws MetamacException {

        // Insert data
        OperationDto operationDto01a = createOperationDtoForInternalPublishing();
        String operation01A = "ope01-a";
        operationDto01a.setCode(operation01A);
        OperationDto operationDto01b = createOperationDtoForInternalPublishing();
        String operation01B = "ope01-b";
        operationDto01b.setCode(operation01B);

        FamilyDto familyDto011 = createFamilyDto();
        familyDto011.setCode("familyDto011");
        FamilyDto familyDto012 = createFamilyDto();
        familyDto012.setCode("familyDto012");

        operationDto01a = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), operationDto01a);
        operationDto01b = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), operationDto01b);
        operationDto01a = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto01a.getId()).getContent();
        operationDto01b = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto01b.getId()).getContent();

        familyDto011 = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), familyDto011);
        familyDto012 = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), familyDto012);

        statisticalOperationsServiceFacade.addFamilyForOperation(getServiceContextAdministrador(), operationDto01a.getId(), familyDto011.getId());
        statisticalOperationsServiceFacade.addFamilyForOperation(getServiceContextAdministrador(), operationDto01b.getId(), familyDto011.getId());
        statisticalOperationsServiceFacade.addFamilyForOperation(getServiceContextAdministrador(), operationDto01a.getId(), familyDto012.getId());

        // Retrieve operations with family code "familyDto011" --> operationDto01-a && operationDto01-b
        MetamacCriteria criteria = new MetamacCriteria();
        criteria.setRestriction(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.FAMILY_ID.name(), familyDto011.getId(), OperationType.EQ));

        MetamacCriteriaResult<OperationBaseDto> result = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Retrieve operations with family code "familyDto012" --> operationDto01-a
        criteria = new MetamacCriteria();
        criteria.setRestriction(new MetamacCriteriaPropertyRestriction(OperationCriteriaPropertyEnum.FAMILY_ID.name(), familyDto012.getId(), OperationType.EQ));

        result = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(1, result.getResults().size());

    }

    @Test
    @Transactional
    public void testFindOperationsByConditionPaginated() throws MetamacException {
        statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());
        statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());
        statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());

        MetamacCriteria criteria = new MetamacCriteria();

        MetamacCriteriaPaginator paginator = new MetamacCriteriaPaginator();
        paginator.setCountTotalResults(Boolean.TRUE);
        paginator.setMaximumResultSize(Integer.valueOf(2));
        criteria.setPaginator(paginator);

        {
            // Page 1
            criteria.getPaginator().setFirstResult(Integer.valueOf(0));

            MetamacCriteriaResult<OperationBaseDto> result = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), criteria);
            assertEquals(2, result.getResults().size());
            assertTrue(result.getPaginatorResult().getTotalResults() >= 3);
            assertEquals(Integer.valueOf(0), result.getPaginatorResult().getFirstResult());
        }

        {
            // Page 2
            criteria.getPaginator().setFirstResult(Integer.valueOf(2));
            MetamacCriteriaResult<OperationBaseDto> result = statisticalOperationsServiceFacade.findOperationsByCondition(getServiceContextAdministrador(), criteria);
            assertTrue(result.getPaginatorResult().getTotalResults() >= 1);
            assertEquals(Integer.valueOf(2), result.getPaginatorResult().getFirstResult());

        }
    }

    @Override
    @Test
    @Transactional
    public void testFindOperationById() throws MetamacException {
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());

        OperationDto operationRetrieved = statisticalOperationsServiceFacade.findOperationById(getServiceContextAdministrador(), operationDto.getId());

        assertNotNull(operationRetrieved);
        assertTrue(operationDto.getId().equals(operationRetrieved.getId()));
    }

    @Override
    @Test
    @Transactional
    public void testFindOperationByCode() throws Exception {
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());

        OperationDto operationRetrieved = statisticalOperationsServiceFacade.findOperationByCode(getServiceContextAdministrador(), operationDto.getCode());

        assertNotNull(operationRetrieved);
        assertTrue(operationDto.getCode().equals(operationRetrieved.getCode()));
    }

    @Override
    @Test
    @Transactional
    public void testFindOperationByUrn() throws Exception {
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());

        OperationDto operationRetrieved = statisticalOperationsServiceFacade.findOperationByUrn(getServiceContextAdministrador(), operationDto.getUrn());

        assertNotNull(operationRetrieved);
        assertTrue(operationDto.getUrn().equals(operationRetrieved.getUrn()));
    }

    @Override
    @Test
    @Transactional
    public void testPublishInternallyOperation() throws MetamacException {

        // Create and Publish operation
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        int operationsBefore = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();

        operationDto = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();

        // Validations
        assertNotNull(operationDto.getInternalInventoryDate());
        assertTrue(ProcStatusEnum.PUBLISH_INTERNALLY.equals(operationDto.getProcStatus()));

        int operationsAfter = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();
        assertEquals(operationsBefore, operationsAfter);
    }

    @Override
    @Test
    @Transactional
    public void testPublishExternallyOperation() throws MetamacException {

        // Create and Publish operation
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        int operationsBefore = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();

        operationDto = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();
        operationDto = statisticalOperationsServiceFacade.publishExternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();

        // Validations
        assertNotNull(operationDto.getInternalInventoryDate());
        assertNotNull(operationDto.getInventoryDate());
        assertTrue(ProcStatusEnum.PUBLISH_EXTERNALLY.equals(operationDto.getProcStatus()));

        // Check number of operations
        int operationsAfter = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();
        assertEquals(operationsBefore, operationsAfter);

        // Check that operation can't be internally published
        try {
            statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto.getId());
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.INVALID_PROC_STATUS.getCode(), e.getExceptionItems().get(0).getCode());
        }
    }

    @Override
    public void testRepublishExternallyOperation() throws Exception {
        // NO TEST
        // In any test method where 'statisticalOperationsServiceFacade.publishExternallyOperation' is called, the same functionality is checked. Sending a message to Kafka
    }

    @Override
    @Test
    @Transactional
    public void testFindFamiliesForOperation() throws MetamacException {
        // Create operation
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());
        assertNotNull(operationDto);

        // Create family
        FamilyDto familyDto = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto());
        assertNotNull(familyDto);

        // Associate
        List<FamilyBaseDto> familiesForOperation = statisticalOperationsServiceFacade.addFamilyForOperation(getServiceContextAdministrador(), operationDto.getId(), familyDto.getId());

        assertNotNull(familiesForOperation);

    }

    @Override
    @Test
    @Transactional
    public void testFindInstancesForOperation() throws MetamacException {
        // Create operation
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        assertNotNull(operationDto);

        operationDto = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();

        // Create instance
        InstanceDto instanceDto = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationDto.getId(), createInstanceDto());
        assertNotNull(instanceDto);

        assertEquals(1, statisticalOperationsServiceFacade.findInstancesForOperation(getServiceContextAdministrador(), operationDto.getId()).size());
    }

    // @Test
    // public void testFindInstancesForOperationId() throws MetamacException {
    // List<InstanceDto> instances = statisticalOperationsServiceFacade.findInstancesForOperation(getServiceContextAdministrador(), Long.valueOf(123));
    //
    // assertNotNull(instances);
    // }

    @Override
    @Test
    @Transactional
    public void testAddFamilyForOperation() throws MetamacException {
        // Create operation
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());
        assertNotNull(operationDto);

        int operationsBefore = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();

        // Create family
        FamilyDto familyDto = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto());
        assertNotNull(familyDto);

        // Associate
        statisticalOperationsServiceFacade.addFamilyForOperation(getServiceContextAdministrador(), operationDto.getId(), familyDto.getId());

        assertNotNull(statisticalOperationsServiceFacade.findFamiliesForOperation(getServiceContextAdministrador(), operationDto.getId()));

        // Check number of operations
        int operationsAfter = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();
        assertEquals(operationsBefore, operationsAfter);

    }

    @Override
    @Test
    @Transactional
    public void testRemoveFamilyForOperation() throws MetamacException {
        // Create operation
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDto());
        assertNotNull(operationDto);

        int operationsBefore = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();

        // Create family
        FamilyDto familyDto = statisticalOperationsServiceFacade.createFamily(getServiceContextAdministrador(), createFamilyDto());
        assertNotNull(familyDto);

        // Associate
        statisticalOperationsServiceFacade.addFamilyForOperation(getServiceContextAdministrador(), operationDto.getId(), familyDto.getId());
        assertNotNull(statisticalOperationsServiceFacade.findFamiliesForOperation(getServiceContextAdministrador(), operationDto.getId()));

        // Remove
        statisticalOperationsServiceFacade.removeFamilyForOperation(getServiceContextAdministrador(), operationDto.getId(), familyDto.getId());
        assertTrue(statisticalOperationsServiceFacade.findFamiliesForOperation(getServiceContextAdministrador(), operationDto.getId()).isEmpty());

        // Check number of operations
        int operationsAfter = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();
        assertEquals(operationsBefore, operationsAfter);
    }

    /**************************************************************************
     * Instance
     **************************************************************************/

    @Override
    @Test
    @Transactional
    public void testCreateInstance() throws MetamacException, InterruptedException {
        int instancesBefore = statisticalOperationsServiceFacade.findAllInstances(getServiceContextAdministrador()).size();

        // Create operation
        Long operationId = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing()).getId();
        statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationId);

        // Create instance
        InstanceDto instanceDto = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationId, createInstanceDto());
        assertNotNull(instanceDto);

        // Ckeck number of instances in the operation
        List<InstanceBaseDto> operationInstances = statisticalOperationsServiceFacade.findInstancesForOperation(getServiceContextAdministrador(), operationId);
        assertEquals(1, operationInstances.size());

        // Check number of instances
        int instancesAfter = statisticalOperationsServiceFacade.findAllInstances(getServiceContextAdministrador()).size();
        assertEquals(instancesBefore + 1, instancesAfter);

    }

    @Test
    @Transactional
    public void testCreateInstanceDuplicatedCode() throws MetamacException {
        // Create operation
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        Long operationId = operationDto.getId();
        operationDto = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();

        // Create instance
        InstanceDto persistedIstanceDto = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationId, createInstanceDto());

        InstanceDto instanceDto = createInstanceDto();
        instanceDto.setCode(persistedIstanceDto.getCode());

        try {
            statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationId, instanceDto);
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.INSTANCE_ALREADY_EXIST_CODE_DUPLICATED.getCode(), e.getExceptionItems().get(0).getCode());
        }
    }

    @Test
    @Transactional
    public void testCreateInstanceError() throws MetamacException {
        int instancesBefore = statisticalOperationsServiceFacade.findAllInstances(getServiceContextAdministrador()).size();

        // Create operation
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        operationDto = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();

        // Create instance
        try {
            statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationDto.getId(), createIncompleteInstanceDto());
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.METADATA_REQUIRED.getCode(), e.getExceptionItems().get(0).getCode());
        }

        // Check number of instances
        int instancesAfter = statisticalOperationsServiceFacade.findAllInstances(getServiceContextAdministrador()).size();
        assertEquals(instancesBefore, instancesAfter);

    }

    @Override
    @Test
    @Transactional
    public void testUpdateInstance() throws Exception {
        // Create operation
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        operationDto = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();

        // Create instance
        InstanceDto instanceDto = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationDto.getId(), createInstanceDto());
        assertNotNull(instanceDto);

        int instancesBefore = statisticalOperationsServiceFacade.findAllInstances(getServiceContextAdministrador()).size();

        // TITLE
        InternationalStringDto title = new InternationalStringDto();
        LocalisedStringDto title_es = new LocalisedStringDto();
        title_es.setLabel("MOD Título en español de instancia");
        title_es.setLocale("es");
        LocalisedStringDto title_en = new LocalisedStringDto();
        title_en.setLabel("MOD Título en inglés de instancia");
        title_en.setLocale("en");
        title.addText(title_es);
        title.addText(title_en);
        instanceDto.setTitle(title);

        instanceDto = statisticalOperationsServiceFacade.updateInstance(getServiceContextAdministrador(), instanceDto);

        assertNotNull(operationDto);

        // Check number of instances
        int instancesAfter = statisticalOperationsServiceFacade.findAllInstances(getServiceContextAdministrador()).size();
        assertEquals(instancesBefore, instancesAfter);

    }

    @Test
    @Transactional
    public void testUpdateInstanceCodeUnmodifiable() throws MetamacException {
        // Create operation
        Long operationId = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing()).getId();
        statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationId);

        // Create instance
        Long instanceId = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationId, createInstanceDto()).getId();

        // Publish instance
        statisticalOperationsServiceFacade.publishInternallyInstance(getServiceContextTecnicoPlanificacion(), instanceId);

        // Change instance code
        InstanceDto instanceDto = statisticalOperationsServiceFacade.findInstanceById(getServiceContextAdministrador(), instanceId);
        instanceDto.setCode("INSTANCE_OTHER_CODE");

        try {
            statisticalOperationsServiceFacade.updateInstance(getServiceContextAdministrador(), instanceDto);
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.METADATA_UNMODIFIABLE.getCode(), e.getExceptionItems().get(0).getCode());
        }
    }

    @Test
    @Transactional
    public void testUpdateInstanceWithExternalItem() throws Exception {
        // Create operation
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        operationDto = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();

        // Create instance
        InstanceDto instanceDto = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationDto.getId(), createInstanceDtoWithGeographicGranularity());
        assertNotNull(instanceDto);

        int instancesBefore = statisticalOperationsServiceFacade.findAllInstances(getServiceContextAdministrador()).size();

        // GEOGRAPHIC_GRANULARITY
        instanceDto.getGeographicGranularity().clear();

        // Save
        instanceDto = statisticalOperationsServiceFacade.updateInstance(getServiceContextAdministrador(), instanceDto);

        // Validations
        assertNotNull(operationDto);
        assertNotNull(instanceDto);
        assertTrue(instanceDto.getGeographicGranularity().isEmpty());

        // Check number of instances
        int instancesAfter = statisticalOperationsServiceFacade.findAllInstances(getServiceContextAdministrador()).size();
        assertEquals(instancesBefore, instancesAfter);

    }

    @Override
    @Test
    @DirtyDatabase
    public void testUpdateInstancesOrder() throws Exception {
        // Create operation
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        operationDto = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();

        InstanceDto instance01 = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationDto.getId(), createInstanceDto());
        InstanceDto instance02 = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationDto.getId(), createInstanceDto());
        InstanceDto instance03 = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationDto.getId(), createInstanceDto());

        // Check orders
        assertEquals(Integer.valueOf(0), instance01.getOrder());
        assertEquals(Integer.valueOf(1), instance02.getOrder());
        assertEquals(Integer.valueOf(2), instance03.getOrder());

        // Change order: updateInstancesOrder receive an asc order
        List<Long> instancesIds = new ArrayList<Long>();
        instancesIds.add(instance03.getId());
        instancesIds.add(instance02.getId());
        instancesIds.add(instance01.getId());

        // orderedInstances return asc order
        List<InstanceBaseDto> orderedInstances = statisticalOperationsServiceFacade.updateInstancesOrder(getServiceContextAdministrador(), operationDto.getId(), instancesIds);

        // Check order number. It has to be asc
        assertEquals(Integer.valueOf(0), orderedInstances.get(0).getOrder());
        assertEquals(Integer.valueOf(1), orderedInstances.get(1).getOrder());
        assertEquals(Integer.valueOf(2), orderedInstances.get(2).getOrder());

        // Check correct order
        assertEquals(instance01.getId(), orderedInstances.get(2).getId());
        assertEquals(instance02.getId(), orderedInstances.get(1).getId());
        assertEquals(instance03.getId(), orderedInstances.get(0).getId());

        // Delete instance
        statisticalOperationsServiceFacade.deleteInstance(getServiceContextAdministrador(), instance02.getId());

        orderedInstances = statisticalOperationsServiceFacade.findInstancesForOperation(getServiceContextAdministrador(), operationDto.getId());

        // Check order number. It has to be desc
        assertEquals(Integer.valueOf(1), orderedInstances.get(0).getOrder());
        assertEquals(Integer.valueOf(0), orderedInstances.get(1).getOrder());

        // Check correct order
        assertEquals(instance01.getId(), orderedInstances.get(0).getId());
        assertEquals(instance03.getId(), orderedInstances.get(1).getId());
    }

    @Test
    @Transactional
    public void testUpdateInstanceOptimisticLockingError() throws Exception {
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        operationDto = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();
        Long id = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationDto.getId(), createInstanceDto()).getId();

        // Retrieve instance - session 1
        InstanceDto instanceDtoSession1 = statisticalOperationsServiceFacade.findInstanceById(getServiceContextAdministrador(), id);
        assertEquals(Long.valueOf(0), instanceDtoSession1.getOptimisticLockingVersion());
        instanceDtoSession1.setCode("newCode");

        // Retrieve instance - session 2
        InstanceDto instanceDtoSession2 = statisticalOperationsServiceFacade.findInstanceById(getServiceContextAdministrador(), id);
        assertEquals(Long.valueOf(0), instanceDtoSession1.getOptimisticLockingVersion());
        instanceDtoSession2.setCode("newCode2");

        // Update instance - session 1
        InstanceDto instanceDtoSession1AfterUpdate = statisticalOperationsServiceFacade.updateInstance(getServiceContextAdministrador(), instanceDtoSession1);
        assertEquals(Long.valueOf(1), instanceDtoSession1AfterUpdate.getOptimisticLockingVersion());

        // Update instance - session 2
        try {
            statisticalOperationsServiceFacade.updateInstance(getServiceContextAdministrador(), instanceDtoSession2);
            fail("Optimistic locking");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEquals(ServiceExceptionType.OPTIMISTIC_LOCKING.getCode(), e.getExceptionItems().get(0).getCode());
            assertNull(e.getExceptionItems().get(0).getMessageParameters());
        }

        // Update instance - session 1
        instanceDtoSession1AfterUpdate.setCode("newCode1_secondUpdate");
        InstanceDto instanceDtoSession1AfterUpdate2 = statisticalOperationsServiceFacade.updateInstance(getServiceContextAdministrador(), instanceDtoSession1AfterUpdate);
        assertEquals(Long.valueOf(2), instanceDtoSession1AfterUpdate2.getOptimisticLockingVersion());
    }

    @Override
    @Test
    @Transactional
    public void testDeleteInstance() throws MetamacException {
        // Create operation
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        operationDto = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();

        // Create instance
        InstanceDto instanceDto = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationDto.getId(), createInstanceDto());
        assertNotNull(instanceDto);

        int numberInstances = statisticalOperationsServiceFacade.findAllInstances(getServiceContextAdministrador()).size();

        statisticalOperationsServiceFacade.deleteInstance(getServiceContextAdministrador(), instanceDto.getId());
        assertTrue(statisticalOperationsServiceFacade.findAllInstances(getServiceContextAdministrador()).size() == (numberInstances - 1));

        try {
            statisticalOperationsServiceFacade.findInstanceById(getServiceContextAdministrador(), instanceDto.getId());
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.INSTANCE_ID_NOT_FOUND.getCode(), e.getExceptionItems().get(0).getCode());
        }

    }

    @Test
    @DirtyDatabase
    public void testCheckInstancesOrderAfterRemoveInstance() throws MetamacException {
        // Create operation
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        operationDto = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();

        // Create instance 01
        InstanceDto instance01 = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationDto.getId(), createInstanceDto());
        assertNotNull(instance01);

        // Create instance 02
        InstanceDto instance02 = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationDto.getId(), createInstanceDto());
        assertNotNull(instance02);

        // Check initial order
        List<InstanceBaseDto> instances = statisticalOperationsServiceFacade.findInstancesForOperation(getServiceContextAdministrador(), operationDto.getId());
        assertEquals(instance02.getId(), instances.get(0).getId());
        assertEquals(instance01.getId(), instances.get(1).getId());

        assertEquals(Integer.valueOf(1), instances.get(0).getOrder());
        assertEquals(Integer.valueOf(0), instances.get(1).getOrder());

        // Reorder instances
        List<Long> newOrder = new ArrayList<Long>();
        newOrder.add(instance02.getId());
        newOrder.add(instance01.getId());
        statisticalOperationsServiceFacade.updateInstancesOrder(getServiceContextAdministrador(), operationDto.getId(), newOrder);

        // Check order
        instances = statisticalOperationsServiceFacade.findInstancesForOperation(getServiceContextAdministrador(), operationDto.getId());
        assertEquals(instance01.getId(), instances.get(0).getId());
        assertEquals(instance02.getId(), instances.get(1).getId());

        assertEquals(Integer.valueOf(0), instances.get(1).getOrder());
        assertEquals(Integer.valueOf(1), instances.get(0).getOrder());

        // Create instance 03
        InstanceDto instance03 = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationDto.getId(), createInstanceDto());
        assertNotNull(instance03);

        // Check order
        instances = statisticalOperationsServiceFacade.findInstancesForOperation(getServiceContextAdministrador(), operationDto.getId());
        assertEquals(instance02.getId(), instances.get(2).getId());
        assertEquals(instance01.getId(), instances.get(1).getId());
        assertEquals(instance03.getId(), instances.get(0).getId());

        assertEquals(Integer.valueOf(0), instances.get(2).getOrder());
        assertEquals(Integer.valueOf(1), instances.get(1).getOrder());
        assertEquals(Integer.valueOf(2), instances.get(0).getOrder());

        // Delete instance
        statisticalOperationsServiceFacade.deleteInstance(getServiceContextAdministrador(), instance02.getId());

        // Check for deleted instance
        try {
            statisticalOperationsServiceFacade.findInstanceById(getServiceContextAdministrador(), instance01.getId());
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.INSTANCE_NOT_FOUND.getCode(), e.getExceptionItems().get(0).getCode());
        }

        // Check order
        instances = statisticalOperationsServiceFacade.findInstancesForOperation(getServiceContextAdministrador(), operationDto.getId());
        assertEquals(instance01.getId(), instances.get(1).getId());
        assertEquals(instance03.getId(), instances.get(0).getId());

        assertEquals(Integer.valueOf(0), instances.get(1).getOrder());
        assertEquals(Integer.valueOf(1), instances.get(0).getOrder());

    }

    @Test
    @DirtyDatabase
    public void testCheckCurrentInstances() throws MetamacException {
        // Create operation
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto.getId());
        statisticalOperationsServiceFacade.publishExternallyOperation(getServiceContextAdministrador(), operationDto.getId());

        // Create instance 01
        InstanceDto instance01 = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationDto.getId(), createInstanceDto());
        assertNotNull(instance01);
        statisticalOperationsServiceFacade.publishInternallyInstance(getServiceContextAdministrador(), instance01.getId());
        statisticalOperationsServiceFacade.publishExternallyInstance(getServiceContextAdministrador(), instance01.getId());

        // Create instance 02
        InstanceDto instance02 = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationDto.getId(), createInstanceDto());
        assertNotNull(instance02);

        // Check initial order
        List<InstanceBaseDto> instances = statisticalOperationsServiceFacade.findInstancesForOperation(getServiceContextAdministrador(), operationDto.getId());
        assertEquals(instance02.getId(), instances.get(0).getId());
        assertEquals(instance01.getId(), instances.get(1).getId());

        assertEquals(Integer.valueOf(1), instances.get(0).getOrder());
        assertEquals(Integer.valueOf(0), instances.get(1).getOrder());

        // Check current instances
        operationDto = statisticalOperationsServiceFacade.findOperationById(getServiceContextAdministrador(), operationDto.getId());
        assertEquals(instance01.getId(), operationDto.getCurrentInstance().getId());
        assertEquals(null, operationDto.getCurrentInternalInstance());

        // Reorder instances
        List<Long> newOrder = new ArrayList<Long>();
        newOrder.add(instance02.getId());
        newOrder.add(instance01.getId());
        statisticalOperationsServiceFacade.updateInstancesOrder(getServiceContextAdministrador(), operationDto.getId(), newOrder);

        // Check order
        instances = statisticalOperationsServiceFacade.findInstancesForOperation(getServiceContextAdministrador(), operationDto.getId());
        assertEquals(instance01.getId(), instances.get(0).getId());
        assertEquals(instance02.getId(), instances.get(1).getId());

        assertEquals(Integer.valueOf(0), instances.get(1).getOrder());
        assertEquals(Integer.valueOf(1), instances.get(0).getOrder());

        // Check current instances
        operationDto = statisticalOperationsServiceFacade.findOperationById(getServiceContextAdministrador(), operationDto.getId());
        assertEquals(instance01.getId(), operationDto.getCurrentInstance().getId());
        assertEquals(null, operationDto.getCurrentInternalInstance());

        // Create instance 03
        InstanceDto instance03 = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationDto.getId(), createInstanceDto());
        assertNotNull(instance03);
        statisticalOperationsServiceFacade.publishInternallyInstance(getServiceContextAdministrador(), instance03.getId());

        // Check order
        instances = statisticalOperationsServiceFacade.findInstancesForOperation(getServiceContextAdministrador(), operationDto.getId());
        assertEquals(instance02.getId(), instances.get(2).getId());
        assertEquals(instance01.getId(), instances.get(1).getId());
        assertEquals(instance03.getId(), instances.get(0).getId());

        assertEquals(Integer.valueOf(0), instances.get(2).getOrder());
        assertEquals(Integer.valueOf(1), instances.get(1).getOrder());
        assertEquals(Integer.valueOf(2), instances.get(0).getOrder());

        // Check current instances
        operationDto = statisticalOperationsServiceFacade.findOperationById(getServiceContextAdministrador(), operationDto.getId());
        assertEquals(instance01.getId(), operationDto.getCurrentInstance().getId());
        assertEquals(instance03.getId(), operationDto.getCurrentInternalInstance().getId());

        // Delete instance
        statisticalOperationsServiceFacade.deleteInstance(getServiceContextAdministrador(), instance02.getId());

        // Publish externally instance03
        statisticalOperationsServiceFacade.publishExternallyInstance(getServiceContextAdministrador(), instance03.getId());

        // Check for deleted instance
        try {
            statisticalOperationsServiceFacade.findInstanceById(getServiceContextAdministrador(), instance01.getId());
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.INSTANCE_NOT_FOUND.getCode(), e.getExceptionItems().get(0).getCode());
        }

        // Check order
        instances = statisticalOperationsServiceFacade.findInstancesForOperation(getServiceContextAdministrador(), operationDto.getId());
        assertEquals(instance01.getId(), instances.get(1).getId());
        assertEquals(instance03.getId(), instances.get(0).getId());

        assertEquals(Integer.valueOf(0), instances.get(1).getOrder());
        assertEquals(Integer.valueOf(1), instances.get(0).getOrder());

        // Check current instances
        operationDto = statisticalOperationsServiceFacade.findOperationById(getServiceContextAdministrador(), operationDto.getId());
        assertEquals(instance03.getId(), operationDto.getCurrentInstance().getId());
        assertEquals(null, operationDto.getCurrentInternalInstance());

    }

    // @Test
    // public void testDeleteInstanceById() throws MetamacException {
    // initializeData();
    //
    // List<InstanceDto> instances = statisticalOperationsServiceFacade.findAllInstances(getServiceContextAdministrador());
    //
    // int numberInstances = instances.size();
    // InstanceDto instanceDto = instances.get(0);
    //
    // statisticalOperationsServiceFacade.deleteInstance(getServiceContextAdministrador(), instanceDto.getId());
    // assertTrue(statisticalOperationsServiceFacade.findAllInstances(getServiceContextAdministrador()).size() == (numberInstances - 1));
    //
    // try {
    // statisticalOperationsServiceFacade.findInstanceById(getServiceContextAdministrador(), instanceDto.getId());
    // } catch (MetamacException e) {
    // assertEquals(ServiceExceptionType.SERVICE_INSTANCE_CODE_NOT_FOUND.getCode(), e.getExceptionItems().get(0).getCode());
    // }
    //
    // }

    @Test
    @Transactional
    public void testDeleteInstanceInternallyPublished() throws MetamacException {
        // Create operation
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        operationDto = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();

        int instancesBeforeCreateInstance = statisticalOperationsServiceFacade.findAllInstances(getServiceContextAdministrador()).size();

        // Create instance
        InstanceDto instanceDto = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationDto.getId(), createInstanceDto());
        assertNotNull(instanceDto);

        int instancesBeforeDelete = statisticalOperationsServiceFacade.findAllInstances(getServiceContextAdministrador()).size();
        assertEquals(instancesBeforeDelete, instancesBeforeCreateInstance + 1);

        // Set PUBLISH_INTERNALLY procStatus
        instanceDto = statisticalOperationsServiceFacade.publishInternallyInstance(getServiceContextAdministrador(), instanceDto.getId());

        // Delete instance with an incorrect procStatus
        try {
            statisticalOperationsServiceFacade.deleteInstance(getServiceContextAdministrador(), instanceDto.getId());
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.INVALID_PROC_STATUS.getCode(), e.getExceptionItems().get(0).getCode());
            assertEquals(1, e.getExceptionItems().size());
        }

        int instancesAfter = statisticalOperationsServiceFacade.findAllInstances(getServiceContextAdministrador()).size();
        assertEquals(instancesBeforeDelete, instancesAfter);
    }

    @Test
    @Transactional
    public void testDeleteInstanceExternallyPublished() throws MetamacException {
        // Create operation
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        operationDto = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();
        operationDto = statisticalOperationsServiceFacade.publishExternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();

        // Create instance
        InstanceDto instanceDto = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationDto.getId(), createInstanceDto());
        assertNotNull(instanceDto);

        int instancesBefore = statisticalOperationsServiceFacade.findAllInstances(getServiceContextAdministrador()).size();

        // Set PUBLISH_INTERNALLY procStatus
        instanceDto = statisticalOperationsServiceFacade.publishInternallyInstance(getServiceContextAdministrador(), instanceDto.getId());
        instanceDto = statisticalOperationsServiceFacade.publishExternallyInstance(getServiceContextAdministrador(), instanceDto.getId());

        // Delete instance with an incorrect procStatus
        try {
            statisticalOperationsServiceFacade.deleteInstance(getServiceContextAdministrador(), instanceDto.getId());
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.INVALID_PROC_STATUS.getCode(), e.getExceptionItems().get(0).getCode());
            assertEquals(1, e.getExceptionItems().size());
        }

        int instancesAfter = statisticalOperationsServiceFacade.findAllInstances(getServiceContextAdministrador()).size();
        assertEquals(instancesBefore, instancesAfter);
    }

    @Override
    @Test
    @Transactional
    public void testFindAllInstances() throws MetamacException {
        Long operationId = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing()).getId();
        statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationId);
        InstanceDto instanceDto = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationId, createInstanceDto());
        assertNotNull(instanceDto);

        List<InstanceBaseDto> instances = statisticalOperationsServiceFacade.findAllInstances(getServiceContextAdministrador());
        assertTrue(!instances.isEmpty());
    }

    @Override
    @Test
    @Transactional
    public void testFindInstanceByCondition() throws MetamacException {
        Long operationId = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing()).getId();
        statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationId);

        statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationId, createInstanceDto());
        statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationId, createInstanceDto());
        statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationId, createInstanceDto());

        MetamacCriteria criteria = new MetamacCriteria();
        MetamacCriteriaResult<InstanceBaseDto> result = statisticalOperationsServiceFacade.findInstanceByCondition(getServiceContextAdministrador(), criteria);
        assertTrue(result.getResults().size() >= 2);
    }

    @Test
    @Transactional
    public void testFindInstanceByConditionWithoutConditions() throws MetamacException {
        Long operationId = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing()).getId();
        statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationId);

        statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationId, createInstanceDto());
        statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationId, createInstanceDto());
        statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationId, createInstanceDto());

        MetamacCriteriaResult<InstanceBaseDto> result = statisticalOperationsServiceFacade.findInstanceByCondition(getServiceContextAdministrador(), null);
        assertTrue(result.getResults().size() >= 2);
    }

    @Test
    @Transactional
    public void testFindInstanceByConditionOrderByLastUpdatedDesc() throws MetamacException {
        Long operationId = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing()).getId();
        statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationId);

        Long id01 = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationId, createInstanceDto()).getId();
        Long id02 = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationId, createInstanceDto()).getId();
        Long id03 = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationId, createInstanceDto()).getId();

        MetamacCriteria metamacCriteria = new MetamacCriteria();
        addOrderToCriteria(metamacCriteria, InstanceCriteriaOrderEnum.LAST_UPDATED, OrderTypeEnum.DESC);

        MetamacCriteriaResult<InstanceBaseDto> instances = statisticalOperationsServiceFacade.findInstanceByCondition(getServiceContextAdministrador(), metamacCriteria);
        assertTrue(instances.getResults().get(0).getLastUpdated().after(instances.getResults().get(1).getLastUpdated()));
        assertTrue(instances.getResults().get(1).getLastUpdated().after(instances.getResults().get(2).getLastUpdated()));
        assertEquals(id03, instances.getResults().get(0).getId());
        assertEquals(id02, instances.getResults().get(1).getId());
        assertEquals(id01, instances.getResults().get(2).getId());
    }

    @Test
    @Transactional
    public void testFindInstanceByConditionOperationCode() throws MetamacException {

        // Insert data

        // Operation 01 --> Instance 011 and Instance 012
        OperationDto operation01 = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operation01.getId());

        InstanceDto instanceDto011 = createInstanceDto();
        instanceDto011.setCode("instanceDto011");
        InstanceDto instanceDto012 = createInstanceDto();
        instanceDto012.setCode("instanceDto012");

        statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operation01.getId(), instanceDto011);
        statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operation01.getId(), instanceDto012);

        // Operation 02 --> Instance 021
        OperationDto operation02 = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operation02.getId());

        InstanceDto instanceDto021 = createInstanceDto();
        instanceDto021.setCode("instanceDto021");
        statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operation02.getId(), instanceDto021);

        // Retrieve instances for "operation01" --> instanceDto011 && instanceDto012
        MetamacCriteria criteria = new MetamacCriteria();
        criteria.setRestriction(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.OPERATION_CODE.name(), operation01.getCode(), OperationType.EQ));

        MetamacCriteriaResult<InstanceBaseDto> result = statisticalOperationsServiceFacade.findInstanceByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Retrieve instances for "operation02" --> instanceDto021
        criteria = new MetamacCriteria();
        criteria.setRestriction(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.OPERATION_CODE.name(), operation02.getCode(), OperationType.EQ));

        result = statisticalOperationsServiceFacade.findInstanceByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(1, result.getResults().size());
    }

    @Test
    @Transactional
    public void testFindInstanceByConditionTitle() throws MetamacException {

        // Operation 01 --> Instance 01 and Instance 02
        OperationDto operation01 = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operation01.getId());

        InstanceDto instanceDto01 = createInstanceDto();
        instanceDto01.setCode("instanceDto01");
        instanceDto01.setTitle(MetamacMocks.mockInternationalStringDto("es", "Índice Precio de Consumo", "en", "Consumer Price Index"));

        InstanceDto instanceDto02 = createInstanceDto();
        instanceDto02.setCode("instanceDto02");
        instanceDto02.setTitle(MetamacMocks.mockInternationalStringDto("es", "Calidad de Vida", "en", "Quality of Life"));

        InstanceDto instanceDto03 = createInstanceDto();
        instanceDto03.setCode("instanceDto03");
        instanceDto03.setTitle(MetamacMocks.mockInternationalStringDto("es", "Condiciones de vida", "en", "Living"));

        statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operation01.getId(), instanceDto01);
        statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operation01.getId(), instanceDto02);
        statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operation01.getId(), instanceDto03);

        // Find "vida" or "quality" --> instanceDto02 and instanceDto03
        MetamacCriteria criteria = new MetamacCriteria();
        MetamacCriteriaDisjunctionRestriction disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.TITLE.name(), "vida", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.TITLE.name(), "quality", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        MetamacCriteriaResult<InstanceBaseDto> result = statisticalOperationsServiceFacade.findInstanceByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Find "vida" or "index" --> instanceDto01, instanceDto02 and instanceDto03
        criteria = new MetamacCriteria();
        disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.TITLE.name(), "vida", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.TITLE.name(), "index", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        result = statisticalOperationsServiceFacade.findInstanceByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(3, result.getResults().size());

        // Find "vida" --> instanceDto02 and instanceDto03
        criteria = new MetamacCriteria();
        criteria.setRestriction(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.TITLE.name(), "vida", OperationType.ILIKE));

        result = statisticalOperationsServiceFacade.findInstanceByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Find title "index" or code "instanceDto02"--> instanceDto01 and instanceDto02
        criteria = new MetamacCriteria();
        disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.CODE.name(), "instanceDto02", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.TITLE.name(), "index", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        result = statisticalOperationsServiceFacade.findInstanceByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Find title "index" or "consumo"--> instanceDto01
        criteria = new MetamacCriteria();
        disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.TITLE.name(), "consumo", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.TITLE.name(), "index", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        result = statisticalOperationsServiceFacade.findInstanceByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(1, result.getResults().size());
    }

    @Test
    @Transactional
    public void testFindInstanceByConditionAcronym() throws MetamacException {

        // Operation 01 --> Instance 01 and Instance 02
        OperationDto operation01 = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operation01.getId());

        InstanceDto instanceDto01 = createInstanceDto();
        instanceDto01.setCode("instanceDto01");
        instanceDto01.setAcronym(MetamacMocks.mockInternationalStringDto("es", "IPC", "en", "CPI"));

        InstanceDto instanceDto02 = createInstanceDto();
        instanceDto02.setCode("instanceDto02");
        instanceDto02.setAcronym(MetamacMocks.mockInternationalStringDto("es", "CALIDAD_VIDA", "en", "QL"));

        InstanceDto instanceDto03 = createInstanceDto();
        instanceDto03.setCode("instanceDto03");
        instanceDto03.setAcronym(MetamacMocks.mockInternationalStringDto("es", "CONDICIONES_VIDA", "en", "Living"));

        statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operation01.getId(), instanceDto01);
        statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operation01.getId(), instanceDto02);
        statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operation01.getId(), instanceDto03);

        // Find "vida" or "quality" --> instanceDto02 and instanceDto03
        MetamacCriteria criteria = new MetamacCriteria();
        MetamacCriteriaDisjunctionRestriction disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.ACRONYM.name(), "vida", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.ACRONYM.name(), "quality", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        MetamacCriteriaResult<InstanceBaseDto> result = statisticalOperationsServiceFacade.findInstanceByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Find "vida" or "index" --> instanceDto02 and instanceDto03
        criteria = new MetamacCriteria();
        disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.ACRONYM.name(), "ql", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.ACRONYM.name(), "condiciones", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        result = statisticalOperationsServiceFacade.findInstanceByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Find "vida" --> instanceDto02 and instanceDto03
        criteria = new MetamacCriteria();
        criteria.setRestriction(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.ACRONYM.name(), "vida", OperationType.ILIKE));

        result = statisticalOperationsServiceFacade.findInstanceByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Find title "index" or code "instanceDto02"--> instanceDto01 and instanceDto02
        criteria = new MetamacCriteria();
        disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.CODE.name(), "instanceDto02", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.ACRONYM.name(), "cp", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        result = statisticalOperationsServiceFacade.findInstanceByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Find title "index" or "consumo"--> instanceDto01
        criteria = new MetamacCriteria();
        disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.ACRONYM.name(), "cpi", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.ACRONYM.name(), "ipc", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        result = statisticalOperationsServiceFacade.findInstanceByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(1, result.getResults().size());
    }

    @Test
    @Transactional
    public void testFindInstanceByConditionDataDescription() throws MetamacException {

        // Operation 01 --> Instance 01 and Instance 02
        OperationDto operation01 = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operation01.getId());

        InstanceDto instanceDto01 = createInstanceDto();
        instanceDto01.setCode("instanceDto01");
        instanceDto01.setDataDescription(MetamacMocks.mockInternationalStringDto("es", "Índice Precio de Consumo", "en", "Consumer Price Index"));

        InstanceDto instanceDto02 = createInstanceDto();
        instanceDto02.setCode("instanceDto02");
        instanceDto02.setDataDescription(MetamacMocks.mockInternationalStringDto("es", "Calidad de Vida", "en", "Quality of Life"));

        InstanceDto instanceDto03 = createInstanceDto();
        instanceDto03.setCode("instanceDto03");
        instanceDto03.setDataDescription(MetamacMocks.mockInternationalStringDto("es", "Condiciones de vida", "en", "Living"));

        statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operation01.getId(), instanceDto01);
        statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operation01.getId(), instanceDto02);
        statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operation01.getId(), instanceDto03);

        // Find "vida" or "quality" --> instanceDto02 and instanceDto03
        MetamacCriteria criteria = new MetamacCriteria();
        MetamacCriteriaDisjunctionRestriction disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.DATA_DESCRIPTION.name(), "vida", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.DATA_DESCRIPTION.name(), "quality", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        MetamacCriteriaResult<InstanceBaseDto> result = statisticalOperationsServiceFacade.findInstanceByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Find "vida" or "index" --> instanceDto01, instanceDto02 and instanceDto03
        criteria = new MetamacCriteria();
        disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.DATA_DESCRIPTION.name(), "vida", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.DATA_DESCRIPTION.name(), "index", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        result = statisticalOperationsServiceFacade.findInstanceByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(3, result.getResults().size());

        // Find "vida" --> instanceDto02 and instanceDto03
        criteria = new MetamacCriteria();
        criteria.setRestriction(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.DATA_DESCRIPTION.name(), "vida", OperationType.ILIKE));

        result = statisticalOperationsServiceFacade.findInstanceByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Find title "index" or code "instanceDto02"--> instanceDto01 and instanceDto02
        criteria = new MetamacCriteria();
        disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.CODE.name(), "instanceDto02", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.DATA_DESCRIPTION.name(), "index", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        result = statisticalOperationsServiceFacade.findInstanceByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Find title "index" or "consumo"--> instanceDto01
        criteria = new MetamacCriteria();
        disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.DATA_DESCRIPTION.name(), "consumo", OperationType.ILIKE));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.DATA_DESCRIPTION.name(), "index", OperationType.ILIKE));
        criteria.setRestriction(disjunction);

        result = statisticalOperationsServiceFacade.findInstanceByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(1, result.getResults().size());
    }

    @Test
    @Transactional
    public void testFindInstanceByConditionProcStatus() throws MetamacException {
        // Create and publish operation
        Long operation = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing()).getId();
        statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operation);
        statisticalOperationsServiceFacade.publishExternallyOperation(getServiceContextAdministrador(), operation);

        // Create instances
        statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operation, createInstanceDto());
        Long instance02 = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operation, createInstanceDto()).getId();
        Long instance03 = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operation, createInstanceDto()).getId();
        Long instance04 = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operation, createInstanceDto()).getId();
        Long instance05 = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operation, createInstanceDto()).getId();
        Long instance06 = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operation, createInstanceDto()).getId();

        // Publish instances
        statisticalOperationsServiceFacade.publishInternallyInstance(getServiceContextAdministrador(), instance02);
        statisticalOperationsServiceFacade.publishInternallyInstance(getServiceContextAdministrador(), instance03);
        statisticalOperationsServiceFacade.publishInternallyInstance(getServiceContextAdministrador(), instance04);
        statisticalOperationsServiceFacade.publishExternallyInstance(getServiceContextAdministrador(), instance04);
        statisticalOperationsServiceFacade.publishInternallyInstance(getServiceContextAdministrador(), instance05);
        statisticalOperationsServiceFacade.publishExternallyInstance(getServiceContextAdministrador(), instance05);
        statisticalOperationsServiceFacade.publishInternallyInstance(getServiceContextAdministrador(), instance06);
        statisticalOperationsServiceFacade.publishExternallyInstance(getServiceContextAdministrador(), instance06);

        // Retrieve "PUBLISH_EXTERNALLY" or "PUBLISH_INTERNALLY"
        MetamacCriteria criteria = new MetamacCriteria();
        MetamacCriteriaDisjunctionRestriction disjunction = new MetamacCriteriaDisjunctionRestriction();
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.PROC_STATUS.name(), ProcStatusEnum.PUBLISH_EXTERNALLY, OperationType.EQ));
        disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.PROC_STATUS.name(), ProcStatusEnum.PUBLISH_INTERNALLY, OperationType.EQ));
        criteria.setRestriction(disjunction);

        MetamacCriteriaResult<InstanceBaseDto> result = statisticalOperationsServiceFacade.findInstanceByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(5, result.getResults().size());

        // Retrieve "DRAFT"
        criteria = new MetamacCriteria();
        criteria.setRestriction(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.PROC_STATUS.name(), ProcStatusEnum.DRAFT, OperationType.EQ));

        result = statisticalOperationsServiceFacade.findInstanceByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(1, result.getResults().size());

        // Retrieve "PUBLISH_EXTERNALLY*"
        criteria = new MetamacCriteria();
        criteria.setRestriction(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.PROC_STATUS.name(), ProcStatusEnum.PUBLISH_EXTERNALLY, OperationType.EQ));

        result = statisticalOperationsServiceFacade.findInstanceByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(3, result.getResults().size());
    }

    @Test
    @Transactional
    public void testFindInstanceByConditionOperationId() throws MetamacException {

        // Insert data

        // Operation 01 --> Instance 011 and Instance 012
        Long operation01 = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing()).getId();
        statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operation01);

        InstanceDto instanceDto011 = createInstanceDto();
        instanceDto011.setCode("instanceDto011");
        InstanceDto instanceDto012 = createInstanceDto();
        instanceDto012.setCode("instanceDto012");

        statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operation01, instanceDto011);
        statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operation01, instanceDto012);

        // Operation 02 --> Instance 021
        Long operation02 = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing()).getId();
        statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operation02);

        InstanceDto instanceDto021 = createInstanceDto();
        instanceDto021.setCode("instanceDto021");
        statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operation02, instanceDto021);

        // Retrieve instances for "operation01" --> instanceDto011 && instanceDto012
        MetamacCriteria criteria = new MetamacCriteria();
        criteria.setRestriction(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.OPERATION_ID.name(), operation01, OperationType.EQ));

        MetamacCriteriaResult<InstanceBaseDto> result = statisticalOperationsServiceFacade.findInstanceByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(2, result.getResults().size());

        // Retrieve instances for "operation02" --> instanceDto021
        criteria = new MetamacCriteria();
        criteria.setRestriction(new MetamacCriteriaPropertyRestriction(InstanceCriteriaPropertyEnum.OPERATION_ID.name(), operation02, OperationType.EQ));

        result = statisticalOperationsServiceFacade.findInstanceByCondition(getServiceContextAdministrador(), criteria);
        assertEquals(1, result.getResults().size());
    }

    @Test
    @Transactional
    public void testFindInstanceByConditionPaginated() throws MetamacException {
        Long operationId = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing()).getId();
        statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationId);

        statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationId, createInstanceDto());
        statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationId, createInstanceDto());
        statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationId, createInstanceDto());

        MetamacCriteria criteria = new MetamacCriteria();

        MetamacCriteriaPaginator paginator = new MetamacCriteriaPaginator();
        paginator.setCountTotalResults(Boolean.TRUE);
        paginator.setMaximumResultSize(Integer.valueOf(2));
        criteria.setPaginator(paginator);

        {
            // Page 1
            criteria.getPaginator().setFirstResult(Integer.valueOf(0));

            MetamacCriteriaResult<InstanceBaseDto> result = statisticalOperationsServiceFacade.findInstanceByCondition(getServiceContextAdministrador(), criteria);
            assertEquals(2, result.getResults().size());
            assertTrue(result.getPaginatorResult().getTotalResults() >= 3);
            assertEquals(Integer.valueOf(0), result.getPaginatorResult().getFirstResult());
        }

        {
            // Page 2
            criteria.getPaginator().setFirstResult(Integer.valueOf(2));
            MetamacCriteriaResult<InstanceBaseDto> result = statisticalOperationsServiceFacade.findInstanceByCondition(getServiceContextAdministrador(), criteria);
            assertTrue(result.getPaginatorResult().getTotalResults() >= 1);
            assertEquals(Integer.valueOf(2), result.getPaginatorResult().getFirstResult());

        }
    }

    @Override
    @Test
    @Transactional
    public void testFindInstanceById() throws MetamacException {
        // Create instance
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        operationDto = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();
        InstanceDto instanceDto = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationDto.getId(), createInstanceDto());

        InstanceDto instanceRetrieved = statisticalOperationsServiceFacade.findInstanceById(getServiceContextAdministrador(), instanceDto.getId());

        assertNotNull(instanceRetrieved);
        assertTrue(instanceDto.getId().equals(instanceRetrieved.getId()));
    }

    @Override
    @Test
    @Transactional
    public void testFindInstanceByCode() throws Exception {
        // Create instance
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        operationDto = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();
        InstanceDto instanceDto = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationDto.getId(), createInstanceDto());

        InstanceDto instanceRetrieved = statisticalOperationsServiceFacade.findInstanceByCode(getServiceContextAdministrador(), instanceDto.getCode());

        assertNotNull(instanceRetrieved);
        assertTrue(instanceDto.getCode().equals(instanceRetrieved.getCode()));
    }

    @Override
    @Test
    @Transactional
    public void testFindInstanceByUrn() throws Exception {
        // Create instance
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        operationDto = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();
        InstanceDto instanceDto = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationDto.getId(), createInstanceDto());

        InstanceDto instanceRetrieved = statisticalOperationsServiceFacade.findInstanceByUrn(getServiceContextAdministrador(), instanceDto.getUrn());

        assertNotNull(instanceRetrieved);
        assertTrue(instanceDto.getUrn().equals(instanceRetrieved.getUrn()));

    }

    @Override
    @Test
    @Transactional
    public void testPublishInternallyInstance() throws MetamacException {
        // Service doesn't check if the associated operation is PUBLISHED_INTERNALLY because it's a requirement for create it.

        // Create a PUBLISH_INTERNALLY Operation
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        operationDto = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();
        assertTrue(ProcStatusEnum.PUBLISH_INTERNALLY.equals(operationDto.getProcStatus()));

        // Create instance
        InstanceDto instanceDto = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationDto.getId(), createInstanceDto());

        int instancesBeforePublish = statisticalOperationsServiceFacade.findAllInstances(getServiceContextAdministrador()).size();
        int operationsBeforePublish = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();

        // Publish instance
        instanceDto = statisticalOperationsServiceFacade.publishInternallyInstance(getServiceContextAdministrador(), instanceDto.getId());

        // Validations
        assertNotNull(operationDto.getInternalInventoryDate());
        assertTrue(ProcStatusEnum.PUBLISH_INTERNALLY.equals(instanceDto.getProcStatus()));

        // Check number of operations and instances
        int instancesAfterPublish = statisticalOperationsServiceFacade.findAllInstances(getServiceContextAdministrador()).size();
        int operationsAfterPublish = statisticalOperationsServiceFacade.findAllOperations(getServiceContextAdministrador()).size();
        assertEquals(instancesBeforePublish, instancesAfterPublish);
        assertEquals(operationsBeforePublish, operationsAfterPublish);
    }

    @Override
    @Test
    @Transactional
    public void testPublishExternallyInstance() throws MetamacException {
        // Create a PUBLISH_EXTERNALLY Operation
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        operationDto = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();
        operationDto = statisticalOperationsServiceFacade.publishExternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();
        assertTrue(ProcStatusEnum.PUBLISH_EXTERNALLY.equals(operationDto.getProcStatus()));

        // Create instance
        InstanceDto instanceDto = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationDto.getId(), createInstanceDto());

        int instancesBefore = statisticalOperationsServiceFacade.findAllInstances(getServiceContextAdministrador()).size();

        // Publish instance
        instanceDto = statisticalOperationsServiceFacade.publishInternallyInstance(getServiceContextAdministrador(), instanceDto.getId());
        instanceDto = statisticalOperationsServiceFacade.publishExternallyInstance(getServiceContextAdministrador(), instanceDto.getId());

        // Validations
        assertNotNull(operationDto.getInternalInventoryDate());
        assertNotNull(operationDto.getInventoryDate());
        assertTrue(ProcStatusEnum.PUBLISH_EXTERNALLY.equals(instanceDto.getProcStatus()));

        // Check number of instances
        int instancesAfter = statisticalOperationsServiceFacade.findAllInstances(getServiceContextAdministrador()).size();
        assertEquals(instancesBefore, instancesAfter);

        // Check that instance can't be internally published
        try {
            statisticalOperationsServiceFacade.publishInternallyInstance(getServiceContextAdministrador(), instanceDto.getId());
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.INVALID_PROC_STATUS.getCode(), e.getExceptionItems().get(0).getCode());
        }
    }

    @Override
    @Test
    @Transactional
    public void testFindOperationForInstance() throws MetamacException {
        // Create operation
        OperationDto operationDto = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing());
        operationDto = statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationDto.getId()).getContent();

        // Create instance
        InstanceDto instanceDto = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationDto.getId(), createInstanceDto());

        int instancesBefore = statisticalOperationsServiceFacade.findAllInstances(getServiceContextAdministrador()).size();

        OperationBaseDto operationRetrieved = statisticalOperationsServiceFacade.findOperationForInstance(getServiceContextAdministrador(), instanceDto.getId());

        assertEquals(operationRetrieved.getId(), operationDto.getId());

        // Check number of instances
        int instancesAfter = statisticalOperationsServiceFacade.findAllInstances(getServiceContextAdministrador()).size();
        assertEquals(instancesBefore, instancesAfter);
    }

    @Override
    @Test
    @Transactional
    public void testExportOperationsTsv() throws Exception {
        // Create operation
        List<Long> operationsId = createListOperationsId();
        String fileName = statisticalOperationsServiceFacade.exportOperationsTsv(getServiceContextAdministrador(), operationsId);
        assertNotNull(fileName);

        // Check headers
        String pathTmpFile = tempDirPath() + File.separatorChar + fileName;
        File file = new File(pathTmpFile);
        BufferedReader bufferedReader = getBufferedReader(file);

        File testFile = new File("src/test/resources/tsv/operations.tsv");
        BufferedReader expectedBufferedReader = getBufferedReader(testFile);

        assertEquals(expectedBufferedReader.readLine(), bufferedReader.readLine());

        // Check information
        Set<String> lines = getFileLines(bufferedReader);
        Set<String> expectedLines = getFileLines(expectedBufferedReader);
        assertEquals(operationsId.size(), lines.size());

        int indexCreatedDate = 26;
        List<String> rows = new ArrayList<>(lines);
        Collections.sort(rows);
        List<String> row0 = Arrays.asList(rows.get(0).split("\t"));
        row0.set(indexCreatedDate, "");
        List<String> row1 = Arrays.asList(rows.get(1).split("\t"));
        row1.set(indexCreatedDate, "");
        List<String> row2 = Arrays.asList(rows.get(2).split("\t"));
        row2.set(indexCreatedDate, "");
        List<String> row3 = Arrays.asList(rows.get(3).split("\t"));
        row3.set(indexCreatedDate, "");

        List<String> expectedRows = new ArrayList<>(expectedLines);
        Collections.sort(expectedRows);
        List<String> expectedRow0 = Arrays.asList(expectedRows.get(0).split("\t"));
        expectedRow0.set(indexCreatedDate, "");
        List<String> expectedRow1 = Arrays.asList(expectedRows.get(1).split("\t"));
        expectedRow1.set(indexCreatedDate, "");
        List<String> expectedRow2 = Arrays.asList(expectedRows.get(2).split("\t"));
        expectedRow2.set(indexCreatedDate, "");
        List<String> expectedRow3 = Arrays.asList(expectedRows.get(3).split("\t"));
        expectedRow3.set(indexCreatedDate, "");

        assertEquals(row0, expectedRow0);
        assertEquals(row1, expectedRow1);
        assertEquals(row2, expectedRow2);
        assertEquals(row3, expectedRow3);

        expectedBufferedReader.close();
        bufferedReader.close();
        file.delete();
    }

    @Override
    @Test
    @Transactional
    public void testFindInstanceBaseById() throws MetamacException {
        // Create instance
        Long operationId = statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), createOperationDtoForInternalPublishing()).getId();
        statisticalOperationsServiceFacade.publishInternallyOperation(getServiceContextAdministrador(), operationId);

        InstanceDto instanceDto = statisticalOperationsServiceFacade.createInstance(getServiceContextAdministrador(), operationId, createInstanceDto());

        int instancesBefore = statisticalOperationsServiceFacade.findAllInstances(getServiceContextAdministrador()).size();

        InstanceBaseDto instanceRetrieved = statisticalOperationsServiceFacade.findInstanceBaseById(getServiceContextAdministrador(), instanceDto.getId());

        assertNotNull(instanceRetrieved);
        assertTrue(instanceDto.getId().equals(instanceRetrieved.getId()));

        // Check number of instances
        int instancesAfter = statisticalOperationsServiceFacade.findAllInstances(getServiceContextAdministrador()).size();
        assertEquals(instancesBefore, instancesAfter);

    }

    /**************************************************************************
     * PRIVATE UTILS
     **************************************************************************/

    private FamilyDto createFamilyDto() {
        FamilyDto familyDto = new FamilyDto();

        // Auditable information
        familyDto.setCreatedBy("Junit");
        familyDto.setCreatedDate(new Date());

        // Identifier
        familyDto.setCode("PRUEBA01" + RandomStringUtils.random(50, true, true));

        // TITLE
        InternationalStringDto title = new InternationalStringDto();
        LocalisedStringDto title_es = new LocalisedStringDto();
        title_es.setLabel("Título en español de familia");
        title_es.setLocale("es");
        LocalisedStringDto title_en = new LocalisedStringDto();
        title_en.setLabel("Título en inglés de familia");
        title_en.setLocale("en");
        title.addText(title_es);
        title.addText(title_en);
        familyDto.setTitle(title);

        // DESCRIPTION
        InternationalStringDto description = new InternationalStringDto();
        LocalisedStringDto description_es = new LocalisedStringDto();
        description_es.setLabel("Descripción en español de familia");
        description_es.setLocale("es");
        LocalisedStringDto description_en = new LocalisedStringDto();
        description_en.setLabel("Descripción en inglés de familia");
        description_en.setLocale("en");
        description.addText(description_es);
        description.addText(description_en);
        familyDto.setDescription(description);

        return familyDto;
    }

    private FamilyDto createFamilyDtoForUpdate() {
        FamilyDto familyDto = new FamilyDto();

        // Auditable information
        familyDto.setCreatedBy("Junit");
        familyDto.setCreatedDate(new Date());

        // Identifier
        familyDto.setCode("PRUEBA01" + RandomStringUtils.random(50, true, true));

        // TITLE
        InternationalStringDto title = new InternationalStringDto();
        LocalisedStringDto title_es = new LocalisedStringDto();
        title_es.setLabel("NO VISIBLE Título en español de familia");
        title_es.setLocale("es");
        LocalisedStringDto title_en = new LocalisedStringDto();
        title_en.setLabel("NO VISIBLE Título en inglés de familia");
        title_en.setLocale("en");
        title.addText(title_es);
        title.addText(title_en);
        familyDto.setTitle(title);

        // DESCRIPTION
        InternationalStringDto description = new InternationalStringDto();
        LocalisedStringDto description_es = new LocalisedStringDto();
        description_es.setLabel("NO VISIBLE Descripción en español de familia");
        description_es.setLocale("es");
        LocalisedStringDto description_en = new LocalisedStringDto();
        description_en.setLabel("NO VISIBLE Descripción en inglés de familia");
        description_en.setLocale("en");
        description.addText(description_es);
        description.addText(description_en);
        familyDto.setDescription(description);

        return familyDto;
    }

    private OperationDto createOperationDto() throws MetamacException {
        OperationDto operationDto = new OperationDto();

        // Auditable information
        operationDto.setCreatedBy("Junit");
        operationDto.setCreatedDate(new Date());

        // Identifier
        operationDto.setCode("PRUEBA01" + RandomStringUtils.random(3, true, true));

        // TITLE
        InternationalStringDto title = new InternationalStringDto();
        LocalisedStringDto title_es = new LocalisedStringDto();
        title_es.setLabel("Título en español de operacion");
        title_es.setLocale("es");
        LocalisedStringDto title_en = new LocalisedStringDto();
        title_en.setLabel("Título en inglés de operacion");
        title_en.setLocale("en");
        title.addText(title_es);
        title.addText(title_en);
        operationDto.setTitle(title);

        // ACRONYM
        InternationalStringDto acronym = new InternationalStringDto();
        LocalisedStringDto acronym_es = new LocalisedStringDto();
        acronym_es.setLabel("Descripción en español de operacion");
        acronym_es.setLocale("es");
        LocalisedStringDto acronym_en = new LocalisedStringDto();
        acronym_en.setLabel("Descripción en inglés de operacion");
        acronym_en.setLocale("en");
        acronym.addText(acronym_es);
        acronym.addText(acronym_en);
        operationDto.setAcronym(acronym);

        // RELEASE_CALENDAR
        operationDto.setReleaseCalendar(true);

        // RELEASE_CALENDAR_ACCESS
        operationDto.setReleaseCalendarAccess("http://www.test.com");

        // SURVEY_TYPE
        operationDto.setSurveyType(statisticalOperationsServiceFacade.findSurveyTypeById(getServiceContextAdministrador(), Long.valueOf(1)));

        // OFFICIALITY_TYPE
        operationDto.setOfficialityType(statisticalOperationsServiceFacade.findOfficialityTypeById(getServiceContextAdministrador(), Long.valueOf(1)));

        // SUBJECT_AREA
        operationDto.setSubjectArea(mockExternalItemDto("HEALTH", "/uri/test/category", "URN:CATEGORY:HEALTH", "URN:CATEGORY:HEALTH:provider", TypeExternalArtefactsEnum.CATEGORY));

        // STATUS
        operationDto.setStatus(StatusEnum.PLANNING);

        // INDICATOR_SYSTEM
        operationDto.setIndicatorSystem(false);

        return operationDto;
    }

    private OperationDto createOperationDtoForInternalPublishing() throws MetamacException {
        OperationDto operationDto = createOperationDto();

        // OBJECTIVE
        InternationalStringDto objective = new InternationalStringDto();
        LocalisedStringDto objective_es = new LocalisedStringDto();
        objective_es.setLabel("OPERACION - OBJECTIVE - ES");
        objective_es.setLocale("es");
        LocalisedStringDto objective_en = new LocalisedStringDto();
        objective_en.setLabel("OPERACION - OBJECTIVE - EN");
        objective_en.setLocale("en");
        objective.addText(objective_es);
        objective.addText(objective_en);
        operationDto.setObjective(objective);

        // SURVEY_TYPE
        operationDto.setSurveyType(statisticalOperationsServiceFacade.findSurveyTypeById(getServiceContextAdministrador(), Long.valueOf(1)));

        // PRODUCER
        operationDto.addProducer(mockExternalItemDto("ISTAC", "/uri/test/agency", "URN:AGENCY:ISTAC", null, TypeExternalArtefactsEnum.AGENCY));
        operationDto.addProducer(mockExternalItemDto("INE", "/uri/test/agency", "URN:AGENCY:INE", null, TypeExternalArtefactsEnum.AGENCY));

        // REGIONAL_RESPONSIBLE
        operationDto.addRegionalResponsible(mockExternalItemDto("ISTAC", "/uri/test/agency", "URN:AGENCY:ISTAC", null, TypeExternalArtefactsEnum.AGENCY));

        // PUBLISHER
        operationDto.addPublisher(mockExternalItemDto("ISTAC", "/uri/test/agency", "URN:AGENCY:ISTAC", null, TypeExternalArtefactsEnum.AGENCY));

        // COMMON_METADATA
        operationDto.setCommonMetadata(mockExternalItemDto("ISTAC", "/uri/test/common_metadata", "URN:COMMON_METADATA:ISTAC", null, TypeExternalArtefactsEnum.CONFIGURATION));

        return operationDto;
    }

    private OperationDto createOperationDtoWithProducer() throws MetamacException {
        OperationDto operationDto = createOperationDto();

        // PRODUCER
        operationDto.addProducer(mockExternalItemDto("ISTAC", "/uri/test/agency?remove", "URN:AGENCY:ISTAC?REMOVE", null, TypeExternalArtefactsEnum.AGENCY));
        operationDto.addProducer(mockExternalItemDto("INE", "/uri/test/agency?remove", "URN:AGENCY:INE?REMOVE", null, TypeExternalArtefactsEnum.AGENCY));

        return operationDto;
    }

    private OperationDto createOperationDtoWithOfficialityType() throws MetamacException {
        OperationDto operationDto = createOperationDto();

        // OFFICIALITY TYPE
        operationDto.setOfficialityType(statisticalOperationsServiceFacade.findOfficialityTypeById(getServiceContextAdministrador(), Long.valueOf(1)));

        return operationDto;
    }

    private InstanceDto createInstanceDto() throws MetamacException {
        InstanceDto instanceDto = new InstanceDto();

        // Auditable information
        instanceDto.setCreatedBy("Junit");
        instanceDto.setCreatedDate(new Date());

        // Identifier
        instanceDto.setCode("PRUEBA01" + RandomStringUtils.random(50, true, true));

        // TITLE
        InternationalStringDto title = new InternationalStringDto();
        LocalisedStringDto title_es = new LocalisedStringDto();
        title_es.setLabel("Título en español de operacion");
        title_es.setLocale("es");
        LocalisedStringDto title_en = new LocalisedStringDto();
        title_en.setLabel("Título en inglés de operacion");
        title_en.setLocale("en");
        title.addText(title_es);
        title.addText(title_en);
        instanceDto.setTitle(title);

        // INSTANCE_TYPE
        instanceDto.setInstanceType(statisticalOperationsServiceFacade.findInstanceTypeById(getServiceContextAdministrador(), Long.valueOf(1)));

        return instanceDto;
    }

    private InstanceDto createInstanceDtoWithGeographicGranularity() throws MetamacException {
        InstanceDto instanceDto = createInstanceDto();

        // GEOGRAPHIC_GRANULARITY
        instanceDto.addGeographicGranularity(mockExternalItemDto("GEOGRAPHIC_GRANULARITY", "/uri/test/concept", "URN:CONCEPT:GEOGRAPHIC_GRANULARITY", null, TypeExternalArtefactsEnum.CONCEPT));

        return instanceDto;

    }

    private InstanceDto createIncompleteInstanceDto() {
        InstanceDto instanceDto = new InstanceDto();

        // Auditable information
        instanceDto.setCreatedBy("Junit");
        instanceDto.setCreatedDate(new Date());

        // Identifier
        instanceDto.setCode("PRUEBA01" + RandomStringUtils.random(50, true, true));

        return instanceDto;
    }

    /**
     * Extract external item by code. IMPORTANT: code can be not unique. This method is only for tests
     */
    private ExternalItemDto getExternalItemDtoByCode(Set<ExternalItemDto> sources, String code) {
        for (ExternalItemDto externalItemDto : sources) {
            if (externalItemDto.getCode().equals(code)) {
                return externalItemDto;
            }
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
    private void addOrderToCriteria(MetamacCriteria metamacCriteria, Enum property, OrderTypeEnum orderType) {
        if (metamacCriteria.getOrdersBy() == null) {
            metamacCriteria.setOrdersBy(new ArrayList<MetamacCriteriaOrder>());
        }
        MetamacCriteriaOrder order = new MetamacCriteriaOrder();
        order.setType(orderType);
        order.setPropertyName(property.name());
        metamacCriteria.getOrdersBy().add(order);
    }

    private List<Long> createListOperationsId() throws MetamacException, ParseException {
        List<Long> operationsId = new ArrayList<Long>();

        OperationDto operationDtoForInternalPublishing = createOperationDtoForInternalPublishing();
        operationDtoForInternalPublishing.setCode("CODE01eXp");
        operationsId.add(statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), operationDtoForInternalPublishing).getId());

        OperationDto operationDtoWithProducer = createOperationDtoWithProducer();
        operationDtoWithProducer.setCode("CODE02eXp");
        operationsId.add(statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), operationDtoWithProducer).getId());

        OperationDto operationDtoWithOfficialityType = createOperationDtoWithOfficialityType();
        operationDtoWithOfficialityType.setCode("CODE03eXp");
        operationsId.add(statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), operationDtoWithOfficialityType).getId());

        OperationDto operationDto = createOperationDtoWithOfficialityType();
        operationDto.setCode("CODE04eXp");
        operationsId.add(statisticalOperationsServiceFacade.createOperation(getServiceContextAdministrador(), operationDto).getId());

        return operationsId;
    }

    private BufferedReader getBufferedReader(File file) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");

        return new BufferedReader(inputStreamReader);
    }

    private Set<String> getFileLines(BufferedReader bufferedReader) throws IOException {
        Set<String> lines = new HashSet<>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            line.replaceAll("\t", "\\\t");
            lines.add(line);
        }
        return lines;
    }

}

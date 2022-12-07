package org.siemac.metamac.statistical.operations.core.dto;

import static org.junit.Assert.fail;

import org.junit.Test;

public class OperationBaseDtoFieldsTest {

    @Test
    public void testExistsGetCode() {
        checkIfExistsMethodInOperationBaseDto("getCode");
    }

    @Test
    public void testExistsGetUrn() {
        checkIfExistsMethodInOperationBaseDto("getUrn");
    }

    @Test
    public void testExistsGetTitle() {
        checkIfExistsMethodInOperationBaseDto("getTitle");
    }

    @Test
    public void testExistsGetAcronym() {
        checkIfExistsMethodInOperationBaseDto("getAcronym");
    }

    @Test
    public void testExistsGetProcStatus() {
        checkIfExistsMethodInOperationBaseDto("getProcStatus");
    }

    @Test
    public void testExistsGetSubjectArea() {
        checkIfExistsMethodInOperationBaseDto("getSubjectArea");
    }

    @Test
    public void testExistsGetDescription() {
        checkIfExistsMethodInOperationBaseDto("getDescription");
    }

    @Test
    public void testExistsGetSurveyType() {
        checkIfExistsMethodInOperationBaseDto("getSurveyType");
    }

    @Test
    public void testExistsGetOfficialityType() {
        checkIfExistsMethodInOperationBaseDto("getOfficialityType");
    }

    @Test
    public void testExistsGetIndicatorSystem() {
        checkIfExistsMethodInOperationBaseDto("getIndicatorSystem");
    }

    @Test
    public void testExistsGetCurrentlyActive() {
        checkIfExistsMethodInOperationBaseDto("getCurrentlyActive");
    }

    @Test
    public void testExistsGetStatus() {
        checkIfExistsMethodInOperationBaseDto("getStatus");
    }

    @Test
    public void testExistsGetInternalInventoryDate() {
        checkIfExistsMethodInOperationBaseDto("getInternalInventoryDate");
    }

    @Test
    public void testExistsGetCreatedDate() {
        checkIfExistsMethodInOperationBaseDto("getCreatedDate");
    }

    @Test
    public void testExistsGetTechnicianInCharge() {
        checkIfExistsMethodInOperationBaseDto("getTechnicianInCharge");
    }

    @Test
    public void testExistsGetAssistantTechnician() {
        checkIfExistsMethodInOperationBaseDto("getAssistantTechnician");
    }

    private void checkIfExistsMethodInOperationBaseDto(String methodName) {
        try {
            OperationBaseDto.class.getMethod(methodName);
        } catch (NoSuchMethodException e) {
            fail("undefined method");
        }
    }

}

package org.siemac.metamac.statistical.operations.web.client.utils;

import static org.siemac.metamac.statistical.operations.web.client.OperationsWeb.getConstants;

import org.siemac.metamac.statistical.operations.web.client.model.ds.FamilyDS;
import org.siemac.metamac.statistical.operations.web.client.model.ds.InstanceDS;
import org.siemac.metamac.statistical.operations.web.client.model.ds.OperationDS;
import org.siemac.metamac.web.common.client.utils.ListGridUtils;
import org.siemac.metamac.web.common.client.widgets.CustomListGridField;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;

public class ResourceListFieldUtils {

    public static CustomListGridField[] getFamilyFields() {

        CustomListGridField code = new CustomListGridField(FamilyDS.CODE, getConstants().familyCode());

        CustomListGridField urn = new CustomListGridField(FamilyDS.URN, getConstants().familyUrn());
        urn.setShowIfCondition(ListGridUtils.getFalseListGridFieldIfFunction());

        CustomListGridField title = new CustomListGridField(FamilyDS.TITLE, getConstants().familyTitle());

        CustomListGridField acronym = new CustomListGridField(FamilyDS.ACRONYM, getConstants().familyAcronym());
        acronym.setShowIfCondition(ListGridUtils.getFalseListGridFieldIfFunction());

        CustomListGridField createdDate = new CustomListGridField(FamilyDS.CREATED_DATE, getConstants().familyCreatedDate());
        createdDate.setShowIfCondition(ListGridUtils.getFalseListGridFieldIfFunction());

        CustomListGridField internalInventoryDate = new CustomListGridField(FamilyDS.INTERNAL_INVENTORY_DATE, getConstants().familyInternalInventoryDate());
        internalInventoryDate.setShowIfCondition(ListGridUtils.getFalseListGridFieldIfFunction());

        CustomListGridField procStatus = new CustomListGridField(FamilyDS.PROC_STATUS, getConstants().familyProcStatus());

        return new CustomListGridField[]{code, urn, title, acronym, createdDate, internalInventoryDate, procStatus};
    }

    public static CustomListGridField[] getOperationFields() {

        CustomListGridField code = new CustomListGridField(OperationDS.CODE, getConstants().operationCode());

        CustomListGridField urn = new CustomListGridField(OperationDS.URN, getConstants().operationUrn());
        urn.setShowIfCondition(ListGridUtils.getFalseListGridFieldIfFunction());

        CustomListGridField title = new CustomListGridField(OperationDS.TITLE, getConstants().operationTitle());

        CustomListGridField acronym = new CustomListGridField(OperationDS.ACRONYM, getConstants().operationAcronym());
        acronym.setShowIfCondition(ListGridUtils.getFalseListGridFieldIfFunction());

        CustomListGridField statisticPlanCode = new CustomListGridField(OperationDS.STATISTIC_PLAN, getConstants().statisticPlanCode());
        statisticPlanCode.setShowIfCondition(ListGridUtils.getFalseListGridFieldIfFunction());

        CustomListGridField subjectArea = new CustomListGridField(OperationDS.SUBJECT_AREA, getConstants().operationSubjectArea());
        subjectArea.setShowIfCondition(ListGridUtils.getFalseListGridFieldIfFunction());

        CustomListGridField surveyType = new CustomListGridField(OperationDS.STATISTICAL_OPERATION_TYPE, getConstants().operationSurveyType());
        surveyType.setShowIfCondition(ListGridUtils.getFalseListGridFieldIfFunction());

        CustomListGridField officialityType = new CustomListGridField(OperationDS.OFFICIALITY_TYPE, getConstants().operationOfficialityType());
        officialityType.setShowIfCondition(ListGridUtils.getFalseListGridFieldIfFunction());

        CustomListGridField indicatorsSystem = new CustomListGridField(OperationDS.INDICATOR_SYSTEM, getConstants().operationIndicatorSystem());
        indicatorsSystem.setType(ListGridFieldType.IMAGE);
        indicatorsSystem.setShowIfCondition(ListGridUtils.getFalseListGridFieldIfFunction());

        CustomListGridField createdDate = new CustomListGridField(OperationDS.CREATED_DATE, getConstants().operationCreatedDate());
        createdDate.setShowIfCondition(ListGridUtils.getFalseListGridFieldIfFunction());

        CustomListGridField internalInventoryDate = new CustomListGridField(OperationDS.INTERNAL_INVENTORY_DATE, getConstants().operationInternalInventoryDate());
        internalInventoryDate.setShowIfCondition(ListGridUtils.getFalseListGridFieldIfFunction());

        CustomListGridField currentlyActive = new CustomListGridField(OperationDS.CURRENTLY_ACTIVE, getConstants().operationCurrentlyActive());
        currentlyActive.setShowIfCondition(ListGridUtils.getFalseListGridFieldIfFunction());

        CustomListGridField procStatus = new CustomListGridField(OperationDS.PROC_STATUS, getConstants().operationProcStatus());

        CustomListGridField status = new CustomListGridField(OperationDS.STATUS, getConstants().operationStatus());

        CustomListGridField publicationStreamStatus = new CustomListGridField(OperationDS.PUBLISH_MSG_STATUS_KAFKA, getConstants().operationPublicationStreamStatus());
        publicationStreamStatus.setType(ListGridFieldType.IMAGE);
        publicationStreamStatus.setAlign(Alignment.CENTER);

        CustomListGridField edatosMigrationStatus = new CustomListGridField(OperationDS.EDATOS_MIGRATION_STATUS, getConstants().edatosMigrationStatus());
        edatosMigrationStatus.setShowIfCondition(ListGridUtils.getFalseListGridFieldIfFunction());

        CustomListGridField technicianInCharge = new CustomListGridField(OperationDS.TECHNICIAN_IN_CHARGE, getConstants().operationTechnicianInCharge());
        CustomListGridField assistantTechnician = new CustomListGridField(OperationDS.ASSISTANT_TECHNICIAN, getConstants().operationAssistantTechnician());

        CustomListGridField disaggregationBySex = new CustomListGridField(OperationDS.DISAGGREGATION_BY_SEX, getConstants().operationDisaggregationBySex());
        disaggregationBySex.setShowIfCondition(ListGridUtils.getFalseListGridFieldIfFunction());

        CustomListGridField disaggregationByAge = new CustomListGridField(OperationDS.DISAGGREGATION_BY_AGE, getConstants().operationDisaggregationByAge());
        disaggregationByAge.setShowIfCondition(ListGridUtils.getFalseListGridFieldIfFunction());

        CustomListGridField disaggregationByNationality = new CustomListGridField(OperationDS.DISAGGREGATION_BY_NATIONALITY, getConstants().operationDisaggregationByNationality());
        disaggregationByNationality.setShowIfCondition(ListGridUtils.getFalseListGridFieldIfFunction());

        CustomListGridField disaggregationByDisability = new CustomListGridField(OperationDS.DISAGGREGATION_BY_DISABILITY, getConstants().operationDisaggregationByDisability());
        disaggregationByDisability.setShowIfCondition(ListGridUtils.getFalseListGridFieldIfFunction());

        return new CustomListGridField[]{code, urn, title, acronym, statisticPlanCode, subjectArea, surveyType, officialityType, indicatorsSystem, createdDate, internalInventoryDate, currentlyActive, procStatus, status, publicationStreamStatus, edatosMigrationStatus, technicianInCharge, assistantTechnician, disaggregationBySex, disaggregationByAge, disaggregationByNationality, disaggregationByDisability
        };
    }

    public static CustomListGridField[] getInstanceFields() {

        CustomListGridField code = new CustomListGridField(InstanceDS.CODE, getConstants().instanceCode());

        CustomListGridField urn = new CustomListGridField(InstanceDS.URN, getConstants().instanceUrn());
        urn.setShowIfCondition(ListGridUtils.getFalseListGridFieldIfFunction());

        CustomListGridField title = new CustomListGridField(InstanceDS.TITLE, getConstants().instanceTitle());

        CustomListGridField acronym = new CustomListGridField(InstanceDS.TITLE, getConstants().instanceTitle());
        acronym.setShowIfCondition(ListGridUtils.getFalseListGridFieldIfFunction());

        CustomListGridField instanceType = new CustomListGridField(InstanceDS.INSTANCE_TYPE, getConstants().instanceType());
        instanceType.setShowIfCondition(ListGridUtils.getFalseListGridFieldIfFunction());

        CustomListGridField createdDate = new CustomListGridField(InstanceDS.CREATED_DATE, getConstants().instanceCreatedDate());
        createdDate.setShowIfCondition(ListGridUtils.getFalseListGridFieldIfFunction());

        CustomListGridField internalInventoryDate = new CustomListGridField(InstanceDS.INTERNAL_INVENTORY_DATE, getConstants().instanceInternalInventoryDate());
        internalInventoryDate.setShowIfCondition(ListGridUtils.getFalseListGridFieldIfFunction());

        CustomListGridField procStatus = new CustomListGridField(InstanceDS.PROC_STATUS, getConstants().instanceProcStatus());

        CustomListGridField order = new CustomListGridField(InstanceDS.ORDER, getConstants().instanceOrder());

        return new CustomListGridField[]{code, urn, title, acronym, instanceType, createdDate, internalInventoryDate, procStatus, order};
    }
}

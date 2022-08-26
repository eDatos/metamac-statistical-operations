package org.siemac.metamac.statistical.operations.core.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.test.dbunit.MetamacDBUnitBaseTests;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.sso.client.MetamacPrincipalAccess;
import org.siemac.metamac.sso.client.SsoClientConstants;
import org.siemac.metamac.statistical.operations.core.constants.StatisticalOperationsConstants;
import org.siemac.metamac.statistical.operations.core.enume.domain.StatisticalOperationsRoleEnum;
import org.springframework.beans.factory.annotation.Value;

public abstract class StatisticalOperationsBaseTest extends MetamacDBUnitBaseTests {

    @Value("${metamac.statistical_operations.db.default_schema}")
    private String      defaultSchema;

    public final String OPERATION_01 = "C0025A";
    public final String OPERATION_02 = "C0025B";

    // --------------------------------------------------------------------------------------------------------------
    // SERVICE CONTEXT
    // --------------------------------------------------------------------------------------------------------------

    protected ServiceContext getServiceContextAdministrador() {
        ServiceContext serviceContext = mockServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, StatisticalOperationsRoleEnum.ADMINISTRADOR);
        return serviceContext;
    }

    protected ServiceContext getServiceContextTecnicoProduccion() {
        ServiceContext serviceContext = mockServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, StatisticalOperationsRoleEnum.TECNICO_PRODUCCION);
        return serviceContext;
    }

    protected ServiceContext getServiceContextTecnicoProduccionOperation01() {
        ServiceContext serviceContext = mockServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, StatisticalOperationsRoleEnum.TECNICO_PRODUCCION, OPERATION_01);
        return serviceContext;
    }

    protected ServiceContext getServiceContextTecnicoProduccionOperation02() {
        ServiceContext serviceContext = mockServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, StatisticalOperationsRoleEnum.TECNICO_PRODUCCION, OPERATION_02);
        return serviceContext;
    }

    protected ServiceContext getServiceContextTecnicoApoyoProduccion() {
        ServiceContext serviceContext = mockServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, StatisticalOperationsRoleEnum.TECNICO_APOYO_PRODUCCION);
        return serviceContext;
    }

    protected ServiceContext getServiceContextTecnicoPlanificacion() {
        ServiceContext serviceContext = mockServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, StatisticalOperationsRoleEnum.TECNICO_PLANIFICACION);
        return serviceContext;
    }

    protected ServiceContext getServiceContextTecnicoApoyoPlanificacion() {
        ServiceContext serviceContext = mockServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, StatisticalOperationsRoleEnum.TECNICO_APOYO_PLANIFICACION);
        return serviceContext;
    }

    protected ServiceContext getServiceContextTecnicoDifusion() {
        ServiceContext serviceContext = mockServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, StatisticalOperationsRoleEnum.TECNICO_DIFUSION);
        return serviceContext;
    }

    protected ServiceContext getServiceContextTecnicoApoyoDifusion() {
        ServiceContext serviceContext = mockServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, StatisticalOperationsRoleEnum.TECNICO_APOYO_DIFUSION);
        return serviceContext;
    }

    private void putMetamacPrincipalInServiceContext(ServiceContext serviceContext, StatisticalOperationsRoleEnum role) {
        MetamacPrincipal metamacPrincipal = new MetamacPrincipal();
        metamacPrincipal.setUserId(serviceContext.getUserId());
        metamacPrincipal.getAccesses().add(new MetamacPrincipalAccess(role.getName(), StatisticalOperationsConstants.APPLICATION_ID, null));
        serviceContext.setProperty(SsoClientConstants.PRINCIPAL_ATTRIBUTE, metamacPrincipal);
    }

    private void putMetamacPrincipalInServiceContext(ServiceContext serviceContext, StatisticalOperationsRoleEnum role, String operationCode) {
        MetamacPrincipal metamacPrincipal = new MetamacPrincipal();
        metamacPrincipal.setUserId(serviceContext.getUserId());
        metamacPrincipal.getAccesses().add(new MetamacPrincipalAccess(role.getName(), StatisticalOperationsConstants.APPLICATION_ID, operationCode));
        serviceContext.setProperty(SsoClientConstants.PRINCIPAL_ATTRIBUTE, metamacPrincipal);
    }

    // --------------------------------------------------------------------------------------------------------------
    // DBUNIT CONFIGURATION
    // --------------------------------------------------------------------------------------------------------------

    @Override
    protected String getDataSetFile() {
        return "dbunit/StatisticalOperationsBaseServiceTest.xml";
    }

    @Override
    protected List<String> getTableNamesOrderedByFKDependency() {
        List<String> tables = new ArrayList<String>();
        tables.add("TB_SEQUENCES");

        tables.add("TB_INTERNATIONAL_STRINGS");
        tables.add("TB_FAMILIES");

        tables.add("TB_EXTERNAL_ITEMS");

        tables.add("TB_OPERATIONS");
        tables.add("TB_FAMILIES_OPERATIONS");
        tables.add("TB_INSTANCES");
        tables.add("TB_INSTANCES_COSTS");

        tables.add("TB_EI_CLASS_SYSTEM_LISTS");
        tables.add("TB_EI_CONC_DEF_LISTS");
        tables.add("TB_EI_FREQ_COLL");
        tables.add("TB_EI_INF_SUPPLIERS");
        tables.add("TB_EI_PRODUCERS");
        tables.add("TB_EI_PUBLISHERS");
        tables.add("TB_EI_REG_CONTRIBUTORS");
        tables.add("TB_EI_REG_RESPONSIBLES");
        tables.add("TB_EI_SECONDARY_AREAS");
        tables.add("TB_EI_STATISTICAL_UNITS");
        tables.add("TB_EI_UNITS_MEASURE");
        tables.add("TB_EI_UPDATE_FREQUENCY");

        tables.add("TB_LOCALISED_STRINGS");
        return tables;
    }

    @Override
    protected Map<String, List<String>> getTablePrimaryKeys() {
        Map<String, List<String>> primaryKeys = new HashMap<String, List<String>>();
        primaryKeys.put("TB_SEQUENCES", Arrays.asList("SEQUENCE_NAME"));
        return primaryKeys;
    }

    @Override
    protected String getDefaultSchema() {
        return defaultSchema;
    }

    protected String tempDirPath() throws IOException {
        File temp = File.createTempFile("temp-file-name", ".tmp");
        String absolutePath = temp.getAbsolutePath();
        String tempFilePath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
        temp.delete();
        return tempFilePath;
    }

}

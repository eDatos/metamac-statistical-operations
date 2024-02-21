package org.siemac.metamac.statistical_operations.rest.external.v1_0.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.ServerWebApplicationException;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.provider.JAXBElementProvider;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.BeforeClass;
import org.junit.Test;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.rest.common.test.MetamacRestBaseTest;
import org.siemac.metamac.rest.common.test.ServerResource;
import org.siemac.metamac.rest.common.test.mockito.PagingParameterMatcher;
import org.siemac.metamac.rest.common.v1_0.domain.ComparisonOperator;
import org.siemac.metamac.rest.common.v1_0.domain.LogicalOperator;
import org.siemac.metamac.rest.constants.RestConstants;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.CollMethods;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Costs;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Families;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Family;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.FamilyCriteriaPropertyRestriction;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Instance;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.InstanceCriteriaPropertyRestriction;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.InstanceTypes;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Instances;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.OfficialityTypes;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Operation;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.OperationCriteriaPropertyRestriction;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Operations;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.StatisticalOperationSources;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.StatisticalOperationTypes;
import org.siemac.metamac.rest.utils.RestUtils;
import org.siemac.metamac.statistical.operations.core.domain.FamilyProperties;
import org.siemac.metamac.statistical.operations.core.domain.InstanceProperties;
import org.siemac.metamac.statistical.operations.core.domain.OperationProperties;
import org.siemac.metamac.statistical.operations.core.serviceapi.StatisticalOperationsBaseService;
import org.siemac.metamac.statistical.operations.core.serviceapi.StatisticalOperationsListsService;
import org.siemac.metamac.statistical_operations.rest.external.exception.RestServiceExceptionType;
import org.siemac.metamac.statistical_operations.rest.external.invocation.CommonMetadataRestExternalFacade;
import org.siemac.metamac.statistical_operations.rest.external.v1_0.mockito.FindFamiliesByOperationMatcher;
import org.siemac.metamac.statistical_operations.rest.external.v1_0.mockito.FindFamiliesMatcher;
import org.siemac.metamac.statistical_operations.rest.external.v1_0.mockito.FindInstancesByOperationMatcher;
import org.siemac.metamac.statistical_operations.rest.external.v1_0.mockito.FindOperationsByFamilyMatcher;
import org.siemac.metamac.statistical_operations.rest.external.v1_0.mockito.FindOperationsMatcher;
import org.siemac.metamac.statistical_operations.rest.external.v1_0.utils.StatisticalOperationsCoreMocks;
import org.siemac.metamac.statistical_operations.rest.external.v1_0.utils.StatisticalOperationsRestAsserts;
import org.siemac.metamac.statistical_operations.rest.external.v1_0.utils.StatisticalOperationsRestMocks;
import org.springframework.context.ApplicationContext;

public class StatisticalOperationsRestExternalFacadeV10Test extends MetamacRestBaseTest {

    private static final String                   PORT                                            = ServerResource.PORT;
    private static String                         jaxrsServerAddress                              = "http://localhost:" + PORT + "/apis/operations";
    private static String                         baseApi                                         = jaxrsServerAddress + "/v1.0";

    private static String                         srmApiExternalEndpoint;
    // not read property from properties file to check explicity
    private static String                         statisticalOperationsApiExternalEndpointV10     = "http://data.istac.es/apis/operations/v1.0";

    private static StatisticalOperationsV1_0      statisticalOperationsRestExternalFacadeClientXml;

    private static ApplicationContext             applicationContext                              = null;

    private static String                         NOT_EXISTS                                      = "NOT_EXISTS";

    // Operations
    public static String                          OPERATION_1                                     = "operation1";
    public static String                          FAMILY_1                                        = "family1";
    public static String                          FAMILY_2                                        = "family2";
    public static String                          INSTANCE_1                                      = "instance1";
    public static String                          COMMON_METADATA_1                               = "commonMetadata1";
    public static String                          QUERY_OPERATION_ID_LIKE_1                       = OperationCriteriaPropertyRestriction.ID + " " + ComparisonOperator.LIKE + " \"1\"";
    public static String                          QUERY_OPERATION_ID_LIKE_1_AND_INDICATORS_SYSTEM = OperationCriteriaPropertyRestriction.ID + " " + ComparisonOperator.LIKE + " \"1\" "
                                                                                                          + LogicalOperator.AND + " " + OperationCriteriaPropertyRestriction.IS_INDICATORS_SYSTEM + " "
                                                                                                          + ComparisonOperator.EQ + " \"true\"";
    public static String                          QUERY_FAMILY_ID_LIKE_1                          = FamilyCriteriaPropertyRestriction.ID + " " + ComparisonOperator.LIKE + " \"1\"";
    public static String                          QUERY_INSTANCE_ID_LIKE_1                        = InstanceCriteriaPropertyRestriction.ID + " " + ComparisonOperator.LIKE + " \"1\"";

    private static StatisticalOperationsRestMocks statisticalOperationsRestMocks;
    private static StatisticalOperationsCoreMocks statisticalOperationsCoreMocks;

    @SuppressWarnings({"unchecked", "rawtypes"})
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        // Start server
        assertTrue("server did not launch correctly", launchServer(ServerResource.class, true));

        // Get application context from Jetty
        applicationContext = ApplicationContextProvider.getApplicationContext();

        // Rest clients
        // xml
        {
            List providers = new ArrayList();
            providers.add(applicationContext.getBean("jaxbProvider", JAXBElementProvider.class));
            statisticalOperationsRestExternalFacadeClientXml = JAXRSClientFactory.create(jaxrsServerAddress, StatisticalOperationsV1_0.class, providers, Boolean.TRUE);
        }

        // Configuration
        ConfigurationService configurationService = applicationContext.getBean(ConfigurationService.class);
        srmApiExternalEndpoint = configurationService.retrieveSrmExternalApiUrlBase();

        // Mockito
        statisticalOperationsRestMocks = new StatisticalOperationsRestMocks(statisticalOperationsApiExternalEndpointV10, srmApiExternalEndpoint);
        statisticalOperationsCoreMocks = new StatisticalOperationsCoreMocks();
        setUpMockito();
    }

    @Test
    public void testWithoutMatchError404() throws Exception {

        String requestUri = baseApi + "/nomatch";

        WebClient webClient = WebClient.create(requestUri).accept(APPLICATION_XML);
        Response response = webClient.get();

        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
        InputStream responseActual = (InputStream) response.getEntity();
        assertTrue(StringUtils.isBlank(IOUtils.toString(responseActual)));
    }

    @Test
    public void testRetrieveOperationByIdXml() throws Exception {

        // Retrieve
        Operation operation = getStatisticalOperationsRestExternalFacadeClientXml().retrieveOperationById(OPERATION_1);

        // Validation
        StatisticalOperationsRestAsserts.assertEqualsOperation(statisticalOperationsRestMocks.mockOperation1(), operation);
    }

    @Test
    public void testRetrieveOperationByIdXmlErrorPublishedInternally() throws Exception {
        try {
            getStatisticalOperationsRestExternalFacadeClientXml().retrieveOperationById(OPERATION_1);
        } catch (ServerWebApplicationException e) {
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = extractErrorFromException(statisticalOperationsRestExternalFacadeClientXml, e);

            assertEquals(RestServiceExceptionType.OPERATION_NOT_FOUND.getCode(), exception.getCode());
            assertEquals("Operation not found with id " + NOT_EXISTS, exception.getMessage());
            assertEquals(1, exception.getParameters().getParameters().size());
            assertEquals(OPERATION_1, exception.getParameters().getParameters().get(0));
            assertNull(exception.getErrors());
        } catch (Exception e) {
            fail("Incorrect exception");
        }
    }

    @Test
    public void testRetrieveOperationByIdXmlWithoutJaxbTransformation() throws Exception {

        String requestUri = getRequestUriRetrieveOperationById(OPERATION_1);
        InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveOperationById.id1.xml");

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
    }

    @Test
    public void testRetrieveOperationByIdXmlWithoutJaxbTransformationWithXmlSufix() throws Exception {

        String requestUri = getRequestUriRetrieveOperationById(OPERATION_1) + ".xml";
        InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveOperationById.id1.xml");

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
    }

    @Test
    public void testRetrieveOperationByIdXmlWithoutJaxbTransformationWithTypeParameter() throws Exception {

        String requestUri = getRequestUriRetrieveOperationById(OPERATION_1) + "?_type=xml";
        InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveOperationById.id1.xml");

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
    }

    @Test
    public void testRetrieveOperationByIdJsonWithoutJaxbTransformation() throws Exception {

        String requestUri = getRequestUriRetrieveOperationById(OPERATION_1);
        InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveOperationById.id1.json");

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_JSON, Status.OK, responseExpected);
    }

    @Test
    public void testRetrieveOperationByIdJsonWithoutJaxbTransformationWithXmlSufix() throws Exception {

        String requestUri = getRequestUriRetrieveOperationById(OPERATION_1) + ".json";
        InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveOperationById.id1.json");

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_JSON, Status.OK, responseExpected);
    }

    @Test
    public void testRetrieveOperationByIdJsonWithoutJaxbTransformationWithTypeParameter() throws Exception {

        String requestUri = getRequestUriRetrieveOperationById(OPERATION_1) + "?_type=json";
        InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveOperationById.id1.json");

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_JSON, Status.OK, responseExpected);
    }

    @Test
    public void testRetrieveOperationByIdErrorNotExistsXml() throws Exception {
        try {
            getStatisticalOperationsRestExternalFacadeClientXml().retrieveOperationById(NOT_EXISTS);
        } catch (ServerWebApplicationException e) {
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = extractErrorFromException(statisticalOperationsRestExternalFacadeClientXml, e);

            assertEquals(RestServiceExceptionType.OPERATION_NOT_FOUND.getCode(), exception.getCode());
            assertEquals("Operation not found with id " + NOT_EXISTS, exception.getMessage());
            assertEquals(1, exception.getParameters().getParameters().size());
            assertEquals(NOT_EXISTS, exception.getParameters().getParameters().get(0));
            assertNull(exception.getErrors());
        } catch (Exception e) {
            fail("Incorrect exception");
        }
    }

    @Test
    public void testRetrieveOperationByIdErrorNotExistsXmlWithoutJaxbTransformation() throws Exception {
        String requestUri = getRequestUriRetrieveOperationById(NOT_EXISTS);
        InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveOperationById.notFound.xml");

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.NOT_FOUND, responseExpected);
    }

    @Test
    public void testRetrieveOperationByIdJsonOk() throws Exception {

        String requestUri = getRequestUriRetrieveOperationById(OPERATION_1);

        // Request and validate
        WebClient webClient = WebClient.create(requestUri).accept("application/json");
        Response response = webClient.get();

        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testFindOperationsXml() throws Exception {

        {
            // without limits
            String limit = null;
            String offset = null;
            String query = null;
            String orderBy = null;
            String fields = null;
            Operations operations = getStatisticalOperationsRestExternalFacadeClientXml().findOperations(query, orderBy, limit, offset, fields);
            StatisticalOperationsRestAsserts.assertEqualsOperations(statisticalOperationsRestMocks.mockOperations(statisticalOperationsApiExternalEndpointV10, limit, offset), operations);
        }
        {
            // with subjectArea
            String limit = null;
            String offset = null;
            String query = null;
            String orderBy = null;
            String fields = "+subjectArea";
            Operations operations = getStatisticalOperationsRestExternalFacadeClientXml().findOperations(query, orderBy, limit, offset, fields);
            StatisticalOperationsRestAsserts.assertEqualsOperationsWithSubjectArea(statisticalOperationsRestMocks.mockOperations(statisticalOperationsApiExternalEndpointV10, limit, offset), operations);
        }
        {
            // without limits, first page
            String limit = "10000";
            String offset = null;
            String query = null;
            String orderBy = null;
            String fields = null;
            Operations operations = getStatisticalOperationsRestExternalFacadeClientXml().findOperations(query, orderBy, limit, offset, fields);
            StatisticalOperationsRestAsserts.assertEqualsOperations(statisticalOperationsRestMocks.mockOperations(statisticalOperationsApiExternalEndpointV10, limit, offset), operations);
        }
        {
            // without limits, first page
            String limit = null;
            String offset = "0";
            String query = null;
            String orderBy = null;
            String fields = null;
            Operations operations = getStatisticalOperationsRestExternalFacadeClientXml().findOperations(query, orderBy, limit, offset, fields);
            StatisticalOperationsRestAsserts.assertEqualsOperations(statisticalOperationsRestMocks.mockOperations(statisticalOperationsApiExternalEndpointV10, limit, offset), operations);
        }
        {
            // first page with pagination
            String limit = "2";
            String offset = "0";
            String query = null;
            String orderBy = null;
            String fields = null;
            Operations operations = getStatisticalOperationsRestExternalFacadeClientXml().findOperations(query, orderBy, limit, offset, fields);
            StatisticalOperationsRestAsserts.assertEqualsOperations(statisticalOperationsRestMocks.mockOperations(statisticalOperationsApiExternalEndpointV10, limit, offset), operations);
        }
        {
            // second page with pagination
            String limit = "2";
            String offset = "2";
            String query = null;
            String orderBy = null;
            String fields = null;
            Operations operations = getStatisticalOperationsRestExternalFacadeClientXml().findOperations(query, orderBy, limit, offset, fields);
            StatisticalOperationsRestAsserts.assertEqualsOperations(statisticalOperationsRestMocks.mockOperations(statisticalOperationsApiExternalEndpointV10, limit, offset), operations);
        }
        {
            // last page with pagination
            String limit = "2";
            String offset = "8";
            String query = null;
            String orderBy = null;
            String fields = null;
            Operations operations = getStatisticalOperationsRestExternalFacadeClientXml().findOperations(query, orderBy, limit, offset, fields);
            StatisticalOperationsRestAsserts.assertEqualsOperations(statisticalOperationsRestMocks.mockOperations(statisticalOperationsApiExternalEndpointV10, limit, offset), operations);
        }
        {
            // no results
            String limit = "2";
            String offset = "9";
            String query = null;
            String orderBy = null;
            String fields = null;
            Operations operations = getStatisticalOperationsRestExternalFacadeClientXml().findOperations(query, orderBy, limit, offset, fields);
            StatisticalOperationsRestAsserts.assertEqualsOperations(statisticalOperationsRestMocks.mockOperations(statisticalOperationsApiExternalEndpointV10, limit, offset), operations);
        }

        // Queries
        {
            // query by id, without limits
            String limit = null;
            String offset = null;
            String query = QUERY_OPERATION_ID_LIKE_1; // operation1 and operation10
            String orderBy = null;
            String fields = null;
            Operations operations = getStatisticalOperationsRestExternalFacadeClientXml().findOperations(query, orderBy, limit, offset, fields);
            StatisticalOperationsRestAsserts.assertEqualsOperations(statisticalOperationsRestMocks.mockOperations(statisticalOperationsApiExternalEndpointV10, limit, offset, query), operations);
        }
        {
            // query by id and indicators system, without limits
            String limit = null;
            String offset = null;
            String query = QUERY_OPERATION_ID_LIKE_1_AND_INDICATORS_SYSTEM; // operation1
            String orderBy = null;
            String fields = null;
            Operations operations = getStatisticalOperationsRestExternalFacadeClientXml().findOperations(query, orderBy, limit, offset, fields);
            StatisticalOperationsRestAsserts.assertEqualsOperations(statisticalOperationsRestMocks.mockOperations(statisticalOperationsApiExternalEndpointV10, limit, offset, query), operations);
        }
        {
            // query by id, first page
            String limit = "1";
            String offset = "0";
            String query = QUERY_OPERATION_ID_LIKE_1; // operation1 and operation10
            String orderBy = null;
            String fields = null;
            Operations operations = getStatisticalOperationsRestExternalFacadeClientXml().findOperations(query, orderBy, limit, offset, fields);
            StatisticalOperationsRestAsserts.assertEqualsOperations(statisticalOperationsRestMocks.mockOperations(statisticalOperationsApiExternalEndpointV10, limit, offset, query), operations);
        }
    }

    @Test
    public void testFindOperationsXmlWithoutJaxbTransformation() throws Exception {
        {
            // without limits
            String limit = null;
            String offset = null;
            String requestUri = getRequestUriFindOperations(null, limit, offset);
            InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/findOperations.nolimits.xml");

            // Request and validate
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
        }
        {
            // second page with pagination
            String limit = "2";
            String offset = "2";
            String requestUri = getRequestUriFindOperations(null, limit, offset);
            InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/findOperations.limit2-offset2.xml");

            // Request and validate
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
        }

        // Queries
        {
            // query by id, without limits
            String limit = null;
            String offset = null;
            String query = QUERY_OPERATION_ID_LIKE_1; // operation1 and operation10
            String requestUri = getRequestUriFindOperations(query, limit, offset);
            InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/findOperations.query1.nolimits.xml");

            // Request and validate
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
        }
        {
            // query by id, first page
            String limit = "1";
            String offset = "0";
            String query = QUERY_OPERATION_ID_LIKE_1; // operation1 and operation10
            String requestUri = getRequestUriFindOperations(query, limit, offset);
            InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/findOperations.query1.limit1-offset0.xml");

            // Request and validate
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
        }
    }

    @Test
    public void testFindOperationsXmlErrorParameterIncorrectWithoutJaxbTransformation() throws Exception {

        // Metadata not supported to search
        {
            String limit = "1";
            String offset = "0";
            String query = "METADATA_INCORRECT LIKE \"1\"";
            String requestUri = getRequestUriFindOperations(query, limit, offset);
            InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/findOperations.parameterIncorrect.xml");

            // Request and validate
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.INTERNAL_SERVER_ERROR, responseExpected);
        }
        // Query incorrect
        {
            String limit = "1";
            String offset = "0";
            String query = OperationCriteriaPropertyRestriction.ID + " OP_INCORRECT \"1\"";
            String requestUri = getRequestUriFindOperations(query, limit, offset);
            InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/findOperations.queryIncorrect.xml");

            // Request and validate
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.INTERNAL_SERVER_ERROR, responseExpected);
        }
    }

    @Test
    public void testFindOperationsByFamilyXml() throws Exception {

        {
            // without limits, family 1
            String limit = null;
            String offset = null;
            String query = null;
            String orderBy = null;
            Operations operations = getStatisticalOperationsRestExternalFacadeClientXml().findOperationsByFamily(FAMILY_1, query, orderBy, limit, offset);
            StatisticalOperationsRestAsserts.assertEqualsOperations(statisticalOperationsRestMocks.mockOperationsByFamily1(statisticalOperationsApiExternalEndpointV10, limit, offset), operations);
        }
        {
            // without limits, first page
            String limit = "10000";
            String offset = null;
            String query = null;
            String orderBy = null;
            Operations operations = getStatisticalOperationsRestExternalFacadeClientXml().findOperationsByFamily(FAMILY_1, query, orderBy, limit, offset);
            StatisticalOperationsRestAsserts.assertEqualsOperations(statisticalOperationsRestMocks.mockOperationsByFamily1(statisticalOperationsApiExternalEndpointV10, limit, offset), operations);
        }
        {
            // without limits, first page
            String limit = null;
            String offset = "0";
            String query = null;
            String orderBy = null;
            Operations operations = getStatisticalOperationsRestExternalFacadeClientXml().findOperationsByFamily(FAMILY_1, query, orderBy, limit, offset);
            StatisticalOperationsRestAsserts.assertEqualsOperations(statisticalOperationsRestMocks.mockOperationsByFamily1(statisticalOperationsApiExternalEndpointV10, limit, offset), operations);
        }
        {
            // first page with pagination
            String limit = "2";
            String offset = "0";
            String query = null;
            String orderBy = null;
            Operations operations = getStatisticalOperationsRestExternalFacadeClientXml().findOperationsByFamily(FAMILY_1, query, orderBy, limit, offset);
            StatisticalOperationsRestAsserts.assertEqualsOperations(statisticalOperationsRestMocks.mockOperationsByFamily1(statisticalOperationsApiExternalEndpointV10, limit, offset), operations);
        }
        {
            // second page with pagination
            String limit = "2";
            String offset = "2";
            String query = null;
            String orderBy = null;
            Operations operations = getStatisticalOperationsRestExternalFacadeClientXml().findOperationsByFamily(FAMILY_1, query, orderBy, limit, offset);
            StatisticalOperationsRestAsserts.assertEqualsOperations(statisticalOperationsRestMocks.mockOperationsByFamily1(statisticalOperationsApiExternalEndpointV10, limit, offset), operations);
        }
        {
            // second page with pagination, family 2
            String limit = "1";
            String offset = "2";
            String query = null;
            String orderBy = null;
            Operations operations = getStatisticalOperationsRestExternalFacadeClientXml().findOperationsByFamily(FAMILY_2, query, orderBy, limit, offset);
            StatisticalOperationsRestAsserts.assertEqualsOperations(statisticalOperationsRestMocks.mockOperationsByFamily2(statisticalOperationsApiExternalEndpointV10, limit, offset), operations);
        }
        {
            // last page, with pagination
            String limit = "2";
            String offset = "4";
            String query = null;
            String orderBy = null;
            Operations operations = getStatisticalOperationsRestExternalFacadeClientXml().findOperationsByFamily(FAMILY_1, query, orderBy, limit, offset);
            StatisticalOperationsRestAsserts.assertEqualsOperations(statisticalOperationsRestMocks.mockOperationsByFamily1(statisticalOperationsApiExternalEndpointV10, limit, offset), operations);
        }
        {
            // no results
            String limit = "2";
            String offset = "7";
            String query = null;
            String orderBy = null;
            Operations operations = getStatisticalOperationsRestExternalFacadeClientXml().findOperationsByFamily(FAMILY_1, query, orderBy, limit, offset);
            StatisticalOperationsRestAsserts.assertEqualsOperations(statisticalOperationsRestMocks.mockOperationsByFamily1(statisticalOperationsApiExternalEndpointV10, limit, offset), operations);
        }
    }

    @Test
    public void testFindOperationsByFamilyXmlWithoutJaxbTransformation() throws Exception {
        {
            // without limits
            String limit = null;
            String offset = null;
            String requestUri = getRequestUriFindOperationsByFamily(FAMILY_1, limit, offset);
            InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/findOperationsByFamily.id1.nolimits.xml");

            // Request and validate
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
        }
        {
            // With limit = 2, offset = 4
            String limit = "2";
            String offset = "4";
            String requestUri = getRequestUriFindOperationsByFamily(FAMILY_1, limit, offset);
            InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/findOperationsByFamily.id1.limit2-offset4.xml");

            // Request and validate
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
        }
    }

    @Test
    public void testFindOperationsByFamilyErrorNotExistsXml() throws Exception {
        try {
            getStatisticalOperationsRestExternalFacadeClientXml().findOperationsByFamily(NOT_EXISTS, null, null, null, null);
        } catch (ServerWebApplicationException e) {
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = extractErrorFromException(statisticalOperationsRestExternalFacadeClientXml, e);

            assertEquals(RestServiceExceptionType.FAMILY_NOT_FOUND.getCode(), exception.getCode());
            assertEquals("Family not found with id " + NOT_EXISTS, exception.getMessage());
            assertEquals(1, exception.getParameters().getParameters().size());
            assertEquals(NOT_EXISTS, exception.getParameters().getParameters().get(0));
            assertNull(exception.getErrors());
        } catch (Exception e) {
            fail("Incorrect exception");
        }
    }

    @Test
    public void testRetrieveFamilyByIdXml() throws Exception {

        // Retrieve
        Family family = getStatisticalOperationsRestExternalFacadeClientXml().retrieveFamilyById(FAMILY_1);

        // Validation
        StatisticalOperationsRestAsserts.assertEqualsFamily(statisticalOperationsRestMocks.mockFamily1(), family);
    }

    @Test
    public void testRetrieveFamilyByIdXmlWithoutJaxbTransformation() throws Exception {

        String requestUri = getRequestUriRetrieveFamilyById(FAMILY_1);
        InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveFamilyById.id1.xml");

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
    }

    @Test
    public void testRetrieveFamilyByIdErrorNotExistsXml() throws Exception {
        try {
            getStatisticalOperationsRestExternalFacadeClientXml().retrieveFamilyById(NOT_EXISTS);
        } catch (ServerWebApplicationException e) {
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = extractErrorFromException(statisticalOperationsRestExternalFacadeClientXml, e);

            assertEquals(RestServiceExceptionType.FAMILY_NOT_FOUND.getCode(), exception.getCode());
            assertEquals("Family not found with id " + NOT_EXISTS, exception.getMessage());
            assertEquals(1, exception.getParameters().getParameters().size());
            assertEquals(NOT_EXISTS, exception.getParameters().getParameters().get(0));
            assertNull(exception.getErrors());
        } catch (Exception e) {
            fail("Incorrect exception");
        }
    }

    @Test
    public void testRetrieveFamilyByIdErrorNotExistsXmlWithoutJaxbTransformation() throws Exception {
        String requestUri = getRequestUriRetrieveFamilyById(NOT_EXISTS);
        InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveFamilyById.notFound.xml");

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.NOT_FOUND, responseExpected);
    }

    @Test
    public void testRetrieveInstanceByIdXml() throws Exception {

        // Retrieve
        Instance instance = getStatisticalOperationsRestExternalFacadeClientXml().retrieveInstanceById(OPERATION_1, INSTANCE_1);

        // Validation
        StatisticalOperationsRestAsserts.assertEqualsInstance(statisticalOperationsRestMocks.mockInstance1(), instance);
    }

    @Test
    public void testRetrieveInstanceByIdXmlWithoutJaxbTransformation() throws Exception {

        String requestUri = getRequestUriRetrieveInstanceById(OPERATION_1, INSTANCE_1);
        InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveInstanceById.id1.xml");

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
    }

    @Test
    public void testRetrieveInstanceByIdErrorNotExistsXml() throws Exception {
        try {
            getStatisticalOperationsRestExternalFacadeClientXml().retrieveInstanceById(OPERATION_1, NOT_EXISTS);
        } catch (ServerWebApplicationException e) {
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = extractErrorFromException(statisticalOperationsRestExternalFacadeClientXml, e);

            assertEquals(RestServiceExceptionType.INSTANCE_NOT_FOUND.getCode(), exception.getCode());
            assertEquals("Instance not found with id " + NOT_EXISTS, exception.getMessage());
            assertEquals(1, exception.getParameters().getParameters().size());
            assertEquals(NOT_EXISTS, exception.getParameters().getParameters().get(0));
            assertNull(exception.getErrors());
        } catch (Exception e) {
            fail("Incorrect exception");
        }
    }

    @Test
    public void testRetrieveInstanceByIdErrorNotExistsXmlWithoutJaxbTransformation() throws Exception {
        String requestUri = getRequestUriRetrieveInstanceById(OPERATION_1, NOT_EXISTS);
        InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveInstanceById.notFound.xml");

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.NOT_FOUND, responseExpected);
    }

    @Test
    public void testFindInstancesXml() throws Exception {

        {
            // without limits
            String limit = null;
            String offset = null;
            String query = null;
            String orderBy = null;
            Instances instances = getStatisticalOperationsRestExternalFacadeClientXml().findInstances(OPERATION_1, query, orderBy, limit, offset);
            StatisticalOperationsRestAsserts.assertEqualsInstances(statisticalOperationsRestMocks.mockInstancesByOperation1(statisticalOperationsApiExternalEndpointV10, limit, offset), instances);
        }
        {
            // without limits, first page
            String limit = "10000";
            String offset = null;
            String query = null;
            String orderBy = null;
            Instances instances = getStatisticalOperationsRestExternalFacadeClientXml().findInstances(OPERATION_1, query, orderBy, limit, offset);
            StatisticalOperationsRestAsserts.assertEqualsInstances(statisticalOperationsRestMocks.mockInstancesByOperation1(statisticalOperationsApiExternalEndpointV10, limit, offset), instances);
        }
        {
            // without limits, first page
            String limit = null;
            String offset = "0";
            String query = null;
            String orderBy = null;
            Instances instances = getStatisticalOperationsRestExternalFacadeClientXml().findInstances(OPERATION_1, query, orderBy, limit, offset);
            StatisticalOperationsRestAsserts.assertEqualsInstances(statisticalOperationsRestMocks.mockInstancesByOperation1(statisticalOperationsApiExternalEndpointV10, limit, offset), instances);
        }
        {
            // first page with pagination
            String limit = "2";
            String offset = "0";
            String query = null;
            String orderBy = null;
            Instances instances = getStatisticalOperationsRestExternalFacadeClientXml().findInstances(OPERATION_1, query, orderBy, limit, offset);
            StatisticalOperationsRestAsserts.assertEqualsInstances(statisticalOperationsRestMocks.mockInstancesByOperation1(statisticalOperationsApiExternalEndpointV10, limit, offset), instances);
        }
        {
            // second page with pagination
            String limit = "2";
            String offset = "2";
            String query = null;
            String orderBy = null;
            Instances instances = getStatisticalOperationsRestExternalFacadeClientXml().findInstances(OPERATION_1, query, orderBy, limit, offset);
            StatisticalOperationsRestAsserts.assertEqualsInstances(statisticalOperationsRestMocks.mockInstancesByOperation1(statisticalOperationsApiExternalEndpointV10, limit, offset), instances);
        }
        {
            // last page with pagination
            String limit = "2";
            String offset = "4";
            String query = null;
            String orderBy = null;
            Instances instances = getStatisticalOperationsRestExternalFacadeClientXml().findInstances(OPERATION_1, query, orderBy, limit, offset);
            StatisticalOperationsRestAsserts.assertEqualsInstances(statisticalOperationsRestMocks.mockInstancesByOperation1(statisticalOperationsApiExternalEndpointV10, limit, offset), instances);
        }
        {
            // no results
            String limit = "2";
            String offset = "9";
            String query = null;
            String orderBy = null;
            Instances instances = getStatisticalOperationsRestExternalFacadeClientXml().findInstances(OPERATION_1, query, orderBy, limit, offset);
            StatisticalOperationsRestAsserts.assertEqualsInstances(statisticalOperationsRestMocks.mockInstancesByOperation1(statisticalOperationsApiExternalEndpointV10, limit, offset), instances);
        }

        // Queries
        {
            // query by id, without limits
            String limit = null;
            String offset = null;
            String query = QUERY_INSTANCE_ID_LIKE_1; // instance1 and instance15
            String orderBy = null;
            Instances instances = getStatisticalOperationsRestExternalFacadeClientXml().findInstances(OPERATION_1, query, orderBy, limit, offset);
            StatisticalOperationsRestAsserts.assertEqualsInstances(statisticalOperationsRestMocks.mockInstancesByOperation1(statisticalOperationsApiExternalEndpointV10, limit, offset, query),
                    instances);
        }
        {
            // query by id, first page
            String limit = "1";
            String offset = "0";
            String query = QUERY_INSTANCE_ID_LIKE_1; // instance1 and instance15
            String orderBy = null;
            Instances instances = getStatisticalOperationsRestExternalFacadeClientXml().findInstances(OPERATION_1, query, orderBy, limit, offset);
            StatisticalOperationsRestAsserts.assertEqualsInstances(statisticalOperationsRestMocks.mockInstancesByOperation1(statisticalOperationsApiExternalEndpointV10, limit, offset, query),
                    instances);
        }
    }

    @Test
    public void testFindInstancesXmlWithoutJaxbTransformation() throws Exception {
        {
            // without limits
            String limit = null;
            String offset = null;
            String requestUri = getRequestUriFindInstances(OPERATION_1, null, limit, offset);
            InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/findInstances.nolimits.xml");

            // Request and validate
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
        }
        {
            // second page with pagination
            String limit = "2";
            String offset = "2";
            String requestUri = getRequestUriFindInstances(OPERATION_1, null, limit, offset);
            InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/findInstances.limit2-offset2.xml");

            // Request and validate
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
        }

        // Queries
        {
            // query by id, without limits
            String limit = null;
            String offset = null;
            String query = QUERY_INSTANCE_ID_LIKE_1;
            String requestUri = getRequestUriFindInstances(OPERATION_1, query, limit, offset);
            InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/findInstances.query1.nolimits.xml");

            // Request and validate
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
        }
        {
            // query by id, first page
            String limit = "1";
            String offset = "0";
            String query = QUERY_INSTANCE_ID_LIKE_1; // instance1 and instance15
            String requestUri = getRequestUriFindInstances(OPERATION_1, query, limit, offset);
            InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/findInstances.query1.limit1-offset0.xml");

            // Request and validate
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
        }
    }

    @Test
    public void testFindInstancesErrorOperationNotExistsXml() throws Exception {
        try {
            getStatisticalOperationsRestExternalFacadeClientXml().findInstances(NOT_EXISTS, null, null, null, null);
        } catch (ServerWebApplicationException e) {
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = extractErrorFromException(statisticalOperationsRestExternalFacadeClientXml, e);

            assertEquals(RestServiceExceptionType.OPERATION_NOT_FOUND.getCode(), exception.getCode());
            assertEquals("Operation not found with id " + NOT_EXISTS, exception.getMessage());
            assertEquals(1, exception.getParameters().getParameters().size());
            assertEquals(NOT_EXISTS, exception.getParameters().getParameters().get(0));
            assertNull(exception.getErrors());
        } catch (Exception e) {
            fail("Incorrect exception");
        }
    }

    @Test
    public void testFindFamiliesXml() throws Exception {

        {
            // without limits
            String limit = null;
            String offset = null;
            String query = null;
            String orderBy = null;
            Families families = getStatisticalOperationsRestExternalFacadeClientXml().findFamilies(query, orderBy, limit, offset);
            StatisticalOperationsRestAsserts.assertEqualsFamilies(statisticalOperationsRestMocks.mockFamilies(statisticalOperationsApiExternalEndpointV10, limit, offset), families);
        }
        {
            // without limits, first page
            String limit = "10000";
            String offset = null;
            String query = null;
            String orderBy = null;
            Families families = getStatisticalOperationsRestExternalFacadeClientXml().findFamilies(query, orderBy, limit, offset);
            StatisticalOperationsRestAsserts.assertEqualsFamilies(statisticalOperationsRestMocks.mockFamilies(statisticalOperationsApiExternalEndpointV10, limit, offset), families);
        }
        {
            // without limits, first page
            String limit = null;
            String offset = "0";
            String query = null;
            String orderBy = null;
            Families families = getStatisticalOperationsRestExternalFacadeClientXml().findFamilies(query, orderBy, limit, offset);
            StatisticalOperationsRestAsserts.assertEqualsFamilies(statisticalOperationsRestMocks.mockFamilies(statisticalOperationsApiExternalEndpointV10, limit, offset), families);
        }
        {
            // first page with pagination
            String limit = "2";
            String offset = "0";
            String query = null;
            String orderBy = null;
            Families families = getStatisticalOperationsRestExternalFacadeClientXml().findFamilies(query, orderBy, limit, offset);
            StatisticalOperationsRestAsserts.assertEqualsFamilies(statisticalOperationsRestMocks.mockFamilies(statisticalOperationsApiExternalEndpointV10, limit, offset), families);
        }
        {
            // second page with pagination
            String limit = "2";
            String offset = "2";
            String query = null;
            String orderBy = null;
            Families families = getStatisticalOperationsRestExternalFacadeClientXml().findFamilies(query, orderBy, limit, offset);
            StatisticalOperationsRestAsserts.assertEqualsFamilies(statisticalOperationsRestMocks.mockFamilies(statisticalOperationsApiExternalEndpointV10, limit, offset), families);
        }
        {
            // last page with pagination
            String limit = "2";
            String offset = "4";
            String query = null;
            String orderBy = null;
            Families families = getStatisticalOperationsRestExternalFacadeClientXml().findFamilies(query, orderBy, limit, offset);
            StatisticalOperationsRestAsserts.assertEqualsFamilies(statisticalOperationsRestMocks.mockFamilies(statisticalOperationsApiExternalEndpointV10, limit, offset), families);
        }
        {
            // no results
            String limit = "2";
            String offset = "9";
            String query = null;
            String orderBy = null;
            Families families = getStatisticalOperationsRestExternalFacadeClientXml().findFamilies(query, orderBy, limit, offset);
            StatisticalOperationsRestAsserts.assertEqualsFamilies(statisticalOperationsRestMocks.mockFamilies(statisticalOperationsApiExternalEndpointV10, limit, offset), families);
        }
        // Queries
        {
            // query by id, without limits
            String limit = null;
            String offset = null;
            String query = QUERY_FAMILY_ID_LIKE_1; // family1 and family15
            String orderBy = null;
            Families families = getStatisticalOperationsRestExternalFacadeClientXml().findFamilies(query, orderBy, limit, offset);
            StatisticalOperationsRestAsserts.assertEqualsFamilies(statisticalOperationsRestMocks.mockFamilies(statisticalOperationsApiExternalEndpointV10, limit, offset, query), families);
        }
        {
            // query by id, first page
            String limit = "1";
            String offset = "0";
            String query = QUERY_FAMILY_ID_LIKE_1; // family1 and family15
            String orderBy = null;
            Families families = getStatisticalOperationsRestExternalFacadeClientXml().findFamilies(query, orderBy, limit, offset);
            StatisticalOperationsRestAsserts.assertEqualsFamilies(statisticalOperationsRestMocks.mockFamilies(statisticalOperationsApiExternalEndpointV10, limit, offset, query), families);
        }
    }

    @Test
    public void testFindFamiliesXmlWithoutJaxbTransformation() throws Exception {
        {
            // without limits
            String limit = null;
            String offset = null;
            String requestUri = getRequestUriFindFamilies(null, limit, offset);
            InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/findFamilies.nolimits.xml");

            // Request and validate
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
        }
        {
            // second page with pagination
            String limit = "2";
            String offset = "2";
            String requestUri = getRequestUriFindFamilies(null, limit, offset);
            InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/findFamilies.limit2-offset2.xml");

            // Request and validate
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
        }

        // Queries
        {
            // query by id, without limits
            String limit = null;
            String offset = null;
            String query = QUERY_FAMILY_ID_LIKE_1;
            String requestUri = getRequestUriFindFamilies(query, limit, offset);
            InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/findFamilies.query1.nolimits.xml");

            // Request and validate
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
        }
        {
            // query by id, first page
            String limit = "1";
            String offset = "0";
            String query = QUERY_FAMILY_ID_LIKE_1; // family1 and family15
            String requestUri = getRequestUriFindFamilies(query, limit, offset);
            InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/findFamilies.query1.limit1-offset0.xml");

            // Request and validate
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
        }
    }

    @Test
    public void testRetrieveFamiliesByOperationXml() throws Exception {

        // Retrieve
        Families families = getStatisticalOperationsRestExternalFacadeClientXml().retrieveFamiliesByOperation(OPERATION_1);

        // Validation
        StatisticalOperationsRestAsserts.assertEqualsFamilies(statisticalOperationsRestMocks.mockFamiliesByOperation1(), families);
    }

    @Test
    public void testRetrieveFamiliesByOperationXmlWithoutJaxbTransformation() throws Exception {

        String requestUri = getRequestUriRetrieveFamiliesByOperation(OPERATION_1);
        InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveFamiliesByOperation.id1.xml");

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
    }

    @Test
    public void testRetrieveFamiliesByOperationErrorNotExistsXml() throws Exception {
        try {
            getStatisticalOperationsRestExternalFacadeClientXml().retrieveFamiliesByOperation(NOT_EXISTS);
        } catch (ServerWebApplicationException e) {
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = extractErrorFromException(statisticalOperationsRestExternalFacadeClientXml, e);

            assertEquals(RestServiceExceptionType.OPERATION_NOT_FOUND.getCode(), exception.getCode());
            assertEquals("Operation not found with id " + NOT_EXISTS, exception.getMessage());
            assertEquals(1, exception.getParameters().getParameters().size());
            assertEquals(NOT_EXISTS, exception.getParameters().getParameters().get(0));
            assertNull(exception.getErrors());
        } catch (Exception e) {
            fail("Incorrect exception");
        }
    }

    @Test
    public void testRetrieveStatisticalOperationTypesXml() throws Exception {

        // Retrieve
        StatisticalOperationTypes statisticalOperationTypes = getStatisticalOperationsRestExternalFacadeClientXml().retrieveStatisticalOperationTypes();

        // Validation
        StatisticalOperationsRestAsserts.assertEqualsStatisticalOperationTypes(statisticalOperationsRestMocks.mockStatisticalOperationTypes(), statisticalOperationTypes);
    }

    @Test
    public void testRetrieveStatisticalOperationTypesXmlWithoutJaxbTransformation() throws Exception {

        String requestUri = getRequestUriRetrieveStatisticalOperationTypes();
        InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveStatisticalOperationTypes.xml");

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
    }

    @Test
    public void testRetrieveOfficialityTypesXml() throws Exception {

        // Retrieve
        OfficialityTypes officialityTypes = getStatisticalOperationsRestExternalFacadeClientXml().retrieveOfficialityTypes();

        // Validation
        StatisticalOperationsRestAsserts.assertEqualsOfficialityTypes(statisticalOperationsRestMocks.mockOfficialityTypes(), officialityTypes);
    }

    @Test
    public void testRetrieveOfficialityTypesXmlWithoutJaxbTransformation() throws Exception {

        String requestUri = getRequestUriRetrieveOfficialityTypes();
        InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveOfficialityTypes.xml");

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
    }

    @Test
    public void testRetrieveInstanceTypesXml() throws Exception {

        // Retrieve
        InstanceTypes instanceTypes = getStatisticalOperationsRestExternalFacadeClientXml().retrieveInstanceTypes();

        // Validation
        StatisticalOperationsRestAsserts.assertEqualsInstanceTypes(statisticalOperationsRestMocks.mockInstanceTypes(), instanceTypes);
    }

    @Test
    public void testRetrieveInstanceTypesXmlWithoutJaxbTransformation() throws Exception {

        String requestUri = getRequestUriRetrieveInstanceTypes();
        InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveInstanceTypes.xml");

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
    }

    @Test
    public void testRetrieveStatisticalOperationSourcesXml() throws Exception {

        // Retrieve
        StatisticalOperationSources statisticalOperationSources = getStatisticalOperationsRestExternalFacadeClientXml().retrieveStatisticalOperationSources();

        // Validation
        StatisticalOperationsRestAsserts.assertEqualsStatisticalOperationSources(statisticalOperationsRestMocks.mockStatisticalOperationSources(), statisticalOperationSources);
    }

    @Test
    public void testRetrieveStatisticalOperationSourcesXmlWithoutJaxbTransformation() throws Exception {

        String requestUri = getRequestUriRetrieveStatisticalOperationSources();
        InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveStatisticalOperationSources.xml");

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
    }

    @Test
    public void testRetrieveCollMethodsXml() throws Exception {

        // Retrieve
        CollMethods collMethods = getStatisticalOperationsRestExternalFacadeClientXml().retrieveCollMethods();

        // Validation
        StatisticalOperationsRestAsserts.assertEqualsCollMethods(statisticalOperationsRestMocks.mockCollMethods(), collMethods);
    }

    @Test
    public void testRetrieveCollMethodsXmlWithoutJaxbTransformation() throws Exception {

        String requestUri = getRequestUriRetrieveCollMethods();
        InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveCollMethods.xml");

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
    }

    @Test
    public void testRetrieveCostsXml() throws Exception {

        // Retrieve
        Costs costs = getStatisticalOperationsRestExternalFacadeClientXml().retrieveCosts();

        // Validation
        StatisticalOperationsRestAsserts.assertEqualsCosts(statisticalOperationsRestMocks.mockCosts(), costs);
    }

    @Test
    public void testRetrieveCostsXmlWithoutJaxbTransformation() throws Exception {

        String requestUri = getRequestUriRetrieveCosts();
        InputStream responseExpected = StatisticalOperationsRestExternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveCosts.xml");

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
    }

    private String getRequestUriRetrieveOperationById(String operationId) {
        return baseApi + "/operations/" + operationId;
    }

    private String getRequestUriFindOperations(String query, String limit, String offset) throws Exception {
        String url = baseApi + "/operations";
        url = RestUtils.createLinkWithQueryParam(url, RestConstants.PARAMETER_QUERY, RestUtils.encodeParameter(query));
        url = RestUtils.createLinkWithQueryParam(url, RestConstants.PARAMETER_LIMIT, limit);
        url = RestUtils.createLinkWithQueryParam(url, RestConstants.PARAMETER_OFFSET, offset);
        return url;
    }

    private String getRequestUriRetrieveFamilyById(String familyId) {
        return baseApi + "/families/" + familyId;
    }

    private String getRequestUriFindFamilies(String query, String limit, String offset) throws Exception {
        String url = baseApi + "/families";
        url = RestUtils.createLinkWithQueryParam(url, RestConstants.PARAMETER_QUERY, RestUtils.encodeParameter(query));
        url = RestUtils.createLinkWithQueryParam(url, RestConstants.PARAMETER_LIMIT, limit);
        url = RestUtils.createLinkWithQueryParam(url, RestConstants.PARAMETER_OFFSET, offset);
        return url;
    }

    private String getRequestUriRetrieveInstanceById(String operationId, String instanceId) {
        return baseApi + "/operations/" + operationId + "/instances/" + instanceId;
    }

    private String getRequestUriFindInstances(String operationId, String query, String limit, String offset) throws Exception {
        String url = baseApi + "/operations/" + operationId + "/instances";
        url = RestUtils.createLinkWithQueryParam(url, RestConstants.PARAMETER_QUERY, RestUtils.encodeParameter(query));
        url = RestUtils.createLinkWithQueryParam(url, RestConstants.PARAMETER_LIMIT, limit);
        url = RestUtils.createLinkWithQueryParam(url, RestConstants.PARAMETER_OFFSET, offset);
        return url;
    }

    private String getRequestUriRetrieveFamiliesByOperation(String operationId) {
        return baseApi + "/operations/" + operationId + "/families";
    }

    private String getRequestUriFindOperationsByFamily(String familyId, String limit, String offset) {
        String url = baseApi + "/families/" + familyId + "/operations";
        url = RestUtils.createLinkWithQueryParam(url, RestConstants.PARAMETER_LIMIT, limit);
        url = RestUtils.createLinkWithQueryParam(url, RestConstants.PARAMETER_OFFSET, offset);
        return url;
    }

    private String getRequestUriRetrieveStatisticalOperationTypes() {
        return baseApi + "/statisticalOperationTypes";
    }

    private String getRequestUriRetrieveOfficialityTypes() {
        return baseApi + "/officialityTypes";
    }

    private String getRequestUriRetrieveInstanceTypes() {
        return baseApi + "/instanceTypes";
    }

    private String getRequestUriRetrieveStatisticalOperationSources() {
        return baseApi + "/statisticalOperationSources";
    }

    private String getRequestUriRetrieveCollMethods() {
        return baseApi + "/collMethods";
    }

    private String getRequestUriRetrieveCosts() {
        return baseApi + "/costs";
    }

    private static void setUpMockito() throws MetamacException {
        // MOCKS
        StatisticalOperationsBaseService statisticalOperationsBaseService = applicationContext.getBean(StatisticalOperationsBaseService.class);
        StatisticalOperationsListsService statisticalOperationsListsService = applicationContext.getBean(StatisticalOperationsListsService.class);
        CommonMetadataRestExternalFacade commonMetadataRestExternalFacade = applicationContext.getBean(CommonMetadataRestExternalFacade.class);

        // Retrieve operations
        when(statisticalOperationsBaseService.findOperationByCode(any(ServiceContext.class), eq(OPERATION_1))).thenReturn(statisticalOperationsCoreMocks.mockOperation1());
        when(statisticalOperationsBaseService.findOperationByCode(any(ServiceContext.class), eq(NOT_EXISTS))).thenReturn(null);
        // Retrieve operations by family
        mockitoFindOperationByConditionByFamily(statisticalOperationsBaseService, FAMILY_1, 25, 0);
        mockitoFindOperationByConditionByFamily(statisticalOperationsBaseService, FAMILY_1, 1000, 0);
        mockitoFindOperationByConditionByFamily(statisticalOperationsBaseService, FAMILY_1, 2, 0);
        mockitoFindOperationByConditionByFamily(statisticalOperationsBaseService, FAMILY_1, 2, 2);
        mockitoFindOperationByConditionByFamily(statisticalOperationsBaseService, FAMILY_1, 2, 4);
        mockitoFindOperationByConditionByFamily(statisticalOperationsBaseService, FAMILY_1, 2, 7);
        mockitoFindOperationByConditionByFamily(statisticalOperationsBaseService, FAMILY_2, 1, 2);
        // Find operations
        mockitoFindOperationByCondition(statisticalOperationsBaseService, 25, 0, null);
        mockitoFindOperationByCondition(statisticalOperationsBaseService, 1000, 0, null);
        mockitoFindOperationByCondition(statisticalOperationsBaseService, 2, 0, null);
        mockitoFindOperationByCondition(statisticalOperationsBaseService, 2, 2, null);
        mockitoFindOperationByCondition(statisticalOperationsBaseService, 2, 8, null);
        mockitoFindOperationByCondition(statisticalOperationsBaseService, 2, 9, null);
        mockitoFindOperationByCondition(statisticalOperationsBaseService, 25, 0, null);
        mockitoFindOperationByCondition(statisticalOperationsBaseService, 25, 0, QUERY_OPERATION_ID_LIKE_1);
        mockitoFindOperationByCondition(statisticalOperationsBaseService, 1, 0, QUERY_OPERATION_ID_LIKE_1);
        mockitoFindOperationByCondition(statisticalOperationsBaseService, 25, 0, QUERY_OPERATION_ID_LIKE_1_AND_INDICATORS_SYSTEM);
        // Retrieve family
        when(statisticalOperationsBaseService.findFamilyByCode(any(ServiceContext.class), eq(FAMILY_1))).thenReturn(statisticalOperationsCoreMocks.mockFamily1());
        when(statisticalOperationsBaseService.findFamilyByCode(any(ServiceContext.class), eq(FAMILY_2))).thenReturn(statisticalOperationsCoreMocks.mockFamily2());
        when(statisticalOperationsBaseService.findFamilyByCode(any(ServiceContext.class), eq(NOT_EXISTS))).thenReturn(null);
        mockitoFindFamilyByConditionByOperation(statisticalOperationsBaseService, OPERATION_1);
        // Find families
        mockitoFindFamilyByCondition(statisticalOperationsBaseService, 25, 0, null);
        mockitoFindFamilyByCondition(statisticalOperationsBaseService, 1000, 0, null);
        mockitoFindFamilyByCondition(statisticalOperationsBaseService, 2, 0, null);
        mockitoFindFamilyByCondition(statisticalOperationsBaseService, 2, 2, null);
        mockitoFindFamilyByCondition(statisticalOperationsBaseService, 2, 4, null);
        mockitoFindFamilyByCondition(statisticalOperationsBaseService, 2, 9, null);
        mockitoFindFamilyByCondition(statisticalOperationsBaseService, 25, 0, QUERY_FAMILY_ID_LIKE_1);
        mockitoFindFamilyByCondition(statisticalOperationsBaseService, 1, 0, QUERY_FAMILY_ID_LIKE_1);
        mockitoFindFamilyByCondition(statisticalOperationsBaseService, 1, 1, QUERY_FAMILY_ID_LIKE_1);
        // Retrieve instance
        when(statisticalOperationsBaseService.findInstanceByCode(any(ServiceContext.class), eq(INSTANCE_1))).thenReturn(statisticalOperationsCoreMocks.mockInstance1());
        when(statisticalOperationsBaseService.findInstanceByCode(any(ServiceContext.class), eq(NOT_EXISTS))).thenReturn(null);
        // Find instances
        mockitoFindInstanceByConditionByOperation(statisticalOperationsBaseService, OPERATION_1, 25, 0, null);
        mockitoFindInstanceByConditionByOperation(statisticalOperationsBaseService, OPERATION_1, 1000, 0, null);
        mockitoFindInstanceByConditionByOperation(statisticalOperationsBaseService, OPERATION_1, 2, 0, null);
        mockitoFindInstanceByConditionByOperation(statisticalOperationsBaseService, OPERATION_1, 2, 2, null);
        mockitoFindInstanceByConditionByOperation(statisticalOperationsBaseService, OPERATION_1, 2, 4, null);
        mockitoFindInstanceByConditionByOperation(statisticalOperationsBaseService, OPERATION_1, 2, 9, null);
        mockitoFindInstanceByConditionByOperation(statisticalOperationsBaseService, OPERATION_1, 25, 0, QUERY_FAMILY_ID_LIKE_1);
        mockitoFindInstanceByConditionByOperation(statisticalOperationsBaseService, OPERATION_1, 1, 0, QUERY_FAMILY_ID_LIKE_1);
        mockitoFindInstanceByConditionByOperation(statisticalOperationsBaseService, OPERATION_1, 1, 1, QUERY_FAMILY_ID_LIKE_1);
        // Lists
        when(statisticalOperationsListsService.findAllSurveyTypes(any(ServiceContext.class))).thenReturn(statisticalOperationsCoreMocks.mockFindAllSurveyTypes());
        when(statisticalOperationsListsService.findAllOfficialityTypes(any(ServiceContext.class))).thenReturn(statisticalOperationsCoreMocks.mockFindAllOfficialityTypes());
        when(statisticalOperationsListsService.findAllInstanceTypes(any(ServiceContext.class))).thenReturn(statisticalOperationsCoreMocks.mockFindAllInstanceTypes());
        when(statisticalOperationsListsService.findAllSurveySources(any(ServiceContext.class))).thenReturn(statisticalOperationsCoreMocks.mockFindAllSurveySources());
        when(statisticalOperationsListsService.findAllCollMethods(any(ServiceContext.class))).thenReturn(statisticalOperationsCoreMocks.mockFindAllCollMethods());
        when(statisticalOperationsListsService.findAllCosts(any(ServiceContext.class))).thenReturn(statisticalOperationsCoreMocks.mockFindAllCosts());

        // External APIS
        when(commonMetadataRestExternalFacade.retrieveConfigurationById(COMMON_METADATA_1)).thenReturn(statisticalOperationsRestMocks.mockExternalApiCommonMetadataRetrieveConfiguration1ById());
    }

    private static void mockitoFindOperationByConditionByFamily(StatisticalOperationsBaseService statisticalOperationsBaseService, String family, int limit, int offset) throws MetamacException {
        PagedResult<org.siemac.metamac.statistical.operations.core.domain.Operation> operations = null;
        if (FAMILY_1.equals(family)) {
            operations = statisticalOperationsCoreMocks.mockOperationsPagedResultByFamily1(String.valueOf(limit), String.valueOf(offset));
        } else if (FAMILY_2.equals(family)) {
            operations = statisticalOperationsCoreMocks.mockOperationsPagedResultByFamily2(String.valueOf(limit), String.valueOf(offset));
        } else {
            fail("family non supported. Family = " + family);
        }
        // Mock
        when(
                statisticalOperationsBaseService.findOperationByCondition(any(ServiceContext.class), argThat(new FindOperationsByFamilyMatcher(family, null, null)),
                        argThat(new PagingParameterMatcher(PagingParameter.rowAccess(offset, offset + limit, Boolean.TRUE))))).thenReturn(operations);

    }

    private static void mockitoFindOperationByCondition(StatisticalOperationsBaseService statisticalOperationsBaseService, int limit, int offset, String query) throws MetamacException {

        List<ConditionalCriteria> conditionalCriterias = null;
        String querySupported1 = QUERY_OPERATION_ID_LIKE_1;
        String querySupported2 = QUERY_OPERATION_ID_LIKE_1_AND_INDICATORS_SYSTEM;

        if (querySupported1.equals(query)) {
            conditionalCriterias = ConditionalCriteriaBuilder.criteriaFor(org.siemac.metamac.statistical.operations.core.domain.Operation.class).withProperty(OperationProperties.code()).like("%1%")
                    .build();
        } else if (querySupported2.equals(query)) {
            conditionalCriterias = ConditionalCriteriaBuilder.criteriaFor(org.siemac.metamac.statistical.operations.core.domain.Operation.class).withProperty(OperationProperties.code()).like("%1%")
                    .and().withProperty(OperationProperties.indicatorSystem()).eq(Boolean.TRUE).build();
        }
        when(
                statisticalOperationsBaseService.findOperationByCondition(any(ServiceContext.class), argThat(new FindOperationsMatcher(conditionalCriterias, null)),
                        argThat(new PagingParameterMatcher(PagingParameter.rowAccess(offset, offset + limit, Boolean.TRUE))))).thenReturn(
                statisticalOperationsCoreMocks.mockOperationsPagedResult(String.valueOf(limit), String.valueOf(offset), query));
    }

    private static void mockitoFindFamilyByConditionByOperation(StatisticalOperationsBaseService statisticalOperationsBaseService, String operation) throws MetamacException {
        PagedResult<org.siemac.metamac.statistical.operations.core.domain.Family> pagedResult = null;
        if (OPERATION_1.equals(operation)) {
            pagedResult = statisticalOperationsCoreMocks.mockFamiliesNoPagedResultByOperation1();
        } else {
            fail("Operation non supported. Operation = " + operation);
        }
        // Mock
        when(
                statisticalOperationsBaseService.findFamilyByCondition(any(ServiceContext.class), argThat(new FindFamiliesByOperationMatcher(operation)), argThat(new PagingParameterMatcher(
                        PagingParameter.noLimits())))).thenReturn(pagedResult);
    }

    private static void mockitoFindFamilyByCondition(StatisticalOperationsBaseService statisticalOperationsBaseService, int limit, int offset, String query) throws MetamacException {
        List<ConditionalCriteria> conditionalCriterias = null;
        String querySupported1 = QUERY_FAMILY_ID_LIKE_1;

        if (querySupported1.equals(query)) {
            conditionalCriterias = ConditionalCriteriaBuilder.criteriaFor(org.siemac.metamac.statistical.operations.core.domain.Family.class).withProperty(FamilyProperties.code()).like("%1%").build();
        }
        when(
                statisticalOperationsBaseService.findFamilyByCondition(any(ServiceContext.class), argThat(new FindFamiliesMatcher(conditionalCriterias, null)), argThat(new PagingParameterMatcher(
                        PagingParameter.rowAccess(offset, offset + limit, Boolean.TRUE))))).thenReturn(
                statisticalOperationsCoreMocks.mockFamiliesPagedResult(String.valueOf(limit), String.valueOf(offset), query));
    }

    private static void mockitoFindInstanceByConditionByOperation(StatisticalOperationsBaseService statisticalOperationsBaseService, String operation, int limit, int offset, String query)
            throws MetamacException {
        List<ConditionalCriteria> conditionalCriterias = null;
        String querySupported1 = QUERY_INSTANCE_ID_LIKE_1;

        if (querySupported1.equals(query)) {
            conditionalCriterias = ConditionalCriteriaBuilder.criteriaFor(org.siemac.metamac.statistical.operations.core.domain.Instance.class).withProperty(InstanceProperties.code()).like("%1%")
                    .build();
        }

        PagedResult<org.siemac.metamac.statistical.operations.core.domain.Instance> pagedResult = null;
        if (OPERATION_1.equals(operation)) {
            pagedResult = statisticalOperationsCoreMocks.mockInstancesPagedResultByOperation1(String.valueOf(limit), String.valueOf(offset), query);
        } else {
            fail("Operation non supported. Operation = " + operation);
        }
        // Mock
        when(
                statisticalOperationsBaseService.findInstanceByCondition(any(ServiceContext.class), argThat(new FindInstancesByOperationMatcher(operation, conditionalCriterias, null)),
                        argThat(new PagingParameterMatcher(PagingParameter.rowAccess(offset, offset + limit, Boolean.TRUE))))).thenReturn(pagedResult);
    }

    private StatisticalOperationsV1_0 getStatisticalOperationsRestExternalFacadeClientXml() {
        WebClient.client(statisticalOperationsRestExternalFacadeClientXml).reset();
        WebClient.client(statisticalOperationsRestExternalFacadeClientXml).accept(APPLICATION_XML);
        return statisticalOperationsRestExternalFacadeClientXml;
    }
}

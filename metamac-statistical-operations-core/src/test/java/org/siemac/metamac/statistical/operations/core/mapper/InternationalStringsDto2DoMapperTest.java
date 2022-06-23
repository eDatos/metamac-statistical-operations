package org.siemac.metamac.statistical.operations.core.mapper;

import static org.mockito.Mockito.never;
import static org.siemac.metamac.common.test.utils.MetamacMocks.mockInternationalStringDto;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.siemac.metamac.common.test.MetamacBaseTest;
import org.siemac.metamac.common.test.mock.ConfigurationServiceMockImpl;
import org.siemac.metamac.common.test.utils.MetamacMocks;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.InternationalStringRepository;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.operations.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.operations.core.utils.asserts.StatisticalOperationsAsserts;
import org.siemac.metamac.statistical.operations.core.utils.mocks.StatisticalOperationsMocks;
import org.springframework.beans.factory.annotation.Value;

public class InternationalStringsDto2DoMapperTest extends MetamacBaseTest {

    private final Dto2DoMapper     dto2DoMapper         = new Dto2DoMapperImpl();

    protected ConfigurationService configurationService = new ConfigurationServiceMockImpl();
    InternationalStringRepository  repository           = Mockito.mock(InternationalStringRepository.class);

    private static final String    METADATA_NAME        = "LOREM_IPSUM";

    @Before
    public void setConfigurationToMapper() throws Exception {
        setFieldToBaseMapper("configurationService", configurationService);
        setFieldToMapper("internationalStringRepository", repository);
    }

    @After
    public void validateMockitoUsage() {
        Mockito.validateMockitoUsage();
    }

    @Test
    public void testInternationalStringDto2DoWithDtoNullAndDoNull() throws Exception {
        // NULL, NULL
        testInternationalStringDtoToEntity(null, null);
        Mockito.verify(repository, never()).delete(Mockito.any(InternationalString.class));
    }

    @Test
    public void testInternationalStringDto2DoWithDtoNullAndDoExists() throws Exception {
        // NULL, EXISTS
        InternationalString internationalString = StatisticalOperationsMocks.mockInternationalString();
        testInternationalStringDtoToEntity(null, internationalString);
        Mockito.verify(repository).delete(Mockito.any(InternationalString.class));
    }

    @Test
    public void testInternationalStringDto2DoWithDtoExistsAndDoNull() throws Exception {
        // EXISTS, NULL
        testInternationalStringDtoToEntity(mockInternationalStringDto(), null);
        Mockito.verify(repository, never()).delete(Mockito.any(InternationalString.class));
    }

    @Test
    public void testInternationalStringDto2DoWithDtoExistsAndDoExists() throws Exception {
        // EXISTS, EXISTS
        InternationalString internationalString = StatisticalOperationsMocks.mockInternationalString();
        testInternationalStringDtoToEntity(mockInternationalStringDto(), internationalString);
        Mockito.verify(repository, never()).delete(Mockito.any(InternationalString.class));
    }

    @Test
    public void testInternationalStringDto2DoWithDtoExistsAndDoNullAndDtoWithoutLocalisedStrings() throws Exception {
        // EXISTS, EXISTS
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_REQUIRED, METADATA_NAME));

        InternationalStringDto internationalStringDto = mockInternationalStringDto();
        internationalStringDto.getTexts().clear();

        testInternationalStringDtoToEntity(internationalStringDto, null);
        Mockito.verify(repository, never()).delete(Mockito.any(InternationalString.class));
    }

    @Test
    public void testInternationalStringDto2DoWithDtoNullAndWithoutLocaleInDefaultLanguage() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_WITHOUT_DEFAULT_LANGUAGE, METADATA_NAME));
        testInternationalStringDtoToEntity(mockInternationalStringDto("rs", "text"), null);
        Mockito.verify(repository, never()).delete(Mockito.any(InternationalString.class));
    }

    @Test
    public void testInternationalStringDto2DoWithoutLocaleInDefaultLanguage() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_WITHOUT_DEFAULT_LANGUAGE, METADATA_NAME));
        testInternationalStringDtoToEntity(mockInternationalStringDto("rs", "text"), StatisticalOperationsMocks.mockInternationalString(configurationService.retrieveLanguageDefault(), "texto"));
        Mockito.verify(repository, never()).delete(Mockito.any(InternationalString.class));
    }

    @Test
    public void testInternationalStringDto2DoWithDtoMaximumLength() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_MAXIMUM_LENGTH, METADATA_NAME, "4000"));
        testInternationalStringDtoToEntity(mockInternationalStringDto("rs", MetamacMocks.mockString(4050)),
                StatisticalOperationsMocks.mockInternationalString(configurationService.retrieveLanguageDefault(), "texto"));
        Mockito.verify(repository, never()).delete(Mockito.any(InternationalString.class));
    }

    private void setFieldToBaseMapper(String fieldName, ConfigurationService fieldValue) throws Exception {
        Field field = dto2DoMapper.getClass().getSuperclass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(dto2DoMapper, fieldValue);
    }

    private void setFieldToMapper(String fieldName, Object fieldValue) throws Exception {
        Field field = dto2DoMapper.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(dto2DoMapper, fieldValue);
    }

    @SuppressWarnings("rawtypes")
    private Method getVisibleInternationalStringDtoToEntityMethod() throws Exception {
        Class[] parameterTypes = new Class[3];
        parameterTypes[0] = InternationalStringDto.class;
        parameterTypes[1] = InternationalString.class;
        parameterTypes[2] = String.class;
        Method internationalStringDtoToEntityMethod = dto2DoMapper.getClass().getDeclaredMethod("internationalStringToEntity", parameterTypes);
        internationalStringDtoToEntityMethod.setAccessible(true);
        return internationalStringDtoToEntityMethod;
    }

    private void testInternationalStringDtoToEntity(InternationalStringDto internationalStringDto, InternationalString internationalString) throws Exception {
        Method internationalStringDtoToEntityMethod = getVisibleInternationalStringDtoToEntityMethod();

        Object[] parameters = new Object[3];
        parameters[0] = internationalStringDto;
        parameters[1] = internationalString;
        parameters[2] = METADATA_NAME;

        InternationalString result = (InternationalString) internationalStringDtoToEntityMethod.invoke(dto2DoMapper, parameters);
        StatisticalOperationsAsserts.assertEqualsInternationalString(result, internationalStringDto);
    }
}

package org.siemac.metamac.statistical.operations.core.serviceimpl.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.statistical.operations.core.constants.StatisticalOperationsConstants;
import org.siemac.metamac.statistical.operations.core.dto.OperationDto;
import org.siemac.metamac.statistical.operations.core.error.ServiceExceptionType;

public class TsvExportationUtils {

    private TsvExportationUtils() {}

    /**
     * OPERATIONS
     * */

    public static String exportStatisticalOperations(List<OperationDto> operationsDtos, List<String> languages) throws MetamacException {
        OutputStream outputStream = null;
        OutputStreamWriter writer = null;
        try {
            File file = File.createTempFile("operations", ".tsv");
            outputStream = new FileOutputStream(file);
            writer = new OutputStreamWriter(outputStream, StatisticalOperationsConstants.TSV_EXPORTATION_ENCODING);

            writeStatisticalOperationsHeader(writer, languages);
            for (OperationDto opDto : operationsDtos) {
                writer.write(StatisticalOperationsConstants.TSV_LINE_SEPARATOR);
                //Identificadores
                writeStringSingleFirstItem(writer, opDto.getCode());
                writeStringSingleItem(writer, opDto.getUrn());
                writeItemInternationalStringDto(writer, opDto.getTitle(), languages);
                writeItemInternationalStringDto(writer, opDto.getAcronym(), languages);

                //Clasificadores de contenido
                writeStringSingleItem(writer, opDto.getSubjectArea().getCode());
                writeExternalItemDtoListItem(writer, opDto.getSecondarySubjectAreas());

                //Descriptores de contenido
                writeItemInternationalStringDto(writer, opDto.getObjective(), languages);
                writeItemInternationalStringDto(writer, opDto.getDescription(), languages);

                //Descriptores de clase
                writeStringSingleItem(writer, opDto.getSurveyType() == null? null : opDto.getSurveyType().getIdentifier());
                writeStringSingleItem(writer, opDto.getOfficialityType() == null? null : opDto.getOfficialityType().getIdentifier());
                writeStringSingleItem(writer, opDto.getIndicatorSystem().toString());

                //Descriptores de producción
                writeExternalItemDtoListItem(writer, opDto.getProducer());
                writeExternalItemDtoListItem(writer, opDto.getRegionalResponsible());
                writeExternalItemDtoListItem(writer, opDto.getRegionalContributor());
                writeDateItem(writer, opDto.getCreatedDate());
                writeDateItem(writer, opDto.getInternalInventoryDate());
                writeStringSingleItem(writer, opDto.getCurrentlyActive().toString());
                writeStringSingleItem(writer, opDto.getStatus().getName());
                writeStringSingleItem(writer, opDto.getProcStatus().getName());

                //Descriptores de difusión
                writeExternalItemDtoListItem(writer, opDto.getPublisher());
                writeStringSingleItem(writer, opDto.getCommonMetadata() == null? null : opDto.getCommonMetadata().getCode());
                writeItemInternationalStringDto(writer, opDto.getRelPolUsAc(),languages);
                writeStringSingleItem(writer, opDto.getReleaseCalendar().toString());
                writeStringSingleItem(writer, opDto.getReleaseCalendarAccess());
                writeExternalItemDtoListItem(writer, opDto.getUpdateFrequency());
                writeStringSingleItem(writer, opDto.getCurrentInternalInstance() == null? null : opDto.getCurrentInternalInstance().getCode());
                writeStringSingleItem(writer, opDto.getCurrentInstance() == null? null : opDto.getCurrentInstance().getCode());
                writeDateItem(writer, opDto.getInventoryDate());

                //Marco legal
                writeItemInternationalStringDto(writer, opDto.getSpecificLegalActs(), languages);
                writeItemInternationalStringDto(writer, opDto.getSpecificDataSharing(),languages);
                writeItemInternationalStringDto(writer, opDto.getRevPractice(), languages);
                writeItemInternationalStringDto(writer, opDto.getRevPolicy(), languages);

                //Anotaciones
                writeItemInternationalStringDto(writer, opDto.getNotes(), languages);
                writeItemInternationalStringDto(writer, opDto.getComment(), languages);
            }
            writer.flush();
            return file.getName();
        } catch (Exception e) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.EXPORTATION_TSV_ERROR).withMessageParameters(e).build();
        } finally {
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(writer);
        }
    }

    private static void writeStatisticalOperationsHeader(OutputStreamWriter writer, List<String> languages) throws IOException {
        //Identificadores
        writer.write(StatisticalOperationsConstants.TSV_HEADER_CODE);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_URN);
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_TITLE);
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_ACRONYM);
        //Clasificadores de contenido
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_SUBJECT_AREA);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_SECONDARY_SUBJECT_AREAS);
        //Descriptores de contenido
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_OBJECTIVE);
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_DESCRIPTION);
        //Descriptores de clase
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_STATISTICAL_OPERATION_TYPE);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_OFFICIALITY_TYPE);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_INDICATOR_SYSTEM);
        //Descriptores de producción
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_PRODUCER);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_REGIONAL_RESPONSIBLE);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_REGIONAL_CONTRIBUTOR);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_CREATED_DATE);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_INTERNAL_INVENTORY_DATE);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_CURRENTLY_ACTIVE);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_STATUS);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_PROC_STATUS);
        //Descriptores de difusión
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_PUBLISHER);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_COMMON_METADATA);
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_REL_POL_US_AC);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_RELEASE_CALENDAR);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_RELEASE_CALENDAR_ACCESS);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_UPDATE_FREQUENCY);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_CURRENT_INTERNAL_INSTANCE);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_CURRENT_INSTANCE);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_INVENTORY_DATE);
        //Marco legal
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_SPECIFIC_LEGAL_ACTS);
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_COMMON_DATA_SHARING);
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_CONFIDENTALITY_POLICY);
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_CONFIDENTALITY_DATA_TREATMENT);
        //Anotaciones
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_NOTES);
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_COMMENT);
    }

    // ---------------------------------------------------------------------------------------------------------------
    // COMMON UTILS
    // ---------------------------------------------------------------------------------------------------------------

    private static void writeHeaderItem(OutputStreamWriter writer, List<String> languages, String header) throws IOException{
        for (String language : languages) {
            writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
            writer.write(header + StatisticalOperationsConstants.TSV_HEADER_INTERNATIONAL_STRING_SEPARATOR + language);
        }
    }

    private static String removeUnsupportedCharaters(String string) {
        if (StringUtils.isNotBlank(string)) {
            string = string.replace('\n', ' ');
            string = string.replace('\t', ' ');
            string = string.replace('\r', ' ');
            string = string.replace('\b', ' ');
            string = string.replace('\f', ' ');
        }
        return string;
    }

    private static void writeStringSingleFirstItem(OutputStreamWriter writer, String item) throws IOException {
        if(item != null){
            writer.write(item);
        }
    }

    private static void writeStringSingleItem(OutputStreamWriter writer, String item) throws IOException {
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        if(item != null){
            writer.write(item);
        }
    }

    private static void writeDateItem(OutputStreamWriter writer, Date item) throws IOException {
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        if(item != null){
            DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");
            String strDate = dateFormat.format(item);
            writer.write(strDate);
        }
    }

    private static void writeExternalItemDtoListItem(OutputStreamWriter writer, Set<ExternalItemDto> list) throws IOException {
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        if(list != null && list.size() > 0){
            String value = null;
            for(ExternalItemDto item : list){
                value = value.join(", ",item.getCode());
            }
            writer.write(value);
        }
    }

    private static void writeItemInternationalStringDto(OutputStreamWriter writer, InternationalStringDto internationalString, List<String> languages) throws IOException {
       for (String language : languages) {
           if(internationalString != null) {
               String stringInLocale = internationalString.getLocalisedLabel(language);
               writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
               if (stringInLocale != null) {
                   stringInLocale = removeUnsupportedCharaters(stringInLocale);
                   writer.write(stringInLocale);
               }
           }else{
               writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
           }
       }
    }

}

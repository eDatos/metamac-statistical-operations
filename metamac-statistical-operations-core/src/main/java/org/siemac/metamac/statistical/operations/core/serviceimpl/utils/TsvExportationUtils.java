package org.siemac.metamac.statistical.operations.core.serviceimpl.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.statistical.operations.core.constants.StatisticalOperationsConstants;
import org.siemac.metamac.statistical.operations.core.dto.OperationDto;
import org.siemac.metamac.statistical.operations.core.error.ServiceExceptionType;

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
                writeStringSingleItem(writer, opDto.getCode());//String code;
                writeStringSingleItem(writer, opDto.getUrn());//String urn;
                writeUriItem(writer, opDto); //opDto ->ExternalItemDto_URI
                writeItemInternationalStringDto(writer, opDto.getTitle(), languages);//InternationalStringDto title;
                writeItemInternationalStringDto(writer, opDto.getAcronym(), languages);//InternationalStringDto acronym;

                //Clasificadores de contenido
           //--     writeItemStatisticalFamily(writer, opDto); //????
                writeStringSingleItem(writer, opDto.getSubjectArea().getCode()); // ExternalItemDto subjectArea;
                writeExternalItemDtoListItem(writer, opDto.getSecondarySubjectAreas()); //Set<ExternalItemDto> secondarySubjectAreas

                //Descriptores de contenido
                writeItemInternationalStringDto(writer, opDto.getObjective(), languages);//InternationalStringDto objective
                writeItemInternationalStringDto(writer, opDto.getDescription(), languages); // InternationalStringDto description
           //--  writeItemStatisticalInstance(writer, opDto); // currentInstance ?

                //Descriptores de clase
                writeStringSingleItem(writer, opDto.getSurveyType() == null? null : opDto.getSurveyType().getIdentifier()); //SurveyTypeDto surveyType
                writeStringSingleItem(writer, opDto.getOfficialityType() == null? null : opDto.getOfficialityType().getIdentifier());// OfficialityTypeDto officialityType
                writeStringSingleItem(writer, opDto.getIndicatorSystem().toString());//Boolean indicatorSystem

                //Descriptores de producción
                writeExternalItemDtoListItem(writer, opDto.getProducer()); // Set<ExternalItemDto> producer
                writeExternalItemDtoListItem(writer, opDto.getRegionalResponsible()); // Set<ExternalItemDto> regionalResponsible
                writeExternalItemDtoListItem(writer, opDto.getRegionalContributor()); // Set<ExternalItemDto> regionalContributor
                writeDateItem(writer, opDto.getCreatedDate());//Date createdDate
                writeDateItem(writer, opDto.getInternalInventoryDate());//Date internalInventoryDate
                writeStringSingleItem(writer, opDto.getCurrentlyActive().toString());//Boolean currentlyActive
                writeStringSingleItem(writer, opDto.getStatus().getName());//StatusEnum status
                writeStringSingleItem(writer, opDto.getProcStatus().getName());//ProcStatusEnum procStatus

                //Descriptores de difusión
                writeExternalItemDtoListItem(writer, opDto.getPublisher());//Set<ExternalItemDto> publisher
                writeItemInternationalStringDto(writer, opDto.getRelPolUsAc(),languages);//InternationalStringDto relPolUsAc;
                writeStringSingleItem(writer, opDto.getReleaseCalendar().toString());//Boolean releaseCalendar
                writeStringSingleItem(writer, opDto.getReleaseCalendarAccess());//String releaseCalendarAccess
                writeExternalItemDtoListItem(writer, opDto.getUpdateFrequency());//Set<ExternalItemDto> updateFrequency
           //--     writeItemCurrentInternalInstance(writer, opDto.getCurrentInternalInstance());//InstanceBaseDto currentInternalInstance;
           //--     writeItemCurrentInstance(writer, opDto);//InstanceBaseDto currentInstance;
                writeDateItem(writer, opDto.getInventoryDate());//Date inventoryDate
           // --    writeItemContact(writer, opDto);

                //Marco legal
           //--     writeItemCommonLegalActs(writer, opDto);
                writeItemInternationalStringDto(writer, opDto.getSpecificLegalActs(), languages); //InternationalStringDto specificLegalActs;
                writeItemInternationalStringDto(writer, opDto.getSpecificDataSharing(),languages);//InternationalStringDto specificDataSharing;
                writeItemInternationalStringDto(writer, opDto.getRevPolicy(), languages);//InternationalStringDto revPolicy;
            //--    writeItemConfidentalityDataTreatment(writer, opDto);

                //Anotaciones
                writeItemInternationalStringDto(writer, opDto.getNotes(), languages); //InternationalStringDto notes;
                writeItemInternationalStringDto(writer, opDto.getComment(), languages);//InternationalStringDto comment;
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
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_URI);
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_TITLE);
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_ACRONYM);
        //Clasificadores de contenido
  /*      writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_STATISTICAL_FAMILY);*/
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_SUBJECT_AREA);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_SECONDARY_SUBJECT_AREAS);
        //Descriptores de contenido
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_OBJECTIVE);
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_DESCRIPTION);
      /*  writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_STATISTICAL_INSTANCE);*/
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
        writer.write(StatisticalOperationsConstants.TSV_HEADER_REL_POL_US_AC);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_RELEASE_CALENDAR);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_RELEASE_CALENDAR_ACCESS);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_UPDATE_FREQUENCY);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
    /*    writer.write(StatisticalOperationsConstants.TSV_HEADER_CURRENT_INTERNAL_INSTANCE);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_CURRENT_INSTANCE); */
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_INVENTORY_DATE);
     //   writer.write(StatisticalOperationsConstants.TSV_HEADER_CONTACT);
        //Marco legal
      //--  writeStatisticalOperationsHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_COMMON_LEGAL_ACTS);
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_SPECIFIC_LEGAL_ACTS);
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_COMMON_DATA_SHARING);
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_CONFIDENTALITY_POLICY);
      //--  writeStatisticalOperationsHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_CONFIDENTALITY_DATA_TREATMENT);
        //Anotaciones
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_NOTES);
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_COMMENT);
    }

    private static void writeUriItem(OutputStreamWriter writer, OperationDto operationDto) throws IOException {
        String uri = operationDto.getCommonMetadata() == null ? operationDto.getSubjectArea().getUri(): operationDto.getCommonMetadata().getUri();
        writeStringSingleItem(writer, uri);
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
        if(list != null && list.size() > 0){
            writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
            String value = null;
            for(ExternalItemDto item : list){
                value = value.join(", ",item.getCode());
            }
            writer.write(value);
        }
    }

    private static void writeItemInternationalStringDto(OutputStreamWriter writer, InternationalStringDto internationalString, List<String> languages) throws IOException {
       if(internationalString != null) {
           for (String language : languages) {
               String stringInLocale = internationalString.getLocalisedLabel(language);
               writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
               if (stringInLocale != null) {
                   stringInLocale = removeUnsupportedCharaters(stringInLocale);
                   writer.write(stringInLocale);
               }
           }
       }
    }

}

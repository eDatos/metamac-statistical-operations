package org.siemac.metamac.statistical.operations.core.serviceimpl.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.AccessController;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CategoryResourceInternal;
import org.siemac.metamac.statistical.operations.core.constants.StatisticalOperationsConstants;
import org.siemac.metamac.statistical.operations.core.domain.Instance;
import org.siemac.metamac.statistical.operations.core.domain.Operation;
import org.siemac.metamac.statistical.operations.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.operations.core.error.ServiceExceptionType;
import sun.security.action.GetPropertyAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TsvExportationUtils {
    private static Logger logger = LoggerFactory.getLogger(TsvExportationUtils.class);

    private TsvExportationUtils() {
    }

    /**
     * OPERATIONS
     */

    public static String exportStatisticalOperations(List<Operation> operations, List<String> languages, Map<String, CategoryResourceInternal> categories) throws MetamacException {
        OutputStream outputStream = null;
        OutputStreamWriter writer = null;
        try {
            File file = File.createTempFile("operations", ".tsv");
            outputStream = new FileOutputStream(file);
            writer = new OutputStreamWriter(outputStream, StatisticalOperationsConstants.TSV_EXPORTATION_ENCODING);

            writeStatisticalOperationsHeader(writer, languages);
            for (Operation operation : operations) {
                writer.write(StatisticalOperationsConstants.TSV_LINE_SEPARATOR);
                // Identificadores
                writeStringSingleFirstItem(writer, operation.getCode());
                writeStringSingleItem(writer, operation.getUrn());
                writeItemInternationalString(writer, operation.getTitle(), languages);
                writeItemInternationalString(writer, operation.getAcronym(), languages);

                // Clasificadores de contenido
                writeStringSingleItem(writer, operation.getSubjectArea() == null ? null : categories.get(operation.getSubjectArea().getCode()).getId());
                writeCategoryResourceInternal(writer, getCategoryFromSecondarySubjectAreas(categories, operation.getSecondarySubjectAreas()));

                // Descriptores de contenido
                writeItemInternationalString(writer, operation.getObjective(), languages);
                writeItemInternationalString(writer, operation.getDescription(), languages);

                // Descriptores de clase
                writeStringSingleItem(writer, operation.getSurveyType() == null ? null : operation.getSurveyType().getIdentifier());
                writeStringSingleItem(writer, operation.getOfficialityType() == null ? null : operation.getOfficialityType().getIdentifier());
                writeStringSingleItem(writer, operation.getIndicatorSystem().toString());

                // Descriptores de producción
                writeStringSingleItem(writer, operation.getTechnicianInCharge());
                writeStringSingleItem(writer, operation.getAssistantTechnician());
                writeExternalItemListItem(writer, operation.getProducer());
                writeExternalItemListItem(writer, operation.getResponsible());
                writeExternalItemListItem(writer, operation.getContributor());
                writeDateItem(writer, operation.getCreatedDate());
                writeDateItem(writer, operation.getInternalInventoryDate());
                writeStringSingleItem(writer, operation.getCurrentlyActive().toString());
                writeStringSingleItem(writer, operation.getStatus().getName());
                writeStringSingleItem(writer, operation.getProcStatus().getName());
                writeItemInternationalString(writer, operation.getGenderPerspective(), languages);

                // Descriptores de difusión
                writeExternalItemListItem(writer, operation.getPublisher());
                writeStringSingleItem(writer, operation.getCommonMetadata() == null ? null : operation.getCommonMetadata().getCode());
                writeItemInternationalString(writer, operation.getRelPolUsAc(), languages);
                writeStringSingleItem(writer, operation.getReleaseCalendar().toString());
                writeStringSingleItem(writer, operation.getReleaseCalendarAccess());
                writeExternalItemListItem(writer, operation.getUpdateFrequency());
                writeStringSingleItem(writer, getCurrentInternalInstance(operation.getInstances()) == null ? null : getCurrentInternalInstance(operation.getInstances()).getCode());
                writeStringSingleItem(writer, getCurrentInstance(operation.getInstances()) == null ? null : getCurrentInstance(operation.getInstances()).getCode());
                writeDateItem(writer, operation.getInventoryDate());
                writeStringSingleItem(writer, operation.getDiffusionPublicationVisible().toString());

                // Marco legal
                writeItemInternationalString(writer, operation.getSpecificLegalActs(), languages);
                writeItemInternationalString(writer, operation.getSpecificDataSharing(), languages);
                writeItemInternationalString(writer, operation.getRevPractice(), languages);
                writeItemInternationalString(writer, operation.getRevPolicy(), languages);

                // Anotaciones
                writeItemInternationalString(writer, operation.getNotes(), languages);
                writeItemInternationalString(writer, operation.getComment(), languages);
            }
            writer.flush();
            return file.getName();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.EXPORTATION_TSV_ERROR).withMessageParameters(e).build();
        } finally {
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(writer);
        }
    }

    private static List<CategoryResourceInternal> getCategoryFromSecondarySubjectAreas(Map<String, CategoryResourceInternal> categories, Set<ExternalItem> secondarySubjectAreas) {
        List<CategoryResourceInternal> categoryResourceInternal = new ArrayList<CategoryResourceInternal>();

        for (ExternalItem categoryElement : secondarySubjectAreas) {
            CategoryResourceInternal category = categories.get(categoryElement.getCode());
            if (category != null) {
                categoryResourceInternal.add(category);
            }
        }
        return categoryResourceInternal;

    }

    private static void writeStatisticalOperationsHeader(OutputStreamWriter writer, List<String> languages) throws IOException {
        // Identificadores
        writer.write(StatisticalOperationsConstants.TSV_HEADER_CODE);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_URN);
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_TITLE);
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_ACRONYM);
        // Clasificadores de contenido
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_SUBJECT_AREA);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_SECONDARY_SUBJECT_AREAS);
        // Descriptores de contenido
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_OBJECTIVE);
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_DESCRIPTION);
        // Descriptores de clase
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_STATISTICAL_OPERATION_TYPE);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_OFFICIALITY_TYPE);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_INDICATOR_SYSTEM);
        // Descriptores de producción
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_TECHNICIAN_IN_CHARGE);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_ASSISTANT_TECHNICIAN);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_PRODUCER);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_RESPONSIBLE);
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_CONTRIBUTOR);
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
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_GENDER_PERSPECTIVE);
        // Descriptores de difusión
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
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        writer.write(StatisticalOperationsConstants.TSV_HEADER_DIFUSION_PRODUCTION_VISIBLE);
        // Marco legal
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_SPECIFIC_LEGAL_ACTS);
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_COMMON_DATA_SHARING);
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_CONFIDENTALITY_POLICY);
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_CONFIDENTALITY_DATA_TREATMENT);
        // Anotaciones
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_NOTES);
        writeHeaderItem(writer, languages, StatisticalOperationsConstants.TSV_HEADER_COMMENT);
    }

    // ---------------------------------------------------------------------------------------------------------------
    // COMMON UTILS
    // ---------------------------------------------------------------------------------------------------------------

    private static void writeHeaderItem(OutputStreamWriter writer, List<String> languages, String header) throws IOException {
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
        if (item != null) {
            writer.write(item);
        }
    }

    private static void writeStringSingleItem(OutputStreamWriter writer, String item) throws IOException {
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        if (item != null) {
            writer.write(item);
        }
    }

    private static void writeDateItem(OutputStreamWriter writer, DateTime item) throws IOException {
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        if (item != null) {
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            String strDate = dateFormat.format(item.toDate());
            writer.write(strDate);
        }
    }

    private static void writeExternalItemListItem(OutputStreamWriter writer, Set<ExternalItem> list) throws IOException {
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        if (list != null && list.size() > 0) {
            Set<String> values = new HashSet<>();
            String value = null;
            for (ExternalItem item : list) {
                values.add(item.getCode());
            }
            writer.write(values.toString().replaceAll("^\\[|\\]$", ""));
        }
    }

    private static void writeCategoryResourceInternal(OutputStreamWriter writer, List<CategoryResourceInternal> list) throws IOException {
        writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
        if (list != null && list.size() > 0) {
            Set<String> values = new HashSet<>();
            String value = null;
            for (CategoryResourceInternal item : list) {
                values.add(item.getId());
            }
            writer.write(values.toString().replaceAll("^\\[|\\]$", ""));
        }
    }

    private static void writeItemInternationalString(OutputStreamWriter writer, InternationalString internationalString, List<String> languages) throws IOException {
        for (String language : languages) {
            if (internationalString != null) {
                String stringInLocale = internationalString.getLocalisedLabel(language);
                writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
                if (stringInLocale != null) {
                    stringInLocale = removeUnsupportedCharaters(stringInLocale);
                    writer.write(stringInLocale);
                }
            } else {
                writer.write(StatisticalOperationsConstants.TSV_SEPARATOR);
            }
        }
    }

    private static Instance getCurrentInternalInstance(List<Instance> instances) {
        for (Instance instance : instances) {
            if (ProcStatusEnum.PUBLISH_INTERNALLY.equals(instance.getProcStatus())) {
                return instance;
            }
        }
        return null;
    }

    private static Instance getCurrentInstance(List<Instance> instances) {
        for (Instance instance : instances) {
            if (ProcStatusEnum.PUBLISH_EXTERNALLY.equals(instance.getProcStatus())) {
                return instance;
            }
        }

        return null;
    }
}

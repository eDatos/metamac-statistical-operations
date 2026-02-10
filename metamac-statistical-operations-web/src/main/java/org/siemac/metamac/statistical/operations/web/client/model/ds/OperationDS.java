package org.siemac.metamac.statistical.operations.web.client.model.ds;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class OperationDS extends DataSource {

    // IDENTIFIERS
    public static final String ID                         = "op-id";
    public static final String CODE                       = "op-code";
    public static final String STATISTIC_PLAN             = "op-statistic-plan";
    public static final String URN                        = "op-urn";
    public static final String CODE_VIEW                  = "op-code-view";                // Not mapped in DTO
    public static final String TITLE                      = "op-title";
    public static final String ACRONYM                    = "op-acron";
    public static final String PUBLICATION_STREAM_STATUS  = "op-publication-stream-status";
    public static final String EDATOS_MIGRATION_STATUS    = "op-edatos-migration-status";
    // CONTENT CLASSIFIERS
    public static final String SUBJECT_AREA               = "op-subjectItem";
    public static final String SECONDARY_SUBJECT_AREAS    = "op-subjetc-secon";
    // CONTENT DESCRIPTORS
    public static final String DESCRIPTION                = "op-desc";
    public static final String OBJECTIVE                  = "op-obj";
    // CLASS DESCRIPTORS
    public static final String STATISTICAL_OPERATION_TYPE = "op-sur";
    public static final String OFFICIALITY_TYPE           = "op-off-type";
    public static final String INDICATOR_SYSTEM           = "op-ind-system";
    // PRODUCTION DESCRIPTORS
    public static final String PRODUCER                   = "op-producer";
    public static final String REG_RESPONSIBLE            = "op-reg-resp";
    public static final String REG_CONTRIBUTOR            = "op-reg-con";
    public static final String INTERNAL_INVENTORY_DATE    = "op-int-inv-date";
    public static final String CURRENTLY_ACTIVE           = "op-currently-active";
    public static final String STATUS                     = "op-status";
    public static final String PROC_STATUS                = "op-proc-status";
    public static final String PROC_STATUS_VIEW           = "op-proc-status-view";         // Not mapped in DTO
    public static final String CREATED_DATE               = "op-created-date";
    public static final String TECHNICIAN_IN_CHARGE       = "op-tech-in-charge";
    public static final String ASSISTANT_TECHNICIAN       = "op-tech-assistant";
    // DIFUSSION DESCRIPTORS
    public static final String PUBLISHER                  = "op-publisherItem";
    public static final String RE_POL_US_AC               = "op-pol-us";
    public static final String RELEASE_CALENDAR           = "op-calendar";
    public static final String RELEASE_CALENDAR_ACCESS    = "op-calendar-access";
    public static final String UPDATE_FREQUENCY           = "op-up-freq";
    public static final String CURRENT_INSTANCE           = "op-current-inst";
    public static final String CURRENT_INTERNAL_INSTANCE  = "op-current-in-inst";
    public static final String INVENTORY_DATE             = "op-inv-date";
    public static final String DIFFUSION_AND_PUBLICATION  = "op-diffusion-publication";
    public static final String REV_POLICY                 = "op-rev-pol";
    public static final String REV_PRACTICE               = "op-rev-pract";
    public static final String COMMON_METADATA            = "op-com-met";
    public static final String PUBLISH_MSG_STATUS_KAFKA   = "op-publish-msg-kafka";
    public static final String NEWNESS_UNTIL_DATE         = "op-date-new-until";
    public static final String FEATURED_UNTIL_DATE        = "op-date-featured-until";
    // LEGAL ACTS
    public static final String SPECIFIC_LEGAL_ACTS        = "op-spe-legal-acts";
    public static final String SPECIFIC_DATA_SHARING      = "op-spe-data-shar";
    // ANNOTATIONS
    public static final String COMMENTS                   = "op-com";
    public static final String NOTES                      = "op-not";

    public static final String GENDER_PERSPECTIVE         = "op-gen-pers";

    public static final String DTO                        = "operation-dto";

    public OperationDS() {

        DataSourceTextField id = new DataSourceTextField(CODE);
        id.setPrimaryKey(true);
        addField(id);

        DataSourceTextField title = new DataSourceTextField(TITLE);
        addField(title);

    }
}
